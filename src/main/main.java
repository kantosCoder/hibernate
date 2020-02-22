package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class main {
	public static String currentUser="";
	public static int currentRole=0; //nivel de permiso 0=USERL//1=USERA//2=ADMIN
	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		//!\TODO LIST
		// falta escribir en el XML, acabar el metodo de login
		//
		boolean proceed = false;
		boolean logged = false;
		String userinput = "";
		String userinput2 ="";
		String[] data = new String[6];
		Scanner input = new Scanner(System.in);;
		System.out.println("FILMOTECA V 1.0 _ HÉCTOR CANTOS");
		System.out.println("-------------------------------");
		System.out.println("Leyendo archivo de configuración....");
		//proceed = XML_Loader.xmlread(); solventar
		proceed = true;
		Connection conn;
		proceed = true; //debug
		while(proceed) {
			System.out.println("Conectando a base de datos...");
			try {
				conn = DriverManager.getConnection(UserEngine.DB_USUARIOS, UserEngine.USUARIOS_USER, UserEngine.USUARIOS_PASS);
				conn.close();
				conn = DriverManager.getConnection(UserEngine.DB_USUARIOS, UserEngine.USUARIOS_USER, UserEngine.USUARIOS_PASS);
				System.out.println("Exito al conectar a la base");
				conn.close();
				proceed = true;
			}
			catch(Exception e){
				System.out.println("Error al conectar a la base");
				proceed = false;
			}
			if(proceed) {
				while(!logged) {
				System.out.println("Por favor, introduzca su nombre de usuario:");
				userinput = input.nextLine();
				System.out.println("Por favor, introduzca su contraseña:");
				userinput2 = input.nextLine();
				if(userinput.equals("") && userinput2.equals("")) {
					System.out.println("--------------------------------------------------");
					System.out.println("Por favor, introduzca un usuario y una contraseña.");
					System.out.println("--------------------------------------------------");
					System.out.println("");
				}
				else {
					logged = UserEngine.login(userinput,userinput2);//acabar el metodo de login...
					currentUser = userinput;
				}
				}//fin de login (logged)
				while(logged) {//comienza el menú
					System.out.println("----------------------------");
					System.out.println("Bienvenido,'"+currentUser+"', elige una opción");
					System.out.println("----------------------------");
					System.out.println("0) Listar usuarios almacenados");//extra
					System.out.println("1) Listar peliculas almacenadas.");
					System.out.println("2) Registrar una nueva película");
					System.out.println("3) Borrar una película");
					System.out.println("4) Modificar la contraseña actual");
					System.out.println("5) Registrar un nuevo usuario");
					System.out.println("6) Eliminar un usuario existente");
					System.out.println("7) Salir");//extra
					userinput = input.nextLine();
					switch(userinput) { //ejecuta la opción necesaria
						case"0":
							UserEngine.userLister(currentRole); //terminar
						break;
						case"1":
							FilmEngine.filmLister(currentRole); //terminar
						break;
						case"2":
							System.out.println("");
							System.out.println("A continuación, introduce los datos de tu pelicula, el nombre es obligatorio,");
							System.out.println("para el resto de valores, no especificar nada, o especificar una opcion");
							System.out.println("erronea, hará que se tomen valores por defecto.");
							System.out.println("Pueden existir dos peliculas con el mismo nombre, siempre y cuando difieran en");
							System.out.println("el año de estreno.");
							System.out.println("-------------------------------------------------------------------------------");
							System.out.println("Inserta el título de la película");
							data[0]=input.nextLine();
							System.out.println("Inserta el nombre del director de la pelicula");
							data[1]=input.nextLine();
							System.out.println("Inserta el genero de la pelicula [1)'accion', 2)'comedia', 3)'horror', 4)'drama' o 5)'documental']");
							System.out.println("(Escribe el nombre del genero conforme se muestra o el número correspondiente, por defecto: accion)");
							data[2]=input.nextLine();
							System.out.println("Inserta el genero secundario de la pelicula [1)'accion', 2)'comedia', 3)'horror', 4)'drama' o 5)'documental']");
							System.out.println("(Escribe el nombre del genero conforme se muestra o el número correspondiente, por defecto: accion)");
							data[3]=input.nextLine();
							System.out.println("Inserta el año de estreno de la pelicula, (por defecto año 2000)");
							data[4]=input.nextLine();
							System.out.println("Escribe una breve descripción/sinopsis sobre la película (Por defecto: Sin especificar)");
							data[5]=input.nextLine();
							FilmEngine.filmBuilder(currentRole, data[0], data[1], data[2], data[3], data[4], data[5]);//insercion con hibernate
							for(int i=0; i<6; i++) {//limpieza del array
								data[i]="";
							}
						break;
						case"3":
							System.out.println("");
							System.out.println("A continuación, el nombre de la pelicula que quieres borrar.");
							System.out.println("/!\\ATENCIÓN/!\\ UNA VEZ ESCRITO EL NOMBRE, CUANDO PULSES INTRO,");
							System.out.println("EL USUARIO SERÁ ELIMINADO PARA SIEMPRE, SI NO ESTAS SEGURO,");
							System.out.println("NO ESCRIBAS NADA Y PULSA ENTER.");
							System.out.println("---------------------------------------------------------");
							userinput = input.nextLine();
							if(userinput.equals("")) {
								System.out.println("No se ha eliminado el usuario porque no se especificó ninguno.");
							}
							else {
								FilmEngine.filmDestroy(currentRole,userinput);
							}
							
						break;
						case"4":
							System.out.println("Escribe la nueva contraseña:");
							userinput = input.nextLine();
							System.out.println("Confirma la nueva contraseña:");
							userinput2 = input.nextLine();
							if(userinput.equals(userinput2)) {
								UserEngine.passwordUpdate(userinput);//TERMINAR...
							}
							else {
							System.out.println("Error al cambiar la contraseña: las contraseñas no coinciden.");
							}
						break;
						case"5":
							System.out.println("");
							System.out.println("A continuación, introduce los datos del nuevo usuario");
							System.out.println("---------------------------------------------------------");
							System.out.println("Inserta nombre de usuario (Nick)");
							data[0]=input.nextLine();
							System.out.println("Inserta la contraseña del nuevo usuario");
							data[1]=input.nextLine();
							System.out.println("Confirma la contraseña del nuevo usuario");
							data[2]=input.nextLine();
							System.out.println("Inserta el rol del usuario [1)'ADMIN', 2)'USERA', 3)'USERL']");
							System.out.println("(Escribe el nombre del ROL conforme se muestra o el número correspondiente, por defecto: USERL)");
							data[3]=input.nextLine();
							if(data[1].equals(1)) {
								UserEngine.userBuilder(currentRole, data[0], data[1], data[3]);//TERMNINAR
							}
							else {
								System.out.println("Error, las contraseñas no coinciden");
							}
							for(int i=0; i<4; i++) {//limpieza del array
								data[i]="";
							}
						break;
						case"6":
							System.out.println("");
							System.out.println("A continuación, el nick del usuario que quieres borrar.");
							System.out.println("/!\\ATENCIÓN/!\\ UNA VEZ ESCRITO EL NOMBRE, CUANDO PULSES INTRO,");
							System.out.println("EL USUARIO SERÁ ELIMINADO PARA SIEMPRE, SI NO ESTAS SEGURO,");
							System.out.println("NO ESCRIBAS NADA Y PULSA ENTER.");
							System.out.println("---------------------------------------------------------");
							userinput = input.nextLine();
							if(userinput.equals("")) {
								System.out.println("No se ha eliminado el usuario porque no se especificó ninguno.");
							}
							else {
								UserEngine.userDestroy(currentRole,userinput); //TERMINAR
							}
						break;
						case"7":
							proceed = false;
							logged = false;
							System.out.println("Se ha cerrado sesión, ahora el programa");
							System.out.println("terminará. Pase buen día.");
						break;
						default:
							System.out.println("");
							System.out.println("Por favor, seleccione una opción válida");
							System.out.println("especificando un número asociado a una");
							System.out.println("de las presentadas y pulse enter.");
							System.out.println("");
						break;
					}
				}
				proceed = false; //parada 
			}
		}
	}
}