package com.Abe.Spoti.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListaReproduccion {
		
	protected Long id;
	protected String nombre;
	protected String descripcion;
	protected Usuario creador;
	protected List<Cancion> canciones;
	protected List<Usuario> listaSubscriptores;
	
	public ListaReproduccion() {
		this(-1L,"RockyAndCocky","RockAndPunch",new Usuario(),new ArrayList<Cancion>(),new ArrayList<Usuario>());
	}
	
	public ListaReproduccion(String nombre, String descripcion, Usuario creador) {
		this(-1L,nombre,descripcion,creador,new ArrayList<Cancion>(),new ArrayList<Usuario>());
	}

	public ListaReproduccion(Long id, String nombre, String descripcion, Usuario creador, List<Cancion> canciones,
			List<Usuario> listaSubscriptores) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.creador = creador;
		this.canciones = canciones;
		this.listaSubscriptores = listaSubscriptores;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getCreador() {
		return creador;
	}

	public void setCreador(Usuario creador) {
		this.creador = creador;
	}

	public List<Cancion> getCanciones() {
		return canciones;
	}

	public void setCanciones(List<Cancion> canciones) {
		this.canciones = canciones;
	}

	public List<Usuario> getListaSubscriptores() {
		return listaSubscriptores;
	}

	public void setListaSubscriptores(List<Usuario> listaSubscriptores) {
		this.listaSubscriptores = listaSubscriptores;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListaReproduccion other = (ListaReproduccion) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "ListaReproduccion [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", creador="
				+ creador + "]";
	}
	
	
}
