package jCiv.fileHandling;

import org.w3c.dom.*;
import java.util.List;
import java.util.ArrayList;

import jCiv.map.Nation;

/**
 * Loads an XML file containing data on nations, parses it, and provides a list of all of the nations.
 * 
 * @author jdl
 */
public class NationLoader extends XMLLoader {

	Document doc;
	List<Nation> nations;
	
	/**
	 * Load and parse a nations XML file.
	 * 
	 * @param filename the file name of the XML document to be loaded and parsed.
	 */
	public NationLoader(String filename) 
	{
		nations = new ArrayList<Nation>();
		doc = loadDocument(filename);
		parse();
	}
	
	/**
	 * 
	 * @return a list containing all of the nation objects from the XML file
	 */
	public List<Nation> getNations()
	{
		return nations;
	}
	
	/**
	 * Parse the xml file, building it into a collection of nation objects.
	 */
	@Override
	protected void parse()
	{
		NodeList nationsList = doc.getElementsByTagName("nation");
		for (int i=0; i<nationsList.getLength(); i++) {
			Element nationElem = (Element) nationsList.item(i);
			int nationID = Integer.parseInt(nationElem.getAttribute("id"));
			String nationName = nationElem.getElementsByTagName("name").item(0).getNodeValue();
			
			NodeList nationStartPositions = nationElem.getElementsByTagName("startingPositions").item(0).getChildNodes();
			int[] startingPositions = new int[7]; // TODO: change this to a better method of determining max players
			for (int j=0; j<nationStartPositions.getLength(); j++) {
				Element startPosElem = (Element) nationStartPositions.item(j);
				int noPlayers = Integer.parseInt(startPosElem.getAttribute("noPlayers"));
				startingPositions[noPlayers] = Integer.parseInt(startPosElem.getNodeValue());
			}
			nations.add(new Nation(nationID, nationName, startingPositions));
		}
	}
}
