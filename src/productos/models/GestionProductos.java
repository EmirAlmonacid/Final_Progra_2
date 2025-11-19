package productos.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class GestionProductos implements ICRUD<ProductoKiosco, Integer>, Iterable<ProductoKiosco> {

    private List<ProductoKiosco> lista = new ArrayList<>();

    // ----------------------
    // CRUD
    // ----------------------
    @Override
    public boolean crear(ProductoKiosco entidad) {
        if (leerPorId(entidad.getId()) != null) return false; // no duplicar ID
        lista.add(entidad);
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

    // ----------------------
    // ORDENAMIENTOS (PUNTO 4)
    // ----------------------
    public void ordenarPorNombre() {
        Collections.sort(lista);
    }

    public void ordenarPorPrecio() {
        Collections.sort(lista, new Comparator<ProductoKiosco>() {
            @Override
            public int compare(ProductoKiosco p1, ProductoKiosco p2) {
                return Double.compare(p1.getPrecio(), p2.getPrecio());
            }
        });
    }

    public void ordenarPorStock() {
        Collections.sort(lista, new Comparator<ProductoKiosco>() {
            @Override
            public int compare(ProductoKiosco p1, ProductoKiosco p2) {
                return Integer.compare(p1.getStock(), p2.getStock());
            }
        });
    }

    // ----------------------
    // FILTROS (PUNTO 4)
    // ----------------------
    public List<ProductoKiosco> filtrar(Predicate<ProductoKiosco> condicion) {
        List<ProductoKiosco> resultado = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            if (condicion.test(p)) resultado.add(p);
        }
        return resultado;
    }

    public List<ProductoKiosco> filtrarPorNombre(String texto) {
        final String t = texto.toLowerCase();
        return filtrar(new Predicate<ProductoKiosco>() {
            @Override
            public boolean test(ProductoKiosco p) {
                return p.getNombre().toLowerCase().contains(t);
            }
        });
    }

    public List<ProductoKiosco> filtrarPorPrecioMax(double max) {
        return filtrar(new Predicate<ProductoKiosco>() {
            @Override
            public boolean test(ProductoKiosco p) {
                return p.getPrecio() <= max;
            }
        });
    }

    // ----------------------
    // PUNTO 5 – Interfaces funcionales
    // ----------------------
    public void paraCada(Consumer<ProductoKiosco> accion) {
        for (ProductoKiosco p : lista) {
            accion.accept(p);
        }
    }

    public void actualizarSi(Predicate<ProductoKiosco> condicion, Consumer<ProductoKiosco> accion) {
        for (ProductoKiosco p : lista) {
            if (condicion.test(p)) {
                accion.accept(p);
            }
        }
    }

    public void eliminarSi(Predicate<ProductoKiosco> condicion) {
        for (int i = 0; i < lista.size(); i++) {
            if (condicion.test(lista.get(i))) {
                lista.remove(i);
                i--;
            }
        }
    }

    public <R> List<R> transformar(Function<ProductoKiosco, R> funcion) {
        List<R> salida = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            R valor = funcion.apply(p);
            salida.add(valor);
        }
        return salida;
    }

    public void reemplazarTodos(UnaryOperator<ProductoKiosco> operador) {
        for (int i = 0; i < lista.size(); i++) {
            ProductoKiosco original = lista.get(i);
            ProductoKiosco nuevo = operador.apply(original);
            lista.set(i, nuevo);
        }
    }

    // ----------------------
    // Iterador simple
    // ----------------------
    @Override
    public Iterator<ProductoKiosco> iterator() {
        return lista.iterator();
    }

    public void mostrar() {
        for (ProductoKiosco p : lista) {
            System.out.println(p);
        }
    }

    // =====================================================
    // PUNTO 6 – PERSISTENCIA (DELEGA EN PersistenciaProductos)
    // =====================================================

    public void guardarCsv(String nombreArchivo) {
        PersistenciaProductos.guardarCsv(lista, nombreArchivo);
    }

    public void cargarCsv(String nombreArchivo) {
        PersistenciaProductos.cargarCsv(lista, nombreArchivo);
    }

    public void guardarJson(String nombreArchivo) {
        PersistenciaProductos.guardarJson(lista, nombreArchivo);
    }

    public void cargarJson(String nombreArchivo) {
        PersistenciaProductos.cargarJson(lista, nombreArchivo);
    }

    public void exportarTxtFiltrado(String nombreArchivo, double precioMaximo) {
        PersistenciaProductos.exportarTxtFiltrado(lista, nombreArchivo, precioMaximo);
    }
}
