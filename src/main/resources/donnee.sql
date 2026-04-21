INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Jean', 'Dupont', '1985-03-12', 'MALE', '2025-01-01', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Marie', 'Sitraka', '1990-07-22', 'FEMALE', '2025-02-15', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Luc', 'Rakoto', '1982-11-30', 'MALE', '2025-03-10', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Soa', 'Andria', '1995-05-05', 'FEMALE', '2025-04-01', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Rija', 'Noel', '1988-12-12', 'MALE', '2025-05-20', 'SENIOR', true, true);

INSERT INTO collectivity (id, location, speciality, federation_approval, president_id, vice_president_id, treasurer_id, secretary_id)
VALUES (1, 'Antsirabe', 'Riziculture', true, 1, 2, 3, 4);

INSERT INTO collectivity (id, location, speciality, federation_approval, president_id, vice_president_id, treasurer_id, secretary_id)
VALUES (2, 'Fianarantsoa', 'Caficulture', true, 5, 4, 3, 2);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Aina', 'Vola', '1998-01-10', 'FEMALE', '2026-03-01', 'JUNIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Tovo', 'Elias', '2000-02-20', 'MALE', '2026-03-15', 'JUNIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Lala', 'Jean', '1994-06-25', 'MALE', '2026-04-01', 'JUNIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Faly', 'Sitraka', '1996-01-30', 'FEMALE', '2026-04-05', 'JUNIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Noro', 'Aina', '1993-10-22', 'FEMALE', '2026-04-10', 'JUNIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Tahina', 'Rakoto', '1990-05-15', 'MALE', 'Ambohijatovo Antananarivo', 'Agriculteur', 341122233, 'tahina@agro.mg', '2026-04-21', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date, occupation, registration_fee_paid, membership_dues_paid, collectivity_id)
VALUES ('Rindra', 'Andrian', '1988-11-10', 'MALE', 'Antsirabe', 'Veterinaire', 345566778, 'rindra@agro.mg', '2024-06-01', 'SENIOR', true, true, 1);

INSERT INTO member (first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date, occupation, registration_fee_paid, membership_dues_paid)
VALUES ('Hery', 'Tiana', '1980-05-30', 'MALE', 'Tamatave', 'Exportateur', 349988776, 'hery@agro.mg', '2024-12-01', 'SENIOR', true, true);

INSERT INTO member (first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date, occupation, registration_fee_paid, membership_dues_paid, collectivity_id)
VALUES ('Lova', 'Hasina', '1991-03-25', 'FEMALE', 'Antsirabe', 'Productrice', 321199887, 'lova@agro.mg', '2024-09-10', 'SENIOR', true, true, 1);

INSERT INTO member (first_name, last_name, birth_date, gender, address, profession, phone_number, email, membership_date, occupation, registration_fee_paid, membership_dues_paid, collectivity_id)
VALUES ('Finoana', 'Rivo', '1992-02-14', 'FEMALE', 'Fianarantsoa', 'Agronome', 320011223, 'finoana@agro.mg', '2025-01-15', 'SENIOR', true, true, 2);

INSERT INTO member (first_name, last_name, birth_date, gender, phone_number, membership_date, occupation, registration_fee_paid, membership_dues_paid, collectivity_id)
VALUES ('Harentsoa', 'Software', '2000-05-15', 'MALE', 341234567, '2026-04-21', 'JUNIOR', true, true, 1);