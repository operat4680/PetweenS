package petweens.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LectureController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@RequestMapping("/hello")
	public ModelAndView getPage(){
		return new ModelAndView("hello");
	}
	
	
	@MessageMapping("/getkey")
	@SendToUser("/queue/key")
	public String test(String message){
		return message+"in";
	}
	
	@MessageMapping("/publish")
	public void publishChange(String message){
		template.convertAndSend("/topic/"+message, "publish");
	}
	
	
}
