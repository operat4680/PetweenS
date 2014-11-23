package petweens.model;

public class LectureAuth {
	private String key;
	private String auth;
	public LectureAuth(String key,String auth){
		this.key = key;
		this.auth = auth;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getAuth() {
		return auth;
	}
	public void setAuth(String auth) {
		this.auth = auth;
	}
	
}
