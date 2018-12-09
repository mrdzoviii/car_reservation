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
INSERT INTO `company` VALUES (3,'Telegroup LTD','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\Ü\0\0\02\0\0\07µs\0\0\0gAMA\0\0±üa\0\0\0 cHRM\0\0z&\0\0€„\0\0ú\0\0\0€\è\0\0u0\0\0\ê`\0\0:˜\0\0pœºQ<\0\0\0bKGD\0\0\0\0\0\0ùC»\0\0\0tIME\â\r\n¯\n\ën\0\0\Z\ZIDATx\Ú\íyt\Ç}\ç?\Ý=fp\ßA‚\0Oð%Š:(Q÷Ê‘e\ÇJ|mö)k¯^¼v%–%/Y)N^œC›ø½¶cË¶¬•-Ñ¶,K²d‰:iŠ¤x !\ç`€`\Î\î®\Ú?z\0\ÄtDT\"\Ï÷½y$\Ð=\ÕU¿®o\Õ\ï,(\ÌRJ9\×{s\È\á·Š¢(\Ù\îQ\ß\ëN\æ\Ãor„\Ë!‡‹ˆ\ár\È\á\"\"G¸r¸ˆ\È\Î9Q\×{Ý÷ºaò\ÈOö\Ð\Ú=„ª\Îv,I)))\Ì\ãO?¶Ê’ü÷º»9¼pa„&\ÒL.x\'\Í\rª{\Þ\Ú…\ã|\ï™ý´v¡©³7y\Ól^½ˆ\Ïrû‚-¤‘@\ÆB\Èx™ƒ)@ó€\êFq\ç¡xòÁWˆ\â-Dñ\Þ\ë.\ç\à€\"œ\Þú\É=@\Ö\èÃ…@Á·ý‹¸–\Ý4o-öŽ1š\Ä\ãra9Y\ÓTEQ¾o!fÁ8µ£\ç5\Ìþƒˆ±\Èxô‰DQ4PTP](nx‹PªQ\ËV\à^q3\î\æ‚ª½\'ý\ÏÁ\çO8)\Ñ;^À\èxeA-A\Å\ëwÞ¼¶y¬g„‰hÒ–lª¢°nYuFus!!B]¤?\Þú\æð1H\Å\ÓB`æ¿œecJq€A\Äp;²\ãU\Ð\'-\Â\åðŸ\çM8™š@·Yd[(\ÂIPŠ\ëQK›\æ³IŽv`˜—–¹\ãy>7k\Z«hPú”“:ð=’{AŒ´[œ«\\•™ÿW\Ðj6\æv·ÿ¤8\ÂEc=«NJ\Ð\ÊV ú\Ë\æ­\ÉxB§­{Ø¶\ÛBJ*J4.š¿g:AŒ\'þ\Â\èmOƒ0,y^ˆL\Ý^\Ô\êõ¥\ï9¼{œ7\á\Ì\à;ˆ\Ø\è\Ûo Õ¬·ó„\á\Ð$\'úC(6\ê¢’¥µ¥T•.¼w\Ò\ì?@ì§Ÿ\Æ\ì\Ûo\ífs‘e¦x…r\æš\Z¨@+[¶\à}\Ï\áüpþ„<Fja	\çr¡Uo˜õk)!žÔ‰%R$S†)p»4ü>/šƒ\í\Õ}z”\àX»<S	4/­$\Ï\ç\ìBK¤ˆ%u’)!$^F \ÏCß›]~\Ãm\Äv~\nó\Ô\Û\ÙUG™þ¸=(y%(\ÞKe4ud2‚LD@OY„+iD)¨9o‘\ë†`,#©|YGºaK\èÄ“:)\ÝÀ\ã\Ö\Èóºñû<¸]ÿµB½\á\É“±$¦ø}nŠòlMóÁùN˜ýÀ\Ä~²Ì¥S\ÉŠ¿­b\0IÝ ½\'\È[-½lï§§?\Äh8F,¡c\n[\Ó(*ð±lQ\×_\Ö\Ä-W¬¤¢d¶‹¼µ{˜XRGµ!œKSY·¬:\ã:2Mr¤c€=-½´t\rqjhœ±‰‰¤Ž”\ÛEY‘Ÿ5MU\Ür\Å\n¶on\Äï›½;\Ëxˆ\Äó÷Ï™ljùrÜ«v\àj¼µ¬	\ÅWdy(…Œ#\ÆN`ö½…\Þþ<Z\í&\ßúª”<þ«\Ãj\ï\Ï\è\0B²m\ÓR\î¸f5I\Ý\à\×ouò\Ä‡i\ë\"\ZOñ±\Û7ñÀ=7Ì’\Å\Ð\è$o?\Å\Þ\Ö>ŽŸa !M¦	gÉ¡0à¥¶¢õ\Ëk¸f\ÓR.Y]Gžwö\"v¤c€\Çu»b!$7m]\ÎM—/\à\Õ\Ý<û\Æqœó?|\ã:.]½ˆ\àx”o\î|‹\Èdb\Öý\Èó¸ø£n¡¶¼\ß\íå©—Žr\àøiB\á¦ø=,_\\\Î®m\æö«VÈ»pM\ëü\'\î\Õw \ÕlÊ°\Ã) R‡~ˆv\Ú\ï€´ú-¸W\ßa}\ç\\\æI\Ðò\Ë1‹–ðÊ¾.¾÷‹ý¼q¨‡‘ñ(¦)¬f%ý¯u¿D²¿\í;wµpù\Ú\Å<t\ï\ÍlYS?\ã\å\í@‰ªe\nxCa¾—UK+gü~2–\ä¯\ã‡\Ï\ä`ûi\"“I„”–¹5õ\"\Ó}xGJv9\É\ã\Ï\âŽkšù›O\Ý\È\â\ê\âI\íÿôö\çœ\É&Añøñ\\z\Þ+?‹j§&Õ£U¯Ã½úx¯ü,R3%ô\ÉXŠoÿt/o\îAË°JK)YZW\Ê\ØDœ/}\çe~ð\ËLÄ’¨Š‚’¿w\Æ\ë;=\áG\Ïd\ç\Ë-tô‰\'t¤õ\ZP˜)‰D¶ÁÓ¯µQœŸ\Çu—6q\ßÇ·±iUÝŒ>¼øV?öº­GXQ`\Ýò\ê\éþ\î|¹…GžÜ“y×‘–\Ã\ë\Ö+V\0\Ðvb˜ÿó\ØL\ÆS³<\ÒBHš•ò·n\äŸø\Z_ûñnFÆ¢3\Æ\"#ƒ<ûf;½u#\Þ{3%\æ1??\Âi<—ü¡ý\\‰…\Ð[wfmÆ³\î\Ãx·ý…\íõ\ÐD’‡¿÷*\ß}z?¡HMUP%³°§™g	óõƒ\'ø\Ü?=\Í÷¼›\å‹\ËˆD“´÷ŒØ†¤”Ô”°\ä,‚tôyð[/ñ\ì›\ÇI$uTUEU\ÔL+\ÉY}ˆ\'\rÿ\Õ!’ºÁ\×ï¿“Â´j&B]$÷~„\é¸)ž\0¾›\Â{\Å\ÏÙ†Uò«f49œ o8ŒÛ­\Í\ÚÑ¥„|¿‡\Å\Õ\Åüõ#/ð\è/\Þ\ä´lý7\ë–UO\ßûò¾NúöK¼}\ìB‚¦*I|®,¹\'Ø¹«…\Ö\î!¾ö…;¹rý\0L!hé²²}\\6BqAÞ´\Ç8š\Ð9\Þ3‚[S3>[ImE!Mõ–\Ã\ëX÷0‰”žQ­54Ô–òƒ_\à\ë?ù\r)Ã´QRºÉ£Ï¼\Í\â\êb\îû\Ä5¶\Ú\Ñ\\° \n¶\ëF„{or{Q«\Ö\Ù^ŽG¹\ï_Ÿ\æ\ßƒðd—¦\ÎP„”H@\ÓTDuÄ¥©\é\à?žÞ\Öõþ‘0}Ca[	)YV_Ni‘¥’µuñ\é/\íd\ç\ËGI&š¦\Î «EQPUeV\Å\êÛ³o\ç¹\Ý\íÓ¿\×[ŸB»m_EÁ³\åS\ïŠl™\Ð\Ù$Že¯DRV\ä\ç—o\ç±g¦C\nÊ´\ÊK4\ÖY÷g¯´ò\é¿\ßÉ¾\Ö>Eq´‘3\Ç\"Tû\Éú\ÖK„\Â1\0\Â	\Þ99b«\n	µ…\ÔW04:ÁÉ1[‡—’ÆºR*Šó‘\Ò\ÒfL‘YUUU•žþ1¾óó}\è†\éH\"ESHÿ\Õ!úG\"\çý>`g·Y†¼\Ã\n\î\äMK\ê_~ôUžüõ‘ô€g6$¤\åIü»{o\æ\Ñÿý>¸}­­¤^\Ú\Û\É\Èx€wzƒŒO\Äm_°¢ÀÚ¦*\Ü.¡\Ð$÷ÿûs¼\Õ\Òk\í\Ü!H\É\Öu‹ù\Ê\ç—ÿûÀ‡Ø¼ªn6\é€D\Ê\à¹7\Û1L‰LFÐ=cmv V¬À{\åg/\Ø;\Û\Ò5D\"ed+\nc‘8?\ÝÕ‚aš3\Æ7å©­)/`okùµ\çN\Ø\îhSš”\ÎCsi*{[ûx\ãp\0§†Ãœ£:h\Ë—SRh©q]}£Ç£Ž\äX\ÓX…\Ï\ëb2–\âXÏˆ\íT\è\Zg2–\Ì\Úo°vô\ÞÁqZ»†.\è,Hò²9pLaOg	j©½7\í\Å=|ÿ—ogþª””xø\ÏvpcÚ^R]\Âk»	…c3È¤*0Œpz8LUi>-]ƒ$u#cþ$€\Ç\íb\í²j¤„\ïül/¯¼\Ým«ºlXQ\Ã7¸‹¦t¼.©úÇŸ\ÍzqŠ}A&\â:E‘N\Ì\áV\ç\ÝM‚»ùNÔ’%™¯\ÃÊ¥´ƒ\êE\Å0-]ƒig\Ä\ì*\nLÄ’il¶CaMcºaò¾\ÂÉ1[O’Í«\ëøo;632\å«O\ìv\\\Ô\âI\ÇNókši?d<ƒC\ãL\Ö6UO?»¥{ˆDÒ°µ÷\Ü.µi5x ¡wp\Ì1[h\ÊN­(	2M\á¤-&S}C\ã\\\æp\ÒH`\ÍzŸVµvÚ›v6&\ã)¾ý³}D¢ÉŒ/YÉŽm«§\É–£\Ã\çqq\î\ÔR…D\Ê`\"\í\æm\é²]É¤””ä±ª¡‚žþ?zþRÊŒ“A\ÓT\î¹ó²i²”æ¡ª*¦9“\n\n\Ñ$Ñ„NÁp«•€\ìD8\×\Ò\íd¼\ÉH’x\éo1[2·¡¹ñm\0­n3\ãq\Þ\é\r’\í˜\r%=v!$.—6­.nXQÃ®ýÝ¼j³\è€5ak\Ëù\×?\ÛÁ\æÕ‹H\é&¿9r’öt\à\Ò\ìŸLk-ƒ\èºiÛ¾\Ïãš¶#M!h\éDH™\Ñ~–RR”\ïcUC\0}£„\ÂöÄ·œ&e|\éoa\Ó\ÊZ^?\Ø\Ã}?C\Ä!\åOJk~^\æŸp“CˆP§ó¤R´š\rdº\éP{?{[{mw!MS©*\Íg_\Û)‹ÀñžKPØ¯”c‘}AGû­¾ªˆº\Ê\"{ö \'\ÇmJw À\ïÁ\çq³¯µoš\ä{[O\Í\"Û™€‚D„º²\ïüþR4›T6&u\äÿ!‚=³E\'A)(ƒ o(LÿH„l\æ–’\Ê\Ò|n¿j%Wnh À\ïe4cËšzþþ»»ˆ%tû\ÝMH®¿lWÖ¥\å^wö)\åri¤t“\Ö\îAÛ¨”’\Ò\Â<–¥ \á‰\í\ï\Â\Þk\í\Z$‘2l\íM[\ã¾O\\ÃŽm«¸íª•|\í\'»9pü4š’y¼Š~\ï…U­\Ì;\áD¨91\äH8Å›Z¹&\ãµ7õžL:\Z\æ_ýñn¾ñ\äž\éŸ\rSO\ê³V&)Á\ë\Ö(ô{990\ÎÀ\è„}À[Âª†J<.]owa\Ú\äZ*\nD\ã)\î{ø™\ä\nú\Îj\äy\Èóhˆ‰9\èÿž_¡l»\ÑÈ”&)@-[†Zd…A\Ú{†	;¨k`fËšzº÷f¶®_2C\æ\'\Æ\Ù\×\Ú\çh/¹]\Z\Û65L/–\ÐpT\Ë\Å\"\ÆX$Nç©ý($‹«‹©)·dqj8Ì©,öÞŠ´½g˜‚£]C¶\ê´’e\reÜ²uùŒ\ïgƒKÓ¨¼À¤y\'œ9Ô‚LÅœ]\Þ5¨%Kg]\Ò\r“C\íý¶‚šB,¡\Ï\Û)™\ï–XjFEI€—÷w9W¨\n\ë—W3>™ ½g\Äq¢	)™Œ\ÍT-œB\rU¥|n„9uD\n¤43Ž\ÇlA&\íe«U6[Y(À\Ñ\ÎAt[W·5\é\Ö4Uñ\Õ/\ÜIó9qG€Ž\Þ†F\'mm )—}óYIÞ–½q”\×\í¢yi%\'\ÇrZ\0UK+)ð[Ž£ö“#Ž\È\Ùö^p<J‡ƒ:-¤d\ãŠZ*\Î*.‹\Ä	Ž\ÇdI¾\ß3½ƒž/\æ—pR`\'¾H\Ð\ÊW \Êg]šŒ¥85v\\!…\Ã\0™`š‚ÚŠBŠò8òŽs…€\ß\ëfmS5Ã¡IF\Ã1\Ç>˜BdõjMÁ0\Ë•\áõ¸ˆ{²¯Ž2:‚ïƒ‚\Ús.H\ÌÁ\Ã\ÖVœ©o\n–š®¨$R­\Ýö»©|^÷}üšŒd\è#‘\Ò5‚ª\Ò|\ê*\Î\ìÆ­\Ý\Ãi\Ù\ÙOôª\Ò|\Ö.«f×¾.¢q{\'…¦Z%RSmƒ½7\å0\é\Ó?b¿ªŠ\Â\ê\Æ\Ê‹IG_\àX\ÔÁä€ºÊ¢™I\çy%œLN\"†Ú²Þ§UgNHŽ\'u&cö¶˜š+YRS2§	/„\àºË¬\ÐCk÷c…@yI€¦Eet÷‡H¦\ÛI£©*[\Ö\ÔSR˜7G\ÒIn\ØbõA-n\È“:ø}òj6‚\ëLN¦Ô£ˆ¡Vû¯zò\Ð\ÒqÍ‘±(Ý§\Ô5Áš\Æ\Ú\ét©L…\ã)\Ñ\ÈSYšOAÀ;ýókºI\ê†ã®ºu\ÝUq¸\Ã~”€\ß\çauƒµ$SG;\æn\ï?9Lx2iÿ5•š²Â³¾»öwK¦l}2\í-+òs!˜WÂ‰\Èi\Ìñ\çI\å\Ò\Ò“\ÙP²\Úºaþñklw9EQP)%¦hšJ\ï\à8=ýcY+*JtŸ9LSù‹O^\Ë\r[–\Ù\êýS}\ÒÚ§&•V·Å›LM\Ú?@‘¤ö\×\nlüjQ(*b¸\r3\Ôe§;£\äWM\×\röô‡MÚW\ÂÆ•µ;¤)\Í5™bj‚?1Ì‹ou8¨ Pðòû·l@7L\Ú@¤¥\â»\\–±º\çh/\ÚO\Û\Úõ\ç\Ú{\Ù\Õi1\í)8pü4?¥\ÍQ½õû\Ü\Üv\å\Ê.Jž_\ÂÛ‘N%;”¼\ÔtBò¹\äy\Ó;‡M\ìxö\Ívn½r%k\Z«f	\Ô4\áh‚wN9=aÇ¶UhªB÷©QF¦S;§\×í¢¬\ØOÀ\ïI;afßŸH\éüø\ÅÃ¬iª¢®¢p\Ö=)\Ýd4\åh\Ç ª¦p\Ãeg‚ûZ\íF´E—at\îrL9Fœäž¯“:üŠ¯™œ@\ÆB¶„SË–¡\ä[;B[÷pZ]³_\á-uÍ¾5å…¶«=X*ß±\Ã<ù\ÒQªJóù\Ê»\éw\ÜUo¿j\×^Ò˜Ž‘\Û.SŽ©‡¾õkš«Øµ¿kVŒõ\Ü÷7e\ï%Rm\Ý\Î\Î)	|\ç\çû¬!!yì¹ƒô9ô\Ý\\ºa	Wol\àB1¯„3€®;»½‹£-\Êx9\ß\ïa\Ó\Ê:öÍœ¦ª\nŽ\æ\î/<\ÆeÍ‹XRSB¾ß‹n˜D&\'\è£«o”;®]Í\Û-Ohk÷ñ„n»:MU\0\ÔW³¼¾œ\á\ÑI´±$EQxò¥Žv²ieµ…ø<.)ƒ±HŒS\ÃNô‡\è\çÁ{ožA8\Å[ˆ÷Š\Ï`žÚ‡LNfW/ad<<\ãwvÐªÖ¢¸óf¤4¹l´óýš—:W´¯_^MY‘Ÿ\àx\æR&EQ‹ò\Ùú9ªªK¤l\'¬i\nš«øü\'¯\Å\çu\Ç,V–Ú®\×õð\ÚÁY\Ó\ÉÎ¶÷FÆ¢t;x?Á²\á:ú‚\Üÿ\ï\Ï™\ÞQ3Ár–xùŸ\Ú\ê¨\ÌóG8aX&Y U®¶\ÊKlñ\á\Öò\ÔKG	†3\ïHŠb¹ˆ{Ç§¶²\Ó\Ï\\…õ\Ëk\Ð\Ò+\ØQÇ€iºB m/¼|\ä¦õ\ìm\í\Ã4e\Æ]@JI[÷0-]C\çT+œ\éC\ß\ËÚ¦\êY\ßu¯Ú÷ªÏ‘xõ\Ë`\ê\Ù\ë	\ç¢Áhªu¬dMi’i\ÇÅ’\Ú\Ç&W6Tpó\Öü\à\Ù¶AlE±vt‰\éH¶\ÆEe|ùs·³:\í Q\çXÕ®©J:\î\é%ž40\Å\ì8§üygý!†\Ç\ì\Õ\é)œé¯’U}þ\Ä\í—pÛ•+\çð\"²c\Þr)e,„¶g]µ-Ošýy[\Ö\Ôó\'½š<\Û>ñ4\ë\ÒT4\Õ\ÊŸúYUUgI‰NLmú-%5\å,©>3?r\Óz>r\ãzÀ\Þ#:•\á®eèƒ‚BUi\r™&µ\æÁ{\íñmÿ¢¥.:di\ÍMð xò\Ñ*­\0n¶”&!­‹òbg\ã\ß\ëvq\ßÇ·qió\"\Ó\Ù+›\éIBX\É\åWml\à[õ!®»ôL0¿¶¢ˆšòB„t¼i\nê«‹¹\ãšfû÷\'$•%ù,­³d=U\Ëg7\r5UaY}yV[LJ‰”’»®_\Ëý¸¯g~ö¦y#œ;;iM »\æšö¤\ÙA\ÓT\îý½­ü\Ãÿº•e‹ÊRb˜\Âz\Ò\Úþ%ge§Ò’LS`˜\Ó\ÔTN§]õ\r…\é\éC«s?)Ã¤©®ŒÒ¢3\êBaÀ\Ç?|\æ6þ\ä£WSQÀV»™û`‘rF„deCÅ™ÏˆT<|\×ÿ5þ\ß\×\Ê[­s%§ddW”+\Ïúœu¯\â	 \Õ_ŽZ\Ò@û\É Ã¡(¦\Íx§Ô»¹d„¬XRÁ7þò.\îºn-y>WzlÂªÔ\ç¼)1…õUUX\ÕPÁ\ßüøþƒw³u\Ý\â\íV•\åó\ß\ï\ØLž×&³œ\ÑÖ”¬\×-¯\æ\ß\îû\0eE~¢ñT\Æñ\è†\É\Ò\Ú*J¦*mj)-\ï\ç÷\\\Ïu››ÎšW3\ç’a\nŠòóø\Ì\ÝWñ/º#c!óùb\ÞTJi$p5lC:¬Zj^	jÕš¬my\Ý.þ\è\Î-l\ß\Ü\Äs»\Ûy\í@7]§F‹\ÄI\ê\ÆtYŒ¦©x\ÝÖ±\åE\×Ó¼´’+\Ö/¡6J¤._[a“v%„\äö«W\áv\Í\ÜuËŠýü\í§o\â\Î\íkx\æõ\ã¼\Õ\ÒK\ï\à8\Ñ$)ÃœVK\\.Ÿ\ÇM¾\ßKei€†\ÚR\Ö5U³ý\ÒF\çUQu\á^y;®†m˜}{\Ñ;_\Ä<½1~3™®™S­\æ±}õ¡T£–4¢U5£Uo°\Ôô€µÀ˜BpÝ¥Ž\Þ\Þ\í›\çü^›—Vò\Í\î\âõƒ\'xnw;‡\ß`pt‚h<…nX…À.—J~ž‡Ê²š—V²m\ÓR¶mZJ}•½\épÏ[(øx\ìùƒtöM\ç(ú}n–Ô”pó\Ö\å\Ü}ó\ZjJ\Ø\ì·\\¹2£g\n\É\ï\\½\nŸ\ÇE$š\äø‰aGuº¢$À\Õ\ZØ²¦ž¯<±›\ß\ê 86‰aZGdT–\äsù\Úz~\ï¦õ\\µ¾\×<1gg\Ö?\È(L+“\Ýñi\Êy•œ$uƒ±pœ\ÑHŒX<ER7ñ¸4\Ü\é³3\nü^\n^y\îYžµ©U\×	.M\ËZ\ãM¤…cŒE\âÄ“:†!ð¸5<nþ¼tü^ü>wÖ„a[\ë1d|\ÌòF\êQ¤‘´NW\Ö\\\à\ÊCñ¬LoŠ+óY#S»\í+ÀZ$Î§ˆR‘\É£\ã1\ÂQ\ëh	EQðy]|”ù)x\ß\Õø\'cIúG\"ŒO$RR\\à£¦¼p\ÆY*ºa:&;L™\í\'G\Øñ¹\ï¦\Ó\Ëf÷Á0·]µ’}\éðº]!`ht‚d\ÊH/šùT\Î\Ëý?—?\È8NU[°³½n\Õ\åT—¼\ë\ïjª\ê\èÞž+>ß…§ö8AqûQ\Ü~(¬;\ï6¦\ì\È\éP”Ÿý`¡wƒ|¿—K*\ï9Wû°CGoPÄ¡\Þ«\ìhJVU…ºŠ\Â\Ù2ÿZG*åƒZ\nn\Ünm:üó^!G¸\Þ°*m³¤”\çûX™e7]h\ä—\Ãûc‘x–\n+ùxQeÑ»ly~ñ[ù÷\árxÿ¡wpœ\ÓÃ‘\é\nö™PB°bqE\ï\Í_DšBŽp9¼/\Ð3`û§:BH.Y]7/´Áü…r\È\á=\Äph2]Ki?¥W_py\æ\È.‡\æ	s!\\\Îi’C9\Â\å\ÃEDŽp9\äp‘#\\9\\D\ä—C9\Â\å\ÃEDŽp9\äp‘#\\9\\Dü\ßH\Ï}B7O\0\0\0%tEXtdate:create\02018-11-20T13:18:10-05:00z2S+\0\0\0%tEXtdate:modify\02018-11-20T13:18:10-05:00o\ë—\0\0\0\0IEND®B`‚',0),(6,'M:TEL','‰PNG\r\n\Z\n\0\0\0\rIHDR\0\0\0\Ü\0\0\02\0\0\07µs\0\0\0gAMA\0\0±üa\0\0\0 cHRM\0\0z&\0\0€„\0\0ú\0\0\0€\è\0\0u0\0\0\ê`\0\0:˜\0\0pœºQ<\0\0\0bKGD\0\0\0\0\0\0ùC»\0\0\0tIME\â\r6²Sõk\0\0\ÇIDATx\Ú\í{ŒU\Ç?¿™û\Ømwu/…²[Ý¥M–G´š†Fj	øDJ	5m\"H0\ÑE‘DŒ!\r5C¢Ö‚¤c–B1\áU4–bª´KYJ[¶	twk»\ÛÝ½™ùù\ÇÌ½wf\î\Ý\ín\é\ìM\ïžO23¿9\çwON\æ»\ç\Ì\ïœ3ƒÁ`0ƒÁ`0ƒÁ`0³itfC—	¢9 ôý(\ç\ÖWC9	úsý}^£\Ûf¶`7vöXˆ~P`°X´Q›\0\Z+u¶m<\àpxZa+\È^#¼\ä1‚K˜Á®žy¢z\"\ß\0]4õ’aÑ”\Ógbó9$¢òK\àW¹£}\'\ÝfÍŒ\\‚uvw r?\ÊF„ôZ˜B‡t\æLÃ­l¾“;\Ú7Ðˆöš\rÁ%\Ä`Ww«ÀO€¯yO7ùä½•¢A\îª]+¦f›\æ\0T\ßò­Žþ¾±F·a32\Ó/\ì³\å‹ ›@,ÿ‘P)\ßÎ½’ŽÚ¤œV(û˜®M4H+S±	\ÊFUnntó5+Fp	0\ÔÕ½\á 5r#>ž¨7¾(\Û4ÐV¯#\é©\ØB>©ø\Ó\ÉmY\àö¡®\îi¼o\Z¦Š\\\"\È:Õ§?¦\n	Ûƒ#nñ\í~\'¤A\ç5\r[\àWQ´’gJ¶eª\\\ß\èVlFŒ\à’@¹•–\ÊðP\ë\ÃRk\Ój>©¤§oS­\çû´G\äºF7c3’jtšai8”\á+¡|)¡¡d(h\"\ÉMj«6\Õ@Š,mt36#FpI Ì‹\Z\â‘I‰\Ù\ëÙ‚tD\rR=M\ÑVÖº/\Þ@–\åŽVC6jòµ5º›3¤L¡\Ò7Aü\rN‚´F\ÔØ¢…\Ãþ\Â1|4\Z%‰\ç\Ó\Èo(\Ø\ÙM\É|\á¦JT&ê¡¢\\3e”\0Fp	!\å\è¾T\ÓQ›T\Ò\ÙN\ëÃ²ŽùH\Û\\ª&=#Búc«I]µ*(#HM>1rK#¸P­hôZƒ„–/”\î«¶\ÇË…®³Y\æ\Üw/\Ù\Ïu¼Š&ñ© U_ZS\ÏF·bsb\Þ\á@b½C\äÒ¶Á¶‘|2¤½\rŠ%tdU)\Û\\¡\Ü{\×AZ[±u\á:Œ´f}s\ÉA=±md^;X:r\n-±j*$A g¢ ‹\á\ìb—ªÕ d%þèº¤¯\\Eö–/S\ÚõÒ«?Œ\Õy1\0\Å\'þ‚7<Bf\í\Z¬®Nh\ÉRü\ëv\n[E‹Eß©\çUœÙ—^Âœ{¾‡Õ³˜\ÌüóI­\\Nq\ÇNò¿Ý‚\Ý\ÓM\Ë\æ\ØÝ‹AohˆÂ–\Ç(½üO¿NZ§ž\å\ë\àlÄ—FpI ¥io#³~\Ø6¥gž\Å}»Ÿ\Ìúu´þ\ànJO>Eñ©§ñ“^»†\Ö;o\Ç\í}\Òó/!){ù2¤½\rg÷ô\'(nß½t	Î¾ý”þ¶÷>¬˜û\ã\â8Áø\Ï~Ž\æódoúsï¿Sw|wo¤ª\áÉ‡°\ÍH.Œ\à’ sˆ>ºŠŽŒÿÅ¯qþû\Zˆ ƒCd>û)JÏ¿Hñ\É²ñŽ¼Mö“7º\ârJÏ½€\\´¹>€\Ýy1#›n£ô\Â.Š\Ûw½u\î¾^\nÛž\0Ë¢\å\Ö\rX^À\è·\ï\Â=ø x‡“º\æj27|‚ñý½±\ÊIº\Z±%…\\„õ&•Uu]ÿ}Nu(– öm–\rŽƒ\n¾\r`t÷\ß{\Ñcx\ÇÁ²ü|‚Ÿ¶S`[\Ø+–A*M\æ\Ó7‚\ëR=JKÖ¢E~^\×\Õôh3…\\L4	\Ï\ÃI¨g‘p^\"1’ŠCfôû÷B*££ˆe\ÕÎ‘[–?E\à”ü ‹Wa+<ö8\îoÖ„üM\Ðdf1‚KˆzA“\àN\è\ßØŽ€ˆƒ\Øu¶…\ì\Í_\ÂZpùG~‡70Z!øt]ô\Ä0\Þ\Éaò¿ßŠž\Z«þ°\çUz:4if.	\âA“z[f\ê¯+©/>O±r9Z6•–¯m&õ¥\àzÕ™´?|,98»_Á¾ôRW]	\êQž%O­\\}Eh‰§ª\ï£N=$©\í\ç\Ó\Ã%Â¤Añ‡\áð„„r…—T–\í–\à;\ÆøOD::pöþIYP,¢ƒƒ¤?ºšôµk`|œÒ‹»pö¼Êœ{\î\"\Þ<¼w\Þ\Å^¹œ–[¾B\á\Û\È<ˆŠ\Ø=‹I]½\nw_/\n•q°	š$‹\Ý\è\n4#\ßmß@\âAEæŸuÑ…”þþ:2€d3Ø—õ\à\ìÞƒw\èX){\É\Ü}ûq_?\0ª¸ûö\ã\ì~-ýä®ƒ‘ú\Ð\n2_‹,X@\é™gq^z\É\å\È\Üx=\ékýy½\â¶?SxüO\èx,›ôG®ÁZ¸\ç_»\Ñ\Ñ1\êˆ\ì\Ä#\Çjt[6\æOYuõô—×½iY~\ä±X¬†3E “\Ç	\"‹ÙŒ?\ìsœ	~IÁS˜;imEóóWšX–¿\Ò$FG\ÇÐ±±\ÐZMioCK%Ï—=\Å†·:úû7º-›\r3¤LˆºA\ÅTYT\åp¦§\ÏGm\nš/T†x\á\Ýr$4ž\ê\Ø8::^•Z–?õpr8´2Lüü\nªž/ð=A\ÐÄ¼\È%€\\L°\Ò\ÄMu+ŽDi©k;­ÿ _H§•\Ý5Bu“y\èª÷\"¶š|£n\Æf\Ä.	&˜‡‹R¤N1©S®,Ó¨\\§bÓš\âd_-Š\×D4º›#¸PÎµ±óôlgPº\ìœ\É6›-Á%€\È9‹z\r\Ø\Þ\èJ4#Fp	¡±%q	*þ÷F\"A©ÚªŸÔ“\ÚÝ \ï\ÅvzŠ \çŽönt6#FpIúˆOtZ¹\Z¬\ï\Ä\Ö\ØŸhù;%:m\Û~š\äQ\Ð?4ºÙš#¸„¨UÖ¬7©Wª¾¯º\å§f›.°\r¸»£ÿ\ÍS3\ÛZ³#¸\Ðs\ïƒ \ïƒ<”;\Úw¼Ñ•ifŒ\à\àš(0Au§\"[}%\×\ß\ç¾WÇ†\É1‚K†ó]‰	P`ÿ?=€ðŽšÁ`0ƒÁ`0ƒÁ`0\ÃY\çÿ\Í\Îþ~QD-\0\0\0%tEXtdate:create\02018-11-20T13:16:54-05:00þIq\0\0\0%tEXtdate:modify\02018-11-20T13:16:54-05:00e£ñ\Í\0\0\0\0IEND®B`‚',0);
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
INSERT INTO `manufacturer` VALUES (1,'Audi'),(2,'BMW'),(12,'Ford'),(3,'Mercedes-Benz'),(7,'Peugeot'),(10,'Porsche'),(6,'Renault'),(8,'Å koda'),(9,'Toyota'),(4,'Volkswagen'),(5,'Volvo');
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
INSERT INTO `user` VALUES (2,'jovan.etf@gmail.com','adminTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Jovan','Jovanovic',1,0,NULL,NULL,3,2,NULL,'ÿ\Øÿ\à\0JFIF\0\0\0\0\0\0ÿ\Û\0„\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z.&%-----+-/--++------+----7-2----------+--------+---+ÿÀ\0\0\á\0\á\"\0ÿ\Ä\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿ\Ä\0F\0		\0\0\0\0\0!1AQ\"aq‘2r¡±#RbÁðBs¢3CDS‚ƒ²\Â\Ñ$4t’³\áñÿ\Ä\0\Z\0\0\0\0\0\0\0\0\0\0ÿ\Ä\0\'\0\0\0\0\0\0\0\0!1A\"Q‘34aðÿ\Ú\0\0\0?\0\ë¨M\n©!4YBhP$&‹ HM@¬±\Ï3X\Ò÷¹­hÔ¸€™^n\Òm41u’›¸\ßu€öž\áÀr\Ê\ã;A´\ÕÏ¼Ž-Žý˜\ÚN\ã\Ô÷•\çžÉ‹\ÛV›²ºf%\Ò5Dµ®|¤}\Æzº\×Z”ý)Q“g²ve\ß n¹¥5ögv\r•\×7õ5\Ûývü\'§ªnõ<Ìr8x´\æúù\ê1-3Ä°\È\æ=º’<»\Â\ë;¶L¬)lÊ‹i|ŸarG#Ü½õ\î™97z{­lBvBös¢…$ Š,¤„²’²dY;!P‘d		\Ù&„Ð!4 HM\n„Ð$y–¾ \ëE!\å¿\ÂPp}¼\Å\ÝSX÷w-`\äÀl—DË°\â®Þ˜Ÿ\×5µ…›.\Õõ½/\"\çÐ‡×§>ò\Ë\Åj`­$\r\Üü\Âõ¤\Îi6\ÏÁsÏw®y~_*–/Jxk\ÍV©\ê­‘„µ\Ípp7\â³§xnb\ÞJTÓ½n+z¯ºn\å\Ã\ÝôVV&‚9Æ’F\×ÿ\0\Ü/e´«ûIÃ ¿ó*Â¾œ|kòHM„’’E\nI HMš	%+$ƒ\"B„Ð HMB¼­¤¬Ž*i:\ÉX\Â\è\Þ¼ð\Ý\çnœ…õ+\Õ\\Ã¥¬<\ÉQNð\ç\r\Ö\0,r½\×6\ç¢\Æ\Ìüq\ë\ÓN¿\Ô\Îb\å­võ\ÍÁ³§‚Ù¥ŒK\Ü#W†¸Ÿ„›‘à±½€=\Ä}ósù¯vdÀ<€lFF\ë<\ã\è\á…\ç³Mï‰¶’–¢l­}Ö¼5¤\è7ÇžªÉƒ\ã’uNG<†\ïqy·y#\â³AD\Ø\ÚCn&“k\\\ç™\ÍkQ[}\Í\r\Â2ÿ\0\êò\Ë9ôõ\Ã^_jþ-^\é\È|“<0ðk®\0½»@{§\Æ\Ë\Ïm<o•‚žB\âHÌœ¬r½e\Â÷,ö˜\Ú\ã˜u\Å\ï™\Ô¬´$\ÃC^%º2µÇ’ô\Çf3\Ùçžœ¯kªôy‰\Ä\êaN$gZ\Ç<n‚\íÖ¬8góV\Û.[\Ñ>õUD\àvDlhø\Þ\ìýC\êk»\Ù\×\ÌÙŽ\\BkLI\0„!²,š$&…BB‚hM\nBB$Ð*\î\ÝÒµÔŽ”¶\æ?-wFNùù+„±‡4µ\Â\í ‚9ƒ¨Y\Ë”²µ†^9LŸ8\ãr7x–·tos\"5¸òVŸ\Æ#Š!½m9q^_H8;)*Ÿnqge\ãz\×\àör\ÖÖ²ñ\è\ãÞ²~Ñ§K®<õ|>Ž½\Ý\ê\ãŠb¯1—µ\Í\Z\ÆûÛ¾<ìµ¶{§2\âZ\í\Ý?5\äa8\rdÀ˜\â\ë,lCd™Û“b&|wš’pöû»Ži¹<˜Xý(\ß\ëWYŠ±µNdO9¸\\\0\ãr½	qv†–¸ƒÞ¼,[g*)¾\êYX\Þn#^þ+\Ï09\\®.ó\ÏK«t\ãI\ê2ŸN\Ã\Ð\ã	‚¢^˜ý–\æ‹\äº\Z¨ô[G\Õá“¬›\Òy8\åò[—v3’G\Ë\Î÷+Bš\Ó$„Ð!4 HM#d&„		¡Q$Y4(!!I\0’kG\ZÅ¡¤õ3¿v&Z\æ\×$“`\Ð¤ž8\ïMµp<\áŒúF ¨\Üxw‘]—k6\\bö†8\Ç8obþë™¨cùxŽ|WÆ°I\éY<OŒò:ðFDx,\ì\Õe÷z\ë\Û>b\Í-L‘¸T@\ç4XoX\èyŸef¥Û°Ø¬e~ÿ\0x¾c¾\ëŸl\î-¸\à\×\ék\n\ÙX)^\Æ\í\ì/¥¾‹ŽË\ã¾g3Ç¯h6ŠZ¹,\é	e\îEò²Ð¤¤}u\\t\Ñ\æ\\\à;š\Ð.\çx…«`»c\0\å\Ñ:¥a†y÷G[\Öoq\Ý\Ý r\Ìý®¬{\ï^ösñŽELØ£dLc\ÖøZ\0E ˜].#B€Bh@š$&‚$š$&…D„(!!$\ãl\Î@}#izQ¡¤%Œsªe±[t\Äòm\ét:‰\ÛK\Þæ±.q\0\08’WÏ&m©\Äe\ê¡$Q\Æ{#N±\âÿ\0h\ï\ÈyñZ{Y\Ò^!x\Þ[=\î\"f‡–ûµwÓ¹UÖ¤Gk\èjD>\Å)ûxš7	9¾!—«r]\Ã!©Œ\ÅQ$g\'\Øóð_/\áU\ÒSLÊˆœ[$n\ÃPy‚2#½}%²{E};j#\È\èöy’\rZ~ ò^½\ìy\Ù\Ë\Ø\å[k\Ñd°^j\':Xµ\ê\ÈûFü.ý¡óñ\\\éó\Ê\ÞÁ\Þi0\àA‘0¾´sn3UMª\ØZZÖ’ö†J´È‹ƒ‡Š\ÆXK=›\Ãe—\Ýó{Z\ç›•\Úz)p£c#œ–š\ÇÝ€\äƒ.\Ðy5§øBñöCa\â}Aë¥…\ìŒ\ä\Ö\És%ŽN¶»™/k¥H\Ý\r#\'\'\Å<Op!\â\ÞY©§”¶·º\Ìl\Æ:‚’¯l¦\Õ\Ó\×\Â\Ç\Ç#D¥£z2\à\×\Û1c¨½\ìB°¬ M$\ÐBB,€B!4BB Ikbuñ\Ó\Âú‰œm.q<‡\ç{4*™µ½$Q\Ð\Þ6»\Ú*ól94þ7\è<3+m—I5u\Ïsc{\é\én@ ¹¼Ž$ó\Z*ˆ7VAeÚ­¹¬¯»f2/ê£»YoÅ\Ý\æª\êEª- \Ñec\Ö69dcUZU‹bö™øuH™·tN\ì\ÈÏ½õˆjx)8úºŽ²9blÑ¼:\'·x:ùž=Êƒ·O)±D\ïb$µòX‚ó{eÉšøøkP\è¿h÷dnQ#…4%·9	xFO:\Úsñ]¦²†9#1=1¹¶\"\Â\Ä¬±™c\ÅÓŸ\él™s¼p\ÌR#§…\Ä\Ûk\Ìwš¶mES\êp©\Ù3@™°‰2\ÑÀYÍ‘½\Æ\Úp^”¥„\Ó\ßyòv\Ü\î‡\î\Û^û«\æ\'\Â\\\×\0\ç\Ç‘Gh4‹y‹w®I³\Æ\å}Oým~X\á²\ßoœ\Ù&v<]\Å\\ös¤š\ê;7|TC÷&¹ ~\Þ\ã\æ¨üT·Ž‹Ö¾S\é—\é\"†´ˆ÷ú‰\ÏóresÉ¯÷O†G¹\\W\Ç\íW=”\é\"¶„Ë½¢û¸’\ÑÉ’f[\á˜SŠú9\n³½$\ÐU–³¬\ê&u†ä½ž\Ñýý	\ä®+!¡@$š$!!\ZBs^ž«K0\ÖB?ž¨`?;võ\0ù.”¹§O”¥\ØtrZý]Cny‚ß©V\Öh\Ö\0Ó¨Y¢r\ÔFp.“™|Šm+.¡Q¢öVx|”†}—kÁaÝ±±A²…žjh©±\Ö\Ó#\Ìj5ôF{U\íÔ»’\Ô\Â\Z\Ù4»…¬\Ù|\ìo\Þ\Ï`¯keq\ç\Ð\Õ2¥™“\Û÷\â>ó|xŽð—‰g]\Zs}¦g6\Ä	œ<C³#¿;¯KÄ›¦§\'³<9¹\å\Ö\Ç¿«ð¯ýln—Q#®Šóq\É\ØwšH‘™‚8[ÿ\0E|¬sñ\ß\Ùû¿S³L\Ï\Óx_\ÙÍ­˜ðD\ÈMÃ´™\ê?,‹O23YˆP@/¢ú\'\Úc[E»)&x\Äþ\Ó,õ¼w¯•ï¡œg\Ùñ™P\Ã÷ƒ´Ï¡iGÐˆI5…B	&„	MB€UÎ‘°\ÃS…\Ô\Â=\î¯}¿Nð\Û\ÍX\Ôd`p-:G‘Añ¸“\ÉgmŽ`§‰Å¹<±\ÛÝ–Fù5\î’\ÃKh\ÛaôY7yŠ2qY·O†ªŒ2ž<B\É+w…Æ«£\Ô\é$\Éˆ\ß%“EYcp²\ÐcRk\à¡tU\ÏañL+Ž€¹ž\í3\Ô\ßÌ¯G†Vþ\è¨4•ní‘žó<¸~^jõŽN$£tŒ÷]÷“­’ùûõód³\í÷}¨òÑ–7\æKü9\Û\Æh!I\Þò\Ð|3YZ8©„v‹55Y†VH\Ãg±Á\Ã\âi¸ú-yŽ`\rnŽÙ¨>±À±&\ÕR\ÃR\Ý%‰¯·\"Fmñ\áo®e\Ðn:%¥}ö;y ÿ\0Rþ7\ãg_\Ô.š°¦„!\0„!\0„!PÐ„(!>UÛºfÅ‰\Õ\Æ\ßtT\ÈGvñ\ß#ø—ˆ\Ö\Ý[:Y¥\êñzŸ\Æö\È<\Æþ`ª¬Kh­\Ï\ÑK­\æ\\?²Bv<þJ[®\î>jŒR®OšPdl¤X4\"\ßE³ˆGbXkH³Ù™\ê\ä\Ó\'qiÈE-\âó¨\ê,¢Ì²K}7sU\"\ë\ÖVJ\Ô\Z\áÙ«\rmðù\á\'6¹„|{~†\ê²\â¶ ”‹€rp±ð;\ê\Æxùq\í§m\×oû–\ÕIÁ!ªš\ÛÅŒòH\ÈNr\Æò1÷}\Ör\Ò{–¤G´·Zx\r>ñ\ÖPb\Í+‹ap1¼\Úöµn@\Ù}*\×\à‹‚4!|ƒ+3\Ï5\Þú\Ú3SHidvô´ö\0“r`u÷}-ôR\ÅtT\ÒMdB„\r	¤ M$§\Ú=\Êø¥·ò°|\ãu¿\Ì5c—w\é\ë	\ëh£ªh\íA)¿\î\å°?\ÄÖ®\nÒµ°\nô°<<TT2œ\Ê\Ø÷Í·˜\Ù_Šó`¶¥z8$0KSu1B\çf\æ\äG,\ìmŸ\Êþ-c;c\ÝÆ¶&x\rš9A\Ç7wÄŸ5\ã\Ó\Ñ>7ZV©ý‡2\ÏGyýU\ßÁŸ[\ìuµ3ˆ{˜\àXfµc\Ä&\ÝÝš\ß\ï5¶¿\Ä\Óùz.·\åñ\Ù_GO‡Ì–9þ#N\èžXx8¤¸V¡\ÃúÀef\íÛ«Z«È®½;<\ã‹~«¯.}6ZsY¸-bV\Ã]’öxµ*t\Å*“š)²%A˜)³ÝºÁ+\ì<Vq\îù*5Xn\åŠgd½ŒgªªwOO$£K€{ûN |×Ÿˆa5Hbž\Äñ«^,|¹Žð¥ö„÷øjÀlV\ìnZb\'4\æ2[Q\Ú\Ú^\êK\Õ\ç-Vž1\Æ\Ð\â‘äˆžO\ä\Zò,\ã\Ü\ZUUÑ5´¯T•C¢ü\Û0ö:óCöRs»@\Ýw›mŸŠ·æ¦„!\0„!„	&’?\ÃUK5+ý\Ùbs<	;\Äò]U;¢‘ñ<Y\ìsš\áøšlW\ØkçŽš°g\Ä\rCG\ÙTÿ\0	Z^<ò>eX(‘ÕžV\ÛõÁb¤67Y\Ú7®}\ÑbØˆ¨\å2Šº™`p\0\Æ[9Œnñ7Ð2^\Ë+÷‰lU©\0‘|·\íÀºÖ¿¢ 8\0	\"ÿ\0\ê±\Ä\Ç{\×!\×\Ê\Æ\Öô\\\Û}?ŸÛ«W©ºþ—iX\íòòXÇr$8\rnJ©b,i´¬ _V_0yŽaJª¢I”yp¦‹Sªýwu\é¸}¦\ïQ\ç\íÀ\×,½hV>£’Nˆ.‡1‘¼A\Z‘6&À¥¸ZoÁeñ5\Þ	\Ô\'bx¬\í<\n-e8-\ísðú€Ç¸º’GZF““o—Z\ÞDe~aw=¦Ù¨q7^¿»x\ä°%¤Œ³\â\Ó\Ä/™.¾’Øœ@»\n¥‘\æ\îöv~º\Éo\åø³o\ì|ýÐº	ŸÅž\Ç‘\Þ›…ÿ\0Y+6\ß\Î‰U2\ë¨€üÁU\"\àI;—.\Ç+;/–2¦^Ó¡\'\ËóH´p6YXÖ‘§ªfß­CÁ~\è_h5i§•Û±\Ô44{\"f\æ\Ëø\æ/\ÎË¿¯¢%¤lA¸#PF`5õ&\Æâ¦®‚ž¡\Þû\ânÿ\0\ï\Ù\Ì\æ±U\í! š€B¡@“I*Òž\Î{vö°^h[{šs<\ÚO˜\n\àRAñ\ÛW[£+\åk\ége}†°\ËmMPK›a`\Én>\îc\ÄòU|\Ïe¸†lnN€’”z_õe‚\àee“ä¨‰?®õFV\æT\é\Û~\ÑQ9ž\äà±œóSy\ÉA¨&NK“%bqPL”:Eˆ•\ÏNŒ…\ää¾Ø˜\Çû¶6‹Í±>«\ç\n`\í\à\ë¹‹ü•¢›lñ\â²©Á \Üv\\<\Ë~«Xee\êe%ÇŸo7$\ÕO¼nL\ÒGù/8ºÚŒ»‚\É<ŽvóœwœIq<K‰¹*}ôÈ¬\Þv\Õ6¸\n[þªeó\â¡~?¨AšË½tZ_‡¾#¤S¹£\ásCþ¤®\×[U\Ûú\0?ð\Õ_õÿ\0\Æªê©¤…Ð„ hB$“)¨”\ÊEÿ\0h,)\r\éIôhü\× c¬W]ÿ\0h\èŸ\Þÿ\0‘q\Ö?‡¢\ÜFô\Z\å6X¡z“¤TNG\ØX(°Yb\ßQt¨2JõŒ½b.P.S£#Þ Û“aŸ\ê\êÁ²û.\êÇ‚\çup\ß23q¿\Ý\Zp\Ôú.¹M†RQ\Ä#\Ýcn\"\å\×È’xø¯Lu\Û;}˜¹{ò8\0»´[0\ÃlÎ«\Ô\ÚL(ST9¬;Ð¸\ï\Æy\Ä\ãvúiä¼¬ø\çggº	Xœ9„üÁ3…\áK®Óº¶NjN\0©AL\é÷Z\\OV¬+£<N|ýŸªoÞšF²ÿ\0\Ù\Í\ß%K\Ç\Õ}Ð®\èpÁ#…ŒòGÁ`ÖŸ0/\æ¼Í–\è‚\\\Ùkd3¼f#hÝŠÿ\0Šù»\Ã ºƒ\Z\0\0\0\0\0\04\0d\0\nUH&’j4‚E$!\0R(B•„ \ã½?ÿ\0Dþûü‹‹GŠ¶Vóu*2¡\n£–4!JX\Ê³GW\è\ÛþQ¾\'ür¯{mx~\ìýP…\Ó\ê\Çþ\Zô\ßR6÷Ý¥ýË¾­T\ä!ygò\Ï\ÚJBF»µY©Ð… \êýÿ\0/7\ÂÏ«\×g‹C\æ„)U)¡\n€šBÿ\Ù'),(3,'jovan.etf@gmail.com','rootTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Mirko','Mirin',1,0,NULL,NULL,NULL,1,NULL,'ÿ\Øÿ\à\0JFIF\0\0\0\0\0\0ÿ\Û\0„\0	( \Z%!1!%)+...383-7(-.+\n\n\n\r\Z.&%-----+-/--++------+----7-2----------+--------+---+ÿÀ\0\0\á\0\á\"\0ÿ\Ä\0\0\0\0\0\0\0\0\0\0\0\0\0\0ÿ\Ä\0F\0		\0\0\0\0\0!1AQ\"aq‘2r¡±#RbÁðBs¢3CDS‚ƒ²\Â\Ñ$4t’³\áñÿ\Ä\0\Z\0\0\0\0\0\0\0\0\0\0ÿ\Ä\0\'\0\0\0\0\0\0\0\0!1A\"Q‘34aðÿ\Ú\0\0\0?\0\ë¨M\n©!4YBhP$&‹ HM@¬±\Ï3X\Ò÷¹­hÔ¸€™^n\Òm41u’›¸\ßu€öž\áÀr\Ê\ã;A´\ÕÏ¼Ž-Žý˜\ÚN\ã\Ô÷•\çžÉ‹\ÛV›²ºf%\Ò5Dµ®|¤}\Æzº\×Z”ý)Q“g²ve\ß n¹¥5ögv\r•\×7õ5\Ûývü\'§ªnõ<Ìr8x´\æúù\ê1-3Ä°\È\æ=º’<»\Â\ë;¶L¬)lÊ‹i|ŸarG#Ü½õ\î™97z{­lBvBös¢…$ Š,¤„²’²dY;!P‘d		\Ù&„Ð!4 HM\n„Ð$y–¾ \ëE!\å¿\ÂPp}¼\Å\ÝSX÷w-`\äÀl—DË°\â®Þ˜Ÿ\×5µ…›.\Õõ½/\"\çÐ‡×§>ò\Ë\Åj`­$\r\Üü\Âõ¤\Îi6\ÏÁsÏw®y~_*–/Jxk\ÍV©\ê­‘„µ\Ípp7\â³§xnb\ÞJTÓ½n+z¯ºn\å\Ã\ÝôVV&‚9Æ’F\×ÿ\0\Ü/e´«ûIÃ ¿ó*Â¾œ|kòHM„’’E\nI HMš	%+$ƒ\"B„Ð HMB¼­¤¬Ž*i:\ÉX\Â\è\Þ¼ð\Ý\çnœ…õ+\Õ\\Ã¥¬<\ÉQNð\ç\r\Ö\0,r½\×6\ç¢\Æ\Ìüq\ë\ÓN¿\Ô\Îb\å­võ\ÍÁ³§‚Ù¥ŒK\Ü#W†¸Ÿ„›‘à±½€=\Ä}ósù¯vdÀ<€lFF\ë<\ã\è\á…\ç³Mï‰¶’–¢l­}Ö¼5¤\è7ÇžªÉƒ\ã’uNG<†\ïqy·y#\â³AD\Ø\ÚCn&“k\\\ç™\ÍkQ[}\Í\r\Â2ÿ\0\êò\Ë9ôõ\Ã^_jþ-^\é\È|“<0ðk®\0½»@{§\Æ\Ë\Ïm<o•‚žB\âHÌœ¬r½e\Â÷,ö˜\Ú\ã˜u\Å\ï™\Ô¬´$\ÃC^%º2µÇ’ô\Çf3\Ùçžœ¯kªôy‰\Ä\êaN$gZ\Ç<n‚\íÖ¬8góV\Û.[\Ñ>õUD\àvDlhø\Þ\ìýC\êk»\Ù\×\ÌÙŽ\\BkLI\0„!²,š$&…BB‚hM\nBB$Ð*\î\ÝÒµÔŽ”¶\æ?-wFNùù+„±‡4µ\Â\í ‚9ƒ¨Y\Ë”²µ†^9LŸ8\ãr7x–·tos\"5¸òVŸ\Æ#Š!½m9q^_H8;)*Ÿnqge\ãz\×\àör\ÖÖ²ñ\è\ãÞ²~Ñ§K®<õ|>Ž½\Ý\ê\ãŠb¯1—µ\Í\Z\ÆûÛ¾<ìµ¶{§2\âZ\í\Ý?5\äa8\rdÀ˜\â\ë,lCd™Û“b&|wš’pöû»Ži¹<˜Xý(\ß\ëWYŠ±µNdO9¸\\\0\ãr½	qv†–¸ƒÞ¼,[g*)¾\êYX\Þn#^þ+\Ï09\\®.ó\ÏK«t\ãI\ê2ŸN\Ã\Ð\ã	‚¢^˜ý–\æ‹\äº\Z¨ô[G\Õá“¬›\Òy8\åò[—v3’G\Ë\Î÷+Bš\Ó$„Ð!4 HM#d&„		¡Q$Y4(!!I\0’kG\ZÅ¡¤õ3¿v&Z\æ\×$“`\Ð¤ž8\ïMµp<\áŒúF ¨\Üxw‘]—k6\\bö†8\Ç8obþë™¨cùxŽ|WÆ°I\éY<OŒò:ðFDx,\ì\Õe÷z\ë\Û>b\Í-L‘¸T@\ç4XoX\èyŸef¥Û°Ø¬e~ÿ\0x¾c¾\ëŸl\î-¸\à\×\ék\n\ÙX)^\Æ\í\ì/¥¾‹ŽË\ã¾g3Ç¯h6ŠZ¹,\é	e\îEò²Ð¤¤}u\\t\Ñ\æ\\\à;š\Ð.\çx…«`»c\0\å\Ñ:¥a†y÷G[\Öoq\Ý\Ý r\Ìý®¬{\ï^ösñŽELØ£dLc\ÖøZ\0E ˜].#B€Bh@š$&‚$š$&…D„(!!$\ãl\Î@}#izQ¡¤%Œsªe±[t\Äòm\ét:‰\ÛK\Þæ±.q\0\08’WÏ&m©\Äe\ê¡$Q\Æ{#N±\âÿ\0h\ï\ÈyñZ{Y\Ò^!x\Þ[=\î\"f‡–ûµwÓ¹UÖ¤Gk\èjD>\Å)ûxš7	9¾!—«r]\Ã!©Œ\ÅQ$g\'\Øóð_/\áU\ÒSLÊˆœ[$n\ÃPy‚2#½}%²{E};j#\È\èöy’\rZ~ ò^½\ìy\Ù\Ë\Ø\å[k\Ñd°^j\':Xµ\ê\ÈûFü.ý¡óñ\\\éó\Ê\ÞÁ\Þi0\àA‘0¾´sn3UMª\ØZZÖ’ö†J´È‹ƒ‡Š\ÆXK=›\Ãe—\Ýó{Z\ç›•\Úz)p£c#œ–š\ÇÝ€\äƒ.\Ðy5§øBñöCa\â}Aë¥…\ìŒ\ä\Ö\És%ŽN¶»™/k¥H\Ý\r#\'\'\Å<Op!\â\ÞY©§”¶·º\Ìl\Æ:‚’¯l¦\Õ\Ó\×\Â\Ç\Ç#D¥£z2\à\×\Û1c¨½\ìB°¬ M$\ÐBB,€B!4BB Ikbuñ\Ó\Âú‰œm.q<‡\ç{4*™µ½$Q\Ð\Þ6»\Ú*ól94þ7\è<3+m—I5u\Ïsc{\é\én@ ¹¼Ž$ó\Z*ˆ7VAeÚ­¹¬¯»f2/ê£»YoÅ\Ý\æª\êEª- \Ñec\Ö69dcUZU‹bö™øuH™·tN\ì\ÈÏ½õˆjx)8úºŽ²9blÑ¼:\'·x:ùž=Êƒ·O)±D\ïb$µòX‚ó{eÉšøøkP\è¿h÷dnQ#…4%·9	xFO:\Úsñ]¦²†9#1=1¹¶\"\Â\Ä¬±™c\ÅÓŸ\él™s¼p\ÌR#§…\Ä\Ûk\Ìwš¶mES\êp©\Ù3@™°‰2\ÑÀYÍ‘½\Æ\Úp^”¥„\Ó\ßyòv\Ü\î‡\î\Û^û«\æ\'\Â\\\×\0\ç\Ç‘Gh4‹y‹w®I³\Æ\å}Oým~X\á²\ßoœ\Ù&v<]\Å\\ös¤š\ê;7|TC÷&¹ ~\Þ\ã\æ¨üT·Ž‹Ö¾S\é—\é\"†´ˆ÷ú‰\ÏóresÉ¯÷O†G¹\\W\Ç\íW=”\é\"¶„Ë½¢û¸’\ÑÉ’f[\á˜SŠú9\n³½$\ÐU–³¬\ê&u†ä½ž\Ñýý	\ä®+!¡@$š$!!\ZBs^ž«K0\ÖB?ž¨`?;võ\0ù.”¹§O”¥\ØtrZý]Cny‚ß©V\Öh\Ö\0Ó¨Y¢r\ÔFp.“™|Šm+.¡Q¢öVx|”†}—kÁaÝ±±A²…žjh©±\Ö\Ó#\Ìj5ôF{U\íÔ»’\Ô\Â\Z\Ù4»…¬\Ù|\ìo\Þ\Ï`¯keq\ç\Ð\Õ2¥™“\Û÷\â>ó|xŽð—‰g]\Zs}¦g6\Ä	œ<C³#¿;¯KÄ›¦§\'³<9¹\å\Ö\Ç¿«ð¯ýln—Q#®Šóq\É\ØwšH‘™‚8[ÿ\0E|¬sñ\ß\Ùû¿S³L\Ï\Óx_\ÙÍ­˜ðD\ÈMÃ´™\ê?,‹O23YˆP@/¢ú\'\Úc[E»)&x\Äþ\Ó,õ¼w¯•ï¡œg\Ùñ™P\Ã÷ƒ´Ï¡iGÐˆI5…B	&„	MB€UÎ‘°\ÃS…\Ô\Â=\î¯}¿Nð\Û\ÍX\Ôd`p-:G‘Añ¸“\ÉgmŽ`§‰Å¹<±\ÛÝ–Fù5\î’\ÃKh\ÛaôY7yŠ2qY·O†ªŒ2ž<B\É+w…Æ«£\Ô\é$\Éˆ\ß%“EYcp²\ÐcRk\à¡tU\ÏañL+Ž€¹ž\í3\Ô\ßÌ¯G†Vþ\è¨4•ní‘žó<¸~^jõŽN$£tŒ÷]÷“­’ùûõód³\í÷}¨òÑ–7\æKü9\Û\Æh!I\Þò\Ð|3YZ8©„v‹55Y†VH\Ãg±Á\Ã\âi¸ú-yŽ`\rnŽÙ¨>±À±&\ÕR\ÃR\Ý%‰¯·\"Fmñ\áo®e\Ðn:%¥}ö;y ÿ\0Rþ7\ãg_\Ô.š°¦„!\0„!\0„!PÐ„(!>UÛºfÅ‰\Õ\Æ\ßtT\ÈGvñ\ß#ø—ˆ\Ö\Ý[:Y¥\êñzŸ\Æö\È<\Æþ`ª¬Kh­\Ï\ÑK­\æ\\?²Bv<þJ[®\î>jŒR®OšPdl¤X4\"\ßE³ˆGbXkH³Ù™\ê\ä\Ó\'qiÈE-\âó¨\ê,¢Ì²K}7sU\"\ë\ÖVJ\Ô\Z\áÙ«\rmðù\á\'6¹„|{~†\ê²\â¶ ”‹€rp±ð;\ê\Æxùq\í§m\×oû–\ÕIÁ!ªš\ÛÅŒòH\ÈNr\Æò1÷}\Ör\Ò{–¤G´·Zx\r>ñ\ÖPb\Í+‹ap1¼\Úöµn@\Ù}*\×\à‹‚4!|ƒ+3\Ï5\Þú\Ú3SHidvô´ö\0“r`u÷}-ôR\ÅtT\ÒMdB„\r	¤ M$§\Ú=\Êø¥·ò°|\ãu¿\Ì5c—w\é\ë	\ëh£ªh\íA)¿\î\å°?\ÄÖ®\nÒµ°\nô°<<TT2œ\Ê\Ø÷Í·˜\Ù_Šó`¶¥z8$0KSu1B\çf\æ\äG,\ìmŸ\Êþ-c;c\ÝÆ¶&x\rš9A\Ç7wÄŸ5\ã\Ó\Ñ>7ZV©ý‡2\ÏGyýU\ßÁŸ[\ìuµ3ˆ{˜\àXfµc\Ä&\ÝÝš\ß\ï5¶¿\Ä\Óùz.·\åñ\Ù_GO‡Ì–9þ#N\èžXx8¤¸V¡\ÃúÀef\íÛ«Z«È®½;<\ã‹~«¯.}6ZsY¸-bV\Ã]’öxµ*t\Å*“š)²%A˜)³ÝºÁ+\ì<Vq\îù*5Xn\åŠgd½ŒgªªwOO$£K€{ûN |×Ÿˆa5Hbž\Äñ«^,|¹Žð¥ö„÷øjÀlV\ìnZb\'4\æ2[Q\Ú\Ú^\êK\Õ\ç-Vž1\Æ\Ð\â‘äˆžO\ä\Zò,\ã\Ü\ZUUÑ5´¯T•C¢ü\Û0ö:óCöRs»@\Ýw›mŸŠ·æ¦„!\0„!„	&’?\ÃUK5+ý\Ùbs<	;\Äò]U;¢‘ñ<Y\ìsš\áøšlW\ØkçŽš°g\Ä\rCG\ÙTÿ\0	Z^<ò>eX(‘ÕžV\ÛõÁb¤67Y\Ú7®}\ÑbØˆ¨\å2Šº™`p\0\Æ[9Œnñ7Ð2^\Ë+÷‰lU©\0‘|·\íÀºÖ¿¢ 8\0	\"ÿ\0\ê±\Ä\Ç{\×!\×\Ê\Æ\Öô\\\Û}?ŸÛ«W©ºþ—iX\íòòXÇr$8\rnJ©b,i´¬ _V_0yŽaJª¢I”yp¦‹Sªýwu\é¸}¦\ïQ\ç\íÀ\×,½hV>£’Nˆ.‡1‘¼A\Z‘6&À¥¸ZoÁeñ5\Þ	\Ô\'bx¬\í<\n-e8-\ísðú€Ç¸º’GZF““o—Z\ÞDe~aw=¦Ù¨q7^¿»x\ä°%¤Œ³\â\Ó\Ä/™.¾’Øœ@»\n¥‘\æ\îöv~º\Éo\åø³o\ì|ýÐº	ŸÅž\Ç‘\Þ›…ÿ\0Y+6\ß\Î‰U2\ë¨€üÁU\"\àI;—.\Ç+;/–2¦^Ó¡\'\ËóH´p6YXÖ‘§ªfß­CÁ~\è_h5i§•Û±\Ô44{\"f\æ\Ëø\æ/\ÎË¿¯¢%¤lA¸#PF`5õ&\Æâ¦®‚ž¡\Þû\ânÿ\0\ï\Ù\Ì\æ±U\í! š€B¡@“I*Òž\Î{vö°^h[{šs<\ÚO˜\n\àRAñ\ÛW[£+\åk\ége}†°\ËmMPK›a`\Én>\îc\ÄòU|\Ïe¸†lnN€’”z_õe‚\àee“ä¨‰?®õFV\æT\é\Û~\ÑQ9ž\äà±œóSy\ÉA¨&NK“%bqPL”:Eˆ•\ÏNŒ…\ää¾Ø˜\Çû¶6‹Í±>«\ç\n`\í\à\ë¹‹ü•¢›lñ\â²©Á \Üv\\<\Ë~«Xee\êe%ÇŸo7$\ÕO¼nL\ÒGù/8ºÚŒ»‚\É<ŽvóœwœIq<K‰¹*}ôÈ¬\Þv\Õ6¸\n[þªeó\â¡~?¨AšË½tZ_‡¾#¤S¹£\ásCþ¤®\×[U\Ûú\0?ð\Õ_õÿ\0\Æªê©¤…Ð„ hB$“)¨”\ÊEÿ\0h,)\r\éIôhü\× c¬W]ÿ\0h\èŸ\Þÿ\0‘q\Ö?‡¢\ÜFô\Z\å6X¡z“¤TNG\ØX(°Yb\ßQt¨2JõŒ½b.P.S£#Þ Û“aŸ\ê\êÁ²û.\êÇ‚\çup\ß23q¿\Ý\Zp\Ôú.¹M†RQ\Ä#\Ýcn\"\å\×È’xø¯Lu\Û;}˜¹{ò8\0»´[0\ÃlÎ«\Ô\ÚL(ST9¬;Ð¸\ï\Æy\Ä\ãvúiä¼¬ø\çggº	Xœ9„üÁ3…\áK®Óº¶NjN\0©AL\é÷Z\\OV¬+£<N|ýŸªoÞšF²ÿ\0\Ù\Í\ß%K\Ç\Õ}Ð®\èpÁ#…ŒòGÁ`ÖŸ0/\æ¼Í–\è‚\\\Ùkd3¼f#hÝŠÿ\0Šù»\Ã ºƒ\Z\0\0\0\0\0\04\0d\0\nUH&’j4‚E$!\0R(B•„ \ã½?ÿ\0Dþûü‹‹GŠ¶Vóu*2¡\n£–4!JX\Ê³GW\è\ÛþQ¾\'ür¯{mx~\ìýP…\Ó\ê\Çþ\Zô\ßR6÷Ý¥ýË¾­T\ä!ygò\Ï\ÚJBF»µY©Ð… \êýÿ\0/7\ÂÏ«\×g‹C\æ„)U)¡\n€šBÿ\Ù'),(4,'jovan.etf@gmail.com','userTG','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Petar','Petrovic',1,0,1,2,3,3,NULL,NULL),(18,'jovan.etf@gmail.com','adminM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Marko','Markovic',1,1,NULL,NULL,6,2,NULL,NULL),(19,'jovan.etf@gmail.com','userM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Mitar','Mitrovic',1,1,3,4,6,3,NULL,NULL),(20,'jovan.etf@gmail.com','rootM','A5AECC7FD12974A7670AEC5F6E96830DB953D1DCDC56785984F42FA987251A43CD90D88946A6D150F9C2C84DDF4C45B6729AA2245619883CAA30FC084B6770A6','Milan','Milanovic',1,0,NULL,NULL,NULL,1,NULL,NULL);
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
