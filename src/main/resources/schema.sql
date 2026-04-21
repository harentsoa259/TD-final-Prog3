-- 1. droits
\c agroptima postgres

GRANT CREATE ON SCHEMA public TO user123;

GRANT ALL PRIVILEGES ON SCHEMA public TO user123;

DROP TABLE IF EXISTS parrainage, structure, membre, collectivite CASCADE;

CREATE TABLE collectivite (
                              id SERIAL PRIMARY KEY,
                              location VARCHAR(255) NOT NULL
);

CREATE TABLE membre (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        birth_date DATE NOT NULL,
                        gender VARCHAR(20),
                        address TEXT,
                        profession VARCHAR(100),
                        phone INTEGER,
                        email VARCHAR(150),
                        occupation VARCHAR(50) NOT NULL,
                        collectivite_id INTEGER REFERENCES collectivite(id) ON DELETE SET NULL
);