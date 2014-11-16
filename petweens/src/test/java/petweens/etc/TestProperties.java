package petweens.etc;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

import org.junit.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/conf/applicationContext.xml","classpath:petweens/test/testMybatisContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class TestProperties {
	@Value("${file.rootpath}")
	String path;
	
	@Test
	public void getPath(){
		assertEquals(path, "D:/petweens");
		
	}
}
