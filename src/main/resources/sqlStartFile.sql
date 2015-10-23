DROP VIEW LOGIN_VIEW;
CREATE VIEW LOGIN_VIEW AS SELECT EMAIL, PASSWORD, TYPES.TYPES FROM ACCOUNT INNER JOIN TYPES ON ACCOUNT.ID_TYPE=TYPES.ID_TYPE WHERE NOT ACTIVE=0;

DELETE FROM ADVERTAPP.ADVERT;
DELETE FROM ADVERTAPP.ACCOUNT;
DELETE FROM ADVERTAPP.CATEGORY;
DELETE FROM ADVERTAPP.STATUS;
DELETE FROM ADVERTAPP.TYPES;

--tak aby generowane wartości ID zaczynały się po wartościach wprowadzonych z pliku sqlStartFile
UPDATE ADVERTAPP."SEQUENCE" SET ADVERTAPP."SEQUENCE".SEQ_COUNT=50; 

INSERT INTO ADVERTAPP.TYPES (ID_TYPE, TYPES) VALUES (1,'USER');
INSERT INTO ADVERTAPP.TYPES (ID_TYPE, TYPES) VALUES (2,'ADMIN');

INSERT INTO ADVERTAPP.STATUS (ID_STATUS, STATUS) VALUES (1,'AKTYWNE/ ACTIVE');
INSERT INTO ADVERTAPP.STATUS (ID_STATUS, STATUS) VALUES (2,'NIEREZERWOWALNE/ NONRESERVABLE');
INSERT INTO ADVERTAPP.STATUS (ID_STATUS, STATUS) VALUES (3,'ZAREZERWOWANE/ RESERVED');

INSERT INTO ADVERTAPP.CATEGORY (ID_CATEGORY, CATEGORY) VALUES (1,'TOWARZYSKIE/ SOCIAL');
INSERT INTO ADVERTAPP.CATEGORY (ID_CATEGORY, CATEGORY) VALUES (2,'MEBLE/ FURNITURE');
INSERT INTO ADVERTAPP.CATEGORY (ID_CATEGORY, CATEGORY) VALUES (3,'ELEKTRONIKA/ ELECTRONICS');
INSERT INTO ADVERTAPP.CATEGORY (ID_CATEGORY, CATEGORY) VALUES (4,'SAMOCHÓD/ CAR');
INSERT INTO ADVERTAPP.CATEGORY (ID_CATEGORY, CATEGORY) VALUES (5,'NIERUCHMOŚCI/ REAL ESTATE');

INSERT INTO ADVERTAPP.ACCOUNT (ID_ACCOUNT,ACTIVATIONDATE,ACTIVE,EMAIL,FIRSTNAME,LASTLOGINDATE,LASTNAME,PASSWORD,VERSION,ID_TYPE) 
VALUES (1,CURRENT_TIMESTAMP,1,'advertapp@tlen.pl','Andrzej',CURRENT_TIMESTAMP,'Pierwszy','abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab',1,1);--password: haslo

INSERT INTO ADVERTAPP.ACCOUNT (ID_ACCOUNT,ACTIVATIONDATE,ACTIVE,EMAIL,FIRSTNAME,LASTLOGINDATE,LASTNAME,PASSWORD,VERSION,ID_TYPE) 
VALUES (2,CURRENT_TIMESTAMP,1,'advertapp@interia.pl','Marcin',CURRENT_TIMESTAMP,'Drugi','abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab',1,1);--password: haslo

INSERT INTO ADVERTAPP.ACCOUNT (ID_ACCOUNT,ACTIVATIONDATE,ACTIVE,EMAIL,FIRSTNAME,LASTLOGINDATE,LASTNAME,PASSWORD,VERSION,ID_TYPE) 
VALUES (3,CURRENT_TIMESTAMP,1,'gmail@gmail.com','Administrator',CURRENT_TIMESTAMP,'Główny','abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab',1,2);--password: haslo

INSERT INTO ADVERTAPP.ACCOUNT (ID_ACCOUNT,ACTIVATIONDATE,ACTIVE,EMAIL,FIRSTNAME,LASTLOGINDATE,LASTNAME,PASSWORD,VERSION,ID_TYPE) 
VALUES (4,CURRENT_TIMESTAMP,1,'fmail@fmail.com','Marcin',CURRENT_TIMESTAMP,'Nowy','abe31fe1a2113e7e8bf174164515802806d388cf4f394cceace7341a182271ab',1,1);--password: haslo

-- Użytkownik 1
-- Status UNRESERVABLE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE, ADVERTEXPIRYDATE, DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_hour, 73, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym I ogłoszeniu',824.55,'Tytuł ogłoszenia I',1,1,1,2);

-- Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (2,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_minute, 1, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym II ogłoszeniu',134.65,'Tytuł ogłoszenia II',1,1,2,1);

--Status RESERVED
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,ADVERTRESERVEDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_USERBUYER,ID_CATEGORY,ID_STATUS)
VALUES (3,CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_day, 2, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -10, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym III ogłoszeniu',54.78,'Tytuł ogłoszenia III',1,1,2,4,3);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (4,CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_minute, 10, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym IV ogłoszeniu',873.78,'Tytuł ogłoszenia IV',1,1,5,1);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (5,CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_hour, 2, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym V ogłoszeniu',3.99,'Tytuł ogłoszenia V',1,1,3,1);

-- Użytkownik 2
-- Status UNRESERVABLE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE, ADVERTEXPIRYDATE, DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (6,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_hour, 73, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym I ogłoszeniu',84.65,'Tytuł ogłoszenia I',1,2,1,2);

-- Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (7,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_day, 7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym II ogłoszeniu',134.65,'Tytuł ogłoszenia II',1,2,5,1);

--Status RESERVED
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,ADVERTRESERVEDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_USERBUYER,ID_CATEGORY,ID_STATUS)
VALUES (8,CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_day, 10, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_hour, -169, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym III ogłoszeniu',4.7,'Tytuł ogłoszenia III',1,2,1,3,3);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (9,CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_day, 2, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym IV ogłoszeniu',73.78,'Tytuł ogłoszenia IV',1,2,4,1);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (10,CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_day, 1, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym V ogłoszeniu',0.89,'Tytuł ogłoszenia V',1,2,2,1);

-- Użytkownik 3
-- Status UNRESERVABLE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE, ADVERTEXPIRYDATE, DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (11,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_day, 7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
'To jest przykładowy opis znajdujący się w przykładowym I ogłoszeniu',4,'Tytuł ogłoszenia I',1,3,1,2);

-- Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (12,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,CAST({ fn timestampadd(sql_tsi_minute, 5, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym II ogłoszeniu',134.65,'Tytuł ogłoszenia II',1,3,3,1);

--Status RESERVED
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,ADVERTRESERVEDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_USERBUYER,ID_CATEGORY,ID_STATUS)
VALUES (13,CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_hour, -12, CURRENT_TIMESTAMP) } AS TIMESTAMP),CURRENT_TIMESTAMP,'To jest przykładowy opis znajdujący się w przykładowym III ogłoszeniu',50.5,'Tytuł ogłoszenia III',1,3,1,4,3);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (14,CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -7, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_hour, 1, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym IV ogłoszeniu',3.89,'Tytuł ogłoszenia IV',1,3,2,1);

--Status ACTIVE
INSERT INTO ADVERTAPP.ADVERT (ID_ADVERT,ADVERTADDDATE,ADVERTEDITDATE,ADVERTEXPIRYDATE,DESCRIPTION,PRICE,TITLE,VERSION,ID_ACCOUNT,ID_CATEGORY,ID_STATUS)
VALUES (15,CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),CAST({ fn timestampadd(sql_tsi_day, -14, CURRENT_TIMESTAMP) } AS TIMESTAMP),
CAST({ fn timestampadd(sql_tsi_day, 3, CURRENT_TIMESTAMP) } AS TIMESTAMP),'To jest przykładowy opis znajdujący się w przykładowym V ogłoszeniu',5.79,'Tytuł ogłoszenia V',1,3,5,1);

