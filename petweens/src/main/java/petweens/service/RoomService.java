package petweens.service;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import petweens.mapper.RoomMapper;
import petweens.model.RoomInfo;
import petweens.util.FileHandleUtil;
import petweens.util.KeyGenUtil;
import petweens.util.PPTtoImageConverter;

@Service(value = "RoomService")
public class RoomService {
	
	@Resource(name = "roomMapper")
	private RoomMapper roomMapper;
	
	@Value("${file.rootpath}")
	private String rootPath;
	
	public boolean createRoom(RoomInfo info){
		String uniqueId = KeyGenUtil.getUniqueID();
		String fileName = info.getFile().getOriginalFilename();
		info.setFilename(fileName);
		info.setPath(uniqueId);
		if(fileUpload(info)){
			if(generateImage(info)){
				roomMapper.insertRoomInfo(info);
				return true;
			}
		}
		return false;
	}
	
	
	
	private boolean fileUpload(RoomInfo info){
		String uploadPath = rootPath+info.getPath();
		MultipartFile file = info.getFile();
		FileHandleUtil.createDirectory(uploadPath);
		File saveDirectory = new File(uploadPath+"/"+info.getFilename());
        try {
			file.transferTo(saveDirectory);
		}catch (IOException e) {
			System.out.println("fileUpload Fail");
			return false;
		}
        return true;
	}
	private boolean generateImage(RoomInfo info){
		String filename=info.getFilename();
		try {
			int endPage= PPTtoImageConverter.convert(rootPath+info.getPath(), filename);
			if(endPage>0){
				info.setEndpage(endPage);
				return true;
			}
		} catch (IOException e) {
			System.out.println("generate Image Fail");
		}
		return false;
	}
}
