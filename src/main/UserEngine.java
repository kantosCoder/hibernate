package main;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import main.entity.usuario;


public class UserEngine {
	//CFG BASE DE DATOS
		static String DB_USUARIOS ="";
		static String USUARIOS_USER = "";
		static String USUARIOS_PASS = "";
	// session factories
		SessionFactory factory_usuario = new Configuration()
				.configure("hibernate2nd.cfg.xml")
				.addAnnotatedClass(usuario.class)
				.buildSessionFactory();
		// sessions
		Session session;
		
		//metodos
		//crear usuario
		public void userBuilder(String nick, String pass, int rol, String url_foto, boolean habilitado) {
			usuario build = new usuario(nick, pass, rol, url_foto, habilitado);
			try {
				session = factory_usuario.getCurrentSession();
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
				factory_usuario.close();
			}
		}
		//metodo login
}
