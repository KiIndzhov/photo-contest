-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.5.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for photo_contest
CREATE DATABASE IF NOT EXISTS `photo_contest` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `photo_contest`;

-- Dumping structure for table photo_contest.contests
CREATE TABLE IF NOT EXISTS `contests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `category_id` int(11) NOT NULL,
  `time_limit_phase_1` date NOT NULL,
  `time_limit_phase_2` datetime NOT NULL,
  `cover_photo` text DEFAULT NULL,
  `is_open` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `contests_title_uindex` (`title`),
  KEY `contests_categories_id_fk` (`category_id`),
  CONSTRAINT `contests_categories_id_fk` FOREIGN KEY (`category_id`) REFERENCES `contests_categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.contests: ~2 rows (approximately)
/*!40000 ALTER TABLE `contests` DISABLE KEYS */;
INSERT INTO `contests` (`id`, `title`, `category_id`, `time_limit_phase_1`, `time_limit_phase_2`, `cover_photo`, `is_open`) VALUES
	(1, 'Dogs', 1, '2021-04-25', '2021-04-25 10:00:00', '1617613452030_Dogs', 1),
	(2, 'Car Lovers', 5, '2021-04-15', '2021-04-15 10:00:00', '1617615067983_Car Lovers', 1),
	(3, 'Nat Geo Wild', 3, '2021-04-15', '2021-04-15 10:00:00', '1617626450282_Nat Geo Wild', 1);
/*!40000 ALTER TABLE `contests` ENABLE KEYS */;

-- Dumping structure for table photo_contest.contests_categories
CREATE TABLE IF NOT EXISTS `contests_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `categories_category_name_uindex` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.contests_categories: ~8 rows (approximately)
/*!40000 ALTER TABLE `contests_categories` DISABLE KEYS */;
INSERT INTO `contests_categories` (`id`, `category`) VALUES
	(8, 'Art'),
	(5, 'Cars'),
	(2, 'Cats'),
	(1, 'Dogs'),
	(6, 'Food'),
	(4, 'Nature'),
	(7, 'Portraits'),
	(3, 'Wild Animals');
/*!40000 ALTER TABLE `contests_categories` ENABLE KEYS */;

-- Dumping structure for table photo_contest.contests_juries
CREATE TABLE IF NOT EXISTS `contests_juries` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contests_jury_contests_id_fk` (`contest_id`),
  KEY `contests_jury_users_id_fk` (`user_id`),
  CONSTRAINT `contests_jury_contests_id_fk` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`),
  CONSTRAINT `contests_jury_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.contests_juries: ~11 rows (approximately)
/*!40000 ALTER TABLE `contests_juries` DISABLE KEYS */;
INSERT INTO `contests_juries` (`id`, `contest_id`, `user_id`) VALUES
	(1, 1, 2),
	(2, 1, 35),
	(3, 1, 3),
	(4, 1, 36),
	(5, 1, 5),
	(6, 1, 22),
	(7, 1, 12),
	(12, 2, 35),
	(13, 2, 36),
	(14, 2, 22),
	(15, 2, 12),
	(16, 3, 35),
	(17, 3, 36),
	(18, 3, 22),
	(19, 3, 12);
/*!40000 ALTER TABLE `contests_juries` ENABLE KEYS */;

-- Dumping structure for table photo_contest.contests_participants
CREATE TABLE IF NOT EXISTS `contests_participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contest_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `contest_perticipants_contests_id_fk` (`contest_id`),
  KEY `contest_perticipants_users_id_fk` (`user_id`),
  CONSTRAINT `contest_perticipants_contests_id_fk` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`),
  CONSTRAINT `contest_perticipants_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.contests_participants: ~18 rows (approximately)
/*!40000 ALTER TABLE `contests_participants` DISABLE KEYS */;
INSERT INTO `contests_participants` (`id`, `contest_id`, `user_id`) VALUES
	(1, 1, 1),
	(2, 1, 17),
	(3, 1, 18),
	(4, 1, 50),
	(5, 1, 9),
	(6, 1, 10),
	(7, 1, 11),
	(8, 1, 15),
	(9, 1, 16),
	(19, 2, 1),
	(20, 2, 50),
	(21, 2, 40),
	(22, 2, 9),
	(23, 2, 41),
	(24, 2, 10),
	(25, 2, 42),
	(26, 2, 43),
	(27, 2, 15),
	(28, 3, 17),
	(29, 3, 33),
	(30, 3, 49),
	(31, 3, 38),
	(32, 3, 41),
	(33, 3, 42),
	(34, 3, 43),
	(35, 3, 15);
/*!40000 ALTER TABLE `contests_participants` ENABLE KEYS */;

-- Dumping structure for table photo_contest.credentials
CREATE TABLE IF NOT EXISTS `credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `credentials_username_uindex` (`username`),
  KEY `user_role_id_fk` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.credentials: ~50 rows (approximately)
/*!40000 ALTER TABLE `credentials` DISABLE KEYS */;
INSERT INTO `credentials` (`id`, `username`, `password`, `role`) VALUES
	(1, 'matey', '12345678', 0),
	(2, 'stefan', '12345678', 0),
	(3, 'ivan', '12345678', 0),
	(4, 'georgi', '12345678', 0),
	(5, 'hristo', '12345678', 0),
	(6, 'ivana', '12345678', 0),
	(7, 'dima', '12345678', 0),
	(8, 'clooney', '12345678', 0),
	(9, 'beckham7', '12345678', 0),
	(10, 'virgil4', '12345678', 0),
	(11, 'becker1', '12345678', 0),
	(12, 'klopp', '12345678', 1),
	(13, 'salah11', '12345678', 0),
	(14, 'mane10', '12345678', 0),
	(15, 'bobby', '12345678', 0),
	(16, 'jhenderson', '12345678', 0),
	(17, 'etoo', '12345678', 0),
	(18, 'ramos', '12345678', 0),
	(19, 'jgomez', '12345678', 0),
	(20, 'trent66', '12345678', 0),
	(21, 'harry', '12345678', 0),
	(22, 'potter', '12345678', 1),
	(23, 'ronHP', '12345678', 0),
	(24, 'cherniqMagiosnik', '12345678', 0),
	(25, 'dtorres', '12345678', 0),
	(26, 'elnino', '12345678', 0),
	(27, 'elcanibal', '12345678', 0),
	(28, 'bom1', '12345678', 0),
	(29, 'bond', '12345678', 0),
	(30, 'jessica', '12345678', 0),
	(31, 'jalba', '12345678', 0),
	(32, 'jlo', '12345678', 0),
	(33, 'arianna', '12345678', 0),
	(34, 'lebron', '12345678', 0),
	(35, 'obama', '12345678', 1),
	(36, 'donald', '12345678', 1),
	(37, 'biden', '12345678', 0),
	(38, 'cherepa', '12345678', 0),
	(39, 'escobar', '12345678', 0),
	(40, 'Hamilton', '12345678', 0),
	(41, 'Williams', '12345678', 0),
	(42, 'messi', '12345678', 0),
	(43, 'Ronaldo', '12345678', 0),
	(44, 'barcelona40', '12345678', 0),
	(45, 'elatePilenca', '12345678', 0),
	(46, 'lampard', '12345678', 0),
	(47, 'Alexis', '12345678', 0),
	(48, 'mfox', '12345678', 0),
	(49, 'cindy', '12345678', 0),
	(50, 'lorena', '12345678', 0);
/*!40000 ALTER TABLE `credentials` ENABLE KEYS */;

-- Dumping structure for table photo_contest.photos
CREATE TABLE IF NOT EXISTS `photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_path` text NOT NULL,
  `user_id` int(11) NOT NULL,
  `contest_id` int(11) NOT NULL,
  `story` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `photos_file_path_uindex` (`file_path`) USING HASH,
  KEY `photos_contests_id_fk` (`contest_id`),
  KEY `photos_users_id_fk` (`user_id`),
  CONSTRAINT `photos_contests_id_fk` FOREIGN KEY (`contest_id`) REFERENCES `contests` (`id`),
  CONSTRAINT `photos_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.photos: ~18 rows (approximately)
/*!40000 ALTER TABLE `photos` DISABLE KEYS */;
INSERT INTO `photos` (`id`, `file_path`, `user_id`, `contest_id`, `story`) VALUES
	(1, '1617613678766_.jpg', 1, 1, 'Cavalier King Charles Spaniel'),
	(2, '1617613756960_.jpg', 17, 1, 'Hachiko'),
	(3, '1617613773736_.jpg', 18, 1, ''),
	(4, '1617613797510_.jpg', 50, 1, 'Frenchie'),
	(5, '1617613848168_.jpg', 9, 1, 'Scary dog'),
	(6, '1617613868769_.jpg', 10, 1, 'Cute dog'),
	(7, '1617613998984_.jpg', 15, 1, ''),
	(8, '1617614020569_.jpg', 16, 1, ''),
	(9, '1617614072082_.jpg', 11, 1, 'Fluffy dog'),
	(12, '1617615510447_.jpg', 1, 2, 'Cool'),
	(13, '1617615527198_.jpg', 50, 2, 'Cool'),
	(14, '1617615536954_.jpg', 40, 2, 'Cool'),
	(15, '1617615551229_.jpg', 9, 2, 'Mustang'),
	(16, '1617615594163_.jpg', 41, 2, ''),
	(17, '1617615604760_.jpg', 10, 2, ''),
	(18, '1617615614799_.jpg', 42, 2, ''),
	(19, '1617615631902_.jpg', 43, 2, 'bmw'),
	(20, '1617615645018_.jpg', 15, 2, 'bmw'),
	(21, '1617627188897_.jpg', 17, 3, 'Mighty Couple'),
	(22, '1617627266438_.jpg', 33, 3, 'Camoflage'),
	(23, '1617627286579_.jpg', 49, 3, 'Flamingo'),
	(24, '1617627304888_.jpg', 38, 3, 'Slow but safe'),
	(25, '1617627322543_.jpg', 41, 3, 'Aquamarine'),
	(26, '1617627335250_.jpg', 42, 3, 'Jelly'),
	(27, '1617627354038_.jpg', 43, 3, 'Elephant'),
	(28, '1617627382273_.jpg', 15, 3, 'Underwater');
/*!40000 ALTER TABLE `photos` ENABLE KEYS */;

-- Dumping structure for table photo_contest.reviews
CREATE TABLE IF NOT EXISTS `reviews` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) NOT NULL,
  `comment` text DEFAULT NULL,
  `jury_id` int(11) NOT NULL,
  `photo_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `reviews_photos_id_fk` (`photo_id`),
  KEY `reviews_users_id_fk` (`jury_id`),
  CONSTRAINT `reviews_photos_id_fk` FOREIGN KEY (`photo_id`) REFERENCES `photos` (`id`),
  CONSTRAINT `reviews_users_id_fk` FOREIGN KEY (`jury_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.reviews: ~99 rows (approximately)
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` (`id`, `score`, `comment`, `jury_id`, `photo_id`) VALUES
	(1, 3, NULL, 5, 1),
	(2, 10, 'Very nice photo', 35, 1),
	(3, 3, NULL, 22, 1),
	(4, 9, 'Amazing', 2, 1),
	(5, 3, NULL, 3, 1),
	(6, 3, NULL, 12, 1),
	(7, 6, 'Very cute', 36, 1),
	(8, 3, NULL, 22, 2),
	(9, 3, NULL, 35, 2),
	(10, 3, NULL, 5, 2),
	(11, 3, NULL, 12, 2),
	(12, 8, 'Good', 3, 2),
	(13, 3, NULL, 2, 2),
	(14, 7, NULL, 36, 2),
	(15, 3, NULL, 5, 3),
	(16, 3, NULL, 3, 3),
	(17, 5, NULL, 36, 3),
	(18, 3, NULL, 35, 3),
	(19, 9, NULL, 2, 3),
	(20, 3, NULL, 12, 3),
	(21, 3, NULL, 22, 3),
	(22, 7, 'Good job', 5, 4),
	(23, 3, NULL, 12, 4),
	(24, 3, NULL, 22, 4),
	(25, 3, NULL, 2, 4),
	(26, 3, NULL, 3, 4),
	(27, 3, NULL, 35, 4),
	(28, 3, NULL, 36, 4),
	(29, 3, NULL, 3, 5),
	(30, 3, NULL, 36, 5),
	(31, 3, NULL, 22, 5),
	(32, 3, NULL, 2, 5),
	(33, 3, NULL, 12, 5),
	(34, 3, NULL, 35, 5),
	(35, 3, NULL, 5, 5),
	(36, 3, NULL, 35, 6),
	(37, 3, NULL, 5, 6),
	(38, 3, NULL, 12, 6),
	(39, 3, NULL, 2, 6),
	(40, 3, NULL, 36, 6),
	(41, 3, NULL, 3, 6),
	(42, 3, NULL, 22, 6),
	(43, 3, NULL, 35, 7),
	(44, 3, NULL, 2, 7),
	(45, 3, NULL, 12, 7),
	(46, 3, NULL, 36, 7),
	(47, 3, NULL, 22, 7),
	(48, 3, NULL, 3, 7),
	(49, 3, NULL, 5, 7),
	(50, 3, NULL, 36, 8),
	(51, 3, NULL, 12, 8),
	(52, 3, NULL, 35, 8),
	(53, 3, NULL, 5, 8),
	(54, 3, NULL, 2, 8),
	(55, 3, NULL, 3, 8),
	(56, 3, NULL, 22, 8),
	(57, 3, NULL, 36, 9),
	(58, 3, NULL, 2, 9),
	(59, 3, NULL, 3, 9),
	(60, 3, NULL, 35, 9),
	(61, 3, NULL, 5, 9),
	(62, 3, NULL, 22, 9),
	(63, 3, NULL, 12, 9),
	(78, 3, NULL, 22, 12),
	(79, 3, NULL, 35, 12),
	(80, 3, NULL, 36, 12),
	(81, 3, NULL, 12, 12),
	(82, 3, NULL, 35, 13),
	(83, 3, NULL, 36, 13),
	(84, 3, NULL, 12, 13),
	(85, 3, NULL, 22, 13),
	(86, 3, NULL, 36, 14),
	(87, 3, NULL, 35, 14),
	(88, 3, NULL, 22, 14),
	(89, 3, NULL, 12, 14),
	(90, 3, NULL, 12, 15),
	(91, 3, NULL, 22, 15),
	(92, 3, NULL, 36, 15),
	(93, 3, NULL, 35, 15),
	(94, 3, NULL, 12, 16),
	(95, 3, NULL, 22, 16),
	(96, 3, NULL, 35, 16),
	(97, 3, NULL, 36, 16),
	(98, 3, NULL, 22, 17),
	(99, 3, NULL, 12, 17),
	(100, 3, NULL, 35, 17),
	(101, 3, NULL, 36, 17),
	(102, 3, NULL, 35, 18),
	(103, 3, NULL, 36, 18),
	(104, 3, NULL, 22, 18),
	(105, 3, NULL, 12, 18),
	(106, 3, NULL, 22, 19),
	(107, 3, NULL, 36, 19),
	(108, 3, NULL, 35, 19),
	(109, 3, NULL, 12, 19),
	(110, 3, NULL, 22, 20),
	(111, 3, NULL, 12, 20),
	(112, 3, NULL, 35, 20),
	(113, 3, NULL, 36, 20),
	(114, 3, NULL, 35, 21),
	(115, 3, NULL, 36, 21),
	(116, 3, NULL, 22, 21),
	(117, 3, NULL, 12, 21),
	(118, 3, NULL, 35, 22),
	(119, 3, NULL, 22, 22),
	(120, 3, NULL, 36, 22),
	(121, 3, NULL, 12, 22),
	(122, 3, NULL, 22, 23),
	(123, 3, NULL, 36, 23),
	(124, 3, NULL, 35, 23),
	(125, 3, NULL, 12, 23),
	(126, 3, NULL, 36, 24),
	(127, 3, NULL, 35, 24),
	(128, 3, NULL, 22, 24),
	(129, 3, NULL, 12, 24),
	(130, 3, NULL, 12, 25),
	(131, 3, NULL, 35, 25),
	(132, 3, NULL, 36, 25),
	(133, 3, NULL, 22, 25),
	(134, 3, NULL, 35, 26),
	(135, 3, NULL, 22, 26),
	(136, 3, NULL, 36, 26),
	(137, 3, NULL, 12, 26),
	(138, 3, NULL, 22, 27),
	(139, 3, NULL, 35, 27),
	(140, 3, NULL, 36, 27),
	(141, 3, NULL, 12, 27),
	(142, 3, NULL, 36, 28),
	(143, 3, NULL, 12, 28),
	(144, 3, NULL, 22, 28),
	(145, 3, NULL, 35, 28);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;

-- Dumping structure for table photo_contest.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `credentials_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_credentials_id_uindex` (`credentials_id`),
  CONSTRAINT `users_credentials_id_fk` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

-- Dumping data for table photo_contest.users: ~50 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`id`, `first_name`, `last_name`, `credentials_id`, `score`) VALUES
	(1, 'Matey', 'Ivanov', 1, 0),
	(2, 'Stefan', 'Stefanov', 2, 0),
	(3, 'Ivan', 'Ivanov', 3, 0),
	(4, 'Georgi', 'Ivanov', 4, 0),
	(5, 'Hristo', 'Ivanov', 5, 0),
	(6, 'Ivana', 'Dimova', 6, 0),
	(7, 'Dima', 'Dimova', 7, 0),
	(8, 'Geroge', 'Clooney', 8, 0),
	(9, 'David', 'Beckham', 9, 0),
	(10, 'Virgil', 'Van Djik', 10, 0),
	(11, 'Alisson', 'Becker', 11, 0),
	(12, 'Jurgen', 'Klopp', 12, 0),
	(13, 'Mohamed', 'Salah', 13, 0),
	(14, 'Sadio', 'Mane', 14, 0),
	(15, 'Bobby', 'Firmino', 15, 0),
	(16, 'Jordan', 'Henderson', 16, 0),
	(17, 'Samuel', 'Eto\'o', 17, 0),
	(18, 'Sergio', 'Ramos', 18, 0),
	(19, 'Joe', 'Gomez', 19, 0),
	(20, 'Trent Alexander', 'Arnold', 20, 0),
	(21, 'Harry', 'Maguire', 21, 0),
	(22, 'Harry', 'Potter', 22, 0),
	(23, 'Ron', 'Wisely', 23, 0),
	(24, 'Voldemort', 'Voldemorov', 24, 0),
	(25, 'Dani', 'Torres', 25, 0),
	(26, 'Fernando', 'Torres', 26, 0),
	(27, 'Luis', 'Suarez', 27, 0),
	(28, 'John', 'Bom', 28, 0),
	(29, 'James', 'Bond', 29, 0),
	(30, 'Jessica', 'Alba', 30, 0),
	(31, 'Jordi', 'Alba', 31, 0),
	(32, 'Jennipher', 'Lopez', 32, 0),
	(33, 'Arianna', 'Grande', 33, 0),
	(34, 'Lebron', 'James', 34, 0),
	(35, 'Barack', 'Obama', 35, 0),
	(36, 'Donald', 'Trump', 36, 0),
	(37, 'Joe', 'Biden', 37, 0),
	(38, 'Vasil', 'Bojkov', 38, 0),
	(39, 'Pablo', 'Escobar', 39, 0),
	(40, 'Lewis', 'Hamilton', 40, 0),
	(41, 'Rhys', 'Williams', 41, 0),
	(42, 'Lionel', 'Messi', 42, 0),
	(43, 'Cristiano', 'Ronaldo', 43, 0),
	(44, 'Divock', 'Origi', 44, 0),
	(45, 'Milko', 'Kalaidhiev', 45, 0),
	(46, 'Frank', 'Lampard', 46, 0),
	(47, 'Alexis', 'Ren', 47, 0),
	(48, 'Megan', 'Fox', 48, 0),
	(49, 'Cindy', 'Mello', 49, 0),
	(50, 'Lorena', 'Rae', 50, 0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
