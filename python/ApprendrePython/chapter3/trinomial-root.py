# Programme de recherche des racines d'un trinôme
# du second degré de la forme ax^2 + bx + c
# Auteur : Sébastien Combéfis
# Version : 7 aout 2015

from math import sqrt

print("Recherche des racines de ax^2 + bx + c")
a = int(input("Coefficient a : "))
b = int(input("Coefficient b : "))
c = int(input("Coefficient c : "))

# Calcul du discriminant
delta = b**2 - 4 * a * c
print("Discriminant :", delta)

# Test des trois cas possibles et affichage des racines du trinôme
if delta < 0:
    print("Pas de racine réelle")
elif delta == 0:
    x = -b / (2 * a)
    print("Une racine réelle double :", x)
else:
    x1 = (-b - sqrt(delta)) / (2 * a)
    x2 = (-b + sqrt(delta)) / (2 * a)
    print("Deux racines réelles distinctes:", x1, "et", x2)