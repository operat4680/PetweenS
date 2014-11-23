package petweens.controller;



import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import petweens.model.LectureAuth;

@Controller
public class LectureController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping("/hello")
	public ModelAndView getPage(){
		return new ModelAndView("hello");
	}
	
	@MessageMapping("/getkey")
	@SendToUser("/queue/key")
	public String getKey(SimpMessageHeaderAccessor header) throws JsonProcessingException{
		Map<String,Object> session = header.getSessionAttributes();
		LectureAuth auth = new LectureAuth((String)session.get("path"),(String)session.get("auth"));
		return mapper.writeValueAsString(auth);
	}
	
	@MessageMapping("/publish")
	public void publishChange(String message,SimpMessageHeaderAccessor header){
		System.out.println(header.getSessionAttributes().get("userId"));
		System.out.println(message);
		template.convertAndSend("/topic/canvas/"+message, "publish");
	}
	@MessageMapping("/userQuestion")
	public void publishQuestion(String message,SimpMessageHeaderAccessor header){
		Map<String,Object> session = header.getSessionAttributes();
		String path = (String)session.get("path");
		template.convertAndSend("/topic/question/"+path,message);
	}
	
}
