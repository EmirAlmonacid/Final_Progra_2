package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.time.LocalDate;

/**
 * Producto tipo Snack del kiosco.
 * Extiende ProductoKiosco y agrega gramos, aptoCeliacos y vencimiento.
 */
public class Snack extends ProductoKiosco {

    private int gramos;
    private boolean aptoCeliacos;
    private LocalDate vencimiento;

    // Constructor completo
    public Snack(int id, String nombre, Marca marca, double precio,
                 int stock, int gramos, boolean aptoCeliacos, LocalDate vencimiento) {

        // Categoria fija: SNACK
        super(id, nombre, marca, precio, stock, CategoriaKiosco.SNACK);

        this.gramos = gramos;
        this.aptoCeliacos = aptoCeliacos;
        this.vencimiento = vencimiento;
    }

    // Constructor simple (usado para CSV/JSON)
    public Snack(int id, String nombre, Marca marca, double precio, int stock) {
        super(id, nombre, marca, precio, stock, CategoriaKiosco.SNACK);
        this.gramos = 100;                         // valores por defecto
        this.aptoCeliacos = false;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}
