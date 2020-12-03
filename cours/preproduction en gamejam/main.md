## Préproduction en Gamejam

Dans ce cours on va apprendre à **mettre en place un projet de jeu** pour une **game jam** selon les méthodes que j'applique dans ce type de projet. 

Ce guide vise à préparer **un projet capable de gagner**, ou pour au moins être **parmi les jeux les plus joués**, donc recevant plus de retours des joueurs, et donc j'en suis convaincu, **des jeux à partir desquels on apprend beaucoup**.

Mais mes conseils ne sont pas une loi absolue. Chacun son style et **n'oubliez pas de vous amuser en faisant des jeux !**

```
+----------------PLAN----------------+
|- Présentation                      |
|  - Préprod ?                       |
|  - Game jam ?                      |
|  - Scénario                        |
|- Trouver l'idée                    |
|  - Evaluer le temps                |
|  - Trouver le gameplay             |
|  - Trouver le style graphique      |
|- Commencer à prototyper            |
|  - Choix du moteur                 |
|  - Les graphismes                  |
|  - Tester son prototype            |
|- Conseils pour la suite            |
+------------------------------------+
```

# Présentation

## La préproduction ?

L'étape de **préproduction** est tout le travail que vous devez réaliser **avant même de vous lancer à faire le jeu pour de vrai**. 

Elle peut comporter plus ou moins d'étapes intermédiaires en fonction de l'ambition de votre projet (concept arts, game design document, prototypes, planning, ciblage marketing ...).

Mais surtout, **elle est capitale**. De la qualité de cette dernière **dépendra la réussite de tout votre projet**. Croyez-moi d'expérience.

La qualité n'étant pas toujours synonyme de quantité, je vous conseille tout de même de **ne pas passer un temps infini en préproduction** dans vos propres projets sinon vous ne les finirez jamais.

D'ailleurs, si vous êtes touché par ce genre de "malédiction du débutant" j'ai surement une solution pour vous : **Les game jams**

## Une game jam ?

Une game jam, c'est une **compétition** qui dure couramment **entre 2 jours et 1 mois**, où plusieurs équipes (ou loups solitaires) doivent créer le meilleur jeu dans le temps imparti et **selon un thème donné** au début.

C'est un excellent exercice qui vous permet de **faire vos premières armes** dans le milieu de la création de jeu.

À mon avis et d'après mon expérience, c'est **le moyen le plus efficace de s'exercer** sur tout les pans de la création de jeux, même sur **la publication et le marketing**, que la plupart des débutants ont tendance à ne jamais faire puisqu'ils ont tendance à faire des jeux trop difficiles qu'ils ne finiront jamais dans un temps raisonnable.

Si vous n'avez pas le temps ni l'envie de vous lancer dans un projet trop ambitieux, la game jam est la solution.

Ici on prendra le contexte d'une game jam pour ne pas perdre trop de temps, mais **dans un vrai projet** (qui pourrait durer des années) **vous auriez beaucoup plus de choses à faire**.

## Scénario imaginaire

Ici on va partir du principe que vous participez à une **game jam** sur le site **[Itch.io](https://itch.io)** ayant pour **thème "Rise and Fall"** et commme durée **un mois**.

# Trouver une idée

Trouver une idée de jeu, c'est très difficile. **Il faut beaucoup d'expérience** pour avoir l'idée qui va vraiment bien marcher car vous devez pouvoir analyser **multiples facteurs** tels que **le temps** que vous avez, ce que **les gens aimeraient jouer**, ce que **vous aimeriez faire** comme jeu aussi. Mais ne vous en faites pas, **avec un peu de méthode on peut s'en sortir** !

## Évaluer le temps

**La capacité à travailler et la motivation dépend de chacun**, mais une bonne manière de ne pas s'engager dans un projet trop difficile est de **calculer le temps maximum** qu'on pourrait donner dans le meilleur des cas.

Ici on a un mois, composé de 4 semaines. On part du principe qu'on travaille lentement les 3 premières semaines et que comme tout bon procrastinateur **on s'y met généralement à fond la dernière semaine**.

Personnellement, **ça dépend de vos activités et de votre vie de famille**, je travaille 4h max le samedi sur mes jeux et 8h max le dimanche. En semaine je travaille 1h30 max en moyenne par soir.

La dernière semaine je met l'accélérateur et je bosse 2h30 par soir en semaine et 12h par jour le weekend.

Au total on a au mieux 95h de travail devant nous.

Voici un petit tableau du **temps de travail max** sur un jeu que j'estime possible **pour moi** lorsqu'on je suis en même temps à la fac :

| Durée de la jam  | temps de travail max  |
|---|---|
| 3h (ça existe)  | 3h  |
| 1j | 16h |
| 2j | 24h |
| 1 semaine | 35h |
| 2 semaines | 70h |
| 1 mois | 100h |

**Partez du principe que vous ferez moins que votre maximum, c'est plus sûr !**

## Trouver le gameplay

**Il est capital de trouver le gameplay avant le reste**, car c'est l'épine dorsale de votre jeu. Si vous perdez du temps sur le reste et que vous ne trouvez pas un gameplay qui convient, c'est dommage. C'est une erreur fréquente. Mais lorsqu'on a un gameplay il est rare de ne pas trouver le reste, c'est même plus facile.

D'ailleurs, il est souvent valorisé dans les game jams de **respecter le thème grâce au gameplay**, même si c'est très subtil, plutôt que seulement grâce au reste.

Personnellement, je fais du **"brainstorming"**. Je prends un cahier et j'essaye de **faire partir du thème des idées par rapprochement**.

Par exemple ici :

```
- "Rise and Fall"
    - Rise = Monter
      - grimper
      - s'épanouir
      - un jeu où tu gagnes en puissance
    - Fall = Tomber
      - un jeu où tu peux tomber
      - un jeu où tu perds quelque chose
      - un jeu basé sur la physique
      - ...
```

Très vite, on a une liste d'idées assez exhaustive. Mais ce n'est pas tout !

En général **les gens ne jouent pas longtemps du tout** (2 minutes max) à votre jeu. Il faut donc que votre jeu soit **simple**, **efficace**, **immédiatement compréhensible**, et **sans difficulté**. Pas besoin de rejouabilité, de quêtes à ralonges ni d'addictivité, bien que dans de plus gros projets vous devriez surement les prendre en compte.

Contrairement à certaines croyances, **l'originalité du gameplay ne fait pas tout** et un gameplay original mais moins accessible sera souvent défavorable à la visibilité de votre jeu, ce qui n'est pas négligeable puisque **les notations sont souvent très proches des classements de popularité** des jeux, même si les jeux populaires ne sont pas toujours les plus recherchés, complets et amusants.

Mais bon le classement c'est bien mais il ne faut pas en faire une obsession, le but c'est de s'améliorer.

## Trouver le style graphique

