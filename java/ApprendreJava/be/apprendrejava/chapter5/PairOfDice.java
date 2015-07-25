// PairOfDice.java

package be.apprendrejava.chapter5;

public class PairOfDice
{
	// Variables d'instance
	private int nbFaces;     // nombre de faces des dés
	private Die die1, die2;  // les deux dés de la paire

	// Constructeur
	public PairOfDice (int faces)
	{
		nbFaces = faces;
		die1 = new Die (faces);
		die2 = new Die (faces);
	}

	// Méthode
	public void printFace()
	{
		System.out.print (die1.getFace() + "," + die2.getFace());
	}
}