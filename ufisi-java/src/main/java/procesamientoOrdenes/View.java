package prueba;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class View extends JFrame{
    private JPanel panel1;
    private JTextField productoTextField;
    private JTextField cantidadTextField;
    private JButton agregarButton;
    private JButton quitarButton;
    private JTable tableProductosCarrito;
    private JButton realizarPedidoButton;
    private JLabel montoTotal;
    private JTable tableProductos;
    private MessageSender productor;
    private MessageReceiver consumidor;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Producto> productosCarrito;
    private Producto productoPrueba1 = new Producto("21","papel",20.5);
    private Producto productoPrueba2 = new Producto("22","madera",10);

    public View() {
        super("Modulo Productos");
        setContentPane(panel1);
        productor = new MessageSender();
        consumidor = new MessageReceiver();
        productosCarrito = new ArrayList<>();
        listaProductos = new ArrayList<>();
        tableProductos.setModel(new DefaultTableModel(
                null,
                new String[] {"Codigo","Nombre","Precio (S/.)"}
        ));
        tableProductosCarrito.setModel(new DefaultTableModel(
                null,
                new String[] {"Codigo","Nombre","Precio (S/.)","Cantidad"}
        ));
        iniciarTablaProductos();

        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Gson convertidor = new Gson();
                montoTotal.setText("10.5");
                String messageToInventario = convertidor.toJson(productosCarrito);
                System.out.println(messageToInventario);
                productor.enviar("inventario",messageToInventario);
                System.out.println( "Mensaje: "+ messageToInventario);
                System.out.println("Enviado !");
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //boolean encontrado = true;
                String nombre = productoTextField.getText();
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                int cantidad = Integer.parseInt(cantidadTextField.getText());
                for(Producto producto: listaProductos){
                    if(producto.getNombre().equals(nombre)){
                        Producto nuevoProducto = new Producto(producto.getId(),producto.getNombre(),producto.getPrecio(),cantidad);
                        productosCarrito.add(nuevoProducto);
                        model.addRow(nuevoProducto.convertirString());
                    }
                }
            }
        });

        quitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                int fila = tableProductosCarrito.getSelectedRow();
                String nombreEliminar = (String) model.getValueAt(fila,1);
                boolean encontrado=false;
                int i =0;
                while(i<productosCarrito.size() && !encontrado){
                    if(productosCarrito.get(i).getNombre().equals(nombreEliminar)){
                        productosCarrito.remove(productosCarrito.get(i));
                        model.removeRow(fila);
                        encontrado = true;
                    }
                    i++;
                }
            }
        });
    }

    private void iniciarTablaProductos(){
        DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
        Gson convertidor = new Gson();
        //String mensajeProductos = consumidor.recibir("inventario","productoID");
        String mensajeProductos = "[{\"id\":\"21\",\"nombre\":\"papel\",\"precio\":20.5,\"cantidad\":0},{\"id\":\"22\",\"nombre\":\"madera\",\"precio\":10.0,\"cantidad\":0}]";
        listaProductos = convertidor.fromJson(mensajeProductos,new TypeToken<ArrayList<Producto>>() {}.getType());


        for (Producto producto: listaProductos){
            model.addRow(producto.convertirString());
        }
    }


}
