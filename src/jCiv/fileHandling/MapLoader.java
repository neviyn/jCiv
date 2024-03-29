package jCiv.fileHandling;

import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;

import org.w3c.dom.*;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import jCiv.map.*;

/**
 * loads a map from an xml file, parses it, and creates a map object.
 * 
 * @author jdl
 *
 */
public class MapLoader extends XMLLoader {

	private JCivMap map;
	private HashMap<Integer, MapNode> mapNodes;
	private Vector<DisasterZone> disasterZones;

	/**
	 * Load and parse a nations XML file.
	 * 
	 * @param filename the file name of the XML document to be loaded and parsed.
	 * @throws XMLParseException 
	 */
	public MapLoader(String filename) 
		throws IOException, SAXException, ParserConfigurationException, XMLParseException
	{
			mapNodes = new HashMap<Integer, MapNode>();
			disasterZones = new Vector<DisasterZone>();
			loadDocument(filename);
	}
	
	public JCivMap getMap()
	{
		return map;
	}
	
	@Override
	protected void parse() 
		throws MapXMLParseException 
	{
		NodeList mapNodesNodeList = doc.getElementsByTagName("mapNode");
		HashMap<MapNode, NeighbourList> neighboursLists = new HashMap<MapNode, NeighbourList>();
		
		for (int i=0; i<mapNodesNodeList.getLength(); i++) {
			Element mapNodeElem = (Element) mapNodesNodeList.item(i);
			
			// get the basics on this node
			int id = Integer.parseInt(mapNodeElem.getAttribute("id"));
			boolean ocean = Boolean.valueOf(mapNodeElem.getElementsByTagName("ocean").item(0).getChildNodes().item(0).getNodeValue());
			boolean coast = Boolean.valueOf(mapNodeElem.getElementsByTagName("coast").item(0).getChildNodes().item(0).getNodeValue());
			int popLimit = Integer.parseInt(mapNodeElem.getElementsByTagName("popLimit").item(0).getChildNodes().item(0).getNodeValue());
			
			// find whether this node is a city site
			boolean isCitySite = false;
			String cityName = null;
			NodeList citySiteNodeList = mapNodeElem.getElementsByTagName("citySite");  
			if (citySiteNodeList.getLength() != 0) {
				isCitySite = true;
				cityName = citySiteNodeList.item(0).getChildNodes().item(0).getNodeValue();
			}
			
			// get the neighbouring nodes' indeces
			NodeList nodeLinksList = mapNodeElem.getElementsByTagName("nodeLinks");
			int numLinkTypes = nodeLinksList.getLength();
			NeighbourList neighbours = new NeighbourList(); 
			for (int j=0; j<numLinkTypes; j++) {
				Element nodeLinksElem = (Element) nodeLinksList.item(j);
				
				int type;
				switch (nodeLinksElem.getAttribute("type")) {
				case ("land"):
					type = NodeLink.LAND_LINK;
					break;
				case ("sea"):
					type = NodeLink.SEA_LINK;
					break;
				default :
					type = NodeLink.LAND_LINK;
				}
				NodeList links = nodeLinksElem.getElementsByTagName("link");
				for (int k=0; k<links.getLength(); k++) {
					int linkTarget = Integer.parseInt(links.item(k).getChildNodes().item(0).getNodeValue());
					neighbours.addNeighbour(type, linkTarget);
				}
			}
			
			// build the MapNode object, and add the neighbours to the pot
			MapNode mapNode = new MapNode(id, ocean, coast, popLimit, isCitySite);
			mapNodes.put(id, mapNode);
			neighboursLists.put(mapNode, neighbours);
			
		}
		
		// add the neighbours to each MapNode
		for (int i : mapNodes.keySet()) {
			MapNode mn = mapNodes.get(i);
			for (Neighbour n : neighboursLists.get(mn).getAllNeighbours()) {
				mn.addNeighbour(n.type, mapNodes.get(n.link));
			}
		}
		
		// parse the disaster zones
		NodeList disasterZoneNodeList = doc.getElementsByTagName("disasterZone");
		for (int i=0; i<disasterZoneNodeList.getLength(); i++) {
			Element disasterZoneElem = (Element) disasterZoneNodeList.item(i);
			
			ArrayList<MapNode> affectedZones = new ArrayList<MapNode>();
			HashMap<MapNode, Boolean> affectedCities = new HashMap<MapNode, Boolean>();
			NodeList affectedMapNodeNodeList = disasterZoneElem.getElementsByTagName("affectedMapNode");
			for (int j=0; j<affectedMapNodeNodeList.getLength(); j++) {
				Element affectedMapNodeElem = (Element) affectedMapNodeNodeList.item(j);
				int affectedZone = Integer.parseInt(affectedMapNodeElem.getChildNodes().item(0).getNodeValue());
				boolean cityAffected = false;
				if (affectedMapNodeElem.hasAttribute("cityAffected")) {
					cityAffected = Boolean.parseBoolean(affectedMapNodeElem.getAttribute("cityAffected"));
				}
				MapNode mn = mapNodes.get(affectedZone);
				affectedZones.add(mn);
				affectedCities.put(mn, cityAffected);
			}
			
			DisasterZone disasterZone;
			String type = disasterZoneElem.getElementsByTagName("type").item(0).getChildNodes().item(0).getNodeValue();
			switch (type) {
			case ("volcano"):
				disasterZone = new Volcano(affectedZones);
				break;
			case ("floodplain"):
				disasterZone = new Floodplain(affectedZones, affectedCities);
				break;
			default:
				throw new MapXMLParseException("Unknown disaster type");
			}
			disasterZones.add(disasterZone);
		}
		
		map = new JCivMap(mapNodes, disasterZones);
	}
	
	/**
	 * lists all the neighbours linked to a node, by the type of the link, and the ID of the target.
	 *
	 */
	private class NeighbourList {
		// key is link type (i.e. sea or land), val is list of ids of neighbours:  
		private ArrayList<Neighbour> neighbours;
		
		/**
		 * 
		 * @param noTypes the number of types of links to neighbours
		 */
		public NeighbourList() 
		{
			neighbours = new ArrayList<Neighbour>();
		}
		
		public void addNeighbour(int type, int neighbourID)
		{
			
			neighbours.add(new Neighbour(type, neighbourID));
		}
		
		public ArrayList<Neighbour> getAllNeighbours()
		{
			return neighbours;
		}
	}
	
	private class Neighbour {
		public int type;
		public int link;
		
		public Neighbour(int type, int neighbourID) {
			this.type = type;
			link = neighbourID;
		}
	}
	
	private class MapXMLParseException extends XMLParseException {
		public MapXMLParseException(String msg)
		{
			super(msg);
		}
	}
}
