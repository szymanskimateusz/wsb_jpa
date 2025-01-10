INSERT INTO ADDRESS (CITY, ADDRESS_LINE1, ADDRESS_LINE2, POSTAL_CODE)
            VALUES ('city', 'xx', 'yy', '62-030'),
                   ('city2', 'xx', 'yy', '62-031'),
                   ('city3', 'xx', 'yy', '32-031');

INSERT INTO PATIENT (FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, PATIENT_NUMBER, DATE_OF_BIRTH, ACTIVE, ADDRESS_ID)
VALUES ('Jan', 'Stok', 123123124, 'dodsvff@fds.esf', 43254, '2000-01-01', true, 1),
       ('Janina', 'Stok', 123123155, 'janina@fds.esf', 43253, '2001-01-01', true, 1),
       ('Konstanty', 'Ubisz', 123123124, 'konstanty@fds.esf', 23254, '2004-01-01', true, 2);

INSERT INTO DOCTOR (FIRST_NAME, LAST_NAME, TELEPHONE_NUMBER, EMAIL, DOCTOR_NUMBER, SPECIALIZATION, ADDRESS_ID)
VALUES ('John', 'Stoch', 123123125, 'dodsf1@fds.esf', 54322, 'GP', 3),
       ('Johna', 'Stoch', 123123123, 'dodsf@fds.esf', 5432, 'GP', 3);

INSERT INTO VISIT (DESCRIPTION, TIME, PATIENT_ID, DOCTOR_ID)
VALUES ('Check-up', '2024-12-15T10:00:00', 1, 1),
       ('Check-up', '2024-12-16T10:00:00', 2, 2),
       ('Check-up 2', '2025-01-15T10:00:00', 1, 1),
       ('Check-up', '2025-01-16T10:00:00', 3, 2);

INSERT INTO MEDICAL_TREATMENT (DESCRIPTION, TREATMENT_TYPE, VISIT_ID)
VALUES ('this is the description', 'USG', 1);

select p.FIRST_NAME, p.LAST_NAME from PATIENT p where p.LAST_NAME = 'Stok';

select v.DESCRIPTION, v.TIME  from VISIT v where v.PATIENT_ID = 1;

select p.FIRST_NAME, p.LAST_NAME from PATIENT p left join VISIT v on p.ID = v.PATIENT_ID group by p.ID, p.FIRST_NAME, p.LAST_NAME  having count(v.PATIENT_ID) > 2;

select p.FIRST_NAME, p.LAST_NAME from PATIENT p where p.DATE_OF_BIRTH between '1999-12-12' and '2001-02-02'; -- zapytanie w ramach polecenia 4, bo u mnie jest jest boolean

select p.FIRST_NAME, p.LAST_NAME from PATIENT p where p.ACTIVE = true; -- zapytanie do dodanego przeze mnie pola