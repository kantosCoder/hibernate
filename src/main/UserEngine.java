package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;
import main.entity.usuario;


public class UserEngine {
	//CFG BASE DE DATOS
		static String DB_USUARIOS ="jdbc:mysql://localhost/Usuarios?serverTimezone=Europe/Amsterdam";
		static String USUARIOS_USER = "root";
		static String USUARIOS_PASS = "";
	// session factories
		static SessionFactory factory_usuario = new Configuration()
				.configure("hibernate2nd.cfg.xml")
				.addAnnotatedClass(usuario.class)
				.buildSessionFactory();
		// sessions
		static Session session;
		
		//metodos
		//crear usuario
		public static void userBuilder(int permission, String nick, String pass, String rol) {
			usuario build = new usuario(nick, pass, Integer.parseInt(rol), "C:\\DAM\\foto.png", true);
			try {
				session = factory_usuario.getCurrentSession();
				session.beginTransaction();
				session.save(build);
				session.getTransaction().commit();
				System.out.println("La pelicula se insertó correctamente");
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al crear el usuario");
			}
			finally {
				factory_usuario.close();
			}
		}
		//listar usuarios
		public static void userLister(int permisos) {
			try {
				session = factory_usuario.getCurrentSession();
				session.beginTransaction();
				List<usuario> usuarios = session.createQuery("from usuario").getResultList();
				for(usuario display : usuarios) {
					System.out.println(display.getNick());
				}
				
				session.getTransaction().commit();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				factory_usuario.close();
			}
			
		}
		
		//metodo login
		public static boolean login(String user, String pass) {//TERMINAR...
			boolean able = true;
			return able;
		}
		public static void passwordUpdate(String newpass) {//TERMINAR...
			
		}
		public static void userDestroy(int permiso, String name) {
			
		}
}
