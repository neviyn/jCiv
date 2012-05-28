package jCiv;

/**
 * Class representing a single trading card.
 * User: Edward Bowles
 * Date: 28/05/12
 * Time: 18:55
 */
public class TradingCard {
    private String name;
    private int maxSetSize;

    /**
     * An object representing a single trading card.
     * @param name the name of the card.
     * @param maxSetSize the max size of each set.
     */
    public TradingCard(String name, int maxSetSize) {
        this.name = name;
        this.maxSetSize = maxSetSize;
    }
    //NOTE: formula for set value is: "value * (quantity^2)"
    public String getName() { return name; }
    public int getMaxSetSize() { return maxSetSize; }
}
