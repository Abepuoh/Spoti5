package com.Abe.Spoti.Model.IDAO;

import com.Abe.Spoti.Model.DataObject.Artista;

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
