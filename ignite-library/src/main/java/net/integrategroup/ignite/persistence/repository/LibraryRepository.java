package net.integrategroup.ignite.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.integrategroup.ignite.persistence.model.LibraryFile;
import net.integrategroup.ignite.persistence.model.LibraryFolder;
import net.integrategroup.ignite.persistence.model.LibraryFolderView;

@Repository
public interface LibraryRepository extends CrudRepository<LibraryFolder, Long> {

	@Query("SELECT l FROM LibraryFolder l WHERE l.libraryFolderId = ?1")
	LibraryFolder findByLibraryFolderId(Long libraryFolderId);

	@Query(
			value="select " +
	              "    new net.integrategroup.ignite.persistence.model.LibraryFolderView(" +
				  "      libraryFolderId, parentFolderId, folderName, pathName, description, " +
	              "      lastUpdateTimestamp, lastUpdateUserName, level," +
				  "		 rowOrder, childCount" +
				  "    )" +
				  "  from " +
	              "    LibraryFolderView " +
				  "  order by " +
	              "    rowOrder, folderName")
	List<LibraryFolderView> getFolderTree();

	@Query("SELECT l FROM LibraryFolder l WHERE parentFolderId IS NULL ORDER BY folderName")
	List<LibraryFolder> getRootFolders();

	@Query("SELECT l FROM LibraryFolder l WHERE parentFolderId = ?1 ORDER BY folderName")
	List<LibraryFolder> getFoldersFor(Long folderId);

	@Query("SELECT count(1) FROM LibraryFolder WHERE parentFolderId = ?1")
	int getFolderCount(Long folderId);

	@Query("SELECT count(1) FROM LibraryFile WHERE libraryFolderId = ?1")
	int getFileCount(Long folderId);

	@Query("SELECT f FROM LibraryFile f WHERE libraryFolderId = ?1 ORDER BY filename")
	List<LibraryFile> getFolderFiles(Long libraryFolderId);

}
