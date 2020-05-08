# Manual tests

## Movement på brettet
Bruk WASD for å velge faste bevegelseskort. W er move 1 forward, A er turn left, S er move 1 backwards, D er turn right. Vi ser at roboten kolliderer med vegger og at den beveger seg der den skal. I tillegg ser vi at brettet ser ut slik som vi forventet og at kollisjonene tilsvarer med de synlige veggene.

## Game.executeCard
Vi har testet executeCard() fra game.java ved å velge fem kort med talltastene fra 1-9 og så trykke enter. Vi ser at det funker når roboten gjør slik som kortene sier. Dette testet at vår metode executeCard() fungerer med forskjellige typer kort og at roboten flytter seg riktig.
 
 ## Lose condition
 Vi har testet lose condition ved å;
 - Bevege roboten mot hullet ved hjelp av kortene.
 - Forsikre oss om at roboten returnerer til backup posisjonen når den dør. 
 - Når man bruker movement cards som har mer en ett steg, så skal man ikke kunne gå gjennom hull, men falle i det.
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
Vi har testet belter ved å:
 - bevege spilleren to plasser til rett frem slik at den står på et belte.
 - se at den blir dyttet tilbake mot den veien pilene peker.
 
## Rotasjonsbånd
Vi har testet rotasjonsbånd ved å:
 - bevege spilleren 6 plasser nedover på kartet og 3 plasser mot høyre slik at den står på et rotasjonsbånd.
 - se at spilleren roterer med klokken 90 grader.
 

## Game.stageCard
Vi testet stageCard() ved å:
 - trykke på knapp 1-9 på tastaturet
 - se at kortet blir flyttet til register ved å se at det flytter seg opp ett hakk
 
## Renderer.getCardTexIndex
Vi testet getCardTexIndex() ved å
 - se at textures som vises i spillet samsvarer med type kort gitt (dette printes i console).
