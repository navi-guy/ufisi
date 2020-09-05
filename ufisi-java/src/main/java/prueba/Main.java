package prueba;

import javax.swing.*;

public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new View();
                frame.setSize(700,400);
                frame.setVisible(true);
            }
        });
    }
}
