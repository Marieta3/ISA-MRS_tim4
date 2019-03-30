insert into hotel(id,adresa,naziv,ocena,opis) values (1000,'nekaAdresa','nekiNaziv',10,'nekiOpis');
insert into hotel(id,adresa,naziv,ocena,opis) values (2000,'nekaAdresa2','nekiNaziv2',11,'nekiOpis2');
insert into usluga(id,cena,opis,hotel_id) values (1,100,'opis',1000);
insert into usluga(id,cena,opis,hotel_id) values (2,200,'opis2',2000);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id) values (1,3,9.9,'nekiOpisss',1000);
insert into soba(id,broj_kreveta,ocena,opis,hotel_id) values (2,5,8.8,'nekiOpisss2',1000);

