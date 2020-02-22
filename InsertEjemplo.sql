use Peliculas;

INSERT into Director(Nombre,Habilitado)values
("James Cameron",true),
("George Lucas",true),
("Quentin Tarantino",true),
("Pedro Almodovar",false);

INSERT into Genero(Nombre,Habilitado)values
("Comedia",true),
("Horror",true),
("Drama",true),
("Documental",true);

INSERT into Pelicula(Nombre,Director,Genero,Genero_secundario,Anyo,Descripcion,Caratula,URL_IMDB,Habilitado)values
("Peliculaso",1,2,1,1994,"Pelicula del siglo","C:\DAM\default.jpg","https://www.imdb.com/",true),
("Los tres cerditos",1,1,2,1990,"Pelicula del siglo","C:\DAM\default.jpg","https://www.imdb.com/",true),
("Jose luis, el duende",2,1,1,2017,"El duende liante","C:\DAM\default.jpg","https://www.imdb.com/",true);

INSERT into Formato(Nombre, Habilitado) values
("DVD",true),
("LaserDisc",true),
("Digital",true),
("VHS",true),
("Betamax",true);

INSERT into Formato_pelicula(Id_pelicula,Id_formato)values
(1,1),
(2,2),
(3,1);

Use Usuarios;

INSERT into Rol (Id, Nombre, Permisos) values 
(1,"ADMIN",3),
(2,"USERA",2),
(3,"USERL",1);

INSERT into Usuario (Nick, Pass, Rol, Url_foto, Habilitado) values 
("Jefe","12321",1,"C:\DAM\default.jpg",true),
("Encargado","encargado",2,"C:\DAM\default.jpg",true),
("Empleado","empleado",3,"C:\DAM\default.jpg",true);