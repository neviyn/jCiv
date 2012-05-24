package jCiv.client;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
	public boolean[] keys = new boolean[65536];
	public int[] mousePosition = new int[2];
	public boolean[] mouseButtons = new boolean[3];
	
	public void mouseDragged(MouseEvent e) {
		mousePosition[0] = e.getX();
		mousePosition[1] = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mousePosition[0] = e.getX();
		mousePosition[1] = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent e) {
		mouseButtons[e.getButton() - 1] = true;
	}

	public void mouseReleased(MouseEvent e) {
		mouseButtons[e.getButton() - 1] = false;
	}

	public void focusGained(FocusEvent arg0) {
	}

	public void focusLost(FocusEvent arg0) {
		for (int i=0; i<keys.length; i++) {
			keys[i] = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode(); 
		if (code>0 && code<keys.length) {
			keys[code] = false;
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}
}
