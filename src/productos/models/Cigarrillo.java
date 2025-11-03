/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;

public class Cigarrillo extends ProductoKiosco {

    private String tipo;
    private int cantidadPorPaquete;
    private boolean conFiltro;

    public Cigarrillo(int id, String nombre, Marca marca, double precio,
                      int stock, String tipo, int cantidadPorPaquete, boolean conFiltro) {
        super(id, nombre, marca, precio, stock, CategoriaKiosco.CIGARRILLO);
        this.tipo = tipo;
        this.cantidadPorPaquete = cantidadPorPaquete;
        this.conFiltro = conFiltro;
    }

    public Cigarrillo(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, CategoriaKiosco.CIGARRILLO);
        this.tipo = "box";
        this.cantidadPorPaquete = 20;
        this.conFiltro = true;
    }
}

