package jCiv.client.menu;

import jCiv.client.Game;
import jCiv.client.InputHandler;
import jCiv.client.gui.Screen;

public interface JCivMenu {
	public void tick(InputHandler ih, Game game);
	public void render(Screen screen);
}
