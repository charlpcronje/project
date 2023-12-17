package net.integrategroup.ignite.persistence.service;

import java.util.List;

import net.integrategroup.ignite.persistence.model.LibraryFile;
import net.integrategroup.ignite.persistence.model.LibraryFolder;
import net.integrategroup.ignite.persistence.model.LibraryFolderView;

public interface LibraryService {

	List<LibraryFolderView> getFolderTree();

	LibraryFolder findByLibraryFolderId(Long folderId);

	LibraryFolder saveFolder(Long folderId, String folderName, String description, Long parentFolderId);

	int getFileCount(Long folderId);

	int getFolderCount(Long folderId);

	void deleteFolder(Long folderId);

	List<LibraryFile> getFolderFiles(Long libraryFolderId);

}
