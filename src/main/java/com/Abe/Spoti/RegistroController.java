package com.Abe.Spoti;

import com.Abe.Spoti.IDAO.DAOException;
import com.Abe.Spoti.Model.Usuario;
import com.Abe.Spoti.mySQLDAO.MySQLusuarioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

	@FXML
	private Button buttCreate;

	@FXML
	private Button buttSave;

	@FXML
	private TextField txtEmail;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtPass;

	@FXML
	public void initialize() {
	}

	@FXML
	void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttSave.getScene().getWindow();
		stage.close();
	}
	/**
	 * Metodo que sirve para guardar usuarios nuevos en la base de datos
	 * @param event
	 */
	@FXML
	void saveUser(ActionEvent event) {

		String name = this.txtName.getText();
		String email = this.txtEmail.getText();
		String password = this.txtPass.getText();

		if (!this.txtName.getText().trim().isEmpty() && !this.txtPass.getText().trim().isEmpty()
				&& !this.txtEmail.getText().trim().isEmpty()) {
			try {
				MySQLusuarioDAO aux = new MySQLusuarioDAO();
				if (!aux.logIn(name,password)) {
					Usuario dummy = new Usuario(name,password,email);
					aux.crear(dummy);
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

}