package net.integrategroup.ignite.filemanager;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import net.integrategroup.ignite.utils.IgniteUtils;

/**
 * @author Tony De Buys
 * 2021/03/29
 *
 * File manager which work off a base directory and tries to prevents access
 * to files outside of the base directory
 *
 */
public class FileManager {
	private Logger logger = Logger.getLogger(FileManager.class.getName());

	private File baseFolder;
	private File currentDirectory;

	public FileManager(String baseDirectoryName) {
		baseFolder = new File(baseDirectoryName);
		currentDirectory = baseFolder;
	}

	public List<DiskEntity> getDiskEntities(String directoryName) throws Exception {
		String base = baseFolder.getAbsolutePath();
		base = base.replace("\\", "/");

		if (
				(directoryName == null) ||
				(directoryName.isEmpty()) ||
				("/".equals(directoryName))) {
			directoryName = base;
		} else {
			if (!directoryName.startsWith(base)) {
				directoryName = baseFolder.getAbsolutePath() + "/" + directoryName;
			}
		}

		File dir = new File(directoryName);

		if (dir.exists()) {
			currentDirectory = dir;
		} else {
			String msg = "The directory does not exist";
			logger.severe(msg + ": " + directoryName);
			throw new Exception(msg);
		}

		return getDiskEntities();
	}

	public List<DiskEntity> getDiskEntities() {
		List<DiskEntity> result = new ArrayList<>();

		File[] files = currentDirectory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return true;
			}
		} );

		if (!currentDirectory.getPath().equals(baseFolder.getPath())) {
			// this is a sub directory, add the "go back option"
			DiskEntity entity = new DiskEntity(baseFolder, currentDirectory.getParentFile().getPath(), "..", null, "Directory", true);
			result.add(entity);
		}

		for (File file: files) {
			DiskEntity entity = new DiskEntity(baseFolder, file);
			result.add(entity);
		}

		return result;
	}

	public void delete(String filename) throws IOException  {
		File f = new File(IgniteUtils.getTerminatedPath(baseFolder.getCanonicalPath()) + filename);

		if (!f.getCanonicalPath().startsWith(baseFolder.getCanonicalPath())) {
			throw new IOException("Cannot delete files/folders outside of the base directory");
		}

		if (!f.delete()) {
			if (f.isDirectory()) {
				throw new IOException("The folder could not be deleted - there could be files in the folder");
			} else {
				String msg = "The file could not be deleted";

				logger.severe(msg + ": " + f.getAbsolutePath());
				throw new IOException(msg);
			}
		}
	}

	public boolean mkdir(String target, String newDir) throws IOException, Exception {
		if (target == null) {
			target = "";
		} else {
			target = IgniteUtils.getTerminatedPath(target);
		}

		String dirPath = target + newDir;

		File f = new File(dirPath);
		if (!f.getCanonicalPath().startsWith(baseFolder.getCanonicalPath())) {
			throw new IOException("Folders cannot be created outside of the base folder");
		}

		if (f.exists()) {
			return true; // already exists - don't need to do anything...
		}

		if (!f.mkdir()) {
			String msg = "The directory could not be created";

			logger.severe(msg + ": " + dirPath);
			throw new IOException(msg);
		}

		return true;
	}

	public String getTitle(String directory) throws IOException {
		String title = "/";

		if ((directory != null) && (!directory.isEmpty())) {
			File f = new File(directory);

			if (f.getCanonicalPath().startsWith(baseFolder.getCanonicalPath())) {
				title = f.getCanonicalPath().replace(baseFolder.getCanonicalPath(), "");
			}
		}

		return title;
	}

	private Map<String, Object> newList(String key, Object value) {
		Map<String, Object> result = new HashMap<>();

		result.put(key, value);

		return result;
	}

	public byte[] getFileAsByteArray(String entityName) throws Exception {
		File f = new File(entityName);

		if (!f.getCanonicalPath().startsWith(baseFolder.getCanonicalPath())) {
			throw new IOException("Cannot download files outside of the base folder");
		}
		if (!f.exists()) {
			String msg = "The file does not exist";

			logger.severe(msg + ": " + entityName);
			throw new Exception(msg);
		}

		// Download the file
		Path fileLocation = Paths.get(entityName);
		byte[] documentBody = Files.readAllBytes(fileLocation);

		return documentBody;
	}

	private String addTarget(HttpServletRequest request, String entityName) {
		String target = request.getParameter("target");

		if ((target == null) || ("/".equals(target)) ){
			target = "";
		} else {
			target = IgniteUtils.getTerminatedPath(target);
		}

		return target + entityName;
	}

	private boolean rename(String source, String target) {
		File src = new File(source);
		File tgt = new File(IgniteUtils.getTerminatedPath(src.getParent()) + target);

		return src.renameTo(tgt);
	}

	public Object executeAction(String action, String entityName, HttpServletRequest request) throws Exception {
		Object result = null;

		if ("mkdir".equalsIgnoreCase(action)) {
			String dirName = addTarget(request, entityName);

			mkdir(baseFolder.getAbsolutePath(), dirName);

			result = newList("result", "Folder created");
		}
		if ("delete".equalsIgnoreCase(action)) {
			delete(entityName);

			result = newList("result", "File/folder deleted");
		}
		if ("rename".equalsIgnoreCase(action)) {
			String newName = request.getParameter("newName");

			entityName = IgniteUtils.getTerminatedPath(baseFolder.getAbsolutePath()) + entityName;

			if (!rename(entityName, newName)) {
				String msg = "The file could not be renamed";

				logger.severe(msg + ": " + entityName + " -> " + newName);
				throw new Exception(msg);
			}
			result = newList("result", "File renamed");
		}

		if (result == null) {
			logger.severe(action + " is an unsupported file manager action.");
			throw new Exception("Unsupported file manager action");
		}
		return result;
	}

}
