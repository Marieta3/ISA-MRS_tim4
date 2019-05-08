insert into hotel(naziv,adresa,ocena,opis) values ('Sole Mio','Rome, Roman Road 22',4.8,'Description');
insert into hotel(naziv,adresa,ocena,opis) values ('The Plaza Hotel','Dublin, Belgard Road 4',3.2,'Description');
insert into hotel(naziv,adresa,ocena,opis) values ('Butlers Townhouse','Dublin, 44 Lansdowne Road',4.5,'Description');
insert into hotel(naziv,adresa,ocena,opis) values ('The Plaza Hotel','Novi Sad, Ilije Ognjanovica 16',2.0,'Description');
insert into hotel(naziv,adresa,ocena,opis) values ('Hotel Park','Novi Sad, Bulevar Jase Tomica 15,',4.6,'Description');
insert into hotel(naziv,adresa,ocena,opis) values ('Prenociste Stojic Novi Sad','Novi Sad, Partizanska 47',3.1,'Description');

insert into usluga(id,cena,opis,hotel_id) values (1,100,'ventilator',1);
insert into usluga(id,cena,opis,hotel_id) values (2,56,'opisanie',1);
insert into usluga(id,cena,opis,hotel_id) values (3,999,'wifi',1);
insert into usluga(id,cena,opis,hotel_id) values (4,600,'wc',1);
insert into usluga(id,cena,opis,hotel_id) values (5,566,'hrana',1);
insert into usluga(id,cena,opis,hotel_id) values (2,200,'kupatilo',2);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id, rezervisana) values (1,3,9.9,'nekiOpisss',1, false);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id, rezervisana) values (2,5,8.8,'nekiOpisss2',2, false);

insert into avio_kompanija(naziv,adresa,opis, ocena) values ('United Airlines','Chicago, 5th Avenue 20','Description', 4.5);
insert into avio_kompanija(naziv,adresa,opis, ocena) values ('Turkish Airlines','Istanbul, Asd 20','Description', 3.6);
insert into avio_kompanija(naziv,adresa,opis, ocena) values ('Frontier Airlines','Boston, 3th Street 15','Description', 2.6);
insert into avio_kompanija(naziv,adresa,opis, ocena) values ('Air Serbia','Belgrade, JNA 25','Description', 4.48);
insert into avio_kompanija(naziv,adresa,opis, ocena) values ('FlySafe','Novi Sad, Partizanska 70','Description', 4.33);
insert into avio_kompanija(naziv,adresa,opis, ocena) values ('Air Croatia','Zagreb, Bulevar Oslobodjenja 15','Description', 3.98);


insert into rentacar(naziv,adresa,opis,ocena) values('Drive safe', 'Novi Sad, Futoska 32','Test1',4.8);
insert into rentacar(naziv,adresa,opis,ocena) values('Mitsubishi rent', 'Temerin, Asd street 2','Test2',4.2);
insert into rentacar(naziv,adresa,opis,ocena) values('TestDrive.eu', 'Novi sad, Asd 66','Test3',4.4);
insert into rentacar(naziv,adresa,opis,ocena) values('FoglaljonMost', 'Dubai, Oil street 4','Test1',4.3);
insert into rentacar(naziv,adresa,opis,ocena) values('Lamborghini Test Drive', 'Kosut Lajosa 4','Test2',2.8);
insert into rentacar(naziv,adresa,opis,ocena) values('Rent-a-car Belgrade', 'Belgrade, Novosadska 50.','Test3',3.8);


insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 1', 'Drive safe filijala 1','+1-202-555-0114',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 2', 'Drive safe filijala 2','+1-202-555-0128',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 3', 'Drive safe filijala 3','+1-202-555-0115',1);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Temerin, Adresa 1', 'Mitsubishi rent filijala 1','+1-202-555-0222',2);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Temerin, Adresa 2', 'Mitsubishi rent filijala 2','+1-202-545-0128',2);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 1', 'TestDrive.eu 1','+1-202-555-6666',3);
insert into filijala(adresa,opis,telefon,rentacar_id) values('Novi Sad, Adresa 2', 'TestDrive.eu 2','+1-203-555-0128',3);

--insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id) values(5,2000,2002,'Astra','Opel','NS-XXX-05',1);
--insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id) values(5,3000,2008,'Corolla','Toyota','NS-XXX-06',1);
--insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id) values(5,5000,2004,'Xtrail','Nissan','NS-XXX-07',1);
--insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id) values(8,500,1999,'Vectra','Opel','NS-XXX-08',2);
--insert into vozilo(broj_mesta, cena, godina, model, proizvodjac, tablica, filijala_id) values(5,2000,2010,'Civic','Honda','NS-XXX-09',2);



--Milan: trenutno imate jednog korisnika kojeg sam oznacio kao 'sis' sistemskog, nije vezan za hotel, rent-a-car ili aviokompaniju i zato je prosledjeno null za te kolone
--Milan: korisnik sme imati samo jednu rolu!

insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,enabled) values (1,'Nikola','sis','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','123@gmail.com','Maksimovic',true,true,null,null,null,'sis',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga, slika,enabled) values (2,'Marieta','hotel','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','lollipop@gmail.com','Rakos',false,true,null,null,1,'hotel', '../slike/marieta.jpg',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,enabled) values (3,'Arpad','rent','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','arpadVS@gmail.com','Varga Somodji',false,true,1,null,null,'rent',true);
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rentacar_id,avio_kompanija_id,hotel_id,uloga,slika,enabled) values (4,'Sreten','avio','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','bozic.sreten@gmail.com','Bozic',false,true,null,4,null,'avio','../slike/Airplane-1.png',true);
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


