create database Peliculas;

use Peliculas;

create table Director (
	Id int NOT NULL auto_increment,
	Nombre varchar (50),
    Habilitado boolean,
    constraint pk_director
	Primary key (Id)
 );

create table Genero (
	Id int NOT NULL auto_increment,
	Nombre varchar (20),
    Habilitado boolean,
    constraint pk_genero
	Primary key (Id)
 );

create table Formato (
	Id int NOT NULL auto_increment,
	Nombre varchar (20),
    Habilitado boolean,
    constraint pk_formato
	Primary key (Id)
 );

 create table Pelicula (
	Id int NOT NULL auto_increment,
	Nombre varchar (100),
    Director int ,
	Genero int ,
	Genero_secundario int ,
	Anyo int ,
	Descripcion text ,
	Caratula varchar (128),
	URL_IMDB varchar (100),
	Habilitado boolean,
	foreign key (Director)
    references Director (Id),
	foreign key (Genero)
    references Genero (Id),
	foreign key (Genero_secundario)
    references Genero (Id),
    constraint pk_pelicula
	Primary key (Id, Anyo)
 );

 create table Formato_pelicula (
Id_pelicula int,
Id_formato int,
foreign key (Id_pelicula)
references Pelicula (Id),
foreign key (Id_formato)
references Formato (Id),
constraint pk_formato_pelicula
Primary key (Id_pelicula, Id_formato)
); 

Create database Usuarios;

Use Usuarios;

create table Rol (
	Id int NOT NULL,
	Nombre varchar (10),
    Permisos int,
    constraint pk_rol
	Primary key (Id)
 );

create table Usuario (
	Id int NOT NULL auto_increment,
	Nick varchar (20),
    Pass varchar (10),
	Rol int ,
	Url_foto varchar(100),
	Habilitado boolean,
	foreign key (Rol)
    references Rol (Id),
    constraint pk_usuario
	Primary key (Id)
 );