package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.io.Serializable;  // para poder serializar si se quiere binario

/**
 * Clase base de cualquier producto del kiosco.
 * De acá heredan Snack, Bebida, Cigarillo, etc.
 */
public abstract class ProductoKiosco
        implements Comparable<ProductoKiosco>, Serializable, Identificable<Integer> {

    private static final long serialVersionUID = 1L;

    // -------------------------
    // Atributos comunes
    // -------------------------
    protected int id;
    protected String nombre;
    protected Marca marca;
    protected double precio;
    protected int stock;
    protected CategoriaKiosco categoria;
    protected boolean activo = true;

    /**
     * Constructor básico con todos los datos que comparten los productos.
     */
    public ProductoKiosco(int id,
                          String nombre,
                          Marca marca,
                          double precio,
                          int stock,
                          CategoriaKiosco categoria) {

        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // -------------------------
    // Getters (lectura)
    // -------------------------

    // Implementa la interfaz Identificable<Integer>
    // (el int se autoboxea a Integer)
    @Override
    public Integer getId() {
        return id;
    }

    // Si querés dejar también la versión primitiva:
    // (no rompe nada, solo es azúcar sintáctica)
    public int getIdPrimitive() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public boolean isActivo() {
        return activo;
    }

    // NUEVOS getters (para usar Marca y Categoria en CSV/JSON/TXT)
    public Marca getMarca() {
        return marca;
    }

    public CategoriaKiosco getCategoria() {
        return categoria;
    }

    // -------------------------
    // Setters (modificación)
    // -------------------------

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Setter de categoría.
     * Lo usamos desde MainFX para cambiar la categoría del producto.
     */
    public void setCategoria(CategoriaKiosco categoria) {
        this.categoria = categoria;
    }

    // -------------------------
    // Orden natural por nombre
    // -------------------------

    @Override
    public int compareTo(ProductoKiosco otro) {
        // Compara por nombre ignorando mayúsculas/minúsculas
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    // -------------------------
    // Representación en texto
    // -------------------------

    /**
     * Este texto es lo que se muestra en la ListView.
     * Incluye el ID como primer dato.
     */
    @Override
    public String toString() {
        return id + " | " + nombre +
               " | $" + precio +
               " | stock: " + stock +
               " | " + marca +
               " | " + categoria;
    }

    // ----------------------------------------------------
    // PUNTO 6 - ayuda para CSV
    // Formato: id;nombre;marca;categoria;precio;stock
    // ----------------------------------------------------
    public String aCsv() {
        return getId() + ";" +
               getNombre() + ";" +
               getMarca() + ";" +
               getCategoria() + ";" +
               getPrecio() + ";" +
               getStock();
    }
}
