package jCiv.map;

import java.util.ArrayList;

/**
 * User: nathan
 * Date: 24/05/12
 * Time: 13:32
 */
public class Volcano extends DisasterZone{

    public Volcano(ArrayList<MapNode> zones)
    {
        super(zones);
    }
    
    @Override
    public String toString()
    {
    	return "\nVolcano:\n" + super.toString();
    }
}
