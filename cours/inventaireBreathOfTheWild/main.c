#include <cassert>

// ---------------------------------------
// Pattern observer/observable
typedef int Event;
typedef enum {TRUE = 1, FALSE = 0} Bool;
typedef enum {EVENT_NONE = 0} BaseEvents;

typedef struct {
    Event event;
    EventChain* next;
    Bool isLast;
} EventChain;

typedef struct {
    EventChain* pendingEvents;
    Bool hasPendingEvents;
} Observer;

typedef struct {
    ObserverChain* subscribers;
    Bool hasSubscribers;
} Observable;

typedef struct {
    Observer* pointer;
    ObserverChain* next;
    Bool isLast;
} ObserverChain;
// ---------------------------------------

// ---------------------------------------
// Données du jeu
typedef struct {
    Observable* asObservable;
    Observer* asObserver;
} Entity;

typedef struct {
    enum {
        ITEM_NAME_MAX_LENGTH = 32
    };
    char name[ITEM_NAME_MAX_LENGTH];
    unsigned int maxDurability;
} ItemStatic;

typedef struct {
    Entity* asEntity;
    ItemStatic* schema;
    unsigned int durability;
} ItemInstance;

typedef struct {
    Entity* asEntity; 
    ItemInstance* inventory;
} Inventory;

typedef struct {
    Entity* asEntity;
    Inventory* inventory;
    unsigned int x;
    unsigned int y;
} Character;

typedef struct {
    enum {
        WORLD_WIDTH = 999,
        WORLD_HEIGHT = 999,
        GAME_ITEM_COUNT = 3,
        GAME_NPC_COUNT = 3
    };
    ItemStatic* gameItems;
    unsigned int gameItemCount;
    Character* npcs;
    unsigned int npcCount;
    Character player;
    unsigned int turn;
} Game;
// ---------------------------------------

void itemstatic_set_name(ItemStatic* item, char* name) {
    strncpy(item->name, name, sizeof(char)*ITEM_NAME_MAX_LENGTH - 1);
}

void game_set_itemstatic(Game* game, unsigned int index, char* name, unsigned int maxDurability) {
    assert(index < game->gameItemCount);

    itemstatic_set_name(&(game->gameItems[index]), name);
}

void game_set_npc(Game* game, unsigned int index, )

void game_loop(Game* game) {
    while (TRUE) {

    }
}

Game* create_game() {
    Game game;

    game.turn = 10;

    game.gameItems = malloc(sizeof(ItemStatic) * GAME_ITEM_COUNT);
    game_set_itemstatic(&game, 0, "Sword", 5);
    game_set_itemstatic(&game, 1, "Axe", 3);
    game_set_itemstatic(&game, 2, "Bow", 2);

    game.npcs = malloc(sizeof(Character) * GAME_NPC_COUNT);
    

    return &game;
}

void destroy_game(Game* game) {
    free(game->gameItems);
    free(game->npcs);
}

void main() {
    Game* game = create_game();
    game_loop(game);
}