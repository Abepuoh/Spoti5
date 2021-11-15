package com.Abe.Spoti.DAO;

import java.util.List;

import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;

public interface UsuarioDAO extends IDAO<Usuario, Long>{
	
	
	/**
	 * Método que añade una lista de reproducción a las listas del usuario
	 * @param aux la lista que queremos añadir
	 * @throws DAOException
	 */
	void añadirListaUsuario(Usuario auxU, ListaReproduccion aux) throws DAOException;
	
	/**
	 * Método que borra una lista de reproduccion de las listas que ya está subscrito 
	 * el usuario.
	 * @param aux la lista que queremos borrar
	 * @throws DAOException
	 */
	void borrarListaUsuario(ListaReproduccion aux) throws DAOException;
	
	/**
	 * Método que devuelve el usuario mediante dos parámetros que introduzcamos
	 * @param nAux es el nombre del usuario
	 * @param cAux es la contraseña del usuario
	 * @throws DAOException
	 */
	Usuario getUsuarioByNombreContraseña(String nAux, String cAux) throws DAOException;
	
	/**
	 * Método que devuelve las playList a las que está subscrito un usuario
	 * @return las playList
	 * @throws DAOException
	 */
	List<ListaReproduccion> getPlaylistSub(Usuario aux) throws DAOException;
}
