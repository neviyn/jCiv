package jCiv;

import java.util.ArrayList;

import jCiv.map.Nation;

public class Civ {
	Nation nation;
	CivCard[] civCards;
	ArrayList<TradingCard> tradingCards;
	int tresure;
	int stock;
	
	public Civ(Nation nation) {
		this.nation = nation;
		civCards = new CivCard[11];		
		tradingCards = new ArrayList<TradingCard>();
		tresure = 0;
		stock = 55;
	}
}
