package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.io.Serializable;

// Clase base para todos los productos del kiosco
public abstract class ProductoKiosco
        implements Comparable<ProductoKiosco>, Serializable, Identificable<Integer> {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected String nombre;
    protected Marca marca;
    protected double precio;
    protected int stock;
    protected CategoriaKiosco categoria;
    protected boolean activo = true;

    // Constructor común
    public ProductoKiosco(int id, String nombre, Marca marca,
                          double precio, int stock, CategoriaKiosco categoria) {

        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // ---- Getters ----

    @Override
    public Integer getId() {
        return id;
    }

    public int getIdPrimitive() { return id; }

    public String getNombre() { return nombre; }

    public double getPrecio() { return precio; }

    public int getStock() { return stock; }

    public boolean isActivo() { return activo; }

    public Marca getMarca() { return marca; }

    public CategoriaKiosco getCategoria() { return categoria; }

    // ---- Setters ----

    public void setPrecio(double precio) { this.precio = precio; }

    public void setStock(int stock) { this.stock = stock; }

    public void setActivo(boolean activo) { this.activo = activo; }

    public void setCategoria(CategoriaKiosco categoria) { this.categoria = categoria; }

    // ---- Orden natural ----
    @Override
    public int compareTo(ProductoKiosco otro) {
        return nombre.compareToIgnoreCase(otro.nombre);
    }

    // ---- Representación para ListView ----
    @Override
    public String toString() {
        return id + " | " + nombre +
               " | $" + precio +
               " | stock: " + stock +
               " | " + marca +
               " | " + categoria;
    }

    // ---- Formato CSV ----
    public String aCsv() {
        return id + ";" +
               nombre + ";" +
               marca + ";" +
               categoria + ";" +
               precio + ";" +
               stock;
    }
}
