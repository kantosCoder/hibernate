package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rol")
public class rol {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;

	@Column(name="Nombre")
	private String Nombre;

	@Column(name="Permisos")
	private int Permisos;
	
	public rol() {}

	public rol(String nombre, int permisos) {
		Nombre = nombre;
		Permisos = permisos;
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

	public int getPermisos() {
		return Permisos;
	}

	public void setPermisos(int permisos) {
		Permisos = permisos;
	}
	
	

}
