package com.garciajuanjo.conexionuniversalbd;

import com.garciajuanjo.conexionuniversalbd.domain.Conexion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

//quita todos los avisos warning. Tenemos que tener en cuenta de revisarlos primero para evitar problemas
@SuppressWarnings("all")
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/AppView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Conexión universal BBDD");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
        //aquí podemos meter si necesitamos algo que instanciar cuando se inicia la App por ejemplo la conexión a la bd
        Properties properties = new Properties();
        properties.load(new FileReader("Aplication.properties"));
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String name_database = properties.getProperty("name_database");

        Conexion conexion = new Conexion(name_database, username, password);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        Conexion.connection.close();
    }

    public static void main(String[] args) { launch();}
}