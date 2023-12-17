-- phpMyAdmin SQL Dump
-- version 4.9.7deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 26, 2021 at 02:03 PM
-- Server version: 8.0.25-0ubuntu0.20.10.1
-- PHP Version: 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ig_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `KeyValuePair`
--

CREATE TABLE `KeyValuePair` (
  `KeyName` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Value` varchar(4000) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `LastUpdateTimestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastUpdateUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

--
-- Dumping data for table `KeyValuePair`
--

INSERT INTO `KeyValuePair` (`KeyName`, `Value`, `LastUpdateTimestamp`, `LastUpdateUserName`) VALUES
('email.enabled', 'Y', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.proxy.enabled', 'N', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.proxy.server.name', 'proxysouth.mud.internal.co.za', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.proxy.server.port', '8080', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.server.name', '74.125.133.109', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.server.port', '465', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('email.smtp.password', '!dbxN0t1f1c@t10n5?', '2021-01-24 14:17:14', 'admin'),
('email.smtp.username', 'dbx.notification@gmail.com', '2021-02-03 12:49:50', 'tony.debuys@gmail.com'),
('schedule.health.crontab', '0 2 * * * ?', '2021-05-18 13:03:34', 'admin'),
('schedule.health.enabled', 'Y', '2021-05-18 15:17:46', 'admin'),
('site.base.url', 'http://23.239.8.138/ignite', '2021-01-24 14:17:14', 'admin'),
('ui.ignite.about', '<p>\nIntegrate Group (IG) is a network of small engineering firms and freelance engineering resources who join forces on \nprojects requiring more skills or expertise than what any of them own individually.\n</p>\n<p>\nThe IG network is administered by The Admin People Pty Ltd (TAP) using its IGNITE system.\n</p>\n<p>\nFree basic IGNITE functionality and administration services are offered to IG participants by TAP.\n</p>\n<p>\nPaying IG participants have access to additional IGNITE system functionality and they qualify for TAP support services:\n</p>\n<ul>\n<li>Purpose-structured file management system with cloud sync/share/backup capabilities (structured dropbox);</li>\n<li>Pro-forma administrative and managerial policies, procedures, processes, standards, guidelines, checklists;</li>\n<li>Pro-forma organisational roles and responsibilities definitions and assignment;</li>\n<li>ISO9001 compliant quality control system;</li>\n<li>General administration (fleet management, trip logging, software licensing, office stock management, deliveries & collections;</li>\n<li>Financial administration (card/cash purchase slip management, expense claims, time sheet system, bank statements, client invoice/payment statements; fellow participant payment statements; invoicing);</li>\n<li>Bookkeeping and auditing services, tax administration;</li>\n</ul>\n<p>Technical support content available for purchase (content created by various parties who share with TAP in sales income):\n<ul>\n<li>Pro-forma and template reports, drawings, agreements, contracts;</li>\n<li>Examples of drawings and reports for typical projects, based on the standard templates;</li>\n<li>Technical procedures and guidelines for typical services;</li>\n<li>Training manuals and videos;</li>\n<li>Utility software;</li>\n</ul>\n<p>Additional paid services:</p>\n<ul>\n<li>Legal advice, preparation of case-specific agreements/contracts;</li>\n<li>Personal assistant services;</li>\n<li>IGNITE customisation;</li>\n</ul>\nIntegrate Group Engineering Enterprise (Pty) Ltd (IGEE) is an industry-compliant engineering company used by IG participants as a Joint Venture vehicle.\n</p>', '2021-01-26 14:07:24', 'tony.debuys@gmail.com'),
('ui.ignite.welcome', '<p>\nWelcome to <b>Ignite</b>.\n</p>\n\n<p>\nPlease report any issues to <a href=\"mailto:tony.debuys@gmail.com\">tony.debuys@gmail.com</a>\n</p>', '2021-01-26 14:08:09', 'tony.debuys@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `KeyValuePair`
--
ALTER TABLE `KeyValuePair`
  ADD PRIMARY KEY (`KeyName`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
