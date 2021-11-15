package com.Abe.Spoti.DAO;

import java.util.List;

import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.Genero;

public interface CancionDAO extends IDAO<Cancion, Long>{
	
	/**
	 * Método que muestras las canciones dependiendo del género musical de las mismas
	 * @param aux es el género por el que queremos hacer el filtro
	 * @return una lista de canciones dependiendo de género que hayamos introducido
	 * @throws DAOException
	 */
	List<Cancion> mostrarPorGenero (Genero aux) throws DAOException;


}
