package jCiv.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import jCiv.client.Art;

public class Bitmap {
	public int[] pixels;
	public int width;
	public int height;

	public int fontSize = 16;
	public String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ01234.:";

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	/**
	 * 
	 * Draw another bitmap onto this bitmap!
	 * 
	 * @param bitmap
	 *            the image to draw onto this bitmap
	 * @param x
	 *            the x location of the image on this bitmap
	 * @param y
	 *            the y location of the image on this bitmap
	 */
	public void render(Bitmap bitmap, int x, int y) {
		for (int i = 0; i < bitmap.height; i++) {
			int ny = y + i;
			if (ny < height) {
				for (int j = 0; j < bitmap.width; j++) {
					int nx = x + j;
					if (nx < width) {
						pixels[nx + (ny * width)] = bitmap.pixels[j
								+ (i * bitmap.width)];
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Draw another bitmap onto this bitmap with scale!
	 * 
	 * @param bitmap
	 *            the image to draw onto this bitmap
	 * @param x
	 *            the x location of the image on this bitmap
	 * @param y
	 *            the y location of the image on this bitmap
	 * @param scale 
	 * 			  the scale relative to the original texture
	 */
	public void render(Bitmap bitmap, int x, int y, int scale) {
		for (int i = 0; i < bitmap.height*scale; i++) {
			int ny = y + i;
			if (ny < height) {
				for (int j = 0; j < bitmap.width*scale; j++) {
					int nx = x + j;
					if (nx < width) {
						pixels[nx + (ny * width)] = bitmap.pixels[(j/scale)
								+ ((i/scale) * bitmap.width)];
					}
				}
			}
		}
	}

	/**
	 * Perform a block color fill on this bitmap.
	 * 
	 * @param color
	 *            the color to fill the area with.
	 */
	public void fill(int color) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels[j + (i * width)] = color;
			}
		}
	}

	/**
	 * Draw a string to a specific location on the bitmap.
	 * 
	 * @param string
	 *            The string to print
	 * @param x
	 *            The X location of where to print the string
	 * @param y
	 *            The Y location of where to print the string
	 * @param color
	 *            The color you want the string to appear.
	 */
	public void render(String string, int x, int y, int color) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, width, height, pixels, 0, width);
		Graphics g = img.getGraphics();
		g.setColor(Color.decode(""+color));
		g.setFont(Art.romanFont.deriveFont(20.0f));
		g.drawChars(string.toCharArray(), 0, string.length(), x, y+20);
		img.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	public void render(String string, int x, int y, int color, float size) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		img.setRGB(0, 0, width, height, pixels, 0, width);
		Graphics g = img.getGraphics();
		g.setColor(Color.decode(""+color));
		g.setFont(Art.romanFont.deriveFont(size));
		g.drawChars(string.toCharArray(), 0, string.length(), x, (int)(y+size));
		img.getRGB(0, 0, width, height, pixels, 0, width);
	}
}
