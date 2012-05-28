package jCiv.client;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

import jCiv.client.gui.Bitmap;

public class Art {
	
	public static final String bottomBarLocation = "/art/bottomBar.png";
	public static final String fontLocation = "/fonts/roman.ttf";
	
	public static final Bitmap bottomBar = loadArt(bottomBarLocation);
	
	public static final Font romanFont = loadFont(fontLocation);
	
	public static Bitmap loadArt(String location) {
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(location));
			
			int w = img.getWidth();
			int h = img.getHeight();
			
			Bitmap result = new Bitmap(w, h);
			
			img.getRGB(0, 0, w, h, result.pixels, 0, w);
			
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
	}
	
	public static Font loadFont(String location) {
		try {
			File font = new File(Art.class.getResource(location).toURI());
			return Font.createFont(Font.TRUETYPE_FONT, font);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (FontFormatException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
