package procesamientoOrdenes;

import java.util.ArrayList;

public class Orden {

    private String id_cliente;
    private ArrayList<Producto> productos;
    private double total_bruto;

    public Orden(String id_cliente, ArrayList<Producto> productos, double total_bruto) {
        this.id_cliente = id_cliente;
        this.productos = productos;
        this.total_bruto = total_bruto;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public double getTotal_bruto() {
        return total_bruto;
    }

    public void setTotal_bruto(double total_bruto) {
        this.total_bruto = total_bruto;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
}
