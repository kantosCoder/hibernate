package main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="director")
public class director {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int Id;
	
	@Column(name="Nombre")
	private String Nombre;

	@Column(name="Habilitado")
	private boolean Habilitado;

	public director()
	{}	
	public director(String nombre, boolean habilitado) {
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
	public boolean getHabilitado() {
		return Habilitado;
	}
	public void setHabilitado(boolean habilitado) {
		Habilitado = habilitado;
	}
	
}
