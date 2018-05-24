-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-05-2018 a las 15:11:53
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.2.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `programiwi`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrera`
--

CREATE TABLE `carrera` (
  `codigo` int(10) UNSIGNED NOT NULL,
  `malla` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(90) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `carrera`
--

INSERT INTO `carrera` (`codigo`, `malla`, `nombre`) VALUES
(29001, 1, 'arquitectura'),
(29004, 1, 'diseño industrial'),
(29004, 2, 'diseño industrial'),
(29027, 1, 'ingeniería civil en informática'),
(29037, 1, 'ingeniería de ejecución en computación e informática'),
(29037, 2, 'ingeniería de ejecución en computación e informática');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrera_tiene_ramos`
--

CREATE TABLE `carrera_tiene_ramos` (
  `carrera_codigo` int(10) UNSIGNED NOT NULL,
  `carrera_malla` int(10) UNSIGNED NOT NULL,
  `ramos_codigo` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `carrera_tiene_ramos`
--

INSERT INTO `carrera_tiene_ramos` (`carrera_codigo`, `carrera_malla`, `ramos_codigo`) VALUES
(29001, 1, 113),
(29001, 1, 122),
(29001, 1, 134),
(29004, 1, 121),
(29004, 1, 122),
(29004, 2, 122),
(29004, 2, 134),
(29027, 1, 123),
(29027, 1, 256),
(29027, 1, 257),
(29037, 1, 112),
(29037, 1, 254),
(29037, 1, 256),
(29037, 2, 133),
(29037, 2, 257);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripciones_ramos`
--

CREATE TABLE `inscripciones_ramos` (
  `usuario_rut` int(9) UNSIGNED NOT NULL,
  `ramos_codigo` int(10) UNSIGNED NOT NULL,
  `semestre` int(1) UNSIGNED NOT NULL,
  `ano` int(4) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `inscripciones_ramos`
--

INSERT INTO `inscripciones_ramos` (`usuario_rut`, `ramos_codigo`, `semestre`, `ano`) VALUES
(6562923, 111, 2, 2017),
(6562923, 113, 2, 2017),
(6562923, 134, 1, 2017),
(16327196, 111, 1, 2018),
(16327196, 113, 2, 2016),
(16327196, 122, 1, 2016),
(16327196, 123, 1, 2017),
(16327196, 131, 1, 2018),
(16327196, 132, 1, 2018),
(16327196, 133, 1, 2018),
(16327196, 134, 2, 2017);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ramos`
--

CREATE TABLE `ramos` (
  `codigo` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ramos`
--

INSERT INTO `ramos` (`codigo`, `nombre`) VALUES
(111, 'introducción al cálculo'),
(112, 'cálculo diferencial e integral'),
(113, 'cálculo en varias variables'),
(121, 'introducción al álgebra'),
(122, 'algebra lineal'),
(123, 'ecuaciones diferenciales ordinarias'),
(131, 'introducción a la física newtoniana'),
(132, 'sistemas newtonianos'),
(133, 'mecánica'),
(134, 'electromagnetismo'),
(254, 'arquitectura de computadores'),
(255, 'sistemas digitales'),
(256, 'ingeniería de software'),
(257, 'metodologías de desarrollo');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `ramos_alumno`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `ramos_alumno` (
`rut` int(9) unsigned
,`codigo` int(10) unsigned
,`semestre` int(1) unsigned
,`ano` int(4) unsigned
,`nombre` varchar(45)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipo_usuario`
--

CREATE TABLE `tipo_usuario` (
  `id` int(10) UNSIGNED NOT NULL,
  `nombre` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `tipo_usuario`
--

INSERT INTO `tipo_usuario` (`id`, `nombre`) VALUES
(1, 'administrador'),
(2, 'secretaria'),
(3, 'alumno');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `rut` int(9) UNSIGNED NOT NULL,
  `contrasena` varchar(45) NOT NULL,
  `primer_nombre` varchar(45) NOT NULL,
  `segundo_nombre` varchar(45) DEFAULT NULL,
  `primer_apellido` varchar(45) NOT NULL,
  `segundo_apellido` varchar(45) DEFAULT NULL,
  `correo` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`rut`, `contrasena`, `primer_nombre`, `segundo_nombre`, `primer_apellido`, `segundo_apellido`, `correo`) VALUES
(6562923, '1234', 'isaias', 'desiderio', 'llanos', 'reyes', 'isaias.llanos.reyes@gmail.com'),
(7231360, '1234', 'ana', 'ester', 'prado', 'gonzalez', 'apradogo@ubiobio.cl'),
(11111111, '1234', 'administrador', NULL, 'cool', NULL, 'admin@sistemiwi.cl'),
(16327196, '1234', 'anibal', 'esteban', 'llanos', 'prado', 'anillano@alumnos.ubiobio.cl');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_tiene_carrera`
--

CREATE TABLE `usuario_tiene_carrera` (
  `usuario_rut` int(9) UNSIGNED NOT NULL,
  `carrera_codigo` int(10) UNSIGNED NOT NULL,
  `carrera_malla` int(10) UNSIGNED NOT NULL,
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario_tiene_carrera`
--

INSERT INTO `usuario_tiene_carrera` (`usuario_rut`, `carrera_codigo`, `carrera_malla`, `fecha`) VALUES
(6562923, 29004, 1, '2017-02-14'),
(16327196, 29037, 2, '2016-08-12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_tiene_tipo_usuario`
--

CREATE TABLE `usuario_tiene_tipo_usuario` (
  `usuario_rut` int(9) UNSIGNED NOT NULL,
  `tipo_usuario_id` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario_tiene_tipo_usuario`
--

INSERT INTO `usuario_tiene_tipo_usuario` (`usuario_rut`, `tipo_usuario_id`) VALUES
(6562923, 3),
(7231360, 2),
(11111111, 1),
(16327196, 3);

-- --------------------------------------------------------

--
-- Estructura para la vista `ramos_alumno`
--
DROP TABLE IF EXISTS `ramos_alumno`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `ramos_alumno`  AS  select `ir`.`usuario_rut` AS `rut`,`ir`.`ramos_codigo` AS `codigo`,`ir`.`semestre` AS `semestre`,`ir`.`ano` AS `ano`,`r`.`nombre` AS `nombre` from (`inscripciones_ramos` `ir` join `ramos` `r`) where (`ir`.`ramos_codigo` = `r`.`codigo`) ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `carrera`
--
ALTER TABLE `carrera`
  ADD PRIMARY KEY (`codigo`,`malla`);

--
-- Indices de la tabla `carrera_tiene_ramos`
--
ALTER TABLE `carrera_tiene_ramos`
  ADD PRIMARY KEY (`carrera_codigo`,`carrera_malla`,`ramos_codigo`),
  ADD KEY `fk_carrera_has_ramos_ramos1_idx` (`ramos_codigo`),
  ADD KEY `fk_carrera_has_ramos_carrera1_idx` (`carrera_codigo`,`carrera_malla`);

--
-- Indices de la tabla `inscripciones_ramos`
--
ALTER TABLE `inscripciones_ramos`
  ADD PRIMARY KEY (`usuario_rut`,`ramos_codigo`),
  ADD KEY `fk_usuario_has_ramos_ramos1_idx` (`ramos_codigo`),
  ADD KEY `fk_usuario_has_ramos_usuario1_idx` (`usuario_rut`);

--
-- Indices de la tabla `ramos`
--
ALTER TABLE `ramos`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`rut`),
  ADD UNIQUE KEY `correo_UNIQUE` (`correo`);

--
-- Indices de la tabla `usuario_tiene_carrera`
--
ALTER TABLE `usuario_tiene_carrera`
  ADD PRIMARY KEY (`usuario_rut`,`carrera_codigo`,`carrera_malla`),
  ADD KEY `fk_usuario_has_carrera_carrera1_idx` (`carrera_codigo`,`carrera_malla`),
  ADD KEY `fk_usuario_has_carrera_usuario1_idx` (`usuario_rut`);

--
-- Indices de la tabla `usuario_tiene_tipo_usuario`
--
ALTER TABLE `usuario_tiene_tipo_usuario`
  ADD PRIMARY KEY (`usuario_rut`,`tipo_usuario_id`),
  ADD KEY `fk_usuario_has_tipo_usuario_tipo_usuario1_idx` (`tipo_usuario_id`),
  ADD KEY `fk_usuario_has_tipo_usuario_usuario_idx` (`usuario_rut`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tipo_usuario`
--
ALTER TABLE `tipo_usuario`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `carrera_tiene_ramos`
--
ALTER TABLE `carrera_tiene_ramos`
  ADD CONSTRAINT `fk_carrera_has_ramos_carrera1` FOREIGN KEY (`carrera_codigo`,`carrera_malla`) REFERENCES `carrera` (`codigo`, `malla`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_carrera_has_ramos_ramos1` FOREIGN KEY (`ramos_codigo`) REFERENCES `ramos` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `inscripciones_ramos`
--
ALTER TABLE `inscripciones_ramos`
  ADD CONSTRAINT `fk_usuario_has_ramos_ramos1` FOREIGN KEY (`ramos_codigo`) REFERENCES `ramos` (`codigo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_has_ramos_usuario1` FOREIGN KEY (`usuario_rut`) REFERENCES `usuario` (`rut`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario_tiene_carrera`
--
ALTER TABLE `usuario_tiene_carrera`
  ADD CONSTRAINT `fk_usuario_has_carrera_carrera1` FOREIGN KEY (`carrera_codigo`,`carrera_malla`) REFERENCES `carrera` (`codigo`, `malla`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_has_carrera_usuario1` FOREIGN KEY (`usuario_rut`) REFERENCES `usuario` (`rut`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `usuario_tiene_tipo_usuario`
--
ALTER TABLE `usuario_tiene_tipo_usuario`
  ADD CONSTRAINT `fk_usuario_has_tipo_usuario_tipo_usuario1` FOREIGN KEY (`tipo_usuario_id`) REFERENCES `tipo_usuario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_usuario_has_tipo_usuario_usuario` FOREIGN KEY (`usuario_rut`) REFERENCES `usuario` (`rut`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
