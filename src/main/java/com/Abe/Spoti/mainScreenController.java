package com.Abe.Spoti;

import java.io.IOException;
import java.util.Optional;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLcancionDAO;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;
import com.Abe.Spoti.mySQLDAO.MySQLusuarioDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class mainScreenController {

	@FXML
	private Button buttECancion;

	@FXML
	private Button buttCLista;

	@FXML
	private Button buttDesub;

	@FXML
	private Button buttEditUser;

	@FXML
	private Button buttExit;

	@FXML
	private Button buttSub;
	@FXML
	private TableColumn<ListaReproduccion, String> colCreador;

	@FXML
	private TableColumn<ListaReproduccion, String> colId;

	@FXML
	private TableColumn<ListaReproduccion, String> colNombre;
	@FXML
	private TableView<ListaReproduccion> listasPropias;

	@FXML
	private TableColumn<ListaReproduccion, String> idPList;
	@FXML
	private TableColumn<ListaReproduccion, String> nombrePList;
	@FXML
	private TableColumn<ListaReproduccion, String> subPList;
	@FXML
	private TableView<ListaReproduccion> listasTotales;

	@FXML
	private TableColumn<Cancion, String> idCan;

	@FXML
	private TableColumn<Cancion, String> nombreCan;

	@FXML
	private TableColumn<Cancion, String> generoCan;
	@FXML
	private TableColumn<Cancion, String> cancionesR;
	@FXML
	private TableColumn<Cancion, String> discoCan;

	@FXML
	private TableView<Cancion> listaCanciones;

	protected Usuario usuario;
	protected MySQLcancionDAO cDao = new MySQLcancionDAO();
	protected MySQLlistaReproduccionDAO lDao = new MySQLlistaReproduccionDAO();
	protected static ObservableList<Cancion> cancionLista = FXCollections.observableArrayList();
	protected static ObservableList<ListaReproduccion> ListadeListas = FXCollections.observableArrayList();
	protected static ObservableList<ListaReproduccion> listasPropiasU = FXCollections.observableArrayList();

	@FXML
	public void initialize() throws DAOException {

		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		cancionLista.setAll(cDao.mostrarTodos());
		ListadeListas.setAll(lDao.mostrarTodos());
		listasPropiasU.setAll(lDao.mostrarPorCreador(usuario));
		colocarInfo();
		
	}

	@FXML
	public void suscribirse(ActionEvent event) throws DAOException {
		MySQLusuarioDAO us = new MySQLusuarioDAO();
		ListaReproduccion aux = this.listasTotales.getSelectionModel().getSelectedItem();
		if (!us.checkSub(aux, usuario)) {
			showSub(aux.getNombre());
			us.añadirListaUsuario(aux, usuario);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No has seleccionado o ya estas suscrito a una lista");
			alert.showAndWait();
		}
	}

	@FXML
	public void desuscribirse(ActionEvent event) throws DAOException {
		MySQLusuarioDAO us = new MySQLusuarioDAO();
		ListaReproduccion aux = this.listasTotales.getSelectionModel().getSelectedItem();
		if (us.checkSub(aux, usuario)==true) {
			if(unSub(aux.getNombre())) {
				us.borrarListaUsuario(aux, usuario);				
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error");
			alert.setContentText("No has seleccionado o no estas suscrito a esa lista");
			alert.showAndWait();
		}
	}

	@FXML
	public void editarListas(ActionEvent event) throws DAOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editarListas.fxml"));
		Parent modal;
		try {
			modal = fxmlLoader.load();
			Stage modalStage = new Stage();
			modalStage.initModality(Modality.APPLICATION_MODAL);
			modalStage.initOwner(App.rootstage);
			Scene modalScene = new Scene(modal);
			modalStage.setScene(modalScene);
			modalStage.showAndWait();
			MySQLlistaReproduccionDAO aux = new MySQLlistaReproduccionDAO();
			ListadeListas.setAll(aux.mostrarTodos());
			listasPropiasU.setAll(aux.mostrarPorCreador(usuario));
			modalStage.setResizable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void editarCancion(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("cancionesController.fxml"));
		Parent modal;
		try {
			modal = fxmlLoader.load();
			Stage modalStage = new Stage();
			modalStage.initModality(Modality.APPLICATION_MODAL);
			modalStage.initOwner(App.rootstage);
			Scene modalScene = new Scene(modal);
			modalStage.setScene(modalScene);
			modalStage.showAndWait();
			modalStage.setResizable(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void editarUsuario(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("editUsuario.fxml"));
		Parent modal;
		try {
			modal = fxmlLoader.load();
			Stage modalStage = new Stage();
			modalStage.initModality(Modality.APPLICATION_MODAL);
			modalStage.initOwner(App.rootstage);
			Scene modalScene = new Scene(modal);
			modalStage.setScene(modalScene);
			modalStage.showAndWait();
			modalStage.setResizable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();
	}

	public void colocarInfo() {

		idCan.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getId() + "");
			return v;
		});
		;
		nombreCan.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getNombre());
			return v;
		});
		;
		generoCan.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getGenero().getNombre());
			return v;
		});
		;
		discoCan.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getDisk().getNombre());
			return v;
		});
		;
		this.listaCanciones.setItems(cancionLista);

		idPList.setCellValueFactory(lista -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(lista.getValue().getId() + "");
			return v;
		});
		;
		nombrePList.setCellValueFactory(lista -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(lista.getValue().getNombre());
			return v;
		});
		;
		subPList.setCellValueFactory(lista -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(lista.getValue().getListaSubscriptores().size() + "");
			return v;
		});
		;
		this.listasTotales.setItems(ListadeListas);

		colId.setCellValueFactory(listas -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(listas.getValue().getId() + "");
			return v;
		});
		;
		colNombre.setCellValueFactory(listas -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(listas.getValue().getNombre());
			return v;
		});
		;
		colCreador.setCellValueFactory(listas -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(listas.getValue().getDescripcion());
			return v;
		});
		;
		this.listasPropias.setItems(listasPropiasU);
	}

	public boolean showSub(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer suscribirte a " + nombre + "?");
		alert.setContentText("Si continuas tus listas seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}

	public boolean unSub(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer borrar tu suscripción " + nombre + "?");
		alert.setContentText("Si continuas tus listas seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
}