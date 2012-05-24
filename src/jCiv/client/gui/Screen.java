package jCiv.client.gui;

import jCiv.Civ;
import jCiv.Progress;
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
