package jCiv;

/**
 * Class representing a single civ or tech card.
 * User: Edward Bowles
 * Date: 28/05/12
 * Time: 18:50
 */
public class CivCard {
    private String name, text;
    private String[] type;
    private int[] discount;
    private int score;

    /**
     * An object representing a single civ or tech card.
     * @param name the name of the card.
     * @param type the types of the card.
     * @param text the text written on the card.
     * @param discount the amount of discount given towards other cards of the same type.
     * @param score the cost and the score the card is worth.
     */
    public CivCard(String name, String[] type, String text, int[] discount, int score) {
        this.name = name;
        this.type = type;
        this.text = text;
        this.discount = discount;
        this.score = score;
    }

    public String getName() { return name; }
    public String[] getType() { return type; }
    public String getText() { return text; }
    public int[] getDiscount() { return discount; }
    public int getScore() { return score; }
}
