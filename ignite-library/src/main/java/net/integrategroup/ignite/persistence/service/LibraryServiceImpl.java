package net.integrategroup.ignite.persistence.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.integrategroup.ignite.persistence.model.LibraryFile;
import net.integrategroup.ignite.persistence.model.LibraryFolder;
import net.integrategroup.ignite.persistence.model.LibraryFolderView;
import net.integrategroup.ignite.persistence.repository.LibraryRepository;
import net.integrategroup.ignite.utils.SecurityUtils;

@Service
public class LibraryServiceImpl implements LibraryService {

	@Autowired
	LibraryRepository libraryRepository;

	@Autowired
	SecurityUtils securityUtils;

	@Override
	public List<LibraryFolderView> getFolderTree() {
		return libraryRepository.getFolderTree();
	}

	@Override
	public LibraryFolder saveFolder(Long folderId, String folderName, String description, Long parentFolderId) {
		LibraryFolder libraryFolder = null;

		if (folderId == null) {
			libraryFolder = new LibraryFolder();
		} else {
			libraryFolder = libraryRepository.findByLibraryFolderId(folderId);
		}

		// TODO: back end must check that this folder doesn't already exist

		libraryFolder.setParentFolderId(parentFolderId);
		libraryFolder.setFolderName(folderName);
		libraryFolder.setDescription(description);
		libraryFolder.setLastUpdateTimestamp(new Date());
		libraryFolder.setLastUpdateUserName(securityUtils.getUsername());

		libraryFolder = libraryRepository.save(libraryFolder);

		return libraryFolder;
	}

	@Override
	public int getFolderCount(Long folderId) {
		return libraryRepository.getFolderCount(folderId);
	}

	@Override
	public int getFileCount(Long folderId) {
		return libraryRepository.getFileCount(folderId);
	}

	@Override
	public LibraryFolder findByLibraryFolderId(Long folderId) {
		return libraryRepository.findByLibraryFolderId(folderId);
	}

	@Override
	public void deleteFolder(Long folderId) {
		LibraryFolder libraryFolder = libraryRepository.findByLibraryFolderId(folderId);
		libraryRepository.delete(libraryFolder);
	}

	@Override
	public List<LibraryFile> getFolderFiles(Long libraryFolderId) {
		return libraryRepository.getFolderFiles(libraryFolderId);
	}


}
