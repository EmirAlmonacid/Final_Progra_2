package productos.models;

import java.util.*;
import java.util.function.*;

// Excepciones simples para validaciones
class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String msg) { super(msg); }
}

class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String msg) { super(msg); }
}

// Maneja la colección principal de productos
public class GestionProductos implements ICRUD<ProductoKiosco, Integer>, Iterable<ProductoKiosco> {

    private List<ProductoKiosco> lista = new ArrayList<>();

    // ---- CRUD ----

    @Override
    public boolean crear(ProductoKiosco p) {
        if (leerPorId(p.getId()) != null) return false;
        lista.add(p);
        return true;
    }

    @Override
    public ProductoKiosco leerPorId(Integer id) {
        for (ProductoKiosco p : lista) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    @Override
    public List<ProductoKiosco> leerTodos() {
        return new ArrayList<>(lista);
    }

    @Override
    public boolean actualizar(ProductoKiosco entidad) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == entidad.getId()) {
                lista.set(i, entidad);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminarPorId(Integer id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.remove(i);
                return true;
            }
        }
        return false;
    }

    // ---- Ordenamientos ----

    public void ordenarPorNombre() {
        Collections.sort(lista);
    }

    public void ordenarPorPrecio() {
        Collections.sort(lista, (a, b) -> Double.compare(a.getPrecio(), b.getPrecio()));
    }

    public void ordenarPorStock() {
        Collections.sort(lista, (a, b) -> Integer.compare(a.getStock(), b.getStock()));
    }

    // ---- Filtros simples ----

    public List<ProductoKiosco> filtrarPorNombre(String texto) {
        List<ProductoKiosco> salida = new ArrayList<>();
        String t = texto.toLowerCase();

        for (ProductoKiosco p : lista) {
            if (p.getNombre().toLowerCase().contains(t)) salida.add(p);
        }
        return salida;
    }

    public List<ProductoKiosco> filtrarPorPrecioMax(double max) {
        List<ProductoKiosco> salida = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            if (p.getPrecio() <= max) salida.add(p);
        }
        return salida;
    }

    // ---- Interfaces funcionales ----

    public void paraCada(Consumer<ProductoKiosco> accion) {
        for (ProductoKiosco p : lista) accion.accept(p);
    }

    public void actualizarSi(Predicate<ProductoKiosco> cond, Consumer<ProductoKiosco> accion) {
        for (ProductoKiosco p : lista) {
            if (cond.test(p)) accion.accept(p);
        }
    }

    public void eliminarSi(Predicate<ProductoKiosco> cond) {
        for (int i = 0; i < lista.size(); i++) {
            if (cond.test(lista.get(i))) {
                lista.remove(i);
                i--;
            }
        }
    }

    public <R> List<R> transformar(Function<ProductoKiosco, R> fun) {
        List<R> salida = new ArrayList<>();
        for (ProductoKiosco p : lista) salida.add(fun.apply(p));
        return salida;
    }

    public void reemplazarTodos(UnaryOperator<ProductoKiosco> op) {
        for (int i = 0; i < lista.size(); i++) {
            lista.set(i, op.apply(lista.get(i)));
        }
    }

    // ---- Iteración ----

    @Override
    public Iterator<ProductoKiosco> iterator() {
        return lista.iterator();
    }

    public void recorrerConIteradorManual() {
        Iterator<ProductoKiosco> it = iterator();
        while (it.hasNext()) System.out.println(it.next());
    }

    // ---- Wildcards ----

    public void imprimirNombres(List<? extends ProductoKiosco> l) {
        for (ProductoKiosco p : l) System.out.println(p.getNombre());
    }

    public void agregarALista(List<? super ProductoKiosco> l, ProductoKiosco p) {
        l.add(p);
    }

    // ---- Excepciones ----

    public ProductoKiosco buscarObligatorio(int id) {
        ProductoKiosco p = leerPorId(id);
        if (p == null) throw new ProductoNoEncontradoException("ID no encontrado: " + id);
        return p;
    }

    public void validarPrecio(double precio) {
        if (precio < 0) throw new DatoInvalidoException("Precio inválido");
    }

    // ---- Persistencia ----

    public void guardarCsv(String archivo) { PersistenciaProductos.guardarCsv(lista, archivo); }
    public void cargarCsv(String archivo) { PersistenciaProductos.cargarCsv(lista, archivo); }
    public void guardarJson(String archivo) { PersistenciaProductos.guardarJson(lista, archivo); }
    public void cargarJson(String archivo) { PersistenciaProductos.cargarJson(lista, archivo); }
    public void exportarTxtFiltrado(String archivo, double max) {
        PersistenciaProductos.exportarTxtFiltrado(lista, archivo, max);
    }

    // ---- Depuración ----

    public void mostrar() {
        for (ProductoKiosco p : lista) System.out.println(p);
    }
}
