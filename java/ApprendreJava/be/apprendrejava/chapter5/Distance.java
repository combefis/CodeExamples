// Distance.java

package be.apprendrejava.chapter5;

import java.util.Scanner;

public class Distance
{
	public static void main (String[] args)
	{
		Scanner scan = new Scanner (System.in);
		
		Coordinate c1 = new Coordinate (scan.nextDouble(), scan.nextDouble());
		Coordinate c2 = new Coordinate (scan.nextDouble(), scan.nextDouble());
		
		double dx = c2.getX() - c1.getX();
		double dy = c2.getY() - c1.getY();
		double distance = Math.sqrt (dx * dx + dy * dy);
		
		System.out.printf ("Distance = %f\n", distance);
	}
}