package com.Abe.Spoti;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLcancionDAO;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class cancionesController {

	@FXML
	private Button ButtAñadir;

	@FXML
	private ComboBox<Cancion> CBCancion;

	@FXML
	private ComboBox<?> CBDiscos;

	@FXML
	private ComboBox<?> CBGenero;

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
	protected ObservableList<ListaReproduccion> auxL;
	protected MySQLcancionDAO auxCD = new MySQLcancionDAO();
	protected ObservableList<Cancion> auxC;
	
	public void initialize() throws DAOException {
		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		auxL = FXCollections.observableArrayList(aux.mostrarPorCreador(usuario));
		auxC = FXCollections.observableArrayList(auxCD.mostrarTodos());
		CBlistas.setItems(auxL);
		CBCancion.setItems(auxC);
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

	}

}
