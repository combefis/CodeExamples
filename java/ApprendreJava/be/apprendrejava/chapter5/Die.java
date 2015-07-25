// Die.java

package be.apprendrejava.chapter5;

public class Die
{
	// Variables d'instance
	private int nbFaces;
	private int visibleFace;
	
	// Constructeurs
	public Die()
	{
		nbFaces = 6;
		visibleFace = (int) (Math.random() * nbFaces) + 1;
	}
	
	public Die (int faces)
	{
		nbFaces = faces;
		visibleFace = (int) (Math.random() * nbFaces) + 1;
	}
	
	// Méthodes
	public void printFace()
	{
		System.out.print (visibleFace);
	}
	
	public int getFace()
	{
		return visibleFace;
	}
	
	public boolean isEven()
	{
		return visibleFace % 2 == 0;
	}
	
	public boolean isGreaterThan (int value)
	{
		return visibleFace > value;
	}
	
	public static void main (String[] args)
	{
		Die myDie = new Die();
		System.out.println ("La face visible du dé est " + myDie.visibleFace);
	}
}