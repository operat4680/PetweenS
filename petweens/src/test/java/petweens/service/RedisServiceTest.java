package petweens.service;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/conf/applicationContext.xml","classpath:petweens/test/testMybatisContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class RedisServiceTest {
	
	@Resource(name="RedisService")
	RedisService redis;
	
	@Test
	public void RoominsertTest(){
		//redis.increaseRoomCount("test");
		redis.decreseRoomCount("test");
	}
	
	@Test
	public void getRoomCount(){
		System.out.println(redis.getRoomCount("test"));
	}
	@Test
	public void insertPro(){
		redis.enterProfessor("test");
	}
	@Test
	public void proIn(){
		assertTrue(redis.isProfessor("test"));
	}
	@Test
	public void proOut(){
		assertTrue(!redis.isProfessor("test"));
	}
	@Test
	public void outPro(){
	//	redis.outProFessor("test");
	}
	@Test
	public void canvasData() throws IOException{
		redis.setCanvasData("test", 1, "{data:hi}");
		redis.setCanvasData("test", 3, "{data:hi2}");
		System.out.println(redis.getCanvasDataAll("test"));
	}


}
