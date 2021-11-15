package com.Abe.Spoti;

import java.io.IOException;
import java.util.List;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLcancionDAO;
import com.Abe.Spoti.mySQLDAO.MySQLlistaReproduccionDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Cancion,String> cancionesR;
	@FXML
	private TableColumn<Cancion, String> discoCan;

	@FXML
	private TableView<Cancion> listaCanciones;

	protected Usuario usuario;

	protected MySQLcancionDAO cDao = new MySQLcancionDAO();
	protected static List<Cancion> cancionLista;
	protected MySQLlistaReproduccionDAO lDao = new MySQLlistaReproduccionDAO();
	protected static List<ListaReproduccion> ListadeListas, listasCreadas;

	@FXML
	public void initialize() throws DAOException {

		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		cancionLista = cDao.mostrarTodos();
		ListadeListas = lDao.mostrarTodos();
		listasCreadas = lDao.mostrarPorCreador(usuario);
		System.out.println(listasCreadas.toString());
		colocarInfo();

	}

	@FXML
	public void editarListas(ActionEvent event) {
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
			modalStage.setResizable(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void suscribirse(ActionEvent event) {

	}

	@FXML
	public void desuscribirse(ActionEvent event) {

	}

	@FXML
	public void editarCancion(ActionEvent event) {

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

	@FXML
	public void seleccionar(MouseEvent event) {

	}

	public void colocarInfo() {
		
		listaCanciones.setItems(FXCollections.observableArrayList(cancionLista));
		idCan.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getId()+"");
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
		
		this.listaCanciones.refresh();

		listasTotales.setItems(FXCollections.observableArrayList(ListadeListas));
		
		idPList.setCellValueFactory(lista -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(lista.getValue().getId()+"");
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
			v.setValue(lista.getValue().getListaSubscriptores().size()+"");
			return v;
		});
		;
		this.listasTotales.refresh();
		
		listasPropias.setItems(FXCollections.observableArrayList(listasCreadas));
		colId.setCellValueFactory(listas -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(listas.getValue().getId()+"");
			return v;
		});
		;
		colNombre.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getNombre());
			return v;
		});
		;
		colCreador.setCellValueFactory(eachsong -> {
			SimpleStringProperty v = new SimpleStringProperty();
			v.setValue(eachsong.getValue().getDescripcion());
			return v;
		});
		;
		this.listasPropias.refresh();
		
		
	}
}
