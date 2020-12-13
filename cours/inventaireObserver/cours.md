# Un système d'inventaire
Dans ce cours nous allons apprendre le Design Pattern "Observer" par le biais d'un inventaire de RPG, ce qui va aussi nous permettre de réviser le Polymorphisme.

# Design Patterns ?
Un Design Pattern est une technique de programmation qui permet, lorsqu'elle est mise en place, de faire certaines choses plus proprement et plus simplement dans votre programme. 

En effet, il est souvent difficile de réinventer la technique parfaite pour faire telle ou telle chose dans un logiciel, de la même manière qu'il est difficile de réinventer la recette parfaite de la ratatouille de votre grand-mère. 

C'est tout simplement parce que ces Design Patterns sont issus de longues recherches au sein de l'industrie du logiciel par des équipes entières. Mais leur pertinence réside dans leur simplicité de mise en place. 

Dès lors, savoir en utiliser un certain nombre vous insufflera la puissance des anciens geeks du mont IBM et du temple MIT, ce qui peut s'avérer plutôt efficace.

# Ok, mais on veut faire quoi au juste ?
C'est une excellente question, qu'il faut toujours se poser avant de dégainer vos meilleurs Design Patterns, car employés aux mauvais endroit ils ne font que compliquer le code pour rien.

## Un inventaire automatisé
On veut un inventaire. Notre inventaire contient des Items. Et en plus, nos Items peuvent être n'importe quoi comme dans un RPG, par exemple du pain, un tas de pièces, une épée.

Comme on sait qu'on va faire un RPG, chaque type d'item aura des statistiques et comportements différents. Le pain aura une date de péremption et pourra être mangé. Le tas de pièce pourra être échangé en partie jusqu'à-ce qu'il n'en reste rien, et l'épée aura une durabilité et finira par casser.

Mais tous ces items seront en vrac dans notre inventaire, qui doit être capable de tous les contenir, mais surtout de les faire disparaître une fois qu'ils sont détruits/consommés/vendus.

Ce qui pose plusieurs soucis :

- Comment faire en sorte de stocker plusieurs types différents dans une même liste ?
- Comment faire savoir à l'inventaire quand un item n'est plus utilisable et doit donc être retiré de l'inventaire (sinon on se retrouverait dans une situation où le joueur pourrait manger un pain périmé, ou vendre un tas d'or sans or) ?

# Un seul inventaire, plusieurs types d'items
C'est dans ce cas de figure que l'orienté objet devient vraiment intéressant grâce au polymorphisme.

En effet, une solution à ce problème est d'utiliser l'héritage. On a une classe `Player` qui comprend un attribut `inventory` dont le type est une liste d'`Item`, avec `Item` une autre classe. Si on fait ensuite une classe `Pain`, une classe `Epee`, une classe `TasDePiece`, qui héritent de la classe `Item`, eh bien on pourra stocker leurs instances dans la liste d'items.

Comme on veut éviter de mettre deux fois la même tranche de pain ou deux fois le même tas de pièces dans l'inventaire, ce qui serait franchement bizarre car dans la vraie vie les objets ne se dupliquent pas dans votre poche, on ne va pas utiliser une liste, mais un `Set` (= un Ensemble en français) qui vérifiera automatiquement qu'on ne mettra pas notre item dans l'inventaire s'il y est déjà.

```java
// Player.java

// HashSet est le type de Set dont on va se servir
import java.util.HashSet;

public class Player {
    private HashSet<Item> inventory = new HashSet<Item>();
    
    public void ajouterItem(Item item) {
        inventory.add(item);
    }
} 
```

Ensuite il faut créer la classe `Item` et on lui donnera un attribut `String name` car on part du principe que tous les items seront nommés. Grâce à l'héritage, tous nos types hérités d'`Item` auront cet attribut même si on ne le recopie pas partout :

```java
// Item.java
public class Item {
    private String name;
    
    public Item(String name) {
        this.name = name;
    }
}
```

Maintenant créons d'autres items pour s'amuser un peu :

```java
// Pain.java
// Le mot clé extends sert à dire que Pain hérite d'Item
public class Pain extends Item {
    private float durabilite;
    
    public Pain(String name, float durabiliteMax) {
        super(name);
        durabilite = durabiliteMax;
    } 
}
```
```java
// TasDor.java
public class TasDor extends Item {
    private int nbPieces;
    
    public TasDor(String name, int nbPieces) {
        super(name);
        this.nbPieces = nbPieces;
    }
}
```

Maintenant si on crée un inventaire et qu'on y met du pain à côté d'un tas d'or tout va bien, notre programme fonctionnera.

```java
// Main.java
// ...
public static void main(String[] args) {
    Player joueur = new Player();
    joueur.ajouterItem(new Pain("Le quignon de Michel", 1.0));
    joueur.ajouterItem(new TasDor("Trésor du capitaine", 300));
}
// ...
```

# Plusieurs items, plusieurs manières de disparaître

