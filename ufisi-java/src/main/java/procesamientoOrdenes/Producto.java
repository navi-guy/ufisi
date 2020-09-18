package procesamientoOrdenes;

public class Producto {

    private String codigo_producto;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String codigo_producto, String nombre, double precio, int cantidad) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Producto(String codigo_producto, String nombre, double precio) {
        this.codigo_producto = codigo_producto;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo_producto() {
        return codigo_producto;
    }

    public void setCodigo_producto(String codigo_producto) {
        this.codigo_producto = codigo_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String[] convertirString() {
        return new String[]{codigo_producto, nombre, Double.toString(precio),Integer.toString(cantidad)};
    }
}
