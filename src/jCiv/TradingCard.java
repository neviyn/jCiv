package jCiv;

/**
 * Class representing a single trading card.
 * User: Edward Bowles
 * Date: 28/05/12
 * Time: 18:55
 */
public class TradingCard {
    private String name;
    private int multiplier, sizeOfSet;

    /**
     * An object representing a single trading card.
     * @param name the name of the card.
     * @param multiplier the multiplier for set bonuses.
     * @param sizeOfSet the max size of each set.
     */
    public TradingCard(String name, int multiplier, int sizeOfSet) {
        this.name = name;
        this.multiplier = multiplier;
        this.sizeOfSet = sizeOfSet;
    }

    public String getName() { return name; }
    public int getMultiplier() { return multiplier; }
    public int getSizeOfSet() { return sizeOfSet; }
}
