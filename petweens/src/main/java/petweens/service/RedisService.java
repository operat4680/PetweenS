package petweens.service;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.stereotype.Service;

@Service(value = "RedisService")
public class RedisService {
	private final String ROOMCOUNT="count";
	private final String PROFESSOR="professor";
	private final String CURRENTPAGE="current";
	private final String CANVASDATA="canvas";
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	ObjectMapper mapper;
	
	public void increaseRoomCount(String key){
		roomCountChange(key,1);
	}
	public void decreseRoomCount(String key){
		roomCountChange(key,-1);
	}
	public boolean isProfessor(String key){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<String>(String.class));
		String pro = (String)redisTemplate.opsForHash().get(key, PROFESSOR);
		if(pro==null||pro.equals("out")){
			return false;
		}
		return true;
	}
	public void setCanvasData(String key,int page,String data){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<String>(String.class));
		redisTemplate.opsForHash().put(key+":"+CANVASDATA,page,data);
	}
	public String getCanvasDataByPage(String key,int page){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<String>(String.class));
		String data = (String)redisTemplate.opsForHash().get(key+":"+CANVASDATA,page);
		if(data==null)return "";
		return data;
	}
	public String getCanvasDataAll(String key) throws IOException{
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<String>(String.class));
		Map<Object,Object> map = redisTemplate.opsForHash().entries(key+":"+CANVASDATA);
		if(map==null)return "";
		String result = mapper.writeValueAsString(map);
		return result;
	}
	
	
	public void setCurrentPage(String key,int page){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Integer>(Integer.class));
		redisTemplate.opsForHash().put(key, CURRENTPAGE,page);
	}
	public int getCurrentPage(String key){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Integer>(Integer.class));
		Integer page = (Integer)redisTemplate.opsForHash().get(key, CURRENTPAGE);
		if(page==null)return 0;
		return page;
	}
	public void enterProfessor(String key){
		redisTemplate.opsForHash().put(key, PROFESSOR,"enter");
	}
	public void outProFessor(String key){
		redisTemplate.opsForHash().put(key, PROFESSOR,"out");
	}

	public int getRoomCount(String key){
		redisTemplate.setHashValueSerializer(new GenericToStringSerializer<Integer>(Integer.class));
		Integer count = (Integer)redisTemplate.opsForHash().get(key, ROOMCOUNT);
		if(count==null){
			return 0;
		}
		return count;
	}
	
	private void roomCountChange(String key,int i){
		if(i==-1){
			int count=getRoomCount(key);
			if(count<=0)return;
		}
		redisTemplate.setValueSerializer(new GenericToStringSerializer<Integer>(Integer.class));
		redisTemplate.opsForHash().increment(key, ROOMCOUNT, i);
	}
}
