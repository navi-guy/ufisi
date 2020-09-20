package procesamientoOrdenes;

import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class View extends JFrame {
    private final MessageSender productor;
    private final MessageReceiver consumidor;
    private final ArrayList<Producto> productosCarrito;
    private JPanel panel1;
    private JTextField productoTextField;
    private JTextField cantidadTextField;
    private JButton agregarButton;
    private JButton quitarButton;
    private JTable tableProductosCarrito;
    private JButton realizarPedidoButton;
    private JLabel labelMontoTotal;
    private JTable tableProductos;
    private JPanel JPanelTabla;
    private JScrollPane JScroll;
    private List<Producto> listaProductos;
    private double montoTotal = 0.0;
    private JTable tablitaConImagen;

    public View() {
        super("Modulo Productos");
        this.setLocationRelativeTo(null);
        setContentPane(panel1);
        productor = new MessageSender();
        consumidor = new MessageReceiver();
        productosCarrito = new ArrayList<>();
        listaProductos = new ArrayList<>();

        tableProductos.setModel(new DefaultTableModel(
                null,
                new String[]{"Imagen", "Codigo", "Nombre", "Precio (S/.)"}
        ));
        tableProductosCarrito.setModel(new DefaultTableModel(
                null,
                new String[]{"Codigo", "Nombre", "Precio (S/.)", "Cantidad"}
        ));
        iniciarTablaProductos();

        realizarPedidoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (productosCarrito.size() > 0) {
                    Gson convertidor = new Gson();
                    Orden orden = new Orden("12", productosCarrito, montoTotal);
                    String messageToInventario = convertidor.toJson(orden);
                    productor.enviar(messageToInventario);
                    System.out.println("Mensaje: " + messageToInventario);
                    System.out.println("Enviado !");
                } else {
                    JOptionPane.showMessageDialog(null, "El carrito está vacío, ingrese un producto antes de hacer el pedido","Alerta", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) tableProductosCarrito.getModel();
                String nombre = productoTextField.getText();
                String cantidadStr = cantidadTextField.getText();
                if (!cantidadStr.isEmpty() && cantidadStr.matches("^[1-9]\\d*$")) {
                    int cantidad = Integer.parseInt(cantidadStr);
                    boolean encontrado = false;
                    int i = 0;
                    while (i < productosCarrito.size() && !encontrado) {
                        if (productosCarrito.get(i).getNombre().equals(nombre)) {
                            int nuevaCantidad = productosCarrito.get(i).getCantidad() + cantidad;
                            montoTotal = montoTotal + productosCarrito.get(i).getPrecio() * cantidad;
                            productosCarrito.get(i).setCantidad(nuevaCantidad);
                            model.setValueAt(nuevaCantidad, i, 3);
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

                            }
                        }
                    }
                    montoTotal = Math.round(montoTotal * 100.00) / 100.00;
                    labelMontoTotal.setText(Double.toString(montoTotal));
                } else {
                    JOptionPane.showMessageDialog(null,"Ingrese una cantidad valida", "Alerta", JOptionPane.WARNING_MESSAGE);
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
                            encontrado = true;
                        }
                        i++;
                    }
                }
                montoTotal = Math.round(montoTotal * 100.00) / 100.00;
                labelMontoTotal.setText(Double.toString(montoTotal));
            }
        });
        tablitaConImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablitaConImagen.rowAtPoint(evt.getPoint());
                int col = tablitaConImagen.columnAtPoint(evt.getPoint());
                if (row >= 50 && col >= 4) {
                    System.out.println("Seleccione un a fila correcta");
                } else {
                    productoTextField.setText(listaProductos.get(row).getNombre());
                    cantidadTextField.setText("0");

                }
            }
        });
        tableProductosCarrito.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tableProductosCarrito.rowAtPoint(evt.getPoint());
                int col = tableProductosCarrito.columnAtPoint(evt.getPoint());
                if (row >= 50 && col >= 4) {
                    System.out.println("Seleccione un a fila correcta");
                } else {
                    productoTextField.setText(productosCarrito.get(row).getNombre());
                    cantidadTextField.setText("0");
                }
            }
        });
    }

    private void iniciarTablaProductos() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class getColumnClass(int column) {
                if (column == 0) return ImageIcon.class;
                return Object.class;
            }
        };
        model.setColumnIdentifiers(new String[]{"Imagen", "Codigo", "Nombre", "Precio (S/.)"});
        listaProductos = Arrays.asList(new Producto("1", "Silicona", 3.10, new ImageIcon(getClass().getResource("/images/glue.png"))),
                new Producto("2", "Borrador", 1.80, new ImageIcon(getClass().getResource("/images/eraser.png"))),
                new Producto("3", "Tijera", 2.10, new ImageIcon(getClass().getResource("/images/scissors.png"))),
                new Producto("4", "Boligrafo", 2.90, new ImageIcon(getClass().getResource("/images/fountain-pen.png"))),
                new Producto("5", "Folder", 4.80, new ImageIcon(getClass().getResource("/images/folder.png"))),
                new Producto("6", "Lapicero", 4.10, new ImageIcon(getClass().getResource("/images/pen.png"))),
                new Producto("7", "Resaltador", 4.20, new ImageIcon(getClass().getResource("/images/marker.png"))),
                new Producto("8", "Compas", 5.20, new ImageIcon(getClass().getResource("/images/compass.png"))),
                new Producto("9", "Tajador", 1.00, new ImageIcon(getClass().getResource("/images/pencil-sharpener.png"))));
        int i = 0;
        for (Producto productoI : listaProductos) {
            model.addRow(new String[]{"Imagen Referencial", productoI.getCodigo_producto(), productoI.getNombre(), Double.toString(productoI.getPrecio())});
            model.setValueAt(productoI.getImagen(), i, 0);
            i++;
        }
        this.tablitaConImagen = new JTable(model);
        this.tablitaConImagen.setPreferredScrollableViewportSize(this.JScroll.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(this.tablitaConImagen);
        this.tablitaConImagen.setRowHeight(72);
        JPanelTabla.setLayout(new GridBagLayout());
        JPanelTabla.remove(this.JScroll);
        JPanelTabla.add(scrollPane);
    }


}
