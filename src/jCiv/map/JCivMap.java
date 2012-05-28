package jCiv.map;

import java.util.HashMap;
import java.util.Vector;

/**
 * The map. MapNodes (territories) are stored in a hashmap, keyed by the id
 * of the nodes. 
 * 
 * @author jdl
 */
public class JCivMap {
	private HashMap<Integer, MapNode> map;
	private Vector<DisasterZone> disasterZones;
	
	public JCivMap(HashMap<Integer, MapNode> nodes, Vector<DisasterZone> disasterZones)
	{
		map = nodes;
		this.disasterZones = disasterZones;
	}
	
	
	public MapNode getNode(int id)
	{
		return map.get(id);
	}
	
	public Vector<DisasterZone> getDisasterZones()
	{
		return disasterZones;
	}
	
	@Override
	public String toString()
	{
		String result = "\nMap:\n";
		for (int i : map.keySet()) {
			result += map.get(i);
		}
		
		return result;
	}
}
