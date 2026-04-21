    CREATE TYPE genre_type AS ENUM ('MASCULIN', 'FEMININ');
    CREATE TYPE poste_type AS ENUM ('PRESIDENT', 'PRESIDENT_ADJOINT', 'TRESORIER', 'SECRETAIRE', 'CONFIRME', 'JUNIOR');
    CREATE TYPE compte_type AS ENUM ('CAISSE', 'BANQUE', 'MOBILE_MONEY');

    CREATE TABLE collectivite (
                                  id SERIAL PRIMARY KEY,
                                  nom VARCHAR(255) UNIQUE NOT NULL,
                                  numero VARCHAR(50) UNIQUE NOT NULL,
                                  ville VARCHAR(100) NOT NULL,
                                  specialite VARCHAR(100),
                                  date_creation DATE DEFAULT CURRENT_DATE,
                                  autorisation_federation BOOLEAN DEFAULT FALSE
    );

    CREATE TABLE membre (
                            id SERIAL PRIMARY KEY,
                            nom VARCHAR(100) NOT NULL,
                            prenom VARCHAR(100) NOT NULL,
                            date_naissance DATE NOT NULL,
                            genre genre_type NOT NULL,
                            adresse TEXT,
                            metier VARCHAR(100),
                            telephone VARCHAR(20),
                            email VARCHAR(150) UNIQUE,
                            date_adhesion DATE DEFAULT CURRENT_DATE,
                            id_collectivite INTEGER REFERENCES collectivite(id),
                            parrain_id INTEGER REFERENCES membre(id),
                            est_actif BOOLEAN DEFAULT TRUE
    );

    CREATE TABLE mandat (
                            id SERIAL PRIMARY KEY,
                            id_membre INTEGER REFERENCES membre(id),
                            id_collectivite INTEGER REFERENCES collectivite(id),
                            poste poste_type NOT NULL,
                            annee_civile INTEGER NOT NULL,
                            UNIQUE(id_collectivite, poste, annee_civile) -- Un seul président par an par coll.
    );

    CREATE TABLE compte (
                            id SERIAL PRIMARY KEY,
                            id_collectivite INTEGER REFERENCES collectivite(id),
                            type_compte compte_type NOT NULL,
                            nom_titulaire VARCHAR(255),
                            banque_nom VARCHAR(50), -- Pour BANQUE
                            rib CHAR(23),           -- Format strict
                            service_mm VARCHAR(50), -- Mvola, Orange...
                            numero_tel VARCHAR(20), -- Pour MM
                            solde_mga DECIMAL(15, 2) DEFAULT 0
    );

    CREATE TABLE cotisation (
                                id SERIAL PRIMARY KEY,
                                id_membre INTEGER REFERENCES membre(id),
                                id_compte INTEGER REFERENCES compte(id),
                                montant DECIMAL(15, 2) NOT NULL,
                                date_paiement TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                type_cotisation VARCHAR(50), -- Mensuelle, Annuelle, Ponctuelle
                                mode_paiement VARCHAR(50)    -- Espèce, Virement, MM
    );

    CREATE TABLE activite (
                              id SERIAL PRIMARY KEY,
                              nom VARCHAR(255) NOT NULL,
                              date_activite TIMESTAMP NOT NULL,
                              type_activite VARCHAR(50),
                              est_obligatoire BOOLEAN DEFAULT TRUE,
                              cible VARCHAR(50), -- "TOUS", "JUNIORS"
                              id_collectivite INTEGER REFERENCES collectivite(id) -- Null si Fédéral
    );

    CREATE TABLE presence (
                              id SERIAL PRIMARY KEY,
                              id_activite INTEGER REFERENCES activite(id),
                              id_membre INTEGER REFERENCES membre(id),
                              statut VARCHAR(20), -- PRESENT, ABSENT, EXCUSE
                              motif_absence TEXT
    );