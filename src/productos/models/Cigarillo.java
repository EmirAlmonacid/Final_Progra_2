package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;

/**
 * Producto tipo Cigarillo.
 */
public class Cigarillo extends ProductoKiosco {

    // Atributos propios
    private String tipo;            // ej: "box", "soft"
    private int cantidadPorPaquete; // ej: 20
    private boolean conFiltro;      // true/false

    // Constructor completo
    public Cigarillo(int id, String nombre, Marca marca, double precio,
                     int stock, String tipo, int cantidadPorPaquete, boolean conFiltro) {

        // Categoria fija: CIGARRILLO
        super(id, nombre, marca, precio, stock, CategoriaKiosco.CIGARRILLO);

        this.tipo = tipo;
        this.cantidadPorPaquete = cantidadPorPaquete;
        this.conFiltro = conFiltro;
    }

    // Constructor corto (valores por defecto)
    public Cigarillo(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 20, CategoriaKiosco.CIGARRILLO); // stock default = 20

        this.tipo = "box";
        this.cantidadPorPaquete = 20;
        this.conFiltro = true;
    }

    // Getters y setters simples
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidadPorPaquete() { return cantidadPorPaquete; }
    public void setCantidadPorPaquete(int cantidadPorPaquete) {
        this.cantidadPorPaquete = cantidadPorPaquete;
    }

    public boolean isConFiltro() { return conFiltro; }
    public void setConFiltro(boolean conFiltro) { this.conFiltro = conFiltro; }

    // Para mostrar en listados
    @Override
    public String toString() {
        return nombre + " | $" + precio +
               " | Stock: " + stock +
               " | " + tipo +
               " | " + cantidadPorPaquete + "u" +
               " | Filtro: " + (conFiltro ? "Sí" : "No");
    }
}
