-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 25-Jun-2023 às 02:58
-- Versão do servidor: 10.4.21-MariaDB
-- versão do PHP: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `pwn3`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `accchar`
--

CREATE TABLE `accchar` (
  `accId` int(11) NOT NULL,
  `charId` int(11) NOT NULL,
  `nick` varchar(20) NOT NULL,
  `charSpec` varchar(40) NOT NULL,
  `charStatus` varchar(5000) NOT NULL DEFAULT '010008004c6f737443617665000000000000000003001000477265617442616c6c734f66466972650100000000000c005265766f6c766572416d6d6f9c00000000000b00436f77626f79436f6465720100000000001000477265617442616c6c734f66466972650b00436f77626f79436f6465720000000000000000000000000000000000000004001c00416368696576656d656e745f477265617442616c6c734f66466972651000477265617442616c6c734f66466972650c004c6f737443617665427573680b00546f776e56697369746564',
  `charLocal` varchar(30) NOT NULL DEFAULT 'Town'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `accont`
--

CREATE TABLE `accont` (
  `id` int(11) NOT NULL,
  `accName` char(30) NOT NULL,
  `accPass` char(30) NOT NULL,
  `accTeamHash` char(128) NOT NULL,
  `accTeamName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estrutura da tabela `mobsbears`
--

CREATE TABLE `mobsbears` (
  `id` int(11) NOT NULL,
  `mobName` varchar(25) NOT NULL DEFAULT 'Bear',
  `mobCoord` varchar(25) NOT NULL,
  `mobId` varchar(8) DEFAULT '7D00',
  `mobHp` int(11) NOT NULL DEFAULT 145
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `mobsbears`
--

INSERT INTO `mobsbears` (`id`, `mobName`, `mobCoord`, `mobId`, `mobHp`) VALUES
(1, 'Bear', 'BAB8AFC66BCFE3C6F7972045', '7D00', 145),
(2, 'Bear', '2B7DB5C6114214C7482B2345', '7D00', 145),
(3, 'Bear', '65B83CC64C4F0AC7755C1E45', '7D00', 145),
(4, 'Bear', 'D72FAEC5AA3701C737842B45', '7D00', 145),
(5, 'Bear', '23B701C6BEAC2BC788AB4645', '7D00', 145),
(6, 'Bear', 'DB649E448B2125C798AF1C45', '7D00', 145),
(7, 'Bear', 'CA8D6B42597C0BC70C531945', '7D00', 145);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `accchar`
--
ALTER TABLE `accchar`
  ADD PRIMARY KEY (`charId`),
  ADD KEY `accId` (`accId`);

--
-- Índices para tabela `accont`
--
ALTER TABLE `accont`
  ADD PRIMARY KEY (`id`);

--
-- Índices para tabela `mobsbears`
--
ALTER TABLE `mobsbears`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `accchar`
--
ALTER TABLE `accchar`
  MODIFY `charId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=60;

--
-- AUTO_INCREMENT de tabela `accont`
--
ALTER TABLE `accont`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de tabela `mobsbears`
--
ALTER TABLE `mobsbears`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `accchar`
--
ALTER TABLE `accchar`
  ADD CONSTRAINT `accchar_ibfk_1` FOREIGN KEY (`accId`) REFERENCES `accont` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
