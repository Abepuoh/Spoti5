package com.Abe.Spoti.Model;

public class UsuarioSingleton {

	private Usuario usuario;
	private final static UsuarioSingleton INSTANCE = new UsuarioSingleton();

	public UsuarioSingleton() {
		  }

	public static UsuarioSingleton getInstance() {
		return INSTANCE;
	}

	public void setUser(Usuario u) {
		this.usuario = u;
	}

	public Usuario getUser() {
		return this.usuario;

	}
}
