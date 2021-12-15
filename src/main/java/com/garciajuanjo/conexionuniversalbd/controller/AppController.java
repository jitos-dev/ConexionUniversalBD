package com.garciajuanjo.conexionuniversalbd.controller;

import com.garciajuanjo.conexionuniversalbd.model.AppDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@SuppressWarnings("all")
public class AppController implements Initializable {

    private AppDao appDao;

    @FXML
    private ComboBox<String> cbTables;

    @FXML
    private TableView tvInformation;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appDao = new AppDao();
        addNamesComboBox();
    }

    /**
     * Acción cuando pinchamos sobre el ComboBox con los nombre de las tablas para mostrar los datos
     */
    @FXML
    void actionCbTables(){
        String nameTable = cbTables.getSelectionModel().getSelectedItem();

        List<String> names_column = appDao.getNamesColumns(nameTable);

        //Limpiamos lo que contenga por si pinchamos más de una vez que se eliminen los datos anteriores
        tvInformation.getColumns().clear();

        //para el tamaño de las columnas. Para que sean todas iguales dividimos el ancho del TableView por el número
        //de columnas que vayamos a poner
        int num_columns = names_column.size();
        double size = tvInformation.getWidth() / num_columns;

        names_column.forEach(name_column -> {
            TableColumn<Map, String> column = new TableColumn<>(name_column);
            column.setPrefWidth(size);
            //le damos la clave a la columna para luego relacionarla y que se introduzcan los datos. En este caso va
            //a ser el nombre de la tabla, que luego es el mismo que le doy en el Map al recoger los datos de la BBDD
            column.setCellValueFactory(new MapValueFactory<>(name_column));
            tvInformation.getColumns().add(column);
        });

        //Añadimos los datos a la tabla
        ObservableList<Map<String, Object>> datos = appDao.getDataTable(nameTable);
        tvInformation.setItems(datos);
    }

    /**
     * Añadimos al iniciar la App nos nombres de las tablas de la base de datos al ComboBox
     */
    private void addNamesComboBox(){
        ObservableList<String> namesTables = appDao.getNamesTables();
        cbTables.setItems(namesTables);
    }
}
