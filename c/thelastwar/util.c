// util.c
// 
// Librairie contenant des routines utilitaires pour la gestion des chaines de caractères
// et la lecture de données depuis des fichiers de texte
// Author: Sébastien Combéfis
// Version: 1 Janvier 2013

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#include "util.h"

#define BUFFER_SIZE 80

// Source: http://stackoverflow.com/questions/122616/how-do-i-trim-leading-trailing-whitespace-in-a-standard-way
char* trim (char *str)
{
	char *end;
	while (isspace (*str))
	{
		str++;
	}
	
	if (*str == 0)
	{
		return str;
	}
	
	end = str + strlen (str) - 1;
	while (end > str && isspace (*end))
	{
		end--;
	}
	
	*(end + 1) = 0;
	
	return str;
}

char* strCopy (char* str)
{
	int strsize = strlen (str);
	char *newstr = malloc (strsize * sizeof (char));
	strncpy (newstr, str, strsize);
	newstr[strsize] = '\0';
	return newstr;
}

char* readString (FILE* file)
{
	char buffer[BUFFER_SIZE];
	fgets (buffer, BUFFER_SIZE, file);
	return strCopy (trim (buffer));
}

int readInt (FILE* file)
{
	char buffer[BUFFER_SIZE];
	fgets (buffer, BUFFER_SIZE, file);
	return atoi (buffer);
}
