package jCiv.map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:32
 */
public class Volcano extends DisasterZone{

    public Volcano(ArrayList<MapNode> zones)
    {
        super(zones, allCitiesAreAffected(zones));
    }
    
    private static HashMap<MapNode, Boolean> allCitiesAreAffected(ArrayList<MapNode> zones)
    {
    	HashMap<MapNode, Boolean> result = new HashMap<MapNode, Boolean>();
    	for (MapNode m : zones) {
    		result.put(m, m.isCityPoint());
    	}
    	return result;
    }
}
