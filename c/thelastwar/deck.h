// deck.h
// 
// Librairie contenant des routines utilitaires pour
// la gestion d'un deck de carte
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#ifndef TLW_DECK
#define TLW_DECK

struct card
{
	char *name;
	int life;
	int cost;
	int attack;
};
typedef struct card CARD;

struct deck
{
	int capacity;
	int size;
	CARD** cards;
};
typedef struct deck DECK;

// Crée une nouvelle carte
CARD* newCard (char* name, int life, int cost, int attack);

// Affiche une carte sous la forme :
// "Nom du personnage" (Vie, Cout, Attaque)
void printCard (CARD *card);

// Charge un deck depuis un fichier texte. La première ligne du fichier contient
// le nombre de cartes dans le fichier, et les lignes suivantes sont de la forme :
// - Nom du personnage
// - Points de vie
// - Cout
// - Points d'attaque
DECK* loadDeck (char *path);

// Affiche un deck de cartes sous la forme :
// ===Deck avec XXX cartes restantes (YYY en tout)
// (liste des cartes)
void printDeck (DECK *deck);

// Retire et renvoie la carte se trouvant au sommet du deck
CARD* popCard (DECK *deck);

#endif
