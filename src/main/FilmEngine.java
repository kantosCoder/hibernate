package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import main.entity.director;
import main.entity.pelicula;

public class FilmEngine {
	//CFG BASE DE DATOS
		static String DB_PELICULAS="jdbc:mysql://localhost/Peliculas?serverTimezone=Europe/Amsterdam";
		static String PELICULAS_USER = "root";
		static String PELICULAS_PASS = "";
		static director currentdirector = new director();
	// session factories
	static SessionFactory factory_pelicula = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(pelicula.class)
			.buildSessionFactory();
	static SessionFactory factory_director = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(director.class)
			.buildSessionFactory();
	// sessions
	static Session session;
	
	//metodos
	//crear pelicula
	public static void filmBuilder(int permiso, String nombre, String director, String genero, String genero_secundario, String anyo, String descripcion) {
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
			directorid = currentdirector.getId();
		}
		else {
			//obtener id y asignarla al directorid
		}
		//obtener id del director
		
		
		pelicula build = new pelicula(nombre, directorid, Integer.parseInt(genero), Integer.parseInt(genero_secundario),
				Integer.parseInt(anyo), descripcion,"C:\\DAM\\default.jpg\\","https://www.imdb.com/",true);
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
			factory_pelicula.close();
		}
	}
	//crear director
		public static void directorBuilder(String nombre, boolean habilitado) {
			director build = new director(nombre, habilitado);
			try {
				session = factory_director.getCurrentSession();
				session.beginTransaction();
				session.save(build);
				session.getTransaction().commit();
				System.out.println("El director se insertó correctamente");
				currentdirector = build;
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al insertar el director");
			}
			finally {
				factory_director.close();
			}
		}
		public static void filmLister(int permiso) {
			
		}
		public static void filmDestroy(int permiso, String name) {
			
		}
}
