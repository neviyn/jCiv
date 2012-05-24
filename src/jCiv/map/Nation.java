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
	private int id;
	private int[] startLocs;
	
	public Nation(int id, String name, int[] startLocs) 
	{
		this.name = name;
		this.id = id;
		this.startLocs = startLocs;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getId()
	{
		return id;
	}
	
	/**
	 * Get the start location for the given number of players. 
	 * @param noPlayers the number of players
	 * @return the NodeNum for the start location
	 */
	public int getStartLocation(int noPlayers)
	{
		return startLocs[noPlayers];
	}	
	
	@Override
	public String toString()
	{
		String result = "";
		result += id;
		result += ": " + name;
		result += "\n";
		for (int i=0; i<7; i++) {
			result += i + ": " + startLocs[i];
		}
		return result;
	}
}