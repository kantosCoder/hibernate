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
		static SessionFactory factory_usuario;
		// sessions
		static Session session;
		
		//metodos
		//reload sessionfactory
		public static void reloadFactory() {
			factory_usuario = new Configuration()
					.configure("hibernate2nd.cfg.xml")
					.addAnnotatedClass(usuario.class)
					.buildSessionFactory();
		}
		//crear usuario
		public static void userBuilder(int permission, String nick, String pass, String rol) {
			int conv = 0;
			usuario build = null;
			boolean exists = false;
			//comprobar si existe:
			if(nick.equals("")||pass.equals("")||rol.equals("")) {
				System.out.println("Error, porfavor, rellena todos los datos.");
			}
			else {
				if (!rol.matches("[0-9]+")) {
					rol = "3";
				}
				else {conv = Integer.parseInt(rol); if(conv>3||conv<1) {rol="3";}}
				build = new usuario(nick, pass, Integer.parseInt(rol), "C:\\DAM\\foto.png", true);
				if(permission == 1) {
				try {
					reloadFactory();
					session = factory_usuario.getCurrentSession();
					session.beginTransaction();
					List<usuario> usuarios = session.createQuery("from usuario u where u.Nick='"+nick+"'").getResultList();
					session.getTransaction().commit();
					for(usuario dload : usuarios) {
						exists = true;
					}
					//crear user si no existe.
					if(!exists) {
						try {
							session = factory_usuario.getCurrentSession();
							session.beginTransaction();
							session.save(build);
							session.getTransaction().commit();
							System.out.println("El nuevo usuario se creó correctamente");
							factory_usuario.close();
							session.close();
							}
						catch(Exception e) {
							System.out.println("Ha ocurrido un error al crear el usuario");
						}
						finally {
							if(factory_usuario!=null) {
								factory_usuario.close();
							}
						}
					}
					else {
						System.out.println("Ya existe un usuario con este nombre.");
					}
					
					}
				catch(Exception e) {
					System.out.println("Ha ocurrido un error inesperado de conexion, intentalo de nuevo.");
				}
				finally {
					if(factory_usuario!=null) {
						factory_usuario.close();
					}
				}
			}
			else {System.out.println("No tienes permiso para crear usuarios");}
			}
		}
		//listar usuarios
		public static void userLister(int permisos) {
			try {
				reloadFactory();
				session = factory_usuario.getCurrentSession();
				session.beginTransaction();
				List<usuario> usuarios = session.createQuery("from usuario").getResultList();
				for(usuario display : usuarios) {
					System.out.println(display.getNick());
				}
				session.getTransaction().commit();
				factory_usuario.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_usuario!=null) {
					factory_usuario.close();
				}
			}
		}
		
		//metodo login
		public static boolean login(String user, String pass) {//TERMINAR...
			boolean able = false;
			try {
				reloadFactory();
				session = factory_usuario.getCurrentSession();
				session.beginTransaction();
				List<usuario> usuarios = session.createQuery("from usuario u where u.Nick='"+user+"' and u.Pass='"+pass+"'").getResultList();
				for(usuario display : usuarios) {
					able = true;
					main.currentRole = display.getRol();
					main.userid = display.getId();
				}
				session.getTransaction().commit();
				factory_usuario.close();
				session.close();
				}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_usuario!=null) {
					factory_usuario.close();
				}
			}
			return able;
		}
		
		//actualizacion de contraseña
		public static void passwordUpdate(String newpass) {
			boolean deletable = false;
			int pointer = 0;
			try {
				reloadFactory();
				session = factory_usuario.getCurrentSession();
				session.beginTransaction();
				usuario user = session.get(usuario.class,main.userid);
				user.setPass(newpass);
				session.getTransaction().commit();
				factory_usuario.close();
				session.close();
				System.out.println("La contraseña ha sido actualizada correctamente");
			}
			catch(Exception e) {
				System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
			}
			finally {
				if(factory_usuario!=null) {
					factory_usuario.close();
				}
			}
		}
		
		//eliminacion de usuario
		public static void userDestroy(int permiso, String name) {
			if(permiso==1) {
				boolean deletable = false;
				int pointer = 0;
				try {
					reloadFactory();
					session = factory_usuario.getCurrentSession();
					session.beginTransaction();
					List<usuario> usuarios = session.createQuery("from usuario u where u.Nick='"+name+"'").getResultList();
					for(usuario display : usuarios) {
						deletable = true;
						pointer = display.getId();
					}
					session.getTransaction().commit();
					factory_usuario.close();
					session.close();
					if(deletable) {
						try {
							reloadFactory();
							session = factory_usuario.getCurrentSession();
							session.beginTransaction();
							usuario target = session.get(usuario.class, pointer);
							session.delete(target);
							session.getTransaction().commit();
							factory_usuario.close();
							session.close();
							System.out.println("El usuario ha sido eliminado correctamente.");
							}
						catch(Exception e) {
							System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
						}
						finally {
							if(factory_usuario!=null) {
								factory_usuario.close();
							}
						}
					}
					else {System.out.println("No se ha encontrado ningún usuario con ese nombre");}
					}
				catch(Exception e) {
					System.out.println("Ha ocurrido un error al tratar de recuperar la informacion");
				}
				finally {
					if(factory_usuario!=null) {
						factory_usuario.close();
					}
				}
			}
			else {
				System.out.println("No tienes permiso para eso.");
			}
		}
}
