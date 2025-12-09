package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.time.LocalDate;

// Representa un snack del kiosco
public class Snack extends ProductoKiosco {

    private int gramos;
    private boolean aptoCeliacos;
    private LocalDate vencimiento;

    // Constructor completo
    public Snack(int id, String nombre, Marca marca, double precio,
                 int stock, int gramos, boolean aptoCeliacos, LocalDate vencimiento) {

        super(id, nombre, marca, precio, stock, CategoriaKiosco.SNACK);

        this.gramos = gramos;
        this.aptoCeliacos = aptoCeliacos;
        this.vencimiento = vencimiento;
    }

    // Constructor usado en cargas simples (CSV/JSON)
    public Snack(int id, String nombre, Marca marca, double precio, int stock) {
        super(id, nombre, marca, precio, stock, CategoriaKiosco.SNACK);

        this.gramos = 100;
        this.aptoCeliacos = false;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}
