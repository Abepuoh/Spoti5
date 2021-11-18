package com.Abe.Spoti.IDAO;

import java.util.List;

public interface IDAO <T,K> {
	/**
	 * Method to create new Object
	 * @param aux object
	 */
	 void crear (T aux) throws DAOException;
	/**
	 * Method to modify an Object
	 * @param aux object
	 */
	void modificar (T aux) throws DAOException;
	/**
	 * Method to delete an Object
	 * @param aux object
	 */
	void borrar (K id) throws DAOException;
	/**
	 * Method to getAll Objects we want
	 */
	List<T>mostrarTodos() throws DAOException;
	/**
	 * Method get an Objects by id
	 * @param id variable type long
	 */
	T mostrarPorId (K id) throws DAOException;	

}
