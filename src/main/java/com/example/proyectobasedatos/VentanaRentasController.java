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
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaRentasController {
    //ELEMENTOS TABLA
    @FXML private TableView tabla;
    @FXML private TableColumn colId;
    @FXML private TableColumn colVehiculo;
    @FXML private TableColumn colFechaE;
    @FXML private TableColumn colFechaDev;
    @FXML private TableColumn colMetodo;
    @FXML private TableColumn colKmI;
    @FXML private TableColumn colKmF;
    @FXML private TableColumn colCliente;
    //ELEMENTOS FORMULARIO
    @FXML private Button delete;
    @FXML private Button add;
    @FXML private Button edit;
    @FXML private Button cancelar;
    @FXML private TextField KmInicial;
    @FXML private TextField KmFinal;
    @FXML private Label errorKmInicial;
    @FXML private Label errorKmFinal;
    @FXML private Label echo;
    private ObservableList<Renta> lista;
    private Renta u;
    private Vehiculo v;
    private Cliente cl;
    @FXML
    public void agregar(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("FormularioRenta.fxml")),862, 545);
            Stage stg = (Stage) echo.getScene().getWindow();
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
    public void eliminar(ActionEvent evt) throws SQLException{
        errorKmInicial.setVisible(false);
        errorKmFinal.setVisible(false);
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm = c.createStatement();
        stm.executeUpdate("delete from rentas where id="+u.getId()+"");
        lista.remove(u);
        tabla.setItems(lista);
        edit.setDisable(true);
        add.setDisable(false);
        delete.setDisable(true);
        KmInicial.clear();
        KmFinal.clear();
        echo.setVisible(true);
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
    public void seleccion(MouseEvent evt) throws SQLException {

        if (evt.getClickCount() >= 2 && tabla.getSelectionModel().getSelectedIndex() != -1) {
            TablePosition pos = (TablePosition) tabla.getSelectionModel().getSelectedCells().get(0);
            errorKmFinal.setVisible(false);
            errorKmInicial.setVisible(false);
            echo.setVisible(false);
            u = (Renta) tabla.getSelectionModel().getSelectedItem();
            KmFinal.setText(u.getKmfinal() + "");
            KmInicial.setText(u.getKminicial() + "");
            edit.setDisable(false);
            add.setDisable(true);
            delete.setDisable(false);
            cancelar.setDisable(false);

            String url = "jdbc:mysql://localhost:3306/rentavehiculos";
            Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
            Statement stm1 = c.createStatement();
            ResultSet rs1 = stm1.executeQuery("SELECT * FROM vehiculos WHERE id=" + u.getVehiculo());
            while(rs1.next()) {
                v = new Vehiculo(rs1.getInt("id"),
                        rs1.getString("marca"),
                        rs1.getString("modelo"),
                        rs1.getString("año"),
                        rs1.getString("placa"),
                        rs1.getString("estado"),
                        rs1.getFloat("preciorenta"),
                        rs1.getInt("kmservicio"));
            }

            Statement stm2 = c.createStatement();
            ResultSet rs2 = stm2.executeQuery("SELECT * FROM clientes WHERE id=" + u.getCliente());
            while(rs2.next()) {
                cl = new Cliente(rs2.getInt("id"),
                        rs2.getString("nombre"),
                        rs2.getString("telefono"),
                        rs2.getString("correo"),
                        rs2.getString("apellido"));
            }

            if(pos.getColumn() == 1){
                try {
                    TextArea textArea = new TextArea();
                    textArea.setText("Información del vehículo\n" +
                            "ID: " + v.getId() + "\n" +
                            "Marca: " + v.getMarca() + "\n" +
                            "Modelo: " + v.getModelo() + "\n" +
                            "Año: " + v.getAno() + "\n" +
                            "Placa: " + v.getPlaca() + "\n" +
                            "Precio: " + v.getPrecio()+ "\n" +
                            "Estado: " + v.getEstado() + "\n" +
                            "Kilometraje: " + v.getKmservicio());
                    textArea.setWrapText(true);
                    textArea.setPrefHeight(200);
                    textArea.setPrefWidth(300);
                    textArea.setEditable(false);

                    VBox vbox = new VBox(10);
                    vbox.getChildren().add(textArea);

                    Scene scene = new Scene(vbox, 300, 200);
                    Stage stage = new Stage();
                    stage.setAlwaysOnTop(true);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(tabla.getScene().getWindow());
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.setTitle("Información del Vehículo");
                    stage.showAndWait();
                } catch (Exception ex) {
                    System.out.println("Error Ventana InfoVehiculo");
                    ex.printStackTrace();
                }
            }else{
                if(pos.getColumn() == 7){
                    try {
                        TextArea textArea = new TextArea();
                        textArea.setText("Información del Cliente\n" +
                                "ID: " + cl.getId() + "\n" +
                                "Nombre: " + cl.getNombre() + "\n" +
                                "Apellido: " + cl.getApellido() + "\n" +
                                "Telefono: " + cl.getTelefono() + "\n" +
                                "Correo: " + cl.getCorreo() + "\n");
                        textArea.setWrapText(true);
                        textArea.setPrefHeight(150);
                        textArea.setPrefWidth(300);
                        textArea.setEditable(false);

                        VBox vbox = new VBox(10);
                        vbox.getChildren().add(textArea);

                        Scene scene = new Scene(vbox, 300, 150);
                        Stage stage = new Stage();
                        stage.setAlwaysOnTop(true);
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(tabla.getScene().getWindow());
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("Información del Vehículo");
                        stage.showAndWait();
                    } catch (Exception ex) {
                        System.out.println("Error Ventana InfoVehiculo");
                        ex.printStackTrace();
                    }

                }
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
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm = c.createStatement();
        ResultSet rs = stm.executeQuery("select * from rentas");
        while(rs.next()){
            lista.add(new Renta(
                    rs.getInt("id"),
                    rs.getString("fechaentrega"),
                    rs.getString("fechadevolucion"),
                    rs.getString("metodopago"),
                    rs.getInt("vehiculo_id"),
                    rs.getInt("cliente_id"),
                    rs.getInt("kminicial"),
                    rs.getInt("kmfinal")));
        }
        KmFinal.clear();
        KmInicial.clear();

    }
    @FXML
    public void modificar(ActionEvent evt) throws SQLException {
        if(KmInicial.getText().isBlank() || KmFinal.getText().isBlank()){
            if(KmInicial.getText().isBlank()){
                errorKmInicial.setText("Enter The Required Field");
                errorKmInicial.setVisible(true);

            }else{
                if(!KmInicial.getText().chars().allMatch(Character::isDigit)) {
                    errorKmInicial.setText("Incorrect Data");
                    errorKmInicial.setVisible(true);
                    KmInicial.clear();
                }else{
                    errorKmInicial.setDisable(false);
                }
            }

            if(KmFinal.getText().isBlank()){
                errorKmFinal.setText("Enter The Required Field");
                errorKmFinal.setVisible(true);

            }else{
                if(!KmFinal.getText().chars().allMatch(Character::isDigit)) {
                    errorKmFinal.setText("Incorrect Data");
                    errorKmFinal.setVisible(true);
                    KmFinal.clear();
                }else{
                    errorKmFinal.setDisable(false);
                }
            }
        }else{
            boolean flag1 = false, flag2 = false;
            errorKmFinal.setVisible(false);
            errorKmInicial.setVisible(false);
            if(!(KmFinal.getText().isBlank() && KmInicial.getText().isBlank())){
                if (KmInicial.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)) {
                    flag1 = true;
                } else {
                    errorKmInicial.setText("Incorrect Data");
                    errorKmInicial.setVisible(true);
                    KmInicial.clear();
                }

                if (KmFinal.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit)) {
                    flag2 = true;
                } else {
                    errorKmFinal.setText("Incorrect Data");
                    errorKmFinal.setVisible(true);
                    KmFinal.clear();
                }

                if(flag1 && flag2){
                    String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                    Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
                    Statement stm = c.createStatement();
                    stm.executeUpdate("update rentas set kminicial='"+KmInicial.getText()+"',kmfinal='"+KmFinal.getText()+"' where id = "+u.getId()+"");
                    echo.setVisible(true);
                    KmInicial.clear();
                    KmFinal.clear();
                    delete.setDisable(true);
                    actualizar();
                }
            }
        }
    }
    @FXML
    public void cancel(ActionEvent evt) {
        KmFinal.clear();
        KmInicial.clear();
        edit.setDisable(true);
        delete.setDisable(true);
        add.setDisable(false);
        errorKmFinal.setVisible(false);
        errorKmInicial.setVisible(false);
        cancelar.setDisable(true);
    }
    @FXML
    public void Teclado(KeyEvent kevt) {
        if (errorKmInicial.isVisible() && !KmInicial.getText().isBlank()) {
            errorKmInicial.setVisible(false);
            echo.setVisible(false);
        }
        if (errorKmFinal.isVisible() && !KmFinal.getText().isBlank()) {
            errorKmFinal.setVisible(false);
            echo.setVisible(false);
        }
    }

    public void initialize() throws SQLException {
        edit.setDisable(true);
        delete.setDisable(true);
        cancelar.setDisable(true);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colVehiculo.setCellValueFactory(new PropertyValueFactory("vehiculo") );
        colCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colFechaE.setCellValueFactory(new PropertyValueFactory("fechaentrega"));
        colFechaDev.setCellValueFactory(new PropertyValueFactory("fechadevolucion"));
        colMetodo.setCellValueFactory(new PropertyValueFactory("metodopago"));
        colKmI.setCellValueFactory(new PropertyValueFactory("kminicial"));
        colKmF.setCellValueFactory(new PropertyValueFactory("kmfinal"));
        try {
            actualizar();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
