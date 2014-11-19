package petweens.util;
import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordHashTest {
	
	@Test
	public void generateSalt(){
		assertNotNull(PasswordUtil.createBase64Salt());
	}
	@Test
	public void hashTest(){
		String password="asdfasdf";
		String salt = PasswordUtil.createBase64Salt();
		String hash = PasswordUtil.getHash(password, salt);
		String password2="asdfasdf";
		String password3="asdfasdfg";
		assertEquals(hash, PasswordUtil.getHash(password2, salt));
		assertNotEquals(hash, PasswordUtil.getHash(password3, salt));
	}
}
