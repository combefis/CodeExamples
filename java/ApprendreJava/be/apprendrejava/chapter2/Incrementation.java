// Incrementation.java

package be.apprendrejava.chapter2;

public class Incrementation
{
    public static void main (String[] args)
    {
        double val = 5.3;

        // Forme suffixe
        System.out.println (val++);   // affiche la valeur de val++
        System.out.println (val);

        val = 5.3;

        // Forme préfixe
        System.out.println (++val);   // affiche la valeur de ++val
        System.out.println (val);
    }
}