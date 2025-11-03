/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.time.LocalDate;

public class Snack extends ProductoKiosco {

    private int gramos;
    private boolean aptoCeliacos;
    private LocalDate vencimiento;

    public Snack(int id, String nombre, Marca marca, double precio,
                 int stock, int gramos, boolean aptoCeliacos, LocalDate vencimiento) {
        super(id, nombre, marca, precio, stock, CategoriaKiosco.SNACK);
        this.gramos = gramos;
        this.aptoCeliacos = aptoCeliacos;
        this.vencimiento = vencimiento;
    }

    public Snack(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, CategoriaKiosco.SNACK);
        this.gramos = 100;
        this.aptoCeliacos = false;
        this.vencimiento = LocalDate.now().plusMonths(8);
    }
}

