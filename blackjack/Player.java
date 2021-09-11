package blackjack;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;

public class Player {
    StringBuilder hiddenSb;
    StringBuilder revealedSb;
    int turn, sum, aceCount;
    List<Card> cards;
    public Player(int i){
        hiddenSb = new StringBuilder();
        revealedSb = new StringBuilder();
        turn = i;
        sum = 0;
        aceCount = 0;
        cards = new ArrayList<>();
    }

    public boolean receiveCard(Card c){
        revealedSb.append(c.getShortened());
        if(c.name>=10) {
            sum += 10;
        }else if(c.name==0){
            aceCount++;
            sum += 11;
        }else{
            sum+=c.name + 1;
        }
        if(sum>21){
            if(getLowerScore()>21) return false;
        }
        cards.add(c);
        return true;
    }

    public String bestPlay(Deck d, int id){
        System.out.println("----------------");
        if(getLowerScore()==21 || sum==21) return "WIN";
        while(sum<13){
            for(int i=0; i<3; i++){
                try{
                    System.out.print(".");
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            System.out.print("\n");
            if(getLowerScore()==21 || sum==21) return "WIN";
            Card c = d.drawCard();
            if(this.receiveCard(c)){
                this.hiddenSb.insert(0, "?");
                System.out.println("Player " + String.valueOf(id+1)+" hits!\t" + "Hand: " + this.getRevealedHand());
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("PLayer " + String.valueOf(id+1) +" busts!\t" + "Hand: " + this.getRevealedHand());
                return "BUST";  
            }
        }
        for(int i=0; i<3; i++){
            try{
                System.out.print(".");
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("Player " + String.valueOf(id+1) + " stays.");
        return "STAY";
    }

    public String getHiddenHand(){
        return hiddenSb.toString();
    }

    public String getRevealedHand(){
        return revealedSb.toString();
    }

    public int getLowerScore(){
        if(sum>=21){
            for(int i=0; i<aceCount; i++){
                aceCount--;
                if(sum-11<21){
                    sum -= 10;
                    return sum;
                }
            }
        }
        return sum;
    }
}
