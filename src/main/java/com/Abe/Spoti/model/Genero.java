package com.Abe.Spoti.model;

public class Genero {
	
	protected Long id;
	protected String nombre;
	
	public Genero() {
		this(-1L, "NONE");
	}
	public Genero(String nombre) {
		this(-1L,nombre);
	}
	public Genero(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre =nombre;
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
	@Override
	public String toString() {
		return nombre;
	}

	
	
	
}
