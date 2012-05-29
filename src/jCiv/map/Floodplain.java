package jCiv.map;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:27
 */
public class Floodplain extends DisasterZone{

    private final HashMap<MapNode, Boolean> cityAffected;

    public Floodplain(ArrayList<MapNode> zones, HashMap<MapNode, Boolean> affectedCities)
    {
        super(zones);
        cityAffected = affectedCities;
    }

    /**
     * @param nodeID the id of the node to be checked
     * @return true iff the given node is within this disaster zone, the node contains a
     * 		   city site, and the city site is affected by the disaster.
     */
    @Override
    public boolean cityIsAffected(int nodeID)
    {
        boolean affected = false;
        for (MapNode n : getZones()) {
            if (n.nodeNum == nodeID && cityAffected.get(n)) {
                affected = true;
            }
        }
        return affected;
    }

    @Override
    public String toString()
    {
        String result = "\tAffected nodes:";
        for (MapNode n : getZones()) {
            result += "\n\t\tID:" + n.nodeNum;
            if (cityAffected.get(n)) {
                result += " (city is affected)";
            }
        }

        return result;
    }

}
