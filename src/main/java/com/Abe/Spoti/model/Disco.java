package com.Abe.Spoti.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Disco {
 
	protected Long id;
	protected String nombre;
	protected LocalDate fecha;
	protected String foto;
	protected int repro;
	protected Artista auth;
	protected List<Cancion>listaCanciones;

	
	public Disco() {
		this(-1L,"Default",LocalDate.now(),"None",0, new Artista(),new ArrayList<Cancion>());

	}
	public Disco(String nombre, LocalDate fecha, String foto, int repro,Artista auth, List<Cancion>listaCanciones) {
		this(-1L,nombre,fecha,foto,repro,auth, listaCanciones);
	}
	public Disco(Long id, String nombre, LocalDate fecha, String foto, 
			int repro, Artista auth, List<Cancion>listaCanciones ) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fecha = fecha;
		this.foto = foto;
		this.repro = repro;
		this.auth = auth;
		this.listaCanciones =listaCanciones;
		
	}
	
	public List<Cancion> getListaCanciones() {
		return listaCanciones;
	}
	public void setListaCanciones(List<Cancion> listaCanciones) {
		this.listaCanciones = listaCanciones;
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
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public int getRepro() {
		return repro;
	}
	public void setRepro(int repro) {
		this.repro = repro;
	}
	public Artista getAuth() {
		return auth;
	}
	public void setAuth(Artista auth) {
		this.auth = auth;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disco other = (Disco) obj;
		return id == other.id;
	}
	@Override
	public String toString() {
		return "Disco [id=" + id + ", nombre=" + nombre + ", fecha=" + fecha + ", foto=" + foto + ", repro=" + repro
				+ ", auth=" + auth + ", listaCanciones=" + listaCanciones + "]";
	}
	
	
	
	
}
