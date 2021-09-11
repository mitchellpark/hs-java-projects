package blackjack;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class Deck {

    Stack<Card> cards;
    
    public Deck(){
        cards = new Stack<>();
        for(int i=0; i<4; i++){
            for(int j=0; j<13; j++){
                cards.add(new Card(i, j));
            }
        }
        Collections.shuffle(cards);
    }

    public Card drawCard(){
        return cards.pop();
    }
}
