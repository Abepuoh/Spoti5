package com.Abe.Spoti.mySQLDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Abe.Spoti.DAO.DAOException;
import com.Abe.Spoti.DAO.UsuarioDAO;
import com.Abe.Spoti.MariaDBConnection.MariaDBConexion;
import com.Abe.Spoti.model.ListaReproduccion;
import com.Abe.Spoti.model.Usuario;

public class MySQLusuarioDAO extends Usuario implements UsuarioDAO {
	private final static String LOGINMENU = "SELECT nombre,contraseña FROM usuario WHERE nombre LIKE ? AND contraseña LIKE ?";
	private final static String CHECKlIST = "SELECT id_listaReproduccion, id_usuario FROM lista_usuario WHERE id_listaReproduccion=? AND id_usuario=?";
	private final static String GETUSERBYID = "SELECT id,nombre, correo ,foto,contraseña FROM usuario WHERE id = ?";
	private final static String GETUSERS = "SELECT id,nombre, correo ,foto,contraseña FROM usuario";
	private final static String GETUSERBYNAMEPASS = "SELECT id,nombre, correo ,foto,contraseña FROM usuario WHERE nombre= ? AND contraseña = ?";
	private final static String DELETEUSER = "DELETE FROM usuario WHERE id =? ";
	private final static String CREATEUSER = "INSERT INTO usuario (nombre, correo ,foto,contraseña ) VALUES (?,?,?,?) ";
	private final static String EDITUSER = "UPDATE usuario SET nombre=?, correo = ?, contraseña=? WHERE id=?";
	private final static String AÑADIRPLAYLIST = "INSERT INTO lista_usuario (id_listaReproduccion, id_usuario) VALUES (?,?)";
	private final static String BORRARPLAYLIST ="DELETE FROM lista_usuario WHERE id_listaReproduccion=? AND id_usuario=?";

	private Connection con;

	public MySQLusuarioDAO(Connection con) {
		this.con = con;
	}

	public MySQLusuarioDAO() {
		super();
	}

	public MySQLusuarioDAO(String nombre, String mail, String contraseña) {
		super(nombre,mail, contraseña);
	}

	public MySQLusuarioDAO(Long id, String nombre, String contraseña, String mail, String foto,
			List<ListaReproduccion> userPList) {
		super(id, nombre, contraseña, mail, foto, userPList);
	}

	public MySQLusuarioDAO(Usuario dummy) {
		super(dummy.getId(), dummy.getNombre(), dummy.getContraseña(), dummy.getMail(), dummy.getFoto(),
				dummy.getUserPList());
	}

	@Override
	public void crear(Usuario aux) throws DAOException {
			con = MariaDBConexion.getConexion();
			if (con != null) {
				PreparedStatement ps = null;
				ResultSet rs = null;
				try {
					ps = con.prepareStatement(CREATEUSER, Statement.RETURN_GENERATED_KEYS);

					ps.setString(1, aux.getNombre());
					ps.setString(2, aux.getMail());
					ps.setString(3, aux.getFoto());
					ps.setString(4, aux.getContraseña());
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


	@Override
	public void modificar(Usuario aux) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(EDITUSER);

				ps.setString(1, aux.getNombre());
				ps.setString(2, aux.getMail());
				ps.setString(3, aux.getContraseña());
				ps.setLong(4, aux.getId());
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
				ps = con.prepareStatement(DELETEUSER);
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
	public List<Usuario> mostrarTodos() throws DAOException {

		List<Usuario> result = new ArrayList<Usuario>();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETUSERS);
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
	public Usuario mostrarPorId(Long id) throws DAOException {

		Usuario result = new Usuario();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETUSERBYID);
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
	public void añadirListaUsuario( ListaReproduccion auxR,Usuario auxU) throws DAOException {

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(AÑADIRPLAYLIST);

				ps.setLong(1, auxR.getId());
				ps.setLong(2, auxU.getId());
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
	public void borrarListaUsuario(ListaReproduccion aux,Usuario auxU) throws DAOException {
		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(BORRARPLAYLIST);
				ps.setLong(1, aux.getId());
				ps.setLong(2, auxU.getId());
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
	public Usuario getUsuarioByNombreContraseña(String nAux, String cAux) throws DAOException {

		Usuario result = new Usuario();

		con = MariaDBConexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETUSERBYNAMEPASS);
				ps.setString(1, nAux);
				ps.setString(2, cAux);
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
	public boolean logIn(String nombre, String contraseña) throws DAOException {
		boolean logResult = false;
		try {
			Connection con = MariaDBConexion.getConexion();
			PreparedStatement ps = con.prepareStatement(LOGINMENU);
			ps.setString(1, nombre);
			ps.setString(2, contraseña);
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
	
	@Override
	public boolean checkSub(ListaReproduccion aux, Usuario auxU) throws DAOException {
		boolean logResult = false;
		try {
			Connection con = MariaDBConexion.getConexion();
			PreparedStatement ps = con.prepareStatement(CHECKlIST);
			ps.setLong(1, aux.getId());
			ps.setLong(2, auxU.getId());
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
	
	public Usuario convertir(ResultSet rs) throws SQLException {
		Usuario user = new Usuario();
		user.setId(rs.getLong("id"));
		user.setNombre(rs.getString("nombre"));
		user.setMail(rs.getString("correo"));
		user.setFoto(rs.getString("foto"));
		user.setContraseña(rs.getString("contraseña"));
		return user;
	}


}
