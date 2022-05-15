#Testausdokumentti

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Testausta varten on luotu lukuisia sovelluslogiikkaa testaavia automaattitestejä. MaintenanceFile ja MaintenanceTask luokille on tehty yksikkötestejä 
jotka testaavat luokkien metodeja. MaintenanceFileService luokan eri metodit on myös kattavasti testattu. MaintenanceFileService luokan metodit kutsuvat 
muiden luokkien metodeja laajasti, joten MaintenanceFileService luokalla suoritetut testit testaavat kattavasti koko ohjelman toimivuutta. 

Harmillisesti jäi MaintenanceFileService luokan metodi getTasksFromDatabase() jacocon mielstä suurimmalta osin testaamatta. Jacoco ei "päässyt" if lauseen
sisälle vaikka metodille tehty testi onnistuu ja sen onnistuminen on selkeästi riippuvainen siitä suorittaako metodi koodia if lauseen sisälle. 
Tämä vaikutti merkittävästi jacocon testikattavuuteen, sillä kyseinen metodi on varsin laaja. 

### Tietokantatestaus

Tietokantaa on testattu muistissa sijaitsevan tietokannan avulla.

### Testikattavuus

Käyttöliittymää lukuunottamatto on ohjelman rivikattavuus 76% ja haarautumiskattavuus 63%. Testikattavuus jäi harmillisen alaiseksi MaintenanceFileService luokassa
sijaitsevan "puutteen" vuoksi

## Järjestelmätestaus

Ohjelmaa on testattu manuaalisesti sekä Linux että Windows ympäristössä. 
Testit on suoritettu käyttämällä ohjelmaa käyttöohjeiden mukaisesti sekä myös yrittämällä "rikkoa" ohjelmaa.
Kaikki vaatimusdokumentissa oleva on testattu.

## Puutteet testauksessa

Automaatti testeissä ei ole paljoa virhetilanteita käsitteleviä testejä.



