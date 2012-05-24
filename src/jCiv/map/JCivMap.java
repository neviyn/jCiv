package jCiv.map;

import java.util.HashMap;
import java.util.Vector;
import java.util.List;

/**
 * The map. MapNodes (territories) are stored in a hashmap, keyed by the id
 * of the nodes. 
 * 
 * @author jdl
 */
public class JCivMap {
	private HashMap<Integer, MapNode> map;
	private Vector<DisasterZone> disasterZones;
	
	public JCivMap(Iterable<MapNode> nodes, Vector<DisasterZone> disasterZones)
	{
		for (MapNode n : nodes) {
			map.put(n.nodeNum, n);
		}
		this.disasterZones = disasterZones;
	}
	
	
	public MapNode getNode(int id)
	{
		return map.get(id);
	}
	
	public Vector<DisasterZone> getDisasterZonesByNodeID(int nodeID)
	{
		Vector<DisasterZone> result = new Vector<DisasterZone>();
		for (DisasterZone d : disasterZones) {
			if (d.containsNode(nodeID)) {
					result.add(d);
			}
		}
		return result;
	}
}
