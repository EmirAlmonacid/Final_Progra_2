package productos.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.List;

import enums.Marca;

public class PersistenciaProductos {

    // =========================
    // CSV
    // =========================
    public static void guardarCsv(List<ProductoKiosco> lista, String nombreArchivo) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));
            pw.println("id;nombre;precio;stock");   // encabezado simple

            for (ProductoKiosco p : lista) {
                pw.println(p.aCsv());              // id;nombre;precio;stock
            }

            pw.close();
            System.out.println("Archivo CSV guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage());
        }
    }

    public static void cargarCsv(List<ProductoKiosco> lista, String nombreArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            lista.clear();

            // leer encabezado
            String linea = br.readLine();
            // leer primera línea de datos
            linea = br.readLine();

            Marca marcaDefault = Marca.values()[0];

            while (linea != null) {
                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    double precio = Double.parseDouble(partes[2]);
                    int stock = Integer.parseInt(partes[3]);

                    Snack s = new Snack(id, nombre, marcaDefault, precio, stock);
                    lista.add(s);
                }
                linea = br.readLine();
            }

            br.close();
            System.out.println("Archivo CSV cargado desde: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar CSV: " + e.getMessage());
        }
    }

    // =========================
    // JSON (simple hecho a mano)
    // =========================
    public static void guardarJson(List<ProductoKiosco> lista, String nombreArchivo) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            pw.println("[");
            for (int i = 0; i < lista.size(); i++) {
                ProductoKiosco p = lista.get(i);
                pw.println("  {");
                pw.println("    \"id\": " + p.getId() + ",");
                pw.println("    \"nombre\": \"" + p.getNombre() + "\",");
                pw.println("    \"precio\": " + p.getPrecio() + ",");
                pw.println("    \"stock\": " + p.getStock());
                pw.print("  }");
                if (i < lista.size() - 1) {
                    pw.println(",");
                } else {
                    pw.println();
                }
            }
            pw.println("]");

            pw.close();
            System.out.println("Archivo JSON guardado en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar JSON: " + e.getMessage());
        }
    }

    public static void cargarJson(List<ProductoKiosco> lista, String nombreArchivo) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(nombreArchivo));
            lista.clear();

            Marca marcaDefault = Marca.values()[0];
            String linea;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();

                if (linea.startsWith("{")) {
                    String lineaId = br.readLine().trim();       // "id": 1,
                    String lineaNombre = br.readLine().trim();   // "nombre": "X",
                    String lineaPrecio = br.readLine().trim();   // "precio": 123,
                    String lineaStock = br.readLine().trim();    // "stock": 10

                    // id
                    String[] partesId = lineaId.split(":");
                    int id = Integer.parseInt(partesId[1].replace(",", "").trim());

                    // nombre
                    String[] partesNombre = lineaNombre.split(":");
                    String nombre = partesNombre[1].replace(",", "").trim();
                    nombre = nombre.replace("\"", "");

                    // precio
                    String[] partesPrecio = lineaPrecio.split(":");
                    double precio = Double.parseDouble(partesPrecio[1].replace(",", "").trim());

                    // stock
                    String[] partesStock = lineaStock.split(":");
                    int stock = Integer.parseInt(partesStock[1].replace(",", "").trim());

                    Snack s = new Snack(id, nombre, marcaDefault, precio, stock);
                    lista.add(s);

                    // leer la línea de cierre "}" o "},"
                    br.readLine();
                }
            }

            br.close();
            System.out.println("Archivo JSON cargado desde: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al cargar JSON: " + e.getMessage());
        }
    }

    // =========================
    // TXT FILTRADO
    // =========================
    public static void exportarTxtFiltrado(List<ProductoKiosco> lista,
                                           String nombreArchivo,
                                           double precioMaximo) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            pw.println("==============================================");
            pw.println("LISTADO DE PRODUCTOS (precio <= " + precioMaximo + ")");
            pw.println("==============================================");
            pw.println("ID\tNOMBRE\tPRECIO\tSTOCK");

            for (ProductoKiosco p : lista) {
                if (p.getPrecio() <= precioMaximo) {
                    pw.println(p.getId() + "\t" +
                               p.getNombre() + "\t" +
                               p.getPrecio() + "\t" +
                               p.getStock());
                }
            }

            pw.close();
            System.out.println("Archivo TXT exportado en: " + nombreArchivo);
        } catch (IOException e) {
            System.out.println("Error al exportar TXT: " + e.getMessage());
        }
    }
}
