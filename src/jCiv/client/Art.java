package jCiv.client;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jCiv.client.gui.Bitmap;

public class Art {
	
	public static final String fontLocation = "/art/font.png";
	public static final String bottomBarLocation = "/art/bottomBar.png";
	
	public static final Bitmap font = loadArt(fontLocation);
	public static final Bitmap bottomBar = loadArt(bottomBarLocation);
	
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
}
