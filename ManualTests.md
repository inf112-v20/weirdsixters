# Manual tests

## Movement på brettet
Bruk WASD for å velge faste bevegelseskort. W er move 1 forward, A er turn left, S er move 1 backwards, D er turn right. Vi ser at roboten kolliderer med vegger og at den beveger seg der den skal. I tillegg ser vi at brettet ser ut slik som vi forventet og at kollisjonene tilsvarer med de synlige veggene. Sjekke også at roboten peker i den retningen man roterer.

## Game.executeCard
Vi har testet executeCard() fra game.java ved å velge fem kort med talltastene fra 1-9 og så trykke enter. Vi ser at det funker når roboten gjør slik som kortene sier. Dette testet at vår metode executeCard() fungerer med forskjellige typer kort og at roboten flytter seg riktig.
 
 ## Lose condition
 Vi har testet lose condition ved å;
 - Bevege roboten mot hullet ved hjelp av kortene.
 - Forsikre oss om at roboten returnerer til backup posisjonen når den dør. 
 - Når man bruker movement cards som har mer en ett steg, så skal man ikke kunne gå gjennom hull, men falle i det.
 - Livene oppe til høyre skal reflektere hvor mange liv jeg har, ettersom jeg mister de.

## Flagg
Vi har testet flagg ved å:
 - Bevege roboten på flagg. I tilfeldig rekkefølge, for å forsikre oss om at det ikke skje noe
 - Bevege roboten på flagg i riktig rekkefølge. Fra 1 til 4 og da oppfyller vi vinne-kravet og alle flaggene har blitt plukket opp.
 - Sjekke at det kommer en meldig når man tar riktig flagg.
 
## Belter
Vi har testet belter ved å:
 - Bevege spilleren to plasser rett frem slik at den står på et belte.
 - Se at den blir dyttet tilbake mot den veien pilene peker.
 - Se at belter med sving roterer roboten 90 grader.
 
## Tannhjul
Vi har testet tannhjul ved å:
 - Se at spilleren roterer 90 grader den retningen som tannhjulet har.
 
## Game.stageCard
Vi testet stageCard() ved å:
 - Trykke på knapp 1-9 på tastaturet
 - Se at kortet blir flyttet til register ved å se at det flytter seg opp ett hakk
