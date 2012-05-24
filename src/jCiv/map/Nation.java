package jCiv.map;


/**
 * The playable nations.
 * 
 * Each nation has a name and a default starting location for each number of players. 
 * 
 * @author jdl and Tehsmash
 *
 */
public final class Nation {
	private String name;
	private MapNode[] startLocs;
	
	public static final int SEVEN_PLAYER_GAME = 0; 
	public static final int FIVE_PLAYER_GAME = 1;
	public static final int THREE_PLAYER_GAME = 2;
	
	public Nation(String name, MapNode[] startLocs) 
	{
		this.name = name;
		this.startLocs = startLocs;
	}
	
	public String getName()
	{
		return name;
	}
	
	public MapNode getStartLocation(int noPlayers)
	{
		return startLocs[noPlayers];
	}	
}
