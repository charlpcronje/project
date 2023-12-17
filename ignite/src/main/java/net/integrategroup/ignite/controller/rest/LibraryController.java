package net.integrategroup.ignite.controller.rest;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.integrategroup.ignite.persistence.model.LibraryFolder;
import net.integrategroup.ignite.persistence.model.LibraryFolderView;
import net.integrategroup.ignite.persistence.service.LibraryService;
import net.integrategroup.ignite.utils.MapUtils;


/**
 * @author Tony De Buys
 *
 */
@RestController
@RequestMapping("/rest/ignite/v1/library")
public class LibraryController {

	@Autowired
	LibraryService libraryService;

	@GetMapping()
	public ResponseEntity<?> getFolderTree() {
		JSONArray jaResult = new JSONArray();

		try {
			List<LibraryFolderView> folders = libraryService.getFolderTree();

			for (LibraryFolderView folder : folders) {
				// this loop only needs to add the top levels,
				// the others will be added by the addFolderChildren method
				if (folder.getLevel() != 1) {
					continue;
				}

				JSONObject jaFolder = folderToJSONObject(folder);

				if (folder.getChildCount() > 0 ) {
					addFolderChildren(folders, jaFolder);
				}

				jaResult.put(jaFolder);
			}

			return ResponseEntity.ok(jaResult.toString(4));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> createFolder(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long folderId = mu.getAsLongOrNull(data, "libraryFolderId");
		String folderName = mu.getAsStringOrNull(data, "folderName");
		String description = mu.getAsStringOrNull(data, "description");
		Long parentFolderId = mu.getAsLongOrNull(data, "parentFolderId");
		try {
			LibraryFolder libraryFolder = libraryService.saveFolder(folderId, folderName, description, parentFolderId);

			return ResponseEntity.ok(libraryFolder);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteFolder(@RequestBody Map<String, Object> data) {

		MapUtils mu = new MapUtils();
		Long folderId = mu.getAsLongOrNull(data, "libraryFolderId");

		try {
			// get the file count for the directory, stop if there are files
			// get the children count, stop if there are folders
			int fileCount = libraryService.getFileCount(folderId);

			if (fileCount > 0) {
				throw new Exception("The folder cannot be delete because files exist in the folder");
			}

			int folderCount = libraryService.getFolderCount(folderId);
			if (folderCount > 0) {
				throw new Exception("The folder cannot be delete because there are subfolders");
			}

			LibraryFolder libraryFolder = libraryService.findByLibraryFolderId(folderId);

			libraryService.deleteFolder(folderId);

			return ResponseEntity.ok(libraryFolder.getParentFolderId());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private void addFolderChildren(List<LibraryFolderView> folders, JSONObject folderObject) {
		Long id = folderObject.getLong("id");

		JSONArray childrenJa = new JSONArray();

		for (LibraryFolderView folder: folders) {
			Long parentId = folder.getParentFolderId();

			if (parentId == null) {
				continue;
			}

			if (id.longValue() == parentId.longValue()) {
				// add this one...
				JSONObject childObject = folderToJSONObject(folder);
				childrenJa.put(childObject);

				if (folder.getChildCount() > 0) {
					addFolderChildren(folders, childObject);
				}
			}
		}

		if (childrenJa.length() > 0) {
			folderObject.put("children", childrenJa);
		}
	}

	private JSONObject folderToJSONObject(LibraryFolderView folder) {
		JSONObject jaFolder = new JSONObject();

		jaFolder.put("id", folder.getLibraryFolderId());
		jaFolder.put("name", folder.getFolderName());
		jaFolder.put("description", folder.getDescription());
		jaFolder.put("pathName", folder.getPathName());

		return jaFolder;
	}

	@GetMapping("/files/{libraryFolderId}")
	public ResponseEntity<?> getFolderFiles(@PathVariable("libraryFolderId") Long libraryFolderId) {
		try {
			return ResponseEntity.ok(libraryService.getFolderFiles(libraryFolderId));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
