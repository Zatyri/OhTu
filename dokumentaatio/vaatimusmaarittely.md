# Pienkiinteistön huolto ohjelma - Vaatimusmäärittely
## Sovelluksen tarkoitus
Sovellus tarjoaa mahdollisuuden luoda ja ylläpitää pienkiinteistön huoltosuunnitelmaa. Huoltosuunnitelman avulla on helppo seurata milloin tietty huoltotoimenpide on tehtävä
ja pitää kirjaa tehdyistä huolloista. Huoltosuunnitelma määräytyy tehtävien avulla, joita voidaan lisätä tarpeen mukaan.
Tehtävät voivat olla kertaluontoisia tai toistuvia. Kertaluontoiset tehtävät ovat huoltotoimenpitäitä joita ei toisteta säännöllisesti, kuten esimerkiksi kattoremontti. 
Toistuvat tehtävät ovat jatkuvaa huoltoa vaativia tehtäviä, kuten ilmanvaihtokoneen suodattimien vaihto, nuohous tai terassin öljyäminen. Toistuviin tehtäviin voi asettaa aikaväli 
kuinka tiheästi huoltotoimenpiteet kuuluu suorittaa, ja ohjelma muistuttaa käyttäjää tulevista huoltotoimenpideistä. 
Ideana on että käyttäjä merkitsee huoltotoimenpiteen suoritetuksi sen jälkeen, jolloin siitä jää jälki milloin, ja kuinka usein jokin toimenpide on tehty. 
Mikäli ammattilainen on tehnyt huoltotoimenpiteen voidaan se mainita ja ladata jokin todistus tästä, esimerkiksi lasku tai muu dokumentti.
Ohjelma antaa mahdollisuuden käyttäjälle luoda yhteenvedon suoritetuista huoltotoimenpiteistä, ja käyttää tätä esimerkiksi kiinteistön myynnin yhteydessä.

## Käyttäjät
Sovelluksen työpöytä versiossa ei tule olemaan erillisiä käyttäjiä. Ideana on että ohjelma voidaan siirtää kiinteistön myynnin yhteydessä uudelle omistajalle.

## Käyttöliittymäluonnos
Sovellus rakennetaan siten, että ylhäällä on navigointipaneeli jossa voidaan vaihtaa eri näkymien välillä.
Näkymät ovat:
- File, jossa voi luoda uusi huoltosuunnitelma tai ladata olemassa oleva
- Edit, jossa voidaan lisätä uusia huoltotoimenpiteitä ja muokata olemassa olevia
- View, jossa voidaan katsella tulevia huoltotoimenpiteitä ja kuitata ne tehdyksi
- Report, jossa voidaan luoda raportti tehdyistä huoltotoimenpiteistä

![rough plan of UI](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/roughPlanOfUI.png)


## Perusversion tarjoama toiminnallisuus
- käyttäjä voi 
	- luoda uuden huoltosuunnitelman tai ladata olemassa oleva
	- lisätä kertaluontoisia ja toistuvia huoltotoimenpiteitä huoltosuunnitelmaansuunnitelmaan
	- kuitata huoltotoimenpiteet tehdyksi
	- listata tulevia huoltotoimenpiteitä
	- luoda yhteenveto suoritetuista huoltotoimenpiteistä
- luodaan valmiita huoltotoimenpideitä
	
## Jatkokehitysideoita
Nämä toteutetaan mikäli aikaa on
- käyttäjä voi
	- lisätä suoritettuihin huoltotoimenpiteisiin dokumentteja
	- luoda yhteenvedosta pdf tiedosto
	- saada työpöytä ilmoituksia huoltotoimenpiteistä jotka pitää suorittaa nyt
	- saada sähköposti ilmoitus huoltotoimenpiteistä jotka pitää suorittaa nyt
- yhdistää sovellus rajapintaan jossa huoltoja tarjoavia yrityksiä (onko sellaista?)
	- sovellus tarjoaisi ajankohtaisiin huoltotoimenpiteisiin huoltoja tekeviä yrityksiä lähialueelta
