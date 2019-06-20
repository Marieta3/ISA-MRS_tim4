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
Aplikacija omogućava CRUD operacije sa svim entitetima unutar paketa "com.ISATim4.WebAppSpringAirport.model". 
## Deployment aplikacije
Za deployment aplikacije, korišćen je Heroku servis. 
## Verzija 
0.0.1-SNAPSHOT
## Developeri
 1. Marieta Rakoš
 2. Arpad Varga Šomođi
 3. Nikola Maksimović
## Licenca i autorska prava
©Marieta Rakoš, ©Arpad Varga Šomođi i ©Nikola Maksimović, Fakultet tehničkih nauka, Novi Sad, smer Softversko inženjerstvo i informacione tehnologije.
