// util.h
// 
// Librairie contenant des routines utilitaires pour la gestion des chaines de caractères
// et la lecture de données depuis des fichiers de texte
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>

#ifndef TLW_UTIL
#define TLW_UTIL

// Fonction permettant de supprimer les caractères blancs
// en début et en fin d'une chaine de caractères
char *trim (char *str);

// Fonction permettant de faire une copie d'une chaine de caractères
char* strCopy (char* str);

// Fonction permettant de lire une ligne dans un fichier de texte,
// préalablement ouvert, et de la renvoyer sous forme d'un char*
char* readString (FILE* file);

// Fonction permettant de lire une ligne dans un fichier de texte,
// préalablement ouvert, et de la renvoyer sous forme d'un int
int readInt (FILE* file);

#endif
