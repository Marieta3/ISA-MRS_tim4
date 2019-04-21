insert into hotel(id,adresa,naziv,ocena,opis) values (1000,'nekaAdresa','nekiNaziv',10,'nekiOpis');
insert into hotel(id,adresa,naziv,ocena,opis) values (2000,'nekaAdresa2','nekiNaziv2',11,'nekiOpis2');
insert into usluga(id,cena,opis,hotel_id) values (1,100,'opis',1000);
insert into usluga(id,cena,opis,hotel_id) values (2,200,'opis2',2000);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id) values (1,3,9.9,'nekiOpisss',1000);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id) values (2,5,8.8,'nekiOpisss2',1000);
insert into avio_kompanija(id,adresa,naziv,opis) values (111,'adresaa2','avio1','los');

insert into rentacar(naziv,adresa,opis) values('Drive safe', 'Novi Sad, Futoska 32','Test1');
insert into rentacar(naziv,adresa,opis) values('Mitsubishi rent', 'Temerin, Asd street 2','Test2');
insert into rentacar(naziv,adresa,opis) values('TestDrive.eu', 'Novi sad, Asd 66','Test3');
insert into rentacar(naziv,adresa,opis) values('FoglaljonMost', 'Dubai, Oil street 4','Test1');
insert into rentacar(naziv,adresa,opis) values('Lamborghini Test Drive', 'Kosut Lajosa 4','Test2');
insert into rentacar(naziv,adresa,opis) values('Rent-a-car Belgrade', 'Belgrade, Novosadska 50.','Test3');


--Milan: trenutno imate jednog korisnika kojeg sam oznacio kao 'sis' sistemskog, nije vezan za hotel, rent-a-car ili aviokompaniju i zato je prosledjeno null za te kolone
--Milan: korisnik sme imati samo jednu rolu!
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rent_a_car_id,avio_kompanija_id,hotel_id,uloga) values (1,'Nikola','sis','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','123@gmail.com','Maksimovic',false,true,null,null,null,'sis');
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rent_a_car_id,avio_kompanija_id,hotel_id,uloga) values (2,'Marieta','hotel','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','lollipop@gmail.com','Rakos',false,true,null,null,1000,'hotel');
insert into korisnik(id,ime,korisnicko_ime,lozinka,mail,prezime,predefinisani_admin,ulogovan_prvi_put,rent_a_car_id,avio_kompanija_id,hotel_id,uloga) values (3,'Arpad','rent','$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra','arpadVS@gmail.com','Varga Somodji',false,true,1,null,null,'rent');
--Milan: trenutno imate samo dve role, treba ubaciti role za razlicite tipove admina
INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO AUTHORITY (id, name) VALUES (3, 'ROLE_HOTEL');
INSERT INTO AUTHORITY (id, name) VALUES (4, 'ROLE_AVIO');
INSERT INTO AUTHORITY (id, name) VALUES (5, 'ROLE_RENT');

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (1, 2);
INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES (2, 3);


