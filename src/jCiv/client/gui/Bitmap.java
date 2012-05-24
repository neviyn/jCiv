package jCiv.client.gui;

public class Bitmap {
	public int[] pixels;
	public int width;
	public int height;
	
	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	/**
	 * 
	 * Draw another bitmap onto this bitmap! 
	 * 
	 * @param bitmap the image to draw onto this bitmap
	 * @param x the x location of the image on this bitmap
	 * @param y the y location of the image on this bitmap
	 */
	public void render(Bitmap bitmap, int x, int y) {
		for(int i = 0; i < bitmap.height; i++) {
			int ny = y + i;
			if(ny < height) {
				for(int j = 0; j < bitmap.width; j++) {
					int nx = x + j;
					if(nx < width) {
						pixels[nx + (ny * width)] = bitmap.pixels[j + (i * width)]; 
					}
				}
			}
		}
	}
}
