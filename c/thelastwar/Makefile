# Author: Sébastien Combéfis
# Version: 1 Janvier 2013

CC = gcc
CFLAGS = -I. -O2 -pedantic -pipe -std=c99 -Wall -Werror
DEPS = cardlist.h deck.h util.h
OBJ = cardlist.o deck.o util.o main.o
TARGET = thelastwar

%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

all: $(TARGET)

$(TARGET): $(OBJ)
	$(CC) -o $@ $^ $(CFLAGS)

.PHONY: clean

clean:
	rm -f *.o
	rm -f $(TARGET)