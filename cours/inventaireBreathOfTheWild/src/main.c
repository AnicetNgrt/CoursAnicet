// ---------------------------------------
// Pattern observer/observable
typedef int Event;
typedef enum {TRUE = 1, FALSE = 0} Bool;
typedef enum {EVENT_NONE = 0} BaseEvents;

typedef struct {
    Event event = EVENT_NONE;
    EventChain* next = NULL;
    Bool isLast = TRUE;
} EventChain;

typedef struct {
    EventChain* pendingEvents = NULL;
    Bool hasPendingEvents = FALSE;
} Observer;

typedef struct {
    ObserverChain* subscribers = NULL;
    Bool hasSubscribers = FALSE;
} Observable;

typedef struct {
    Observer* pointer;
    ObserverChain* next = NULL;
    Bool isLast = TRUE;
} ObserverChain;
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
    Character[] npcs = Character[MAX_NPC];
    Character* player;
    unsigned int turn = 0;
} Game;
// ---------------------------------------

void game_loop(Game* game) {
    while (true) {

    }
}

Game* create_game() {
    Game game;
    game.gameItems = ItemSchema[] {
        
    };
    return &game;
}

void main() {
    Game* game = create_game();
    game_loop(game);
}