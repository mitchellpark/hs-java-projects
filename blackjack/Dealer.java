package blackjack;
import java.util.Scanner;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Dealer {

    int playerCount;
    int numGames = 0;
    Deck deck;
    List<Player> players;
    List<Integer> turnsList;
    Scanner sc;
    int playerTurn = -1;
    boolean stillPlaying = false;

    public Dealer(){
        promptPlay();
    }

    public void promptPlay(){
        String msg1 = "   WELCOME TO THE ONLINE CASINO.  ";
        String msg2 = "  YOU ARE AT THE BLACKJACK TABLE. ";
        printGivenMessage(msg1, msg2);

        if(playerMessage("Start the first game? (y/n)", 'Y', 'N')){
            startGame();
        }else{
            endGame();
        }
    }

    public void startGame(){
        stillPlaying = true;
        System.out.println("\n____________________________________________________________");
        String msg = (numGames>0)? "Okay. Game " + String.valueOf(numGames + 1)+ ":\n" : "Welcome.";
        System.out.print(msg + "At this table there will be 2-7 players");
        for(int i=0; i<0; i++){ //I=8888888888
            System.out.print(".");
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(i==3) System.out.print("\nDeciding on the number of players");
        }
        playerCount = new Random().nextInt(6)+2;
        playerTurn = new Random().nextInt(playerCount);
        System.out.println("\nOkay. There will be " + playerCount + " players.");
        players = new ArrayList<>();
        turnsList = new ArrayList<>();
        for(int i=0; i<playerCount; i++){
            players.add(new Player(i));
            turnsList.add(i+1);
        }
        if(playerMessage("Continue? (y/n):", 'Y', 'N')){
            dealCards();
        }else endGame();

    }

    public void dealCards(){
        deck = new Deck();
        StringBuilder sb = new StringBuilder();
        System.out.println("\nThe cards are dealt:");
        for(int i=0; i<playerCount; i++){
            Player p = players.get(i);
            Card first = deck.drawCard();
            Card second = deck.drawCard();

            p.receiveCard(first);
            p.receiveCard(second);
            p.hiddenSb.append("?" + second.getShortened());
            sb.append("Player " + String.valueOf(i+1) + ": ");
            if(i==playerTurn) {
                sb.append(p.getRevealedHand());
                sb.append("\t(You)");
            } else sb.append(p.getRevealedHand()); //gethidden

            sb.append("\n");
        }
        System.out.println(sb.toString());
        System.out.println("----------------------------------------------------");
        playRounds();
    }

    public void playRounds(){
        for(int i=0; i<turnsList.size(); i++){
            if(i==playerTurn){
                userTurn(deck);
            }else{
                String s = "----------------\n";
                s+= " | Player " + String.valueOf(i+1) + ": |" ;
                if(i==playerTurn) s += "\t(You)";
                System.out.println(s);
                String res = players.get(i).bestPlay(deck, i);
                if(res.equals("WIN")){
                    System.out.println("Player " + String.valueOf(i+1) + " wins the game.");
                    numGames++;
                    askContinue();
                }else if(res.equals("BUST")){
                    players.remove(i);
                    turnsList.remove(i);
                    if(players.size()==1){
                        System.out.println("Player " + turnsList.get(0) + " wins, by the process of elimination");
                        System.out.println("His hand: " + players.get(0).getRevealedHand());
                        askContinue();
                    }
                }
            }
        }
        getWinner();
        askContinue();
    }

    public void getWinner(){
        System.out.println("\nThe round is over, and we determine who is the winner.");
        int bestPlayer = 0;
        List<Player> tiedList = new ArrayList<>();
        tiedList.add(players.get(0));
        for(int i=1; i<turnsList.size(); i++){
            Player p = players.get(i);
            int currDiff = Math.abs(21-p.sum);
            int smallest = Math.abs(21 - players.get(bestPlayer).sum);
            if(currDiff< smallest){
                bestPlayer = i;
            }
            if(currDiff==smallest) tiedList.add(p);
            String ln = "Player " + String.valueOf(i+1) + "'s sum: " + p.sum + "\tHand: " + p.getRevealedHand();
            if(i==playerTurn) ln += "\t(You)";
            System.out.println(ln);
        }
        String res = "";
        if(tiedList.size()>1){
            announceTies(tiedList);
        }else{
            res  = (bestPlayer==playerTurn) ? "You are the winner": "The winner is Player" + String.valueOf(bestPlayer+1)+ 
                ", with the sum of " + players.get(bestPlayer).sum;
            System.out.println(res);
        }
    }

    public void announceTies(List<Player> l){
        StringBuilder sb = new StringBuilder();
        sb.append("The winners: ");
        for(int i=0; i<l.size(); i++){
            sb.append("Player " + l.get(i) + " with a score of " + l.get(i).sum);
            if(i==l.size()-1) sb.append(".");
            else if(i==l.size()-2){
                if(l.size()!=2) sb.append(", ");
                sb.append("and ");
            }else sb.append(",");
        }
        System.out.println(sb.toString());
    }

    public void askContinue(){
        stillPlaying = false;
        if(playerMessage("\nWould you like to play a new round? (y/n):", 'Y', 'N')){
            startGame();
        }else {
            endGame();
        }
    }

    public void userTurn(Deck d){
        Player p = players.get(playerTurn);
        if(p.sum==21 || p.getLowerScore()==21) {
            System.out.println("21!! You win the game!");
            System.out.println("Your hand: " + p.getRevealedHand());
            askContinue();
        }
        String msg = "----------------------------\n";
        msg += "It is your turn. This is your hand:\n\n\t" + p.getRevealedHand() + "\n\n";
        msg += "You have a total sum of " + getAceSum(p) + ". ";
        while(playerMessage(msg + "Stand or hit? (s/h)", 'H', 'S')){
            Card c = d.drawCard();
            boolean ableToReceive = p.receiveCard(c);
            System.out.println("\nYou hit. Card:\t" + c.getShortened());
            System.out.println("Your hand:\t" + p.getRevealedHand());
            if(ableToReceive){
                if(p.sum==21 || p.getLowerScore()==21) {
                    System.out.println("You win the game!");
                    System.out.println("Your hand: " + p.getRevealedHand());
                    askContinue();
                }else{
                    msg = "\nYou got the " + c.getFullName() +", for a total sum of " + p.sum +".";
                }
            }else{
                System.out.println("\nBust! Sorry, you went over 21, for a total sum of " + p.sum + ".");
                System.out.println("Your hand: " + p.getRevealedHand());
                askContinue();
            }
        }
        System.out.println("You stayed.");
    }

    public String getAceSum(Player p){
        StringBuilder res = new StringBuilder();
        int sum = p.sum;
        res.append(sum);
        int a = p.aceCount;
        if(a>0){
            for(int i=0; i<a; i++){
                a--;
                sum -= 10;
                if(i!=a-1) res.append(", ");
                else res.append(", or ");
                res.append(sum);
            }
            res.append(".");
            return res.toString();
        }
        return String.valueOf(sum);
    }

    public void endGame(){
        numGames = 0;
        String msg1 = "  WE'RE SAD TO SEE YOU GO.  ";
        String msg2 = "  THANKS FOR STOPPING BY!   ";
        printGivenMessage(msg1, msg2);
        if(playerMessage("Are you sure you want to exit? (y/n):", 'N', 'Y')){
            startGame();
        }else {
            sc.close();
            System.exit(0);
        }
    }

    public void printGivenMessage(String str1, String str2){
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n\n");
        for(int row=0; row<8; row++){
            for(int col=0; col<40; col++){
                if((row==2 || row==5) && col>3 && col <37) sb.append(" ");
                else if(row==3 && col==(40-str1.length())/2){
                    sb.append(str1);
                    col += str1.length()-1;
                }else if(row==4 && col==(40-str2.length())/2){
                    sb.append(str2);
                    col += str2.length()-1;
                }
                else sb.append("*");
            }
            sb.append("\n");
        }
        sb.append("\n\n");
        System.out.println(sb.toString());
    }

    public boolean playerMessage(String line, char yes, char no){
        sc = new Scanner(System.in);
        System.out.println(line);
        while(sc.hasNextLine()){
            String str = sc.nextLine().replaceAll("\\s", "");
            if(str.equals(String.valueOf(yes))||str.equals(String.valueOf((char)(yes+32)))){
                return true;
            }else if(str.equals(String.valueOf(no)) || str.equals(String.valueOf((char)(no+32)))){
                return false;
            }
            else{
                System.out.println("Enter a valid input.");
            }
        }
        return false;
    }
}
