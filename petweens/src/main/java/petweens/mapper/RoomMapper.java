package petweens.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import petweens.model.RoomInfo;

@Repository(value = "roomMapper")
public interface RoomMapper {
	public void insertRoomInfo(RoomInfo info);
	public List<RoomInfo> getRoomInfo();
	public RoomInfo getRoomInfoByPath(String path);
	public RoomInfo getRoomInfoById(int roomId);
}
