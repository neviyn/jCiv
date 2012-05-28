package jCiv;

public class CivCard {
    private String name, text;
    private String[] type;
    private int[] discount;
    private int max, score;

    public CivCard(String name, String[] type, String text, int[] discount, int max, int score) {
        this.name = name;
        this.type = type;
        this.text = text;
        this.discount = discount;
        this.max = max;
        this.score = score;
    }

    public String getName() { return name; }
    public String[] getType() { return type; }
    public String getText() { return text; }
    public int[] getDiscount() { return discount; }
    public int getMax() { return max; }
    public int getScore() { return score; }
}
