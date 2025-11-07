/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package productos.models;
import java.util.List;
import java.util.Optional;
/**
 *
 * @author emira
 */
public interface ICRUD<T, K> {

    boolean crear(T entidad);      // Agregar un nuevo elemento
    T leerPorId(K id);             // Buscar uno por su ID (devuelve null si no existe)
    List<T> leerTodos();           // Mostrar todos los elementos
    boolean actualizar(T entidad); // Modificar un elemento existente
    boolean eliminarPorId(K id);   // Eliminar uno por su ID
}