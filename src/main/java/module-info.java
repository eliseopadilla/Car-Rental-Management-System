module com.example.proyectobasedatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proyectobasedatos to javafx.fxml;
    exports com.example.proyectobasedatos;
}