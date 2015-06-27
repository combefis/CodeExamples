// main.c
// 
// Fichier principal du jeu "The Last War"
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <time.h>

#include "util.h"
#include "deck.h"
#include "cardlist.h"

#define BUFFER_SIZE 80
#define NUM_PLAYERS 2
#define INIT_NB_CARDS 7

struct gameboard
{
	CARDLIST *hand;
	int resources;
	CARD *attack;
};
typedef struct gameboard GAMEBOARD;

struct player
{
	char *name;
	int life;
	DECK *deck;
	GAMEBOARD *gameboard;
};
typedef struct player PLAYER;

GAMEBOARD* newGameBoard()
{
	GAMEBOARD *gameboard = malloc (sizeof (GAMEBOARD));
	gameboard->hand = newCardList();
	gameboard->resources = 0;
	gameboard->attack = NULL;
	return gameboard;
}

PLAYER* newPlayer (char *name, char *deckfile)
{
	PLAYER *player = malloc (sizeof (PLAYER));
	char *copy = strCopy (name);
	player->name = copy;
	player->life = 2;
	player->deck = loadDeck (deckfile);
	player->gameboard = newGameBoard();
	return player;
}

int max (int a, int b)
{
	int max = a;
	if (b > a)
	{
		max = b;
	}
	return max;
}

int main()
{
	int i;
	
	// Message de bienvenue
	printf ("====================================\n");
	printf ("Bienvenue dans le jeu \"The Last War\"\n");
	printf ("====================================\n");
	
	// Création des deux joueurs
	PLAYER *players[NUM_PLAYERS];
	for (i = 0; i < NUM_PLAYERS; i++)
	{
		char *name = malloc (BUFFER_SIZE * sizeof (char));
		char *deckfile = malloc (BUFFER_SIZE * sizeof (char));
		printf ("* Joueur %d\n  - Entrez votre nom : ", i + 1);
		scanf ("%s", name);
		printf ("  - Entrez le nom de votre deck : ");
		scanf ("%s", deckfile);
		
		players[i] = newPlayer (trim (name), trim (deckfile));
		
		// Pioche des premières cartes de chaque joueur
		int j;
		for (j = 0; j < INIT_NB_CARDS; j++)
		{
			CARD *card = popCard (players[i]->deck);
			addCardLast (players[i]->gameboard->hand, card);
		}
	}
	
	// Choix au hasard du joueur qui commence
	printf ("\n====================================\n");
	printf ("Decks chargés, début de la partie\n");
	printf ("====================================\n");
	srand (time (NULL));
	int curPlayer = rand() % 2;
	printf ("\n=> Le joueur qui commence est le joueur %d : %s\n", curPlayer + 1, players[curPlayer]->name);
	
	// Boucle principale du jeu
	while (players[0]->life > 0 && players[1]->life > 0)
	{
		printf ("\n====================================\n");
		printf ("Au tour de %s (%d vies)\n", players[curPlayer]->name, players[curPlayer]->life);
		printf ("====================================\n\n");
		
		if (players[curPlayer]->deck->capacity - players[curPlayer]->deck->size == 0)
		{
			printf ("+-------------------------------------------------------------------+\n");
			printf ("| Il n'y a plus de carte sur votre deck, vous avez perdu la partie. |\n");
			printf ("+-------------------------------------------------------------------+\n\n");
			return 0;
		}
		
		// Pioche et ajout de la carte dans la main
		CARD *card = popCard (players[curPlayer]->deck);
		addCardLast (players[curPlayer]->gameboard->hand, card);
		
		printf ("===Votre main :\n");
		printCardList (players[curPlayer]->gameboard->hand);
		
		// Le joueur peut décider de placer une carte comme ressources
		int resource;
		printf ("\n===Vous avez actuellement %d ressources.\n===Entrez un numéro de carte si vous voulez en ajouter une, et -1 sinon : ", players[curPlayer]->gameboard->resources);
		scanf ("%d", &resource);
		if (resource != -1)
		{
			CARD *card = removeCard (players[curPlayer]->gameboard->hand, resource);
			players[curPlayer]->gameboard->resources++;
			
			printf ("\n=> Carte placée en resource :\n   ");
			printCard (card);
			
			printf ("\n===Votre main :\n");
			printCardList (players[curPlayer]->gameboard->hand);
		}
		
		// Le joueur peut décider d'envoyer une carte sur le front
		if (players[curPlayer]->gameboard->attack == NULL)
		{
			int attack;
			printf ("\n===Vous n'avez pas de carte sur le front.\n===Entrez un numéro de carte si vous voulez en envoyer une, et -1 sinon : ");
			scanf ("%d", &attack);
			
			while (attack != -1 && getCard (players[curPlayer]->gameboard->hand, attack)->cost > players[curPlayer]->gameboard->resources)
			{
				printf ("\n=> La carte que vous tentez d'envoyer sur le front coute %d et vous n'avez que %d ressources disponibles !\n", getCard (players[curPlayer]->gameboard->hand, attack)->cost, players[curPlayer]->gameboard->resources);
				printf ("\n===Faites un nouveau choix : ");
				scanf ("%d", &attack);
			}

			if (attack != -1)
			{
				CARD *card = removeCard (players[curPlayer]->gameboard->hand, attack);
				players[curPlayer]->gameboard->attack = card;

				printf ("\n=> Carte placée en attaque :\n   ");
				printCard (players[curPlayer]->gameboard->attack);

				printf ("\n===Votre main :\n");
				printCardList (players[curPlayer]->gameboard->hand);
			}
		}
		else
		{
			printf ("===Vous avez actuellement une carte sur le front :\n   ");
			printCard (players[curPlayer]->gameboard->attack);
			printf ("\n");
		}
		
		// Le joueur peut décider de lancer une attaque
		if (players[curPlayer]->gameboard->attack == NULL)
		{
			printf ("===Vous n'avez pas de cartes sur le front et ne pouvez donc pas lancer d'attaques");
		}
		else
		{
			int launch;
			printf ("\n\n===Voulez-vous lancer une attaque (1/0) ? ");
			scanf ("%d", &launch);
			
			if (launch == 1)
			{
				int otherPlayer = (curPlayer + 1) % 2;
				if (players[otherPlayer]->gameboard->attack == NULL)
				{
					int attackPnt = players[curPlayer]->gameboard->attack->attack;
					players[otherPlayer]->life = max (0, players[otherPlayer]->life - attackPnt);
					
					printf ("\n=> Lancement d'une attaque directe contre l'autre joueur !\n");
					if (players[otherPlayer]->life == 0)
					{
						printf ("+------------------------------------------------------------------+\n");
						printf ("| Vous venez de tuer votre adversaire, vous avez gagné la partie ! |\n");
						printf ("+------------------------------------------------------------------+\n\n");
						return 0;
					}
					else
					{
						printf ("   Vous lui infligez %d dégâts\n", attackPnt);
					}
				}
				else
				{
					printf ("\n=> Votre attaque est dirigée contre la carte de l'autre joueur :\n   ");
					printCard (players[otherPlayer]->gameboard->attack);
					
					players[otherPlayer]->gameboard->attack->life = max (0, players[otherPlayer]->gameboard->attack->life - players[curPlayer]->gameboard->attack->attack);
					
					if (players[otherPlayer]->gameboard->attack->life == 0)
					{
						players[otherPlayer]->gameboard->attack = NULL;
						printf ("    Vous venez de tuer la carte adverse");
					}
					else
					{
						printf ("    Vous lui infligez %d dégâts\n", players[curPlayer]->gameboard->attack->attack);
					}
				}
			}
		}
		
		// Changement de joueur
		curPlayer = (curPlayer + 1) % 2;
	}
		
	return 0;
}
