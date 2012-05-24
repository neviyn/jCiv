package jCiv.map;

import java.util.ArrayList;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:42
 */
abstract class DisasterZone {
    private ArrayList<MapNode> zones;

    public DisasterZone(ArrayList<MapNode> zones)
    {
        this.zones = zones;
    }

    /**
     * @return List of zones within this disaster area.
     */
    public ArrayList<MapNode> getZones()
    {
        return zones;
    }

    /**
     * @param playerNum The number representing the player we are checking for tokens of.
     * @return Does this area contain units belonging to the parameter player.
     */
    public boolean containsPlayer(int playerNum)
    {
        boolean found = false;
        for(MapNode node:zones)
        {
            // TODO: Add code for checking if a map node contains tokens of a certain player.
        }
        return found;
    }
    
    /**
     * Check whether this zone contains a given mapNode
     * @param nodeID the id of the mapnode to be checked
     * @return true if the zone contains the node
     */
    public boolean containsNode(int nodeID)
    {
    	boolean found = false;
    	for (MapNode n : zones) {
    		if (n.nodeNum == nodeID) {
    			found = true;
    		}
    	}
    	return found;
    }
}
