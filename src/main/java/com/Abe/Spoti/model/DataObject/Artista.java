package com.Abe.Spoti.Model.DataObject;


public class Artista {

	protected Long id;
	protected String nombre;
	protected String nacionalidad;
	protected String foto;
	
	public Artista() {
		this(-1L,"Default","Default","None");
	}
	
	public Artista(String nombre, String nacionalidad, String foto) {
		this(-1L,nombre,nacionalidad,foto);
	}

	public Artista(Long id, String nombre, String nacionalidad, String foto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.foto = foto;
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

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Artista other = (Artista) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "artist [id=" + id + ", nombre=" + nombre + 
				", nacionalidad=" + nacionalidad + ", foto=" + foto + "]";
	}
	
}
