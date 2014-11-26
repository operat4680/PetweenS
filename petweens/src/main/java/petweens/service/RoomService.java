package petweens.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import petweens.mapper.MemoMapper;
import petweens.mapper.RoomMapper;
import petweens.model.ImageFile;
import petweens.model.MemoData;
import petweens.model.RoomInfo;
import petweens.util.FileHandleUtil;
import petweens.util.PPTtoImageConverter;
import petweens.util.PasswordUtil;

@Service(value = "RoomService")
public class RoomService {
	
	@Value("${file.rootpath}")
	private String IMAGE_DIR;
	
	@Resource(name = "roomMapper")
	private RoomMapper roomMapper;
	
	@Resource(name = "memoMapper")
	private MemoMapper memoMapper;
	
	@Value("${file.rootpath}")
	private String rootPath;
	
	
	public void insertMemo(int roomid,int userid,String memo){
		memoMapper.insertMemo(roomid, userid, memo);
	}
	public String getMemoById(int roomid,int userid){
		String memo = memoMapper.getMemoById(roomid, userid);
		if(memo==null)return "";
		return memo;
	}
	
	public String createRoom(RoomInfo info){
		String uniqueId = PasswordUtil.getUniqueID();
		String fileName = info.getFile().getOriginalFilename();
		if(getExtension(fileName).equals("ppt")||getExtension(fileName).equals("pptx")){
			info.setFilename(fileName);
			info.setPath(uniqueId);
			if(fileUpload(info)){
				if(generateImage(info)){
					roomMapper.insertRoomInfo(info);
					return uniqueId;
				}
			}	
		}
		else{
			return "fileError";
		}
		return "";
	}
	public ImageFile getImageFile(String path,String page){
		File file = new File(IMAGE_DIR+path+"/ppt/"+page+ImageFile.IMG_TYPE);
		ImageFile imageFile = new ImageFile(file,"image/png");
		return imageFile;
		
	}
	
	public int getRoomIdBypath(String path){
		RoomInfo room = roomMapper.getRoomInfoByPath(path);
		return room.getRoomid();
	}
	public RoomInfo getRoomInfoById(int roomId){
		RoomInfo room = roomMapper.getRoomInfoById(roomId);
		return room;
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
	private String getExtension(String fileName) {
		int dotPosition = fileName.lastIndexOf('.');
		
		if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
			return fileName.substring(dotPosition + 1);
		} else {
			return "";
		}
	}
	public List<RoomInfo> getRoomInfoList() {
		List<RoomInfo> list = roomMapper.getRoomInfoList();
		return list;
	}
	public void deleteRoom(int roomid, Integer userid) {
		roomMapper.deleteRoom(roomid,(int)userid);
		
	}
	public List<MemoData> getMemoList(int userid) {
		return memoMapper.getMemoList(userid);
	}
}
