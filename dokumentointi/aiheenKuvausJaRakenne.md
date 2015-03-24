**Aihe:** Tilastointijärjestelemä saliharjoitteluun
Ohjelmalla on tarkoitus kirjata harjoittelusession sisältö enemmän tai vähemmän yksityiskohtaisesti per suoritettu liike: lämmittelysarjat, työsarjat, RPE per sarja, lepoajat, kommentit subjektiivisesta olotilasta. Kirjatusta datasta ohjelma voi näyttää hyödyllisiä summia (kokonaistyövoluumi) ja keskiarvoja (työsarjojen keskimääräinen paino).

Ohjelman toinen tarkoitus on tallennetujen tilastojen tarkastelu taulukkojen, kaavioiden tai käyrien muodossa.

Käyttäjä valitsee kirjattavat muuttujat jokaiselle liikkeelle. Käyttäjä voi luoda listan usein käytetyistä muuttujista niin uutta liikettä luodessa ei tarvitse nähdä paljoa vaivaa. Tilastoitu data olisi tarkoitus olla yhteensopiva ohjelman kanssa vaikka tilastoituja muuttujia lisättäisiin.

Tiedostojärjestelmä tallentaa tilastot polkuun "Sessiot\LiikkeenNimi\SessionPaivamaara.txt", eli yhdestä sessiosta tulee yksi tiedosto per liike. Tiedostoo sisältää liikkeeseen liitettyjen sarjojen tiedot yksi sarja per rivi muodossa.

**Toiminnot:**
* uuden liikkeen luonti
  * valitse muuttujalista
* suorituksen kirjaus
  * liikkeen valinta
* tilastojen tarkastelu
