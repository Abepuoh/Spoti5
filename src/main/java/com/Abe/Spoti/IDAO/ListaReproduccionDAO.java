package com.Abe.Spoti.IDAO;

import java.util.List;

import com.Abe.Spoti.Model.Cancion;
import com.Abe.Spoti.Model.ListaReproduccion;
import com.Abe.Spoti.Model.Usuario;

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
      * @throws DAOException
      */
     void añadirCancion(Cancion cancion,ListaReproduccion lista) throws DAOException;
     /**
      * Método que borra una canción de una PlayList
      * @param cancion  que queremos borrar
      * @param lista  de la que borrar
      * @throws DAOException
      */
     void borrarCancion(Cancion cancion,ListaReproduccion lista) throws DAOException;
     /**
      * Método que checkea si una canción esta en la playlist
      * @param cancion que queremos buscar
      * @param lista donde buscar
      * @return true o false dependiendo de si está o no.
      * @throws DAOException
      */
     boolean checkSong(Cancion cancion,ListaReproduccion lista)throws DAOException;
}
