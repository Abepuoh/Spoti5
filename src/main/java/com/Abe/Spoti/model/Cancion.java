package com.Abe.Spoti.model;

import java.util.Objects;

public class Cancion {
	
	protected Long id;
	protected String nombre;
	protected int duracion;
	protected Genero genero;
	protected Disco disk;
	
	public Cancion() {
		this(-1L,"Default",-1,new Genero(),new Disco());
	}
	
	public Cancion(String nombre, int duracion, Genero genero, Disco disk) {
		this(-1L,nombre,duracion,genero,disk);
	}

	public Cancion(Long id, String nombre, int duracion, Genero genero, Disco disk) {	
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.genero = genero;
		this.disk = disk;

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

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public Disco getDisk() {
		return disk;
	}

	public void setDisk(Disco disk) {
		this.disk = disk;
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
		Cancion other = (Cancion) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	
	
}
