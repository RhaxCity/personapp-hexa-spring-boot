USE persona_db;

INSERT INTO `persona`(`cc`,`nombre`,`apellido`,`genero`,`edad`) VALUES
    (123456789,'Pepe','Perez','M',30),
    (987654321,'Pepito','Perez','M',NULL),
    (321654987,'Pepa','Juarez','F',30),
    (147258369,'Pepita','Juarez','F',10),
    (963852741,'Fede','Perez','M',18);

INSERT INTO `profesion`(`id`,`nom`,`des`) VALUES
    (1,'Ingeniero','Descripción del Ingeniero'),
    (2,'Medico','Descripción del Médico');

INSERT INTO `telefono`(`num`,`oper`,`duenio`) VALUES
    ('3001234567','Movistar',123456789),
    ('3101234567','Claro',987654321);

INSERT INTO `estudios`(`id_prof`,`cc_per`,`fecha`,`univer`) VALUES
    (1,123456789,'2020-01-01','Universidad Javeriana'),
    (2,321654987,'2019-01-01','Universidad Nacional');
