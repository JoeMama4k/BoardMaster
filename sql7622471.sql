-- phpMyAdmin SQL Dump
-- version 4.7.1
-- https://www.phpmyadmin.net/
--
-- Servidor: sql7.freesqldatabase.com
-- Temps de generació: 30-05-2023 a les 15:23:40
-- Versió del servidor: 5.5.62-0ubuntu0.14.04.1
-- Versió de PHP: 7.0.33-0ubuntu0.16.04.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de dades: `sql7622471`
--

-- --------------------------------------------------------

--
-- Estructura de la taula `Score`
--

CREATE TABLE `Score` (
  `Game_ID` varchar(3) CHARACTER SET utf8 NOT NULL,
  `User_id` int(10) NOT NULL,
  `Score` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de la taula `Users`
--

CREATE TABLE `Users` (
  `User_ID` int(10) NOT NULL,
  `Email` char(64) CHARACTER SET utf8 NOT NULL,
  `Username` char(16) CHARACTER SET utf8 NOT NULL,
  `Password` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexos per taules bolcades
--

--
-- Index de la taula `Score`
--
ALTER TABLE `Score`
  ADD PRIMARY KEY (`Game_ID`),
  ADD UNIQUE KEY `Game_ID` (`Game_ID`),
  ADD KEY `fk_user_id` (`User_id`);

--
-- Index de la taula `Users`
--
ALTER TABLE `Users`
  ADD PRIMARY KEY (`User_ID`),
  ADD UNIQUE KEY `Email` (`Email`),
  ADD UNIQUE KEY `Username` (`Username`);

--
-- Restriccions per taules bolcades
--

--
-- Restriccions per la taula `Score`
--
ALTER TABLE `Score`
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`User_id`) REFERENCES `Users` (`User_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
