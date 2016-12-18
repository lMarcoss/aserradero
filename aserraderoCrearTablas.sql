-- Crear tablas proyecto aserradero
-- Base de datos aserradero
drop database if exists aserradero;
CREATE DATABASE aserradero CHARACTER SET utf8 COLLATE utf8_general_ci;
USE aserradero;

CREATE TABLE MUNICIPIO(
	nombre_municipio	VARCHAR(45) NOT NULL,
    estado 				VARCHAR(60),
	telefono 			CHAR(10),
	PRIMARY KEY (nombre_municipio))ENGINE=InnoDB;

CREATE TABLE LOCALIDAD(
	nombre_localidad	VARCHAR(45) NOT NULL,
	nombre_municipio	VARCHAR(45) NOT NULL,
	telefono 			CHAR(10),
	PRIMARY KEY (nombre_localidad),
	FOREIGN KEY (nombre_municipio) REFERENCES MUNICIPIO (nombre_municipio))ENGINE=InnoDB;

CREATE TABLE PERSONA(
	id_persona			CHAR(18) NOT NULL,
	nombre				VARCHAR(30) NOT NULL,
	apellido_paterno	VARCHAR(30) NOT NULL,
	apellido_materno	VARCHAR(30),
	localidad			VARCHAR(45),
    direccion			VARCHAR(60),
	sexo				ENUM('H','M'),
	fecha_nacimiento	DATE,
	telefono			CHAR(10),
	primary key(id_persona),
	FOREIGN KEY (localidad) REFERENCES LOCALIDAD (nombre_localidad))ENGINE=InnoDB;


    
-- CREATE TABLE EMPLEADO(
-- 	id_empleado 	VARCHAR(26) NOT NULL,
--     id_persona		CHAR(18) NOT NULL,
--     id_jefe 		VARCHAR(18) NOT NULL,
-- 	roll			ENUM('Administrador','Empleado','Vendedor','Chofer'),	
-- 	estatus			ENUM('Activo','Inactivo'),
-- 	PRIMARY KEY (id_empleado,id_jefe,roll),
-- 	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona) ON DELETE CASCADE ON UPDATE CASCADE,
--     FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE EMPLEADO(
	id_empleado 	VARCHAR(26) NOT NULL,
    id_persona		CHAR(18) NOT NULL,
    id_jefe 		VARCHAR(26) NOT NULL,
	rol				ENUM('Administrador','Empleado','Vendedor','Chofer'),	
	estatus			ENUM('Activo','Inactivo'),
	PRIMARY KEY (id_empleado,id_jefe,rol),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona),
    FOREIGN KEY (id_jefe) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;
 -- CREATE TABLE EMPLEADO_JEFE(id_empleado 	VARCHAR(18) NOT NULL,id_jefe			VARCHAR(18) NOT NULL,PRIMARY KEY (id_empleado,id_jefe),FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE,	FOREIGN KEY (id_jefe) REFERENCES EMPLEADO (id_empleado) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE ADMINISTRADOR(
	id_administrador	VARCHAR(26) NOT NULL,
    cuenta_inicial 		DECIMAL(15,2),
	PRIMARY KEY (id_administrador),
	FOREIGN KEY (id_administrador) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE	TABLE PAGO_EMPLEADO(
	id_pago_empleado INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(15,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_empleado),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE USUARIO(
	id_empleado 		VARCHAR(26) NOT NULL,
	nombre_usuario 		VARCHAR(255),
    contrasenia			varchar(255) NOT NULL,
	metodo 				ENUM('sha1'),
    email				VARCHAR(50),
    PRIMARY KEY(nombre_usuario),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

-- entrada
CREATE TABLE PROVEEDOR(
	id_proveedor 	VARCHAR(26) NOT NULL,
	id_persona 		VARCHAR(18) NOT NULL,
	id_jefe			VARCHAR(26) NOT NULL,
	PRIMARY KEY (id_proveedor,id_jefe),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona),
	FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador))ENGINE=InnoDB;

CREATE TABLE COSTO_MADERA_ENTRADA(
	id_administrador 	VARCHAR(26) NOT NULL,
    id_empleado			VARCHAR(26) NOT NULL,
	clasificacion		ENUM('Primario','Secundario','Terciario') NOT NULL,
	costo 				DECIMAL(8,2),
	PRIMARY KEY (id_administrador,clasificacion),
    FOREIGN KEY (id_administrador) REFERENCES ADMINISTRADOR (id_administrador),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE ENTRADA_MADERA_ROLLO( -- entrada_madera	
	id_entrada	 		INT NOT NULL AUTO_INCREMENT,
	fecha 				DATE,
    id_proveedor		CHAR(26) NOT NULL,
    id_chofer			CHAR(26) NOT NULL,
	id_empleado 		CHAR(26) NOT NULL,
	num_piezas			INT,
    volumen_primario	DECIMAL(15,3),	-- cantidad de volumen primaria
    costo_primario		DECIMAL(15,2),	-- costo volumen primario
    volumen_secundario	DECIMAL(15,3),	-- cantidad de volumen primaria
    costo_secundario	DECIMAL(15,2),	-- cantidad de volumen primaria
    volumen_terciario	DECIMAL(15,3),
    costo_terciario		DECIMAL(15,2),	-- cantidad de volumen primaria
    id_pago				INT(9) default 0, -- 0 para entradas no pagadas: Se le asigna: insertar un pago cada que se inserta entrada madera, con el campo Pago = "Pagado", "Sin pagar"
	PRIMARY KEY (id_entrada),
    FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor),
    FOREIGN KEY (id_chofer) REFERENCES EMPLEADO (id_empleado),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE SALIDA_MADERA_ROLLO( -- entrada_madera	
	id_salida	 		INT NOT NULL AUTO_INCREMENT,
	fecha 				DATE,
	id_empleado 		CHAR(26) NOT NULL,
	num_piezas			INT,
    volumen_total	DECIMAL(15,3),	-- cantidad de volumen primaria
	PRIMARY KEY (id_salida),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

-- CREATE TABLE PAGO_COMPRA(fecha		DATE,id_compra	CHAR(7) NOT NULL,monto 		DECIMAL(10,2),pago 		ENUM('Anticipado','Normal'),PRIMARY KEY (fecha,id_compra),	FOREIGN KEY (id_compra) REFERENCES COMPRA (id_compra) ON DELETE CASCADE ON UPDATE CASCADE)ENGINE=InnoDB;

CREATE TABLE MADERA_ASERRADA_CLASIF(
	id_administrador 		VARCHAR(26) NOT NULL,
	id_empleado		 		VARCHAR(26) NOT NULL,
	id_madera				VARCHAR(20) NOT NULL,
	grueso					DECIMAL(8,2),
	ancho					DECIMAL(8,2),
	largo					DECIMAL(8,2),
	volumen					DECIMAL(15,3),
    costo_por_volumen		DECIMAL(15,2),
	primary key(id_administrador,id_madera),
    FOREIGN KEY (id_administrador) REFERENCES ADMINISTRADOR (id_administrador),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

-- producción
CREATE TABLE ENTRADA_MADERA_ASERRADA(
	id_entrada			INT NOT NULL AUTO_INCREMENT,
	fecha 				DATE,
 	id_madera    		VARCHAR(20) NOT NULL,
 	num_piezas 			INT,
    id_empleado 		VARCHAR(26) NOT NULL,
    id_administrador 	VARCHAR(26) NOT NULL,
 	PRIMARY KEY (id_entrada),
    FOREIGN KEY (id_administrador,id_madera) REFERENCES MADERA_ASERRADA_CLASIF (id_administrador,id_madera),    
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

-- FOREIGN KEY (id_administrador) REFERENCES MADERA_ASERRADA_CLASIF (id_administrador)

CREATE TABLE CLIENTE(
	id_cliente 	CHAR(26) NOT NULL,
    id_persona 	CHAR(18) NOT NULL,
	id_jefe		CHAR(26),
	PRIMARY KEY(id_cliente,id_jefe),
	FOREIGN KEY (id_persona) REFERENCES PERSONA (id_persona),
	FOREIGN KEY (id_jefe) REFERENCES ADMINISTRADOR (id_administrador))ENGINE=InnoDB;


CREATE TABLE VENTA(
	id_venta 	VARCHAR(30),
	fecha 		DATE,
	id_cliente 	CHAR(26) NOT NULL,
	id_empleado CHAR(26),
	estatus 	ENUM('Pagado','Sin pagar'),
	tipo_venta 	ENUM('Paquete','Mayoreo','Extra'),
    tipo_pago 	ENUM('Anticipado','Normal'),
	PRIMARY KEY(id_venta),
	FOREIGN KEY (id_cliente) REFERENCES CLIENTE (id_cliente),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE VENTA_MAYOREO(
	id_administrador		VARCHAR(26) NOT NULL,
	id_venta 				VARCHAR(30),
	id_madera 				VARCHAR(20),
	num_piezas				INT,
	volumen 				DECIMAL(8,3),
	monto					DECIMAL(15,2),
    tipo_madera 			ENUM('Madera','Amarre') NOT NULL,
	PRIMARY KEY(id_venta,id_madera),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta),
	FOREIGN KEY (id_administrador,id_madera) REFERENCES MADERA_ASERRADA_CLASIF (id_administrador,id_madera))ENGINE=InnoDB;

CREATE TABLE VENTA_PAQUETE(
	id_administrador		VARCHAR(26) NOT NULL,
	id_venta 				VARCHAR(30),
	numero_paquete			INT,
	id_madera 				VARCHAR(20),
	num_piezas				INT,
	volumen 				DECIMAL(15,3),
	monto					DECIMAL(15,2),
    tipo_madera 			ENUM('Madera','Amarre') NOT NULL,
	PRIMARY KEY(id_venta,numero_paquete,id_madera),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta),
	FOREIGN KEY (id_administrador,id_madera) REFERENCES MADERA_ASERRADA_CLASIF (id_administrador,id_madera))ENGINE=InnoDB;

CREATE TABLE VENTA_EXTRA(
	id_venta 				VARCHAR(30),
	tipo 					VARCHAR(50),
	monto					DECIMAL(15,2),
	observacion				VARCHAR(100),
	PRIMARY KEY(id_venta,tipo),
	FOREIGN KEY (id_venta) REFERENCES VENTA (id_venta))ENGINE=InnoDB;

CREATE	TABLE PAGO_RENTA(
	id_pago_renta	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	nombre_persona	VARCHAR(50),
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(15,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_renta),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;
-- INICIAR id_pago_renta con 1
-- ALTER TABLE PAGO_RENTA AUTO_INCREMENT=1;
-- insertar dato en PAGO RENTA: NO SE INSERTA EL ID_PAGO_RENTA
-- INSERT INTO Persons (FirstName,LastName) VALUES ('Lars','Monsen');

CREATE	TABLE PAGO_LUZ(
	id_pago_luz		INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto 			DECIMAL(15,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_pago_luz),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE	TABLE OTRO_GASTO(
	id_gasto		INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_empleado 	VARCHAR(26) NOT NULL,
	nombre_gasto	VARCHAR(250),
	monto 			DECIMAL(15,2),
	observacion		VARCHAR(250),
	PRIMARY KEY (id_gasto),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE ANTICIPO_CLIENTE(
	id_anticipo_c	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_cliente 		CHAR(26) NOT NULL,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto_anticipo	DECIMAL(15,2),
	PRIMARY KEY(id_anticipo_c),
	FOREIGN KEY (id_cliente) REFERENCES CLIENTE (id_cliente),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE ANTICIPO_PROVEEDOR(
	id_anticipo_p	INT NOT NULL AUTO_INCREMENT,
	fecha 			DATE,
	id_proveedor	CHAR(26) NOT NULL,
	id_empleado 	VARCHAR(26) NOT NULL,
	monto_anticipo	DECIMAL(15,2),
	PRIMARY KEY(id_anticipo_p),
	FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE VEHICULO(
	id_vehiculo		INT NOT NULL AUTO_INCREMENT,
	matricula		VARCHAR(20) NOT NULL,
	tipo			VARCHAR(20),
	color			VARCHAR(20),
	carga_admitida	VARCHAR(20),
	motor			VARCHAR(20),
	modelo			VARCHAR(20),
	costo 			DECIMAL(15,2),
	id_empleado		VARCHAR(26) NOT NULL,
	PRIMARY KEY(id_vehiculo),
	FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE PRESTAMO(
	id_prestamo			INT NOT NULL AUTO_INCREMENT,
    fecha 				DATE,
    id_prestador		VARCHAR(26) NOT NULL, -- registramos un id de la tabla Persona agregando 8 letras del administrador para completar 26 caracteres
    id_empleado			VARCHAR(26) NOT NULL,
	monto_prestamo		DECIMAL(15,2),
    interes				INT, -- porcentaje de interes (0-100)
	PRIMARY KEY(id_prestamo, id_prestador),
    FOREIGN KEY (id_empleado) REFERENCES EMPLEADO (id_empleado))ENGINE=InnoDB;

CREATE TABLE PAGO_COMPRA(
	id_pago				INT NOT NULL AUTO_INCREMENT,
    fecha 				DATE,
    id_proveedor		CHAR(26) NOT NULL,
    monto_pago 			DECIMAL(15,2),
    monto_por_pagar		DECIMAL(15,2),
 	PRIMARY KEY (id_pago),
    FOREIGN KEY (id_proveedor) REFERENCES PROVEEDOR (id_proveedor))ENGINE=InnoDB;
    
