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
	private TableColumn<MySQLcancionDAO, String> AutorCan;
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
	private Button buttNext;

	@FXML
	private Button buttPause;

	@FXML
	private Button buttPlay;

	@FXML
	private Button buttPrevious;

	@FXML
	private Button buttSub;

	@FXML
	private TableColumn<ListaReproduccion, Long> idPList;
	@FXML
	private TableColumn<ListaReproduccion, String> nombrePList;
	@FXML
	private TableColumn<ListaReproduccion, Integer> subPList;
	@FXML
	private TableView<ListaReproduccion> listasTotales;

	@FXML
	private TableColumn<Cancion, Long> idCan;

	@FXML
	private TableColumn<Cancion, String> nombreCan;

	@FXML
	private TableColumn<Cancion, String> generoCan;

	@FXML
	private TableColumn<Cancion, String> discoCan;

	@FXML
	private TableView<Cancion> listaCanciones;

	protected Usuario usuario;

	protected MySQLcancionDAO cDao = new MySQLcancionDAO();
	protected static List<Cancion> cancionLista;
	protected MySQLlistaReproduccionDAO lDao = new MySQLlistaReproduccionDAO();
	protected static List<ListaReproduccion> ListadeListas;

	@FXML
	public void initialize() throws DAOException {

		UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
		cancionLista = cDao.mostrarTodos();
		ListadeListas = lDao.mostrarTodos();

		listaCanciones.setItems(FXCollections.observableArrayList(cancionLista));
		this.idCan.setCellValueFactory(new PropertyValueFactory<Cancion, Long>("id"));
		this.nombreCan.setCellValueFactory(new PropertyValueFactory<Cancion, String>("nombre"));
		this.generoCan.setCellValueFactory(new PropertyValueFactory<Cancion, String>("genero"));
		this.discoCan.setCellValueFactory(new PropertyValueFactory<Cancion, String>("disk"));

		
		
		listasTotales.setItems(FXCollections.observableArrayList(ListadeListas));
		
		idPList.setCellValueFactory(playList->{
            SimpleStringProperty v=new SimpleStringProperty();
            try {           
             v.setValue(playList.getValue().getId().toString());
         } catch (DAOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
            return v;
        });
		this.nombrePList.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, String>("nombre"));
		this.subPList.setCellValueFactory(new PropertyValueFactory<ListaReproduccion, Integer>("listaSubscriptores"));

	}

    @FXML
    void anteriorCancion(ActionEvent event) {
    
    }

    @FXML
    void editarListas(ActionEvent event) {
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
    void suscribirse(ActionEvent event) {

    }

    @FXML
    void desuscribirse(ActionEvent event) {

    }

    @FXML
    void editarCancion(ActionEvent event) {

    }

    @FXML
    void editarUsuario(ActionEvent event) {
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
    void exit(ActionEvent event) {
    	Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();
    }

    @FXML
    void pause(ActionEvent event) {

    }

    @FXML
    void play(ActionEvent event) {

    }

    @FXML
    void seleccionar(MouseEvent event) {

    }

    @FXML
    void siguienteCancion(ActionEvent event) {

    }
	
}
