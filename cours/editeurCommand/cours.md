# Le Command Pattern
Cette fois-ci, c'est au tour d'un autre Design Pattern, nommé le "Command Pattern". Ce pattern permet d'encoder et de stocker dans des variables les actions et les transformations susceptibles d'êtres annullées, répétées ou copiées un nombre arbitraire de fois. Ce qui permet de coder l'action "Undo" (le fameux ctrl+z) et "Redo" (souvent un ctrl+y) dans un logiciel, comme un éditeur de texte, un photoshop-like, ou tout simplement un éditeur de map dans un jeu vidéo. Il permet aussi d'encoder la liste des actions successives entreprises par l'utilisateur, afin de les rejouer plus tard (fonctionnalité d'enregistrement de partie), de les envoyer sur le réseau (jeu en ligne), ou de les sauvegarder en mémoire.

# Un éditeur de map
L'implémentation de ce pattern peut être faite de plusieurs manières, plus ou moins strictes et élaborées, donc fixons nous les idées grâce à un exemple.

Imaginez que vous veniez d'être embauché par NADEO afin de travailler sur le prochain TrackMania. Comme tout TrackMania qui se respecte, votre jeu de courses de voitures sera doté d'un éditeur de map.

Vous décidez qu'une map (ou carte) soit composée de blocs (des morceaux de route ou de décor) dans une grille en trois dimensions. Chaque placement et modification de bloc devra pouvoir être annulé par un ctrl+z ou refait après annulation par un ctrl+y.

Afin de permettre le ctrl+z et le ctrl+y à l'infini, il va falloir encoder et stocker les actions du joueur successives dans des piles afin d'obtenir un mécanisme LIFO (last in, first out).

L'idée est que dès qu'une action sera faite, elle sera encodée puis mise tout en haut d'une pile. Puis lorsque le ctrl+z sera appuyé, une méthode inversant l'effet de l'action tout en haut de la pile sera appelée puis l'action en question sera déplacée en haut d'une deuxième pile. Et lorsque le ctrl+y sera appuyé, l'action en haut de la deuxième pile sera exécutée à nouveau puis redéplacée vers la première pile. Et ainsi de suite.

`action exécutée -> action mise en haut de la pile du ctrl+z -> ctrl+z -> action inversée -> action mise en haut de la pile du ctrl+y -> ctrl+y -> action (re)exécutée -> ...`

# Encoder les actions
On a donc plusieurs actions qu'il va faloir bien définir afin de pouvoir les encoder, les exécuter, et aussi les inverser.

## Placer un bloc
Placer un bloc consiste à prendre un bloc (on ne va pas rentrer dans les détails, mais libre à vous de créer une classe Bloc avec tous les attributs qui vous plaisent), une coordonnée en 3D et une grille en 3D puis à remplacer le bloc qui se trouve aux dites coordonnées par notre bloc.

Inverser le placement d'un bloc consiste à placer le bloc qui se trouvait aux dites coordonnées avant lui. S'il n'y avait pas de bloc avant lui, l'on placera du vide à nouveau (le bloc "vide" peut être un type de bloc à part entière, ou juste une référence vide telle que la valeur `null` en java).

Créons donc la classe `CommandPlacementBloc` qui va permettre d'encoder, d'exécuter et d'inverser l'action de placement d'un bloc :

```java
//CommandPlacementBloc.java
public class CommandPlacementBloc {
    private Bloc nouveauBloc;
    private Bloc ancienBloc;
    private Coord3D coord;
    private Bloc[][][] grille;
    
    public CommandPlacementBloc(Bloc nouvBloc, Coord3D coord, Bloc[][][] grille) {
        nouveauBloc = nouvBloc;
        this.coord = coord;
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

Et voilà, c'est si simple que ça. Une version alternative consiste à ne pas assigner immédiatement la grille à la commande. Comme ça l'on pourra exécuter cette action sur n'importe quelle grille. Ce qui peut servir si l'on décide de l'envoyer à un autre joueur par le réseau afin, par exemple, de coder une version collaborative de notre éditeur de map.

Pour cela, il suffit de modifier le constructeur et les méthodes exécuter et inverser :

```java
//CommandPlacementBloc.java
//...
// Supprimer la propriété private Bloc[][][] grille
//...
public CommandPlacementBloc(Bloc nouvBloc, Coord3D coord) {
    nouveauBloc = nouvBloc;
    this.coord = coord;
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

Créons maintenant une classe `Bloc` absolument bidon :

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

Je vous laisse coder la classe `Coord3D` avec un `x`, un `y` et un `z` entiers car c'est absolument trivial.

Maintenant, testons de placer un bloc et d'annuler son placement ensuite :

```java
//App.java
public class App {
    public static final int TAILLE = 10;
    
    public static void main(String[] args) {
        Bloc[][][] grille = new Bloc[TAILLE][TAILLE][TAILLE];
        remplirGrille(grille, "VIDE");
        
        Coord3D crd = new Coord3D(TAILLE-1); // Constructeur à coder 
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
//CommandModificationBloc.java
public class CommandModificationBloc {
    private String nouveauType;
    private String ancienType;
    
    public CommandModificationBloc(String nouvType) {
        nouveauType = nouvType;
    }
    
    public void exécuter(Bloc b) {
        ancienType = b.getType();
        b.setType(nouvType);
    }
    
    public void inverser(Bloc b) {
        b.setType(ancienType);
    }
}
```

Vous pouvez créer ainsi autant de commandes que nécessaire dans votre jeu.

# Coder le Undo et le Redo
Comme toutes nos actions sont inversables, l'on va pouvoir implémenter la mécanique de piles successives pour l'implémentation de l'Undo (aka le CTRL+Z) et du redo (CTRL+Y).

Pour cela, il suffit de créer une classe centrale qui contient les deux piles de commandes et qui présente des méthodes publiques à travers lesquelles toutes les actions du joueur seront effectuées.

Cependant, on a un petit problème. Nos deux classes `CommandPlacementBloc` et `CommandModificationBloc` ne peuvent pas être mises toutes les deux dans une même pile car ce ne sont pas les mêmes types. 

Or, elles présentent toutes les deux des similitudes, comme la possibilité de les exécuter et de les inverser. Il faudrait donc créer une interface `Command` avec une méthode exécuter et inverser que `CommandPlacementBloc` et `CommandModificationBloc` implémenteraient. 

Ainsi, elles pouraient toutes les deux être mises dans une même pile en tant que simples instances de `Command`. Et la fonction servant à faire les (re)exécutions et les inversions des commandes passées aura la garantie que toute instance trouvée dans une pile de `Command` sera exécutable et inversable.

```java
//Command.java
public interface Command {
    public void executer(Bloc[][][] grille);
    public void inverser(Bloc[][][] grille);
}
```

Mais ici, notre interface `Command` définit des commandes qui s'exécutent seulement sur des instances de type `Bloc[][][]`, c'est à dire sur une carte de jeu.

Or notre classe `CommandModificationBloc` définit une commande s'exécutant sur des instances de type `Bloc` tout court.

Heureusement, si l'on limite son usage aux blocs déjà présents sur la carte de jeu. Par exemple en lui donnant les coordonnées du bloc en question dans le constructeur et la carte de jeu à l'exécution, comme pour la commande de placement, alors elle pourra implémenter l'interface `Command` qu'on vient de définir :

```java
//CommandModificationBloc.java
public class CommandModificationBloc implements Command {
    private Coord3D coord;
    private String nouveauType;
    private String ancienType;
    
    public CommandModificationBloc(Coord3D coordonnees, String nouvType) {
        coord = coordonnees;
        nouveauType = nouvType;
    }
    
    public void exécuter(Bloc[][][] grille) {
        Bloc b = grille[coord.x()][coord.y()][coord.z()]
        ancienType = b.getType();
        b.setType(nouvType);
    }
    
    public void inverser(Bloc[][][] grille) {
        Bloc b = grille[coord.x()][coord.y()][coord.z()]
        b.setType(ancienType);
    }
}
```

Faisons aussi en sorte que `CommandPlacementBloc` implémente `Command` :

```java
public class CommandPlacementBloc implements Command {
//...
```

Maintenant, créons une classe `MapEditor` qui contiendra les piles de l'undo et du redo, ainsi que des méthodes permettant d'effectuer les actions de placement et de modification et aussi d'effectuer un undo et un redo :

```java
//Game.java
public class MapEditor {
    //Pile de l'undo
    private Stack<Command> undoStack = new Stack<Command>();
    //Pile du redo
    private Stack<Command> redoStack = new Stack<Command>();

    private Bloc[][][] carte;

    public MapEditor(Coord3D taille) {
        //On initialise la carte
        carte = new Bloc[taille.x()][taille.y()][taille.z()];
    }

    //exécuter une commande jamais exécutée auparavant
    private void exécuterCommand(Command c) {
        c.exécuter(carte);
        undoStack.add(c);
    }

    /* Undo & Redo */

    public void undo() {
        Command c = undoStack.pop(); //on retire et récupère le dernier élement
        c.exécuter(carte);
        redoStack.push(c); //on ajouter à la pile du redo
    }

    public void redo() {
        Command c = redoStack.pop();
        c.exécuter(carte);
        undoStack.push(c);
    }


    /* Nos commandes persos */

    public void placerBloc(Bloc b, Coord3D coord) {
        CommandPlacementBloc c = new CommandPlacementBloc(b, coord);
        exécuterCommand(c);
    }

    public void modifierBloc(Coord3D coord, String nouvType) {
        CommandModificationBloc c = new CommandModificationBloc(coord, nouvType);
        exécuterCommand(c);
    }
}
```

Et voilà, tant que l'on implémentera correctement l'interface `Command` pour nos diverses actions et que l'on ajoutera pour chacune d'elles une méthode dans la classe `MapEditor` alors elles seront affectées lors d'un undo ou d'un redo.

Les autres classes de notre projet pourront appeler les méthodes de `MapEditor` quand le joueur appuira sur certaines touches, sans même avoir à se soucier de notre système de commandes. Ce qui facilite aussi le travail d'équipe.

# Pistes d'amélioration

- Relier une classe capable de capter les touches appuyées par l'utilisateur à notre classe `MapEditor` afin que le ctrl+z et le ctrl+y entraînent un undo et un redo, par exemple à l'aide de l'Observer Pattern vu précédemment.
- Remplir par défaut la carte vide créée dans le constructeur `MapEditor` avec des `new Bloc("VIDE")` car sinon n'importe quelle opération de modification sur un bloc vide résulterait en une `NullPointerException` car la classe `CommandModificationBloc` manipulerait des `null` à la place de vrais blocs.
- Créer un enum ou des instances constantes d'une classe `TypeBloc` pour les types de blocs plutôt que des `String` comme `"VIDE"` ou `"ROUTE"` afin d'étendre les fonctionnalités de nos blocs et de les documenter.