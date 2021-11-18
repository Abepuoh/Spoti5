package com.Abe.Spoti.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.IDAO.DAOException;
import com.Abe.Spoti.IDAO.GeneroDAO;
import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.Model.Genero;

public class MySQLgeneroDAO extends Genero implements GeneroDAO {

	private final static String GETGENREBYID = "SELECT id, nombre FROM genero WHERE id = ?";
	private final static String GETGENRES = "SELECT id, nombre FROM genero";
	private final static String DELETEGENRE = "DELETE FROM genero WHERE id = ?";
	private final static String CREATEGENRE = "INSERT INTO genero (nombre) VALUES (?) ";
	private final static String EDITGENRE = "UPDATE genero SET nombre=?" + "WHERE id=?";

	private Connection con = null;
	
	public MySQLgeneroDAO(Connection con) {
		this.con = con;
	}

	public MySQLgeneroDAO() {
		super();
	}

	public MySQLgeneroDAO(Long id, String nombre) {
		super(id, nombre);
	}

	public MySQLgeneroDAO(Genero dummy) {
		super(dummy.getId(),dummy.getNombre());
	}
	
	@Override
	public void crear(Genero aux) throws DAOException {
		if (id != -1) {
			modificar(aux);
		} else {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATEGENRE, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, aux.getNombre());
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
	public void modificar(Genero aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(EDITGENRE);

				ps.setString(1, aux.getNombre());
				ps.setLong(2, aux.getId());
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
				ps = con.prepareStatement(DELETEGENRE);
				ps.setLong(1, id);
				ps.executeUpdate();
				this.id = -1L;
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
	public List<Genero> mostrarTodos() throws DAOException {
		
		List<Genero> result = new ArrayList<Genero>();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETGENRES);
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
	public Genero mostrarPorId(Long id) throws DAOException {
		Genero result = new Genero();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETGENREBYID);
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
	
	public Genero convertir(ResultSet rs) throws SQLException {
		Long id = rs.getLong("id");
		String nombre = rs.getString("nombre");
		Genero genero = new Genero(id, nombre);
		return genero;
	}
	


	
}
