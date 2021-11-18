package com.Abe.Spoti.Model.DataObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {
	protected Long id;
	protected String nombre;
	protected String contraseña;
	protected String mail;
	protected String foto;
	protected List<ListaReproduccion> userPList;
	
	
	public Usuario() {
		this(-1L,"Default","Default","Default","Default",new ArrayList<ListaReproduccion>());
	}
	
	public Usuario(String nombre,String mail, String contraseña) {
		this(-1L,nombre,mail,contraseña,"Por defecto",new ArrayList<ListaReproduccion>());
	}

	public Usuario(Long id,String nombre, String mail, String foto, String contraseña) {
		this(id,nombre,mail,foto,contraseña,new ArrayList<ListaReproduccion>());
	}
	public Usuario(String nombre, String mail, String foto,String contraseña) {
		this(-1L,nombre,contraseña,mail,foto,new ArrayList<ListaReproduccion>());
	}
	public Usuario(Long id, String nombre,String contraseña,String mail, String foto, List<ListaReproduccion> userPList) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.contraseña = contraseña;
		this.mail = mail;
		this.foto = foto;
		this.userPList = userPList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<ListaReproduccion> getUserPList() {
		return userPList;
	}

	public void setUserPList(List<ListaReproduccion> userPList) {
		this.userPList = userPList;
	}
	
	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contraseña, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return contraseña == other.contraseña && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", nombre=" + nombre + ", contraseña=" + contraseña + ", mail=" + mail + ", foto="
				+ foto + "]";
	}

	

}
