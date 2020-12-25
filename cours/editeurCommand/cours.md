# Le Command Pattern
Cette fois-ci, c'est au tour d'un autre Design Pattern, nommé le "Command Pattern". Ce pattern permet d'encoder et de stocker dans des variables les actions et les transformations susceptibles d'êtres annullées, répétées ou copiées un nombre arbitraire de fois. Ce qui permet de coder l'action "Undo" (le fameux ctrl+z) et "Redo" (souvent un ctrl+y) dans un logiciel, comme un éditeur de texte, un photoshop-like, ou tout simplement un éditeur de map dans un jeu vidéo. Il permet aussi d'encoder la liste des actions successives entreprises par l'utilisateur, afin de les rejouer plus tard (fonctionnalité d'enregistrement de partie), de les envoyer sur le réseau (jeu en ligne), ou de les sauvegarder en mémoire.

# Un éditeur de map
L'implémentation de ce pattern peut être faite de plusieurs manières, plus ou moins strictes et élaborées, donc fixons nous les idées grâce à un exemple.

Imaginez que vous veniez d'être embauché par NADEO afin de travailler sur le prochain TrackMania. Comme tout TrackMania qui se respecte, votre jeu de courses de voiture sera doté d'un éditeur de map.

Vous décidez qu'une map soit composée de blocs (des morceaux de route ou de décor) dans une grille en trois dimensions. Chaque placement et modification de bloc devra pouvoir être annulé par un ctrl+z ou refait après annulation par un ctrl+y.

Afin de permettre le ctrl+z et le ctrl+y à l'infini, il va falloir encoder et stocker les actions du joueur dans des piles successives afin d'obtenir un mécanisme FIFO (first in, first out).

L'idée c'est que dès qu'une action est faite elle est encodée puis mise en tout en haut d'une pile. Puis lorsque le ctrl+z sera appuyé, l'action en haut de la pile sera annulée, une fonction inversant l'effet de l'action sera appelée puis l'action encodée sera déplacée en haut d'une deuxième pile. Et lorsque le ctrl+y sera appuyé, l'action en haut de la deuxième pile sera exécutée à nouveau puis redéplacée vers la première pile. Et ainsi de suite.

`action exécutée -> action mise en haut de la pile du ctrl+z -> ctrl+z -> action inversée -> action mise en haut de la pile du ctrl+y -> ctrl+y -> action (re)exécutée -> ...`

# Encoder les actions
On a donc plusieurs actions qu'il va faloir bien définir afin de pouvoir les encoder, les exécuter, et aussi les inverser.

## Placer un bloc
Placer un bloc consiste à prendre un bloc (on ne va pas rentrer dans les détails, mais libre à vous de créer une classe Bloc avec tous les attributs qui vous plaisent), une coordonnée en 3D et une grille en 3D puis de remplacer le bloc qui s'y trouvait par notre bloc.

Inverser le placement d'un bloc consiste à placer à sa position de bloc qui s'y trouvait avant lui. S'il n'y avait pas de bloc avant lui, l'on placera du vide à nouveau (le bloc "vide" peut être un type de bloc à part entière, ou juste une référence vide telle que la valeur null en java).

Créons donc la classe PlacementBloc qui va permettre d'encoder, d'exécuter et d'inverser l'action de placement d'un bloc :

```java
//CommandPlacementBloc.java
public class CommandPlacementBloc {
    private Bloc nouveauBloc;
    private Bloc ancienBloc;
    private Coord3D coordonnées;
    private Bloc[][][] grille;
    
    public CommandPlacementBloc(Bloc nouvBloc, Coord3D coord, Bloc[][][] grille) {
        nouveauBloc = nouvBloc;
        coordonnées = coord;
        this.grille = grille;
        ancienBloc = grille[coord.x()][coord.y()][coord.z()];
    }
    
    public void exécuter() {
        grille[coord.x()][coord.y()][coord.z()] = nouveauBloc;
    }
    
    public void inverser() {
        grille[coord.x()][coord.y()][coord.z()] = ancienBloc;
    }
}
```

Et voilà, c'est si simple que ça. Une version alternative consiste à ne pas ratacher immédiatement la grille à la commande comme ça l'on pourra exécuter cette action sur n'importe quelle grille. Ce qui peut servir si l'on décide de l'envoyer à un autre joueur par le réseau pour par exemple coder une version collaborative de notre éditeur de map.

Pour cela, il suffit de modifier le constructeur et les méthodes exécuter et inverser :

```java
//CommandPlacementBloc.java
//...
// Supprimer la propriété private Bloc[][][] grille
//...
public CommandPlacementBloc(Bloc nouvBloc, Coord3D coord) {
    nouveauBloc = nouvBloc;
    coordonnées = coord;
    ancienBloc = null;
}

public void exécuter(Bloc[][][] grille) {
    ancienBloc = grille[coord.x()][coord.y()][coord.z()];
    grille[coord.x()][coord.y()][coord.z()] = nouveauBloc;
}

public void inverser(Bloc[][][] grille) {
    grille[coord.x()][coord.y()][coord.z()] = ancienBloc;
}
//...
```

Créons maintenant une classe Bloc absolument bidon :

```java
//Bloc.java
public class Bloc {
    private String type;
    
    public Bloc(String type) {
        this.type = type;
    }
    
    public String setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }
}
```

Je vous laisse coder la classe Coord3D avec un x, un y et un z entiers car c'est absolument trivial. Maintenant, testons de placer un bloc et d'annuler son placement ensuite :

```java
//App.java
public class App {
    public static final int TAILLE = 10;
    
    public static void main(String[] args) {
        Bloc[][][] grille = new Bloc[TAILLE][TAILLE][TAILLE];
        remplirGrille(grille, "VIDE");
        
        Coord3D crd = new Coord3D(TAILLE-1); 
        // {x: 9, y: 9, z: 9}
        grille[crd.x()][crd.y()][crd.z()].getType();
        // "VIDE"
        
        Bloc b = new Bloc("ROUTE");
        // {type: "ROUTE"}
        CommandPlacementBloc c = new CommandPlacementBloc(bloc, crd); 
        // {nouveauBloc: b, coordonnées: crd, ancienBloc: null}
        
        c.executer(grille);
        grille[crd.x()][crd.y()][crd.z()].getType();
        // "ROUTE"
        
        c.inverser(grille);
        grille[crd.x()][crd.y()][crd.z()].getType();
        // "VIDE"
    }
    
    public static void remplirGrille(Bloc[][][] grille, String typeBloc) {
        for(int x = 0; x < TAILLE; x++)
            for(int y = 0; y < TAILLE; y++)
                for(int z = 0; z < TAILLE; z++)
                    grille[x][y][z] = new Bloc(typeBloc);
    } 
}
```

## Modifier un bloc
Maintenant, nous souhaitons modifier un bloc en utilisant la même technique qu'avec le placement d'un bloc.

```java
public class CommandModifierBloc {
    private String nouveauType;
    private String ancienType;
    
    public CommandModifierBloc(String nouvType) {
        nouveauType = nouvType;
    }
    
    public void exécuter(Bloc b) {
        ancienType = b.getType();
        b.setType(nouvType);
    }
}
```
