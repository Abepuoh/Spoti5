package com.Abe.Spoti.Model.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.Model.DataObject.Artista;
import com.Abe.Spoti.Model.IDAO.ArtistaDAO;
import com.Abe.Spoti.Model.IDAO.DAOException;

public class MySQLartistaDAO extends Artista implements ArtistaDAO {

	private final static String GETARTISTBYID = "SELECT id, nombre, nacionalidad,foto FROM artista WHERE id = ?";
	private final static String GETARTISTS = "SELECT id, nombre, nacionalidad,foto FROM artista";
	private final static String DELETEARTIST = "DELETE FROM artista WHERE id = ?";
	private final static String CREATEARTIST = "INSERT INTO artista (nombre, nacionalidad,foto) VALUES (?,?,?) ";
	private final static String EDITARTIST = "UPDATE artista SET nombre=?,nacionalidad=?,foto=? WHERE id=?";

	private Connection con;

	public MySQLartistaDAO() {
		super();
	}

	public MySQLartistaDAO(String nombre, String nacionalidad, String foto) {
		super(nombre, nacionalidad, foto);
	}

	public MySQLartistaDAO(Long id, String nombre, String nacionalidad, String foto) {
		super(id, nombre, nacionalidad, foto);

	}

	public MySQLartistaDAO(Artista dummy) {
		super(dummy.getId(),dummy.getNombre(),dummy.getNacionalidad(),dummy.getFoto());
	}

	@Override
	public void modificar(Artista aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(EDITARTIST);

				ps.setString(1, aux.getNombre());
				ps.setString(2, aux.getNacionalidad());
				ps.setString(3, aux.getFoto());
				ps.setLong(4, aux.getId());

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
	public void borrar(Long id) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(DELETEARTIST);
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
	public void borrar(Artista aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(DELETEARTIST);
				ps.setLong(1, aux.getId());
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
	public List<Artista> mostrarTodos() throws DAOException {
		con = MariaDBConexion.getConexion();
		List<Artista> result = new ArrayList<Artista>();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETARTISTS);
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
	public Artista mostrarPorId(Artista aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		Artista result = new Artista();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETARTISTBYID);
				ps.setLong(1, aux.getId());
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

	public Artista mostrarPorId(Long id) throws DAOException {
		con = MariaDBConexion.getConexion();
		Artista result = new Artista();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETARTISTBYID);
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
	public void crear(Artista aux) throws DAOException {
		if (id != -1) {
			modificar(aux);
		} else {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATEARTIST, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, aux.getNombre());
					ps.setString(2, aux.getNacionalidad());
					ps.setString(3, aux.getFoto());

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

	public Artista convertir(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String nombre = rs.getString("nombre");
		String nacionalidad = rs.getString("nacionalidad");
		String foto = rs.getString("foto");
		Artista artista = new Artista(id, nombre, nacionalidad, foto);
		return artista;
	}

}