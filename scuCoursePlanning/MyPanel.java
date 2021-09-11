import javax.swing.*;
import java.awt.*;
import java.util.*;

class MyPanel extends JPanel {
    
    public MyPanel(){
        ArrayList<JButton> jbList = new ArrayList<>();
            setBackground(Color.LIGHT_GRAY);
            JButton j1 = new JButton("J1 baby");
            JButton j2 = new JButton("J2 baby");
            JButton j3 = new JButton("yuh");
            jbList.add(j1);
            jbList.add(j2);
            jbList.add(j3);
            for(JButton j: jbList){
                add(j);
            }
    }
}