package jCiv.client.gui;

import java.awt.Color;

import jCiv.Civ;
import jCiv.Progress;
import jCiv.client.Art;
import jCiv.client.Game;
import jCiv.map.JCivMap;

/**
 * 
 * Main class to handle the rendering of the game.
 * 
 * @author Tehsmash
 *
 */
public class Screen extends Bitmap {
	
	public Screen(int width, int height) {
		super(width, height);
	}
	
	public void render(Game game) {
		if(game.inMenu) {
			game.menu.render(this);
		}
		
		renderMap(game.map);
		renderCivs(game.civs);
		
		fill(Color.BLUE.getRGB());
		
		render(Art.bottomBar, 0, height - 208, 2);
		render("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0, height - 208 - 128, Color.GREEN.getRGB(), 20.0f);
		render("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0, height - 208 - 64, Color.GREEN.getRGB(), 12.0f);
		render("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 0, height - 208 - 8, Color.GREEN.getRGB(), 8.0f);
	}
		
	public void renderMap(JCivMap map) {
		
	}
	
	public void renderCivs(Civ[] civs) {
		
	}
	
	public void renderProgress(Progress progress) {
		
	}
	
	public void renderTradingCards() {
		
	}
	
	public void renderCivCards() {
		
	}
}
