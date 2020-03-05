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

## Playersprite rotates with player
Vi har testet den grafiske roteringen til spiller ved å:
 - Kjøre spillet og så trykke D for å rotere til høyre.
 - Trykke A for å rotere til venstre.
 Hver gang så ser vi at roboten grafisk roterer seg slik at den alltid vendes den retningen som roboten ser. 
