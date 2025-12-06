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

// ================================================================
// PUNTO 9 – Excepciones propias
// ================================================================
class ProductoNoEncontradoException extends RuntimeException {
    public ProductoNoEncontradoException(String msg) {
        super(msg);
    }
}

class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String msg) {
        super(msg);
    }
}

/**
 * Clase que gestiona la lista de productos del kiosco.
 * Implementa:
 * - CRUD
 * - Ordenamientos
 * - Filtros
 * - Interfaces funcionales
 * - Wildcards
 * - Excepciones propias
 * - Persistencia delegada
 * - Iterable (para foreach)
 */
public class GestionProductos implements ICRUD<ProductoKiosco, Integer>, Iterable<ProductoKiosco> {

    // Lista interna de productos
    private List<ProductoKiosco> lista = new ArrayList<>();

    // =====================================================
    // 1. CRUD BÁSICO
    // =====================================================

    @Override
    public boolean crear(ProductoKiosco entidad) {
        if (leerPorId(entidad.getId()) != null) {
            return false;
        }
        lista.add(entidad);
        return true;
    }

    @Override
    public ProductoKiosco leerPorId(Integer id) {
        for (ProductoKiosco p : lista) {
            if (p.getId() == id) {
                return p;
            }
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

    // =====================================================
    // 2. ORDENAMIENTOS
    // =====================================================

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

    // =====================================================
    // 3. FILTROS PRINCIPIANTES
    // =====================================================

    public List<ProductoKiosco> filtrarPorNombre(String texto) {
        List<ProductoKiosco> resultado = new ArrayList<>();
        String buscado = texto.toLowerCase();

        for (ProductoKiosco p : lista) {
            if (p.getNombre().toLowerCase().contains(buscado)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public List<ProductoKiosco> filtrarPorPrecioMax(double max) {
        List<ProductoKiosco> resultado = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            if (p.getPrecio() <= max) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    // =====================================================
    // 4. INTERFACES FUNCIONALES
    // =====================================================

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
            salida.add(funcion.apply(p));
        }
        return salida;
    }

    public void reemplazarTodos(UnaryOperator<ProductoKiosco> operador) {
        for (int i = 0; i < lista.size(); i++) {
            lista.set(i, operador.apply(lista.get(i)));
        }
    }

    // =====================================================
    // 5. ITERABLE → permite usar foreach
    // =====================================================

    @Override
    public Iterator<ProductoKiosco> iterator() {
        // simplemente devolvemos el iterador de la lista interna
        return lista.iterator();
    }

    // =====================================================
    // ⭐ NUEVO MÉTODO DEMOSTRATIVO (con hasNext()) ⭐
    // =====================================================
    /**
     * Recorre la lista usando explícitamente el iterador con hasNext(),
     * para demostrar cómo funciona la lógica que usa también el foreach.
     */
    public void recorrerConIteradorManual() {

        System.out.println("Recorriendo productos usando hasNext():");

        Iterator<ProductoKiosco> it = iterator(); // usa iterator() de arriba

        while (it.hasNext()) {        // mientras haya elementos sin recorrer
            ProductoKiosco p = it.next();  // obtengo el siguiente
            System.out.println(p);         // muestro el producto
        }
    }

    // =====================================================
    // 6. WILDCARDS
    // =====================================================

    public void imprimirNombres(List<? extends ProductoKiosco> productos) {
        for (ProductoKiosco p : productos) {
            System.out.println(p.getNombre());
        }
    }

    public void agregarALista(List<? super ProductoKiosco> productos, ProductoKiosco p) {
        productos.add(p);
    }

    // =====================================================
    // 7. EXCEPCIONES PROPIAS
    // =====================================================

    public ProductoKiosco buscarObligatorio(int id) {
        ProductoKiosco p = leerPorId(id);
        if (p == null) {
            throw new ProductoNoEncontradoException("No se encontró producto con ID: " + id);
        }
        return p;
    }

    public void validarPrecio(double precio) {
        if (precio < 0) {
            throw new DatoInvalidoException("El precio no puede ser negativo");
        }
    }

    // =====================================================
    // 8. PERSISTENCIA
    // =====================================================

    public void guardarCsv(String archivo) {
        PersistenciaProductos.guardarCsv(lista, archivo);
    }

    public void cargarCsv(String archivo) {
        PersistenciaProductos.cargarCsv(lista, archivo);
    }

    public void guardarJson(String archivo) {
        PersistenciaProductos.guardarJson(lista, archivo);
    }

    public void cargarJson(String archivo) {
        PersistenciaProductos.cargarJson(lista, archivo);
    }

    public void exportarTxtFiltrado(String archivo, double max) {
        PersistenciaProductos.exportarTxtFiltrado(lista, archivo, max);
    }

    // =====================================================
    // 9. DEPURACIÓN
    // =====================================================

    public void mostrar() {
        for (ProductoKiosco p : lista) {
            System.out.println(p);
        }
    }
}
