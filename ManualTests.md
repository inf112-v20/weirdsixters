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
