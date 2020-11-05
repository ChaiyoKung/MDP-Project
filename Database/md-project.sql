-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 05, 2020 at 05:12 PM
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
(1, 'cus1', 'Y3VzMQ==', 'ginggar@gmail.com', 'กิ้งก่า', 'ล่าไม้', '99/60 บ้านไก่ อ.เมือง จ.ขอนไก่ 40099', '0123456789', 'https://www.thairath.co.th/media/dFQROr7oWzulq5FZX9zvkXMWbt6JmalCpW3tUwBu9NcQI7o3Z2q2rJPVbfBCW8Smhhv.webp'),
(2, 'cus2', 'Y3VzMg==', 'cus2@gmail.com', 'cus2', 'real', '99/60 40000', '0123456789', 'https://images.squarespace-cdn.com/content/5b47794f96d4553780daae3b/1531516790942-VFS0XZE207OEYBLVYR99/profile-placeholder.jpg'),
(13, 'cus3', 'Y3VzMw==', 'cus3@gmail.com', 'cus3', 'cus3', '33/33', '0333333333', 'https://www.google.co.th/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png'),
(14, 'user', 'dXNlcg==', 'user@gmail.com', 'ไก่', 'กุ๊กๆ', '99/99 Thailand 40000', '0123456789', 'https://th-test-11.slatic.net/p/eb7130037969755476e315d73f425b67.jpg_720x720q80.jpg_.webp'),
(16, 'user2', 'dXNlcjI=', 'user2@gmail.com', 'ข้าวหุง', 'หอมจุง', '123/321 Bangkok Thailand 55512', '0226667895', 'https://bestreview.asia/wp-content/uploads/2020/01/%E0%B8%82%E0%B9%89%E0%B8%B2%E0%B8%A7%E0%B8%AB%E0%B8%AD%E0%B8%A1%E0%B8%A1%E0%B8%B0%E0%B8%A5%E0%B8%B4-600x375.jpg'),
(17, 'mingissun', 'NjEzMDIwNTM1Ng==', 'athitayaph@kkumail.com', 'Athitaya', 'Phankhan', 'KhonKaen University 55', '0832631078', 'https://bloximages.newyork1.vip.townnews.com/statesville.com/content/tncms/assets/v3/editorial/d/9c/d9cae2f8-fdcc-11ea-882d-6b9e14f43093/5f6b979db329f.image.jpg');

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
(990000001, 'KRG-MASTER-99', 'bWFzdGVyLTI2NTMxNDY1NTM2', 'Master', 'Final', '0999999999');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `order_id` int(11) NOT NULL,
  `order_date` varchar(10) NOT NULL,
  `order_track` varchar(45) DEFAULT NULL,
  `cus_id` int(11) NOT NULL,
  `transport_id` int(11) DEFAULT 1,
  `status_id` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`order_id`, `order_date`, `order_track`, `cus_id`, `transport_id`, `status_id`) VALUES
(1, '2020-11-01', 'TH123456789EMS', 1, 3, 4),
(11, '2020-11-03', 'KER987654321RY', 2, 3, 3),
(12, '2020-11-03', NULL, 2, 1, 1),
(13, '2020-11-03', 'TH555555555REG', 13, 1, 3),
(14, '2020-11-03', NULL, 13, 1, 1),
(15, '2020-11-04', 'KER999999991RY', 1, 3, 4),
(16, '2020-11-05', 'KER156548456489RY', 1, 3, 4),
(24, '2020-11-05', 'TH123564985489EMS', 14, 2, 3),
(25, '2020-11-05', NULL, 14, 3, 2),
(26, '2020-11-05', NULL, 14, 1, 1),
(27, '2020-99-99', NULL, 16, 1, 1),
(28, '2020-11-05', 'TH12356987EMS', 1, 2, 4),
(29, '2020-11-05', 'TH156445648454REG', 1, 1, 4),
(30, '2020-11-05', NULL, 1, 1, 1),
(31, '2020-11-05', 'KER123987654RY', 17, 3, 4),
(32, '2020-11-05', NULL, 17, 1, 1);

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
(1, 3, 1),
(11, 4, 1),
(13, 1, 1),
(1, 2, 8),
(15, 5, 5),
(15, 1, 2),
(15, 2, 2),
(15, 7, 1),
(24, 7, 1),
(25, 1, 1),
(25, 2, 1),
(25, 3, 1),
(25, 4, 1),
(25, 5, 1),
(25, 7, 1),
(16, 11, 30),
(28, 5, 4),
(28, 4, 4),
(28, 7, 3),
(29, 2, 1),
(31, 11, 2),
(31, 13, 5);

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
(2, 'กิมจิลูกพลับ', 'กิมจิลูกพลับ รสชาตเหมือนได้ขึ้นสวรรค์', 46, 120, 'https://pbs.twimg.com/media/EleE3BVVcAAB87S.jpg'),
(3, 'สาหร่ายแผ่นใหญ่สําหรับทําคิมบับ', 'สาหร่ายแผ่นใหญ่สําหรับทําคิมบับ ราคคาถูก ได้หลายแผ่น ซื้อครั้งเดียวใช้ได้ยาวๆ', 35, 40, 'https://shop-api.readyplanet.com/v1/image/0x0/6de689f8350f4f61a98ce237a7077cad'),
(4, 'สูตรลับเกาหลี! ซอสพริกเกาหลี SUPER HOT เผ็ดมาก', 'สูตรลับเกาหลี! ซอสพริกเกาหลี SUPER HOT เผ็ดมาก ย้ำอีกครั้ง เผ็ดมากๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆๆ อย่ากินในปริมาณที่เยอะ ไม่เช่นนั้นจะได้เข้าโรงพยาบาล', 78, 125, 'https://shop-image.readyplanet.com/DLaN4cNEKhXlqLzutLlhXbdI7to=/8bb927f7b2d54b01a4b2964a90d677c9'),
(5, 'ข้าวสาร Japonica Type 500 กรัม', 'ข้าวสาร Japonica Type 500 กรัม ข้าวหอม หุงง่าย เม็ดสวย อร่อย นุ่มนิ่ม ทุกคนชอบ', 169, 50, 'https://shop-api.readyplanet.com/v1/image/0x0/e1249a6a7b634f078a5f2ce539168066'),
(7, 'ต็อกป๊อกกิ', 'ต็อกป๊อกกิ พร้อมปรุง รส ชีส ตรา ดงวอน 332g.', 19, 75, 'https://cf.shopee.co.th/file/207f46838a3a136de28846e1b8fc794a'),
(11, 'หมูสามชั้นสไลซ์แช่แข็ง 500 กรัม', 'หมูสามชั้นสไลซ์กระทะอนามัยแช่แข็ง 500 กรัม', 48, 125, 'https://www.pitchameat.com/wp-content/uploads/2018/06/p-086.jpg'),
(12, 'ซอสโคชูจัง แฮชั่นดึล', 'เครื่องปรุงเกาหลีซอสโคชูจัง ขนาด 500 กรัม', 30, 270, 'https://static.bigc.co.th/media/catalog/product/8/8/8801007052397_1.jpg'),
(13, 'ซัมยังฮ็อตชิคเค่นราเม็ง(มาม่าเผ็ดเกาหลี)1แพ็ก', 'ซัมยังฮ็อตชิคเค่นราเม็ง(มาม่าเผ็ดเกาหลี)แบบแพ็ก แพ็กละ5ซอง', 12, 260, 'https://i.ebayimg.com/00/s/MTQ1OFgxNTAw/z/neQAAOSwOdpXxrHT/$_1.JPG'),
(14, 'มาม่าเกาหลี Nongshim Shin Ramyun (1แพ็ก)', 'บะหมี่กึ่งสำเร็จรูปรสเผ็ด ขนาด 120 กรัม แบบแพ็ก แพ็กละ4ซอง', 40, 100, 'https://img.biggo.com.tw/sSL7-gAvBn00iaxITXo03l2iaFGj6JkNgbuqfRjdTZIs/https://th-test-11.slatic.net/p/bacd15eb763c501146abd5070f386d39.jpg');

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
  MODIFY `cus_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `emp_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=990000005;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `product`
--
ALTER TABLE `product`
  MODIFY `product_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

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
