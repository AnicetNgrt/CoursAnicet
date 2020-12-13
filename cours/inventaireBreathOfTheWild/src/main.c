// ---------------------------------------
// Pattern observer/observable
typedef struct {
    int a;
} Observer;

typedef struct {
    ObserverLink* subscribers;
} Observable;

typedef struct {
    Observer* pointer;
    ObserverLink* next;
    unsigned int isLast;
} ObserverLink;
// ---------------------------------------

// ---------------------------------------
// Données du jeu
typedef struct {
    Observable* asObservable;
    Observer* asObserver;
} Entity;

typedef struct {
    char[] name;
    unsigned int maxDurability;
} ItemStatic;

typedef struct {
    Entity* asEntity;
    ItemSchema* schema;
    unsigned int durability;
} ItemInstance;

typedef struct {
    enum {
        ABSOLUTE_INVENTORY_MAX_SIZE = 1000
    };
    Entity* asEntity; 
    ItemInstance[] inventory = ItemInstance[ABSOLUTE_INVENTORY_MAX_SIZE];
    unsigned int inventorySize;
    unsigned int inventoryMaxSize;
} Inventory;

typedef struct {
    Entity* asEntity;
    Inventory* inventory;
    unsigned int x;
    unsigned int y;
} Character;

typedef struct {
    enum {
        WORLD_WIDTH = 999;
        WORLD_HEIGHT = 999;
        MAX_NPC = 50;
    }
    ItemSchema[] gameItems;
    Character* player;
    Character[] npcs = Character[MAX_NPC];
    unsigned int turn = 0;
} Game;
// ---------------------------------------


void update() {

}

void gameLoop() {
    while (true) {

    }
}

void main() {
    
}