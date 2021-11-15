package com.Abe.Spoti.DAO;

import java.util.List;

import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;

public interface ListaReproduccionDAO extends IDAO<ListaReproduccion, Long>{
	
	 /**
	  * Método que muestras las listas que ha creado un usuario.
	  * @param aux es el usuario para buscar	
	  * @return la lista o listas de los usuarios
	  * @throws DAOException
	  */
     List<ListaReproduccion> mostrarPorCreador(Usuario aux) throws DAOException;
     
	//AÑADIR CANCION A PLAYLIST
	//BORRAR CANCION DE PLAYLIST
	//MOSTRAR CANCIONES
	
}
