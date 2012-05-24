package jCiv.client;

import jCiv.Civ;
import jCiv.Progress;
import jCiv.client.menu.JCivMenu;
import jCiv.client.menu.MainMenu;
import jCiv.map.JCivMap;

public class Game {
	public boolean inMenu;
	public JCivMenu menu;
	public Progress progress;
	public JCivMap map;
	public Civ[] civs;
	public int playerCiv;
	
	public Game() {
		inMenu = true;
		menu = new MainMenu();
	}
	
	public void tick(InputHandler ih) {		
		if(inMenu) {
			menu.tick(ih, this);			
		} else {
			//Do game things :-P 
		}
	}
}
