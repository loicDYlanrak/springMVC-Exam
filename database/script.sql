

CREATE TABLE Bibliothecaire (
    id_biblio INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100),
    pwd VARCHAR(100)
);

CREATE TABLE TypeAdherent (
    id_type_adherent INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(50) NOT NULL,
    duree_pret INT NOT NULL,
    quota INT NOT NULL ,
    nb_reservation_max INT NOT NULL,
    duree_penalite INT NOT NULL,
    nb_jour_max_prologement INT NOT NULL
);

CREATE TABLE Adherent (
    id_adherent INT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    pwd VARCHAR(100) NOT NULL,
    date_naissance DATE NOT NULL, 
    id_type_adherent INT NOT NULL,
    FOREIGN KEY (id_type_adherent) REFERENCES TypeAdherent(id_type_adherent)
);


CREATE TABLE Livre (
    id_livre INT PRIMARY KEY AUTO_INCREMENT,
    titre VARCHAR(100) NOT NULL,
    auteur VARCHAR(100) NOT NULL,
    age_minimum INT NOT NULL, 
    annee_publication INT
);

CREATE TABLE Exemplaire (
    id_exemplaire INT PRIMARY KEY AUTO_INCREMENT,
    id_livre INT NOT NULL,
    FOREIGN KEY (id_livre) REFERENCES Livre(id_livre)
);

CREATE TABLE Abonnement (
    id_abonnement INT PRIMARY KEY AUTO_INCREMENT,
    id_adherent INT NOT NULL,
    date_debut DATE NOT NULL,
    date_fin DATE NOT NULL,
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent)
);
 
CREATE TABLE EtatExemplaire ( 
    id_etat INT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) 
);

CREATE TABLE StatusExemplaire ( 
    id_status_exemplaire INT PRIMARY KEY AUTO_INCREMENT,
    id_exemplaire INT NOT NULL,
    date_changement DATE NOT NULL,
    id_etat INT NOT NULL,
    id_biblio INT NULL,
    FOREIGN KEY (id_exemplaire) REFERENCES Exemplaire(id_exemplaire),
    FOREIGN KEY (id_biblio) REFERENCES Bibliothecaire(id_biblio),
    FOREIGN KEY (id_etat) REFERENCES EtatExemplaire(id_etat)
);

--disponible / prêté

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
    date_retour DATE DEFAULT NULL,
    FOREIGN KEY (id_exemplaire) REFERENCES Exemplaire(id_exemplaire),
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent)
);

CREATE TABLE Prologement (
    id_prologement INT PRIMARY KEY AUTO_INCREMENT,
    id_pret INT NOT NULL,
    FOREIGN KEY (id_prologement) REFERENCES Pret(id_pret)
);

CREATE TABLE Reservation (
    id_reservation INT PRIMARY KEY AUTO_INCREMENT,
    id_exemplaire INT NOT NULL,
    id_adherent INT NOT NULL,
    date_reservation DATETIME NOT NULL,
    valide BOOLEAN,
    FOREIGN KEY (id_exemplaire) REFERENCES Exemplaire(id_exemplaire),
    FOREIGN KEY (id_adherent) REFERENCES Adherent(id_adherent)
);

CREATE TABLE Penalite (
    id_penalite INT PRIMARY KEY AUTO_INCREMENT,
    id_pret INT,
    FOREIGN KEY (id_pret) REFERENCES Pret(id_pret)
);

CREATE TABLE JourFerie (
    id_jour_ferie INT PRIMARY KEY AUTO_INCREMENT,
    date_ferie DATE NOT NULL,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE JourOuvrable (
    id_jour_ouvrable INT PRIMARY KEY AUTO_INCREMENT,
    jour_semaine INT NOT NULL
);

CREATE TABLE RegleRetour (
    id_regle INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    action ENUM('AVANT', 'APRES', 'PROCHAIN_OUVRABLE') NOT NULL,
    jours_decallage INT DEFAULT 0
);