# Manual tests

## Movement på brettet
Bruk WASD for å velge faste bevegelseskort. W er move 1 forward, A er turn left, S er move 1 backwards, D er turn right. Vi ser at roboten kolliderer med vegger og at den beveger seg der den skal. I tillegg ser vi at brettet ser ut slik som vi forventet og at kollisjonene tilsvarer med de synlige veggene.

## Game.executeCard
Vi har testet executeCard() fra game.java ved å velge fem kort med talltastene fra 1-9 og så trykke enter. Vi ser at det funker når roboten gjør slik som kortene sier. Dette testet at vår metode executeCard() fungerer med forskjellige typer kort og at movePlayer flytter riktig i forhold til tilgitte argumenter.
 
 ## Lose condition
 Vi har testet lose condition ved å;
 - Bevege roboten mot hullet ved hjelp av piltastene.
 - Forsikre oss om at roboten returnerer til gitt "startpoint" når den treffer hullet.
 - Gjøre det flere ganger, og i tillegg printe en melding når vi treffer hullet. Dette for å forsikre oss om at alt stemmer med tanke på at tile-typen stemmer.
 - Forsikre oss om at roboten faller tilbake til startposisjon om den prøver å gå ut av brettet.
 - Når man bruker moveCards som har mer en ett steg, så skal man ikke kunne gå gjennom hull, men falle i det.
 - Livene oppe til høyre skal reflektere hvor mange liv jeg har, ettersom jeg mister de.

## Playersprite rotates with player
Vi har testet den grafiske roteringen til spiller ved å:
 - Kjøre spillet og så trykke D for å rotere til høyre.
 - Trykke A for å rotere til venstre.
 Hver gang så ser vi at roboten grafisk roterer seg slik at den alltid vendes den retningen som roboten ser. 

## Flagg
Vi har testet flagg ved å:
 - Bevege roboten på flagg. I tilfeldig rekkefølge, for å forsikre oss om at det ikke skje noe
 - Bevege roboten på flagg i riktig rekkefølge. Fra 1 til 4 og da oppfyller vi vinne-kravet og alle flaggene har blitt plukket opp.
 - Dette ble gjort flere ganger fra forskjellige vinkler. Hver gang funket det som det skulle og riktig melding ble printet ut i terminalen. 
 
## Belter
Vi har testet belter ved å;
 - bevege spilleren to plasser til rett frem slik at den står på et belte.
 - se at den blir dyttet tilbake mot den veien pilene peker.
 

## Game.stageCard
Vi testet stageCard() ved å:
 - trykke på knapp 1-9 på tastaturet
 - se at kortet blir flyttet til register ved å se at det flytter seg opp ett hakk
 
## Renderer.getCardTexIndex
Vi testet getCardTexIndex() ved å
 - se at textures som vises i spillet samsvarer med type kort gitt (dette printes i console).
