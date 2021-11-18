package com.Abe.Spoti.Model.mySQLDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.Model.DataObject.Artista;
import com.Abe.Spoti.Model.DataObject.Cancion;
import com.Abe.Spoti.Model.DataObject.Disco;
import com.Abe.Spoti.Model.IDAO.DAOException;
import com.Abe.Spoti.Model.IDAO.DiscoDAO;

public class MySQLdiscoDAO extends Disco implements DiscoDAO {

	private final static String GETDISKBYID = "SELECT id, nombre, fecha, foto, reproducciones,id_artista FROM disco WHERE id = ?";
	private final static String GETDISKBYAUTHOR = "SELECT id, nombre, fecha,foto, reproducciones, id_artista FROM disco WHERE id_artista=?";
	private final static String GETDISKS = "SELECT id, nombre,fecha,foto,reproducciones,id_artista FROM disco";
	private final static String DELETEDISK = "DELETE FROM disco WHERE id = ?";
	private final static String CREATEDISK = "INSERT INTO disco (nombre, foto,fecha,id_artista,reproducciones) VALUES (?,?,?,?,?) ";
	private final static String EDITDISK = "UPDATE disco SET nombre=?,fecha = ?,foto=?, reproducciones =?, id_artista = ?"
			+ " WHERE id=?";

	private Connection con;
	public MySQLdiscoDAO() {
		super();
	}

	public MySQLdiscoDAO(Long id, String nombre, LocalDate fecha, String foto, int repro, Artista auth, List<Cancion>listaCanciones) {
		super(id, nombre, fecha, foto, repro, auth,listaCanciones);
	}

	public MySQLdiscoDAO(String nombre, LocalDate fecha, String foto, int repro, Artista auth,List<Cancion>listaCanciones) {
		super(-1L, nombre, fecha, foto, repro, auth,listaCanciones);
	}

	public MySQLdiscoDAO(Disco dummy) {
		super(dummy.getId(), dummy.getNombre(), dummy.getFecha(), dummy.getFoto(), dummy.getRepro(), dummy.getAuth(),dummy.getListaCanciones());
	}

	@Override
	public void crear(Disco aux) throws DAOException {
		if (id != -1) {
			modificar(aux);
		} else {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATEDISK, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, aux.getNombre());
					ps.setString(2, aux.getFoto());
					ps.setDate(3, Date.valueOf(aux.getFecha()));
					ps.setObject(4, aux.getAuth().getId().intValue());
					ps.setInt(5, aux.getRepro());
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
	public void modificar(Disco aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(EDITDISK);

				ps.setString(1, aux.getNombre());
				ps.setDate(2, Date.valueOf(aux.getFecha()));
				ps.setString(3, aux.getFoto());
				ps.setInt(4, aux.getRepro());
				ps.setObject(5, aux.getAuth().getId());
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
				ps = con.prepareStatement(DELETEDISK);
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
	public List<Disco> mostrarTodos() throws DAOException {

		List<Disco> result = new ArrayList<Disco>();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETDISKS);
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
	public Disco mostrarPorId(Long id) throws DAOException {
		Disco result = new Disco();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETDISKBYID);
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
	public List<Disco> mostrarDiscosPorArtista(Artista aux) throws DAOException {

		List<Disco> result = new ArrayList<Disco>();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETDISKBYAUTHOR);
				ps.setLong(1, aux.getId());
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

	public Disco convertir(ResultSet rs) throws SQLException, DAOException {
		Disco aux = new Disco();
		Artista ar = new MySQLartistaDAO().mostrarPorId(rs.getLong("id_artista"));
		aux.setId(rs.getLong("id"));
		aux.setNombre(rs.getString("nombre"));
		aux.setFecha(rs.getDate("fecha").toLocalDate());
		aux.setFoto(rs.getString("foto"));
		aux.setAuth(ar);
		return aux;
	}

	

}
