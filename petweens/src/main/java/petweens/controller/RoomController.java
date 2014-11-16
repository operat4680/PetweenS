package petweens.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import petweens.model.RoomInfo;
import petweens.service.RoomService;

@Controller
@RequestMapping("/room")
public class RoomController {
	@Resource(name="RoomService")
	private RoomService roomService;
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public @ResponseBody String generateRoom(@ModelAttribute RoomInfo info){
		String roomOwner="test";
		info.setRoomOwner(roomOwner);
		return roomService.createRoom(info)+"";
	}
	
	
	
	
	
}
