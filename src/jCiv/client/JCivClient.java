package jCiv.client;

import jCiv.client.gui.Screen;

import java.awt.Canvas;
import java.awt.Dimension;

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
	}
	
	public void run() {
		while(running) {
			tick();
			render();
		}
	}
	
	private void render() {
		screen.render(game);		
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
