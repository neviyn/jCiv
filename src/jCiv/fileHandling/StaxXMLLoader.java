package jCiv.fileHandling;

import jCiv.map.Floodplain;
import jCiv.map.MapNode;
import jCiv.map.DisasterZone;
import jCiv.map.Volcano;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Nathan Cannon
 * Date: 25/05/12
 * Time: 19:33
 */
public class StaxXMLLoader {
    private final String gameMap;
    private final String disasterZonesLocation;

    public StaxXMLLoader(String mapLocation, String disasterZonesLocation)
    {
        this.gameMap = mapLocation;
        this.disasterZonesLocation = disasterZonesLocation;
    }
    private HashMap<Integer, MapNode> loadMap() throws XMLStreamException
    {
        HashMap<Integer, MapNode> nodes = new HashMap<>();
        // Create connection to XML file
        System.out.println("Opening:" + gameMap);
        StreamSource s = new StreamSource(getClass().getClassLoader().getResource(gameMap).getPath());
        XMLInputFactory fact = XMLInputFactory.newFactory();
        XMLStreamReader read = fact.createXMLStreamReader(s);
        // Create variables to hold active data in.
        HashMap<Integer, ArrayList<Tuple>> nextTo = new HashMap<>();
        ArrayList<Tuple> neighbours = new ArrayList<>();
        int state = 0;
        int nodeNum = -1;
        boolean ocean = false;
        boolean coast = false;
        int support = -1;
        boolean cityPoint = false;
        int linkType = -1;
        boolean running = true;
        // Generate nodes and a list to generate neighbour data from.
        while(running)
        {
            int eventCode = read.next();
            switch(eventCode) {
                // Starting XML element, set read state.
                case XMLStreamConstants.START_ELEMENT:
                    switch(read.getLocalName()){
                        case "ocean":
                            state = 1;
                            break;
                        case "coast":
                            state = 2;
                            break;
                        case "popLimit":
                            state = 3;
                            break;
                        case "neighbourList":
                            state = 4;
                            break;
                        case "nodeLinks":
                            state = 5;
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
                            state = 6;
                            break;
                        case "citySite":
                            state = 7;
                            break;
                        case "mapNode":
                            nodeNum = Integer.parseInt(read.getAttributeValue(0));
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    switch(state){
                        case 0:
                            break;
                        case 1:
                            ocean = Boolean.parseBoolean(read.getText());
                            break;
                        case 2:
                            coast = Boolean.parseBoolean(read.getText());
                            break;
                        case 3:
                            support = Integer.parseInt(read.getText());
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            neighbours.add(new Tuple(linkType, Integer.parseInt(read.getText())));
                            break;
                        case 7:
                            cityPoint = !read.getText().equals("");
                            break;
                        default:
                            throw new IllegalStateException("Unknown state based on XML tag!");
                    }
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
                        case "map":
                            running = false;
                            break;
                        default:
                            break;
                    }
                    state = 0;
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
        return nodes;
    }

    private ArrayList<DisasterZone> loadDisasterZones(HashMap<Integer, MapNode> mapNodes) throws XMLStreamException
    {
        ArrayList<DisasterZone> zones = new ArrayList<>();
        // Create connection to XML file
        System.out.println("Opening:" + disasterZonesLocation);
        StreamSource s = new StreamSource(getClass().getClassLoader().getResource(disasterZonesLocation).getPath());
        XMLInputFactory fact = XMLInputFactory.newFactory();
        XMLStreamReader read = fact.createXMLStreamReader(s);
        int state = 0;
        int type = 0;
        boolean running = true;
        ArrayList<MapNode> temp = new ArrayList<>();
        // Generate nodes and a list to generate neighbour data from.
        while(running)
        {
            int eventCode = read.next();
            switch(eventCode) {
                // Starting XML element, set read state.
                case XMLStreamConstants.START_ELEMENT:
                    System.out.println("Start:"+read.getLocalName());
                    switch(read.getLocalName())
                    {
                        case "disasterZoneList":
                            System.out.println("Attr:"+read.getAttributeValue(0));
                            switch(read.getAttributeValue(0))
                            {
                                case "flood plain":
                                    type = 1;
                                    break;
                                case "volcano":
                                    type = 2;
                                    break;
                            }
                            break;
                        case "mapNode":
                            state = 1;
                        default:
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    System.out.println("'"+read.getText()+"'");
                    if(state == 0)
                    {
                    }
                    else
                    {
                        temp.add(mapNodes.get(Integer.parseInt(read.getText())));
                    }
                    break;

                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    System.out.println("End:"+read.getLocalName());
                    switch(read.getLocalName())
                    {
                        case "disasterZone":
                            switch(type)
                            {
                                case 1:
                                    zones.add(new Floodplain(temp));
                                    break;
                                case 2:
                                    zones.add(new Volcano(temp));
                                    break;
                            }
                            temp = new ArrayList<>();
                            break;
                        case "disasterZones":
                            // XML file has ended.
                            running = false;
                            break;
                    }
                    state = 0;
                    break;
                // XML file has ended (redundant).
                case XMLStreamConstants.END_DOCUMENT:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Unknown XML event.");

            }
        }
        return zones;
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
}

