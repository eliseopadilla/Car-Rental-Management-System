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
import java.util.Objects;

public class VentanaMantenimientoController {
    @FXML private TableView tabla1;
    @FXML private TableView tabla2;
    @FXML private TableColumn colIdMantenimiento;
    @FXML private TableColumn colVehiculo;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colKm;
    @FXML private TableColumn colCosto;
    @FXML private TableColumn colDesc;
    @FXML private TableColumn colAno;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colModelo;
    @FXML private TableColumn colPlaca;
    @FXML private TableColumn colIdVehiculo;
    @FXML private TextField kilometraje;
    @FXML private Label seleccion;
    @FXML private Label fecha;
    @FXML private Label errorSeleccion;
    @FXML private Label errorKilometraje;
    @FXML private Label errorFecha;
    @FXML private TextArea descripcion;
    @FXML private ChoiceBox dia;
    @FXML private ChoiceBox mes;
    @FXML private ChoiceBox ano;
    @FXML private Button add;
    @FXML private Button delete;
    @FXML private Button edit;
    @FXML private Button cancel;
    private ObservableList<Mantenimiento> lista1;
    private ObservableList<Vehiculo> lista2;
    private Vehiculo u;
    private Mantenimiento m;

    public void initialize(){
        edit.setDisable(true);
        delete.setDisable(true);
        cancel.setDisable(true);
        dia.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30");
        mes.getItems().addAll("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre");
        ano.getItems().addAll("2024","2025");

        colIdMantenimiento.setCellValueFactory(new PropertyValueFactory("id"));
        colVehiculo.setCellValueFactory(new PropertyValueFactory("vehiculo_id"));
        colFecha.setCellValueFactory(new PropertyValueFactory("fecha"));
        colCosto.setCellValueFactory(new PropertyValueFactory("costo"));
        colDesc.setCellValueFactory(new PropertyValueFactory("descripcion"));
        colKm.setCellValueFactory(new PropertyValueFactory("kilometraje"));

        colIdVehiculo.setCellValueFactory(new PropertyValueFactory("id"));
        colAno.setCellValueFactory(new PropertyValueFactory("ano") );
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory("placa"));
        try {
            actualizar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void actualizar()throws SQLException {
        add.setDisable(false);
        edit.setDisable(true);
        lista1 = FXCollections.observableArrayList();
        lista2 = FXCollections.observableArrayList();
        tabla1.setItems(lista1);
        tabla2.setItems(lista2);
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm1 = c.createStatement();
        Statement stm2 = c.createStatement();
        ResultSet rs1 = stm1.executeQuery("select * from Mantenimiento");
        ResultSet rs2 = stm2.executeQuery("SELECT * FROM vehiculos WHERE estado = 'No Disponible'");
        while(rs1.next()){
            lista1.add(new Mantenimiento(
                    rs1.getInt("id"),
                    rs1.getString("fecha"),
                    rs1.getString("descripcion"),
                    rs1.getFloat("costo"),
                    rs1.getInt("kilometraje"),
                    rs1.getInt("vehiculos_id")));
        }
        while (rs2.next()) {
            lista2.add(new Vehiculo(
                    rs2.getInt("id"),
                    rs2.getString("marca"),
                    rs2.getString("modelo"),
                    rs2.getString("año"),
                    rs2.getString("placa"),
                    rs2.getString("estado"),
                    rs2.getFloat("preciorenta"),
                    rs2.getInt("kmservicio")));
        }
        kilometraje.clear();
        descripcion.clear();
        seleccion.setText("SELECCIONA");
        dia.setValue(null);
        mes.setValue(null);
        ano.setValue(null);
    }
    @FXML
    public void Teclado(KeyEvent kevt) {
        if (errorKilometraje.isVisible() && !kilometraje.getText().isBlank()) {
            errorKilometraje.setVisible(false);
        }
        if (errorFecha.isVisible() && !fecha.getText().isBlank()) {
            errorFecha.setVisible(false);
        }
        if (errorSeleccion.isVisible() && !(seleccion.getText()=="SELECCIONA")) {
            errorSeleccion.setVisible(false);
        }
    }
    @FXML
    public void seleccion(MouseEvent evt) {
        if (evt.getClickCount() >= 2 && tabla2.getSelectionModel().getSelectedIndex() != -1) {
            u = (Vehiculo) tabla2.getSelectionModel().getSelectedItem();
            seleccion.setText(u.getMarca() + " " + u.getModelo() + " " + u.getAno());
        }

        if (evt.getClickCount() >= 2 && tabla1.getSelectionModel().getSelectedIndex() != -1) {
            m = (Mantenimiento) tabla1.getSelectionModel().getSelectedItem();
            descripcion.setText(m.getDescripcion());
            kilometraje.setText(String.valueOf(m.getKilometraje()));
            edit.setDisable(false);
            add.setDisable(true);
            delete.setDisable(false);
            cancel.setDisable(false);
        }
    }
    @FXML
    public void regresar(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaPrincipal.fxml")),600, 400);
            Stage stg = (Stage) fecha.getScene().getWindow();
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
        kilometraje.clear();
        descripcion.clear();
        edit.setDisable(true);
        delete.setDisable(true);
        add.setDisable(false);
        errorKilometraje.setVisible(false);
        errorSeleccion.setVisible(false);
        errorFecha.setVisible(false);
        seleccion.setText("SELECCIONA");
        dia.setValue(null);
        mes.setValue(null);
        ano.setValue(null);
        cancel.setDisable(true);
    }
    @FXML
    public void agregar(ActionEvent evt) throws SQLException {
        if (kilometraje.getText().isBlank() || dia.getValue() == null || mes.getValue() == null || ano.getValue() == null || Objects.equals(seleccion.getText(), "SELECCIONA")) {
            if (kilometraje.getText().isBlank()) {
                errorKilometraje.setText("Enter The Required Field");
                errorKilometraje.setVisible(true);
            } else {
                if (!kilometraje.getText().chars().allMatch(Character::isDigit)) {
                    errorKilometraje.setText("Incorrect Data");
                    errorKilometraje.setVisible(true);
                    kilometraje.clear();
                } else {
                    errorKilometraje.setDisable(false);
                }
            }

            if (dia.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (mes.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (ano.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (Objects.equals(seleccion.getText(), "SELECCIONA")) {
                errorSeleccion.setText("Enter The Required Field");
                errorSeleccion.setVisible(true);
            } else {
                errorSeleccion.setVisible(false);
            }
        }else{
            boolean flag3 = false;
            errorKilometraje.setVisible(false);
            errorFecha.setVisible(false);
            errorSeleccion.setVisible(false);
            if(!(kilometraje.getText().isBlank())){
                if(kilometraje.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)){
                    flag3=true;
                }else{
                    errorKilometraje.setText("Incorrect Data");
                    errorKilometraje.setVisible(true);
                    kilometraje.clear();
                }
            }
            if(flag3){
                String fechaStr = dia.getValue() + "/" + mes.getValue() + "/" + ano.getValue();
                float costo = (float) (Math.random()*100000);
                String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                Connection c = DriverManager.getConnection(url,"root","admin");
                Statement stm = c.createStatement();
                stm.execute("insert into Mantenimiento values(0,'"+fechaStr+"','"+descripcion.getText()+"',"+costo+","+kilometraje.getText().trim().replaceAll("\\s","")+","+u.getId()+")");
                kilometraje.clear();
                descripcion.clear();
                seleccion.setText("SELECCIONA");
                dia.setValue(null);
                mes.setValue(null);
                ano.setValue(null);
                actualizar();
            }
        }

    }
    @FXML
    public void editar(ActionEvent evt) throws SQLException {
        if (kilometraje.getText().isBlank() || dia.getValue() == null || mes.getValue() == null || ano.getValue() == null || Objects.equals(seleccion.getText(), "SELECCIONA")) {
            if (kilometraje.getText().isBlank()) {
                errorKilometraje.setText("Enter The Required Field");
                errorKilometraje.setVisible(true);
            } else {
                if (!kilometraje.getText().chars().allMatch(Character::isDigit)) {
                    errorKilometraje.setText("Incorrect Data");
                    errorKilometraje.setVisible(true);
                    kilometraje.clear();
                } else {
                    errorKilometraje.setDisable(false);
                }
            }

            if (dia.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (mes.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (ano.getValue() == null) {
                errorFecha.setText("Enter The Required Field");
                errorFecha.setVisible(true);
            } else {
                errorFecha.setVisible(false);
            }

            if (Objects.equals(seleccion.getText(), "SELECCIONA")) {
                errorSeleccion.setText("Enter The Required Field");
                errorSeleccion.setVisible(true);
            } else {
                errorSeleccion.setVisible(false);
            }
        }else{
            boolean flag3 = false;
            errorKilometraje.setVisible(false);
            errorFecha.setVisible(false);
            errorSeleccion.setVisible(false);
            if(!(kilometraje.getText().isBlank())){
                if(kilometraje.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)){
                    flag3=true;
                }else{
                    errorKilometraje.setText("Incorrect Data");
                    errorKilometraje.setVisible(true);
                    kilometraje.clear();
                }
            }
            if(flag3){
                String fechaStr = dia.getValue() + "/" + mes.getValue() + "/" + ano.getValue();
                float costo = (float) (Math.random()*100000);
                String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
                Statement stm = c.createStatement();
                stm.execute("update Mantenimiento set fecha='"+fechaStr+"',descripcion='"+descripcion.getText()+"',costo="+costo+",kilometraje='"+kilometraje.getText()+"',vehiculos_id="+u.getId()+" where id = "+m.getId()+"");
                kilometraje.clear();
                descripcion.clear();
                seleccion.setText("SELECCIONA");
                dia.setValue(null);
                mes.setValue(null);
                ano.setValue(null);
                actualizar();
            }
        }

    }
    @FXML
    public void eliminar(ActionEvent evt) throws SQLException{
        errorKilometraje.setVisible(false);
        errorSeleccion.setVisible(false);
        errorFecha.setVisible(false);
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm = c.createStatement();
        try{
            stm.executeUpdate("delete from Mantenimiento where id="+m.getId()+"");
        }catch (Exception ex){
            System.out.println("ERROR");
        }
        lista1.remove(m);
        tabla1.setItems(lista1);
        edit.setDisable(true);
        add.setDisable(false);
        delete.setDisable(true);
        kilometraje.clear();
        descripcion.clear();
        dia.setValue(null);
        mes.setValue(null);
        ano.setValue(null);
    }
}
