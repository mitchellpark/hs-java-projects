package blackjack;

public class Card {
    String[] suits = {"\u2660", "\u2663", "\u2665", "\u2666"};
    String[] suitNames = {"Spades", "Clovers", "Hearts", "Diamonds"};
    String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    String[] longNames = {"Ace", "Two", "Three", "Four","Five","Six","Seven","Eight","Nine","Ten", "Jack", "Queen", "King"};

    int suit = -1;
    int name = -1;
    public Card(int suit, int name){
        this.suit = suit;
        this.name = name;
    }

    public String getFullName(){
        return longNames[name] + " of " + suitNames[suit];
    }

    public String getShortened(){
        return names[name]+suits[suit];
    }

}
