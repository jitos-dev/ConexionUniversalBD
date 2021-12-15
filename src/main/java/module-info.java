module com.garciajuanjo.conexionuniversalbd {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.garciajuanjo.conexionuniversalbd to javafx.fxml;
    exports com.garciajuanjo.conexionuniversalbd;
    exports com.garciajuanjo.conexionuniversalbd.controller;
    opens com.garciajuanjo.conexionuniversalbd.controller to javafx.fxml;
}