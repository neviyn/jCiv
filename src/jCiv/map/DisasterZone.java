package jCiv.map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:42
 */
public abstract class DisasterZone {
    private final ArrayList<MapNode> zones;
    private final HashMap<MapNode, Boolean> cityAffected;

    /**
     * 
     * @param zones
     * @param cityAffected each value should be true if the node has a city site which is affected 
     * 	      by the disaster, and false otherwise (in practice this will only affect flood plains)
     */
    public DisasterZone(ArrayList<MapNode> zones, HashMap<MapNode, Boolean> cityAffected)
    {
        this.zones = zones;
        this.cityAffected = cityAffected;
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
     * @param nodeID the id of the node to be checked
     * @return true iff the given node is within this disaster zone, the node contains a
     * 		   city site, and the city site is affected by the disaster.
     */
    public boolean cityIsAffected(int nodeID)
    {
    	boolean affected = false;
    	for (MapNode n : zones) {
    		if (n.nodeNum == nodeID && cityAffected.get(n)) {
    			affected = true;
    		}
    	}
    	return affected;
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
    
    @Override
    public String toString()
    {
    	String result = "\tAffected nodes:";
    	for (MapNode n : zones) {
    		result += "\n\t\tID:" + n.nodeNum;
    		if (cityAffected.get(n)) {
    			result += " (city is affected)";
    		}
    	}
    	
    	return result;
    }
}
