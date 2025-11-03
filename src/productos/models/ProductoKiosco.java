/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.io.Serializable;


public abstract class ProductoKiosco implements Comparable<ProductoKiosco>, Serializable {

 
    protected int id;
    protected String nombre;
    protected Marca marca;
    protected double precio;
    protected int stock;
    protected CategoriaKiosco categoria;


    public ProductoKiosco(int id, String nombre, Marca marca, double precio,
                          int stock, CategoriaKiosco categoria) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }


    public ProductoKiosco(int id, String nombre, Marca marca, double precio, CategoriaKiosco categoria) {
        this(id, nombre, marca, precio, 0, categoria);
    }

    public ProductoKiosco(int id, String nombre, Marca marca, double precio) {
        this(id, nombre, marca, precio, 0, CategoriaKiosco.SNACK);
    }
    
    
    
     // ====== COMPORTAMIENTOS COMUNES ======

    /** Aplica un descuento porcentual sobre el precio del producto. */
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) return;
        double nuevo = precio * (1.0 - porcentaje / 100.0);
        this.precio = Math.max(0, nuevo);
    }

    /**  Devuelve una descripción legible del producto (marca + nombre + categoría). */
    public String getDescripcion() {
    return marca + " " + nombre + " [" + categoria + "]";
}


    /**  Calcula el precio final del producto (cada subclase define su forma). */
    public double calcularPrecioFinal() {
        return precio * 1.21;
    }
    
    @Override
    public int compareTo(ProductoKiosco otro) {
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }
    
}
