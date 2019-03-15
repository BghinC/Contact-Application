-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 15 mars 2019 à 16:23
-- Version du serveur :  5.7.21
-- Version de PHP :  5.6.35

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `contact_app`
--

-- --------------------------------------------------------

--
-- Structure de la table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `idaddress` int(11) NOT NULL AUTO_INCREMENT,
  `number` int(11) NOT NULL,
  `street` varchar(100) NOT NULL,
  `postalCode` int(11) NOT NULL,
  `city` varchar(100) NOT NULL,
  PRIMARY KEY (`idaddress`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `address`
--

INSERT INTO `address` (`idaddress`, `number`, `street`, `postalCode`, `city`) VALUES
(1, 36, 'Boulevard Vauban', 59000, 'Lille');

-- --------------------------------------------------------

--
-- Structure de la table `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `idperson` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(100) NOT NULL,
  `lastname` varchar(100) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `phonenumber` varchar(100) NOT NULL,
  `address_id` int(11) NOT NULL,
  `emailaddress` varchar(100) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `avatarPath` varchar(255) NOT NULL,
  PRIMARY KEY (`idperson`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `person`
--

INSERT INTO `person` (`idperson`, `firstname`, `lastname`, `nickname`, `phonenumber`, `address_id`, `emailaddress`, `birthdate`, `avatarPath`) VALUES
(1, 'Jules', 'Guiot', '', '', 0, '', NULL, ''),
(2, 'Clément', 'Béghin', 'BghinC', '', 0, '', '2019-02-01', ''),
(3, 'Thibaut', 'Mouton', '', '', 26, '', '2019-03-05', '');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
