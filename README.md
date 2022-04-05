# Ohjelmistotekniikka, kevät 2022

This repository is for course *TKT20002* at **Helsinki University**

## Laskarit
# Viikko 1
[komentorivi.txt](https://github.com/Zatyri/ot-harjoitustyo/blob/master/laskarit/viikko1/komentorivi.txt)

[gitlog.txt](https://github.com/Zatyri/ot-harjoitustyo/blob/master/laskarit/viikko1/gitlog.txt)

# Viikko 2
[testikattavuus.png](https://github.com/Zatyri/ot-harjoitustyo/blob/master/laskarit/viikko2/testikattavuus.png)

# Viikko 3

[luokkaJaSekvenssikaaviot](https://github.com/Zatyri/ot-harjoitustyo/blob/master/laskarit/viikko3/ClassAndSequenceDiagram.md)

## Harjoitustyön dokumetaatio
[vaatimusmaarittely.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[tuntikirjanpito.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[changelog.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/changelog.md)

## Komentorivitoiminnot

Ohjelman käynnistys
```
mvn compile exec:java -Dexec.mainClass=pienkiinteistohuoltosovellus.ui.UserInterface
```

# Testaus

Ohjelman testaus
```
mvn test
```

Testikattavuusraportti
```
mvn jacoco:report
```

