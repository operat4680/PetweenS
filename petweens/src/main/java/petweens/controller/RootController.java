package petweens.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import petweens.model.RoomInfo;
import petweens.service.RoomService;

@Controller
public class RootController {
	@Resource(name="RoomService")
	RoomService roomService;
	
	@RequestMapping(value = {"/home"})
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView("index");
		List<RoomInfo> roomInfoList = roomService.getRoomInfoList();
		mv.addObject("infoList", roomInfoList);
		return mv;
	}
}
