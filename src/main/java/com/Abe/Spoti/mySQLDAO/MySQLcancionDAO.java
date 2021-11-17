package com.Abe.Spoti.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.DAO.CancionDAO;
import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.model.Cancion;
import com.Abe.Spoti.model.Disco;
import com.Abe.Spoti.model.Genero;

public class MySQLcancionDAO extends Cancion implements CancionDAO {

	private static final String GETSONGS = "SELECT id, nombre, duracion ,id_genero,id_disco  FROM cancion";
	private static final String GETSONGBYID = "SELECT id, nombre, duracion ,id_genero,id_disco FROM cancion WHERE id=?";
	private static final String GETSONGBYGENERO = "SELECT id, nombre, duracion FROM cancion WHERE id_genero= ? ";
	private static final String GETSONGBYNOMBRE = "SELECT id, nombre, duracion ,id_genero,id_disco FROM cancion WHERE nombre= ? ";
	private final static String DELETESONG = "DELETE FROM cancion WHERE id =? ";
	private final static String CREATESONG = "INSERT INTO cancion (nombre, duracion ,id_genero,id_disco ) VALUES (?,?,?,?) ";
	private final static String EDITSONG = "UPDATE cancion SET nombre=?,duracion=?,id_genero=?,id_disco=? WHERE id=?";

	private Connection con= null;;

	public MySQLcancionDAO() {
		super();
	}

	public MySQLcancionDAO(String nombre, Float duracion, Genero genero, Disco disk) {
		super(nombre, duracion, genero, disk);
	}

	public MySQLcancionDAO(Long id, String nombre, Float duracion, Genero genero, Disco disk) {
		super(id, nombre, duracion, genero, disk);

	}

	public MySQLcancionDAO(Cancion dummy) {
		super(dummy.getId(), dummy.getNombre(), dummy.getDuracion(), dummy.getGenero(), dummy.getDisk());
	}

	@Override
	public void crear(Cancion aux) throws DAOException {
		if (id != -1) {
			modificar(aux);
		} else {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATESONG, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, aux.getNombre());
					ps.setFloat(2, aux.getDuracion());
					ps.setObject(3, aux.getGenero().getId().intValue());
					ps.setObject(4, aux.getDisk().getId().intValue());
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
	public void modificar(Cancion aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(EDITSONG);

				ps.setString(1, aux.getNombre());
				ps.setFloat(2, aux.getDuracion());
				ps.setObject(3, aux.getGenero().getId().intValue());
				ps.setObject(4, aux.getDisk().getId().intValue());
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
				ps = con.prepareStatement(DELETESONG);
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
	public List<Cancion> mostrarTodos() throws DAOException {
		con = MariaDBConexion.getConexion();
		List<Cancion> result = new ArrayList<Cancion>();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETSONGS);
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
	public Cancion mostrarPorId(Long id) throws DAOException {

		con = MariaDBConexion.getConexion();
		Cancion result = new Cancion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETSONGBYID);
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
	public List<Cancion> mostrarPorGenero(Genero aux) throws DAOException {

		con = MariaDBConexion.getConexion();
		List<Cancion> result = new ArrayList<Cancion>();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETSONGBYGENERO);
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
	
	@Override
	public Cancion mostrarPorNombre(String nombre)throws DAOException{
		con = MariaDBConexion.getConexion();
		Cancion result = new Cancion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETSONGBYNOMBRE);
				ps.setString(1, nombre);
				rs = ps.executeQuery();
				if (rs.next()) {
					result = convertir(rs);
				} else {
					throw new DAOException("No se ha encontrado ese registro");
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
	
	public Cancion convertir(ResultSet rs) throws SQLException, DAOException {
		Cancion cancion = new Cancion();
		Disco disco = new MySQLdiscoDAO().mostrarPorId(rs.getLong("id_disco"));
		Genero genero = new MySQLgeneroDAO().mostrarPorId(rs.getLong("id_genero"));
		cancion.setId(rs.getLong("id"));
		cancion.setNombre(rs.getString("nombre"));
		cancion.setDuracion(rs.getFloat("duracion"));
		cancion.setDisk(disco);
		cancion.setGenero(genero);
		return cancion;
	}

}
