// cardlist.h
// 
// Librairie contenant des routines utilitaires pour
// la gestion d'une liste de cartes, implémentée
// sous la forme d'une structure chainée simple
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>
#include <stdlib.h>

#include "deck.h"

#ifndef TLW_CARDLIST
#define TLW_CARDLIST

struct node
{
	CARD *card;
	struct node *next;
};
typedef struct node NODE;

struct cardlist
{
	int size;
	NODE* first;
	NODE* last;
};
typedef struct cardlist CARDLIST;

// Crée une nouvelle liste de cartes, vide
CARDLIST* newCardList();

// Récupère la ième carte de la liste
CARD* getCard (CARDLIST *cardlist, int i);

// Ajoute une carte à la fin de la liste
void addCardLast (CARDLIST *cardlist, CARD *card);

// Supprime la ième carte de la liste
CARD* removeCard (CARDLIST *cardlist, int i);

// Affiche la liste de cartes
void printCardList (CARDLIST* cardlist);

#endif
