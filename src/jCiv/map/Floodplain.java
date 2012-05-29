package jCiv.map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:27
 */
public class Floodplain extends DisasterZone{

    public Floodplain(ArrayList<MapNode> zones, HashMap<MapNode, Boolean> affectedCities)
    {
        super(zones, affectedCities);
    }
    
    @Override
    public String toString()
    {
    	return "\nFlood plain:\n" + super.toString();
    }

}
