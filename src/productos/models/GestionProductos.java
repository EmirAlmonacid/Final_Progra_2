/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
/**
 *
 * @author emira
 */
public class GestionProductos implements ICRUD<ProductoKiosco, Integer>, Iterable<ProductoKiosco> {

    // Lista donde se guardan los productos
    private final List<ProductoKiosco> lista = new ArrayList<>();

    // Contador de cambios (lo usa el iterador)
    private int modCount = 0;

    // -----------------------
    // MÉTODOS CRUD
    // -----------------------

    // Agrega un producto si el ID no existe
    @Override
    public boolean crear(ProductoKiosco entidad) {
        if (entidad == null) return false;
        if (leerPorId(entidad.getId()) != null) return false; // ID duplicado
        lista.add(entidad);
        modCount++;
        return true;
    }

    // Busca un producto por su ID. Devuelve el producto o null si no existe
    @Override
    public ProductoKiosco leerPorId(Integer id) {
        for (ProductoKiosco p : lista) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    // Devuelve una copia de la lista (para no exponer la original)
    @Override
    public List<ProductoKiosco> leerTodos() {
        return new ArrayList<>(lista);
    }

    // Reemplaza un producto que ya existe (por ID)
    @Override
    public boolean actualizar(ProductoKiosco entidad) {
        if (entidad == null) return false;
        Integer id = entidad.getId();
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                lista.set(i, entidad);
                modCount++;
                return true;
            }
        }
        return false;
    }

    // Elimina un producto por su ID
    @Override
    public boolean eliminarPorId(Integer id) {
        Iterator<ProductoKiosco> it = lista.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                modCount++;
                return true;
            }
        }
        return false;
    }

    // -----------------------
    // ITERADOR (para for-each)
    // -----------------------

    @Override
    public Iterator<ProductoKiosco> iterator() {
        return new IteradorProductos();
    }

    // Clase interna: recorre la lista
    private class IteradorProductos implements Iterator<ProductoKiosco> {
        private int cursor = 0;
        private int lastRet = -1;
        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return cursor < lista.size();
        }

        @Override
        public ProductoKiosco next() {
            if (cursor >= lista.size())
                throw new NoSuchElementException("No hay más productos");
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException("La lista cambió durante la iteración");
            lastRet = cursor++;
            return lista.get(lastRet);
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException("Primero llamá a next()");
            if (expectedModCount != modCount)
                throw new ConcurrentModificationException("La lista cambió durante la iteración");
            lista.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            modCount++;
            expectedModCount = modCount;
        }
    }
    
    
    // Filtrar por nombre (texto que contenga)
    public List<ProductoKiosco> filtrarPorNombre(String texto) {
        List<ProductoKiosco> filtrados = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            if (p.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    // Filtrar por precio máximo
    public List<ProductoKiosco> filtrarPorPrecioMax(double max) {
        List<ProductoKiosco> filtrados = new ArrayList<>();
        for (ProductoKiosco p : lista) {
            if (p.getPrecio() <= max) {
                filtrados.add(p);
            }
        }
        return filtrados;
    }

    // Para ver todos los productos
    public void mostrarLista() {
        for (ProductoKiosco p : lista) {
            System.out.println(p);
        }
    }

  
}


