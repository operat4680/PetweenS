package petweens.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import petweens.service.RedisService;

public class PresenceEventListener implements ApplicationListener<ApplicationEvent>{
	@Autowired
	SimpMessagingTemplate template;
	
	@Resource(name="RedisService")
	RedisService service;
	public void onApplicationEvent(ApplicationEvent event) {
		// TODO Auto-generated method stub
		if(event instanceof SessionDisconnectEvent) {
			handleSessionDisconnect((SessionDisconnectEvent) event);
		}
		else if(event instanceof SessionConnectEvent){
			handleSessionConnect((SessionConnectEvent) event);
		}
	}

	private void handleSessionConnect(SessionConnectEvent event) {
		SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
		handle(header,"in");
	}
	private void handleSessionDisconnect(SessionDisconnectEvent event) {
		SimpMessageHeaderAccessor header = SimpMessageHeaderAccessor.wrap(event.getMessage());
		handle(header,"out");
	}
	private void handle(SimpMessageHeaderAccessor header,String text){	
		String auth = (String)header.getSessionAttributes().get("auth");
		String path = (String)header.getSessionAttributes().get("path");
		if(auth.equals("professor")){
			if(text.equals("in"))service.enterProfessor(path);
			else service.outProFessor(path);
			template.convertAndSend("/topic/enter/"+path,text);
		}
		else{
			if(text.equals("in"))service.increaseRoomCount(path);
			else service.decreseRoomCount(path);
		}
	}
	
	
}