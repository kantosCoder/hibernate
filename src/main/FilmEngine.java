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
	static SessionFactory factory_formato_p;
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
	factory_formato_p = new Configuration()
			.configure("hibernate.cfg.xml")
			.addAnnotatedClass(formato_pelicula.class)
			.buildSessionFactory();
	}
	
	//crear pelicula
		public static void filmBuilder(int permiso, String nombre, String director, String genero, String genero_secundario, String anyo, String descripcion, String fmt) {
		boolean goodtogo = false;
		boolean exists = false;
		boolean newdirector = true;
		boolean newone = true;
		int pelid = 1;
		int directorid = 0;
		int conv = 0;
		if(nombre.equals("")) {
			System.out.println("Error: Debes especificar un nombre...");
			goodtogo = false;
		}
		//comprobacion de enteros y valores por defecto.
		if (!genero.matches("[0-9]+")) {
			genero = "1";
		}
		else {conv = Integer.parseInt(genero); if(conv>5||conv<5) {genero="1";}}
		if (!genero_secundario.matches("[0-9]+")) {
			genero_secundario = "1";
		}
		else {conv = Integer.parseInt(genero_secundario); if(conv>5||conv<5) {genero_secundario="1";}}
		if (!anyo.matches("[0-9]+")) {
			anyo = "2000";
		}
		else {conv = Integer.parseInt(anyo); if(conv>3000||conv<1800) {anyo="2000";}}
		if (!fmt.matches("[0-9]+")) {
			fmt = "1";
		}
		else {conv = Integer.parseInt(fmt); if(conv>5||conv<5) {fmt="1";}}
		if (descripcion.equals("")) {
			descripcion="Sin especificar.";
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
				}
				session.getTransaction().commit();
				factory_pelicula.close();
				session.close();
				if(newone) {
					//comprobacion director existe
					try {
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
						System.out.println("Error al acceder");
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
					//insercion pelicula
					pelicula build = new pelicula(nombre, directorid, Integer.parseInt(genero), Integer.parseInt(genero_secundario),
							Integer.parseInt(anyo), descripcion,"C:\\DAM\\default.jpg\\","https://www.imdb.com/",true);
					try {
						reloadFactory();
						session = factory_pelicula.getCurrentSession();
						session.beginTransaction();
						session.save(build);
						session.getTransaction().commit();
						pelid = build.getId();
						factory_pelicula.close();
						session.close();
						System.out.println("La pelicula se insertó correctamente");
						}
					catch(Exception e) {
						System.out.println("Error al acceder");
					}
					finally {
						if(factory_pelicula!=null) {
							factory_pelicula.close();
						}
					}
					//inscripcion en relacion n:n formato
					try {
						formato_pelicula relation = new formato_pelicula(pelid,Integer.parseInt(fmt));
						session = factory_formato_p.getCurrentSession();
						session.beginTransaction();
						session.save(relation);
						session.getTransaction().commit();
						factory_formato_p.close();
						session.close();
						}
					catch(Exception e) {
						System.out.println("Error al acceder");
					}
					finally {
						if(factory_formato_p!=null) {
							factory_formato_p.close();
						}
					}
				}
				else {System.out.println("Ya hay una pelicula con ese nombre y fecha, no puedes crear otra igual.");}
				}
			catch(Exception e) {
				System.out.println("Error al acceder");
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
			formato_pelicula relation = null;
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
				if(factory_formato!=null) {
					factory_formato.close();
				}
			}
			//carga de generos
			try {
				session = factory_genero.getCurrentSession();
				session.beginTransaction();
				generos = session.createQuery("from genero").getResultList();
				session.getTransaction().commit();
				factory_genero.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_genero!=null) {
					factory_genero.close();
				}
			}
			//carga de relaciones
			try {
				session = factory_formato_p.getCurrentSession();
				session.beginTransaction();
				relaciones = session.createQuery("from formato_pelicula").getResultList();
				session.getTransaction().commit();
				factory_formato_p.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_formato_p!=null) {
					factory_formato_p.close();
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
				//obtener relacion:
				for(formato_pelicula rel : relaciones) {
					if(display.getId()==rel.getId_pelicula()){relation = rel;}
				}
				System.out.println("Titulo: "+display.getNombre()+", Director: "+directores.get(display.getDirector()-1).getNombre()+", "+
			"Generos: "+generos.get(display.getGenero()-1).getNombre()+", "+generos.get(display.getGenero_secundario()-1).getNombre()+", Año: "+display.getAnyo()+", "+
						"Formato: "+formatos.get(relation.getId_formato()-1).getNombre()+", Sinopsis: "+display.getDescripcion());
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
