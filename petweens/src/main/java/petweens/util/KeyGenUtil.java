package petweens.util;

import java.util.UUID;

public class KeyGenUtil {
	public static String getUniqueID(){
		UUID id = UUID.randomUUID();
		return id.toString();
	}
}
