package com.garciajuanjo.conexionuniversalbd.model;

import com.garciajuanjo.conexionuniversalbd.domain.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppDao {

    /**
     * Devuelve el nombre de las tablas de la base de datos que especifiquemos en el archivo de configuración
     * Application.properties para cuando iniciemos la App cargar los datos en el ComboBox
     * @return ObservableList<String> con los nombres de las tablas
     */
    public ObservableList<String> getNamesTables() {
        ObservableList<String> namesTables = FXCollections.observableArrayList();

        try {
            ResultSet resultSet = Conexion.connection.getMetaData().getTables(null, null, null, null);
            while (resultSet.next()){
                // en el ResultSet tenemos los nombres de todas las tablas de la base de datos así que filtramos
                // para quedarnos solo con las que sean de la BBDD que queremos
                if(resultSet.getString(1).equals(Conexion.name_db))
                    namesTables.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException sql){
            sql.printStackTrace();
        }

        return namesTables;
    }

    /**
     * Devuelve el nombre de las columnas de la tabla que pasamos por parámetro. Lo utilizamos para poner el nombre
     * a las columnas del TableView al mostrar los datos
     * @param nameTable String con el nombre de la tabla de la base de datos
     * @return List<String> con el nombre de las columnas
     */
    public List<String> getNamesColumns(String nameTable){
        List<String> namesColumns = new ArrayList<>();

        try {
            String sql = "SELECT * FROM " + nameTable;
            PreparedStatement statement = Conexion.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            int num_columns = resultSet.getMetaData().getColumnCount();

            for (int i = 0; i < num_columns ; i++) {
                //en getColumnName indico i+1 porque las posiciones de las columnas empiezan en 1 e i vale 0
                namesColumns.add(resultSet.getMetaData().getColumnName(i + 1));
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }

        return namesColumns;
    }

    /**
     * Devuelve todos los datos de la base de datos de la tabla que le pasamos por parámetro
     * @param nameTable nombre de la tabla de la base de datos
     * @return ObservableList<Map<String, Object>> con los datos de la tabla
     */
    public ObservableList<Map<String, Object>> getDataTable(String nameTable){
        ObservableList<Map<String, Object>> fields = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM " + nameTable;
            PreparedStatement statement = Conexion.connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            int num_columns = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()){
                Map<String, Object> value = new HashMap<>();
                for (int i = 0; i < num_columns; i++) {
                    String name_column = resultSet.getMetaData().getColumnName(i+1);
                    //introducimos como Key el nombre de la columna para que luego coincidan cuando creemos las columnas
                    //como value le damos el texto que recojamos de la base de datos
                    value.put(name_column, resultSet.getString(name_column));
                }
                fields.add(value);
            }
        }catch (SQLException sql){
            sql.printStackTrace();
        }

        return fields;
    }

}
