CREATE TABLE docentes (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `numero_empleado` varchar(300) NOT NULL,
  `curp` varchar(300) DEFAULT NULL,
  `f_area_trabajo` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `paterno` varchar(100) DEFAULT NULL,
  `materno` varchar(100) DEFAULT NULL,
  `antiguedad` varchar(200) DEFAULT NULL,
  UNIQUE KEY numero_empleado (numero_empleado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

