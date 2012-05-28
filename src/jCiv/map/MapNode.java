package jCiv.map;


import java.util.ArrayList;

/**
 * Class representing a single map location.
 * User: Nathan Cannon
 * Date: 22/05/12
 * Time: 21:46
 */
public class MapNode{
    public int nodeNum;
    private boolean ocean;
    private boolean coast;
    private int support;
    private boolean cityPoint;
    private int cityBuilt = -1;
    private ArrayList<NodeLink> neighbours;

    /**
     * An object representing a single map tile.
     * @param nodeNum Number representing this node.
     * @param ocean Is this an ocean tile?
     * @param coast Is this a coastal tile?
     * @param support Max tokens this tile supports.
     * @param cityPoint Is it cheaper to build cities on this tile?
     */
    public MapNode(int nodeNum, boolean ocean, boolean coast, int support, boolean cityPoint)
    {
        this.nodeNum = nodeNum;
        this.ocean = ocean;
        this.coast = coast;
        this.support = support;
        this.cityPoint = cityPoint;
        neighbours = new ArrayList<NodeLink>();
    }

    public void addNeighbour(int linkType, MapNode neighbour)
    {
        neighbours.add(new NodeLink(linkType, neighbour));
    }

    public boolean isOcean() {
        return ocean;
    }

    public boolean isCoast() {
        return coast;
    }

    public int getSupport() {
        return support;
    }

    public boolean isCityPoint() {
        return cityPoint;
    }

    public int getCityBuilt() {
        return cityBuilt;
    }

    public void setCityBuilt(int cityBuilt) {
        this.cityBuilt = cityBuilt;
    }

    public void destroyCity()
    {
        cityBuilt = -1;
    }
    
    @Override
    public String toString()
    {
    	String result = "";
    	result += "\nID: " + nodeNum;
    	result += "\nOcean: " + ocean;
    	result += "\nCoast: " + coast;
    	result += "\nPopulation limit: " + support;
    	result += "\nCity site: " + cityPoint;
    	result += "\nNeighbours:";
    	for (NodeLink n : neighbours) {
    		result += "\n\tType: " + n.getLinkType() + "\tLink: " + n.getNeighbour().nodeNum;
    	}
    	
    	return result;
    }
}
