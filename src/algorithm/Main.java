package algorithm;

import representation.Point;
import utility.Utility;

public class Main {

	public static void main(String[] args) {

		
		Point[] testPoints = Utility.generateRandomPoints(1000, -1000f, -2000f, 1000.0f, 600.0f);
		
		//in this demo, testPoints are already random, hence the random permutation step
		//in RandomizedAlgo is redundantly masked. 
		//As an exploration, Try using Utility.generateStrictlyCloserPoints
		//and verify that the random permute in RandomizedAlgo is crucial
		
		
		/*
		 * Below we compare the numOperations carried out by the Randomized Method
		 * versus the Brute Force (nested for loop) method
		 * 
		 * Note that because the randomized method scales input into a unit square, we
		 * also scale the input before running it via brute force so that the output
		 * results can be quickly verified
		 */

		
		//METHOD RANDOMIZED
		RandomizedAlgo ra = new RandomizedAlgo();
		float raResult = ra.closestDistance(testPoints);
		System.out.println("Rand Algo closest distance: " + raResult);
		ra.printNumOperations();
		
		
		
		//METHOD BRUTE FORCE
		Utility.scaleOneByOne(testPoints);
		float accuMin = 100;
		int bruteOps = 0;
		for (int i=0; i < testPoints.length; i++) {
			for (int j=0; j < testPoints.length; j++) {
				if (i != j && Utility.distance(testPoints[i], testPoints[j]) < accuMin) {
					accuMin = Utility.distance(testPoints[i], testPoints[j]);
				}
				bruteOps++;
			}
		}
		System.out.println("Brute Force closest distance: " + accuMin);
		System.out.println("Brute Force num operations: " + bruteOps);
		
		
		
		
		
	}

}
