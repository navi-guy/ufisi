package procesamientoOrdenes;

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
    private JLabel labelMontoTotal;
    private JTable tableProductos;
    private MessageSender productor;
    private MessageReceiver consumidor;
    private ArrayList<Producto> listaProductos;
    private ArrayList<Producto> productosCarrito;
    private Producto productoPrueba1 = new Producto("21","papel",20.5);
    private Producto productoPrueba2 = new Producto("22","madera",10);
    private double montoTotal = 0.0;

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
                Orden orden = new Orden("12",productosCarrito,montoTotal);
                String messageToInventario = convertidor.toJson(orden);
                System.out.println(messageToInventario);
                productor.enviar("orden",messageToInventario);
                System.out.println( "Mensaje: "+ messageToInventario);
                System.out.println("Enviado !");
            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                String nombre = productoTextField.getText();
                int cantidad = Integer.parseInt(cantidadTextField.getText());
                boolean encontrado = false;
                int i=0;
                while(i<productosCarrito.size() && !encontrado){
                    if(productosCarrito.get(i).getNombre().equals(nombre)){
                        int nuevaCantidad = productosCarrito.get(i).getCantidad() + cantidad;
                        montoTotal = montoTotal + productosCarrito.get(i).getPrecio() * cantidad;
                        productosCarrito.get(i).setCantidad(nuevaCantidad);
                        model.setValueAt(nuevaCantidad,i,3);
                        labelMontoTotal.setText(Double.toString(montoTotal));
                        encontrado = true;
                    }
                    i++;
                }
                if(!encontrado){
                    for(Producto producto: listaProductos){
                        if(producto.getNombre().equals(nombre)){
                            Producto nuevoProducto = new Producto(producto.getCodigo_producto(),producto.getNombre(),producto.getPrecio(),cantidad);
                            productosCarrito.add(nuevoProducto);
                            model.addRow(nuevoProducto.convertirString());
                            montoTotal = montoTotal + nuevoProducto.getPrecio() * nuevoProducto.getCantidad();
                            labelMontoTotal.setText(Double.toString(montoTotal));
                        }
                    }
                }
            }
        });

        quitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                int fila = tableProductosCarrito.getSelectedRow();
                int i =0;
                boolean encontrado=false;
                if(fila >= 0){
                    String nombreEliminar = (String) model.getValueAt(fila,1);
                    while(i<productosCarrito.size() && !encontrado){
                        if(productosCarrito.get(i).getNombre().equals(nombreEliminar)){
                            montoTotal = montoTotal - productosCarrito.get(i).getPrecio() * productosCarrito.get(i).getCantidad();
                            productosCarrito.remove(productosCarrito.get(i));
                            model.removeRow(fila);
                            labelMontoTotal.setText(Double.toString(montoTotal));
                            encontrado = true;
                        }
                        i++;
                    }
                } else {
                    i = 0;
                    encontrado = false;
                    String nombre = productoTextField.getText();
                    int cantidad = Integer.parseInt(cantidadTextField.getText());
                    while(i<productosCarrito.size() && !encontrado){
                        if(productosCarrito.get(i).getNombre().equals(nombre)){
                            int nuevaCantidad = productosCarrito.get(i).getCantidad() - cantidad;
                            montoTotal = montoTotal - productosCarrito.get(i).getPrecio() * cantidad;
                            productosCarrito.get(i).setCantidad(nuevaCantidad);
                            model.setValueAt(nuevaCantidad,i,3);
                            labelMontoTotal.setText(Double.toString(montoTotal));
                            encontrado = true;
                        }
                        i++;
                    }
                }

            }
        });
    }

    private void iniciarTablaProductos(){
        DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
        Gson convertidor = new Gson();
        //String mensajeProductos = consumidor.recibir("inventario","productoID");
        String mensajeProductos = "[{\"codigo_producto\":\"21\",\"nombre\":\"papel\",\"precio\":20.5,\"cantidad\":0},{\"codigo_producto\":\"22\",\"nombre\":\"madera\",\"precio\":10.0,\"cantidad\":0}]";
        listaProductos = convertidor.fromJson(mensajeProductos,new TypeToken<ArrayList<Producto>>() {}.getType());


        for (Producto producto: listaProductos){
            model.addRow(producto.convertirString());
        }
    }


}
