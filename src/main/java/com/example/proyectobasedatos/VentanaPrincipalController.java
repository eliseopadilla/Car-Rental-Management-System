package com.example.proyectobasedatos;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VentanaPrincipalController {
    @FXML
    private Label display;
    @FXML
    public void vehiculosButton(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaVehiculos.fxml")),950, 700);
            Stage stg = (Stage) display.getScene().getWindow();
            stg.setTitle("Ventana Vehiculos");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    @FXML
    public void mantenimientoButton(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaMantenimiento.fxml")),735, 677);
            Stage stg = (Stage) display.getScene().getWindow();
            stg.setTitle("Ventana Mantenimiento");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    @FXML
    public void rentasButton(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaRentas.fxml")),870, 545);
            Stage stg = (Stage) display.getScene().getWindow();
            stg.setTitle("Ventana Rentas");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
            ex.printStackTrace();
        }
    }

    @FXML
    public void cancelarButton(ActionEvent etv){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("hello-view.fxml")),500, 230);
            Stage stg = (Stage) display.getScene().getWindow();
            stg.setTitle("Login");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
        }
    }


}
