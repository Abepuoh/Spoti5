package com.Abe.Spoti;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class editListasController {

	@FXML
	private ComboBox<ListaReproduccion> CBlistas;

	@FXML
	private Button buttBLista;

	@FXML
	private Button buttCLista;

	@FXML
	private Button buttExit;

	@FXML
	private TextField txtDescripción;

	@FXML
	private TextField txtNombre;
	
	protected Usuario usuario;
	
	protected MySQLlistaReproduccionDAO aux = new MySQLlistaReproduccionDAO();
	protected ObservableList<ListaReproduccion> auxL;
    @FXML
  	public void initialize() throws DAOException {
      	UsuarioSingleton transfer = UsuarioSingleton.getInstance();
  		usuario = transfer.getUser();
  		auxL =  FXCollections.observableArrayList(aux.mostrarTodos());
  		this.CBlistas.setItems(auxL);
  	}
    
	@FXML
	void borrarLista(ActionEvent event) {
		ListaReproduccion dummy = this.CBlistas.getValue();
		System.out.println(dummy.toString());
	}

	@FXML
	void chooseList(ActionEvent event) {

	}

	@FXML
	void crearLista(ActionEvent event) {

	}

	@FXML
	void exit(ActionEvent event) {

	}

}
