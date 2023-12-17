package net.integrategroup.ignite.controller.api;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.integrategroup.ignite.persistence.model.ProjectExpense;
import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;
import net.integrategroup.ignite.persistence.model.VBankTransaction;
import net.integrategroup.ignite.persistence.model.VProjectBankExpenses;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ProjectExpenseFileService;
import net.integrategroup.ignite.persistence.service.ProjectExpenseService;
import net.integrategroup.ignite.utils.HttpUtils;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;
import net.integrategroup.ignite.utils.MapUtils;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/project-expense")
public class ProjectExpenseApiController {
	
	private Logger logger = Logger.getLogger(ProjectExpenseApiController.class.getName());
	
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

    private MultipartConfigElement multipartConfigElement;
    
	@Autowired
	ProjectExpenseService projectExpenseService;
	
	@Autowired
	ProjectExpenseFileService projectExpenseFileService;
	
	@Autowired
	KeyValuePairService keyValuePairService;
	
	public ProjectExpenseApiController() {
	   multipartConfigElement = 
			        new MultipartConfigElement("/temp", MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
	}
		
	@GetMapping
	public ResponseEntity<?> getProjectExpenses(HttpServletRequest request) {
		try {
			String username = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "u");
			String cardNumber = HttpUtils.getHeaderVariable(request, ApiUtils.PARAM_IDENTIFIER + "cn");
			
			// get project expenses associated with this user
			List<VProjectBankExpenses> expenseList = projectExpenseService.getProjectExpensesByUsernameAndCardNumber(username, cardNumber);
			
			return ResponseEntity.ok(expenseList);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping(path = "", consumes = "multipart/form-data")
	public ResponseEntity<?> saveProjectExpense(HttpServletRequest request, 
			                                    MultipartRequest multipartRequest, 
			                                    RedirectAttributes redirectAttributes) {
		String targetPath = keyValuePairService.getKeyValue(IgniteConstants.KEY_UPLOAD_PATH_PROJECT_EXPENSE);

		String username = request.getHeader("username");
		String projectName = request.getHeader("projectName");
		String cardNumber = request.getHeader("cardNumber");
		String description = request.getHeader("description");
		Double amountPerUnit = IgniteUtils.toDouble(request.getHeader("amountPerUnit"));
		Double numberOfUnits = IgniteUtils.toDouble(request.getHeader("numberOfUnits"));
		String noteToAccountant = request.getHeader("noteToAccountant");
		String expenseType = request.getHeader("expenseType");
		String paymentMethodCode = request.getHeader("paymentMethodCode");
		Date purchaseDate = IgniteUtils.toDate(request.getHeader("purchaseDate"));
		ProjectExpenseFile projectExpenseFile = null;
		
		try {
			Long projectExpenseId = projectExpenseService
					.createProjectExpense(cardNumber,
					                      projectName, 
					                      purchaseDate, 
					                      description, 
					                      amountPerUnit, 
					                      numberOfUnits,
					                      noteToAccountant, 
					                      expenseType, 
					                      paymentMethodCode, 
					                      username);
			
			if (projectExpenseId == null) {
				// Could not save the parent record, an exception should have been thrown, this is just an extra check
				return ResponseEntity.badRequest().body("Could not save the Project Expense header");
			}

			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			
			for (String key: fileMap.keySet()) {
				MultipartFile multipartFile = fileMap.get(key);
				
				long filesize = multipartFile.getSize();
				String originalFilename = multipartFile.getOriginalFilename();
				String ext = IgniteUtils.getFileExtension(originalFilename);
				
				// NOTE: this is copied from UploadController
				// Insert into purchase expense file
				projectExpenseFile = new ProjectExpenseFile();
				projectExpenseFile.setDescription(description);
				projectExpenseFile.setFileSize(filesize);
				projectExpenseFile.setOriginalFileName(originalFilename);
				projectExpenseFile.setFileType(ext);
				projectExpenseFile.setProjectExpenseId(projectExpenseId);
				projectExpenseFile.setUploadDate(new Date());

				projectExpenseFile = projectExpenseFileService.save(projectExpenseFile);

				// Our filename is saved as projectExpenseId_projectExpenseFileId.fileType
				// keyValue is the string value of the projectExpenseId
				String baseFilename = String.valueOf(projectExpenseId) + "_" + projectExpenseFile.getProjectExpenseFileId() + ext;
				String targetFilename = IgniteUtils.getTerminatedPath(targetPath) + baseFilename;

				// Not the prettiest, but update the record we just created to set the filename
		        projectExpenseFile.setFileName(baseFilename);
		        projectExpenseFileService.save(projectExpenseFile);
		        
				// Now save the MultipartFile to targetFilename
		        saveUploadedFile(targetFilename, multipartFile);
			}
		
			return ResponseEntity.ok(projectExpenseFile);
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	private void saveUploadedFile(String targetFilename, MultipartFile multipartFile) throws IOException {
		byte[] buffer = new byte[1024]; // This buffer will store the data read from the file
										//
		// Now create the output file on the server.
		File outputFile = new File(targetFilename);

		InputStream reader = null;
		FileOutputStream writer = null;
		long totalBytes = 0;
		try {
			outputFile.createNewFile();
			reader = multipartFile.getInputStream(); // Create the input stream to uploaded file to read data from it
			writer = new FileOutputStream(outputFile); // Create writer for 'outputFile' to write data read from the
														// file

			int bytesRead = 0;
			// Iteratively read data from the file and write to 'outputFile'
			while ((bytesRead = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, bytesRead);
				totalBytes += bytesRead;
			}

			writer.flush();
		} finally {
			IgniteUtils.closeQuietly(reader);
			IgniteUtils.closeQuietly(writer);
		}

		logger.info(targetFilename + " written: " + totalBytes + " bytes");
	}

	@Deprecated
	// TODO: we need another one of these with a multipart file on it
	// this used to be the post mapping
	public ResponseEntity<?> saveProjectExpense(@RequestBody HashMap<String, Object> data) {
		int fileCount = 0;
		
		try {
			MapUtils mu = new MapUtils();
			
			String username = mu.getAsStringOrNull(data, "username");
			String projectName = mu.getAsStringOrNull(data, "projectName");
			String cardNumber = mu.getAsStringOrNull(data, "cardNumber");
			
			Long d = mu.getAsLongOrNull(data, "purchaseDate");
			Date purchaseDate = new Date(d);
			
			String description = mu.getAsStringOrNull(data, "description");
			Double amountPerUnit = mu.getAsDoubleOrNull(data, "amountPerUnit");
			Double numberOfUnits = mu.getAsDoubleOrNull(data, "numberOfUnits");
			String noteToAccountant = mu.getAsStringOrNull(data, "noteToAccountant");
			String expenseType = mu.getAsStringOrNull(data, "expenseType");
			String paymentMethodCode = mu.getAsStringOrNull(data, "paymentMethodCode");
			
			Long projectExpenseId = projectExpenseService.createProjectExpense(cardNumber,
					projectName, purchaseDate, description, amountPerUnit, numberOfUnits,
					noteToAccountant, expenseType, paymentMethodCode, username);
			
			if (projectExpenseId == null) {
				// Could not save the parent record, an exception should have been thrown, this is just an extra check
				return ResponseEntity.badRequest().body("Could not save the Project Expense header");
			}
			
			// TODO: save the files locally, and insert into projectExpenseFile
			// projectExpenseFileId, projectExpenseId, Filename, OriginalFileName, FileType, FileSize, UploadDate, Description, LastUpdateUserName, LastUpdateTimestamp
			
			for (int foo = 0; foo < 1000; foo++) {
				String key = "file" + (foo + 1);
				
				if (!data.containsKey(key)) {
					break;
				}
				
				fileCount = foo + 1;
				String base64Data = mu.getAsStringOrNull(data, key);
				byte[] fc = DatatypeConverter.parseBase64Binary(base64Data);
				
				String filename = /* TODO: get location */ + projectExpenseId + "_" + (foo + 1) + ".jpg";
				
				FileOutputStream fos = null;
				OutputStream os = null;
				
				try {
					File file = new File(filename);
					fos = new FileOutputStream(file);
					os = new BufferedOutputStream(fos);
					
					os.write(fc);
				} finally {
					IgniteUtils.closeQuietly(os);
					IgniteUtils.closeQuietly(fos);
				}
				
				// TODO: save file to dir with above key
			}
			
			
			// TODO: figure out what is in the cargo and send it to a proc 
			// TODO: (how will we deal with the photos?!)
			
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			
			// delete temporary files... 
			for (int foo = 0; foo < fileCount; foo++) {
				IgniteUtils.deleteFile("file" + (foo + 1));
			}
			
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}


}
