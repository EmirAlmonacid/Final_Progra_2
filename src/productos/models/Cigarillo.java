package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;

public class Cigarillo extends ProductoKiosco {

    // Atributos propios
    private String tipo;              // ej: "box", "soft"
    private int cantidadPorPaquete;   // ej: 20
    private boolean conFiltro;        // true/false

    // ------------------------------
    // Constructor COMPLETO
    // ------------------------------
    public Cigarillo(int id, String nombre, Marca marca, double precio,
                     int stock, String tipo, int cantidadPorPaquete, boolean conFiltro) {

        // Llama al constructor de la clase base (IMPORTANTE: incluye stock)
        super(id, nombre, marca, precio, stock, CategoriaKiosco.CIGARRILLO);

        this.tipo = tipo;
        this.cantidadPorPaquete = cantidadPorPaquete;
        this.conFiltro = conFiltro;
    }

    // ------------------------------
    // Constructor CORTO (valores por defecto)
    // ------------------------------
    public Cigarillo(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 20, CategoriaKiosco.CIGARRILLO); // stock default = 20

        this.tipo = "box";
        this.cantidadPorPaquete = 20;
        this.conFiltro = true;
    }

    // ------------------------------
    // Getters y Setters
    // ------------------------------
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidadPorPaquete() { return cantidadPorPaquete; }
    public void setCantidadPorPaquete(int cantidadPorPaquete) {
        this.cantidadPorPaquete = cantidadPorPaquete;
    }

    public boolean isConFiltro() { return conFiltro; }
    public void setConFiltro(boolean conFiltro) { this.conFiltro = conFiltro; }

    // ------------------------------
    // toString para mostrar en listados
    // ------------------------------
    @Override
    public String toString() {
        return nombre + " | $" + precio +
               " | Stock: " + stock +
               " | " + tipo +
               " | " + cantidadPorPaquete + "u" +
               " | Filtro: " + (conFiltro ? "Sí" : "No");
    }
}
