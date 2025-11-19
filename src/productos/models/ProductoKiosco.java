package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.io.Serializable;  // para poder serializar si se quiere binario

public abstract class ProductoKiosco implements Comparable<ProductoKiosco>, Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected String nombre;
    protected Marca marca;
    protected double precio;
    protected int stock;
    protected CategoriaKiosco categoria;
    protected boolean activo = true;

    public ProductoKiosco(int id, String nombre, Marca marca, double precio,
                          int stock, CategoriaKiosco categoria) {

        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    public boolean isActivo() { return activo; }

    public void setPrecio(double precio) { this.precio = precio; }
    public void setStock(int stock) { this.stock = stock; }
    public void setActivo(boolean activo) { this.activo = activo; }

    // Ordenamiento natural (por nombre)
    @Override
    public int compareTo(ProductoKiosco otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    @Override
    public String toString() {
        return nombre + " | $" + precio + " | stock: " + stock;
    }

    // ----------------------------------------------------
    // PUNTO 6 - ayuda para CSV
    // Formato: id;nombre;precio;stock
    // ----------------------------------------------------
    public String aCsv() {
        return getId() + ";" + getNombre() + ";" + getPrecio() + ";" + getStock();
    }
}
