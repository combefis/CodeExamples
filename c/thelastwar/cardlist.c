// cardlist.c
// 
// Librairie contenant des routines utilitaires pour
// la gestion d'une liste de cartes, implémentée
// sous la forme d'une structure chainée simple
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>
#include <stdlib.h>

#include "cardlist.h"

NODE *newNode (CARD *card, NODE *next)
{
	NODE *newnode = malloc (sizeof (NODE));
	newnode->card = card;
	newnode->next = next;
	return newnode;
}

CARDLIST* newCardList()
{
	CARDLIST *cardlist = malloc (sizeof (CARDLIST));
	cardlist->size = 0;
	cardlist->first = NULL;
	cardlist->last = NULL;
	return cardlist;
}

CARD* getCard (CARDLIST *cardlist, int i)
{
	if (i < 0 || i >= cardlist->size)
	{
		return NULL;
	}
	
	int j;
	NODE *current = cardlist->first;
	for (j = 0; j < i; j++)
	{
		current = current->next;
	}
	return current->card;
}

void addCardLast (CARDLIST *cardlist, CARD *card)
{
	NODE *newnode = newNode (card, NULL);
	
	// Cas de la liste vide
	if (cardlist->size == 0)
	{
		cardlist->first = newnode;
	}
	else
	{
		cardlist->last->next = newnode;
	}
	cardlist->last = newnode;
	cardlist->size++;
}

// Retire et renvoie la ième carte de la liste
CARD* removeCard (CARDLIST *cardlist, int i)
{
	if (i < 0 || i >= cardlist->size)
	{
		return NULL;
	}
	
	CARD *card = NULL;
	
	// Cas de la liste à un seul élément
	if (cardlist->size == 1 && i == 0)
	{
		card = cardlist->first->card;
		cardlist->first = NULL;
		cardlist->last = NULL;
	}
	else
	{
		NODE *toremove = cardlist->first;
		
		// Celui à supprimer est le premier
		if (i == 0)
		{
			cardlist->first = cardlist->first->next;
			toremove->next = NULL;
			card = toremove->card;
		}
		// Il faut se placer avant celui à supprimer
		else
		{
			int j;
			for (j = 1; j < i; j++)
			{
				toremove = toremove->next;
			}
			card = toremove->next->card;
			
			// Celui à supprimer est le dernier
			if (i == cardlist->size - 1)
			{
				toremove->next = NULL;
				cardlist->last = toremove;
			}
			else
			{
				toremove->next = toremove->next->next;
			}
		}
	}
	cardlist->size--;
	
	return card;
}

void printCardList (CARDLIST* cardlist)
{
	if (cardlist->size == 0)
	{
		printf ("[]\n");
		return;
	}
	
	NODE *current = cardlist->first;
	printf ("[0: \"%s\" (V: %d, C: %d, A: %d)", current->card->name, current->card->life, current->card->cost, current->card->attack);
	int i = 1;
	while (current->next != NULL)
	{
		printf (",\n%i: \"%s\" (V: %d, C: %d, A: %d)", i, current->next->card->name, current->next->card->life, current->next->card->cost, current->next->card->attack);
		current = current->next;
		i++;
	}
	printf ("]\n");
}
