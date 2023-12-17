-- phpMyAdmin SQL Dump
-- version 4.9.7deb1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jul 26, 2021 at 12:33 PM
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
-- Table structure for table `Api`
--

CREATE TABLE `Api` (
  `ApiId` bigint NOT NULL,
  `ApplicationName` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ApiKey` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `Secret` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ActiveFlag` varchar(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `LastUpdateTimestamp` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastUpdateUserName` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_bin;

--
-- Dumping data for table `Api`
--

INSERT INTO `Api` (`ApiId`, `ApplicationName`, `ApiKey`, `Secret`, `ActiveFlag`, `LastUpdateTimestamp`, `LastUpdateUserName`) VALUES
(1, 'Ignite Mobile Application', 'IgniteMobile', '$2a$10$jIPRv.IdNO0hF.hbDZtGBOvIRdF7VJ6VJ3GNt9R9V9JwhILLDFgrC', 'Y', '2021-01-14 11:19:51', 'tony.debuys@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Api`
--
ALTER TABLE `Api`
  ADD PRIMARY KEY (`ApiId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Api`
--
ALTER TABLE `Api`
  MODIFY `ApiId` bigint NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
