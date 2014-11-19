package petweens.util;

import java.util.regex.Pattern;

public class ValidateUtil {
	public static boolean isNumeric(String s) { 
	    java.util.regex.Pattern pattern = Pattern.compile("[+-]?\\d+"); 
	    return pattern.matcher(s).matches(); 
	}


}
