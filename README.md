# Ohjelmistotekniikka, kevät 2022

This repository is for course *TKT20002* at **Helsinki University**

## Harjoitustyön dokumetaatio
[vaatimusmaarittely.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[tuntikirjanpito.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[changelog.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/changelog.md)

[arkkitehtuuri.md](https://github.com/Zatyri/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

Mene ensin kansioon "Huoltosovellus"
```
cd Huoltosovellus/
```

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

Checkstyle
```
mvn jxr:jxr checkstyle:checkstyle
```

