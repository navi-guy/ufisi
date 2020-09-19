package procesamientoOrdenes;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View extends JFrame {
    private final MessageSender productor;
    private final MessageReceiver consumidor;
    private final ArrayList<Producto> productosCarrito;
    private final Producto productoPrueba1 = new Producto("21", "papel", 20.5);
    private final Producto productoPrueba2 = new Producto("22", "madera", 10);
    private JPanel panel1;
    private JTextField productoTextField;
    private JTextField cantidadTextField;
    private JButton agregarButton;
    private JButton quitarButton;
    private JTable tableProductosCarrito;
    private JButton realizarPedidoButton;
    private JLabel labelMontoTotal;
    private JTable tableProductos;
    private List<Producto> listaProductos;
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
                new String[]{"Codigo", "Nombre", "Precio (S/.)"}
        ));
        tableProductosCarrito.setModel(new DefaultTableModel(
                null,
                new String[]{"Codigo", "Nombre", "Precio (S/.)", "Cantidad"}
        ));
        iniciarTablaProductos();

        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(productosCarrito.size() > 0){
                    Gson convertidor = new Gson();
                    Orden orden = new Orden("12", productosCarrito, montoTotal);
                    String messageToInventario = convertidor.toJson(orden);
                    productor.enviar(messageToInventario);
                    System.out.println("Mensaje: " + messageToInventario);
                    System.out.println("Enviado !");
                } else{
                    JOptionPane.showMessageDialog(null,"Mano no seas idiota llena algo primero");
                }

            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                String nombre = productoTextField.getText();
                int cantidad = Integer.parseInt(cantidadTextField.getText());
                boolean encontrado = false;
                int i = 0;
                while (i < productosCarrito.size() && !encontrado) {
                    if (productosCarrito.get(i).getNombre().equals(nombre)) {
                        int nuevaCantidad = productosCarrito.get(i).getCantidad() + cantidad;
                        montoTotal = Math.round(montoTotal + productosCarrito.get(i).getPrecio() * cantidad);
                        productosCarrito.get(i).setCantidad(nuevaCantidad);
                        model.setValueAt(nuevaCantidad, i, 3);
                        labelMontoTotal.setText(Double.toString(montoTotal));
                        encontrado = true;
                    }
                    i++;
                }
                if (!encontrado) {
                    for (Producto producto : listaProductos) {
                        if (producto.getNombre().equals(nombre)) {
                            Producto nuevoProducto = new Producto(producto.getCodigo_producto(), producto.getNombre(), producto.getPrecio(), cantidad);
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
                int i = 0;
                boolean encontrado = false;
                if (fila >= 0) {
                    String nombreEliminar = (String) model.getValueAt(fila, 1);
                    while (i < productosCarrito.size() && !encontrado) {
                        if (productosCarrito.get(i).getNombre().equals(nombreEliminar)) {
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
                    while (i < productosCarrito.size() && !encontrado) {
                        if (productosCarrito.get(i).getNombre().equals(nombre)) {
                            int nuevaCantidad = productosCarrito.get(i).getCantidad() - cantidad;
                            montoTotal = montoTotal - productosCarrito.get(i).getPrecio() * cantidad;
                            productosCarrito.get(i).setCantidad(nuevaCantidad);
                            model.setValueAt(nuevaCantidad, i, 3);
                            labelMontoTotal.setText(Double.toString(montoTotal));
                            encontrado = true;
                        }
                        i++;
                    }
                }

            }
        });
    }

    private void iniciarTablaProductos() {
        DefaultTableModel model = (DefaultTableModel) tableProductos.getModel();
        listaProductos = Arrays.asList(new Producto("1", "silicona", 3.10),
                new Producto("2", "borrador", 1.80),
                new Producto("3", "tijera", 2.10),
                new Producto("4", "boligrafo", 2.90),
                new Producto("5", "folder", 4.80),
                new Producto("6", "lapicero", 4.10),
                new Producto("7", "resaltador", 4.20),
                new Producto("8", "compas", 5.20),
                new Producto("9", "tajador", 1.00));
        for (Producto producto : listaProductos) {
            model.addRow(producto.convertirString());
        }
    }


}
