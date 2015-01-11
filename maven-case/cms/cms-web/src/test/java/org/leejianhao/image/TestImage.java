package org.leejianhao.image;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.junit.Test;

public class TestImage {
	String original = "D:/tools/personal/portrait/portraitfanmian.jpg";
	String now = "D:/tools/personal/portrait/portraitfanmian1.jpg";
	
	String path1 = "C:/Users/ljh/Desktop/banner.jpg";
	String path2 = "C:/Users/ljh/Desktop/banner_small.jpg";
	@Test
	public void test01() {
		/*try{
			Thumbnails.of(original)  
			.size(160, 160)
		    .outputFormat("jpg")  
		    .toFile(now);  
		}catch(IOException e) {
			e.printStackTrace();
		}*/
		try {
			Thumbnails.of(path1)
			.scale(0.5)
			.outputQuality(0.2)
			.outputFormat("jpg")
			.toFile(path2);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02() {
		try {
			Thumbnails.of(original)  
			.scale(1f)  
			.outputQuality(0.25f)  
			.outputFormat("jpg")  
			.toFile(now);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	@Test
	public void test03() {
		try {
			BufferedImage bi = Thumbnails.of(original)  
			.scale(2f)  
			.asBufferedImage();
			ImageIO.write(bi,"jpg" , new FileOutputStream(now));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	@Test
	public void test04() {
		try  {
			BufferedImage bi =Thumbnails.of(path1)  
			.scale(0.5)
			.sourceRegion(Positions.CENTER,200,200)
			.asBufferedImage();
			ImageIO.write(bi,"jpg" , new FileOutputStream(path2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	@Test
	public void test05() {
		try  {
			BufferedImage bi = Thumbnails.of(new FileInputStream(now)).scale(1.0).asBufferedImage();
			System.out.println(bi.getWidth()+" "+bi.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
