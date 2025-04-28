Space Invaders Game
Dette er ein Java-implementasjon av arkadespelet Space Invaders.

Om applikasjonen
Dette er eit spel der spelaren styrer eit romskip som beveger seg horisontalt nedst på skjermen, og må skyte ned invaderande romvesen som kjem mot spelaren. Romvesena beveger seg sakte frå side til side og gradvis nedover skjermen etter kvart som tida går. Målet er å skyte ned alle romvesena før dei når botnen av skjermen, eller kolliderer med skipet.

Funksjonar
Styring: Spelaren bruker piltastane for å bevege romskipet til venstre og høgre.

Skyting: Spelaren skyter med mellomromstasten (space bar), med ei kort nedkjølingstid mellom skota.

Fiendar: Romvesena beveger seg horisontalt og endrar retning når dei treff kanten, før dei beveger seg gradvis nedover.

Game Over: Spelet er over når ein fiende når botnen av brettet eller treff spelaren sitt skip.

Seier: Det er ikkje eit klart sluttmål, men det handlar om å skyte ned flest mogleg romvesen. Når alle fiendar er skotne ned, går spelaren vidare til neste nivå med ei ny bølgje av fiendar som beveger seg raskare.

Program
Programmet er skrive i Java, og nyttar bibliotek som:

Swing for spelepanelet (grafisk brukargrensesnitt)

java.awt for teikning av grafikk, handtering av tastaturinput og spel-ticks

Dette inkluderer java.awt.Graphics, java.awt.event.* og javax.swing.Timer.