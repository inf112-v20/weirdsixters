# Manual tests

## Movement på brettet
Bruk piltastene for å se at roboten beveger seg der den skal og at den kolliderer med vegger. I tillegg ser vi at brettet ser ut slik som vi forventet og at kollisjonene tilsvarer med de synlige veggene.

## Game.executeCard
Vi har testet executeCard() fra game.java ved å:
 - Kjøre spillet og så først trykke W, sett at vi beveger oss to ruter oppover.
 - Ha trykket D og så W og sett at vi beveger oss to ruter mot høyre. 
 - Ha trykket vi A og W og sett at vi bevegde oss to ruter oppover. 
 - Ha trykket F og så W og sett at vi beveger oss to ruter nedover. 
 - Ha trykket S og sett at vi beveger oss en rute oppover. 
 Dette testet at vår metode executeCard() fungerer med forskjellige typer kort og at movePlayer flytter riktig i forhold til tilgitte argumenter.
 
 ## Game.loseCondition
 Vi har testet loseCondition() ved å;
 - Bevege roboten mot hullet ved hjelp av piltastene.
 - Forsikre oss om at roboten returnerer til gitt "startpoint" når den treffer hullet.
 - Gjøre det flere ganger, og i tillegg printe en melding når vi treffer hullet. Dette for å forsikre oss om at alt stemmer mtp. at tile-typen stemmer.
 - Forsikre oss om at roboten faller tilbake til startposisjon om den prøver å gå ut av brettet.

## Playersprite rotates with player
Vi har testet den grafiske roteringen til spiller ved å:
 - Kjøre spillet og så trykke D for å rotere til høyre.
 - Trykke A for å rotere til venstre.
 Hver gang så ser vi at roboten grafisk roterer seg slik at den alltid vendes den retningen som roboten ser. 

## Game.updateFlag
Vi har testet updateFlag() ved å:
 - Bevege roboten på flagg. I tilfeldig rekkefølge, for å forsikre oss om at det ikke skje noe
 - Bevege roboten på flagg i riktig rekkefølge. Fra 1 til 4 og da oppfyller vi vinne-kravet og alle flaggene har blitt plukket opp.
 - Dette ble gjort flere ganger fra forskjellige vinkler. Hver gang funket det som det skulle og riktig melding ble printet ut i terminalen. 

