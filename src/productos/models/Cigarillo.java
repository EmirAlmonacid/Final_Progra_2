package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;

// Representa un paquete de cigarrillos
public class Cigarillo extends ProductoKiosco {

    private String tipo;            // box, soft, etc.
    private int cantidadPorPaquete; // unidades
    private boolean conFiltro;

    // Constructor completo
    public Cigarillo(int id, String nombre, Marca marca, double precio,
                     int stock, String tipo, int cantidadPorPaquete, boolean conFiltro) {

        super(id, nombre, marca, precio, stock, CategoriaKiosco.CIGARRILLO);

        this.tipo = tipo;
        this.cantidadPorPaquete = cantidadPorPaquete;
        this.conFiltro = conFiltro;
    }

    // Constructor con valores por defecto
    public Cigarillo(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 20, CategoriaKiosco.CIGARRILLO);

        this.tipo = "box";
        this.cantidadPorPaquete = 20;
        this.conFiltro = true;
    }

    // Getters y setters
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidadPorPaquete() { return cantidadPorPaquete; }
    public void setCantidadPorPaquete(int cantidadPorPaquete) { this.cantidadPorPaquete = cantidadPorPaquete; }

    public boolean isConFiltro() { return conFiltro; }
    public void setConFiltro(boolean conFiltro) { this.conFiltro = conFiltro; }

    // Representación en listas
    @Override
    public String toString() {
        return nombre + " | $" + precio +
               " | Stock: " + stock +
               " | " + tipo +
               " | " + cantidadPorPaquete + "u" +
               " | Filtro: " + (conFiltro ? "Sí" : "No");
    }
}
