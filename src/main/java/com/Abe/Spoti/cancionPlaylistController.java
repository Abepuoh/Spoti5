package com.Abe.Spoti;

import com.Abe.Spoti.Model.DataObject.Cancion;
import com.Abe.Spoti.Model.mySQLDAO.MySQLcancionDAO;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class cancionPlaylistController {

	@FXML
	private Button buttExit;

	@FXML
	private TableColumn<Cancion, String> discoCan;

	@FXML
	private TableColumn<Cancion, String> generoCan;

	@FXML
	private TableColumn<Cancion, String> idCan;

	@FXML
	private TableView<Cancion> listaCanciones;

	@FXML
	private TableColumn<Cancion, String> nombreCan;

	protected static ObservableList<Cancion> cancionLista = FXCollections.observableArrayList();
	MySQLcancionDAO aux = new MySQLcancionDAO();
	@FXML
	public void initialize() {
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
	}

	@FXML
	void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();
	}

}
