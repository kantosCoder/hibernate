package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import main.entity.director;
import main.entity.pelicula;

public class FilmEngine {
	//CFG BASE DE DATOS
		static String DB_PELICULAS="";
		static String PELICULAS_USER = "";
		static String PELICULAS_PASS = "";
	// session factories
	SessionFactory factory_pelicula = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(pelicula.class)
			.buildSessionFactory();
	SessionFactory factory_director = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(director.class)
			.buildSessionFactory();
	// sessions
	Session session;
	
	//metodos
	//crear pelicula
	public void filmBuilder(String nombre, String director, String genero, String genero_secundario, String anyo, String descripcion,
			String caratula, String uRL_IMDB, boolean habilitado) {
		boolean goodtogo = false;
		boolean exists = false;
		boolean newdirector = false;
		int gender = 0;
		int affected = 0;
		int directorid = 0;
		if(nombre.equals("")) {
			System.out.println("Error: Debes especificar un nombre...");
			goodtogo = false;
		}
		//comprobacion de enteros
		if (!genero.matches("[0-9]+")) {
			genero = "1";
		}
		if (!genero_secundario.matches("[0-9]+")) {
			genero_secundario = "1";
		}
		if (!anyo.matches("[0-9]+")) {
			anyo = "2000";
		}
		//comprobacion pelicula existe
		//comprobacion director existe
		//creacion nuevo director
		if(newdirector) {
			directorBuilder(director,true);
		}
		pelicula build = new pelicula(nombre, Integer.parseInt(director), Integer.parseInt(genero), Integer.parseInt(genero_secundario), Integer.parseInt(anyo), descripcion,
				caratula, uRL_IMDB, true);
		try {
			session = factory_pelicula.getCurrentSession();
			session.beginTransaction();
			session.save(build);
			session.getTransaction().commit();
			System.out.println("La pelicula se insertó correctamente");
			}
		catch(Exception e) {
			System.out.println("Ha ocurrido un error al insertar la pelicula");
		}
		finally {
			session.close();
			factory_pelicula.close();
		}
	}
	//crear director
		public void directorBuilder(String nombre, boolean habilitado) {
			director build = new director(nombre, habilitado);
			try {
				session = factory_director.getCurrentSession();
				session.beginTransaction();
				session.save(build);
				session.getTransaction().commit();
				System.out.println("El director se insertó correctamente");
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al insertar el director");
			}
			finally {
				session.close();
				factory_director.close();
			}
		}	
}
