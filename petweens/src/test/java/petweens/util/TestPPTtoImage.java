package petweens.util;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


public class TestPPTtoImage {
	@Value("${file.rootpath}")
	private String path;
	//"D:/lunaWorkspace/petweens/testppt/"
	public static final String filename = "port.pptx";
	
	@Test
	public void trensferPPTX() throws IOException{
		assertNotSame(PPTtoImageConverter.convert("D:/petweens/",filename), 0);
	}
	/*
	@Test
	public void trensferPPT() throws IOException{
		assertTrue(PPTtoImageConverter.convert(path+filename, path));
	}
	*/
	
}
