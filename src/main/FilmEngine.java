package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import main.entity.director;
import main.entity.formato;
import main.entity.formato_pelicula;
import main.entity.genero;
import main.entity.pelicula;

public class FilmEngine {
	//CFG BASE DE DATOS
		static String DB_PELICULAS="jdbc:mysql://localhost/Peliculas?serverTimezone=Europe/Amsterdam";
		static String PELICULAS_USER = "root";
		static String PELICULAS_PASS = "";
		static director currentdirector = new director();
	// session factories
	static SessionFactory factory_pelicula;
	static SessionFactory factory_director;
	static SessionFactory factory_formato;
	static SessionFactory factory_genero;
	static SessionFactory factory_formato_pelicula;
	// sessions
	static Session session;
	//metodos
	//reload session factory
	public static void reloadFactory() {
	factory_pelicula = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(pelicula.class)
			.buildSessionFactory();
	factory_director = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(director.class)
			.buildSessionFactory();
	factory_formato = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(formato.class)
			.buildSessionFactory();
	factory_genero = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(genero.class)
			.buildSessionFactory();
	factory_formato = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(formato_pelicula.class)
			.buildSessionFactory();
	}
	
	//crear pelicula
		public static void filmBuilder(int permiso, String nombre, String director, String genero, String genero_secundario, String anyo, String descripcion) {
		boolean goodtogo = false;
		boolean exists = false;
		boolean newdirector = true;
		boolean newone = true;
		int gender = 0;
		int affected = 0;
		int directorid = 0;
		int pointer = 0;
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
		if(permiso==1||permiso==2) {
			//comprobacion pelicula existe
			try {
				reloadFactory();
				session = factory_pelicula.getCurrentSession();
				session.beginTransaction();
				List<pelicula> peliculas = session.createQuery("from pelicula p where p.Nombre='"+nombre+"' and p.Anyo like "+anyo+"").getResultList();
				for(pelicula display : peliculas) {
					newone = false;
					pointer = display.getId();
				}
				session.getTransaction().commit();
				factory_pelicula.close();
				session.close();
				if(newone) {
					//comprobacion director existe
					try {
						reloadFactory();
						session = factory_director.getCurrentSession();
						session.beginTransaction();
						List<director> directores = session.createQuery("from director d where d.Nombre='"+director+"'").getResultList();
						for(director display : directores) {
							newdirector = false;
							directorid = display.getId();
						}
						session.getTransaction().commit();
						factory_director.close();
						session.close();
						}
					catch(Exception e) {
						System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
					}
					finally {
						if(factory_director!=null) {
							factory_director.close();
						}
					}
					//creacion nuevo director
					if(newdirector) {
						directorBuilder(director,true);
						directorid = currentdirector.getId();
					}
					pelicula build = new pelicula(nombre, directorid, Integer.parseInt(genero), Integer.parseInt(genero_secundario),
							Integer.parseInt(anyo), descripcion,"C:\\DAM\\default.jpg\\","https://www.imdb.com/",true);
					try {
						reloadFactory();
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
				else {System.out.println("Ya hay una pelicula con ese nombre y fecha, no puedes crear otra igual.");}
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_pelicula!=null) {
					factory_pelicula.close();
				}
				}
			}
			else {System.out.println("No tienes permiso para agregar peliculas.");}
		
	}
	//crear director
		public static void directorBuilder(String nombre, boolean habilitado) {
			director build = new director(nombre, habilitado);
			try {
				reloadFactory();
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
		//listar peliculas
		public static void filmLister(int permiso) {
			//falta cargar el genero...
			List<director> directores = null;
			List<pelicula> peliculas = null;
			List<formato> formatos = null;
			List<genero> generos = null;
			List<formato_pelicula> relaciones = null;
			//carga de directores
			try {
				reloadFactory();
				session = factory_director.getCurrentSession();
				session.beginTransaction();
				directores = session.createQuery("from director").getResultList();
				session.getTransaction().commit();
				factory_director.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_director!=null) {
					factory_director.close();
				}
			}
			//carga de formatos
			try {
				reloadFactory();
				session = factory_formato.getCurrentSession();
				session.beginTransaction();
				formatos = session.createQuery("from formato").getResultList();
				session.getTransaction().commit();
				factory_formato.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_director!=null) {
					factory_director.close();
				}
			}
			//carga de peliculas
			try {
				session = factory_pelicula.getCurrentSession();
				session.beginTransaction();
				peliculas = session.createQuery("from pelicula").getResultList();
				session.getTransaction().commit();
				factory_pelicula.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_pelicula!=null) {
					factory_pelicula.close();
				}
			}
			//muestra todos los resultados
			for(pelicula display : peliculas) {
				System.out.println("Titulo: "+display.getNombre()+", Director: "+directores.get(display.getDirector()-1).getNombre()+", Año: "+display.getAnyo());
			}
		}
		//eliminar pelicula
		public static void filmDestroy(int permiso, String name) {
			if(permiso==1||permiso==2) {
				boolean deletable = false;
				int pointer = 0;
				try {
					reloadFactory();
					session = factory_pelicula.getCurrentSession();
					session.beginTransaction();
					List<pelicula> peliculas = session.createQuery("from pelicula p where p.Nombre='"+name+"'").getResultList();
					for(pelicula display : peliculas) {
						deletable = true;
						pointer = display.getId();
					}
					session.getTransaction().commit();
					factory_pelicula.close();
					session.close();
					if(deletable) {
						try {
							reloadFactory();
							session = factory_pelicula.getCurrentSession();
							session.beginTransaction();
							pelicula target = session.get(pelicula.class, pointer);
							session.delete(target);
							session.getTransaction().commit();
							factory_pelicula.close();
							session.close();
							System.out.println("La pelicula ha sido eliminada correctamente.");
							}
						catch(Exception e) {
							System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
						}
						finally {
							if(factory_pelicula!=null) {
								factory_pelicula.close();
							}
						}
					}
					else {System.out.println("No se ha encontrado ninguna pelicula con ese nombre");}
					}
				catch(Exception e) {
					System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
				}
				finally {
					if(factory_pelicula!=null) {
						factory_pelicula.close();
					}
				}
			}
			else {
				System.out.println("No tienes permiso para eso.");
			}
		}
}
