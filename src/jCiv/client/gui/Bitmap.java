package jCiv.client.gui;

import java.awt.Color;

import jCiv.client.Art;

public class Bitmap {
	public int[] pixels;
	public int width;
	public int height;

	public int fontSize = 16;
	public String chars = "ABCDEFGHIJKLMNOPQ";

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

	public void fill(int color) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels[j + (i * width)] = color;
			}
		}
	}

	public void render(String string, int x, int y, int color) {
		for (int c = 0; c < string.length(); c++) {
			int index = chars.indexOf(string.charAt(c));
			if (index != -1) {
				for (int i = 0; i < fontSize; i++) {
					int ny = y + i;
					if (ny < height) {
						for (int j = 0; j < fontSize; j++) {
							int nx = x + j + ((fontSize+2) * c);
							if (nx < width) {
								int srcX = ((index * fontSize) % Art.font.width) + j;
								int srcY = (((index * fontSize) / Art.font.width)
										* fontSize) + i;
								
								int srcPixel = Art.font.pixels[srcX + (srcY
										* Art.font.width)];
								if(srcPixel != Color.MAGENTA.getRGB()) {
									pixels[nx + (ny * width)] = color;
								}
							}
						}
					}
				}
			}
		}
	}
}
