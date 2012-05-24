package jCiv.map;

import java.util.HashMap;

public class JCivMap {
	private HashMap<Integer, MapNode> map;
	
	public JCivMap(Iterable<MapNode> nodes)
	{
		for (MapNode n : nodes) {
			map.put(n.nodeNum, n);
		}
	}
	
	
	public MapNode getNode(int id)
	{
		return map.get(id);
	}
}
