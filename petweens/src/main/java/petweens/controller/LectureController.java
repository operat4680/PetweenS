package petweens.controller;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import petweens.model.LectureAuth;
import petweens.service.RedisService;


@Controller
public class LectureController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	ObjectMapper mapper;
	
	@Resource(name="RedisService")
	RedisService redis;
	
	@RequestMapping("/hello")
	public ModelAndView getPage(){
		return new ModelAndView("hello");
	}
	
	@MessageMapping("/getkey")
	@SendToUser("/queue/key")
	public String getKey(SimpMessageHeaderAccessor header) throws JsonGenerationException, JsonMappingException, IOException{
		Map<String,Object> session = header.getSessionAttributes();
		LectureAuth auth = new LectureAuth((String)session.get("path"),(String)session.get("auth"));
		return mapper.writeValueAsString(auth);
	}
	
	@MessageMapping("/canvasdata")
	public void canvasData(String message,SimpMessageHeaderAccessor header) throws JsonParseException, JsonMappingException, IOException{
		Map<String,Object> session = header.getSessionAttributes();
		String path = (String)session.get("path");
		String auth = (String)session.get("auth");
		TypeReference<HashMap<String,Object>> typeRef 
        = new TypeReference<HashMap<String,Object>>() {};
		Map<String, Object> m = mapper.readValue(message,typeRef);
		if(auth.equals("professor")){
			redis.setCanvasData(path, (Integer)m.get("currentPage"), message);
			redis.setCurrentPage(path, (Integer)m.get("currentPage"));
			
			template.convertAndSend("/topic/canvas/"+path,message);
		}
	}
	@MessageMapping("/userQuestion")
	public void publishQuestion(String message,SimpMessageHeaderAccessor header){
		Map<String,Object> session = header.getSessionAttributes();
		String path = (String)session.get("path");
		template.convertAndSend("/topic/question/"+path,message);
	}
	//TODO
	//template.convertAndSend("/topic/canvas/"+path,redis.getCanvasDataAll(path));
	
}
