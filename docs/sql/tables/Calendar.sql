-- phpMyAdmin SQL Dump
-- version 4.9.7deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 26, 2021 at 01:41 PM
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
-- Table structure for table `Calendar`
--

CREATE TABLE `Calendar` (
  `CalendarDate` date NOT NULL,
  `EventName` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `IsPublicHoliday` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `EventDescription` varchar(4000) COLLATE utf8_bin NOT NULL,
  `LastUpdateTimestamp` datetime DEFAULT NULL,
  `LastUpdateUserName` varchar(50) COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

--
-- Dumping data for table `Calendar`
--

INSERT INTO `Calendar` (`CalendarDate`, `EventName`, `IsPublicHoliday`, `EventDescription`, `LastUpdateTimestamp`, `LastUpdateUserName`) VALUES
('2021-01-01', 'New Years Day', 'Y', 'New Years Day', '2021-03-17 12:59:20', 'admin'),
('2021-03-22', 'Human Rights Day', 'Y', 'Human Rights Day', '2021-03-17 12:59:20', 'admin'),
('2021-04-02', 'Good Friday', 'Y', 'Good Friday', '2021-03-17 12:59:20', 'admin'),
('2021-04-05', 'Family Day', 'Y', 'Family Day', '2021-03-17 12:59:20', 'admin'),
('2021-04-27', 'Freedom Day', 'Y', 'Freedom Day', '2021-03-17 12:59:20', 'admin'),
('2021-05-01', 'International Workers Day', 'Y', 'International Workers Day', '2021-03-17 12:59:20', 'admin'),
('2021-06-16', 'Youth Day', 'Y', 'Youth Day', '2021-03-17 12:59:20', 'admin'),
('2021-08-09', 'National Womens Day', 'Y', 'National Womens Day', '2021-03-17 12:59:20', 'admin'),
('2021-09-24', 'Heritage Day', 'Y', 'Heritage Day', '2021-03-17 12:59:20', 'admin'),
('2021-12-16', 'Day of Reconciliation', 'Y', 'Day of Reconciliation', '2021-03-17 12:59:20', 'admin'),
('2021-12-25', 'Christmas Day', 'Y', 'Christmas Day', '2021-03-17 12:59:20', 'admin'),
('2021-12-27', 'Boxing Day', 'Y', 'Boxing Day', '2021-03-17 12:59:20', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Calendar`
--
ALTER TABLE `Calendar`
  ADD PRIMARY KEY (`CalendarDate`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
