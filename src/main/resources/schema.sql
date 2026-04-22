-- 1. Nettoyage complet
DROP TABLE IF EXISTS member_referees CASCADE;
DROP TABLE IF EXISTS collectivity CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TYPE IF EXISTS gender_type CASCADE;
DROP TYPE IF EXISTS occupation_type CASCADE;

-- 2. Création des énumérations (identiques à vos Enums Java)
CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_type AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

-- 3. Table des MEMBRES
-- On la crée avec collectivity_id en nullable pour gérer la circularité
CREATE TABLE member (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender gender_type NOT NULL,
                        address TEXT,
                        profession VARCHAR(150),
                        phone_number INT,
                        email VARCHAR(150) UNIQUE,
                        membership_date DATE DEFAULT CURRENT_DATE,
                        occupation occupation_type NOT NULL,

    -- Status financier (Consigne B-2 du 21 avril)
                        registration_fee_paid BOOLEAN DEFAULT FALSE,
                        membership_dues_paid BOOLEAN DEFAULT FALSE,

    -- Référence à la collectivité (sera liée plus tard)
                        collectivity_id INT
);

-- 4. Table des COLLECTIVITÉS
CREATE TABLE collectivity (
                              id SERIAL PRIMARY KEY,
                              location VARCHAR(255) NOT NULL,
                              speciality VARCHAR(255),
                              federation_approval BOOLEAN DEFAULT FALSE,
                              creation_date DATE DEFAULT CURRENT_DATE,

    -- Structure du bureau (IDs pointant vers la table member)
                              president_id INT REFERENCES member(id),
                              vice_president_id INT REFERENCES member(id),
                              treasurer_id INT REFERENCES member(id),
                              secretary_id INT REFERENCES member(id)
);

-- 5. Ajout de la clé étrangère manquante sur member
ALTER TABLE member ADD CONSTRAINT fk_member_collectivity
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id);

-- 6. Table de liaison pour les PARRAINS (Many-to-Many)
-- Stocke les parrains d'un membre et la nature de leur relation
CREATE TABLE member_referees (
                                 member_id INT REFERENCES member(id),
                                 referee_id INT REFERENCES member(id),
                                 relationship_type VARCHAR(100), -- ex: 'famille', 'amis', 'collègues'
                                 PRIMARY KEY (member_id, referee_id)
);

--7
ALTER TABLE collectivity
    ADD COLUMN name VARCHAR(255) UNIQUE,
ADD COLUMN number VARCHAR(50) UNIQUE;

-- --------------------------------------------FONCTIONNALITE C ET D_____________________________

CREATE TYPE frequency AS ENUM ('WEEKLY', 'MONTHLY', 'ANNUALLY', 'PUNCTUALLY');
CREATE TYPE payment_mode AS ENUM ('CASH', 'MOBILE_BANKING', 'BANK_TRANSFER');

-- Types de frais de cotisation
CREATE TABLE membership_fee (
                                id SERIAL PRIMARY KEY,
                                collectivity_id INT REFERENCES collectivity(id),
                                eligible_from DATE NOT NULL,
                                frequency frequency NOT NULL,
                                amount DECIMAL NOT NULL,
                                label VARCHAR(255),
                                status VARCHAR(20) DEFAULT 'ACTIVE'
);

-- Comptes financiers (Table unique pour simplifier le JDBC)
CREATE TABLE financial_account (
                                   id SERIAL PRIMARY KEY,
                                   type VARCHAR(50), -- CASH, MOBILE, BANK
                                   amount DECIMAL DEFAULT 0,
                                   holder_name VARCHAR(255),
                                   mobile_service VARCHAR(50),
                                   mobile_number VARCHAR(20),
                                   bank_name VARCHAR(50),
                                   bank_account_number VARCHAR(50)
);

-- Paiements et Transactions
CREATE TABLE member_payment (
                                id SERIAL PRIMARY KEY,
                                member_id INT REFERENCES member(id),
                                fee_id INT REFERENCES membership_fee(id),
                                account_id INT REFERENCES financial_account(id),
                                amount DECIMAL NOT NULL,
                                payment_mode payment_mode NOT NULL,
                                creation_date DATE DEFAULT CURRENT_DATE
);