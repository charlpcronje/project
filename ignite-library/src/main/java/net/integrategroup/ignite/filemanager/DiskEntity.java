package net.integrategroup.ignite.filemanager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DiskEntity {

	private Logger logger = Logger.getLogger(DiskEntity.class.getName());

	private String id;
	private String name;
	private Date creationDate;
	private Long longCreationDate;
	private boolean isDirectory;
	private Long size = null;
	private String mimeType;

	private String baseDirectory;

	public DiskEntity() {
		// nothing
	}

	public DiskEntity(File baseDirectory, File file) {
		this(baseDirectory.getAbsolutePath(), file);
	}

	public DiskEntity(String baseDirectory, File file) {
		this();

		setBaseDirectory(baseDirectory);

		setId(file.getPath());
		setName(file.getName());
		setCreationDate(new Date(file.lastModified()));
		setDirectory(file.isDirectory());

		if (file.isDirectory()) {
			setMimeType("Directory");
		} else {
			setSize(file.length());

			try {
				setMimeType(Files.probeContentType(file.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public DiskEntity(File fileBaseDirectory, String id, String name, Long size, String mimeType, boolean isDirectory) {
		this(fileBaseDirectory.getAbsolutePath(), id, name, size, mimeType, isDirectory);
	}

	public DiskEntity(String baseDirectory, String id, String name, Long size, String mimeType, boolean isDirectory) {
		setBaseDirectory(baseDirectory);

		setId(id);

		this.name = name;
		this.size = size;
		this.isDirectory = isDirectory;
		this.mimeType = mimeType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;

		if (creationDate == null) {
			this.longCreationDate = null;
		} else {
			this.longCreationDate = creationDate.getTime();
		}
	}

	public String getId() {
		return id;
	}

	private void setBaseDirectory(String directory) {
		if (directory != null) {
			directory = directory.replace("\\", "/");
		}

		this.baseDirectory = directory;
	}

	public void setId(String id) {
		if (id != null) {
			id = id.replace("\\", "/");
			if (id.endsWith("/")) {
				id = id.substring(0, id.length() - 1);
			}

			if (id.startsWith(baseDirectory)) {
				if (baseDirectory.equals(id)) {
					id = "/";
				} else {
					id = id.substring(baseDirectory.length() + 1);
				}
			}
		}

		this.id = id;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Long getLongCreationDate() {
		return longCreationDate;
	}

	public void setLongCreationDate(Long longCreationDate) {
		this.longCreationDate = longCreationDate;
	}

	@JsonProperty("fileType")
	public String getFileType() {
		String fileType = "";

		try {
			if (name == null) {
				return fileType;
			}

			if (isDirectory()) {
				return "Directory";
			}

			Optional<String> o = Optional.ofNullable(name)
					.filter(f -> f.contains("."))
					.map(f -> f.substring(name.lastIndexOf(".") + 1));
			String ext = o.get();

			if ((ext == null) || (ext.isEmpty())) {
				return fileType;
			}

			ext = ext.toLowerCase();

			// Windows displays ext + " File" for unknown files - should we do that or rather just show an empty area?

			if ("txt".equals(ext)) { fileType = "Text Document"; }
			if ("pdf".equals(ext)) { fileType = "PDF Document"; }

			if ("gif".equals(ext)) { fileType = "Image"; }
			if (("jpg".equals(ext)) || ("jpeg".equals(ext))) { fileType = "Image"; }

			if ("csv".equals(ext)) { fileType = "CSV File"; }

			if (("doc".equals(ext)) || ("docx".equals(ext))) { fileType = "Word Document"; }
			if (("ppt".equals(ext)) || ("pptx".equals(ext))) { fileType = "Powerpoint Document"; }
			if (("xls".equals(ext)) || ("xlsx".equals(ext))) { fileType = "Excel Document"; }

			if (("7z".equals(ext)) || ("zip".equals(ext))) { fileType = "Compressed File"; }

		} catch (Exception e) {
			logger.info("Cannot determine file type: " + e.getMessage());
		}

		return fileType;
	}
}
