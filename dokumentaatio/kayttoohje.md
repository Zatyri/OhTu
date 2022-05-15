# Käyttöohje

Lataa tiedosto [HuoltoSovellus-1.0-SNAPSHOT.jar](https://github.com/Zatyri/ot-harjoitustyo/releases/tag/viikko6)

## Konfigurointi

Ohjelma ei vaadi erillistä konfigurointia. Ohjelma käyttää tietokantaa tallennukseen. Ensimmäisen käynnistyksen yhteydessä luo ohjelma tietokannan samaan kansioon kuin .jar tiedosto.
Tietokanta tiedoston nimi on MaintenanceFileDB


## Ohjelman käynnistäminen

Kaksoisklikkaa HuoltoSovellus-1.0-SNAPSHOT.jar tiedostoa.

## Ohjelman logiikka

Ohjelma koostuu neljästä näkymästä. File, Edit, View ja Report. Ohjelma käynnistyy aina View näkymään ja lataa tietokannasta oletus huolto-ohjelman (Maintenance File).
Jos oletus huolto-ohjelmaa (Maintenance File) ei löydy tietokannasa (esimerkiksi ensimmäisen käynnistyksen yhteydessä), luo ohjhelma pohjalla tyhjän huolto-ohjelman.

### File

File näkymässä voi käyttäjä luoda, valita ja muokkaa huolto-ohjelmia (Maintenance File). Huolto-ohjelma sisältää kaikki siihen liittyvät huoltotoimenpiteet.
File näkymässä tallennat myös tehdyt muutokset. HUOM! Ilman erillistä tallentamista häviävät tehdyt muutokset kun ohjelma suljetaan.

### Edit

Edit näkymässä voit lisätä huolto-ohjelmaan, muokata ja poistaa niitä. 

### View

View näkymä näyttää nykyisen kuukauden huoltotoimenpiteet. Nuolilla voit siirtyä edellisiin tai seuraaviin kuukausiin.
View näkymän kautta voit merkata huoltotoimenpide tehdyksi.
View näkymä näyttää oikealla ei suoritettuja huoltotoimenpiteitä joiden eräpäivä on mennyt

### Report

Report näkymässä voit luoda yhteenvedon tehdyistä huolloista pdf muodossa.


