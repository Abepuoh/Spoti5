package com.Abe.Spoti;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.Disco;
import com.Abe.Spoti.model.Genero;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLcancionDAO;
import com.Abe.Spoti.mySQLDAO.MySQLdiscoDAO;
import com.Abe.Spoti.mySQLDAO.MySQLgeneroDAO;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class cancionesController {

	@FXML
	private Button ButtAñadir;

	@FXML
	private ComboBox<Cancion> CBCancion;

	@FXML
	private ComboBox<Disco> CBDiscos;

	@FXML
	private ComboBox<Genero> CBGenero;

	@FXML
	private ComboBox<ListaReproduccion> CBlistas;

	@FXML
	private Button buttBCancion;

	@FXML
	private Button buttCCancion;

	@FXML
	private Button buttExit;

	@FXML
	private Button buttonDel;

	@FXML
	private TextField txtDuracion;

	@FXML
	private TextField txtNombre;

	Usuario usuario;

	protected MySQLlistaReproduccionDAO aux = new MySQLlistaReproduccionDAO();
	protected MySQLcancionDAO auxCD = new MySQLcancionDAO();
	protected MySQLdiscoDAO auxD = new MySQLdiscoDAO();
	protected MySQLgeneroDAO auxG = new MySQLgeneroDAO();
	protected ObservableList<ListaReproduccion> auxL;
	protected ObservableList<Cancion> auxC;
	protected ObservableList<Disco> auxDiscos;
	protected ObservableList<Genero> auxGenero;
	
	public void initialize() throws DAOException {
		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		auxL = FXCollections.observableArrayList(aux.mostrarPorCreador(usuario));
		auxC = FXCollections.observableArrayList(auxCD.mostrarTodos());
		auxDiscos = FXCollections.observableArrayList(auxD.mostrarTodos());
		auxGenero =	FXCollections.observableArrayList(auxG.mostrarTodos());
		
		CBlistas.setItems(auxL);
		CBCancion.setItems(auxC);
		CBGenero.setItems(auxGenero);
		CBDiscos.setItems(auxDiscos);
	}

	@FXML
	void ACancion(ActionEvent event) {

	}

	@FXML
	void borrarCancion(ActionEvent event) {

	}

	@FXML
	void borrarCancionP(ActionEvent event) {

	}

	@FXML
	void crearCancion(ActionEvent event) {

	}

	@FXML
	void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();
	}

}
