package petweens.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class PasswordUtil {
	public static final int SALT_BYTE_SIZE = 24;

	public static String getUniqueID() {
		UUID id = UUID.randomUUID();
		return id.toString();
	}
	public static String getHash(String password,String salt){
		String result=null;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(Base64.decodeBase64(salt));
			byte[] input = digest.digest(password.getBytes("UTF-8"));
			result = Base64.encodeBase64String(input);
		} catch (NoSuchAlgorithmException e) {} 
		  catch (UnsupportedEncodingException e) {}
		return result;
	}
	public static String createBase64Salt(){
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return Base64.encodeBase64String(salt);
	}

}
