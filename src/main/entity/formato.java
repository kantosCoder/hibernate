package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="formato")
public class formato {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;

	@Column(name="Nombre")
	private String Nombre;

	@Column(name="Habilitado")
	private boolean Habilitado;
	
	public formato() {}

	public formato(String nombre, boolean habilitado) {
		Nombre = nombre;
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

	public boolean isHabilitado() {
		return Habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		Habilitado = habilitado;
	}
	
	
}
