insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('Sole Mio','Egypt, Alexandria',4.8,'Description', 31.214535,29.945663);
insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('The Plaza Hotel','Dublin, Belgard Road 4',3.2,'Description',31.214535,29.945663);
insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('Butlers Townhouse','Dublin, 44 Lansdowne Road',4.5,'Description',31.214535,29.945663);
insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('The Plaza Hotel','Novi Sad, Ilije Ognjanovica 16',2.0,'Description',31.214535,29.945663);
insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('Hotel Park','Novi Sad, Bulevar Jase Tomica 15,',4.6,'Description',31.214535,29.945663);
insert into hotel(naziv,adresa,ocena,opis, coord1, coord2) values ('Prenociste Stojic Novi Sad','Novi Sad, Partizanska 47',3.1,'Description',31.214535,29.945663);

insert into usluga(id,cena,opis,hotel_id) values (1,100,'ventilator',1);
insert into usluga(id,cena,opis,hotel_id) values (2,56,'opisanie',1);
insert into usluga(id,cena,opis,hotel_id) values (3,999,'wifi',1);
insert into usluga(id,cena,opis,hotel_id) values (4,600,'wc',1);
insert into usluga(id,cena,opis,hotel_id) values (5,566,'hrana',1);
insert into usluga(id,cena,opis,hotel_id) values (6,200,'kupatilo',2);

insert into soba(id,broj_kreveta,ocena,opis,hotel_id, rezervisana, cena) values (1,3,9.9,'cista soba',1, false, 100);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id, rezervisana, cena) values (2,5,8.8,'dobra',2, false, 200);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id, rezervisana, cena) values (3,3,9.8,'prelepo',3, false, 400);

insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('United Airlines','Egypt, Alexandria','Description', 4.5, 31.214535,29.945663);
insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('Turkish Airlines','Istanbul, Asd 20','Description', 3.6, 31.214535,29.945663);
insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('Frontier Airlines','Boston, 3th Street 15','Description', 2.6, 31.214535,29.945663);
insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('Air Serbia','Belgrade, JNA 25','Description', 4.48, 31.214535,29.945663);
insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('FlySafe','Novi Sad, Partizanska 70','Description', 4.33, 31.214535,29.945663);
insert into avio_kompanija(naziv,adresa,opis, ocena, coord1, coord2) values ('Air Croatia','Zagreb, Bulevar Oslobodjenja 15','Description', 3.98, 31.214535,29.945663);


insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('Drive safe', 'Egypt, Alexandria','Test1',4.8, 31.214535,29.945663);
insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('Mitsubishi rent', 'Temerin, Asd street 2','Test2',4.2, 31.214535,29.945663);
insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('TestDrive.eu', 'Novi sad, Asd 66','Test3',4.4, 31.214535,29.945663);
insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('FoglaljonMost', 'Dubai, Oil street 4','Test1',4.3, 31.214535,29.945663);
insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('Lamborghini Test Drive', 'Kosut Lajosa 4','Test2',2.8, 31.214535,29.945663);
insert into rentacar(naziv,adresa,opis,ocena, coord1, coord2) values('Rent-a-car Belgrade', 'Belgrade, Novosadska 50.','Test3',3.8, 31.214535,29.945663);


insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 1', 'Drive safe filijala 1','+1-202-555-0114',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 2', 'Drive safe filijala 2','+1-202-555-0128',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 3', 'Drive safe filijala 3','+1-202-555-0115',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Temerin, Adresa 1', 'Mitsubishi rent filijala 1','+1-202-555-0222',2);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Temerin, Adresa 2', 'Mitsubishi rent filijala 2','+1-202-545-0128',2);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 1', 'TestDrive.eu 1','+1-202-555-6666',3);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 2', 'TestDrive.eu 2','+1-203-555-0128',3);

insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id, rezervisano) values(5,2000,2002,'Astra','Opel','NS-XXX-05',1, false);
insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id, rezervisano) values(5,3000,2008,'Corolla','Toyota','NS-XXX-06',1, false);
insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id, rezervisano) values(5,5000,2004,'Xtrail','Nissan','NS-XXX-07',1, false);
insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id, rezervisano) values(8,500,1999,'Vectra','Opel','NS-XXX-08',2, false);
insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id, rezervisano) values(5,2000,2010,'Civic','Honda','NS-XXX-09',2, false);



--Milan: trenutno imate jednog korisnika kojeg sam oznacio kao 'sis' sistemskog, nije vezan za hotel, rent-a-car ili aviokompaniju i zato je prosledjeno null za te kolone
--Milan: korisnik sme imati samo jednu rolu!

insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,enabled) values (1,'Nikola','sis','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','123@gmail.com','Maksimovic',true,true,null,null,null,'sis',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga, slika,enabled) values (2,'Marieta','hotel','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','lollipop@gmail.com','Rakos',false,true,null,null,1,'hotel', '../slike/marieta.jpg',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,enabled) values (3,'Arpad','rent','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','arpadVS@gmail.com','Varga Somodji',false,true,1,null,null,'rent',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,slika,enabled) values (4,'Sreten','avio','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','bozic.sreten@gmail.com','Bozic',false,true,null,1,null,'avio','../slike/Airplane-1.png',true);
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (5,'Mika','Mikic','user1','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+1@gmail.com',true,'user',true,'Asd Street 1,NY','Tel 1');
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (6,'Milan','Varga','user2','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+2@gmail.com',true,'user',true,'Asd Street 2,NY','Tel 2');
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (7,'Tom','Hanks','user3','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+3@gmail.com',true,'user',true,'Asd Street 3,NY','Tel 3');
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (8,'Brad','Pitt','user4','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+4@gmail.com',true,'user',true,'Asd Street 4,NY','Tel 4');
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (9,'Felix','Smith','user5','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+5@gmail.com',true,'user',true,'Asd Street 5,NY','Tel 5');
insert into korisnik(id,ime,prezime,korisnicko_ime,lozinka,mail,ulogovan_prvi_put,uloga,enabled,adresa,telefon) values (10,'Ragnar','Lothbrok','user6','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','tirmann25+6@gmail.com',true,'user',true,'Asd Street 6,NY','Tel 6');
--Milan: trenutno imate samo dve role, treba ubaciti role za razlicite tipove adm3ina
INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_HOTEL');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_AVIO');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_RENT');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 3);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (3, 5);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (4, 4);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (5, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (6, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (7, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (8, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (9, 1);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (10, 1);

INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-14',true,5,6);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-14',true,5,7);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-14',true,5,8);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-12',true,5,9);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-10',true,7,8);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (true,'2019-05-11',true,9,6);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (false,'2019-05-12',false,10,5);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (false,'2019-05-12',false,10,6);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (false,'2019-05-12',false,10,7);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (false,'2019-05-12',false,8,6);
INSERT INTO PRIJATELJSTVO (accepted,datum,reacted,receiver_id,sender_id) VALUES (false,'2019-05-12',false,8,9);

INSERT INTO DESTINACIJA (id,adresa,slika) VALUES (1,'Beograd','../slike/Airplane-1.png');
INSERT INTO DESTINACIJA (id,adresa,slika) VALUES (2,'London','../slike/Airplane-1.png');
INSERT INTO DESTINACIJA (id,adresa,slika) VALUES (3,'Paris','../slike/Airplane-1.png');
INSERT INTO DESTINACIJA (id,adresa,slika) VALUES (4,'Dubai','../slike/Airplane-1.png');
INSERT INTO DESTINACIJA (id,adresa,slika) VALUES (5,'Moscow','../slike/Airplane-1.png');

INSERT INTO AVION (id,model,broj_kolona,broj_redova,broj_redovaec,broj_redovabc,broj_redovafc) VALUES (1,'Boeing 747',6,6,3,2,1);
INSERT INTO AVION (id,model,broj_kolona,broj_redova,broj_redovaec,broj_redovabc,broj_redovafc) VALUES (2,'Boeing 737',7,7,4,2,1);
INSERT INTO AVION (id,model,broj_kolona,broj_redova,broj_redovaec,broj_redovabc,broj_redovafc) VALUES (3,'Airbus A380',9,9,5,3,1);
INSERT INTO AVION (id,model,broj_kolona,broj_redova,broj_redovaec,broj_redovabc,broj_redovafc) VALUES (4,'Douglas DC-8',8,8,3,3,2);

INSERT INTO LET (id,pocetna_destinacija,krajnja_destinacija,vreme_polaska,vreme_dolaska,duzina_putovanja,model,broj_redova, broj_kolona,broj_redovaEC,broj_redovaBC,broj_redovaFC) VALUES(1,'Belgrade','Paris','2019-05-14 15:45:32','2019-05-14 18:45:32',3,'Airbus A380',7,7,3,3,1);
INSERT INTO LET (id,pocetna_destinacija,krajnja_destinacija,vreme_polaska,vreme_dolaska,duzina_putovanja,model,broj_redova, broj_kolona,broj_redovaEC,broj_redovaBC,broj_redovaFC) VALUES(2,'Paris','Belgrade','2019-05-18 16:40:22','2019-05-18 18:40:22',3,'MiG 21',5,5,3,1,1);
INSERT INTO LET (id,pocetna_destinacija,krajnja_destinacija,vreme_polaska,vreme_dolaska,duzina_putovanja,model,broj_redova, broj_kolona,broj_redovaEC,broj_redovaBC,broj_redovaFC) VALUES(3,'London','Belgrade','2019-05-20 14:00:00','2019-05-20 18:00:00',3,'Boeing 747',9,9,5,3,1);
INSERT INTO LET (id,pocetna_destinacija,krajnja_destinacija,vreme_polaska,vreme_dolaska,duzina_putovanja,model,broj_redova, broj_kolona,broj_redovaEC,broj_redovaBC,broj_redovaFC) VALUES(4,'Dubai','Belgrade','2019-05-21 19:00:00','2019-05-21 00:00:00',3,'Boeing 737',8,8,4,3,1);
INSERT INTO LET (id,pocetna_destinacija,krajnja_destinacija,vreme_polaska,vreme_dolaska,duzina_putovanja,model,broj_redova, broj_kolona,broj_redovaEC,broj_redovaBC,broj_redovaFC) VALUES(5,'Belgrade','Dubai','2019-05-31 19:00:00','2019-05-31 00:00:00',3,'Boeing 737',8,8,4,3,1);

/*INSERT INTO REZERVACIJA(id,aktivna_rezervacija,datum_rezervacije, cena) VALUES (1,true,'2019-05-14 15:45:32', 100);
INSERT INTO REZERVACIJA_SOBA(rezervacija_id,soba_id) VALUES (1,1);

INSERT INTO REZERVACIJA(id,aktivna_rezervacija,datum_rezervacije, cena) VALUES (2,true,'2019-05-14 18:00:00', 200);
INSERT INTO REZERVACIJA_SOBA(rezervacija_id,soba_id) VALUES (2,2);

INSERT INTO REZERVACIJA(id,aktivna_rezervacija,datum_rezervacije, cena) VALUES (3,true,'2019-05-14 15:45:32', 400);
INSERT INTO REZERVACIJA_SOBA(rezervacija_id,soba_id) VALUES (3,3);*/

