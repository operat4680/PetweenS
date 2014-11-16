package petweens.util;

import java.io.File;

public class FileHandleUtil {
	public static boolean deleteDirectoryALL(File path) {
        if(!path.exists()) {
            return false;
        }
         
        File[] files = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectoryALL(file);
            } else {
                file.delete();
            }
        }
         
        return path.delete();
    }
	public static void createDirectory(String path){
		File file = new File(path);
		if(file.isDirectory()){
			deleteDirectoryALL(file);
		}
		file.mkdirs();
		
	}
}
