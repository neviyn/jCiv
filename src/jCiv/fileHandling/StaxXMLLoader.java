package jCiv.fileHandling;

import jCiv.CivCard;
import jCiv.map.*;

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

    public StaxXMLLoader()
    {
    }

    private XMLStreamReader openXMLStream(String file) throws XMLStreamException
    {
        StreamSource s = new StreamSource(getClass().getClassLoader().getResource(file).getPath());
        XMLInputFactory fact = XMLInputFactory.newFactory();
        return fact.createXMLStreamReader(s);
    }

    private enum mapState
    {
        ZERO, OCEAN, COAST, POPLIMIT, NEIGHBOURLIST, NODELINKS, LINK, CITYSITE
    }
    private HashMap<Integer, MapNode> loadMap(String gameMapFile) throws XMLStreamException
    {
        HashMap<Integer, MapNode> nodes = new HashMap<>();
        XMLStreamReader read = openXMLStream(gameMapFile);
        // Create variables to hold active data in.
        HashMap<Integer, ArrayList<Tuple>> nextTo = new HashMap<>();
        ArrayList<Tuple> neighbours = new ArrayList<>();
        mapState state = mapState.ZERO;
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
                            state = mapState.OCEAN;
                            break;
                        case "coast":
                            state = mapState.COAST;
                            break;
                        case "popLimit":
                            state = mapState.POPLIMIT;
                            break;
                        case "neighbourList":
                            state = mapState.NEIGHBOURLIST;
                            break;
                        case "nodeLinks":
                            state = mapState.NODELINKS;
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
                            state = mapState.LINK;
                            break;
                        case "citySite":
                            state = mapState.CITYSITE;
                            break;
                        case "mapNode":
                            nodeNum = Integer.parseInt(read.getAttributeValue(0));
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    switch(state){
                        case ZERO:
                            break;
                        case OCEAN:
                            ocean = Boolean.parseBoolean(read.getText());
                            break;
                        case COAST:
                            coast = Boolean.parseBoolean(read.getText());
                            break;
                        case POPLIMIT:
                            support = Integer.parseInt(read.getText());
                            break;
                        case NEIGHBOURLIST:
                            neighbours.add(new Tuple(linkType, Integer.parseInt(read.getText())));
                            break;
                        case CITYSITE:
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
                    state = mapState.ZERO;
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

    private enum disasterType
    {
        NULL, FLOODPLAIN, VOLCANO
    }
    private ArrayList<DisasterZone> loadDisasterZones(String disasterZonesLocationFile, HashMap<Integer, MapNode> mapNodes) throws XMLStreamException
    {
        ArrayList<DisasterZone> zones = new ArrayList<>();
        XMLStreamReader read = openXMLStream(disasterZonesLocationFile);
        boolean mNode = false;
        disasterType type = disasterType.NULL;
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
                                    type = disasterType.FLOODPLAIN;
                                    break;
                                case "volcano":
                                    type = disasterType.VOLCANO;
                                    break;
                            }
                            break;
                        case "mapNode":
                            mNode = true;
                        default:
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    System.out.println("'"+read.getText()+"'");
                    if(mNode)
                        temp.add(mapNodes.get(Integer.parseInt(read.getText())));
                    break;

                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    System.out.println("End:"+read.getLocalName());
                    switch(read.getLocalName())
                    {
                        case "disasterZone":
                            switch(type)
                            {
                                case FLOODPLAIN:
                                    zones.add(new Floodplain(temp));
                                    break;
                                case VOLCANO:
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
                    mNode = false;
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

    private enum nationState
    {
        NULL, NAME, POSITION
    }
    public ArrayList<Nation> loadNations(String nationsFile) throws XMLStreamException
    {
        ArrayList<Nation> nations = new ArrayList<>();
        XMLStreamReader read = openXMLStream(nationsFile);
        nationState state = nationState.NULL;
        int nationID = 0;
        int playerNumPos = 0;
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
                            state = nationState.NAME;
                            break;
                        case "position":
                            state = nationState.POSITION;
                            playerNumPos = Integer.parseInt(read.getAttributeValue(0));
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    switch(state){
                        case NAME:
                            nationName = read.getText();
                            break;
                        case POSITION:
                            startLocs[playerNumPos] = Integer.parseInt(read.getText());
                            break;
                    }
                    break;
                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    switch (read.getLocalName()){
                        case "nation":
                            Nation temp = new Nation(nationID, nationName, startLocs);
                            startLocs = new int[7];
                            nations.add(temp);
                    }
                    state = nationState.NULL;
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

    private enum civCardState {
        NULL, TYPE, DISCOUNT, SCORE, QUANTITY, TEXT
    }

    private ArrayList<CivCard> loadCivCards(String techCardFile) throws XMLStreamException
    {
        ArrayList<CivCard> output = new ArrayList<>();
        XMLStreamReader read = openXMLStream(techCardFile);
        String name = null, text = null;
        ArrayList<String> type = new ArrayList<>();
        ArrayList<Integer> discount = new ArrayList<>();
        int max = 0, score = 0;
        boolean running = true;
        civCardState state =  civCardState.NULL;
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
                            state = civCardState.TYPE;
                            break;
                        case "discount":
                            state = civCardState.DISCOUNT;
                            break;
                        case "score":
                            state = civCardState.SCORE;
                            break;
                        case "quantity":
                            state = civCardState.QUANTITY;
                            break;
                        case "text":
                            state = civCardState.TEXT;
                            break;
                    }
                    break;
                // Read data between XML tags based on state, state indicates the tag we are within.
                case XMLStreamConstants.CHARACTERS:
                    switch(state){
                        case TYPE:
                            type.add(read.getText());
                            break;
                        case DISCOUNT:
                            discount.add(Integer.parseInt(read.getText()));
                            break;
                        case SCORE:
                            score = Integer.parseInt(read.getText());
                            break;
                        case QUANTITY:
                            max = Integer.parseInt(read.getText());
                            break;
                        case TEXT:
                            text = read.getText();
                            break;

                    }
                    state = civCardState.NULL;
                    break;
                // If we now have enough data to do something, do it.
                case XMLStreamConstants.END_ELEMENT:
                    switch (read.getLocalName())
                        {
                            case "civCard":
                                String[] conv = new String[type.size()];
                                type.toArray(conv);
                                CivCard temp = new CivCard(name, conv, text, toIntArray(discount), max, score);
                                output.add(temp);
                                type = new ArrayList<>();
                                discount = new ArrayList<>();
                                text = null;
                                break;
                            case "cards":
                                running = false;
                                break;
                            default:
                                state = civCardState.NULL;
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

