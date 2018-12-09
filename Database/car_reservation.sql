-- MySQL dump 10.13  Distrib 5.7.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: car_reservation
-- ------------------------------------------------------
-- Server version	5.5.5-10.3.9-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `plate_number` varchar(10) COLLATE utf8mb4_croatian_ci NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CAR_MODEL` (`model_id`),
  KEY `FK_CAR_LOCATION` (`location_id`),
  KEY `FK_CAR_COMPANY` (`company_id`),
  CONSTRAINT `FK_CAR_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_CAR_LOCATION` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_CAR_MODEL` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `logo` longblob NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (3,'Telegroup LTD','�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\�\0\0\02\0\0\07�s\0\0\0gAMA\0\0���a\0\0\0 cHRM\0\0z&\0\0��\0\0�\0\0\0�\�\0\0u0\0\0\�`\0\0:�\0\0p��Q<\0\0\0bKGD\0\0\0\0\0\0�C�\0\0\0tIME\�\r\n�\n\�n\0\0\Z\ZIDATx\�\�yt\�}\�?\�=fp\�A�\0O�%�:(Q�ʑe\�J|m�)k�^�v%�%/Y)N^�C�����c˶��-Ѷ,K�d�:i��x !\�`�`\�\�\�?z\0\�tDT\"\���y$\�=\�U��o\�\�,(\�RJ9\�{s\�\���(\�\�Q\�\�N\�\�or�\�!���\�r\�\�\"\"G�r��\�\�9Q\�{݁��a�\�O�\�\�=��\�v,I)))\�\�O?��ʒ����9��pa�&\�L.x\'\�\r�{\�\��\�|\���v���7y\�l^��\�r���-��@\�B\�x��)@�\�Fq\�x��W�\�-D�\�\�.\�\��\"�\��\�=�@\�\�Å@������\�4o-��1�\�\�ra9Y\�TEQ�o!f�8��\�5\�����\�x��DQ4PTP](nx�P�Q\�V\�^q3\�\����\'�\��\�O8)\�;^�\�xeA-A\�\�w޼�y�g��hҖl���nYuFus!!B]�?�\��\��1H\�\�B`濜ecJ�q�A\�p;�\�U\�\'-\�\��\�M8��@�Yd[(\�IP�\�QK�\�I�v`����\�y>7k\Z�hP���:�=�{A��[���\\���W\�j6\�v���8\�Ec=�NJ\�\�V��\�\�\�xB��{ض\�BJ*J4.��g:A�\'�\�\�mO�0,y^�L\�^\�\���\�9�{�7\�\�\�;�\�\�\�o�լ��\�\�$\'�C(6\�����T�.�w\�\�?@짟\�\�\�o\�fs�e�x�r\�\Z�@+[�\�}\�\��p��<Fja	\�r�Uo��k)!�ԉ%R$S�)p�4�>/��\�\�}z�\�X�<S	4/�$\�\�\�BK��%u�)!$^�F \�C�ߛ]~\�m\�v~\n�\�\�\�UG���=(y%(\�Ke4ud2�LD@OY�+iD)�9o�\�`,#�|YG�aK\�ē:)\��\�\�\����<�]��B�\�\���$���}n��lM����N���\�~�̥�S\����b\0Iݠ�\'\�[-�l裏?\�h8F,�c\n�[\�(*�lQ\�_\�\�-W���d����{�XRG�!�KSY��:\�:2Mr�c�=-��t\rqjh��������\�EY��5MU\�r\�\n�on\�;\�x�\���ϙlj�rܫv\�j���	\�Wdy(����#\�N`���\��<Z\�&�\����<��\�j\�\�\�\0B�m\�R\�f5I\�\�\�ou�\��i\�\"\ZO�\�7��=7̒\�\�\�$o?\�\�\�>��a !M�	gɡ0ॶ���\�k�f\�R.Y]G�w�\"v�c�\�u�b!$7m]\�M�/\�\�\�<�\�q��?|\�:.]��\�x�o\�|�\�db\��\����n����\�\�婗�r\��iB\����=,_\\\��m\���VȻpM\��\'\�\�w�\�lʰ\�) R�~�v\�\���-�W\�a}\�\\\�I\��\�1���ʾ.�����q����(�)�f%��u�D��\�;w�p�\�\�<t\�\�lYS?\�\�\�@��e\nxCa��UK+g�~2�\��\�\�\�`�i\"�I����5�\"\�}xGJv9\�\�\�\�k���O\�\�\�\�\�I\����\�\�&A���\\z\�+?�j�&գU�ý�x��,R�3%�\�X�o�t/o\�A˰JK)YZW\�\�D�/}\�e~�\�LĒ������w\�\�;=\�G\�d\�\�-t��\'t��\ZP�)�D��ӯ�Q��\�u�6q\�Ƿ�iU݌>��V?���GXQ`\��\�\��\�|��G�ܓyב�\�\�\�+V\0\�vb���\�L\�S�<\�BH����n\��\Z_��nFƢ3\�\"�#��<�f;�u#\�{3%\�1??\�i<����\\��\�[wfmƳ\�\�x���\��\�D����*\�}z?�HMUP%����g	���\'�\�?=\����\�\��D����؆��Ԕ�\�,�t�y�[/�\�\�I$uTUEU\�L+\�Y}�\'\r�\�!���\�ￓ´j&B]$�~�\�)�\0��\�{\�\�نU�f49��o8�ۭ\�\�ѥ�|��\�\�\���#/�\�/\�\�l�7\�UO\���N��K�}\�B��*I|�,�\'ع��\�\�!���;�r�\0L!h鲲}\\6BqA޴\�8�\�9\�3�[S3>[ImE!M��\�\�X�0���Q�54Ԗ�_\�\�?�\r)ôQR�ɣϼ\�\�\�b\��\�5�\�\�\\� \n�\�F�{�or{Q�\�\�^�G�\�_�\�\���d��\�P��H@\�TDuĥ�\�\�?�ޏ\����0}Ca[�	)YV_Ni����u�\�/\�d\�\�GI&��\� �EQPUeV\�\�۳o\�\�\�ӿ\�[�B��m_E��\�S\�l�\�\�$�e�DRV\�\�o\�g�C\nʴ\�K4\�Y�g���\�\�ɾ\�>Eq��3\�\"T�\��\�K�\�1\0\�	\�99b�\n	��\�W04:�Ɂ1[����ƺR*��\�\�fL�YUUU���1���}\�\�H\"ESH�\�!�G\"\��>`�g�Y��\�\n\�\�MK\�_~�U�����g6$�\�I��{o\�\���>�}���^\�\�\�\�x�wz��O\�m_���ڦ*\�.��\�$���s�\�\�k\�\�!H\�\�u��\�\������ؼ�n6\�D\�\�7\�1L�LFЏ=cmv��V��{\�g/\�;\�\�5D\"ed+\nc�8?\�Ղa�3\�7婭)/`ok��\�N\�\�hS��\�Csi*{[�x\�p\0��Ü�:h\��SRh�q]}�ǣ�\�X\�X�\�\�b2�\�Xψ\�T\�\Zg2�\�\�o�v�\��qZ��.\�,H�9pLaOg	j��7\�\�=|��og����x�\�vpcڐ^R]\�k�	�c3Ȥ*0�pz8LUi>-]�$u#c�$�\�\�b\�j��\��l/��\�m��lXQ\�7���t�.��ǟ\�zq�}A&\�:E�N\�\�V\�\�M���NԒ%��\�ʥ��\�E\�0-]�ig\�\�*\nLĒil�CaMc�a򏏾\�Ɂ1[O���ͫ\��o;632\�O\�v\\\�\�I�\�N�k�i?d<�C\�L\�6UO?��{�DҰ��\�.��i5x �wp\�1[h\�N�(	�2M\�-&S}C\�\\\�p\�H`\�z�V�vڛv6&\�)���}D�Ɍ/YɎm��\���\�\�qq\�\�R�D\�`\"\�\�m\��]ɤ��䱪����?z�Rʌ�A\�T\��i��桪*�9�\n\n\�$фN�p���\�D8�\�\�\�d�\�H�x\�o1[2����m\0�n3\�q\�\�\r�\�\r%=v!$.�6�.nXQî�ݼj�\�5ak\��\�?\��\�ՋH\�&�9r��t\�\�\�Lk-��\�i۾\�㚶#M!h\�DH�\�~�RR�\�cUC\0}��\��ķ�&e|\�oa\�\�Z^?\�\�}?C\�!\�OJk~^\�p�C�P��R��\rd�\�P{?{[{mw!MS�*\�g_\�)���KPد�c��}AG������\�\"{� \'\�mJw��\��\�q���o�\�{[O\�\"ۙ��D���\���R4�T6&u\��!�=�E\'A)(��o(L�H�l\��\�\�|n�j%Wnh��\�e4c˚z�����%t�\�MH��lW֥\�^w�)\�ri�t�\�\�Aۨ���\�\�<�� \�\�\�\�\�k\�\Z$�2l\�M�[\�O\\Îm���|\�\'�9p�4��y��~\�U�\�;\�D�91\�H8ś�Z�&\�7��L:\Z\�_��n��\�\�\rSO\�V&)�\�\�(�{990\��\�}�[ª�J<.�]owa\�\�Z*\nD\�)\�{��\�\n�\�j\�y\��h��9\���_��l��\�Ȕ&)@-[�Zd�A\�{�	;�k`f˚z��f��_2C\�\'\�\�\�\�\�h/�]\Z\�65L/�\��pT\�\�\"\�X$N穐�($����)�dqj8̩,�ފ��g���]C�\��e\reܲu��\�g�KӨ���y\'�9ԂLŜ]\�5�%Kg]\�\r�C\�����B,�\�\�)�\�XjFEI���w9W�\n\�W3>���g\�q�	)��\�T-�B\rU�|n�9uD\n�43�\�lA&\�e�U6[Y(�\�\�At[W�5\�\�4U�\�/\�I�9qG��\��F\'mm�)�}�YIޖ�q��\�\�yi%\'\�rZ\0�UK+)�[����#�\�\��^p<J��:-�d\�Z*\�*.�\�	�\�dI�\�3���/\�pR`\'�H\�\�W�\�g]���85v\\!��\�\0�`��ڊB��8�s��\�\�fmS5áIF\�1\�>�Bd�jM�0\��\����{���2:�\�s.H\��\�\�V��o\n����$R�\����|^�}���d\�#�\�5��\�|\�*\�\�ƭ\�\�i\�\�O��\�|\�.�f׾.�q{\'��Z%RSm���7\�0\�\�?b���\�\�\�\��IG_�\�X\��䀺ʢ�I\�y%�LN\"�ڲާUgNH�\'u&c����+YRS2�	/�\�ˬ\�Ck��c�@yI��Eet��H�\�I��*[\�\�SR�7G\�In\�b�A-n\��:�}�j6�\�LN�ԣ��V��z�\�\�q͑�(ݧ�\�5��\�\�\�t�L�\�)\�\�SY�OA�;��k�I\�㮺u\�Uq�\�~��\�\�au��$SG;\�n\�?9Lx2i�5���³���wK�l}2\�-+�s!�W\�i\��\�I\�\�\��\�P�\��a��klw9EQP)%��h�J\�\�8=�cY+*Jt�9LS��O^\�\r[�\�\��S}\�ڍ�&�V�ś�LM\�?@���\�\nl�jQ(*b�\r3\�e�;�\�WM\�\r��MڏW\�ƕ�;�)\�5�bj�?1̋ou8��P����l@7L\�@��\�\\���\�h/\�O\�\��\�\�{\�\�i1\�)8p�4?�\�Q���\�\�v\�\�.J�_\�ۑN%;��\�tB�\�y\�;�M\�x�\�vn�r%k\Z�f	\�4\�h�wN9=aǶUh�B��QF�S;�\��\�O�\�I;afߟH\���\�ìi����p\�=)\�d4\�h\� ��p\�eg��Z\�F�E�at\�rL9�F�䞯�:�����@\�B��S˖�\�[;B[�pZ]�_\�-u;5其�=X*߱\�<�\�Q�J��\��\�w\�Uo�j\�^Ҙ���\�.S�����k��ص�kV��\��7e\�%Rm\�\�\�)	|\�\���!!y칃�9�\�\\�a	Wol\�B1��3���;����-\�x9\�\�a\�\�:�͜��\n��\�\�/<\�e͋XRSB�ߋn�D&\'\���o�;�]͝\�-Ohk��n�:MU\0\�W����\�\�I��$EQx��v�ie���<.)��H�S\�N�\�\��{o�A8\�[���\�`�ڇLNfW/ad<<\�wvЪ֢��f�4�l�����:W��_^MY��\�x\�R&EQ��\��9��K�l\'�i\n����\'�\�\�u\�,V�ڮ\���\��Y\�\�ζ�FƢt;x?��\�:��\��\�\��\�Q3�r�x��\�\�\��G8aX&Y�U��\�Kl�\�\��\�KG	�3\�H�b��{ǧ��\�\�\\��\�k\�\�+\�Qǀi�B m/�|\��\�m\�\�4e\�]@JI[�0-]C\�T+�\�C�\�\�ڦ\�Y\�u�ځ��ϑx�\�`\�\�\�	\��h�u�dMi�i\�Œ\�\�&W6Tp�\��\�\��AlE�vt�\�H�\�Ee|�s��:\�Q\�Xծ�J:\�\�%�40\�\�8��yg��!�\�\�\�\�)�鯒U}�\�\�pە+\��\"�c\�r)e,��g]�-O��y[\�\��\'��<�\�>�4�\�\�T4\�\���YUUg�I�NLm�-%5\�,�>3?r\�z>r\�z�\�#:�\�e胂BUi\r�&�\��{\��m���.:di\�M�x�\�*�\0n��&!���bg\�\�\�vq\�Ƿqi�\"\�\�+�\�IBX\�\�Wml\�[�!���L0������B�t�i\n꫋�\�f��\'$�%�,��d=U\�g7\r5UaY}yV[LJ�����_\����g~��y#�;�;iM ��\���\�A\�T\�����\����e�ʐRb�\�z�\�\��%ge�ҒLS`�\�\�TN�]�\r�\�\�C��s?)ä���Ң3\�Ba�\�?|\�6�\�WSQ�V���`�rF�deCřψT<|\��5�\�\�\�[�s%�ddW�+\���u�\�	�\�_�Z\�@�\� á(�\�x�Ի�d��XR�7��.\�n-y>WzlªԐ\�)1��UUX\�P�\������w�u\�\�\�V�\��\�\�\�L�ם&��\�֔�\�-�\�\�\��\0eE~��T\��\�\�\�\�*J�*mj)-\�\��\\\�u��ΚW3\�a\n����\�\�W�/�#c!��b\�TJi$p5lC:�Zj^	j՚�my\�.�\�\�-l\�\�\�s�\�y\�@7]�F�\�I\�\�tY���x\�ֱ\�E\�Ӽ��+\�/�6J�._[�a�v%�\���W\�v\�\�uˊ��\�o\�\�\�kx\��\�\�\�K\�\�8\�$)ÜVK\\.�\�M�\�Kei��\�R\�5U��\�F\�UQu\�^y;��m�}{\�;_\�<�1~3���S��\�}��T��4�U5�Uo�\����Bpݥ��\�\�\�\��^��V�\�\�\���\'xnw;�\�`pt�h<�nX��.�J~��ʲ��V�m\�R�mZJ}��\�pϝ[(�x\���t��M\�(�}n�Ԕp�\�\�\�}�\ZjJ\�\��\\�2�g\n\�\�\\�\n�\�E$�\���aGu��$�\�\Zز���<��\�\� 86�aZGdT�\�s�\�z~\��\\��\�<1gg\�?\�(L+�\��i\�y��$u��p�\�H�X<ER7�4\�\�3\n�^\n^y\�Y���U\�	.M\�Z\�M��c�E\�ē:�!�5<n��t�^�>wքa[\�1d|\��F\�Q���NW\�\\\�\�C��Lo�+�Y#S��\�+�Z$Χ�R�\��\�1\�Q\�h	EQ�y]|��)x\�\��\'cI�G\"�O$RR\\ࣦ�p\�Y*�a:&;L�\�\'G\��\�\�\�f��0�]��}\��]!�`ht�d\�H/��T\�\��?�?\�8NU[���n\�\�T��\�\�j�\�\�ޞ+>߅��8Aq�Q\�~(�;\�6�\�\�\�P���`�w�|��K*\�9W��CGo�Pġ\��\�hJ�VU���\�\�2��ZG*吃Z\nn\�nm:��^!G�\��*m���\��X�e7]h\��\��c�x�\n+�xQeѻly~�[��\�rx��wp�\�Ñ\�\n��PB�bqE\�\�_D�B�p9�/\�3`��:BH.Y]7/����r\�\�=\�ph2]Ki?�W_py�\�\�.�\�	s!\\\�i�C9\�\�\�ED�p9\�p�#\\9\\D\��C9\�\�\�ED�p9\�p�#\\9\\D�\�H\�}B7O\0\0\0%tEXtdate:create\02018-11-20T13:18:10-05:00z2S+\0\0\0%tEXtdate:modify\02018-11-20T13:18:10-05:00o\�\0\0\0\0IEND�B`�',0),(6,'M:TEL','�PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\�\0\0\02\0\0\07�s\0\0\0gAMA\0\0���a\0\0\0 cHRM\0\0z&\0\0��\0\0�\0\0\0�\�\0\0u0\0\0\�`\0\0:�\0\0p��Q<\0\0\0bKGD\0\0\0\0\0\0�C�\0\0\0tIME\�\r6�S�k\0\0\�IDATx\�\�{�U\�?���\�mwu/��[ݥM�G���Fj	�D�J	5m\"H0\�E�D�!\r5C�ւ�c�B1\�U4�b��KYJ[�	twk�\�ݽ����\�̽wf\�\�\�n\�\�M\�O2�3�9\�wON\�\�\�\�3��`0��`0��`0�itfC��	�9���(\�\�WC9	�s�}^�\�f�`7v�X�~P`�X�Q�\0\Z+u�m<\�pxZa+\�^#�\�1�K����y�z\"\�\0]4��aє\�gb�9�$���K\�W��}\'\�f͌\\�uvw r?\�F��Z�B�t\�Líl��;\�7Ј��\r�%\�`Ww��O��yO7�何�A\�]+�f�\�\0T�\�����F�a32\�/\�\� �@,��P)\�΁���ڤ�V(���M4H+S�	\�FUnnt�5+Fp	0\�ս\��5r#>��7�(\�4ЏV�#\�\�B>��\�\�mY\����\�i�o\Z��\\\"\�:�է?�\n	ۃ#n�\�~\'�A\�5\r[\�WQ��gJ�e�\\\�\�VlF�\��@���\��P\�\�Rk\�j>���oS�\���G\�F7c3�jt�ai8�\�+�|)��d(h\"\�Mj�6\�@�,mt36#FpI�̋\Z\�I�\�\�قtD\rR=M\�Vֺ/\�@�\�VC6j�5��3�L�\�7A�\rN��F\�آ�\��\�1|4\Z%�\�\�\�o(\�\�M\�|\�JT&ꡢ\\3e�\0Fp	!\�\�T\�Q�T\�\�N\�ò���H\�\\��&=#B�c�I]�*(#HM>1rK#�P�h�Z���/�\��\�˅��Y\�\�w/\�\�u��&�� U_ZS\�F�bsb\�\�@b�C\�Ҷ���|2��\r�%tdU)\�\\�\�{\�AZ[�u\�:��f}s\�A=�md^;X:r\n-�j*$A g� �\�\�b��ՠd%�躤�\\E��/S\��ҫ?�\�y1\0\�\'��7<Bf\�\Z��Nh\�R�\�v\n[E�Eߩ\�U�ٗ^{��ճ�\���I�\\Nq\�N�݂\�\�M\�\�\�݋Aoh�\�(��O�NZ��\�\�\�lėFpI��io#�~\�6�g�\�}��\��u��\�nJO>E����^��\�;o\�\�}�\��/!){�2��\rg��\'(n߁�t	ξ������>���\�\�8��\�~�\��do�sￏSw|wo��\�ɇ�\�H.�\�� s�>������ůq��\Z���Cd>�)JϿH�\����M��7��\�rJϽ�\\���>�\�y1#�n��\�.�\�w��u\�^\n۞\0ˢ\�\�\rX^�\�\�\�=� x���\�j27|�����\�I��\Z�%�\\��&�Uu]�}Nu(� ��m�\r��\n�\r`t�\�{\�cx\����|���S`[\�+�A*M\�\�7�\�R=JK֢E~^�\�\��h3�\\L4	\�\�I�g�p^\"1��Cf���B*���e\�Α[�?E\�����W�a+<�8\�oք�M\�df1�K�zA�\�N\�\�؎���\�u��\�\�_\�Zp�G~�70Z!�t]�\�0\�\�a�ߊ�\Z���\�Uz:4if.	\�A�z[f\�+�/>O�r9Z6���m&���\�zՍ��?|,98�_���RW]	\�Q�%O�\\�}Eh���\�N=$�\�\�\�\�%¤A�\����r��T�\�\�;\��OD::p��IYP,����?����k`|�ҋ�p��ʜ{\�\"\�<�w\�\�^���[�B\�\�\�<��\�=�I]�\nw_/\n�q�	�$�\�\�\n4#\�mߝ@\�AE柏uх���:2�d3ؗ�\�\�ރw\�X){\�\�}�q_?\0����\�\�~-��䮃��\�\n2_�,X@\�gq^z\�\�\�\�x=\�k�y�\�?Sx�O\�x,��G��Z�\�_�\�\�1\�\�\�#\�jt[6\�OYu���׽iY~\�X��3E �\�	\"�ٌ?\�s�	~I�S�;imE��W�X��\�$�FG\�б�\�ZMioCK%ϗ=\���:��7�-�\r3�L��A\�TYT\�p���\�Gm\n�/T�x\�\�r$4�\�\�8::^�Z�?�pr8�2L��\n��/�=A\�ļ\�%�\\L�\�\�Mu+�Di�k;�� _H��\�5Bu�y\���\"��|��n\�f\�.	&���R�N1�S�,Ө\\�bӚ�\�d_-�\�D4��#�P�ε�����lgP�\�\�6�-�%�\�9�z\r\�\�\�J4#Fp	��%q	*��F\"A�ڪ�ԓ\�ݠ\�\�vz� \��nt6#FpI��OtZ�\Z�\�\�\�\��h�;%:m\�~�\�Q\�?4�ٚ#���U֬7�W����\�f�.�\r����\�S3\�Z�#�\�s\� \��<�;\�w�ѕif�\�\��(0Au�\"[}%\�\�\�Wǆ\�1�K��]�	P`�?=�����`0��`0��`0\�Y\��\�\��~Q�D-\0\0\0%tEXtdate:create\02018-11-20T13:16:54-05:00�Iq\0\0\0%tEXtdate:modify\02018-11-20T13:16:54-05:00e��\�\0\0\0\0IEND�B`�',0);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cost`
--

DROP TABLE IF EXISTS `cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cost`
--

LOCK TABLES `cost` WRITE;
/*!40000 ALTER TABLE `cost` DISABLE KEYS */;
INSERT INTO `cost` VALUES (1,'Service & Maintenance'),(2,'Fuel'),(3,'Other');
/*!40000 ALTER TABLE `cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expense`
--

DROP TABLE IF EXISTS `expense`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expense` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  `user_id` int(11) NOT NULL,
  `reservation_id` int(11) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RUNNING_COST_COST` (`cost_id`),
  KEY `FK_RUNNING_COST_CAR` (`car_id`),
  KEY `FK_EXPENSE_USER` (`user_id`),
  KEY `FK_EXPENSE_RESERVATION` (`reservation_id`),
  KEY `FK_EXPENSE_COMPANY` (`company_id`),
  CONSTRAINT `FK_EXPENSE_CAR` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FK_EXPENSE_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_EXPENSE_COST` FOREIGN KEY (`cost_id`) REFERENCES `cost` (`id`),
  CONSTRAINT `FK_EXPENSE_RESERVATION` FOREIGN KEY (`reservation_id`) REFERENCES `reservation` (`id`),
  CONSTRAINT `FK_EXPENSE_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expense`
--

LOCK TABLES `expense` WRITE;
/*!40000 ALTER TABLE `expense` DISABLE KEYS */;
/*!40000 ALTER TABLE `expense` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fuel`
--

DROP TABLE IF EXISTS `fuel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fuel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fuel` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fuel`
--

LOCK TABLES `fuel` WRITE;
/*!40000 ALTER TABLE `fuel` DISABLE KEYS */;
INSERT INTO `fuel` VALUES (1,'Gasoline'),(2,'Diesel');
/*!40000 ALTER TABLE `fuel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  `address` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  `longitude` decimal(10,6) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_LOCATION_COMPANY` (`company_id`),
  CONSTRAINT `FK_LOCATION_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (2,'Tech hub','Patre 5,Banja Luka',3,44.766686,17.186979,0),(4,'M:TEL HQ','Vuka Karadzica 2,Banja Luka',6,44.775918,17.191682,1);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logger`
--

DROP TABLE IF EXISTS `logger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(128) COLLATE utf8mb4_croatian_ci NOT NULL,
  `action_details` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `table_name` varchar(128) COLLATE utf8mb4_croatian_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `user_id` int(11) NOT NULL,
  `atomic` tinyint(4) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LOGGER_USER` (`user_id`),
  KEY `FK_LOGGER_COMPANY` (`company_id`),
  CONSTRAINT `FK_LOGGER_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_LOGGER_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logger`
--

LOCK TABLES `logger` WRITE;
/*!40000 ALTER TABLE `logger` DISABLE KEYS */;
/*!40000 ALTER TABLE `logger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mail_option`
--

DROP TABLE IF EXISTS `mail_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mail_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mail_option`
--

LOCK TABLES `mail_option` WRITE;
/*!40000 ALTER TABLE `mail_option` DISABLE KEYS */;
INSERT INTO `mail_option` VALUES (1,'location'),(2,'company'),(3,'off');
/*!40000 ALTER TABLE `mail_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `manufacturer_name_uindex` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturer`
--

LOCK TABLES `manufacturer` WRITE;
/*!40000 ALTER TABLE `manufacturer` DISABLE KEYS */;
INSERT INTO `manufacturer` VALUES (1,'Audi'),(2,'BMW'),(12,'Ford'),(3,'Mercedes-Benz'),(7,'Peugeot'),(10,'Porsche'),(6,'Renault'),(8,'Škoda'),(9,'Toyota'),(4,'Volkswagen'),(5,'Volvo');
/*!40000 ALTER TABLE `manufacturer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer_id` int(11) NOT NULL,
  `model` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `engine` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `transmission` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `year` char(4) COLLATE utf8mb4_croatian_ci NOT NULL,
  `fuel_id` int(11) NOT NULL,
  `image` longblob NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_MODEL_MANUFACTURER` (`manufacturer_id`),
  KEY `FK_MODEL_FUEL_TYPE` (`fuel_id`),
  CONSTRAINT `FK_MODEL_FUEL_TYPE` FOREIGN KEY (`fuel_id`) REFERENCES `fuel` (`id`),
  CONSTRAINT `FK_MODEL_MANUFACTURER` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `start_mileage` int(11) DEFAULT NULL,
  `finish_mileage` int(11) DEFAULT NULL,
  `direction` varchar(250) COLLATE utf8mb4_croatian_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `state_id` int(11) NOT NULL DEFAULT 0,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_RESERVATION_USER` (`user_id`),
  KEY `FK_RESERVATION_CAR` (`car_id`),
  KEY `FK_RESERVATION_RESERVATION_STATUS` (`state_id`),
  KEY `FK_RESERVATION_COMPANY` (`company_id`),
  CONSTRAINT `FK_RESERVATION_CAR` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FK_RESERVATION_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_RESERVATION_RESERVATION_STATUS` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FK_RESERVATION_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'System admin'),(2,'Company admin'),(3,'User');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `state` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `state`
--

LOCK TABLES `state` WRITE;
/*!40000 ALTER TABLE `state` DISABLE KEYS */;
INSERT INTO `state` VALUES (1,'reserved'),(2,'running'),(3,'finished'),(4,'completed'),(5,'canceled');
/*!40000 ALTER TABLE `state` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_status_uindex` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'active'),(2,'inactive');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `username` varchar(80) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `password` char(128) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `status_id` int(11) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  `mail_option_id` int(11) DEFAULT NULL,
  `location_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `role_id` int(11) NOT NULL,
  `token` char(24) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `avatar` longblob DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK_USER_ROLE` (`role_id`),
  KEY `FK_USER_COMPANY` (`company_id`),
  KEY `FK_USER_MAIL_OPTION` (`mail_option_id`),
  KEY `FK_USER_LOCATION` (`location_id`),
  KEY `FK_USER_STATUS` (`status_id`),
  CONSTRAINT `FK_USER_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_USER_LOCATION` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_USER_MAIL_OPTION` FOREIGN KEY (`mail_option_id`) REFERENCES `mail_option` (`id`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FK_USER_STATUS` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'jovan.etf@gmail.com','adminTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Jovan','Jovanovic',1,0,NULL,NULL,3,2,NULL,'�\��\�\0JFIF\0\0\0\0\0\0�\�\0�\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z.&%-----+-/--++------+----7-2----------+--------+---+��\0\0\�\0\�\"\0�\�\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\�\0F\0		\0\0\0\0\0!1AQ\"aq��2r��#Rb��Bs�3CDS���\�\�$4t��\���\�\0\Z\0\0\0\0\0\0\0\0\0\0�\�\0\'\0\0\0\0\0\0\0\0!1A\"Q�34a��\�\0\0\0?\0\�M\n�!4YBhP$&� HM@��\�3X\����hԸ��^n\�m41u���\�u���\��r\�\�;A�\�ϼ�-���\�N\�\���\�ɋ\�V���f%\�5D��|�}\�z�\�Z��)Q�g�ve�\� n��5��gv\r�\�7�5\��v�\'��n�<̐r8x�\���\�1-3İ\�\�=��<�\�\�;�L�)lʋi|�arG#ܽ�\�97z{�lBvB�s��$ �,�����dY;!P���d		\�&�Ё!4 HM\n�Ё$y��� \�E!\��\�Pp}�\�\�SX�w-`\��l�Dː�\�ޘ�\�5���.\���/\"\�Їק>�\�\�j`�$\r\��\����\�i6\��sϏw�y~_*�/Jxk\�V�\�����\�pp7\���xnb\�J�Tӽn+z��n\�\�\��VV&�9ƒF\��\0\�/e���Ià��*¾�|k�HM���E\nI HM��	%+$�\"B�РHMB�����*i:\�X\�\�\���\�\�n���+\�\\å�<\�QN�\�\r\�\0,r�\�6\�\�\��q\�\�N�\�\�b\�v�\������٥�K\�#W������౽�=\�}�s��v�d�<�lFF\�<\�\�\�\�M���l�}ּ5�\�7Ǟ�Ƀ\�uNG<�\�qy�y#\�AD\�\�Cn&�k\\\�\�kQ[}\�\r\�2�\0\��\�9��\�^_j�-^\�\�|�<0�k�\0��@{�\�\�\�m<o���B\�H̜�r��e\��,��\�\�u\�\�\���$\�C^%��2�ǒ�\�f3\�瞜�k��y�\�\�aN$gZ\�<n�\�֐�8g�V\�.[\�>�UD\�vDlh�\�\��C\�k�\�\�\�ُ�\\BkLI\0�!�,�$&�BB�hM\nBB$Ё*\�\�ҵԎ��\�?-wFN��+���4�\�\� �9��Y\�����^9L�8\�r7x��tos\"5��V�\�#�!�m9q^_H8;)*�nqge\�z\�\��r\�ֲ�\�\�ޏ�~ѧK�<�|>��\�\�\�b�1��\�\Z\��۾<쵶{�2�\�Z\�\�?5\�a8\rd��\�\�,lCd�ۓb&|w��p����i�<�X�(\�\�W�Y���NdO9�\\�\0\�r�	qv����޼,[g*)�\�YX\�n#^�+\�09�\\�.�\�K�t\�I\�2�N\�\�\�	��^���\��\�\Z��[G\�ᐓ��\�y8\��[�v3�G\�\��+B�\�$�Ё!4 HM#d&�		�Q$Y4(!!I\0�kG\Zš���3�v&Z\�\�$�`\���8\�M�p<\��F��\�xw�]�k6\\b���8\�8ob�뙨c�x�|WưI\�Y<O��:�FDx,\�\�e�z\�\�>b\�-L��T@\�4XoX\�y�ef�۰جe~�\0x�c�\�l\�-�\�\�\�k\n\�X)^\�\�\�/����ˍ\�g3ǯh6�Z�,\�	e\�E�Ф�}u\\t\�\�\\\�;�\�.\�x���`�c\0\�\�:�a�y�G[\�oq\�\� r\����{\�^�s�ELأdLc\��Z\0E� �].#B�Bh@��$&��$�$&�D��(!!$\�l\�@}#izQ��%�s�e�[t\��m\�t:�\�K\�汍.q\0\08�Wϝ&m�\�e\�$Q\�{#N�\��\0h\�\�y�Z{Y\�^!x\�[=\�\"f����wӹU֤Gk\�jD>\�)�x�7	9�!��r]\�!��\�Q$g\'\����_/\�U\�SLʈ�[$n\�Py�2#�}%�{E};j#\�\��y�\rZ~��^�\�y\�\�\�\�[k\�d�^j\':X�\�\��F�.����\\\��\�\��\�i0\�A�0��sn3UM�\�ZZ֒��J��ȏ����\�XK=�\�e�\��{Z\���\�z)p�c#���\�݀\��.\�y5��B��Ca\�}A륅\�\�\�\�s%�N���/k�H\�\r#\'�\'\�<Op!\�\�Y������\�l\�:���l�\�\�\�\�\�\�#D��z2\�\�\�1c��\�B�� M$\�BB,�B�!4�BB�Ikbu�\�\����m.q<�\�{4*���$Q\�\�6�\�*�l94�7\�<3+�m�I5u\�sc{\�\�n@�� ���$�\Z*�7VAeڭ����f�2/ꣻYoŝ\�\�\�E�- \�ec\�69dcUZU�b���uH��tN\�\�Ͻ��jx)8����9blѼ:\'�x:��=ʃ�O)�D\�b$��X��{eɚ��kP\�h�dnQ#�4�%�9	xFO:\�s�]���9#1=�1��\"\�\����c\�ӟ\�l�s�p\�R#��\�\�k�\�w���mES\�p�\�3@���2\��Y͑�\�\�p^���\�\�y��v\�\�\�\�^��\�\'\�\\\�\0\�\��Gh4�y�w�I�\�\�}O�m~X\�\�o�\�&v<]\�\\�s��\�;7|TC�&� ~\�\�\��T���־S\��\�\"������\��resɯ�O�G�\\W\�\�W=�\�\"��˽����\�ɒf[\�S��9\n���$\�U���\�&u�佞\����	\�+!�@$�$!!\ZBs^��K0\�B?��`?;v�\0�.���O��\�trZ�]Cny�ߩV\�h\�\0ӨY�r\�Fp.��|�m+.�Q��Vx�|��}�k�aݱ�A���jh��\�\�#\�j5�F{U\�Ի�\�\�\Z\�4���\�|\�o\�\�`�keq\�\�\�2����\��\�>�|x����g]\Zs}�g6\�	�<C�#�;�Kě��\'�<9�\�\�\������ln�Q#���q\�\�w�H���8[�\0E|�s�\�\���S�L\�\�x_\�ͭ��D\�Mô�\�?,�O23Y�P@/��\'\�c[E�)&x�\��\�,��w���g\����P\����ϡiGЈI5�B	&�	MB�UΑ�\�S�\�\�=\�}�N�\�\�X\�d`p-:G�A�\�gm�`��Ź<�\�ݖF�5\��\�Kh\�a�Y7y�2qY�O����2�<B\�+w�ƫ��\�\�$\��\�%�EYcp�\�cRk�\�tU\�a�L�+����\�3\�\�̯G�V�\�4�n�푞�<�~^j��N$�t��]��������d�\��}��і7\�K�9\�\�h!I\��\�|3YZ8��v�55Y�VH\�g��\�\�i��-y�`\rn��٨>���&\�R\�R\�%���\"Fm�\�o�e\�n:%�}���;y��\0R�7\�g_\�.����!\0�!\0�!PЄ(!>Uۺfŉ\�\�\�tT\�Gv�\�#���\�\�[:Y�\��z�\��\�<\��`��Kh��\�\�K�\�\\?�Bv<�J[�\�>j�R�O�Pdl�X4\"\�E��GbXkH�ٙ\�\�\�\'qiȏE-\��\�,�̲K}7sU\"\�\�VJ\�\Z\�٫\rm��\�\'6��|{~�\�\� ���rp��;\�\�x�q\�m\�o��\�I�!��\�Ō�H\�Nr\��1�}\�r\�{��G��Zx\r>��\�Pb\�+�ap1�\����n@\�}*\�\���4!|�+3\�5\��\�3SHidv���\0�r`u�}-�R\�tT\�MdB�\r	��M$�\�=\�����|\�u�\�5c�w\�\�	\�h��h\�A)�\�\�?\�֮\nҵ�\n��<<TT2�\�\��ͷ��\�_��`��z8$0KSu1B\�f\�\�G,\�m�\��-c;c\�ƶ&x\r�9A�\�7wğ5\�\�\�>7ZV���2\�Gy�U\���[\�u�3�{�\�Xf�c\�&\�ݚ\�\�5��\�\��z.�\��\�_GO�̖9�#N\�Xx�8��V�\���ef\�۫Z�Ȯ�;<\�~��.}6ZsY�-bV\�]��x�*t\�*��)�%A�)�ݺ�+\�<Vq\��*5Xn\�gd��g��w�OO$�K�{�N |ן�a5Hb�\��^,|�������j�lV\�nZb\'4\�2[Q\�\�^\�K\�\�-V��1\�\�\��䈞O\�\Z�,\�\�\ZUUя5���T�C��\�0�:�C�Rs�@\�w�m���榄!\0�!���	&�?\�UK5+�\�bs<	;\��]U;���<Y\�s�\���lW\�k玚�g\�\rCG\�T��\0	Z^<�>eX(��՞V\���b�67Y\�7�}\�b؈�\�2���`p\0\�[9�n�7Н2^\�+��lU�\0�|�\���ֿ��8\0	\"�\0\�\�\�{\�!\�\�\�\��\\\�}?�۫W����iX\���Xǐr$8\rnJ�b,i�� _V_0y�aJ��I�yp��S��wu\�}�\�Q\�\��\�,�hV>��N�.�1��A\Z�6&���Zo�e��5\�	\�\'bx�\�<\n-e8-\�s���Ǹ��GZF��o�Z\�De~aw=�٨q7^��x\�%���\�\�\�/�.��؜@�\n��\�\��v~�\�o\���o�\�|��к	�Ş\��\����\0Y+6\�\��U2\������U\"\�I;�.\�+;/�2�^ӡ\'\��H�p6YX֑��f߭C�~\�_h5i��۱\�44{\"f\�\��\�/\�˿���%�lA�#PF`�5�&\�⦮���\��\�n�\0\�\�\�\�U\�! ��B��@�I*�Ҟ\�{v��^h�[{�s<\�O�\n\�RA�\�W[�+\�k\�ge}��\�mMPK�a`\�n>\�c\��U�|\�e��lnN����z_�e��\�ee�䨉?��FV\�T\�\�~\�Q9�\�౜�Sy\�A�&NK�%bqPL�:E���\�N��\�侏ؘ\���6�͍�>�\�\n`\�\�\�������l�\�����\�v\\<\�~�Xee\�e%ǟo7$\�O�nL\�G�/8�ڌ��\�<�v�w�Iq<K��*}�Ȭ\�v\�6�\n[��e�\�~?�A�˽tZ_��#�S��\�sC���\�[U\��\0?�\�_��\0\��ꩤ��Є hB$�)��\�E�\0h,)\r\�I�h�\� c�W]�\0h\�\��\0�q\�?��\�F�\Z\�6X�z��TNG\�X(�Yb\�Qt�2J���b.P.S�#ޠۓa�\�\����.\�ǂ\�up\�23q�\�\Zp\��.�M�RQ\�#\�cn\"\�\�Ȓx��Lu\�;}��{�8\0��[0\�lΫ\�\�L(ST9�;и\�\�y\�\�v�i伬�\�gg�	X�9���3�\�K�Ӻ�NjN\0�AL\���Z\\O�V�+�<N|���oޚF��\0\�\�\�%K\�\�}Ю\�p�#���G�`֟0/\�͖\�\\\�kd3�f#h݊�\0���\� ��\Z\0\0\0\0\0\04\0d\0\nUH&�j4��E$!\0R(B�� \�?�\0D������G��V�u*2�\n��4!JX\��GW\�\��Q�\'�r�{mx~\��P�\�\�\��\Z�\�R6�ݥ�˾�T\�!yg�\�\�JBF��Y�Ѕ \���\0/7\�ϫ\�g�C\�)U�)�\n��B�\�'),(3,'jovan.etf@gmail.com','rootTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Mirko','Mirin',1,0,NULL,NULL,NULL,1,NULL,'�\��\�\0JFIF\0\0\0\0\0\0�\�\0�\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z.&%-----+-/--++------+----7-2----------+--------+---+��\0\0\�\0\�\"\0�\�\0\0\0\0\0\0\0\0\0\0\0\0\0\0�\�\0F\0		\0\0\0\0\0!1AQ\"aq��2r��#Rb��Bs�3CDS���\�\�$4t��\���\�\0\Z\0\0\0\0\0\0\0\0\0\0�\�\0\'\0\0\0\0\0\0\0\0!1A\"Q�34a��\�\0\0\0?\0\�M\n�!4YBhP$&� HM@��\�3X\����hԸ��^n\�m41u���\�u���\��r\�\�;A�\�ϼ�-���\�N\�\���\�ɋ\�V���f%\�5D��|�}\�z�\�Z��)Q�g�ve�\� n��5��gv\r�\�7�5\��v�\'��n�<̐r8x�\���\�1-3İ\�\�=��<�\�\�;�L�)lʋi|�arG#ܽ�\�97z{�lBvB�s��$ �,�����dY;!P���d		\�&�Ё!4 HM\n�Ё$y��� \�E!\��\�Pp}�\�\�SX�w-`\��l�Dː�\�ޘ�\�5���.\���/\"\�Їק>�\�\�j`�$\r\��\����\�i6\��sϏw�y~_*�/Jxk\�V�\�����\�pp7\���xnb\�J�Tӽn+z��n\�\�\��VV&�9ƒF\��\0\�/e���Ià��*¾�|k�HM���E\nI HM��	%+$�\"B�РHMB�����*i:\�X\�\�\���\�\�n���+\�\\å�<\�QN�\�\r\�\0,r�\�6\�\�\��q\�\�N�\�\�b\�v�\������٥�K\�#W������౽�=\�}�s��v�d�<�lFF\�<\�\�\�\�M���l�}ּ5�\�7Ǟ�Ƀ\�uNG<�\�qy�y#\�AD\�\�Cn&�k\\\�\�kQ[}\�\r\�2�\0\��\�9��\�^_j�-^\�\�|�<0�k�\0��@{�\�\�\�m<o���B\�H̜�r��e\��,��\�\�u\�\�\���$\�C^%��2�ǒ�\�f3\�瞜�k��y�\�\�aN$gZ\�<n�\�֐�8g�V\�.[\�>�UD\�vDlh�\�\��C\�k�\�\�\�ُ�\\BkLI\0�!�,�$&�BB�hM\nBB$Ё*\�\�ҵԎ��\�?-wFN��+���4�\�\� �9��Y\�����^9L�8\�r7x��tos\"5��V�\�#�!�m9q^_H8;)*�nqge\�z\�\��r\�ֲ�\�\�ޏ�~ѧK�<�|>��\�\�\�b�1��\�\Z\��۾<쵶{�2�\�Z\�\�?5\�a8\rd��\�\�,lCd�ۓb&|w��p����i�<�X�(\�\�W�Y���NdO9�\\�\0\�r�	qv����޼,[g*)�\�YX\�n#^�+\�09�\\�.�\�K�t\�I\�2�N\�\�\�	��^���\��\�\Z��[G\�ᐓ��\�y8\��[�v3�G\�\��+B�\�$�Ё!4 HM#d&�		�Q$Y4(!!I\0�kG\Zš���3�v&Z\�\�$�`\���8\�M�p<\��F��\�xw�]�k6\\b���8\�8ob�뙨c�x�|WưI\�Y<O��:�FDx,\�\�e�z\�\�>b\�-L��T@\�4XoX\�y�ef�۰جe~�\0x�c�\�l\�-�\�\�\�k\n\�X)^\�\�\�/����ˍ\�g3ǯh6�Z�,\�	e\�E�Ф�}u\\t\�\�\\\�;�\�.\�x���`�c\0\�\�:�a�y�G[\�oq\�\� r\����{\�^�s�ELأdLc\��Z\0E� �].#B�Bh@��$&��$�$&�D��(!!$\�l\�@}#izQ��%�s�e�[t\��m\�t:�\�K\�汍.q\0\08�Wϝ&m�\�e\�$Q\�{#N�\��\0h\�\�y�Z{Y\�^!x\�[=\�\"f����wӹU֤Gk\�jD>\�)�x�7	9�!��r]\�!��\�Q$g\'\����_/\�U\�SLʈ�[$n\�Py�2#�}%�{E};j#\�\��y�\rZ~��^�\�y\�\�\�\�[k\�d�^j\':X�\�\��F�.����\\\��\�\��\�i0\�A�0��sn3UM�\�ZZ֒��J��ȏ����\�XK=�\�e�\��{Z\���\�z)p�c#���\�݀\��.\�y5��B��Ca\�}A륅\�\�\�\�s%�N���/k�H\�\r#\'�\'\�<Op!\�\�Y������\�l\�:���l�\�\�\�\�\�\�#D��z2\�\�\�1c��\�B�� M$\�BB,�B�!4�BB�Ikbu�\�\����m.q<�\�{4*���$Q\�\�6�\�*�l94�7\�<3+�m�I5u\�sc{\�\�n@�� ���$�\Z*�7VAeڭ����f�2/ꣻYoŝ\�\�\�E�- \�ec\�69dcUZU�b���uH��tN\�\�Ͻ��jx)8����9blѼ:\'�x:��=ʃ�O)�D\�b$��X��{eɚ��kP\�h�dnQ#�4�%�9	xFO:\�s�]���9#1=�1��\"\�\����c\�ӟ\�l�s�p\�R#��\�\�k�\�w���mES\�p�\�3@���2\��Y͑�\�\�p^���\�\�y��v\�\�\�\�^��\�\'\�\\\�\0\�\��Gh4�y�w�I�\�\�}O�m~X\�\�o�\�&v<]\�\\�s��\�;7|TC�&� ~\�\�\��T���־S\��\�\"������\��resɯ�O�G�\\W\�\�W=�\�\"��˽����\�ɒf[\�S��9\n���$\�U���\�&u�佞\����	\�+!�@$�$!!\ZBs^��K0\�B?��`?;v�\0�.���O��\�trZ�]Cny�ߩV\�h\�\0ӨY�r\�Fp.��|�m+.�Q��Vx�|��}�k�aݱ�A���jh��\�\�#\�j5�F{U\�Ի�\�\�\Z\�4���\�|\�o\�\�`�keq\�\�\�2����\��\�>�|x����g]\Zs}�g6\�	�<C�#�;�Kě��\'�<9�\�\�\������ln�Q#���q\�\�w�H���8[�\0E|�s�\�\���S�L\�\�x_\�ͭ��D\�Mô�\�?,�O23Y�P@/��\'\�c[E�)&x�\��\�,��w���g\����P\����ϡiGЈI5�B	&�	MB�UΑ�\�S�\�\�=\�}�N�\�\�X\�d`p-:G�A�\�gm�`��Ź<�\�ݖF�5\��\�Kh\�a�Y7y�2qY�O����2�<B\�+w�ƫ��\�\�$\��\�%�EYcp�\�cRk�\�tU\�a�L�+����\�3\�\�̯G�V�\�4�n�푞�<�~^j��N$�t��]��������d�\��}��і7\�K�9\�\�h!I\��\�|3YZ8��v�55Y�VH\�g��\�\�i��-y�`\rn��٨>���&\�R\�R\�%���\"Fm�\�o�e\�n:%�}���;y��\0R�7\�g_\�.����!\0�!\0�!PЄ(!>Uۺfŉ\�\�\�tT\�Gv�\�#���\�\�[:Y�\��z�\��\�<\��`��Kh��\�\�K�\�\\?�Bv<�J[�\�>j�R�O�Pdl�X4\"\�E��GbXkH�ٙ\�\�\�\'qiȏE-\��\�,�̲K}7sU\"\�\�VJ\�\Z\�٫\rm��\�\'6��|{~�\�\� ���rp��;\�\�x�q\�m\�o��\�I�!��\�Ō�H\�Nr\��1�}\�r\�{��G��Zx\r>��\�Pb\�+�ap1�\����n@\�}*\�\���4!|�+3\�5\��\�3SHidv���\0�r`u�}-�R\�tT\�MdB�\r	��M$�\�=\�����|\�u�\�5c�w\�\�	\�h��h\�A)�\�\�?\�֮\nҵ�\n��<<TT2�\�\��ͷ��\�_��`��z8$0KSu1B\�f\�\�G,\�m�\��-c;c\�ƶ&x\r�9A�\�7wğ5\�\�\�>7ZV���2\�Gy�U\���[\�u�3�{�\�Xf�c\�&\�ݚ\�\�5��\�\��z.�\��\�_GO�̖9�#N\�Xx�8��V�\���ef\�۫Z�Ȯ�;<\�~��.}6ZsY�-bV\�]��x�*t\�*��)�%A�)�ݺ�+\�<Vq\��*5Xn\�gd��g��w�OO$�K�{�N |ן�a5Hb�\��^,|�������j�lV\�nZb\'4\�2[Q\�\�^\�K\�\�-V��1\�\�\��䈞O\�\Z�,\�\�\ZUUя5���T�C��\�0�:�C�Rs�@\�w�m���榄!\0�!���	&�?\�UK5+�\�bs<	;\��]U;���<Y\�s�\���lW\�k玚�g\�\rCG\�T��\0	Z^<�>eX(��՞V\���b�67Y\�7�}\�b؈�\�2���`p\0\�[9�n�7Н2^\�+��lU�\0�|�\���ֿ��8\0	\"�\0\�\�\�{\�!\�\�\�\��\\\�}?�۫W����iX\���Xǐr$8\rnJ�b,i�� _V_0y�aJ��I�yp��S��wu\�}�\�Q\�\��\�,�hV>��N�.�1��A\Z�6&���Zo�e��5\�	\�\'bx�\�<\n-e8-\�s���Ǹ��GZF��o�Z\�De~aw=�٨q7^��x\�%���\�\�\�/�.��؜@�\n��\�\��v~�\�o\���o�\�|��к	�Ş\��\����\0Y+6\�\��U2\������U\"\�I;�.\�+;/�2�^ӡ\'\��H�p6YX֑��f߭C�~\�_h5i��۱\�44{\"f\�\��\�/\�˿���%�lA�#PF`�5�&\�⦮���\��\�n�\0\�\�\�\�U\�! ��B��@�I*�Ҟ\�{v��^h�[{�s<\�O�\n\�RA�\�W[�+\�k\�ge}��\�mMPK�a`\�n>\�c\��U�|\�e��lnN����z_�e��\�ee�䨉?��FV\�T\�\�~\�Q9�\�౜�Sy\�A�&NK�%bqPL�:E���\�N��\�侏ؘ\���6�͍�>�\�\n`\�\�\�������l�\�����\�v\\<\�~�Xee\�e%ǟo7$\�O�nL\�G�/8�ڌ��\�<�v�w�Iq<K��*}�Ȭ\�v\�6�\n[��e�\�~?�A�˽tZ_��#�S��\�sC���\�[U\��\0?�\�_��\0\��ꩤ��Є hB$�)��\�E�\0h,)\r\�I�h�\� c�W]�\0h\�\��\0�q\�?��\�F�\Z\�6X�z��TNG\�X(�Yb\�Qt�2J���b.P.S�#ޠۓa�\�\����.\�ǂ\�up\�23q�\�\Zp\��.�M�RQ\�#\�cn\"\�\�Ȓx��Lu\�;}��{�8\0��[0\�lΫ\�\�L(ST9�;и\�\�y\�\�v�i伬�\�gg�	X�9���3�\�K�Ӻ�NjN\0�AL\���Z\\O�V�+�<N|���oޚF��\0\�\�\�%K\�\�}Ю\�p�#���G�`֟0/\�͖\�\\\�kd3�f#h݊�\0���\� ��\Z\0\0\0\0\0\04\0d\0\nUH&�j4��E$!\0R(B�� \�?�\0D������G��V�u*2�\n��4!JX\��GW\�\��Q�\'�r�{mx~\��P�\�\�\��\Z�\�R6�ݥ�˾�T\�!yg�\�\�JBF��Y�Ѕ \���\0/7\�ϫ\�g�C\�)U�)�\n��B�\�'),(4,'jovan.etf@gmail.com','userTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Petar','Petrovic',1,0,1,2,3,3,NULL,NULL),(18,'jovan.etf@gmail.com','adminM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Marko','Markovic',1,1,NULL,NULL,6,2,NULL,NULL),(19,'jovan.etf@gmail.com','userM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Mitar','Mitrovic',1,1,3,4,6,3,NULL,NULL),(20,'jovan.etf@gmail.com','rootM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Milan','Milanovic',1,0,NULL,NULL,NULL,1,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-09 14:20:03
