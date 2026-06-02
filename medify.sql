-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Bulan Mei 2026 pada 09.59
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE TABLE `obat` (
  `id_obat` int(11) NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `harga` int(11) NOT NULL,
  `stok` int(11) NOT NULL,
  `jenis` enum('Biasa','Resep') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



INSERT INTO `obat` (`id_obat`, `nama_obat`, `harga`, `stok`, `jenis`) VALUES
(1, 'Paracetamol', 10000, 50, 'Biasa'),
(2, 'Promag', 15000, 40, 'Biasa'),
(3, 'Bodrex', 8000, 35, 'Biasa'),
(4, 'Komix', 12000, 25, 'Biasa'),
(5, 'Amoxilin', 25000, 30, 'Resep'),
(6, 'Tramadol', 50000, 20, 'Resep'),
(7, 'Metformin', 35000, 25, 'Resep'),
(8, 'Captopril', 20000, 15, 'Resep'),
(9, 'Cefixime', 45000, 10, 'Resep'),
(10, 'Asam Mefenamat 500 mg', 5000, 35, 'Resep'),
(11, 'Omeprazole 20 mg', 15000, 10, 'Resep'),
(12, 'Lansoprazole 30 mg', 20000, 15, 'Resep'),
(13, 'Simvastatin 20 mg', 10000, 10, 'Resep'),
(14, 'Cardio Aspirin', 38000, 20, 'Resep'),
(15, 'Siladex Cough n Cold', 24000, 18, 'Biasa'),
(16, 'Tolak Angin(Box)', 42000, 5, 'Biasa'),
(17, 'Tolak Angin (Bungkus)', 4500, 30, 'Biasa'),
(18, 'Lespain Cream 15g', 12700, 7, 'Biasa'),
(19, 'FreshCare Roll On', 15500, 20, 'Biasa'),
(20, 'Combantrin 250mg', 22000, 14, 'Biasa');


CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `nama_customer` varchar(100) NOT NULL,
  `nama_obat` varchar(100) NOT NULL,
  `jenis_obat` varchar(50) NOT NULL,
  `jumlah_beli` int(11) NOT NULL,
  `harga_satuan` int(11) NOT NULL,
  `total_bayar` int(11) NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `obat`
  ADD PRIMARY KEY (`id_obat`);


ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);


ALTER TABLE `obat`
  MODIFY `id_obat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;


ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
