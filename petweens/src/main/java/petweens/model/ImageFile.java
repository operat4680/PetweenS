package petweens.model;

import java.io.File;


public class ImageFile {
	public static final String IMG_TYPE=".png";
	
	private String id;
	private String contentType;
	private int contentLength;
	private File file;
	
	public ImageFile(File file,String contentType) {
		this.contentLength = (int)file.length(); 
		this.contentType = contentType;
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public int getContentLength() {
		return contentLength;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
