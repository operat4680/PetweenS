package petweens.util;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/conf/applicationContext.xml","classpath:petweens/test/testMybatisContext.xml","file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
public class TestPPTtoImage {
	@Value("${file.rootpath}")
	private String path;
	//"D:/lunaWorkspace/petweens/testppt/"
	public static final String filename = "port.pptx";
	
	@Test
	public void pathExist(){
		assertEquals(path+filename,"D:/petweens/port.pptx");
	}
	@Test
	public void existFile(){
		File f = new File(path+filename);
		assertTrue(f.isFile());
	}
	@Test
	public void trensferPPTX() throws IOException{
		assertNotSame(PPTtoImageConverter.convert(path,filename), 0);
	}
	/*
	@Test
	public void trensferPPT() throws IOException{
		assertTrue(PPTtoImageConverter.convert(path+filename, path));
	}
	*/
	
}
