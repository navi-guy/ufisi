package procesamientoOrdenes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame{
    private JPanel panel1;
    private JTextField productoTextField;
    private JTextField cantidadTextField;
    private JButton agregarButton;
    private JButton quitarButton;
    private JTable table1;
    private JButton realizarPedidoButton;
    private JLabel lblUserName;
    private MessageSender productor;
    private MessageReceiver consumidor;

    public View() {
        super("Modulo Productos");
        setContentPane(panel1);
        productor = new MessageSender();
        consumidor = new MessageReceiver();
        cargarTabla();

        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String nombre = productoTextField.getText();
                // int cantidad = Integer.parseInt(cantidadTextField.getText());
                String messageToInventario = "{\"codigo_cliente\":\"16200001\","+
                        "\"nombre_cliente\":\"Sergio A.\","+
                        " \"ruc_cliente\":\"12345678923\","+
                        "\"items\": [{\"nombre\":\"Cuaderno Anillado\","+
                                    "\"cantidad\":\"12\"," +
                                    "\"precio_unitario\":\"10.50\"}," +
                                    "{\"nombre\":\"Cuaderno Normal\"," +
                                    "\"cantidad\":\"8\","+
                                    "\"precio_unitario\":\"5.50\"}]}";
                productor.enviar(messageToInventario);
                System.out.println( "Mensaje: "+ messageToInventario);
                System.out.println("Enviado !");
                // System.out.println(consumidor.recibir("test","productoID"));
                // System.out.println("Llego");
            }
        });
    }

    private void cargarTabla(){
        String[][] data = new String[2][4];
        data[0][0]="P-2";
        data[0][1]="Cuaderno Anillado";
        data[0][2]="10.50";
        data[0][3]="12";
        data[1][0]="P-1";
        data[1][1]="Cuaderno Normal";
        data[1][2]="5.50";
        data[1][3]="8";
        table1.setModel(new DefaultTableModel(
                data,
                new String[] {"Codigo","Nombre","Precio (S/.)","Cantidad"}
                ));
    }

}
