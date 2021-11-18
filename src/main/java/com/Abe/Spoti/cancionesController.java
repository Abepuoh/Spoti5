package com.Abe.Spoti;

import java.util.Optional;

import com.Abe.Spoti.Model.DataObject.Cancion;
import com.Abe.Spoti.Model.DataObject.Disco;
import com.Abe.Spoti.Model.DataObject.Genero;
import com.Abe.Spoti.Model.DataObject.ListaReproduccion;
import com.Abe.Spoti.Model.DataObject.Usuario;
import com.Abe.Spoti.Model.DataObject.UsuarioSingleton;
import com.Abe.Spoti.Model.IDAO.DAOException;
import com.Abe.Spoti.Model.mySQLDAO.MySQLcancionDAO;
import com.Abe.Spoti.Model.mySQLDAO.MySQLdiscoDAO;
import com.Abe.Spoti.Model.mySQLDAO.MySQLgeneroDAO;
import com.Abe.Spoti.Model.mySQLDAO.MySQLlistaReproduccionDAO;

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

	protected MySQLlistaReproduccionDAO auxLR = new MySQLlistaReproduccionDAO();
	protected MySQLcancionDAO auxCD = new MySQLcancionDAO();
	protected MySQLdiscoDAO auxD = new MySQLdiscoDAO();
	protected MySQLgeneroDAO auxG = new MySQLgeneroDAO();
	protected ObservableList<ListaReproduccion> auxL;
	protected ObservableList<Cancion> auxC;
	protected ObservableList<Disco> auxDiscos;
	protected ObservableList<Genero> auxGenero;

	public void initialize()  {
		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		try {
			auxL = FXCollections.observableArrayList(auxLR.mostrarPorCreador(usuario));
			auxC = FXCollections.observableArrayList(auxCD.mostrarTodos());
			auxDiscos = FXCollections.observableArrayList(auxD.mostrarTodos());
			auxGenero = FXCollections.observableArrayList(auxG.mostrarTodos());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CBlistas.setItems(auxL);
		CBCancion.setItems(auxC);
		CBGenero.setItems(auxGenero);
		CBDiscos.setItems(auxDiscos);
	}
	/**
	 * Metodo que añade canciones a playList si estas no estan en ya.
	 * @param event
	 * @throws DAOException
	 */
	@FXML
	void ACancion(ActionEvent event) {
		ListaReproduccion dummy = this.CBlistas.getValue();
		Cancion aux = this.CBCancion.getValue();
		try {
			if(dummy != null) {	
				if (!auxLR.checkSong(aux, dummy)) {
					showAñadir(aux.getNombre());
					auxLR.añadirCancion(aux, dummy);
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Ya está en tu playList tienes que elegir otra lista");
					alert.showAndWait();
				}
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("No puede añadir sino tienes PlayList");
				alert.showAndWait();
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Método que borra canciones de la playList si estan dentro de la misma
	 * @param event
	 * @throws DAOException
	 */
	@FXML
	void borrarCancionP(ActionEvent event)  {
		ListaReproduccion dummy = this.CBlistas.getValue();
		Cancion aux = this.CBCancion.getValue();
		try {
			if(dummy != null) {	
			if (auxLR.checkSong(aux, dummy) == true) {
				showBorrar(aux.getNombre());
				auxLR.borrarCancion(aux, dummy);
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("Tienes que elegir una canción que aún esté en la playlist");
				alert.showAndWait();
			}
			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error");
				alert.setContentText("No puede borrar sino tienes PlayList");
				alert.showAndWait();
			}
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/** 
	 * Metodo para borrar una cancion a la base de datos introduciendo el nombre de la misma
	 * @param event
	 * @throws DAOException
	 */
	@FXML
	void borrarCancion(ActionEvent event)  {
		String nombre = this.txtNombre.getText();
		if (!this.txtNombre.getText().trim().isEmpty()) {
			try {
				if (auxCD.mostrarPorNombre(nombre) != null) {
					if (auxCD.mostrarTodos().contains(auxCD.mostrarPorNombre(nombre))) {
						Cancion dummy = auxCD.mostrarPorNombre(nombre);
						if (showBorrar(nombre)) {
							auxCD.borrar(dummy.getId());
						}
					}else{
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setHeaderText(null);
						alert.setTitle("Error");
						alert.setContentText("Tienes que elegir una canción que exista");
						alert.showAndWait();
					}	
				} else {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setHeaderText(null);
					alert.setTitle("Error");
					alert.setContentText("Tienes que introducir el nombre de la canción");
					alert.showAndWait();
				}
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No dejes el campo del nombre vacío");
			alert.showAndWait();
		}
	}
	/**
	 * Metodo que crea canciones y las introduce en la base de datos.
	 * @param event
	 */
	@FXML
	void crearCancion(ActionEvent event) {
		String nombre = this.txtNombre.getText();
		String txtDuracion = this.txtDuracion.getText();
		Float time = Float.parseFloat(txtDuracion);
		Genero auxG = this.CBGenero.getValue();
		Disco auxD = this.CBDiscos.getValue();

		if (!this.txtNombre.getText().trim().isEmpty() && !this.txtDuracion.getText().trim().isEmpty() && auxG != null
				&& auxD != null) {
			try {
				Cancion dummy = new Cancion(nombre, time, auxG, auxD);
					auxCD.crear(dummy);
					this.txtNombre.clear();
					this.txtDuracion.clear();
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText(null);
					alert.setTitle("Informacion");
					alert.setContentText("Se ha añadido correctamente");
					alert.showAndWait();
			} catch (DAOException e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error de creacion");
				alert.setContentText("La cancion ya existe");
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
	/**
	 * Metodo que devuelve true o false usado para confirmar una accion
	 * @param nombre de la cancion
	 * @return
	 */
	public boolean showEdit(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer borrar la cancion " + nombre + "?");
		alert.setContentText("Si continuas tus datos seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Metodo que devuelve true o false usado para confirmar una accion
	 * @param nombre de la cancion
	 * @return
	 */
	public boolean showAñadir(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer añadir la cancion a la playlist " + nombre + "?");
		alert.setContentText("Si continuas tus datos seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Metodo que devuelve true o false usado para confirmar una accion
	 * @param nombre de la cancion
	 * @return
	 */
	public boolean showBorrar(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer borrar la cancion " + nombre + "?");
		alert.setContentText("Si continuas tus datos seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
}
