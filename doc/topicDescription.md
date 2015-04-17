## Aihe: Tilastointijärjestelemä saliharjoitteluun

### Kuvaus käyttötarkoituksista
Ohjelmalla on tarkoitus kirjata, ylläpitää ja tarkastella harjoittelusuorituksia. Pääasiassa tarkoitettu voimaharjoitteluun mutta pitäisi toimia yhtä hyvin kestävyysharjoitteluunkin; muuttujien nimet ovat erilaisia (paino vs etäisyys, toistot vs aika) mutta arvot silti lukuja. Harjoittelu koostuu yhdestä tai useammasta liikkeestä, joista jokaisessa suoritetaan yksi tai useampi sarja. "Sarja" voi olla tietty määrä toistoja (useimmat voimaharjoitteluliikkeet), tietty aika (staattiset pidot paikallaan esim. lankku tai liikkeessä esim. farmer's walk), tietty etäisyys (esim. farmer's walk, kelkan pusku). Ohjelmalla tilastoidaan niitä arvoja ja liikkeitä mitkä käyttäjä valitsee.

Ohjelmalla on kaksi käyttöpuolta: datan lisääminen ja datan lukeminen.

**Esimerkki käytöstä:**
Käyttäjä avaa ohjelman salilta tullessaan ja lisää tekemästään harjoittelusta kirjaamansa muistiinpanot ohjelmaan enemmän tai vähemmän yksityiskohtaisesti per suoritettu liike kirjaten esim. suoritetut sarjat mainiten lepoajat sarjojen välillä, RPE per sarja ja erotellen lämmittelyt, työvoluumin ja testisuoritukset; mitä treeniohjelmaa tämä sessio oli osa; mikä osa treeniohjelmasta tämä sessio oli; menikö sessio ohjelman mukaisesti; kommentit subjektiivisesta olotilasta per sarja tai koskien koko sessiota. Muulloin käyttäjä katselee ohjelmalla lisäämiään tilastoja valiten eri tapoja analysoida niitä.

### Datan lisääminen:
 Käyttäjä valitsee yksityiskohtaisuuden tason luomalla jokaiselle liikkeelle haluamansa muuttujat, joihin jokaiseen tallenetaan
* muuttujan nimi ja
* muuttujan tyyppi:
  1. numero,
  2. vapaaehtoinen (voi jättää valitsematta) tai pakollinen (pakko valita) kyllä/ei-vaihtoehto,
  3. vapaaehtoinen tai pakollinen lista itse luoduista vaihtoehdoista (enemmän kuin kaksi) tai
  4. teksti.
  Käyttäjä tallentaa listan tekemistään liikkeistä ja niiden muuttujista. Näistä hän valitsee sessiodataa kirjatessaan haluamansa. Data tallennetaan tiedostoihin; yksi per liike per salikerta tallentamaan suoritusdataa ja yksi tiedosto per liike tallentamaan liikedataa (nimi ja muuttujat). 

**Tiedostorakenne**
* sessiotiedostot: `tilastotKansio/liikkeenNimi/sessionPaivamaara.sessiopääte`
* liiketiedostot: `liikkeetKansio/liikkeenNimi.liikepääte`

Sessiotiedostolla ja liiketiedostolla on eri päätteet.

### Datan lukeminen:
Tallennetua dataa on tarkoitus tarkastella vähintäänkin taulukoiden ja mahdollisesti viiva- tai palkkikaavioiden tai näiden yhdistelmän muodossa. Tarkoitus olisi pystyä rakentamaan numero-muuttujista käyttäjän valitsemia johdannaisia: 
* laskutoimitus (summa/tulo/keskiarvo/maksimi/minimi) yhden muuttujan arvoista jokaisesta sarjasta, joka liitettäisiin sessioon;
* kahden tai useamman muuttujan laskutoimitus, joka lisättäisiin sarjaan yhtena arvona;
* näiden yhdistelmä.
Johdannaisten määrittelyssä voi ottaa huomioon vaihtoehto-muuttujat: otetaan laskutoimitus tietystä numero-muuttujasta vain niissä sarjoissa, joissa on valittu tietty vaihtoehto tietystä vaihtoehto-muuttujasta.
Johdannaisten määritelmät voisi tallentaa samaan tiedostoon liikkeen muuttujalistan kanssa. Kaavioiden tarkoitus olisi pääasiallisesti johdannaisten tarkastelu ja taulukot raakaa dataa varten.

Taulukkomuoto yhden liikkeen yhden session datan näyttämiselle olisi sessioon liitetyt johdannaiset ensimmäisellä rivillä, jonka jälkeen olisi yksi sarja per rivi, jossa muuttujien arvot (mukaanlukien johdannaiset) yksi per sarake. Usean session sisältävä taulukko olisi session päivämäärä rivin ensimmäisessä sarakkeessa, jonka jälkeen olisi sen session sarjat ja seuraavan session ensimmäinen rivi seuraisi edellisen viimeistä. Tämä voi olla melko iso taulukko jos sessioita on kymmeniä ja sarjoja aina muutama, joten yhdestä voisi näkyä vain yksi rivi, joka sisältää tiedot session johdannaisarvoista ja jonka voisi sitten avata näyttämään tiedot jokaisesta sarjasta.

### Käyttöliittymä
Käyttöliittymä koostuu pääikkunasta josta avataan sisempiä ikkunoita tabeihin käyttäen pääikkunan valikkoa. Avattavia ikkunoita on neljä: käyttöohjeet, liikkeiden muokkaus, sessioiden lisäys ja sessioiden tarkastelu. 

Liikkeiden muokkaus ja sessioiden lisäys -ikkuna sisältävät listan tallenetuista olevista liikkeistä. Valitsemalla listasta liikkeen avautuu ikkunaan joko liike- tai sessio-editori valitulle liikkeelle, riippuen missä ikkunassa ollaan. Editoreissa luodaan dataa ja tallenetaan se tiedostoon. Liike-editorissa muokataan olemassaolevia liikkeitä tai luodaaan uusia. Sessio-editorissa luodaan uusi sessio liikkeelle. Editorin voi sulkea. Samalle liikkeelle ei voi avata toista editoria, eikä toista uuden liikkeen -editoria ennen kuin uudelle liikkeelle on ainakin vaihdettu nimi (tallentaa ei tarvitse).

Muokatun liikkeen tai uuden liikkeen tallentaminen tiedostoon editorissa päivittää liikelistan sisällön molemmissa ikkunoissa.

Sessioiden tarkastelu -ikkuna sisältää listan tallenetuista liikkeistä, joihin on lisätty sessiotiedostoista luetut sessiot. Valitsemalla liikkeen ikkuna näyttää valitun liikkeen sessiodatan. Ikkunassa voi valita eri tapoja katsella dataa: taulukoita tai kaavioita, eri johdannaisia.

Käyttöohjeet-ikkunassa näytetään ohjeet ohjelman käyttöön: miten muokata ja lisätä liikkeitä, miten lisätä sessioita, miten tarkastella lisäämäänsä dataa, esimerkkejä liikkeille annettavista muuttujista ja niiden käytöstä tilastoinnissa.