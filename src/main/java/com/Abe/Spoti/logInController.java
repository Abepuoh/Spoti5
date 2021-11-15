package com.Abe.Spoti;

import java.io.IOException;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLusuarioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class logInController {

	@FXML
	private Button LogButt;

	@FXML
	private Hyperlink buttCreate;

	@FXML
	private PasswordField txtPass;

	@FXML
	private TextField txtUser;

	@FXML
	public void initialize() {
	}

	@FXML
	void addUser(ActionEvent event) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("registroUser.fxml"));
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
	void logUser(ActionEvent event) throws IOException, DAOException {
		String name = this.txtUser.getText();
		String password = this.txtPass.getText();
		MySQLusuarioDAO aux = new MySQLusuarioDAO();
		if (aux.logIn(name, password)) {
			this.txtPass.clear();
			this.txtUser.clear();
			UsuarioSingleton data = UsuarioSingleton.getInstance();
			data.setUser(aux.getUsuarioByNombreContraseña(name, password));
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScreen.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setHeaderText(null);
			alert.setTitle("Error de acceso");
			alert.setContentText("Has introducido mal algun dato");
			alert.showAndWait();
		}
	}
}