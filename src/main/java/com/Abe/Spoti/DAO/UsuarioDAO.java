package com.Abe.Spoti.DAO;

import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;

public interface UsuarioDAO extends IDAO<Usuario, Long>{
	
	
	/**
	 * Método que añade una lista de reproducción a las listas del usuario
	 * @param aux la lista que queremos añadir
	 * @throws DAOException
	 */
	void añadirListaUsuario( ListaReproduccion aux,Usuario auxU) throws DAOException;
	
	/**
	 * Método que borra una lista de reproduccion de las listas que ya está subscrito 
	 * el usuario.
	 * @param aux la lista que queremos borrar
	 * @throws DAOException
	 */
	void borrarListaUsuario(ListaReproduccion aux,Usuario auxU) throws DAOException;
	
	/**
	 * Método que devuelve el usuario mediante dos parámetros que introduzcamos
	 * @param nAux es el nombre del usuario
	 * @param cAux es la contraseña del usuario
	 * @throws DAOException
	 */
	Usuario getUsuarioByNombreContraseña(String nAux, String cAux) throws DAOException;
	
	/**
	 * Método que devuelve true o false en función de si el usuario esta logueado o no
	 * @param nombre del usuario	
	 * @param contraseña del usuario	
	 * @return true o false si esta logged
	 * @throws DAOException
	 */
	boolean logIn(String nombre, String contraseña) throws DAOException;
	/**
	 * Checkea si estas suscrito a una playlist o no
	 * @param aux la lista que quieres añadir
	 * @param auxU el usuario que añadiremos
	 * @return true o false si esta suscrito o no
	 * @throws DAOException
	 */
	boolean checkSub(ListaReproduccion aux,Usuario auxU)throws DAOException;
}
