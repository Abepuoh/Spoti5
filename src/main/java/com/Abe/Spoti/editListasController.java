package com.Abe.Spoti;

import java.util.Optional;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
  		CBlistas.setItems(auxL);
		
  		
  	}
    
	@FXML
	void borrarLista(ActionEvent event) throws DAOException {
		ListaReproduccion dummy = this.CBlistas.getValue();
		if (dummy!= null) {	
			aux.borrar(dummy.getId());
			showEdit(dummy.getNombre());
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("Tienes que elegir una lista");
			alert.showAndWait();
		}
	}

	@FXML
	void crearLista(ActionEvent event) {
		String txtNombre = this.txtNombre.getText();
		String txtDescripción = this.txtDescripción.getText();
		

		if (!this.txtNombre.getText().trim().isEmpty() && !this.txtDescripción.getText().trim().isEmpty()) {
			try {
				MySQLlistaReproduccionDAO aux = new MySQLlistaReproduccionDAO();
				ListaReproduccion dummy = new ListaReproduccion(txtNombre,txtDescripción,usuario);
				if (!aux.mostrarTodos().equals(dummy)) {
					aux.crear(dummy);
					this.txtNombre.clear();
					this.txtDescripción.clear();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Informacion");
					alert.setContentText("Se ha añadido correctamente");
					alert.showAndWait();
				}
			} catch (DAOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error de creacion");
				alert.setContentText("El usuario ya existe");
				alert.showAndWait();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de creacion");
			alert.setContentText("Porfavor no deje ningun dato vacío");
			alert.showAndWait();
		}
	}


	@FXML
	void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();
	}
	public boolean showEdit(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer borrar la playlist " + nombre + "?");
		alert.setContentText("Si continuas tus datos seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

}
