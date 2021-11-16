package com.Abe.Spoti.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.DAO.ListaReproduccionDAO;
import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;

public class MySQLlistaReproduccionDAO extends ListaReproduccion implements ListaReproduccionDAO {

	private final static String GETPLAYLISTBYID = "SELECT id, nombre, descripcion,id_usuario FROM listareproduccion WHERE id = ?";
	private final static String GETPLAYLISTS = "SELECT  id, nombre, descripcion,id_usuario FROM listareproduccion";
	private final static String DELETEPLAYLIST = "DELETE FROM listareproduccion WHERE id = ?";
	private final static String CREATEPLAYLIST = "INSERT INTO listareproduccion (nombre, descripcion,id_usuario) VALUES (?,?,?)";
	private final static String EDITARPLAYLIST = "UPDATE listareproduccion SET nombre=?,descripcion=?,id_usuario=? WHERE id=?";
	private final static String GETPLAYLISTBYCREATOR = "SELECT id, nombre, descripcion,id_usuario FROM listareproduccion WHERE id_usuario = ?";
	
	
	private Connection con = null;

	@Override
	public void crear(ListaReproduccion aux) throws DAOException {
		if (id != -1) {
			modificar(aux);
		} else {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATEPLAYLIST, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, aux.getNombre());
					ps.setString(2, aux.getDescripcion());
					ps.setObject(3, aux.getCreador().getId().intValue());
					ps.executeUpdate();
					// Only execute if u insert RETURN_GENERATED_KEYS
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						aux.setId(rs.getLong(1));
					}
				} catch (SQLException err) {
					throw new DAOException("Error SQL en : ", err);
				} finally {
					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException err) {
							throw new DAOException("Error SQL :", err);
						}
					}
					if (rs != null) {
						try {
							rs.close();
						} catch (SQLException err) {
							throw new DAOException("Error SQL :", err);
						}
					}
				}
			}
		}
	}

	@Override
	public void modificar(ListaReproduccion aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(EDITARPLAYLIST);

				ps.setString(1, aux.getNombre());
				ps.setString(2, aux.getDescripcion());
				ps.setObject(3, aux.getCreador().getId().intValue());
				ps.setLong(5, aux.getId());
				ps.executeUpdate();

			} catch (SQLException err) {
				throw new DAOException("Error SQL en : ", err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
			}
		}
	}

	@Override
	public void borrar(Long id) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(DELETEPLAYLIST);
				ps.setLong(1, id);
				ps.executeUpdate();
			} catch (SQLException err) {
				throw new DAOException("Error SQL :", err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
			}
		}
	}

	@Override
	public List<ListaReproduccion> mostrarTodos() throws DAOException {

		con = MariaDBConexion.getConexion();
		List<ListaReproduccion> result = new ArrayList<ListaReproduccion>();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETPLAYLISTS);
				rs = ps.executeQuery();

				while (rs.next()) {

					result.add(convertir(rs));
				}

			} catch (SQLException err) {
				throw new DAOException("Error SQL :", err);

			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
			}
		}
		return result;
	}

	@Override
	public ListaReproduccion mostrarPorId(Long id) throws DAOException {
		con = MariaDBConexion.getConexion();
		ListaReproduccion result = new ListaReproduccion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETPLAYLISTBYID);
				ps.setLong(1, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					result = convertir(rs);
				} else {
					throw new DAOException("No se ha encontrado ese registro");
				}
			} catch (SQLException err) {
				throw new DAOException("Error SQL : ", err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
			}
		}
		return result;
	}
	
	public List<ListaReproduccion> mostrarPorCreador(Usuario aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		List<ListaReproduccion> result = new ArrayList<ListaReproduccion>();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETPLAYLISTBYCREATOR);
				ps.setLong(1, aux.getId());
				rs = ps.executeQuery();
				while (rs.next()) {

					result.add(convertir(rs));
				}
			} catch (SQLException err) {
				throw new DAOException("Error SQL : ", err);
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException err) {
						throw new DAOException("Error SQL :", err);
					}
				}
			}
		}
		return result;
	}

	
	public void añadirCancion(Cancion cancion) {
		List<Cancion> canciones = new ArrayList<>();
		
	}
	
	public void borrarCancion(Cancion cancion) {
		List<Cancion> canciones = new ArrayList<>();
		
	}
	
	public List<Cancion> mostrarCanciones(){
		List<Cancion> canciones = new ArrayList<>();
		
		return canciones;
	}

	public ListaReproduccion convertir(ResultSet rs) throws SQLException, DAOException {
		ListaReproduccion aux = new ListaReproduccion();
		Usuario ar = new MySQLusuarioDAO().mostrarPorId(rs.getLong("id_usuario"));
		aux.setId(rs.getLong("id"));
		aux.setNombre(rs.getString("nombre"));
		aux.setDescripcion(rs.getString("descripcion"));
		aux.setCreador(ar);
		return aux;
	}

}
