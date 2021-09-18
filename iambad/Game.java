package iambad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game{
    public static void main(String[] args){
        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new GamePanel());
        frame.setSize(1200, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel implements ActionListener{
    Color currColor = Color.CYAN;
    boolean gameStarted = false;
    int xCoor=100, yCoor=0, pieceX = 121, pieceY=21;
    Timer timer;

    public GamePanel(){
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        JButton button = new JButton("Start");
        button.setBackground(Color.WHITE);
        button.setBounds(500, 0, 100, 20);
        button.addActionListener(new StartButton(this));
        this.add(button);
        startGame();
    }
    public void startGame(){
        this.addKeyListener(new KA());
        timer = new Timer(1000, this);
        timer.start();
    }

    public class StartButton implements ActionListener{
        GamePanel g;
        public StartButton(GamePanel g){
            this.g = g;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            g.gameStarted = true;
        }

    }

    public void checkHitBottom(){
        if(pieceY>=651){
            repaint();
        } 
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        for(int i=0; i<32; i++){
            for(int j=0; j<12; j++){
                if(i==0||i==31||j==0||j==11){
                    g.setColor(Color.DARK_GRAY);
                }else g.setColor(Color.GRAY);
                g.fillRect(xCoor, yCoor, 20, 20);
                xCoor+=21;
            }
            yCoor+=21;  
            xCoor=100;
        }
        xCoor = 100;
        yCoor = 0;
        if(pieceY>=631){
            g.setColor(Color.YELLOW);
            g.fillRect(pieceX, pieceY, 20, 20);
            pieceX = 121;
            pieceY = 21;
            //g.setColor(Color.)
        }else if(gameStarted){
            g.setColor(currColor);
            g.fillRect(pieceX, pieceY, 20, 20);
            pieceY +=21;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(gameStarted) {
            checkHitBottom();
        }
    }

    public class KA extends KeyAdapter{ 

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
            super.keyPressed(e);
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(pieceX<330) {
                        pieceX -=21;
                        System.out.println("L");
                        repaint();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(pieceX>=121){
                        pieceX +=21;
                        System.out.println("R");
                        repaint();
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    timer.setDelay(100);
                break;
            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
            super.keyReleased(e);
            if(e.getKeyCode()==KeyEvent.VK_DOWN){
                timer.setDelay(1000);
            }
        }
    }
}