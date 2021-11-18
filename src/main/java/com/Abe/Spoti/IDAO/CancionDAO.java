package com.Abe.Spoti.IDAO;

import java.util.List;

import com.Abe.Spoti.Model.Cancion;
import com.Abe.Spoti.Model.Genero;
import com.Abe.Spoti.Model.ListaReproduccion;

public interface CancionDAO extends IDAO<Cancion, Long>{
	
	/**
	 * Método que muestras las canciones dependiendo del género musical de las mismas
	 * @param aux es el género por el que queremos hacer el filtro
	 * @return una lista de canciones dependiendo de género que hayamos introducido
	 * @throws DAOException
	 */
	List<Cancion> mostrarPorGenero (Genero aux) throws DAOException;
	/**
	 * Método que muestra la cancion por el nombre introducido
	 * @param name que buscaremos en la base de datos
	 * @throws DAOException
	 */
	Cancion mostrarPorNombre(String name) throws DAOException;
	/**
	 * Metodo que muestra una lista de canciones de una playlist.
	 * @param lista contenedora de las canciones
	 * @return una lista de canciones
	 * @throws DAOException
	 */
	 List<Cancion> mostrarTodasEnPlaylist(ListaReproduccion lista) throws DAOException;
	
	
}
