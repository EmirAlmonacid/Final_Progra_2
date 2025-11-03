/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.time.LocalDate;

public class Bebida extends ProductoKiosco {

    private int tamanioMl;
    private boolean conAzucar;
    private LocalDate vencimiento;

    public Bebida(int id, String nombre, Marca marca, double precio,
                  int stock, int tamanioMl, boolean conAzucar, LocalDate vencimiento) {
        super(id, nombre, marca, precio, stock, CategoriaKiosco.BEBIDA);
        this.tamanioMl = tamanioMl;
        this.conAzucar = conAzucar;
        this.vencimiento = vencimiento;
    }

    public Bebida(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, CategoriaKiosco.BEBIDA);
        this.tamanioMl = 500;
        this.conAzucar = true;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}

