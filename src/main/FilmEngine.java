package main;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import main.entity.director;
import main.entity.formato;
import main.entity.formato_pelicula;
import main.entity.genero;
import main.entity.pelicula;

public class FilmEngine {
	// session factories
	SessionFactory factory_pelicula = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(pelicula.class)
			.buildSessionFactory();
	SessionFactory factory_director = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(director.class)
			.buildSessionFactory();
	SessionFactory factory_formato = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(formato.class)
			.buildSessionFactory();
	SessionFactory factory_genero = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(genero.class)
			.buildSessionFactory();
	SessionFactory factory_formato_pelicula = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(formato_pelicula.class)
			.buildSessionFactory();
	
	// sessions
	Session session_pelicula;
	Session session_director;
	Session session_formato;
	Session session_genero;
	Session session_formato_pelicula;
	
	//metodos
	
	//crear pelicula
	public void filmBuilder(String nombre, int director, int genero, int genero_secundario, int anyo, String descripcion,
			String caratula, String uRL_IMDB, boolean habilitado) {
		pelicula build = new pelicula(nombre, director, genero, genero_secundario, anyo, descripcion,
				caratula, uRL_IMDB, habilitado);
		try {
			session_pelicula = factory_pelicula.getCurrentSession();
			session_pelicula.beginTransaction();
			session_pelicula.save(build);
			session_pelicula.getTransaction().commit();
			System.out.println("La pelicula se insertó correctamente");
			}
		catch(Exception e) {
			System.out.println("Ha ocurrido un error al insertar la pelicula");
		}
		finally {
			session_pelicula.close();
			factory_pelicula.close();
		}
	}
	//crear pelicula
		public void directorBuilder(String nombre, int habilitado) {
			director build = new director(nombre, habilitado);
			try {
				session_pelicula = factory_pelicula.getCurrentSession();
				session_pelicula.beginTransaction();
				session_pelicula.save(build);
				session_pelicula.getTransaction().commit();
				System.out.println("El director se insertó correctamente");
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al insertar el director");
			}
			finally {
				session_pelicula.close();
				factory_pelicula.close();
			}
		}	
}
