package petweens.model;

import org.springframework.web.multipart.MultipartFile;

public class RoomInfo {
	private String roomname;
	private String roomowner;
	private boolean ispasswd;
	private String password;
	private String path;
	private String filename;
	private int endpage;
	private MultipartFile file;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public String getRoomOwner() {
		return roomowner;
	}
	public void setRoomOwner(String roomowner) {
		this.roomowner = roomowner;
	}
	public boolean isIspasswd() {
		return ispasswd;
	}
	public void setIspasswd(boolean ispasswd) {
		this.ispasswd = ispasswd;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getRoomName() {
		return roomname;
	}
	public void setRoomName(String roomName) {
		this.roomname = roomName;
	}
	public boolean isPasswd() {
		return ispasswd;
	}
	public void setPasswd(boolean isPasswd) {
		this.ispasswd = isPasswd;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public int getEndpage() {
		return endpage;
	}
	public void setEndpage(int endpage) {
		this.endpage = endpage;
	}
	
	
}
