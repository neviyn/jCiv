package jCiv.fileHandling;

import jCiv.CivCard;
import jCiv.map.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * User: Nathan Cannon
 * Date: 25/05/12
 * Time: 19:33
 */
public class StaxXMLLoader {

    public StaxXMLLoader()
    {
    }

    private XMLStreamReader openXMLStream(String file) throws XMLStreamException, FileNotFoundException
    {
        StreamSource s;
        try {
            s = new StreamSource(getClass().getClassLoader().getResource(file).getPath());
        } catch (NullPointerException ex) {
            throw new FileNotFoundException("Could not open XML file!");
        }
        XMLInputFactory fact = XMLInputFactory.newFactory();
        return fact.createXMLStreamReader(s);
    }

    private enum dType{
        VOLCANO, FLOODPLAIN
    }
    public JCivMap loadMap(String gameMapFile) throws XMLStreamException, FileNotFoundException
    {
        HashMap<Integer, MapNode> nodes = new HashMap<>();
        ArrayList<DisasterZone> zones = new ArrayList<>();
        XMLStreamReader read = openXMLStream(gameMapFile);
        // Create variables to hold active data in.
        HashMap<Integer, ArrayList<Tuple>> nextTo = new HashMap<>();
        HashMap<MapNode, Boolean> floodSpecial = new HashMap<>();
        ArrayList<MapNode> disasterArea = new ArrayList<>();
        ArrayList<Tuple> neighbours = new ArrayList<>();
        dType disasterType = null;
        int nodeNum = -1, support = -1, linkType = -1;
        boolean ocean = false, coast = false, cityPoint = false, running = true;
        // Generate nodes and a list to generate neighbour data from.
        while(running)
        {
            int eventCode = read.next();
            switch(eventCode) {
                // Starting XML element, set read state.
                case XMLStreamConstants.START_ELEMENT:
                    switch(read.getLocalName()){
                        case "ocean":
                            read.next();
                            ocean = Boolean.parseBoolean(read.getText());
                            break;
                        case "coast":
                            read.next();
                            coast = Boolean.parseBoolean(read.getText());
                            break;
                        case "popLimit":
                            read.next();
                            support = Integer.parseInt(read.getText());
                            break;
                        case "neighbourList":
                            break;
                        case "nodeLinks":
                            String t = read.getAttributeValue(0);
                            switch(t){
                                case "land":
                                    linkType = 1;
                                    break;
                                case "sea":
                                    linkType = 2;
                                    break;
                            }
                            break;
                        case "link":
                            read.next();
                            neighbours.add(new Tuple(linkType, Integer.parseInt(read.getText())));
                            break;
                        case "citySite":
                            read.next();
                            cityPoint = !read.getText().equals("");
                            break;
                        case "mapNode":
                            nodeNum = Integer.parseInt(read.getAttributeValue(0));
                            break;
                        case "disasterZone":
                            break;
                        case "type":
                            read.next();
                            switch(read.getText()){
                                case "volcano":
                                    disasterType = dType.VOLCANO;
                                    break;
                                case "floodplain":
                                    disasterType = dType.FLOODPLAIN;
                                    break;
                            }
                            break;
                        case "affectedMapNode":
                            boolean temp = false;
                            if(disasterType == dType.FLOODPLAIN && read.getAttributeCount() > 0)
                            {
                                temp = Boolean.parseBoolean(read.getAttributeValue(0));
                            }
                            read.next();
                            switch (disasterType){
                                case VOLCANO:
                                    disasterArea.add(nodes.get(Integer.parseInt(read.getText())));
                                    break;
                                case FLOODPLAIN:
                                    MapNode active = nodes.get(Integer.parseInt(read.getText()));
                                    disasterArea.add(active);
                                    floodSpecial.put(active, temp);
                            }
                            break;

                        default:
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    switch(read.getLocalName())
                    {
                        case "mapNode":
                            MapNode in = new MapNode(nodeNum,ocean,coast,support,cityPoint);
                            nodes.put(nodeNum, in);
                            cityPoint = false;
                            break;
                        case "neighbourList":
                            nextTo.put(nodeNum, neighbours);
                            neighbours = new ArrayList<>();
                            break;
                        // XML file has ended.
                        case "disasterZone":
                            switch (disasterType){
                                case VOLCANO:
                                    Volcano vTemp = new Volcano(disasterArea);
                                    zones.add(vTemp);
                                    break;
                                case FLOODPLAIN:
                                    Floodplain fTemp = new Floodplain(disasterArea, floodSpecial);
                                    zones.add(fTemp);
                                    floodSpecial = new HashMap<>();
                                    break;
                            }
                            disasterArea = new ArrayList<>();
                            break;
                        case "map":
                            running = false;
                            break;
                        default:
                            break;
                    }
                    break;
                // XML file has ended (redundant).
                case XMLStreamConstants.END_DOCUMENT:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown XML event.");

            }
        }
        // Transform neighbour data onto nodes.
        for (Map.Entry<Integer, ArrayList<Tuple>> integerArrayListEntry : nextTo.entrySet()) {
            Map.Entry entry = (Map.Entry) integerArrayListEntry;
            int key = (Integer) entry.getKey();
            @SuppressWarnings("unchecked")
            ArrayList<Tuple> value = (ArrayList<Tuple>) entry.getValue();
            MapNode first = nodes.get(key);
            for (Tuple t : value) {
                first.addNeighbour(t.linkT, nodes.get(t.target));
            }

        }
        return new JCivMap(nodes, new Vector<>(zones));
    }

    public ArrayList<Nation> loadNations(String nationsFile) throws XMLStreamException, FileNotFoundException
    {
        ArrayList<Nation> nations = new ArrayList<>();
        XMLStreamReader read = openXMLStream(nationsFile);
        int nationID = 0;
        String nationName = null;
        int[] startLocs = new int[7];
        boolean running = true;
        // Generate nodes and a list to generate neighbour data from.
        while(running)
        {
            int eventCode = read.next();
            switch(eventCode) {
                // Starting XML element, set read state.
                case XMLStreamConstants.START_ELEMENT:
                    switch(read.getLocalName()){
                        case "nation":
                            nationID = Integer.parseInt(read.getAttributeValue(0));
                            break;
                        case "name":
                            read.next();
                            nationName = read.getText();
                            break;
                        case "position":
                            int playerNumPos = Integer.parseInt(read.getAttributeValue(0));
                            read.next();
                            startLocs[playerNumPos] = Integer.parseInt(read.getText());
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    break;
                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    switch (read.getLocalName()){
                        case "nation":
                            Nation temp = new Nation(nationID, nationName, startLocs);
                            startLocs = new int[7];
                            nations.add(temp);
                    }
                    break;
                // XML file has ended (redundant).
                case XMLStreamConstants.END_DOCUMENT:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown XML event.");

            }
        }
        return nations;
    }


    public ArrayList<CivCard> loadCivCards(String techCardFile) throws XMLStreamException, FileNotFoundException
    {
        ArrayList<CivCard> output = new ArrayList<>();
        XMLStreamReader read = openXMLStream(techCardFile);
        String name = null, text = null;
        ArrayList<String> type = new ArrayList<>();
        ArrayList<Integer> discount = new ArrayList<>();
        int score = 0;
        boolean running = true;
        while(running)
        {
            int eventCode = read.next();
            switch(eventCode) {
                // Starting XML element, set read state.
                case XMLStreamConstants.START_ELEMENT:
                    switch(read.getLocalName()){
                        case "civCard":
                            name = read.getAttributeValue(0);
                            break;
                        case "type":
                            read.next();
                            type.add(read.getText());
                            break;
                        case "discount":
                            read.next();
                            discount.add(Integer.parseInt(read.getText()));
                            break;
                        case "score":
                            read.next();
                            score = Integer.parseInt(read.getText());
                            break;
                        case "quantity":
                            //TODO: Await review of CivCard format.
                            break;
                        case "text":
                            if(nextIsCharacters(read))
                                text = read.getText();
                            else
                                text = null;
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    break;
                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    switch (read.getLocalName())
                    {
                        case "civCard":
                            String[] conv = new String[type.size()];
                            type.toArray(conv);
                            CivCard temp = new CivCard(name, conv, text, toIntArray(discount), score);
                            output.add(temp);
                            type = new ArrayList<>();
                            discount = new ArrayList<>();
                            text = null;
                            break;
                        case "cards":
                            running = false;
                            break;
                    }
                    break;
                // XML file has ended (redundant).
                case XMLStreamConstants.END_DOCUMENT:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown XML event.");

            }
        }
        return output;
    }

    /**
     * Moves the reader on one and reports whether it is now on text.
     * Also returns false if what is read is all empty space.
     * @param read XMLStreamerReader being used to parse XML.
     * @return Is the reader now looking at characters?
     * @throws XMLStreamException
     */
    private boolean nextIsCharacters(XMLStreamReader read) throws XMLStreamException
    {
        int eventCode = read.next();
        return eventCode == XMLStreamConstants.CHARACTERS && !read.getText().trim().equals("");
    }

    private class Tuple {
        public final int linkT;
        public final int target;

        private Tuple(int linkT,int target)
        {
            this.linkT = linkT;
            this.target = target;
        }
    }

    int[] toIntArray(ArrayList<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }

}

