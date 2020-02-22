package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;
	
	@Column(name="Nick")
	private String Nick;

	@Column(name="Pass")
	private String Pass;

	@Column(name="Rol")
	private int Rol;

	@Column(name="Url_foto")
	private String Url_foto;

	@Column(name="Habilitado")
	private boolean Habilitado;
	
	public usuario()
	{}

	public usuario(String nick, String pass, int rol, String url_foto, boolean habilitado) {
		Nick = nick;
		Pass = pass;
		Rol = rol;
		Url_foto = url_foto;
		Habilitado = habilitado;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String nick) {
		Nick = nick;
	}

	public String getPass() {
		return Pass;
	}

	public void setPass(String pass) {
		Pass = pass;
	}

	public int getRol() {
		return Rol;
	}

	public void setRol(int rol) {
		Rol = rol;
	}

	public String getUrl_foto() {
		return Url_foto;
	}

	public void setUrl_foto(String url_foto) {
		Url_foto = url_foto;
	}

	public boolean isHabilitado() {
		return Habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		Habilitado = habilitado;
	}
	
	}
