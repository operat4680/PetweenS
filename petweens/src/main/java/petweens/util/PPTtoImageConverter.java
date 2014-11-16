package petweens.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;


public class PPTtoImageConverter {
	public static final int SCALE = 1;
	public static final String IMGTYPE="png";
	//original은 전체경로   savePath는 중간 path
	public static int convert(String path,String filename) throws IOException{
		String fullPath = path+"/"+filename;
		String type = checkType(fullPath);
		if(type.equals("ppt")){
			return convertPPT(fullPath,path);
		}
		else if(type.equals("pptx")){
			try {
				return convertPPTX(fullPath,path);
			} catch (FontFormatException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	private static String checkType(String original){
		File f = new File(original);
		String name = f.getName();
		return name.substring(name.lastIndexOf(".")+1,name.length());
	}

	private static int convertPPT(String original, String savePath) throws IOException{
			int endPage = 0;

			InputStream src = new FileInputStream(original);
			SlideShow ppt = new SlideShow(src);
			Slide[] slide = ppt.getSlides();
		

			Dimension pgsize = ppt.getPageSize();
			int width = (int) (pgsize.width * SCALE);
			int height = (int) (pgsize.height * SCALE);
			src.close();
			
			File folder = new File(savePath+"/ppt");
			if(folder.isDirectory())FileHandleUtil.deleteDirectoryALL(folder);
			folder.mkdir();
			
			for(int i=0;i<slide.length;i++){
				BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D graphics = img.createGraphics();
				// default rendering options
				graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
	
				//clear the drawing area
				graphics.setColor(Color.white);
				graphics.clearRect(0, 0, width, height);
				graphics.scale(SCALE, SCALE);
	
				//render
				slide[i].draw(graphics);
		
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePath+"/ppt/"+(i+1)+"."+IMGTYPE)));
				boolean isSuccess = ImageIO.write(img, IMGTYPE, bos);		
				bos.close();
				if(!isSuccess)return 0;
				endPage++;
			}
			
			return endPage;
	}
	private static int convertPPTX(String original, String savePath) throws IOException, FontFormatException{
		int endPage = 0;

		InputStream src = new FileInputStream(original);
		XMLSlideShow ppt = new XMLSlideShow(src);
		XSLFSlide[] slide = ppt.getSlides();

		Dimension pgsize = ppt.getPageSize();
		int width = (int) (pgsize.width * SCALE);
		int height = (int) (pgsize.height * SCALE);
		src.close();
		
		File folder = new File(savePath+"/ppt");
		if(folder.isDirectory())FileHandleUtil.deleteDirectoryALL(folder);
		folder.mkdir();
		
		/*
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        InputStream is = new FileInputStream("src/main/resources/font/malgunbd.ttf");
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        is.close();
        ge.registerFont(font);  
        */

		
		for(int i=0;i<slide.length;i++){
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = img.createGraphics();
			// default rendering options
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

			//clear the drawing area
			graphics.setColor(Color.white);
			graphics.clearRect(0, 0, width, height);
			graphics.scale(SCALE, SCALE);

			//render
			slide[i].draw(graphics);
	
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePath+"/ppt/"+(i+1)+"."+IMGTYPE)));
			boolean isSuccess = ImageIO.write(img, IMGTYPE, bos);		
			bos.close();
			if(!isSuccess)return 0;
			endPage++;
		}
		
		return endPage;
}
		


	
	
}
