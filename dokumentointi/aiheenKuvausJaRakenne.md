## Aihe: Tilastointijärjestelemä saliharjoitteluun

### Kuvaus käyttötarkoituksista
Ohjelmalla on tarkoitus kirjata, ylläpitää ja tarkastella harjoittelusuorituksia. Pääasiassa tarkoitettu voimaharjoitteluun mutta pitäisi toimia yhtä hyvin kestävyysharjoitteluunkin; muuttujien nimet ovat erilaisia (paino vs etäisyys, toistot vs aika) mutta arvot silti lukuja. Harjoittelu koostuu yhdestä tai useammasta liikkeestä, joista jokaisessa suoritetaan yksi tai useampi sarja. "Sarja" voi olla tietty määrä toistoja (useimmat voimaharjoitteluliikkeet), tietty aika (staattiset pidot paikallaan esim. lankku tai liikkeessä esim. farmer's walk), tietty etäisyys (esim. farmer's walk, kelkan pusku). Ohjelmalla tilastoidaan niitä arvoja ja liikkeitä mitkä käyttäjä valitsee.

Ohjelmalla on kaksi käyttöpuolta: datan lisääminen ja datan lukeminen.

Yleensä saliharjoittelun jälkeen käyttäjä avaa ohjelman ja lisää salikerrasta kirjaamansa informaation ohjelmaan enemmän tai vähemmän yksityiskohtaisesti per suoritettu liike: lämmittelysarjat, työsarjat, RPE per sarja, lepoajat, kommentit subjektiivisesta olotilasta. Muulloin käyttäjä katselee ohjelmalla lisäämiään tilastoja.

### Datan lisääminen:
 Käyttäjä valitsee yksityiskohtaisuuden tason valitsemalla mitä muuttujia hän kirjaa per liike. Käyttäjä tallentaa listan tekemistään liikkeistä ja niiden muuttujista. Tästä hän valitsee dataa kirjatessaan haluamansa. Data tallennetaan tekstitiedostoihin; yksi per liike per salikerta tallentamaan suoritusdataa ja yksi tiedosto per liike tallentamaan liikedataa (nimi ja muuttujat). 

**Tiedostorakenne**
- sessiotiedostot: `Tilastot/liikkeenNimi/sessionPaivamaara.txt`
- liiketiedostot: `Liikkeet/liikkeenNimi.txt`

### Datan lukeminen:
Tallennetua dataa on tarkoitus tarkastella vähintäänkin taulukoiden ja mahdollisesti viiva- tai palkkikaavioiden tai näiden yhdistelmän muodossa. Tarkoitus olisi pystyä rakentamaan muuttujista käyttäjän valitsemia johdannaisia: laskutoimitus (summa/tulo/keskiarvo/maksimi/minimi) yhden muuttujan arvoista jokaisesta sarjasta, joka liitettäisiin sessioon; kahden tai useamman muuttujan laskutoimitus, joka lisättäisiin sarjaan muuttujana; näiden yhdistelmä. Määritellyt johdannaiset voisi tallentaa samaan tiedostoon liikkeen muuttujalistan kanssa. Kaavioiden tarkoitus olisi pääasiallisesti johdannaisten tarkastelu ja taulukot raakaa dataa varten.

 Taulukkomuoto yhden liikkeen yhden session datan näyttämiselle olisi sessioon liitetyt johdannaiset ensimmäisellä rivillä, jonka jälkeen olisi yksi sarja per rivi, jossa muuttujien arvot (mukaanlukien johdannaiset) yksi per sarake. Usean session sisältävä taulukko olisi session päivämäärä rivin ensimmäisessä sarakkeessa, jonka jälkeen olisi sen session sarjat ja seuraavan session ensimmäinen rivi seuraisi edellisen viimeistä. Tämä voi olla melko iso taulukko jos sessioita on kymmeniä ja sarjoja aina muutama, joten yhdestä voisi näkyä vain yksi rivi, joka sisältää tiedot session johdannaisarvoista ja jonka voisi sitten avata näyttämään tiedot jokaisesta sarjasta.

### Kayttoliittyma

### Sisäinen rakenne
Dataa lisätessä käytetään luokkia Sessio, Liike, Sarja, LiikeTiedostoon, TiedostostaLiike ja SessioTiedostoihin. Sessio sisältää päivämäärän ja Liike-olioita, jotka sisältävät muuttujalistan ja Sarja-olioita, jotka sisältävät varsinaisen suoritusdatan. LiikeTiedostoon-luokka ottaa yhden Liike-olion ja kirjoittaa sen muuttujat liiketiedostoon. Tätä käytetään kun käyttäjä on luonut uuden liikkeen ja haluaa tallentaa sen tiedot. TiedostostaLiike-luokka ottaa liiketiedoston ja luo sen perusteella Liike-olion, jolla on tiedostosta saadut muuttujat ja nimi. Tätä käytetään kun käyttäjä valitsee jo aiemmin luomansa liikkeen listasta. SessioTiedostoihin ottaa yhden Sessio-olion ja kirjottaa sen sisällön sessiotiedostoihin ja luo liikkeiden kansiot jos niitä ei ole jo. Tätä käytetään kun käyttäjä on lisännyt kaiken datan Sessio-olioon ja on valmis tallentamaan tiedot.

Merkit-luokka sisältää vakiomuuttujat, joihin on tallennettu lista merkeistä joita ei saa käyttää Liike-olion nimessä tai muuttujissa, johtuen näiden merkkien käytöstä tiedostojen sisällön käsittelyssä, sekä tarkistusmetodin.

Kansiot-luokka sisältää vakiomuuttujat joihin on tallennettu kansiorakenteen nimet. Tämä tekee helpommaksi näiden vaihtamisen; ei tarvitse etsiä koodista joka kohtaa jossa kansion nimi on mainittu.

Dataa lukiessa käytetään luokkia Taulukko, Liike, Sarja, TiedostostaLiike ja TiedostostaTaulukko. Taulukko sisältää päivämäärän ja yhden Liike-olion, joka sisältää Sarja-olioita. Tätä on tarkoitus käyttää taulukoita ja kaavioita tehdessä. TiedostostaTaulukko ottaa sessiotiedoston ja luo siitä Taulukko-olion, jonka Liike-oliolla on tiedostoista saadut muuttujat, nimi ja Sarja-oliot. Tämä lukee siis yhden sessiotiedoston. Tätä on tarkoitus käyttää toistuvasti luomaan dataa, joka näytetään taulukkona tai kaaviona.
