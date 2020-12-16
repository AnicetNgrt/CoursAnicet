# Un système d'inventaire
Dans ce cours nous allons revoir le polymorphisme et apprendre le Design Pattern "Observer" par le biais d'un inventaire de jeux vidéo, ce qui va aussi nous permettre de réviser le Polymorphisme.

# Design Patterns ?
Un Design Pattern est une technique de programmation qui permet, lorsqu'elle est mise en place, de faire certaines choses plus proprement et plus simplement dans votre programme. 

En effet, il est souvent difficile de réinventer la technique parfaite pour faire telle ou telle chose dans un logiciel, de la même manière qu'il est difficile de réinventer la recette parfaite de la ratatouille de votre grand-mère. 

C'est tout simplement parce que ces Design Patterns sont issus de longues recherches au sein de l'industrie du logiciel par des équipes entières. Mais leur pertinence réside dans leur simplicité de mise en place. 

Dès lors, savoir en utiliser un certain nombre vous insufflera la puissance des anciens geeks du mont IBM et du temple MIT, ce qui peut s'avérer plutôt efficace.

# Ok, mais on veut faire quoi au juste ?
C'est une excellente question, qu'il faut toujours se poser avant de dégainer vos meilleurs Design Patterns, car employés aux mauvais endroit ils ne font que compliquer le code pour rien.

## Un inventaire automatisé
On veut un inventaire. Notre inventaire contient des Items. Et en plus, nos Items peuvent être n'importe quoi, par exemple du pain, un tas de pièces, une épée ...

Comme dans n'importe quel jeu vidéo, chaque type d'item aura des statistiques et comportements différents. Le pain aura une date de péremption et pourra être mangé. Le tas de pièce pourra être échangé en partie jusqu'à-ce qu'il n'en reste rien, et l'épée aura une durabilité et finira par casser.

Mais tous ces items seront en vrac dans notre inventaire, qui doit être capable de tous les contenir, mais surtout de les faire disparaître une fois qu'ils sont détruits/consommés/vendus.

Ce qui pose plusieurs soucis :

- Comment faire en sorte de stocker plusieurs types différents dans une même liste ?
- Comment faire savoir à l'inventaire quand un item n'est plus utilisable et doit donc être retiré de l'inventaire (sinon on se retrouverait dans une situation où le joueur pourrait manger un pain périmé, ou vendre un tas d'or sans or) ?

# Un seul inventaire, plusieurs types d'items
C'est dans ce cas de figure que l' OOP (object oriented programming), la programmation orientée objet, devient vraiment intéressant grâce au polymorphisme.

En effet, une solution à ce problème est d'utiliser l'héritage. On a une classe `Player` qui comprend un attribut `inventory` dont le type est une liste d'`Item`, avec `Item` une autre classe. Si on fait ensuite une classe `Pain`, une classe `Epee`, une classe `TasDePiece`, qui héritent de la classe `Item`, eh bien on pourra stocker leurs instances dans la liste d'items. Ensuite on pourra donner des propriétés et méthodes différentes à toutes ces classes héritées d'item selon le gameplay qu'on a prévu pour chacun d'eux, mais ils pourront tous être mis dans la même liste.

Détail important : comme on veut éviter de mettre deux fois la même tranche de pain ou deux fois le même tas de pièces dans l'inventaire, ce qui serait franchement bizarre car dans la vraie vie les objets ne se dupliquent pas dans votre poche, on ne va pas utiliser une liste, mais un `Set` (= un Ensemble en français) qui vérifiera automatiquement qu'on ne met pas un item dans l'inventaire s'il y est déjà.

```java
// Player.java

// HashSet est le type de Set dont on va se servir
import java.util.HashSet;

public class Player {
    private HashSet<Item> inventory = new HashSet<Item>();
    
    public void ajouterItem(Item item) {
        inventory.add(item);
    }
    
    public void retirerItem(Item item) {
        inventory.remove(item);
    }
} 
```

Ensuite il faut créer la classe `Item` et on lui donnera un attribut `String name` car on part du principe que tous les items seront nommés. Grâce à l'héritage, tous nos types hérités d'`Item` auront cet attribut même si on ne le recopie pas partout. On va aussi mettre `abstract`pour dire que la classe Item ne peut pas être utilisée tel-quel. Elle devra impérativement être héritée pour pouvoir être instanciée. Ce qui est logique puisqu'on peut créer du pain, un sac de pièces d'or, une épée, mais un "Item". C'est trop vague, on dit que c'est une "abstraction", un terme qui en désigne plein d'autres mais qui ne veut rien dire tout seul. C'est comme "truc" ou "machin". On peut dire que ton stylo est un "machin", mais on ne peut pas dire ce qu'est un "machin" à tous les coups.

```java
// Item.java
public abstract class Item {
    protected String name; 
    // protected = private, mais les classes héritées
    // auront quand même accès à l'attribut.
    
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

Maintenant si on crée un inventaire et qu'on y met du pain à côté d'un tas d'or tout va bien, notre programme compile.

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

# Observer pattern
Maintenant qu'on est capable de mettre plusieurs types d'items dans un même inventaire, on peut passer au vrai problème : Comment supprimer un item dans l'inventaire dès qu'il devient inutilisable, alors que chaque item a sa propre manière de devenir inutilisable ?

Il y a plusieurs solutions à ce problème qui sont plus ou moins optimisées et élégantes. Mais voici celle que l'on va utiliser :

On va mettre en place un système de notification entre nos classes, un peu comme si elles pouvaient s'abonner à des newsletter que d'autres classes viendraient alimenter. 

Pour cela on va créer une classe Observer, qui pourra être notifiée par un appel de méthode quand une instance d'une classe Observable aura décidé de le notifier. Comme ça on pourra faire hériter notre Player d'Observer pour que la classe Item, qui héritera donc d'Observable, le notifie quand il devra disparaître.

Le Player pourra s'abonner et se désabonner des items de son inventaire pour être tenu au courant quand les items le notifieront. (Et ne pas l'être quand un item qui n'est pas dans son inventaire disparaît, sinon c'est du spam). Comme ça, les items pourront notifier le joueur quand ils deviennent inutilisables, peu importe la manière dont ça arrive.

```java
//Observable.java
import java.util.HashSet;

public class Observable {
    private HashSet<Observer> abonnes = new HashSet<Observer>();
    
    private void notifierAbonnes(String raison) {
        for(Observer o:abonnes)
            o.notifier(this, raison);
    }
    
    public void subscribe(Observer abonne) {
        abonnes.add(abonne);
    }
    
    public void unsubscribe(Observer abonne) {
        abonnes.remove(abonne);
    }
}
```

Observer sera une Interface, ce qui veut dire qu'on ne fait que déclarer ses méthodes mais pas le code qu'il y a dedans. Mais toutes les classes qui implémenterons ensuite Observer devront mettre du code dans toute ses méthodes. C'est une façon de garantir à n'importe quel bout de code qui a affaire à un Observer, quelle que soit la classe qui se cache réellement derrière, qu'il se passera quelque chose s'il appelle les méthodes en question.

De cette manière, tous les Observer pourront être notifiés, mais il sera libre à chaque classe implémentant Observer de définir la manière dont elle réagira aux différentes notifications.

```java
//Observer.java
public interface Observer {
    public void notifier(Observable origine, String raison);
}
```

Maintenant il faut faire en sorte que Player implémente Observer :

```java
//Player.java
//...
public class Player implements Observer {
    //...
    public void ajouterItem(Item item) {
        inventory.add(item);
        // Et on s'abonne pour savoir s'il disparaît
        item.subscribe(this);
    }
    
    public void retirerItem(Item item) {
        inventory.remove(item);
        // Et on se désabonne car on a plus besoin de savoir
        item.unsubscribe(this);
    }
    
    public void notifier(Observable origine, String raison) {
        if(origine instanceof Item && raison == "Item inutilisable") {
            retirerItem((Item) origine);
            // On doit "caster" origine de type Observable vers Item
        }
    }
    //...
```

# Plusieurs manières de disparaître
Maintenant que nos items peuvent prévenir notre player quand ils disparaissent, il faut coder les différents moyen de disparaître pour chacun de ces types d'items.

