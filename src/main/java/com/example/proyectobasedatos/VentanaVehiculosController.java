package com.example.proyectobasedatos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.*;

public class VentanaVehiculosController {
    //ELEMENTOS TABLA
    @FXML private TableView tabla;
    @FXML private TableColumn colId;
    @FXML private TableColumn colAno;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colModelo;
    @FXML private TableColumn colPlaca;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colKm;
    //ELEMENTOS FORMULARIO
    @FXML private Button delete;
    @FXML private Button add;
    @FXML private Button refresh;
    @FXML private Button edit;
    @FXML private Button cancelar;
    @FXML private Label errorMarca;
    @FXML private Label errorModelo;
    @FXML private Label errorAno;
    @FXML private Label errorPlaca;
    @FXML private Label errorEstado;
    @FXML private Label errorPrecio;
    @FXML private Label errorKm;
    @FXML private Label echo;
    @FXML private TextField marca;
    @FXML private TextField modelo;
    @FXML private TextField ano;
    @FXML private TextField placa;
    @FXML private TextField precio;
    @FXML private TextField km;
    @FXML private ChoiceBox caja;
    private ObservableList<Vehiculo> lista;
    private Vehiculo u;

    @FXML
    private void agregar(ActionEvent evt) throws SQLException {
        if(marca.getText().isBlank()||modelo.getText().isBlank()||ano.getText().isBlank()||placa.getText().isBlank()||caja.getValue()==null||precio.getText().isBlank()||km.getText().isBlank()){
            if(marca.getText().isBlank()){
                errorMarca.setText("Enter The Required Field");
                errorMarca.setVisible(true);
            }else{
                errorMarca.setVisible(false);
            }

            if(modelo.getText().isBlank()){
                errorModelo.setText("Enter The Required Field");
                errorModelo.setVisible(true);
            }else{
                errorModelo.setVisible(false);
            }

            if(placa.getText().isBlank()){
                errorPlaca.setText("Enter The Required Field");
                errorPlaca.setVisible(true);
            }else{
                errorPlaca.setVisible(false);
            }

            if(caja.getValue()==null){
                errorEstado.setText("Enter The Required Field");
                errorEstado.setVisible(true);
            }else{
                errorEstado.setVisible(false);
            }

            if(ano.getText().isBlank()){
                errorAno.setText("Enter The Required Field");
                errorAno.setVisible(true);

            }else{
                if(!ano.getText().chars().allMatch(Character::isDigit)) {
                    errorAno.setText("Incorrect Data");
                    errorAno.setVisible(true);
                    ano.clear();
                }else{
                    errorAno.setDisable(false);
                }
            }

            if(precio.getText().isBlank()){
                errorPrecio.setText("Enter The Required Field");
                errorPrecio.setVisible(true);

            }else{
                if(!precio.getText().chars().allMatch(Character::isDigit)) {
                    errorPrecio.setText("Incorrect Data");
                    errorPrecio.setVisible(true);
                    precio.clear();
                }else{
                    errorPrecio.setDisable(false);
                }
            }

            if(km.getText().isBlank()){
                errorKm.setText("Enter The Required Field");
                errorKm.setVisible(true);

            }else{
                if(!km.getText().chars().allMatch(Character::isDigit)) {
                    errorKm.setText("Incorrect Data");
                    errorKm.setVisible(true);
                    ano.clear();
                }else{
                    errorKm.setDisable(false);
                }
            }

        }else{
            boolean flag1 = false, flag2 = false, flag3 = false;
            errorModelo.setVisible(false);
            errorMarca.setVisible(false);
            errorAno.setVisible(false);
            errorPlaca.setVisible(false);
            errorEstado.setVisible(false);
            errorPrecio.setVisible(false);
            errorKm.setVisible(false);
            if(!(ano.getText().isBlank()&&precio.getText().isBlank()&&km.getText().isBlank())){
                if(ano.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)){
                    flag1=true;
                }else{
                    errorAno.setText("Incorrect Data");
                    errorAno.setVisible(true);
                    ano.clear();
                }

                if(precio.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)){
                    flag2=true;
                }else{
                    errorPrecio.setText("Incorrect Data");
                    errorPrecio.setVisible(true);
                    precio.clear();
                }

                if(km.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)){
                    flag3=true;
                }else{
                    errorKm.setText("Incorrect Data");
                    errorKm.setVisible(true);
                    km.clear();
                }
            }

            if(flag1 && flag2 && flag3){
                String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
                Statement stm = c.createStatement();
                stm.execute("insert into vehiculos values(0,'"+marca.getText().trim()+"','"+modelo.getText().trim()+"','"+ano.getText().trim().replaceAll("\\s","")+"','"+placa.getText().trim()+"','"+caja.getValue()+"',"+precio.getText().trim().replaceAll("\\s","")+","+km.getText().trim().replaceAll("\\s","")+")");
                echo.setVisible(true);
                marca.clear();
                modelo.clear();
                ano.clear();
                placa.clear();
                precio.clear();
                km.clear();
                caja.setValue(null);
                actualizar();
            }

        }
    }
    @FXML
    public void modificar(ActionEvent evt) throws SQLException {
        if(marca.getText().isBlank()||modelo.getText().isBlank()||ano.getText().isBlank()||placa.getText().isBlank()||caja.getValue()==null||precio.getText().isBlank()||km.getText().isBlank()){
            if(marca.getText().isBlank()){
                errorMarca.setText("Enter The Required Field");
                errorMarca.setVisible(true);
            }else{
                errorMarca.setVisible(false);
            }

            if(modelo.getText().isBlank()){
                errorModelo.setText("Enter The Required Field");
                errorModelo.setVisible(true);
            }else{
                errorModelo.setVisible(false);
            }

            if(placa.getText().isBlank()){
                errorPlaca.setText("Enter The Required Field");
                errorPlaca.setVisible(true);
            }else{
                errorPlaca.setVisible(false);
            }

            if(caja.getValue()==null){
                errorEstado.setText("Enter The Required Field");
                errorEstado.setVisible(true);
            }else{
                errorEstado.setVisible(false);
            }

            if(ano.getText().isBlank()){
                errorAno.setText("Enter The Required Field");
                errorAno.setVisible(true);

            }else{
                if(!ano.getText().chars().allMatch(Character::isDigit)) {
                    errorAno.setText("Incorrect Data");
                    errorAno.setVisible(true);
                    ano.clear();
                }else{
                    errorAno.setDisable(false);
                }
            }

            if(precio.getText().isBlank()){
                errorPrecio.setText("Enter The Required Field");
                errorPrecio.setVisible(true);

            }else{
                if(!precio.getText().chars().allMatch(Character::isDigit)) {
                    errorPrecio.setText("Incorrect Data");
                    errorPrecio.setVisible(true);
                    precio.clear();
                }else{
                    errorPrecio.setDisable(false);
                }
            }

            if(km.getText().isBlank()){
                errorKm.setText("Enter The Required Field");
                errorKm.setVisible(true);

            }else{
                if(!km.getText().chars().allMatch(Character::isDigit)) {
                    errorKm.setText("Incorrect Data");
                    errorKm.setVisible(true);
                    ano.clear();
                }else{
                    errorKm.setDisable(false);
                }
            }

        }else {
            boolean flag1 = false, flag2 = false, flag3 = false;
            errorModelo.setVisible(false);
            errorMarca.setVisible(false);
            errorAno.setVisible(false);
            errorPlaca.setVisible(false);
            errorEstado.setVisible(false);
            errorPrecio.setVisible(false);
            errorKm.setVisible(false);
            if (!(ano.getText().isBlank() && precio.getText().isBlank() && km.getText().isBlank())) {
                if (ano.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)) {
                    flag1 = true;
                } else {
                    errorAno.setText("Incorrect Data");
                    errorAno.setVisible(true);
                    ano.clear();
                }

                if (precio.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)) {
                    flag2 = true;
                } else {
                    errorPrecio.setText("Incorrect Data");
                    errorPrecio.setVisible(true);
                    precio.clear();
                }

                if (km.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)) {
                    flag3 = true;
                } else {
                    errorKm.setText("Incorrect Data");
                    errorKm.setVisible(true);
                    km.clear();
                }
            }

            if (flag1 && flag2 && flag3) {
                String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
                Statement stm = c.createStatement();
                stm.executeUpdate("update vehiculos set marca='"+marca.getText()+"',modelo='"+modelo.getText()+"',año="+ano.getText()+",placa='"+placa.getText()+"',estado='"+caja.getValue()+"',preciorenta="+precio.getText()+",kmservicio="+km.getText()+" where id = "+u.getId()+"");
                echo.setVisible(true);
                marca.clear();
                modelo.clear();
                ano.clear();
                placa.clear();
                caja.setValue(null);
                precio.clear();
                km.clear();
                delete.setDisable(true);
                actualizar();
            }
        }

    }
    @FXML
    public void actualizar()throws SQLException {
        add.setDisable(false);
        edit.setDisable(true);
        echo.setVisible(false);
        lista = FXCollections.observableArrayList();
        tabla.setItems(lista);
        //lista.add(new Usuario(10,"juan",21, "33333"));
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm = c.createStatement();
        ResultSet rs = stm.executeQuery("select * from vehiculos");
        while(rs.next()){
            lista.add(new Vehiculo(
                    rs.getInt("id"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getString("año"),
                    rs.getString("placa"),
                    rs.getString("estado"),
                    rs.getFloat("preciorenta"),
                    rs.getInt("kmservicio")));
        }
        marca.clear();
        modelo.clear();
        ano.clear();
        placa.clear();
        caja.setValue(null);
        precio.clear();
        km.clear();
    }
    @FXML
    public void eliminar(ActionEvent evt) throws SQLException{
        errorModelo.setVisible(false);
        errorMarca.setVisible(false);
        errorAno.setVisible(false);
        errorPlaca.setVisible(false);
        errorEstado.setVisible(false);
        errorPrecio.setVisible(false);
        errorKm.setVisible(false);
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm = c.createStatement();
        try{
            stm.executeUpdate("delete from vehiculos where id="+u.getId()+"");
        }catch (Exception ex){
            echo.setText("VEHICULO EN USO!!");
        }
        lista.remove(u);
        tabla.setItems(lista);
        edit.setDisable(true);
        add.setDisable(false);
        delete.setDisable(true);
        marca.clear();
        modelo.clear();
        ano.clear();
        placa.clear();
        caja.setValue(null);
        precio.clear();
        km.clear();
        echo.setVisible(true);
    }
    @FXML
    public void seleccion(MouseEvent evt) {

        if (evt.getClickCount() >= 2 && tabla.getSelectionModel().getSelectedIndex() != -1) {
            errorModelo.setVisible(false);
            errorMarca.setVisible(false);
            errorAno.setVisible(false);
            errorPlaca.setVisible(false);
            errorEstado.setVisible(false);
            errorPrecio.setVisible(false);
            errorKm.setVisible(false);
            echo.setVisible(false);
            u = (Vehiculo) tabla.getSelectionModel().getSelectedItem();
            marca.setText(u.getMarca());
            modelo.setText(u.getModelo());
            ano.setText(u.getAno());
            placa.setText(u.getPlaca());
            caja.setValue(u.getEstado());
            precio.setText(u.getPrecio() + "");
            km.setText(u.getKmservicio() + "");
            edit.setDisable(false);
            add.setDisable(true);
            delete.setDisable(false);
            cancelar.setDisable(false);
        }
    }
    @FXML
    public void Teclado(KeyEvent kevt) {
        if (errorMarca.isVisible() && !marca.getText().isBlank()) {
            errorMarca.setVisible(false);
            echo.setVisible(false);
        }
        if (errorModelo.isVisible() && !modelo.getText().isBlank()) {
            errorModelo.setVisible(false);
            echo.setVisible(false);
        }
        if (errorAno.isVisible() && !ano.getText().isBlank()) {
            errorAno.setVisible(false);
            echo.setVisible(false);
        }
        if (errorPlaca.isVisible() && !placa.getText().isBlank()) {
            errorPlaca.setVisible(false);
            echo.setVisible(false);
        }
        if (errorEstado.isVisible() && !(caja.getValue()==null)) {
            errorEstado.setVisible(false);
            echo.setVisible(false);
        }
        if (errorPrecio.isVisible() && !precio.getText().isBlank()) {
            errorPrecio.setVisible(false);
            echo.setVisible(false);
        }
        if (errorKm.isVisible() && !km.getText().isBlank()) {
            errorKm.setVisible(false);
            echo.setVisible(false);
        }
    }
    @FXML
    public void regresar(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml")),600, 400);
            Stage stg = (Stage) echo.getScene().getWindow();
            stg.setTitle("Ventana Principal");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
        }
    }
    @FXML
    public void cancel(ActionEvent evt) {
        marca.clear();
        modelo.clear();
        ano.clear();
        placa.clear();
        precio.clear();
        km.clear();
        caja.setValue(null);
        edit.setDisable(true);
        delete.setDisable(true);
        add.setDisable(false);
        errorModelo.setVisible(false);
        errorMarca.setVisible(false);
        errorAno.setVisible(false);
        errorPlaca.setVisible(false);
        errorEstado.setVisible(false);
        errorPrecio.setVisible(false);
        errorKm.setVisible(false);
        cancelar.setDisable(true);
    }
    public void initialize(){
        edit.setDisable(true);
        delete.setDisable(true);
        cancelar.setDisable(true);
        caja.getItems().addAll("Disponible", "No disponible");
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colAno.setCellValueFactory(new PropertyValueFactory("ano") );
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colKm.setCellValueFactory(new PropertyValueFactory("kmservicio"));
        try {
            actualizar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
