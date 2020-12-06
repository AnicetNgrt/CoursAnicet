# Atelier découverte des Shaders
Aujourd'hui l'on va découvrir les shaders. Ce sont des outils très puissants pour créer des effets visuels dans le jeux et dans le cinéma qui sont aussi fascinants que complexes.


# Qu'est-ce qu'un shader ?
Un shader est un programme informatique bien particulier qui permet de dessiner à l'écran uniquement à partir du code. C'est à partir de ces programmes que sont construits la quasi-totalité des moteurs de jeux modernes. Les shaders peuvent produirent des images très complexes et peuvent être écrits avec des langages variés mais similaire qui dépendent du moteur de jeu utilisé ou de l'[API graphique](https://www.frandroid.com/hardware/345822_vulkan-a-quoi-sert-nouvelle-api-graphique-joueurs) visée.

## Comment ça marche ?
La nature du shader est intimement liée au fonctionnement du GPU (= carte graphique). En effet, pour dessiner des choses à l'écran pas besoin d'un langage en particulier ou même du GPU. Il suffit de choisir une couleur par pixel et de les mettre dans un tableau à deux dimensions. Ensuite il suffit d'écrire ce tableau dans un fichier selon un algorithme qu'on peut trouver sur Internet et de lui donner la bonne extension. Et voilà on a une image.

Le problème vient plus de la taille de l'image. Lorsque vous faites 60 images par seconde (ips) au sein d'un moteur de jeu, il faut calculer plein de choses pour chaque pixel. Et si vous faisiez ça sur le CPU (= processeur) qui ne peut faire qu'une seule chose à la fois (en réalité ça dépend du nombre de coeurs), et bien vous devriez faire passer les pixels à la queue leu leu est ça serait beaucoup trop long pour atteindre les 60 ips.

La solution est d'utiliser le GPU car il possède des milliers de coeurs pas très intelligents mais capables de faire des opérations spécifiques en parallèle. Finie la queue devant le magasin, tous vos pixels peuvent être calculés presque en simultané! Mais pour coordonner tous ces passages simultanés il faut des programmes très spécifiques tels que les shaders.

Ainsi le shader est un bout de code exécuté sur tous les coeurs du GPU en même temps. Mais comme chaque coeur est pas super intelligent il faut impérativement que tous les coeurs reçoivent le même shader, les mêmes données en entrée, et aient à renvoyer leur résultat de la même façon.

Celà implique des techniques assez farfelues pour faire comprendre à chaque coeur où et comment calculer son propre pixel. Mais on ne verra pas ça aujourd'hui car la plupart des outils nous donnent des moyens de le faire sans trop avoir besoin de comprendre. Pour l'instant voyons ce qu'on peut faire de cool avec des shaders.

## Quelques shaders cools
Les shaders ne servent pas qu'à calculer la couleur de pixels, ils peuvent servir à toute forme de calculs intensifs et répétés. Par exemple calculer le mouvement de milliers de brins d'herbes en fonction du vent, simuler l'érosion à partir de milliers de goutelletes pour générer des montagnes procéduralement, faire des automates cellulaires...

Pour tous ces calculs existent des types de shaders appropriés comportant des raccourcis et variables d'entrée/sortie adaptées, même si rien ne vous empèche d'utiliser ces variables d'entrée et de sortie pour faire des choses pas vraiment prévues au départ (par exemple encoder dans la couleur de chaque pixel d'une image le taux de joie de milliers de Sims, ou encore encoder un booléen "vrai = vivant", "faux = mort" en fonction de si l'intensité du bleu dans la couleur est plus proche de 0 ou de 1 si vous codez un simulateur de sélection naturelle).

Voici quelques exemple de shaders cools et variés :

- [Une formation hexagonale envoutante](https://www.shadertoy.com/view/4lBSzW)
- [De l'herbe dynamique à la Breath of the Wild](https://roystan.net/articles/grass-shader.html)
- [Un tutoriel pour animer des arbres à la manière d'Animal Crossing avec des shaders](https://www.youtube.com/watch?v=V1nkv8g-oi0&list=LL&index=50)

Dans cet atelier on se concentrera sur les shaders "Fragment", qui servent à calculer la couleur de chaque pixel d'une image. Même vous avez bien compris qu'on peut aussi faire une IA et des simulations galactiques à partir de cette image. Mais bon on va rester simple et visuel pour le moment.

# Comment écrire un shader
Ce sujet est assez spécifique à l'API graphique visée et au moteur. Les langages peuvent changer, mais aussi la manière dont on charge les shaders, dont on les paramètre et dont on les fait exécuter par le GPU.

Mais ne vous inquiétez pas, la documentations des différents outils l'explique amplement et le vocabulaire entre différents langages de shader est très très similaire donc on est jamais perdu.

Prenons ici l'exemple du site shadertoy qui utilise l'API graphique OpenGL et donc le langage de shading GLSL.

## Shadertoy
[Shadertoy](https://www.shadertoy.com) est un site pour écrire des shaders, les visualiser dans le navigateur et les partager ensuite à la communauté.

On y trouve beaucoup de shaders très avancés qui reproduisent des mondes réalistes 100% dans le code, sans passer par de logiciels de modélisation 3D. 

Difficile de s'imaginer comment "coder" un arbre ou un lapin qui sautille, mais les utilisateurs de shadertoy sont très ingénieux. Si les gens vous prennent pour un sorcier lorsqu'ils apprennent que vous programmez, et bien sachez dans ce cas là que les pros des shaders sont les sorciers parmi les sorciers, ce qui est loin de manquer de style. 

Nous on va rester au stade magicien du dimanche avec ses tours de cartes pas dingues, mais comprenez qu'on peut aller très loin.

## La syntaxe
La syntaxe de GLSL est similaire à celle du langage C.

Les différences marquantes sont les suivantes :
- Il faut indiquer `out` ou `in` devant les paramètres dans le prototype des fonctions
	
	Exemple: `void mainImage( out vec4 fragColor, in vec2 fragCoord ){}`
- Des types vecteur, textures et matrice sont présents par défaut (et sont parfois natifs comme les int et les float en C)
	
	Exemple: vecteur taille 2 (x, y) `vec2`, vecteur taille 3 (x, y, z) `vec3` vecteur taille 4 (x, y, z, a) `vec4`
- Les couleurs sont représentées par des vecteurs de taille 4 (rouge, vert, bleu, opacité) est écrit (r, g, b, a) ou (x, y , z, a) avec chaque attribut nombre à virgule compris entre 0 (noir) et 1 (couleur au max)
- Les vecteurs comportent des raccourcis d'accès intuitifs
	
	Exemple: faire `monVecteurDeTaille4.yzz` donne un vecteur de taille 3 comprenant en x la valeur en y du premier vecteur et en y et en z la valeur en z du premier vecteur.
	
	Exemple: V = (x, y, z, a), v.xxzy = V2 = (V.x, V.x, V.z, V.y))

## Shader minimal
De la même manière qu'en programmation classique le programme minimal consiste en un `printf("hello world");`, le shader minimal dessine un rectangle coloré en chaque point selon l'UV. L'UV (rien à voir avec l'ultra violet) c'est une valeur d'entrée souvent utilisée dans les shaders. C'est un vecteur de taille 2 avec en x une valeur de 0 à 1 indiquant à quel point on est proche du côté droit de l'image, et en y à quel point on est proche du bord bas de l'image.C'est pratique pour faire comprendre au shader où il se situe dans l'image. 

En fonction du langage de shading on a soit l'UV dès le départ et on peut recalculer les coordonnées du pixel à partir de l'UV et de la taille de l'image, soit comme en GLSL on part directement avec les coordonnées et la taille puis en faisant la division de l'un par l'autre on obtient le vecteur UV.

Ci-dessous le shader minimal tel qu'il est lorsqu'on [crée un nouveau shader sur shadertoy](https://www.shadertoy.com/new) :

```C++
void mainImage( out vec4 fragColor, in vec2 fragCoord )
{
    // Normalized pixel coordinates (from 0 to 1)
    vec2 uv = fragCoord/iResolution.xy;

    // Time varying pixel color
    vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));

    // Output to screen
    fragColor = vec4(col,1.0);
}
```

Décortiquons cette magie noire :

Le shader de type fragment en GLSL commence toujours par la fonction `mainImage` qui ne renvoie aucune valeur `void` et qui a en paramètre `out` un vecteur de taille 4 `fragColor` dans lequel on va encoder la couleur du pixel et en paramètre `in` un vecteur de taille 2 `fragCoord` qui nous indique les coordonnées du pixel dans l'image. 

Ensuite, on a accès à plusieurs constantes qui seront les mêmes à chaque appel simultané du shader. Toutes ces constantes sont indiquées dans la documentation de GLSL, mais ici on a deux exemple :
- `iResolution` un vecteur 3 indiquant la taille horizontale de l'image en x, la taille verticale de l'image y et son ratio de pixels en z
- `iTime` un nombre à virgule en croissance rapide qui peut servir d'indicateur de temps (pour animer)

Maintenant expliquons ligne par ligne :

`vec2 uv = fragCoord/iResolution.xy;` ici on crée le vecteur UV de taille 2 `vec2` en divisant deux autres vecteurs de taille 2 `fragCoord` et le raccourcis `iResolution.xy` que j'ai expliqué précédemment. La division entre deux vecteurs se fait paramètre par paramètre. Par exemple prenons V1=(x, y) et V2=(a, b) et bien V1/V2 donne V3=(x/a, y/b). Dès lors ici on fait UV=(coordonnée_en_x/largeur, coordonnées_en_y/hauteur) ce qui nous donne bien un x et un y compris entre 0 et 1 et indiquant à quel point on est à droite, respectivement en bas, de l'image.

`vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));` ici on calcule directement la couleur du pixel. 
D'abord on fait `col = 0.5` ce qui peut paraître étrange puisque `col` est un `vec3` et `0.5` un `float`. Mais en réalité cette opération est permise et donne un vecteur de taille 3 rempli avec des 0.5.
Ensuite `0.5*cos(iTime+uv.xyx+vec3(0,2,4))` qu'on va décortique en 2 étapes:
- `iTime+uv.xyx+vec3(0,2,4)`: ici on a un calcul qui donne un vecteur 3. En effet, additionner uv.xyx qui est un vecteur 3, vec3(0,2,4) qui est aussi un vecteur 3, et iTime qui est un float consiste à faire l'opération `Vecteur3(x=iTime, y=iTime, z=iTime) + Vecteur3(x=UV.x, y=UV.y, z=UV.x) + Vecteur3(x=0, y=2, z=4)`. En fait l'on veut faire une couleur donc, dans le résultat de ce calcul, le x indique la quantité de rouge, y la quantité de vert et z la quantité de bleu. Ainsi on a :
  - Rouge = temps + a_quel_point_on_est_a_droite + 0
  - Vert = temps + a_quel_point_on_est_en_bas + 2
  - Bleu = temps + a_quel_point_on_est_a_droite + 4
  Or ces valeurs ne sont pas comprises entre 0 et 1, et c'est ce que la suite du calcul va faire.
- `0.5*cos(étape 1)` En effet, faire le cosinus d'un vecteur de taille 3 V1=(x, y, z) donne un vecteur de taille 3 V2=(cos(V1.x), cos(V1.y), cos(V1.z)). Or, [la fonction cos varie périodiquement entre -1 et 1](https://previews.123rf.com/images/julvil/julvil1405/julvil140500063/28459289-vector-illustration-de-la-fonction-math%C3%A9matique-y-=-cos-x.jpg) donc la couleur n'est pas encore entre 0 et 1. Mais comme on la multiplie par 0.5 on a finalement une couleur comprise entre -0.5 et 0.5, et comme on a ajouté 0.5 au tout début du calcul on est bon, on a des valeurs pour l'intensité du bleu, du vert et du rouge qui sont valides.
Donc pour récapituler on a:
- Rouge = 0.5 + 0.5 * cosinus(temps + a_quel_point_on_est_a_droite + 0)
- Vert = 0.5 + 0.5 * cosinus(temps + a_quel_point_on_est_en_bas + 2)
- Bleu = 0.5 + 0.5 * cosinus(temps + a_quel_point_on_est_a_droite + 4)

`fragColor = vec4(col,1.0);` Ensuite on dit au programme qu'on veut appliquer cette couleur en l'assignant à la variable `out fragColor`, mais sans oublier qu'une couleur valide est un vecteur 4, et que `col` est un vecteur 3. On a donc besoin d'un paramètre supplémentaire : l'opacité, qu'on met à 1.0 ici pour dire qu'elle est maximale (sinon ça serait transparant). 

Ce qui nous donne cette variation périodique de couleurs en fonction d'où on se situe sur l'image. 

N'hésitez pas à changer `uv.xyx` par `uv.yxy` ligne 7 par exemple pour voir ce que ça change. Vous pouvez aussi créer une fonction comme en C qui fait le calcul qu'on vient de décortiquer à part et qui prend en paramètre un vecteur qui servira à remplacer `vec3(0,2,4)` ligne 7. 

N'oubliez pas d'appuyer sur le bouton play en bas à gauche du code pour compiler le shader et voir le résultat.

À vous de jouer !

# Des liens utiles
- [Le grimoire des shaders (je vous avais dit que ce sont des sorciers)](https://thebookofshaders.com/?lan=fr)
- [Une chaîne pour apprendre des shaders étape par étape avec les maths derrière](https://www.youtube.com/watch?v=PGtv-dBi2wE)
- [Une chaîne pas mal pour voir des utilisations avancées des shaders dans des vrais jeux](https://www.youtube.com/channel/UC8bYucAICXmYet8pZ5Ja9Dw)
- [Une autre chaîne qui fait strictement la même chose en plus varié et éducatif](https://www.youtube.com/watch?v=4QOcCGI6xOU)
- [La doc de shadertoy](https://www.shadertoy.com/howto#q1)