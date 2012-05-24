package jCiv.map;


/**
 * User: Nathan Cannon
 * Date: 23/05/12
 * Time: 00:13
 */
public class NodeLink {
    private int linkType;
    private MapNode target;

    public NodeLink(int linkType, MapNode target)
    {
        this.linkType = linkType;
        this.target = target;
    }

    public int getLinkType()
    {
        return linkType;
    }

    public MapNode getNeighbour()
    {
        return target;
    }
}
