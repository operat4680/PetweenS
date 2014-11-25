package petweens.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import petweens.model.ImageFile;
import petweens.model.ImageView;
import petweens.model.RoomInfo;
import petweens.service.RoomService;
import petweens.util.ValidateUtil;

@Controller
@RequestMapping("/room")
public class RoomController {
	@Resource(name="RoomService")
	private RoomService roomService;
	
	@Resource(name="imageView") 
	private ImageView imageView;
	
	/*
	public ModelAndView generateRoom(@ModelAttribute RoomInfo info){
		String roomOwner="test";
		info.setRoomOwner(roomOwner);
		return new ModelAndView()
	}
	*/
	
	@RequestMapping(value="{id}")
	public ModelAndView enterRoom(@PathVariable String id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/home");
		HttpSession session= request.getSession();
		int userId=(Integer)session.getAttribute("userId");
		if(id!=null&&ValidateUtil.isNumeric(id)){
			int roomId = Integer.parseInt(id);
			RoomInfo info =roomService.getRoomInfoById(roomId);
			if(info!=null){
				if(info.isIspasswd()){
					String password = request.getParameter("password");
					if(password==null||!info.getPassword().equals(password)){
						return mv;
					}
				}
				mv.addObject("info", info);
				session.setAttribute("path", info.getPath());
				if(info.getUserid()==userId){
					mv.addObject("auth","professor");
					session.setAttribute("auth","professor");
				}
				else{
					mv.addObject("auth","student");
					session.setAttribute("auth","student");
				}
				mv.setViewName("slideshow");
			}
		}
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}")
	public ModelAndView deleteRoom(@PathVariable int id,HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		mv.setViewName("redirect:/home");
		roomService.deleteRoom(id,(Integer)session.getAttribute("userId"));
		return mv;
	}
	@RequestMapping(value="/image/{path}/{page}")
	public ImageView getImage(@PathVariable String path,@PathVariable String page,ModelMap modelMap){
			ImageFile imageFile = roomService.getImageFile(path, page);
			modelMap.put("imageFile", imageFile);
			return imageView;
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ModelAndView createRoom(@ModelAttribute RoomInfo info,HttpServletRequest request){
		ModelAndView mv = new ModelAndView("generate");
		HttpSession session = request.getSession();
		info.setUserid((Integer)session.getAttribute("userId"));
		String uniqueKey=roomService.createRoom(info);
		if(uniqueKey.equals("fileError")){
			mv.addObject("fileError", true);
		}
		else if(!uniqueKey.equals("")){
			int id = roomService.getRoomIdBypath(uniqueKey);
			mv.setViewName("redirect:/room/"+id);
		}
		return mv;
	}
	
	//AOP JoinPoint
	@RequestMapping(value="/generate",method=RequestMethod.GET)
	public ModelAndView generateRoom(@ModelAttribute RoomInfo info,HttpServletRequest request){
		return new ModelAndView("generate");
	}
	
	
	
	
	
}
