#### Ohjelman käynnistys

Ohjelman suoritus lähtee käyntiin main-metodin sisältäväst luokasta App, joka luo BodyLog-olion. BodyLog luo MainWindow-olion ja sisältää myös odottamatomien virheiden käsittelyn.

#### MainWindow

MainWindow sisältää JTabbedPanen joka sisältää muut UI:n ikkunat:
- HelpWindow
- MoveEditorWindow
- SessionEditorWindow
- StatisticsViewerWindow

Ikkunoita voi kerrallaan olla auki jokaista yksi tai nolla.

MainWindow luo yhden MoveListContainerUpdater-olion, mikä manageroi muiden ikkunoiden liikelistojen (JComboBox ja JList) päivittämistä; tämä ei koske HelpWindow-luokkaa. Aina ohjelman käynnistyessä MainWindow luo HelpWindow-olion ja näyttää sen ensimmäisenä. JTabbedPane käyttää kustomoituja tabi-nappeja jotka voi sulkea, mikä poistaa ikkunan. Tämä on CloseableTab-luokka.

Kun ikkuna suljetaan, CloseableTab tarkistaa onko ikkuna WindowWithMoveListContainer-instanssi, hakee tämän ikkunan MoveListContainerUpdaterin ja MoveListContainerin ja käskee päivittäjän poistaa haettu MoveListContainer sen listasta. Muuten ikkunoiden avaaminen ja sulkeminen kasvattaisi päivittäjän pitämää listaa liikelistoista rajatta.

#### WindowWithMoveListContainer

MoveEditorWindow, SessionEditorWindow tai StatisticsViewerWindow perivät abstraktin WindowWithMoveListContainer-luokan. Kun pääikkunassa luodaan jokin näistä, antaa MainWindow tallentamansa MoveListContainerUpdater-olion uudelle ikkunalle. Jokainen ikkuna siis käyttää samaa oliota.

#### MoveEditorWindow ja SessionEditorWindow

Nämä kaksi perivät abstraktin WindowWithMoveChooser-luokan, joka perii abstraktin luokan WindowWithMoveListContainer. WindowWithMoveChooser sisältäää MoveChooser-olion, joka perii abstraktin MoveListContainer-luokan.

#### WindowWithMoveChooser

Kun WindowWithMoveChooserin perivä ikkuna luodaan, se luo MoveChooser-olion, jonka se antaa sille annetulle MoveListContainerUpdater-oliolle, joka tallentaa sen listaansa. Kun MoveChoserista valitaan jokin liike, luodaan ikkunassa uusi abstraktin Editor-luokan perivä olio.

#### MoveEditor ja SessionEditor

Nämä perivät abstraktin Editor-luokan, joka sisältää niiden yhteisen koodin. Molemmat sisältävät JTablen, jossa käytetään kustomia TableModelia. MoveEditorissa käytetään MoveEditorTablea ja SessionEditorissa SessionEditorTablea. Nämä perivät abstraktin luokan EditorTable, mikä perii DefaultTableModelin.

MoveEditor sisältää myös VariableChoicesEditor-olion, mikä on kustomoitu TableCellEditor.

Editor-luokassa käytetään abstraktia Saver-oliota, jonka ikkuna antaa editorille sitä luodessa, MoveEditorille MoveSaver-olio ja SessionEditorille SessionSaver-olio.

#### MoveSaver ja SessionSaver

Nämä sisältävät Move-olion, ja Editor-oliot hakevat Move-olion niiden sisältämältä Saver-oliolta. Saverit tallentavat myös MoveListContainerUpdater-olion, jonka ne saavat ikkunalta Editor-oliota luodessa. Päivittäjä löytää siis tiensä Saver-olioon MainWindowista MoveEditorWindowiin/SessionEditorWindowiin ja sieltä MoveSaveriin/SessionSaveriin.

MoveSaver kutsuu MoveListContainerUpdateria päivittämään MoveListContainerit.
SessionSaver kutsuu MoveListContainerUpdateria päivittämään StatisticsDisplayerin.

MoveSaver käyttää Moves-luokan staattista metodia tallentamaan Move-olionsa tiedostoon.
SessionSaver käyttää Sessions-luokkaa.

#### StatisticsViewerWindow

Tämä perii WindowWithMoveListContainer-luokan suoraan. Kun tämä ikkuna luodaan, se luo MoveSelector-olion ja StatisticsDisplayer-olion, jotka se antaa sille annetulle MoveListContainerUpdater-oliolle.

Kun ikkunassa klikataan liikettä listassa, ikkuna kutsuu StatisticsDisplayeria antamaan StatisticsDisplayn.

#### StatisticsDisplayer

Sisältää SessionReader-olion, jolla se lukee tiedostoista Sessio-oliot, joista se luo StatisticsDisplay-olioita (sisäinen luokka). StatisticsDisplay sisältää JTableja, jotka taas käyttävät kustomoitua TableModelia, ViewTable-luokkaa. ViewTable perii AbstractTableModelin.

#### MoveChooser ja MoveSelector

MoveChooser käyttää JComboBoxinsa ComboBoxModelina SortedComboBoxModel-luokkaa. MoveSelector taasen JListinsä ListModelina on SortedListModel.

Kaikki ohjelmassa käytettävät Move-oliot tulevat näiden olioiden sisältämästä listasta. Saver-olioiden Move-olio on peräisin MoveChooserista, StatisticsDisplayerin saa käsittelyynsä Move-olion MoveSelectorista.

#### MoveListContainerUpdater

Tämä sisältää siis kerrallaan nollasta kolmeen MoveListContainer riippuen siitä, kuinka moni kolmesta näitä sisältävästä ikkunasta on sillä hetkellä auki, ja nollasta yhteen StatisticsDisplayeria riippuen siitä onko StatisticsViewerWindow avattu vielä vai ei.

MoveListContainerUpdater sisältää MoveReader-olion, jolla se luo tiedostoista Move-oliot, millä se päivittää MoveListContainerien sisältöä.

#### MoveReader ja SessionReader

MoveReader käyttää Moves-luokan staattisia metodeja ja MoveFileFilter-oliota lukiessaan tiedostoja.
SessionReader käyttää Sessions-luokkaa ja SessionFileFilter-oliota.

#### Move, Variable, VariableList, Session ja Set

Move-luokka on ohjelman keskusta. Se sisältää Session-olioita, joka sisältää Set-olioita. Move ja Session perivät abstraktin VariableList-luokan, joten ne sisältävät Variable-olioita myös.

Move-oliota annetaan parametrina tai sen metodeja kutsutaan lähes kaikissa luokissa. Session-olioita ei käsitellä yksinään vaan ne haetaan aina Move-oliolta, paitsi Session-olioita luodessa tiedostoista mutta silloinkin ne liitetään tallennetaan lopuksi Move-olioon. Set-oliot haetaan Session-oliosta ja Variable-oliot Move-oliosta tai Session-oliosta.

Move ja Variable kutsuvat Names-luokan staattista metodia tarkistaessaan nimien sopivuutta.

#### Names, Delimiters, Moves, Variables, Sessions, Sets

Nämä luokat sisältävät pelkästään staattisia metodeja ja muuttujia. Sessions-luokka kutsuu Sets- ja Variables -luokan metodeja. Moves-luokka kutsuu Variables-luokan metodeja. Moves, Variables, Sessions ja Sets käyttävät Delimiters-luokan muuttujia. Names-luokka kutsuu Delimiters-luokan metodia.