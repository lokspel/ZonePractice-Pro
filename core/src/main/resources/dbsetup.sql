CREATE TABLE IF NOT EXISTS `global_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  `firstJoin` bigint NOT NULL,
  `lastJoin` bigint NOT NULL,
  `unrankedWins` int DEFAULT NULL,
  `unrankedLosses` int DEFAULT NULL,
  `rankedWins` int DEFAULT NULL,
  `rankedLosses` int DEFAULT NULL,
  `globalElo` int DEFAULT NULL,
  `globalRank` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `ladder_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  `ladder` varchar(100) NOT NULL,
  `unrankedWins` int DEFAULT NULL,
  `unrankedLosses` int DEFAULT NULL,
  `rankedWins` int DEFAULT NULL,
  `rankedLosses` int DEFAULT NULL,
  `elo` int DEFAULT NULL,
  `rank` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;