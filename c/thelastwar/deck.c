// deck.c
// 
// Librairie contenant des routines utilitaires pour
// la gestion d'un deck de carte
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>
#include <stdlib.h>

#include "util.h"
#include "deck.h"

#define BUFFER_SIZE 80

CARD* createCard (char* name, int life, int cost, int attack)
{
	CARD *card = malloc (sizeof (CARD));
	card->name = name;
	card->life = life;
	card->cost = cost;
	card->attack = attack;
	return card;
}

void printCard (CARD *card)
{
	printf ("\"%s\" (V: %d, C: %d, A: %d)\n", card->name, card->life, card->cost, card->attack);
}

DECK* loadDeck (char *path)
{
	DECK *deck = NULL;
	
	FILE *file = fopen (path, "r");
	if (file != NULL)
	{
		deck = malloc (sizeof (DECK));
		char buffer[BUFFER_SIZE];
		
		// Lecture du nombre de cartes
		fgets (buffer, BUFFER_SIZE, file);
		deck->capacity = atoi (buffer);
		deck->size = 0;
		deck->cards = malloc (deck->capacity * sizeof (CARD*));
		
		// Lecture des cartes
		int i;
		for (i = 0; i < deck->capacity; i++)
		{
			char *name = readString (file);
			deck->cards[i] = createCard (name, readInt (file), readInt (file), readInt (file));
		}
		
		fclose (file);
	}
	
	return deck;
}

void printDeck (DECK *deck)
{
	printf ("===Deck avec %d cartes restantes (%d en tout)\n", deck->capacity - deck->size, deck->capacity);
	int i;
	for (i = deck->size; i < deck->capacity; i++)
	{
		printCard (deck->cards[i]);
	}
}

CARD* popCard (DECK *deck)
{
	if (deck->size == deck->capacity)
	{
		return NULL;
	}
	
	deck->size++;
	return deck->cards[deck->size - 1];
}
