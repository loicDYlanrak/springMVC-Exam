CREATE DATABASE IF NOT EXISTS biblio;
USE biblio;


-- Désactiver temporairement les contraintes de clés étrangères pour éviter les erreurs de dépendance
SET FOREIGN_KEY_CHECKS = 0;

-- Supprimer les données de toutes les tables
DELETE FROM Prologement;
DELETE FROM Penalite;
DELETE FROM Reservation;
DELETE FROM Pret;
DELETE FROM TypePret;
DELETE FROM StatusExemplaire;
DELETE FROM EtatExemplaire;
DELETE FROM Abonnement;
DELETE FROM Exemplaire;
DELETE FROM Livre;
DELETE FROM Adherent;
DELETE FROM TypeAdherent;
DELETE FROM Bibliothecaire;
DELETE FROM JourFerie;
DELETE FROM JourOuvrable;

-- Réactiver les contraintes de clés étrangères
SET FOREIGN_KEY_CHECKS = 1;