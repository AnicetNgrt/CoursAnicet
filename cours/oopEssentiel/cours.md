# Quelques notions d'OOP pour faire des jeux
L'OOP, ou Object Oriented Programming (programmation orientée objet en français) est un paradigme de programmation, c'est à dire une abstraction ou une structuration particulière du code pour simplifier l'expression de certaines logiques.

# Paradigme ?
Le paradigme de base en programmation est dit "impératif", c'est à dire que l'on programme avec des simples instructions à la suite les unes des autres. Fait ci, puis fait ça, puis fait ci ... Parce que si vous utilisez les langages les plus bas niveau, c'est à dire les plus proches de la machine, donc présentant un niveau d'abstraction bas, alors vous n'avez pas d'autre choix que d'exécuter les actions une à une car la machine ne sait pas faire autrement.

Le C, par chance, vous laisse faire des `if` des `else` et des `for` ou encore même des `while`, des opérations de `flow control`, mais il n'existe pas de telle chose pour la machine, ce n'est là aussi qu'une abstraction, un tours de passe passe qui vous donnent l'illusion que votre code n'est pas linéaire alors qu'en réalité il l'est. 

Ce que fait le C lorsque l'on écrit `while` ou `if`, ou du moins le code machine compilé à partir du code en C, est qu'il change l'adresse mémoire de la prochaine ligne de code à exécuter par l'ordinateur afin de, par exemple, le faire boucler sur le même bout de code à l'infini, ou le faire aller vers du code dans un `else`.

# Orienté objet
L'orienté objet est donc un paradigme construit au dessus du paradigme impératif, il permet de simplifier certaines logiques. Mais il est totalement optionnel. De même que le code machine peut faire des boucles sans `while` en changeant l'adresse mémoire de la prochaine ligne, le C peut répliquer tous les mécanismes de l'orienté objet, mais c'est plus long et moins élégant.

L'orienté objet consiste en son cœur à relier fortement les données et les fonctions qui modifient ces données.

Prenons le cas d'une sphère que l'on peut tourner d'un certain angle à l'écran. En C, les fonctions et les données sont séparées, il faut passer une référence de la structure sphère dans les prototypes de chaque fonction qui s'en sert. Et il faut se discipliner à bien utiliser des références afin que ce soit plus performant et à bien mettre la donnée modifiée en premier dans le prototype pour simplifier la lecture :

```c
typedef struct Sphere {
    int x, y, z;
    float angle;
};

Sphere* sphere_initialiser(int x, int y, int z, float angle) {
    Sphere sphere = { x, y, z, angle };
    return &sphere;
}

void sphere_tourner(Sphere *sphere, float rotation) {
    sphere->angle += rotation;
}
```

Alors qu'avec un langage orienté fonction, tel que le Java, on a des "classes", des sortes de `struct` avec des fonctions, sauf que ces fonctions, appelées "méthodes" sont appelée depuis les instances de la classe et sont donc capables de les éditer sans avoir à les réécrire dans le prototype :

```java
public class Sphere {
    // Cette partie déclare les attributs comme les struct
    private int x;
    private int y;
    private int z;
    private float angle;
    
    // Le constructeur (initialise une instance de Sphere)
    public Sphere(int x, int y, int z, float angle) {
        this.x = x; // "this" fait référence à l'instance de Sphère créée
        this.y = y;
        this.z = z;
        this.angle = angle;
    }
    
    // Les méthodes (modifient une instance de Sphere)
    public void tourner(float rotation) {
        // Pas besoin d'utiliser "this" si il n'y a pas de conflits
        // de noms avec d'autres variables dans le contexte d'exécution
        // en cours, c.a.d. s'il n'y a pas d'autres variables nommées
        // "angle" que la propriété de la classe
        angle += rotation;
    }
}
```

Pour éditer tourner une sphère en C il faudrait faire :

```c
int main(int argc, char *argv[]) {
    Sphere *sphere = sphere_initialiser(1, 1, 1, 18.5);
    sphere_tourner(sphere, 15.17);
    return 0;
}
```

alors qu'en Java (comme dans d'autres langages OOP) :

```java
public static void main(String args[]) {
    // raccourcis "new" pour avoir le constructeur en java
    Sphere sphere = new Sphere(1, 1, 1, 18.5);
    // on appelle la méthode tourner sur notre instance sphere
    sphere.tourner(15.17);
}
```

On a donc un léger gain en lisibilité avec le Java, mais ce n'est pas le cas avec tous les langages orientés objet à ce stade. Là où l'orienté objet devient vraiment intéressant, c'est lorsque l'on utilise les nouvelles relations qu'il introduit, telles que l'héritage.

# Héritage
L'héritage permet de construire les classes les unes au dessus des autres, comme un jeu de poupées russes, afin de réemployer facilement des comportements et des données communs à plusieurs morceaux du code.

Pour apprendre l'héritage l'on montre souvent des cas de figures totalement détachés de la réalité, comme un programme manipulant des modèles de voitures ou des espèces animales. Mais cette relation d'héritage revient surtout dans la conception d'interface, et donc aussi dans la conception de mondes virtuels. Donc prenons pour exemple une librairie de composants d'interface.

Vous avez le composant de base, appelé sobrement `Rectangle` qui est un rectangle coloré qui peut être affiché à l'écran. Il a donc naturellement des coordonnées, une taille, et une couleur. Puis il y a son petit frère, le bouton de la classe `Button`, un rectangle, mais avec du texte dessus et 3 couleurs : la couleur quand il ne se passe rien, la couleur quand la souris passe dessus (hovered state), et la couleur quand il est appuyé (pressed state).

Si l'on veut faire partager au bouton les attributs du rectangle sans recopier bêtement les même choses dans les deux structs, on peut utiliser la composition pour dire que le bouton a aussi une partie rectangle en lui :

```c
typedef struct Color {
    unsigned char r, g, b, a; // intensité comprise entre 0 et 255
}

typedef struct Rectangle {
    int x, y, z; // Coordonnées
    int w, h; // Taille
    Color* color;
}

typedef struct Button {
    Rectangle *rectangle_part;
    char* text;
    Color* color_hovered;
    Color* color_pressed;
}
```

Maintenant, si on veut manipuler la partie `Rectangle` d'un `Button` c'est pas super pratique, et il ne faut surtout pas se planter sur les pointeurs sinon on a des bugs partout :

```c
int main() {
    const Color red = { 1.0, 0.0, 0.0, 1.0 };
    const Color green = { 0.0, 1.0, 0.0, 1.0 };
    const Color white = {1.0, 1.0, 1.0, 1.0 };
    
    Rectangle rectangle_part = { 1, 1, 2, 100, 250, &red };
    Button button = { &rectangle_part, "Clique moi !!!", &green, &white };
    
    int button_width = button.rectangle_part->w; // C'est long
    
    return 0;
}
```

En OOP, on crée deux classes Rectangle et `Button` à la place des structs, mais au lieu de mettre une référence vers une rectangle dans chaque boutton, on dit que `Button` hérite de `Rectangle` et on obient une classe `Button` qui contient aussi tous les attributs et toutes les méthodes de `Rectangle`.

```java
public class Rectangle {
    public int x;
    public int y;
    public int z;
    public int w;
    public int h;
    public Color color;
}

public class Button extends Rectangle { // "extends" = "hérite de" 
    public String text;
    public Color color_pressed;
    public Color color_hovered;
}
```

Et maintenant, quand on s'en sert c'est bien plus simple :

```java
public static void main(String[] args) {
    Button button = new Button();
    button.w = 1; // ça compile !
}
```

Ce qui est encore plus pratique, est que `button` est simultanément de type `Rectangle` et de type `Button`. Ce qui veut dire qu'on peut le mettre dans une liste de `Rectangle` ou dans une liste de `Button`. Mais lorsqu'on le récupérera depuis une liste de `Rectangle`, il ne sera plus considéré comme un `Button`. En effet, tous les `Button` sont des instances de `Rectangle` mais pas nécessairement l'inverse. Heureusement, en Java et dans plein d'autres langages OOP strictement typés, on peut tester si notre rectangle est bien de type `Button` avec le mot-clé `instanceof` et le caster ensuite.

Cette relation d'héritage ne permet pas tout, vous allez toujours avoir besoin de la composition dans la plupart des cas, mais quand vous devez mettre plusieurs types d'objets similaires, mais ayant des comportements différents, dans un même endroit, comme un inventaire dans un jeu vidéo par exemple, il n'y a pas vraiment d'alternatives.