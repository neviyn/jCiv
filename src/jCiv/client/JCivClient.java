package jCiv.client;

import jCiv.client.gui.Screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

/**
 * 
 * Main Client Program and game loop.
 * 
 * @author Tehsmash
 *
 */
public class JCivClient extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	boolean running;	
	Thread thread;
	int WIDTH;
	int HEIGHT;
	Game game;
	Screen screen;
	InputHandler ih; 
	
	private BufferedImage img;
	private int[] pixels;
	
	/**
	 * 
	 * The constructor that creates the game and sets the default
	 * width and height.
	 *  
	 * @param width The width the game will start at.
	 * @param height The height the game will start at.
	 */
	public JCivClient(int width, int height) {
		running = false;
		WIDTH = width;
		HEIGHT = height;
		
		setSize(new Dimension(WIDTH, HEIGHT));
		setMinimumSize(new Dimension(WIDTH, HEIGHT));
		
		ih = new InputHandler();
		
		game = new Game();
		screen = new Screen(WIDTH, HEIGHT);
		
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public void run() {
		while(running) {
			tick();
			render();
		}
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		screen.render("jciv Version: 0.0000001", 0, 0, Color.yellow.getRGB());	
		
		for (int i = 0; i < WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();		
	}

	private void tick() {
		game.tick(ih);
	}

	public void start() {
		if(!running){
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void stop() {
		if(running) {
			running = !running;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("JCiv Board Game Client");
		JCivClient client = new JCivClient(800, 600);		
		
		frame.add(client);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		client.start();
	}
}
