package procesamientoOrdenes;

import javax.swing.*;


public class Main {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new View();
                frame.setSize(1400,600);
                frame.setVisible(true);
            }
        });
        Runnable runnableReceiver = new MessageReceiver(); // or an anonymous class, or lambda...
        Thread thread = new Thread(runnableReceiver);
        thread.start();
    }
}
