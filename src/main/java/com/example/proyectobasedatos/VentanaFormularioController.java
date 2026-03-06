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
import javafx.stage.Stage;

import java.sql.*;
import java.util.*;

public class VentanaFormularioController {
    @FXML private TextField nombre;
    @FXML private TextField apellido;
    @FXML private TextField telefono;
    @FXML private TextField correo;
    @FXML private Label precio;
    @FXML private Label seleccion;
    @FXML private Label errorNombre;
    @FXML private Label errorCorreo;
    @FXML private Label errorApellido;
    @FXML private Label errorTelefono;
    @FXML private Label errorSeleccion;
    @FXML private Button aceptar;
    @FXML private TableView tabla;
    @FXML private TableColumn colAno;
    @FXML private TableColumn colMarca;
    @FXML private TableColumn colModelo;
    @FXML private TableColumn colPrecio;
    private ObservableList<Vehiculo> lista;
    private List<Cliente> listaClientes;
    private List<Renta> listarentas;
    private Vehiculo u;


    public void initialize() throws SQLException {
        lista = FXCollections.observableArrayList();
        listarentas = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/rentavehiculos";
        Connection c = DriverManager.getConnection(url, "YOUR_DB_USER", "YOUR_DB_PASSWORD");
        Statement stm1 = c.createStatement();
        Statement stm2 = c.createStatement();
        ResultSet rs1 = stm1.executeQuery("SELECT * FROM vehiculos WHERE estado = 'Disponible'");
        ResultSet rs2 = stm2.executeQuery("SELECT * FROM rentas");
        while(rs2.next()){
            Renta renta = new Renta(
                   rs2.getInt("id"),
                   rs2.getString("fechaentrega"),
                    rs2.getString("fechadevolucion"),
                    rs2.getString("metodopago"),
                    rs2.getInt("vehiculo_id"),
                    rs2.getInt("cliente_id"),
                    rs2.getInt("kminicial"),
                    rs2.getInt("kmfinal")
            );
            listarentas.add(renta);
        }
        while (rs1.next()) {
            Vehiculo vehiculo = new Vehiculo(
                    rs1.getInt("id"),
                    rs1.getString("marca"),
                    rs1.getString("modelo"),
                    rs1.getString("año"),
                    rs1.getString("placa"),
                    rs1.getString("estado"),
                    rs1.getFloat("preciorenta"),
                    rs1.getInt("kmservicio")
            );
            lista.add(vehiculo);
            for(Renta r: listarentas){
                if(r.getVehiculo()==vehiculo.getId()){
                    lista.remove(vehiculo);
                }
            }
        }
        colAno.setCellValueFactory(new PropertyValueFactory("ano") );
        colMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory("precio"));

        tabla.setItems(lista);
    }

    @FXML
    public void seleccion(MouseEvent evt) {
        if (evt.getClickCount() >= 2 && tabla.getSelectionModel().getSelectedIndex() != -1) {
            u = (Vehiculo) tabla.getSelectionModel().getSelectedItem();
            precio.setText("$" + u.getPrecio() + "");
            seleccion.setText(u.getMarca() + " " + u.getModelo() + " " + u.getAno());
        }
    }

    @FXML
    public void regresar(ActionEvent evt){
        try {
            Scene scn = new Scene(FXMLLoader.load(getClass().getResource("VentanaRentas.fxml")),862, 545);
            Stage stg = (Stage) precio.getScene().getWindow();
            stg.setTitle("Ventana Principal");
            stg.setScene(scn);
            stg.setResizable(true);
            stg.show();
        }catch(Exception ex){
            System.out.println("ERROR!");
        }
    }

    @FXML
    public void aceptar(ActionEvent evt) throws SQLException {
        if(nombre.getText().isBlank() || apellido.getText().isBlank() || telefono.getText().isBlank() || correo.getText().isBlank() || Objects.equals(seleccion.getText(), "SELECCIONA")){
            if(nombre.getText().isBlank()){
                errorNombre.setText("Enter The Required Field");
                errorNombre.setVisible(true);
            }else{
                errorNombre.setVisible(false);
            }

            if(apellido.getText().isBlank()){
                errorApellido.setText("Enter The Required Field");
                errorApellido.setVisible(true);
            }else{
                errorApellido.setVisible(false);
            }

            if(correo.getText().isBlank()){
                errorCorreo.setText("Enter The Required Field");
                errorCorreo.setVisible(true);
            }else{
                errorCorreo.setVisible(false);
            }

            if(Objects.equals(seleccion.getText(), "SELECCIONA")){
                errorSeleccion.setText("Enter The Required Field");
                errorSeleccion.setVisible(true);
            }else{
                errorSeleccion.setVisible(false);
            }

            if(telefono.getText().isBlank()){
                errorTelefono.setText("Enter The Required Field");
                errorTelefono.setVisible(true);

            }else{
                if(!telefono.getText().chars().allMatch(Character::isDigit)) {
                    errorTelefono.setText("Incorrect Data");
                    errorTelefono.setVisible(true);
                    telefono.clear();
                }else{
                    errorTelefono.setDisable(false);
                }
            }
        }else{
            boolean flag1 = false, flag2 = false;
            errorNombre.setVisible(false);
            errorApellido.setVisible(false);
            errorCorreo.setVisible(false);
            errorTelefono.setVisible(false);

            if(telefono.getText().trim().replaceAll("\\s", "").chars().allMatch(Character::isDigit) && !(telefono.getText().isBlank())){
                flag1=true;
            }else{
                errorTelefono.setText("Incorrect Data");
                errorTelefono.setVisible(true);
                telefono.clear();
            }

            if(!(Objects.equals(seleccion.getText(), "SELECCIONA"))){
                flag2=true;
            }else{
                errorSeleccion.setText("Incorrect Data");
                errorSeleccion.setVisible(true);
            }

            int ano = 2024;
            int diainicio = (int) (Math.random() * 15) + 1;
            int diaentrega = (int) (Math.random() * 16) + 15;
            int mesInicio = (int) (Math.random() * 12) + 1;
            int mesEntrega = (int) (Math.random() * 12) + 1;

            String fechaInicio = diainicio + "-" + mesInicio + "-" + ano;
            if(mesEntrega < mesInicio){
                ano = 2025;
            }
            String fechaEntrega = diaentrega + "-" + mesEntrega + "-" + ano;

            List metodos = new ArrayList();
            metodos.add("Tarjeta");
            metodos.add("Efectivo");
            metodos.add("Transferencia");

            String metodo = String.valueOf(metodos.get((int)(Math.random() * 2)));

            if(flag1 && flag2){
                lista.remove(u);
                tabla.refresh();
                listaClientes = new ArrayList<>();
                String url = "jdbc:mysql://localhost:3306/rentavehiculos";
                Connection c = DriverManager.getConnection(url,"root","admin");
                Statement stm = c.createStatement();
                stm.execute("insert into clientes values(0,'"+nombre.getText()+"','"+apellido.getText()+"','"+telefono.getText()+"','"+correo.getText()+"')");
                ResultSet rs = stm.executeQuery("select * from clientes");
                while(rs.next()){
                    Cliente cliente = new Cliente(rs.getInt("id"),rs.getString("nombre"),rs.getString("telefono"),rs.getString("correo"),rs.getString("apellido"));
                    listaClientes.add(cliente);
                }
                stm.execute("insert into rentas values(0,'"+fechaInicio+"','"+fechaEntrega+"','"+metodo+"',"+u.getId()+","+listaClientes.get(listaClientes.size()-1).getId()+","+15+","+16+")");
                nombre.clear();
                apellido.clear();
                correo.clear();
                telefono.clear();
                seleccion.setText("SELECCIONA");
                precio.setText("$0");
            }

        }
    }
    @FXML
    public void Teclado(KeyEvent kevt) {
        if (errorNombre.isVisible() && !nombre.getText().isBlank()) {
            errorNombre.setVisible(false);
        }
        if (errorApellido.isVisible() && !apellido.getText().isBlank()) {
            errorApellido.setVisible(false);
        }
        if (errorTelefono.isVisible() && !telefono.getText().isBlank()) {
            errorTelefono.setVisible(false);
        }
        if (errorCorreo.isVisible() && !correo.getText().isBlank()) {
            errorCorreo.setVisible(false);
        }
        if (errorSeleccion.isVisible() && !(seleccion.getText()=="$0")) {
            errorSeleccion.setVisible(false);
        }
    }

}
