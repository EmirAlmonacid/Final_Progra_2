package productos.view;

// Enums y modelos
import enums.Marca;
import enums.CategoriaKiosco;
import productos.models.GestionProductos;
import productos.models.ProductoKiosco;
import productos.models.Snack;

// JavaFX básico
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Para elegir archivos (CSV / JSON / TXT)
import javafx.stage.FileChooser;
import java.io.File;

public class MainFX extends Application {

    // Lógica de negocio (lista de productos, CRUD, etc.)
    private GestionProductos gestion = new GestionProductos();

    // Lista visual
    private ListView<ProductoKiosco> lstProductos = new ListView<>();

    // Campos de texto
    private TextField txtId = new TextField();
    private TextField txtNombre = new TextField();
    private TextField txtPrecio = new TextField();
    private TextField txtStock = new TextField();
    private TextField txtPrecioMax = new TextField();

    // NUEVOS: combos para elegir marca y categoría
    private ComboBox<Marca> cboMarca = new ComboBox<>();
    private ComboBox<CategoriaKiosco> cboCategoria = new ComboBox<>();

    // Mensajes al usuario
    private Label lblMensaje = new Label();

    @Override
    public void start(Stage stage) {

        // =====================================
        // FORMULARIO (ID, Nombre, Precio, Stock)
        // =====================================
        GridPane form = new GridPane();
        form.setHgap(5);
        form.setVgap(5);
        form.setPadding(new Insets(10));

        // Fila 0: ID
        form.add(new Label("ID:"), 0, 0);
        form.add(txtId, 1, 0);

        // Fila 1: Nombre
        form.add(new Label("Nombre:"), 0, 1);
        form.add(txtNombre, 1, 1);

        // Fila 2: Precio
        form.add(new Label("Precio:"), 0, 2);
        form.add(txtPrecio, 1, 2);

        // Fila 3: Stock
        form.add(new Label("Stock:"), 0, 3);
        form.add(txtStock, 1, 3);

        // ===============================
        // COMBOS DE MARCA Y CATEGORÍA
        // ===============================

        // Cargo las opciones de los enums en los combos
        cboMarca.getItems().addAll(Marca.values());
        cboCategoria.getItems().addAll(CategoriaKiosco.values());

        // Dejo seleccionados algunos valores por defecto
        cboMarca.getSelectionModel().selectFirst();       // primera marca (COCA_COLA)
        cboCategoria.getSelectionModel().selectFirst();   // primera categoría (BEBIDA)

        // Fila 4: Marca
        form.add(new Label("Marca:"), 0, 4);
        form.add(cboMarca, 1, 4);

        // Fila 5: Categoría
        form.add(new Label("Categoría:"), 0, 5);
        form.add(cboCategoria, 1, 5);

        // =====================================
        // BOTONES CRUD
        // =====================================
        Button btnAgregar = new Button("Agregar");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");

        btnAgregar.setOnAction(e -> agregarProducto());
        btnActualizar.setOnAction(e -> actualizarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());

        HBox boxCrud = new HBox(5, btnAgregar, btnActualizar, btnEliminar);

        // =====================================
        // BOTONES ORDENAR
        // =====================================
        Button btnOrdenarNombre = new Button("Ordenar por nombre");
        Button btnOrdenarPrecio = new Button("Ordenar por precio");
        Button btnOrdenarStock = new Button("Ordenar por stock");

        btnOrdenarNombre.setOnAction(e -> {
            gestion.ordenarPorNombre();
            refrescarLista();
            lblMensaje.setText("Lista ordenada por nombre.");
        });

        btnOrdenarPrecio.setOnAction(e -> {
            gestion.ordenarPorPrecio();
            refrescarLista();
            lblMensaje.setText("Lista ordenada por precio.");
        });

        btnOrdenarStock.setOnAction(e -> {
            gestion.ordenarPorStock();
            refrescarLista();
            lblMensaje.setText("Lista ordenada por stock.");
        });

        HBox boxOrden = new HBox(5, btnOrdenarNombre, btnOrdenarPrecio, btnOrdenarStock);

        // =====================================
        // FILTRO POR PRECIO MÁXIMO
        // =====================================
        HBox boxFiltro = new HBox(5);
        boxFiltro.getChildren().addAll(new Label("Precio max:"), txtPrecioMax);

        Button btnFiltrar = new Button("Filtrar (mostrar solo <= max)");
        btnFiltrar.setOnAction(e -> filtrarPorPrecio());

        // =====================================
        // PERSISTENCIA (CSV / JSON / TXT)
        // =====================================
        Button btnGuardarCsv = new Button("Guardar CSV");
        Button btnCargarCsv = new Button("Cargar CSV");
        Button btnGuardarJson = new Button("Guardar JSON");
        Button btnCargarJson = new Button("Cargar JSON");
        Button btnExportarTxt = new Button("Exportar TXT filtrado");

        // GUARDAR CSV
        btnGuardarCsv.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar CSV");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

            File archivo = fc.showSaveDialog(stage);
            if (archivo != null) {
                gestion.guardarCsv(archivo.getAbsolutePath());
                lblMensaje.setText("CSV guardado en: " + archivo.getAbsolutePath());
            }
        });

        // CARGAR CSV
        btnCargarCsv.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar CSV");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

            File archivo = fc.showOpenDialog(stage);
            if (archivo != null) {
                gestion.cargarCsv(archivo.getAbsolutePath());
                refrescarLista();
                lblMensaje.setText("CSV cargado desde: " + archivo.getAbsolutePath());
            }
        });

        // GUARDAR JSON
        btnGuardarJson.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar JSON");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos JSON", "*.json"));

            File archivo = fc.showSaveDialog(stage);
            if (archivo != null) {
                gestion.guardarJson(archivo.getAbsolutePath());
                lblMensaje.setText("JSON guardado en: " + archivo.getAbsolutePath());
            }
        });

        // CARGAR JSON
        btnCargarJson.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setTitle("Cargar JSON");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos JSON", "*.json"));

            File archivo = fc.showOpenDialog(stage);
            if (archivo != null) {
                gestion.cargarJson(archivo.getAbsolutePath());
                refrescarLista();
                lblMensaje.setText("JSON cargado desde: " + archivo.getAbsolutePath());
            }
        });

        // EXPORTAR TXT FILTRADO
        btnExportarTxt.setOnAction(e -> {
            double max;
            try {
                max = Double.parseDouble(txtPrecioMax.getText());
            } catch (NumberFormatException ex) {
                lblMensaje.setText("Precio máximo inválido.");
                return;
            }

            FileChooser fc = new FileChooser();
            fc.setTitle("Guardar TXT filtrado");
            fc.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));

            File archivo = fc.showSaveDialog(stage);
            if (archivo != null) {
                gestion.exportarTxtFiltrado(archivo.getAbsolutePath(), max);
                lblMensaje.setText("TXT exportado en: " + archivo.getAbsolutePath());
            }
        });

        VBox boxPersist = new VBox(5,
                new Label("Persistencia (CSV / JSON / TXT):"),
                new HBox(5, btnGuardarCsv, btnCargarCsv),
                new HBox(5, btnGuardarJson, btnCargarJson),
                btnExportarTxt
        );

        // =====================================
        // LISTA VISUAL
        // =====================================
        lstProductos.setPrefHeight(200);
        refrescarLista();

        // Cuando selecciono un producto, cargo sus datos en el formulario
        lstProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                txtId.setText(String.valueOf(newV.getId()));
                txtNombre.setText(newV.getNombre());
                txtPrecio.setText(String.valueOf(newV.getPrecio()));
                txtStock.setText(String.valueOf(newV.getStock()));
                // también actualizo los combos
                cboMarca.setValue(newV.getMarca());
                cboCategoria.setValue(newV.getCategoria());
            }
        });

        // =====================================
        // LAYOUT GENERAL
        // =====================================
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(
                form,
                boxCrud,
                new Label("Productos:"),
                lstProductos,
                boxOrden,
                boxFiltro,
                btnFiltrar,
                boxPersist,
                lblMensaje
        );

        Scene scene = new Scene(root, 650, 650);
        stage.setScene(scene);
        stage.setTitle("Gestión de Productos");
        stage.show();
    }

    // ======================================================
    // MÉTODOS DE APOYO
    // ======================================================
    private void refrescarLista() {
        ObservableList<ProductoKiosco> datos =
                FXCollections.observableArrayList(gestion.leerTodos());
        lstProductos.setItems(datos);
    }

    private void agregarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Marca marcaSeleccionada = cboMarca.getValue();
            if (marcaSeleccionada == null) {
                marcaSeleccionada = Marca.values()[0]; // por las dudas
            }

            CategoriaKiosco categoriaSeleccionada = cboCategoria.getValue();
            if (categoriaSeleccionada == null) {
                categoriaSeleccionada = CategoriaKiosco.SNACK; // por las dudas
            }

            // Creo el producto Snack
            Snack s = new Snack(id, nombre, marcaSeleccionada, precio, stock);
            // Seteo la categoría elegida
            s.setCategoria(categoriaSeleccionada);

            boolean ok = gestion.crear(s);
            if (ok) {
                refrescarLista();
                lblMensaje.setText("Producto agregado.");
            } else {
                lblMensaje.setText("Ya existe un producto con ese ID.");
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Datos numéricos inválidos.");
        }
    }

    private void actualizarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());
            int stock = Integer.parseInt(txtStock.getText());

            Marca marcaSeleccionada = cboMarca.getValue();
            if (marcaSeleccionada == null) {
                marcaSeleccionada = Marca.values()[0];
            }

            CategoriaKiosco categoriaSeleccionada = cboCategoria.getValue();
            if (categoriaSeleccionada == null) {
                categoriaSeleccionada = CategoriaKiosco.SNACK;
            }

            Snack s = new Snack(id, nombre, marcaSeleccionada, precio, stock);
            s.setCategoria(categoriaSeleccionada);

            boolean ok = gestion.actualizar(s);
            if (ok) {
                refrescarLista();
                lblMensaje.setText("Producto actualizado.");
            } else {
                lblMensaje.setText("No se encontró producto con ese ID.");
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Datos numéricos inválidos.");
        }
    }

    private void eliminarProducto() {
        try {
            int id = Integer.parseInt(txtId.getText());
            boolean ok = gestion.eliminarPorId(id);
            if (ok) {
                refrescarLista();
                lblMensaje.setText("Producto eliminado.");
            } else {
                lblMensaje.setText("No se encontró producto con ese ID.");
            }
        } catch (NumberFormatException ex) {
            lblMensaje.setText("ID inválido.");
        }
    }

    private void filtrarPorPrecio() {
        try {
            double max = Double.parseDouble(txtPrecioMax.getText());
            ObservableList<ProductoKiosco> filtrados =
                    FXCollections.observableArrayList(gestion.filtrarPorPrecioMax(max));
            lstProductos.setItems(filtrados);
            lblMensaje.setText("Mostrando productos con precio <= " + max);
        } catch (NumberFormatException ex) {
            lblMensaje.setText("Precio máximo inválido.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
