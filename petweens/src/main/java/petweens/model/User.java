package petweens.model;

public class User {
	private int userid;
	private String email;
	private String username;
	private String password;
	private String salt;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public boolean resistValidate(){
		return (username!=null&&password!=null&& username.length()>=2 &&password.length()>=7) ? true : false;
	}
	public boolean loginValidate(){
		return (email!=null&&password!=null) ? true : false;
	}
}
