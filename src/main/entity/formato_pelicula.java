package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="formato_pelicula")
public class formato_pelicula {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;
	
	@Column(name="Id_pelicula")
	private int Id_pelicula;

	@Column(name="Id_formato")
	private int Id_formato;
	
	public formato_pelicula() {}

	public formato_pelicula(int id_pelicula, int id_formato) {
		Id_pelicula = id_pelicula;
		Id_formato = id_formato;
	}

	public int getId_pelicula() {
		return Id_pelicula;
	}

	public void setId_pelicula(int id_pelicula) {
		Id_pelicula = id_pelicula;
	}

	public int getId_formato() {
		return Id_formato;
	}

	public void setId_formato(int id_formato) {
		Id_formato = id_formato;
	}
	
	
}
