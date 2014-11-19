package petweens.mapper;

import java.util.List;

import javax.annotation.Resource;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import petweens.model.RoomInfo;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/conf/applicationContext.xml","classpath:petweens/test/testMybatisContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class TestRoomMapper {
	@Resource(name = "roomMapper")
	private RoomMapper dao;
	
	@Test
	public void insertRoom(){
		RoomInfo info = new RoomInfo();
		info.setRoomName("들어와");
		info.setUserid(1);
		info.setPasswd(false);
		
		RoomInfo info2 = new RoomInfo();
		info2.setRoomName("들어와");
		info2.setUserid(2);
		info2.setPasswd(true);
		info2.setPassword("1234");

	}
	
}
