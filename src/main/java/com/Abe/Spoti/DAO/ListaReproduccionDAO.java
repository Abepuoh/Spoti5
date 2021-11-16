package com.Abe.Spoti.DAO;

import java.util.List;

import com.Abe.Spoti.model.Cancion;
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
     /**
      * Método que añade una canción a una PlayList
      * @param cancion que queremos añadir
      * @param lista a la que añadir cancion
      * @return tr
      * @throws DAOException
      */
     void añadirCancion(Cancion cancion,ListaReproduccion lista) throws DAOException;

     void borrarCancion(Cancion cancion,ListaReproduccion lista) throws DAOException;
}
