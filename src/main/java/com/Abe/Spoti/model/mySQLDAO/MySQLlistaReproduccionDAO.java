package com.Abe.Spoti.Model.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.Model.DataObject.Cancion;
import com.Abe.Spoti.Model.DataObject.ListaReproduccion;
import com.Abe.Spoti.Model.DataObject.Usuario;
import com.Abe.Spoti.Model.IDAO.DAOException;
import com.Abe.Spoti.Model.IDAO.ListaReproduccionDAO;

public class MySQLlistaReproduccionDAO extends ListaReproduccion implements ListaReproduccionDAO {

	private final static String GETPLAYLISTBYID = "SELECT id, nombre, descripcion,id_usuario FROM listareproduccion WHERE id = ?";
	private final static String GETPLAYLISTS = "SELECT  id, nombre, descripcion,id_usuario FROM listareproduccion";
	private final static String CHECKSONG= "SELECT id_cancion, id_listaReproduccion FROM cancion_lista WHERE id_cancion=? AND id_listaReproduccion=?";
	private final static String DELETEPLAYLIST = "DELETE FROM listareproduccion WHERE id = ?";
	private final static String CREATEPLAYLIST = "INSERT INTO listareproduccion (nombre, descripcion,id_usuario) VALUES (?,?,?)";
	private final static String EDITARPLAYLIST = "UPDATE listareproduccion SET nombre=?,descripcion=?,id_usuario=? WHERE id=?";
	private final static String GETPLAYLISTBYCREATOR = "SELECT id, nombre, descripcion,id_usuario FROM listareproduccion WHERE id_usuario = ?";
	private final static String AÑADIRCANCIONAPL = "INSERT INTO cancion_lista (id_cancion, id_listaReproduccion) VALUES (?,?)";
	private final static String BORRARCANCIONPL ="DELETE FROM cancion_lista WHERE id_cancion=? AND id_listaReproduccion=?";
	
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
	
	@Override
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

	@Override
	public void añadirCancion(Cancion cancion,ListaReproduccion lista) throws DAOException {

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(AÑADIRCANCIONAPL);

				ps.setLong(1, cancion.getId());
				ps.setLong(2, lista.getId());
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
	public void borrarCancion(Cancion cancion,ListaReproduccion lista) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(BORRARCANCIONPL);

				ps.setLong(1, cancion.getId());
				ps.setLong(2, lista.getId());
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
	public boolean checkSong(Cancion cancion, ListaReproduccion lista) throws DAOException {
		boolean logResult = false;
		try {
			Connection con = MariaDBConexion.getConexion();
			PreparedStatement ps = con.prepareStatement(CHECKSONG);
			ps.setLong(1, cancion.getId());
			ps.setLong(2, lista.getId());
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				logResult = false;
			} else {
				logResult = true;
			}
		} catch (SQLException err) {
			throw new DAOException("Error SQL :", err);
		}
		return logResult;
		
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
