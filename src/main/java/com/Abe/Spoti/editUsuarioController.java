package com.Abe.Spoti;

import java.util.Optional;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.model.Usuario;
import com.Abe.Spoti.model.UsuarioSingleton;
import com.Abe.Spoti.mySQLDAO.MySQLusuarioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editUsuarioController {

    @FXML
    private Button buttEdit;

    @FXML
    private Button buttExit;

    @FXML
    private TextField textContraseña;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtNombre;
    
    Usuario usuario;
    
    @FXML
	public void initialize() {
    	UsuarioSingleton transfer = UsuarioSingleton.getInstance();
		usuario = transfer.getUser();
	}
    /** 
     * Método que nos permite cambiar los parámetros del usuario con el que nos hemos logueado
     * @param event
     * @throws DAOException
     */
    @FXML
    void editarUsuario(ActionEvent event) throws DAOException {
    	String nombre = this.txtNombre.getText();
		String mail = this.txtCorreo.getText();
		String contraseña = this.textContraseña.getText();
		
		Usuario auxUS = new Usuario(usuario.getId(),nombre, contraseña, mail, usuario.getFoto());
		
		MySQLusuarioDAO aux = new MySQLusuarioDAO();

		if (!this.txtNombre.getText().trim().isEmpty() && !this.textContraseña.getText().trim().isEmpty()
				&& !this.txtCorreo.getText().trim().isEmpty()) {
			
			if (usuario.getId()==auxUS.getId()) {
				System.out.println(auxUS);
				aux.modificar(auxUS);
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setTitle("Informacion");
				alert.setContentText("Se ha añadido correctamente");
				alert.showAndWait();
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText(null);
				alert.setTitle("Error de edicion");
				alert.setContentText("Tiene que introducir su usuario");
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

    /**
	 * Metodo que devuelve true o false usado para confirmar una accion
	 * @param nombre de la cancion
	 * @return
	 */
	public boolean showEdit(String nombre) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirme la acción");
		alert.setHeaderText("¿Estas seguro de querer modificar tus datos " + nombre + "?");
		alert.setContentText("Si continuas tus datos seran modificados");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
    @FXML
	private void exit(ActionEvent event) {
		Stage stage = (Stage) this.buttExit.getScene().getWindow();
		stage.close();

	}
}