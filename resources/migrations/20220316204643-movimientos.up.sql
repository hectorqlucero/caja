CREATE TABLE movimientos (
  `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `f_docentes` int(11) NOT NULL,
  `fecha` date DEFAULT NULL,
  `cantidad` decimal(13,2) NOT NULL,
  `cuenta_banco` varchar(200) NOT NULL,
  `tipo_movimiento` char(1) DEFAULT NULL COMMENT 'D=Deposito,R=Retiro',
  `imagen` text DEFAULT NULL,
  KEY `fk_movimientos_docentes` (`f_docentes`),
  CONSTRAINT `fk_movimientos_docentes` FOREIGN KEY (`f_docentes`) REFERENCES `docentes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET = utf8
