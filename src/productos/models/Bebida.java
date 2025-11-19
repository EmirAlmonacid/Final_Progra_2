package productos.models;

import enums.CategoriaKiosco;
import enums.Marca;
import java.time.LocalDate;

public class Bebida extends ProductoKiosco {

    private int tamanioMl;
    private boolean conAzucar;
    private LocalDate vencimiento;

    // Constructor completo
    public Bebida(int id, String nombre, Marca marca, double precio,
                  int stock, int tamanioMl, boolean conAzucar, LocalDate vencimiento) {

        super(id, nombre, marca, precio, stock, CategoriaKiosco.BEBIDA);

        this.tamanioMl = tamanioMl;
        this.conAzucar = conAzucar;
        this.vencimiento = vencimiento;
    }

    // Constructor corto (predefinido)
    public Bebida(int id, String nombre, Marca marca, double precio) {
        super(id, nombre, marca, precio, 10, CategoriaKiosco.BEBIDA); // stock por defecto

        this.tamanioMl = 500;
        this.conAzucar = true;
        this.vencimiento = LocalDate.now().plusMonths(6);
    }
}
