
DROP TABLE IF EXISTS member_referees CASCADE;
DROP TABLE IF EXISTS collectivity CASCADE;
DROP TABLE IF EXISTS member CASCADE;
DROP TYPE IF EXISTS gender_type CASCADE;
DROP TYPE IF EXISTS occupation_type CASCADE;

CREATE TYPE gender_type AS ENUM ('MALE', 'FEMALE');
CREATE TYPE occupation_type AS ENUM ('JUNIOR', 'SENIOR', 'SECRETARY', 'TREASURER', 'VICE_PRESIDENT', 'PRESIDENT');

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

                        registration_fee_paid BOOLEAN DEFAULT FALSE,
                        membership_dues_paid BOOLEAN DEFAULT FALSE,

                        collectivity_id INT
);

CREATE TABLE collectivity (
                              id SERIAL PRIMARY KEY,
                              location VARCHAR(255) NOT NULL,
                              speciality VARCHAR(255),
                              federation_approval BOOLEAN DEFAULT FALSE,
                              creation_date DATE DEFAULT CURRENT_DATE,

                              president_id INT REFERENCES member(id),
                              vice_president_id INT REFERENCES member(id),
                              treasurer_id INT REFERENCES member(id),
                              secretary_id INT REFERENCES member(id)
);

ALTER TABLE member ADD CONSTRAINT fk_member_collectivity
    FOREIGN KEY (collectivity_id) REFERENCES collectivity(id);

CREATE TABLE member_referees (
                                 member_id INT REFERENCES member(id),
                                 referee_id INT REFERENCES member(id),
                                 relationship_type VARCHAR(100), -- ex: 'famille', 'amis', 'collègues'
                                 PRIMARY KEY (member_id, referee_id)
);

ALTER TABLE collectivity
    ADD COLUMN name VARCHAR(255) UNIQUE,
ADD COLUMN number VARCHAR(50) UNIQUE;

-- --------------------------------------------FONCTIONNALITE C ET D_____________________________

CREATE TYPE frequency AS ENUM ('WEEKLY', 'MONTHLY', 'ANNUALLY', 'PUNCTUALLY');
CREATE TYPE payment_mode AS ENUM ('CASH', 'MOBILE_BANKING', 'BANK_TRANSFER');

CREATE TABLE membership_fee (
                                id SERIAL PRIMARY KEY,
                                collectivity_id INT REFERENCES collectivity(id),
                                eligible_from DATE NOT NULL,
                                frequency frequency NOT NULL,
                                amount DECIMAL NOT NULL,
                                label VARCHAR(255),
                                status VARCHAR(20) DEFAULT 'ACTIVE'
);

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

CREATE TABLE member_payment (
                                id SERIAL PRIMARY KEY,
                                member_id INT REFERENCES member(id),
                                fee_id INT REFERENCES membership_fee(id),
                                account_id INT REFERENCES financial_account(id),
                                amount DECIMAL NOT NULL,
                                payment_mode payment_mode NOT NULL,
                                creation_date DATE DEFAULT CURRENT_DATE
);