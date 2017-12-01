-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 30, 2016 at 04:15 AM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_perpus`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_anggota`
--

CREATE TABLE IF NOT EXISTS `tb_anggota` (
  `id_anggota` varchar(9) NOT NULL,
  `nama_anggota` varchar(9) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_anggota`
--

INSERT INTO `tb_anggota` (`id_anggota`, `nama_anggota`, `alamat`, `telepon`) VALUES
('A-001', 'Andi', 'Kudus', '085123456789'),
('A-002', 'Budi', 'Semarang', '081987654321');

-- --------------------------------------------------------

--
-- Table structure for table `tb_buku`
--

CREATE TABLE IF NOT EXISTS `tb_buku` (
  `kode_buku` varchar(5) NOT NULL,
  `judul_buku` text,
  `penulis_buku` varchar(50) DEFAULT NULL,
  `penerbit_buku` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_buku`
--

INSERT INTO `tb_buku` (`kode_buku`, `judul_buku`, `penulis_buku`, `penerbit_buku`) VALUES
('B-001', 'Belajar PHP', 'Anton Subagia', 'Elex Media Komputindo'),
('B-002', 'Kupas Tuntas Isilah NO PROGRAMMING', 'Fiqih Ismawan', 'Elex Media Komputindo'),
('B-003', 'Panduan Lengkap Pemrograman Android', 'Zamrony P. Juhara', 'Andi');

-- --------------------------------------------------------

--
-- Table structure for table `tb_detilkembali`
--

CREATE TABLE IF NOT EXISTS `tb_detilkembali` (
  `no_detilkembali` int(11) NOT NULL,
  `no_transaksi_k` varchar(15) NOT NULL,
  `kode_buku` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tb_detilpinjam`
--

CREATE TABLE IF NOT EXISTS `tb_detilpinjam` (
  `no_detilpinjam` int(11) NOT NULL,
  `no_transaksi_p` varchar(15) NOT NULL,
  `kode_buku` varchar(5) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_detilpinjam`
--

INSERT INTO `tb_detilpinjam` (`no_detilpinjam`, `no_transaksi_p`, `kode_buku`) VALUES
(1, '20161128001', 'B-002'),
(2, '20161128001', 'B-001'),
(3, '20161128002', 'B-003');

-- --------------------------------------------------------

--
-- Table structure for table `tb_masterkembali`
--

CREATE TABLE IF NOT EXISTS `tb_masterkembali` (
  `no_transaksi_k` varchar(15) NOT NULL,
  `tanggal_kembali` int(11) NOT NULL,
  `id_anggota` varchar(9) NOT NULL,
  `no_akhir_k` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tb_masterpinjam`
--

CREATE TABLE IF NOT EXISTS `tb_masterpinjam` (
  `no_transaksi_p` varchar(15) NOT NULL,
  `tanggal_pinjam` date NOT NULL,
  `id_anggota` varchar(9) NOT NULL,
  `no_akhir_p` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_masterpinjam`
--

INSERT INTO `tb_masterpinjam` (`no_transaksi_p`, `tanggal_pinjam`, `id_anggota`, `no_akhir_p`) VALUES
('20161128001', '2016-11-28', 'A-001', 1),
('20161128002', '2016-11-28', 'A-002', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE IF NOT EXISTS `tb_user` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`username`, `password`, `status`) VALUES
('admin', 'admin', 'admin'),
('staff', 'staff', 'staff');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_anggota`
--
ALTER TABLE `tb_anggota`
  ADD PRIMARY KEY (`id_anggota`);

--
-- Indexes for table `tb_buku`
--
ALTER TABLE `tb_buku`
  ADD PRIMARY KEY (`kode_buku`);

--
-- Indexes for table `tb_detilkembali`
--
ALTER TABLE `tb_detilkembali`
  ADD PRIMARY KEY (`no_detilkembali`),
  ADD KEY `kode_buku` (`kode_buku`),
  ADD KEY `no_transaksi_k` (`no_transaksi_k`);

--
-- Indexes for table `tb_detilpinjam`
--
ALTER TABLE `tb_detilpinjam`
  ADD PRIMARY KEY (`no_detilpinjam`),
  ADD KEY `no_transaksi_p` (`no_transaksi_p`),
  ADD KEY `kode_buku` (`kode_buku`);

--
-- Indexes for table `tb_masterkembali`
--
ALTER TABLE `tb_masterkembali`
  ADD PRIMARY KEY (`no_transaksi_k`),
  ADD KEY `id_anggota` (`id_anggota`);

--
-- Indexes for table `tb_masterpinjam`
--
ALTER TABLE `tb_masterpinjam`
  ADD PRIMARY KEY (`no_transaksi_p`),
  ADD KEY `id_anggota` (`id_anggota`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_detilpinjam`
--
ALTER TABLE `tb_detilpinjam`
  MODIFY `no_detilpinjam` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_detilkembali`
--
ALTER TABLE `tb_detilkembali`
  ADD CONSTRAINT `tb_detilkembali_ibfk_1` FOREIGN KEY (`kode_buku`) REFERENCES `tb_buku` (`kode_buku`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detilkembali_ibfk_2` FOREIGN KEY (`no_transaksi_k`) REFERENCES `tb_masterkembali` (`no_transaksi_k`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_detilpinjam`
--
ALTER TABLE `tb_detilpinjam`
  ADD CONSTRAINT `tb_detilpinjam_ibfk_1` FOREIGN KEY (`no_transaksi_p`) REFERENCES `tb_masterpinjam` (`no_transaksi_p`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detilpinjam_ibfk_2` FOREIGN KEY (`kode_buku`) REFERENCES `tb_buku` (`kode_buku`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_masterkembali`
--
ALTER TABLE `tb_masterkembali`
  ADD CONSTRAINT `tb_masterkembali_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `tb_anggota` (`id_anggota`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_masterpinjam`
--
ALTER TABLE `tb_masterpinjam`
  ADD CONSTRAINT `tb_masterpinjam_ibfk_1` FOREIGN KEY (`id_anggota`) REFERENCES `tb_anggota` (`id_anggota`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
