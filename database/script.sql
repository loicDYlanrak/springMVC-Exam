CREATE DATABASE IF NOT EXISTS biblio;
USE biblio;

CREATE TABLE Users (
    id_user INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    email VARCHAR(100),
    pwd VARCHAR(100)
);

CREATE TABLE TypeAdherent (
    id_type_adherent INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50) NOT NULL,
    duree_pret INT NOT NULL COMMENT 'Duree maximale de pret en jours',
    nb_livres_max INT NOT NULL COMMENT 'Nombre maximum de livres pouvant etre empruntes simultanement',
    cotisation DECIMAL(10,2) NOT NULL COMMENT 'Montant de la cotisation annuelle'
);

CREATE TABLE Adherent (
    id_adherent INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    prenom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telephone VARCHAR(20),
    adresse TEXT,
    date_inscription DATE NOT NULL,
    date_expiration DATE NOT NULL,
    id_type_adherent INT NOT NULL,
    FOREIGN KEY (id_type_adherent) REFERENCES TypeAdherent(id_type_adherent)
);

CREATE TABLE Cotisation (
    id_cotisation INT PRIMARY KEY AUTO_INCREMENT,
    id_adherent INT NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    date_paiement DATE NOT NULL,
    mode_paiement VARCHAR(50),
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent)
);

CREATE TABLE Livre (
    id_livre INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(100) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    annee_publication INT
);

CREATE TABLE EtatExemplaire (
    id_etat INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE Exemplaire (
    id_exemplaire INT PRIMARY KEY AUTO_INCREMENT,
    id_livre INT NOT NULL,
    id_etat INT NOT NULL,
    disponible BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_livre) REFERENCES Livre(id_livre),
    FOREIGN KEY (id_etat) REFERENCES EtatExemplaire(id_etat)
);

CREATE TABLE HistoEtat ( 
    id_histoEtat INT PRIMARY KEY AUTO_INCREMENT,
    id_exemplaire INT NOT NULL,
    date_changement DATE NOT NULL,
    id_etat INT NOT NULL,
    FOREIGN KEY (id_exemplaire) REFERENCES Exemplaire(id_exemplaire),
    FOREIGN KEY (id_etat) REFERENCES EtatExemplaire(id_etat)
);

CREATE TABLE TypePret ( 
    id_type_pret INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50) NOT NULL
);

CREATE TABLE Pret (
    id_pret INT PRIMARY KEY AUTO_INCREMENT,
    id_exemplaire INT NOT NULL,
    id_adherent INT NOT NULL,
    id_type_pret INT NOT NULL,
    date_pret DATE NOT NULL,
    date_retour_prevue DATETIME NOT NULL,
    date_retour_effective DATE,
    prolonge BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_exemplaire) REFERENCES Exemplaire(id_exemplaire),
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent)
);

CREATE TABLE ProlongementTypeAdherent (
    id_type_adherent INT NOT NULL,
    nb_prolongements_max INT NOT NULL DEFAULT 0,
    duree_prolongement INT NOT NULL,
    PRIMARY KEY (id_type_adherent),
    FOREIGN KEY (id_type_adherent) REFERENCES TypeAdherent(id_type_adherent)
);

CREATE TABLE Reservation (
    id_reservation INT PRIMARY KEY AUTO_INCREMENT,
    id_livre INT NOT NULL,
    id_adherent INT NOT NULL,
    id_type_pret INT NOT NULL,
    date_reservation DATETIME NOT NULL,
    date_expiration DATETIME NOT NULL,
    statut ENUM('en_attente', 'annulee', 'terminee') NOT NULL,
    FOREIGN KEY (id_livre) REFERENCES Livre(id_livre),
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent),
    FOREIGN KEY (id_type_pret) REFERENCES TypePret(id_type_pret)
);


CREATE TABLE TypePenalite (
    id_type_penalite INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL COMMENT 'Type de pénalité (retard, perte, dégradation, etc.)',
    description TEXT COMMENT 'Description détaillée de la pénalité',
    impact_monetaire BOOLEAN DEFAULT FALSE COMMENT 'Si la pénalité implique une amende',
    montant_base DECIMAL(10,2) DEFAULT 0 COMMENT 'Montant de base de l\'amende le cas échéant',
    impact_pret BOOLEAN DEFAULT FALSE COMMENT 'Si la pénalité restreint les prêts',
    duree_restriction_pret INT DEFAULT 0 COMMENT 'Durée de restriction des prêts en jours',
    impact_reservation BOOLEAN DEFAULT FALSE COMMENT 'Si la pénalité restreint les réservations',
    duree_restriction_reservation INT DEFAULT 0 COMMENT 'Durée de restriction des réservations en jours',
    impact_adhesion BOOLEAN DEFAULT FALSE COMMENT 'Si la pénalité suspend l\'adhésion',
    duree_suspension_adhesion INT DEFAULT 0 COMMENT 'Durée de suspension de l\'adhésion en jours'
);

CREATE TABLE Penalite (
    id_penalite INT PRIMARY KEY AUTO_INCREMENT,
    id_adherent INT NOT NULL,
    id_type_penalite INT NOT NULL,
    id_pret INT COMMENT 'Peut être NULL si la pénalité n\'est pas liée à un prêt',
    date_debut DATE NOT NULL,
    montant DECIMAL(10,2) DEFAULT 0,
    payee BOOLEAN DEFAULT FALSE,
    commentaire TEXT,
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent),
    FOREIGN KEY (id_type_penalite) REFERENCES TypePenalite(id_type_penalite),
    FOREIGN KEY (id_pret) REFERENCES Pret(id_pret)
);

CREATE TABLE JourFerie (
    id_jour_ferie INT PRIMARY KEY AUTO_INCREMENT,
    date_ferie DATE NOT NULL,
    libelle VARCHAR(100) NOT NULL
);

CREATE TABLE JourOuvrable (
    id_jour_ouvrable INT PRIMARY KEY AUTO_INCREMENT,
    jour_semaine ENUM('lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi', 'dimanche') NOT NULL,
    ouvert BOOLEAN DEFAULT TRUE,
    heure_ouverture TIME,
    heure_fermeture TIME
);

