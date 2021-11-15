package com.Abe.Spoti.DAO;

import com.Abe.Spoti.model.Artista;

public interface ArtistaDAO extends IDAO<Artista,Long>{
	
	/**
	 * Metodo sobreescrito que borra un Artista
	 * @param aux Artista
	 * @throws DAOException
	 */
	void borrar (Artista aux) throws DAOException;
	/**
	 * Metodo sobreescrito que muestra introduciendo un Artista
	 * @param aux Artista
	 * @throws DAOException
	 */
	Artista mostrarPorId (Artista aux) throws DAOException;
	
}
