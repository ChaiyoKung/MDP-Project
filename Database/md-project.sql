-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 30, 2020 at 03:36 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `md-project`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `cus_id` int(11) NOT NULL,
  `cus_username` varchar(45) NOT NULL,
  `cus_password` varchar(45) NOT NULL,
  `cus_email` varchar(100) NOT NULL,
  `cus_fname` varchar(45) NOT NULL,
  `cus_lname` varchar(45) NOT NULL,
  `cus_address` varchar(500) NOT NULL,
  `cus_tel` varchar(10) NOT NULL,
  `cus_image` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`cus_id`, `cus_username`, `cus_password`, `cus_email`, `cus_fname`, `cus_lname`, `cus_address`, `cus_tel`, `cus_image`) VALUES
(1, 'cus1', 'Y3VzMQ==', 'cus@gmail.com', 'cus_fname', 'cus_lname', '99/60 บ้านไก่ อ.เมือง จ.ขอนไก่ 40099', '0123456789', 'https://images.squarespace-cdn.com/content/5b47794f96d4553780daae3b/1531516790942-VFS0XZE207OEYBLVYR99/profile-placeholder.jpg'),
(2, 'cus2', 'Y3VzMg==', 'cus2@gmail.com', 'cus2', 'real', '99/60 40000', '0123456789', 'https://images.squarespace-cdn.com/content/5b47794f96d4553780daae3b/1531516790942-VFS0XZE207OEYBLVYR99/profile-placeholder.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `emp_id` int(11) NOT NULL,
  `emp_username` varchar(45) NOT NULL,
  `emp_password` varchar(45) NOT NULL,
  `emp_fname` varchar(45) NOT NULL,
  `emp_lname` varchar(45) NOT NULL,
  `emp_tel` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`emp_id`, `emp_username`, `emp_password`, `emp_fname`, `emp_lname`, `emp_tel`) VALUES
(990000001, 'KRS-MASTER-99', 'a3JzLW1Ac3Rlcg==', 'Master', 'Final', '0999999999'),
(990000004, 'KRS-Submaster-99', 'a3JzLXN1Ym1Ac3Rlcg==', 'Sub', 'Master', '0111222999');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `order_date` date NOT NULL,
  `order_track` varchar(45) DEFAULT NULL,
  `cus_id` int(11) NOT NULL,
  `transport_id` int(11) DEFAULT NULL,
  `status_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `order_date`, `order_track`, `cus_id`, `transport_id`, `status_id`) VALUES
(1, '2020-10-25', NULL, 1, NULL, 1),
(2, '2020-10-25', NULL, 2, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

CREATE TABLE `order_detail` (
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `order_detail_product_amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order_detail`
--

INSERT INTO `order_detail` (`order_id`, `product_id`, `order_detail_product_amount`) VALUES
(1, 5, 3),
(1, 4, 2),
(1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `product_id` int(11) NOT NULL,
  `product_name` varchar(100) NOT NULL,
  `product_detail` varchar(1000) NOT NULL,
  `product_amount` int(11) NOT NULL,
  `product_price` int(11) NOT NULL,
  `product_image` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`product_id`, `product_name`, `product_detail`, `product_amount`, `product_price`, `product_image`) VALUES
(1, 'กิมจิน้ำผักและผลไม้', 'กิมจิน้ำผักและผลไม้ อร่อยถูกใจวัยรุ่นและวัยสูงอายุ', 20, 100, 'https://scontent.fbkk2-8.fna.fbcdn.net/v/t1.0-9/122167184_1025442754596862_2083203713209262796_o.jpg?_nc_cat=100&ccb=2&_nc_sid=8bfeb9&_nc_eui2=AeE-a8hlo0cB81jzelogxx_2C1tdDAgW2DcLW10MCBbYN2Lv01oIu8VzA7hSbMRwKm5Mw3nzSonPd6jQ6Mi8djKX&_nc_ohc=X67TFkJDLa4AX-IbK3Z&_nc_ht=scontent.fbkk2-8.fna&oh=ab597a62a9a1d57ad4e72b9c9026eef8&oe=5FBB3AFE'),
(2, 'กิมจิลูกพลับ', 'กิมจิลูกพลับ รสชาตเหมือนได้ขึ้นสวรรค์', 10, 120, 'https://scontent.fbkk2-6.fna.fbcdn.net/v/t1.0-9/122119749_1024642888010182_3664546807994960719_o.jpg?_nc_cat=107&ccb=2&_nc_sid=8bfeb9&_nc_eui2=AeFUfcOlBXooJBHosFasLyRs4qwIoUYReSPirAihRhF5I-aHctNKrMLbvJvdDtbWX5NIZhxn6zWvn_3M7Qja5eeE&_nc_ohc=HCJBNIjOtQ8AX9UQqbn&_nc_ht=scontent.fbkk2-6.fna&oh=93c984278a89f519d8597e3dd7d3cb49&oe=5FBCA7E3'),
(3, 'สาหร่ายแผ่นใหญ่สําหรับทําคิมบับ', 'สาหร่ายแผ่นใหญ่สําหรับทําคิมบับ ราคคาถูก ได้หลายแผ่น ซื้อครั้งเดียวใช้ได้ยาวๆ', 50, 40, 'https://shop-api.readyplanet.com/v1/image/0x0/6de689f8350f4f61a98ce237a7077cad'),
(4, 'สูตรลับเกาหลี! ซอสพริกเกาหลี SUPER HOT เผ็ดมาก', 'สูตรลับเกาหลี! ซอสพริกเกาหลี SUPER HOT เผ็ดมาก ย้ำอีกครั้ง เผ็ดมากๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆ อย่ากินในปริมาณที่เยอะ ไม่เช่นนั้นจะได้เข้าโรงพยาบาล', 90, 125, 'https://shop-image.readyplanet.com/DLaN4cNEKhXlqLzutLlhXbdI7to=/8bb927f7b2d54b01a4b2964a90d677c9'),
(5, 'ข้าวสาร Japonica Type 500 กรัม', 'ข้าวสาร Japonica Type 500 กรัม ข้าวหอม หุงง่าย เม็ดสวย อร่อย นุ่มนิ่ม ทุกคนชอบ', 200, 50, 'https://shop-api.readyplanet.com/v1/image/0x0/e1249a6a7b634f078a5f2ce539168066');

-- --------------------------------------------------------

--
-- Table structure for table `status`
--

CREATE TABLE `status` (
  `status_id` int(11) NOT NULL,
  `status_name` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `status`
--

INSERT INTO `status` (`status_id`, `status_name`) VALUES
(1, 'รอชำระเงิน'),
(2, 'ชำระเงินเรียบร้อย'),
(3, 'กำลังจัดส่งสินค้า'),
(4, 'เสร็จสิ้น');

-- --------------------------------------------------------

--
-- Table structure for table `transport`
--

CREATE TABLE `transport` (
  `transport_id` int(11) NOT NULL,
  `transport_name` varchar(45) NOT NULL,
  `transport_cost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `transport`
--

INSERT INTO `transport` (`transport_id`, `transport_name`, `transport_cost`) VALUES
(1, 'ไปรษณีย์ไทย - ลงทะเบียน', 30),
(2, 'ไปรษณีย์ไทย - EMS', 40),
(3, 'Kerry Express', 45);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`cus_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`emp_id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk_cus_id` (`cus_id`),
  ADD KEY `fk_transport_id` (`transport_id`),
  ADD KEY `fk_status_id` (`status_id`);

--
-- Indexes for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD KEY `fk_order_id` (`order_id`),
  ADD KEY `fk_product_id` (`product_id`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`product_id`);

--
-- Indexes for table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`status_id`);

--
-- Indexes for table `transport`
--
ALTER TABLE `transport`
  ADD PRIMARY KEY (`transport_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `cus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=990000005;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `status`
--
ALTER TABLE `status`
  MODIFY `status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `transport`
--
ALTER TABLE `transport`
  MODIFY `transport_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order`
--
ALTER TABLE `order`
  ADD CONSTRAINT `fk_cus_id` FOREIGN KEY (`cus_id`) REFERENCES `customer` (`cus_id`),
  ADD CONSTRAINT `fk_status_id` FOREIGN KEY (`status_id`) REFERENCES `status` (`status_id`),
  ADD CONSTRAINT `fk_transport_id` FOREIGN KEY (`transport_id`) REFERENCES `transport` (`transport_id`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `fk_order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`),
  ADD CONSTRAINT `fk_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
