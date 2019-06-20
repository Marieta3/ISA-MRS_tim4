# ISA-MRS_tim4 
Na ovom github repozitorijumu, nalazi se projekat iz predmeta ISA i MRS, koji se slušaju na 3. godini, smera Softversko inženjerstvo i informacione tehnologije.
## WebAppSpringAirport
Ovo je web aplikacija čiji je cilj rezervisanje letova na različitim destinacijama, kao i rezervisanje rent-a-car automobila i hotela. 
## Korišćene tehnologije
Aplikacija na backend-u koristi Java Spring Boot i Maven, a na frontend-u koristi JavaScript, HTML i CSS. Aplikacija, takođe, koristi Spring Boot Security i radi sa MySql bazom podataka. Omogućen je i konkurentni pristup pri rezervaciji.
## Korišćene biblioteke
1. Spring Boot 2.1.3. RELEASE
2. java.util
3. org.springframework
4. javax.persistence
5. com.fasterxml.jackson 
6. ...
## Pokretanje aplikacije
Za pokretanje aplikacije potrebni su Eclipse, Maven, Apache Tomcat 9.0.21 i instalirana Java sa svojim SDK i JDK. Prvo pokretanje(build) se radi na sledeći način: desnim klikom na projekat i odabirom opcije "Run as" i onda "Maven build ..", otvara se prozor u čije polje "Goals" se unosi "package" i pritiskanjem dugmeta "Run", pokrenuće se build aplikacije. Nakon toga je potrebno otići u paket "com.ISATim4.WebAppSpringAirport" i odabrati klasu "WebAppSpringAirport" i onda projekat pokrenuti kao Java aplikaciju. Nakon završenog pokretanja, u Web Browser-u ukucati localhost:8080 i prikazaće se početna strana aplikacije.
## Osobine aplikacije
Aplikacija omogućava CRUD operacije nad svim entitetima unutar paketa "com.ISATim4.WebAppSpringAirport.model". 
## Deployment aplikacije
Za deployment aplikacije, korišćen je Heroku servis. Postupak za deployment je sledeći: potrebno je prvo napraviti nalog na sajtu www.heroku.com, nakon toga potrebno je skinuti heroku CLI, onda u korenskom direktorijumu, otvoriti komandnu liniju i kucati sledeće komande: heroku create(kreira se prazan heroku projekat), onda napraviti heroku git repozitorijum naredbom "git init", onda komandama "git add", "git commit" i "git push heroku master" dodati sve fajlove iz korenskog repozitorijuma na heroku git. Pokretanjem poslednje navedene komande, heroku će posle dodavanja na repozitorijum sam pokrenuti Spring Boot aplikaciju, nakon čijeg pokretanja ćete dobiti log(da li je uspešan build ili ne) i link, čijim kopiranjem u browser dobijate početnu stranu Vaše deploy-ovane aplikacije na Heroku server. Link ka aplikaciji na heroku serveru je: https://webspringappairport.herokuapp.com/.
## Verzija 
0.0.1-SNAPSHOT
## Developeri
 1. Marieta Rakoš
 2. Arpad Varga Šomođi
 3. Nikola Maksimović
## Licenca i autorska prava
©Marieta Rakoš, ©Arpad Varga Šomođi i ©Nikola Maksimović, Fakultet tehničkih nauka, Novi Sad, smer Softversko inženjerstvo i informacione tehnologije.
