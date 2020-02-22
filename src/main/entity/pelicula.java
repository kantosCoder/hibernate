package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pelicula")
public class pelicula {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;
	
	@Column(name="Nombre")
	private String Nombre;
	
	@Column(name="Director")
	private int Director;

	@Column(name="Genero")
	private int Genero;

	@Column(name="Genero_secundario")
	private int Genero_secundario;

	@Column(name="Anyo")
	private int Anyo;

	@Column(name="Descripcion")
	private String Descripcion;

	@Column(name="Caratula")
	private String Caratula;

	@Column(name="URL_IMDB")
	private String URL_IMDB;

	@Column(name="Habilitado")
	private boolean Habilitado;
	
	public pelicula() {}

	public pelicula(String nombre, int director, int genero, int genero_secundario, int anyo, String descripcion,
			String caratula, String uRL_IMDB, boolean habilitado) {
		Nombre = nombre;
		Director = director;
		Genero = genero;
		Genero_secundario = genero_secundario;
		Anyo = anyo;
		Descripcion = descripcion;
		Caratula = caratula;
		URL_IMDB = uRL_IMDB;
		Habilitado = habilitado;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public int getDirector() {
		return Director;
	}

	public void setDirector(int director) {
		Director = director;
	}

	public int getGenero() {
		return Genero;
	}

	public void setGenero(int genero) {
		Genero = genero;
	}

	public int getGenero_secundario() {
		return Genero_secundario;
	}

	public void setGenero_secundario(int genero_secundario) {
		Genero_secundario = genero_secundario;
	}

	public int getAnyo() {
		return Anyo;
	}

	public void setAnyo(int anyo) {
		Anyo = anyo;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getCaratula() {
		return Caratula;
	}

	public void setCaratula(String caratula) {
		Caratula = caratula;
	}

	public String getURL_IMDB() {
		return URL_IMDB;
	}

	public void setURL_IMDB(String uRL_IMDB) {
		URL_IMDB = uRL_IMDB;
	}

	public boolean isHabilitado() {
		return Habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		Habilitado = habilitado;
	}
	
	
}
