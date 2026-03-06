package com.example.proyectobasedatos;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelloController {

    @FXML private Label display;
    @FXML private Label displayError;
    @FXML private TextField user;
    @FXML private PasswordField password;
    private String contraseña = "1234";
    private String usuario = "admin";
    @FXML
    public void Teclado(KeyEvent kevt){
        displayError.setVisible(false);
        Object o = kevt.getSource();
        if(o.equals(user)) {
            if (kevt.getText().isBlank()) {
                user.clear();
            }
        } else if (o.equals(password)) {
            if (kevt.getText().isBlank()) {
                password.clear();
            }
        }
    }

    @FXML
    public void Login(ActionEvent evt){
        displayError.setVisible(false);
        String u = user.getText();
        String p = password.getText();
        //String u ="admin";
        //String p ="1234";
        if(!u.isEmpty() && !p.isEmpty()){
            if(u.equals(usuario) && p.equals(contraseña)){
                try {
                    Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml")),600, 400);
                    Stage stg = (Stage) display.getScene().getWindow();
                    stg.setTitle("Ventana Principal");
                    stg.setScene(scn);
                    stg.setResizable(true);
                    stg.show();
                }catch(Exception ex){
                    System.out.println("ERROR!");
                }
            }else{
                if(u.equals(usuario) || p.equals(contraseña)){
                    if(!u.equals(usuario)){
                        displayError.setText("*Incorrect Username!");
                        displayError.setVisible(true);
                        user.clear();
                    }else {
                        if (!p.equals(contraseña)) {
                            displayError.setText("*Incorrect Password!");
                            displayError.setVisible(true);
                            password.clear();
                        }
                    }

                }else{
                    displayError.setText("*Incorrect data, please try again!");
                    displayError.setVisible(true);
                    user.clear();
                    password.clear();
                }
            }
        }else{
            if(u.isEmpty() && p.isEmpty()){
                displayError.setText("*Enter the required fields!");
                displayError.setVisible(true);
            }else{
                if(u.isEmpty()){
                    displayError.setText("*Enter the Username!");
                    displayError.setVisible(true);
                }else{
                    if(p.isEmpty()){
                        displayError.setText("*Enter the Password!");
                        displayError.setVisible(true);
                    }
                }
            }
        }
    }

    @FXML
    public void Close(ActionEvent evt){
        Platform.exit();
    }

}