package net.integrategroup.ignite.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.integrategroup.ignite.persistence.model.InvoiceFile;
import net.integrategroup.ignite.persistence.model.Participant;
import net.integrategroup.ignite.persistence.model.ProjectExpenseFile;
import net.integrategroup.ignite.persistence.service.InvoiceFileService;
import net.integrategroup.ignite.persistence.service.KeyValuePairService;
import net.integrategroup.ignite.persistence.service.ParticipantService;
import net.integrategroup.ignite.persistence.service.ProjectExpenseFileService;
import net.integrategroup.ignite.reports.ReportEngine;
import net.integrategroup.ignite.utils.IgniteConstants;
import net.integrategroup.ignite.utils.IgniteUtils;

@Controller
public class UploadController {

	@Autowired
	ProjectExpenseFileService projectExpenseFileService;

	@Autowired
	InvoiceFileService invoiceFileService;

	@Autowired
	KeyValuePairService keyValuePairService;

	@Autowired
	ReportEngine reportEngine;

	@Autowired
	ParticipantService participantService;
	private Logger logger= Logger.getLogger(getClass().getName());
    private int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;

	public UploadController() {
	   MultipartConfigElement multipartConfigElement = new MultipartConfigElement("/temp",
       MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);
	}

	@PostMapping(path = "/upload", consumes = "multipart/form-data")
	public String upload(HttpServletRequest request, MultipartRequest multipartRequest, RedirectAttributes redirectAttributes) throws Exception {
		// We need to figure out what we are receiving because each file type will be saved to a different location
		// We need to update the relevant table to notify Ignite of an upload
		// In the UI we will need to refresh the relevant table and in the UI we will need to redirect to a proper page
		// We need to calculate the saved file name. E.g: projectExpenseId-projectExpenseFileId.ext
		String successResult = "redirect:/upload-success";
		String failResult = "redirect:/upload-failure?e=";
		boolean fileAlreadySaved = false;    // if the saving is handled outside of the normal upload file code
		boolean handled = false;

		// get the file parts/info that we will need
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		if (fileMap.size() == 0) {
			return failResult + "No file was attached to the request.";
		}

		if (fileMap.size() != 1) {
			return failResult + "Only one file must be submitted at a time.";
		}

		Iterator<String> it = fileMap.keySet().iterator();
		String fileMapEntry = it.next(); // If we want to upload multiple files: have to loop here though all the files
		MultipartFile multipartFile = fileMap.get(fileMapEntry);

		String originalFileName = multipartFile.getOriginalFilename();
		String fileType = IgniteUtils.getFileExtension(originalFileName);

		Long fileSize = multipartFile.getSize();

		// We dont calculate or set the filename here because each type might write
		// to a different path and might generate the filename differently
		String targetFilename = null;

		// Parameters parsed into the dialog form as generic data cargo
		String description = request.getParameter("fileDlgFileDescription");
		String type = request.getParameter("fileDlgUploadType");
		String key = request.getParameter("fileDlgPrimaryKey");
		String keyValue = request.getParameter("fileDlgPrimaryKeyValue");

		logger.info("originalFileName:= " + originalFileName);
		logger.info("fileType:=        " + fileType);
		logger.info("fileSize:=       " + fileSize);
		logger.info("description:=   " + description);
		logger.info("type:=         " + type);
		logger.info("key:=         " + key);
		logger.info("keyValue:=   " + keyValue);

		if ("projectExpense".equalsIgnoreCase(type)) {
			Long projectExpenseId = Long.valueOf(keyValue);
			
			try {
				targetFilename = processProjectExpense(fileType, projectExpenseId, originalFileName, description, fileSize);
			} catch (Exception e) {
				e.printStackTrace();
				return failResult + e.getMessage();
			}

			handled = true;
		}

		// fake code for non existent upload type
		if ("invoice".equalsIgnoreCase(type)) {
			Long invoiceId = Long.valueOf(keyValue);

			try {
				targetFilename = processInvoice(fileType, invoiceId, originalFileName, description, fileSize);
			} catch (Exception e) {
				return failResult + e.getMessage();
			}

			handled = true;
		}

		if ("participant-logo".equalsIgnoreCase(type)) {
			Long participantId = Long.valueOf(keyValue);

			try {
				byte[] logoContent = multipartFile.getBytes();

				logger.info("Processing upload: [" + fileType + "]");
				processParticipantLogo(participantId, logoContent, fileType);
				fileAlreadySaved = true;
			} catch (Exception e) {
				e.printStackTrace();
				return failResult + e.getMessage();
			}

			handled = true;
		}

		if (!handled){
			return failResult + "The upload type was not handled.";
		}

		String errorMessage = null;
		// We only save the file if we've actually handled the generic info that comes along with the file
		try {
			if (!fileAlreadySaved) {
				uploadFile(fileMap, targetFilename);
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (e.getMessage() == null) {
				errorMessage = "An internal error has occurred.  The file was not submitted correctly.";
			} else {
				errorMessage = e.getMessage();
			}
		}

		if (errorMessage == null) {
			// success
			return successResult;
		} else {
			// failure
			return failResult + errorMessage;
		}
	}

	private void processParticipantLogo(Long participantId, byte[] logoContent, String fileType) throws Exception {
		// Update the database table Participant, set ParticipantLogo = logoContent where ParticipantId = participantId

		Participant p = participantService.findByParticipantId(participantId);

		if ((fileType.equalsIgnoreCase(".jpg"))
			|| (fileType.equalsIgnoreCase(".jpeg"))) {
			Blob blob = new SerialBlob(logoContent);

			p.setParticipantLogo(blob);

			participantService.save(p);
		}
		else {
			throw new Exception("Only jpg images are allowed to be uploaded.");
		}


	}

	private String processInvoice(String fileType,
						            Long invoiceId,
						            String originalFileName,
						            String description,
						            Long fileSize) throws Exception {

		// So basically the same as processProjectExpense apart from:

		// 1. Use the KEY_UPLOAD_PATH_PROJECT_INVOICE constant to determine where to save the file
		// 2. Save a ProjectInvoiceExpenseFile record and use it's ID to generate a filename

		// now that we have saved entry for the file we can generate the
		// filename and set the directory to write to
		String targetPath = keyValuePairService.getKeyValue(IgniteConstants.KEY_UPLOAD_PATH_INVOICE);

		// We need to throw our toys out of the cot if the key is not defined.
		// That would mean an incomplete configuration of Ignite
		if (targetPath == null) {
			throw new Exception("A key variable for \"" +
                                   IgniteConstants.KEY_UPLOAD_PATH_INVOICE + "\" has not been configured.");
		}

		// ********************************************************************
		// Anything that might prevent our save should raise an error and send it back to the
		// UI before this point, otherwise we will land up with an orphan record
		//
		// If there are any validation issues, simply throw an Exception at this
		// point to back out of the process, without saving an orphan record
		// and without saving an orphan file
		// ********************************************************************

		// Insert into purchase expense file
		InvoiceFile iif = new InvoiceFile();
		iif.setDescription(description);
		iif.setFileSize(fileSize);
		iif.setOriginalFileName(originalFileName);
		iif.setFileType(fileType);
		iif.setInvoiceId(invoiceId);
		iif.setUploadDate(new Date());

		iif = invoiceFileService.save(iif);

		// Our filename is saved as projectExpenseId_projectExpenseFileId.fileType
		// keyValue is the string value of the projectExpenseId
		String baseFilename = String.valueOf(invoiceId) + "_" + iif.getInvoiceFileId() + fileType;
		String targetFilename = IgniteUtils.getTerminatedPath(targetPath) + baseFilename;

		// Not the prettiest, but update the record we just created to set the filename
        iif.setFileName(baseFilename);
        invoiceFileService.save(iif);

        return targetFilename;
	}

	private String processProjectExpense(String fileType,
                                            Long projectExpenseId,
                                            String originalFileName,
                                            String description,
                                            Long fileSize) throws Exception {

		// now that we have saved entry for the file we can generate the
		// filename and set the directory to write to
		String targetPath = keyValuePairService.getKeyValue(IgniteConstants.KEY_UPLOAD_PATH_PROJECT_EXPENSE);

		// We need to throw our toys out of the cot if the key is not defined.
		// That would mean an incomplete configuration of Ignite
		if (targetPath == null) {
			throw new Exception("A key variable for \"" +
                                   IgniteConstants.KEY_UPLOAD_PATH_PROJECT_EXPENSE + "\" has not been configured.");
		}

		// ********************************************************************
		// Anything that might prevent our save should raise an error and send it back to the
		// UI before this point, otherwise we will land up with an orphan record
		//
		// If there are any validation issues, simply throw an Exception at this
		// point to back out of the process, without saving an orphan record
		// and without saving an orphan file
		// ********************************************************************

		// Insert into purchase expense file
		ProjectExpenseFile pef = new ProjectExpenseFile();
		pef.setDescription(description);
		pef.setFileSize(fileSize);
		pef.setOriginalFileName(originalFileName);
		pef.setFileType(fileType);
		pef.setProjectExpenseId(projectExpenseId);
		pef.setUploadDate(new Date());

		pef = projectExpenseFileService.save(pef);

		// Our filename is saved as projectExpenseId_projectExpenseFileId.fileType
		// keyValue is the string value of the projectExpenseId
		String baseFilename = String.valueOf(projectExpenseId) + "_" + pef.getProjectExpenseFileId() + fileType;
		String targetFilename = IgniteUtils.getTerminatedPath(targetPath) + baseFilename;

		// Not the prettiest, but update the record we just created to set the filename
        pef.setFileName(baseFilename);
        projectExpenseFileService.save(pef);

        return targetFilename;
	}


	private void uploadFile(Map<String, MultipartFile> fileMap, String targetFilename) throws Exception {
		Iterator<String> it = fileMap.keySet().iterator();
		String fileMapEntry = it.next(); // If we want to upload multiple files: have to loop here though all the files
		MultipartFile multipartFile = fileMap.get(fileMapEntry);

		saveUploadedFile(targetFilename, multipartFile);
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

}
