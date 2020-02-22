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
		System.out.println("FILMOTECA V 1.0 _ H�CTOR CANTOS");
		System.out.println("-------------------------------");
		System.out.println("Leyendo archivo de configuraci�n....");
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
				System.out.println("Por favor, introduzca su contrase�a:");
				userinput2 = input.nextLine();
				if(userinput.equals("") && userinput2.equals("")) {
					System.out.println("--------------------------------------------------");
					System.out.println("Por favor, introduzca un usuario y una contrase�a.");
					System.out.println("--------------------------------------------------");
					System.out.println("");
				}
				else {
					logged = UserEngine.login(userinput,userinput2);//acabar el metodo de login...
					currentUser = userinput;
				}
				}//fin de login (logged)
				while(logged) {//comienza el men�
					System.out.println("----------------------------");
					System.out.println("Bienvenido,'"+currentUser+"', elige una opci�n");
					System.out.println("----------------------------");
					System.out.println("0) Listar usuarios almacenados");//extra
					System.out.println("1) Listar peliculas almacenadas.");
					System.out.println("2) Registrar una nueva pel�cula");
					System.out.println("3) Borrar una pel�cula");
					System.out.println("4) Modificar la contrase�a actual");
					System.out.println("5) Registrar un nuevo usuario");
					System.out.println("6) Eliminar un usuario existente");
					System.out.println("7) Salir");//extra
					userinput = input.nextLine();
					switch(userinput) { //ejecuta la opci�n necesaria
						case"0":
							UserEngine.userLister(currentRole); //terminar
						break;
						case"1":
							FilmEngine.filmLister(currentRole); //terminar
						break;
						case"2":
							System.out.println("");
							System.out.println("A continuaci�n, introduce los datos de tu pelicula, el nombre es obligatorio,");
							System.out.println("para el resto de valores, no especificar nada, o especificar una opcion");
							System.out.println("erronea, har� que se tomen valores por defecto.");
							System.out.println("Pueden existir dos peliculas con el mismo nombre, siempre y cuando difieran en");
							System.out.println("el a�o de estreno.");
							System.out.println("-------------------------------------------------------------------------------");
							System.out.println("Inserta el t�tulo de la pel�cula");
							data[0]=input.nextLine();
							System.out.println("Inserta el nombre del director de la pelicula");
							data[1]=input.nextLine();
							System.out.println("Inserta el genero de la pelicula [1)'accion', 2)'comedia', 3)'horror', 4)'drama' o 5)'documental']");
							System.out.println("(Escribe el nombre del genero conforme se muestra o el n�mero correspondiente, por defecto: accion)");
							data[2]=input.nextLine();
							System.out.println("Inserta el genero secundario de la pelicula [1)'accion', 2)'comedia', 3)'horror', 4)'drama' o 5)'documental']");
							System.out.println("(Escribe el nombre del genero conforme se muestra o el n�mero correspondiente, por defecto: accion)");
							data[3]=input.nextLine();
							System.out.println("Inserta el a�o de estreno de la pelicula, (por defecto a�o 2000)");
							data[4]=input.nextLine();
							System.out.println("Escribe una breve descripci�n/sinopsis sobre la pel�cula (Por defecto: Sin especificar)");
							data[5]=input.nextLine();
							FilmEngine.filmBuilder(currentRole, data[0], data[1], data[2], data[3], data[4], data[5]);//insercion con hibernate
							for(int i=0; i<6; i++) {//limpieza del array
								data[i]="";
							}
						break;
						case"3":
							System.out.println("");
							System.out.println("A continuaci�n, el nombre de la pelicula que quieres borrar.");
							System.out.println("/!\\ATENCI�N/!\\ UNA VEZ ESCRITO EL NOMBRE, CUANDO PULSES INTRO,");
							System.out.println("EL USUARIO SER� ELIMINADO PARA SIEMPRE, SI NO ESTAS SEGURO,");
							System.out.println("NO ESCRIBAS NADA Y PULSA ENTER.");
							System.out.println("---------------------------------------------------------");
							userinput = input.nextLine();
							if(userinput.equals("")) {
								System.out.println("No se ha eliminado el usuario porque no se especific� ninguno.");
							}
							else {
								FilmEngine.filmDestroy(currentRole,userinput);
							}
							
						break;
						case"4":
							System.out.println("Escribe la nueva contrase�a:");
							userinput = input.nextLine();
							System.out.println("Confirma la nueva contrase�a:");
							userinput2 = input.nextLine();
							if(userinput.equals(userinput2)) {
								UserEngine.passwordUpdate(userinput);//TERMINAR...
							}
							else {
							System.out.println("Error al cambiar la contrase�a: las contrase�as no coinciden.");
							}
						break;
						case"5":
							System.out.println("");
							System.out.println("A continuaci�n, introduce los datos del nuevo usuario");
							System.out.println("---------------------------------------------------------");
							System.out.println("Inserta nombre de usuario (Nick)");
							data[0]=input.nextLine();
							System.out.println("Inserta la contrase�a del nuevo usuario");
							data[1]=input.nextLine();
							System.out.println("Confirma la contrase�a del nuevo usuario");
							data[2]=input.nextLine();
							System.out.println("Inserta el rol del usuario [1)'ADMIN', 2)'USERA', 3)'USERL']");
							System.out.println("(Escribe el nombre del ROL conforme se muestra o el n�mero correspondiente, por defecto: USERL)");
							data[3]=input.nextLine();
							if(data[1].equals(1)) {
								UserEngine.userBuilder(currentRole, data[0], data[1], data[3]);//TERMNINAR
							}
							else {
								System.out.println("Error, las contrase�as no coinciden");
							}
							for(int i=0; i<4; i++) {//limpieza del array
								data[i]="";
							}
						break;
						case"6":
							System.out.println("");
							System.out.println("A continuaci�n, el nick del usuario que quieres borrar.");
							System.out.println("/!\\ATENCI�N/!\\ UNA VEZ ESCRITO EL NOMBRE, CUANDO PULSES INTRO,");
							System.out.println("EL USUARIO SER� ELIMINADO PARA SIEMPRE, SI NO ESTAS SEGURO,");
							System.out.println("NO ESCRIBAS NADA Y PULSA ENTER.");
							System.out.println("---------------------------------------------------------");
							userinput = input.nextLine();
							if(userinput.equals("")) {
								System.out.println("No se ha eliminado el usuario porque no se especific� ninguno.");
							}
							else {
								UserEngine.userDestroy(currentRole,userinput); //TERMINAR
							}
						break;
						case"7":
							proceed = false;
							logged = false;
							System.out.println("Se ha cerrado sesi�n, ahora el programa");
							System.out.println("terminar�. Pase buen d�a.");
						break;
						default:
							System.out.println("");
							System.out.println("Por favor, seleccione una opci�n v�lida");
							System.out.println("especificando un n�mero asociado a una");
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