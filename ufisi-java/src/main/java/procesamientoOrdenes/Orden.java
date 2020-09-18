package procesamientoOrdenes;

import java.util.ArrayList;

public class Orden {

    private String id_cliente;
    private ArrayList<Producto> productos;

    public Orden(String id_cliente, ArrayList<Producto> productos) {
        this.id_cliente = id_cliente;
        this.productos = productos;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
