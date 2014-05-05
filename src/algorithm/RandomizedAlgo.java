/*
 * RANDOMIZED ALGORITHM TO FIND CLOSEST ALL-PAIRS DISTANCE AMONG POINTS IN 2D
 * Algorithmic blueprint:
 * 	- by using randomization, we can achieve an expected runtime of O(n)
 * 	- this is a significant achievement over the O(n^2) naive implementation
 * 	- and also an improvement over the O(n log n) runtime using divide and conquer
 * 
 * @Authored by Hardy Wijaya
 * 
 * Demo proof of concept. Code can be freely distributed for educational purposes
 * 
 * The demo implementation uses a simple HashMap, and 
 * 	uses critical observations from probability
 * (1) The HashMap is conceptually a 2D grid, to keep track of points already encountered
 * (2) Given how the hashMap was build (using grid size = delta/2), we guarantee that
 * 		each grid can have max 1 point residing within it
 * (3) With each incoming new point, we
 * 		- find the respective bucket (in the 2D grid) for this point
 * 		- check a 5x5 grid neighboring the incoming point 
 * 			//note that 5x5 is O(1)
 * 			//and in #2 we established that each cell in the grid has at most O(1) points
 * 			- if a new (smaller) delta is found, rebuild the hash
 * 			- else, put this new point into the existing hash map
 * 
 * The total cost of checking neighbors, given that the original list has size n,
 * 		is therefore = O(n) * O(1) * O(1) 
 * 					 = O(n)
 * 
 * The expected total cost of rebuild can be derived by observing that:
 * 		- the random permutation ensures that points obey probabilistic expectations
 * 		- at iteration i, Probability(i-th point triggers a rebuild) = 1/i
 * 		- if it does trigger a rebuilt, the rebuild itself costs O(i), since there are i items
 * 		- TotalExpectedRebuildCost = Summation_over_i ( (1/i) * O(i) ) 
 * 								   = O(n)
 * 
 * Therefore, the expected total cost of the entire algorithm is O(n)
 * 
 * 
 */


package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


import representation.CheckNeighbour;
import representation.HashCoordinate;
import representation.Point;
import utility.Utility;

public class RandomizedAlgo {
	
	//use this as a simple counter to compare algorithm cost
	private int numOperations;
	
	
	//Constructor
	public RandomizedAlgo() {
		numOperations = 0;
		
		//May also define load factor in hash table, etc
	}
	
	/**
	 * Return the closest distance between all pairs in input
	 * @param in
	 * @return a float value of closest distance
	 */
	public float closestDistance(Point[] in) {
		
		//First, randomly permute the input
		//This is crucial to ensure randomization assumption about expected runtime
		Utility.randomPermutePoints(in);
		
		//Then, scale the points into a unit square
		Utility.scaleOneByOne(in);
		
		
		//Initialize variables with just 1st and 2nd point info
		//	delta: is the closestDistance so far
		//	hashDim: the 'side' of the conceptually 2D hash table
		//			 e.g. hashDim = 3 means we have a 3x3 hash table
		float delta = Utility.distance(in[1], in[2]);
		int hashDim = (int) Math.ceil(1.0f / (0.5*delta));
		
		HashMap<HashCoordinate, Point> myHash = new HashMap<HashCoordinate, Point>();
		
		
		for (int i=0; i < in.length; i++) {
			Point current = in[i];
			HashCoordinate currentBin = getBin(current, delta, hashDim);
			
			ArrayList<HashCoordinate> neigh = getNeighbour(currentBin, hashDim);
			CheckNeighbour result = checkNeighbour(current, myHash, neigh, delta);
			
			if (result.needUpdate) {
				
				delta = result.deltaValue;
				hashDim = (int) Math.ceil(1.0f / (0.5*result.deltaValue));
				
				
				//since delta gets updated, we rebuild hash table
				myHash = rebuildHash(myHash, result.deltaValue);
				
				HashCoordinate newBin = getBin(current, result.deltaValue, hashDim);
				myHash.put(newBin, current);
				
			}
			else {
				myHash.put(currentBin, current);
			}
				
		}
		
		return delta;
		
	}
	
	
	/**
	 * Get bin within the 2-D grid that houses current point
	 * @param current
	 * @param delta
	 * @param hashDim
	 * @return the bin, in HashCoordinate
	 */
	private HashCoordinate getBin(Point current, float delta, int hashDim) {
		
		int yOffset = (int) Math.floor(current.y / (0.5*delta));
		int xOffset = (int) Math.floor(current.x / (0.5*delta));
		
		//This basically represents the hashMap as a 2D array
		return new HashCoordinate(xOffset, yOffset);
	}
	
	/**
	 * Get the HashCoordinates of up to 25 neighbors within the 5x5 grid around current
	 * @param current
	 * @param hashDim
	 * @return the list of neighbor's HashCoordinates
	 */
	private ArrayList<HashCoordinate> getNeighbour(HashCoordinate current, int hashDim) {
		
		ArrayList<HashCoordinate> neigh = new ArrayList<HashCoordinate>();
		int xmax, xmin, ymax, ymin;
		ymin = Math.max(0, current.y - 2);
		ymax = Math.min(hashDim-1, current.y + 2);
		
		xmin = Math.max(0, current.x - 2);
		xmax = Math.min(hashDim-1, current.x + 2);
		
		for (int i=ymin; i <= ymax; i++) {
			for (int j=xmin; j <= xmax; j++) {

					neigh.add(new HashCoordinate(j, i));
					
					numOperations++;
			}
		}
		
		return neigh;
	}
	
	/**
	 * Check if distance from me to neighbors is smaller than the existing delta
	 * @param me
	 * @param hash
	 * @param neigh
	 * @param delta
	 * @return a CheckNeighbour object
	 */
	private CheckNeighbour checkNeighbour(Point me, HashMap<HashCoordinate, Point> hash, ArrayList<HashCoordinate> neigh, float delta) {
		
		for(HashCoordinate n : neigh) {
			
			numOperations++;
			
			if (hash.containsKey(n)) {

				if (Utility.distance(hash.get(n), me) < delta) {
					System.out.println("!!found new closest distance!!");
					float newDelta = Utility.distance(hash.get(n), me);
					
					return new CheckNeighbour(true, newDelta);
				}
			}
			
		}
		
		return new CheckNeighbour(false, delta);
	}
	
	/**
	 * Rebuild the hash map based on newDelta, and return this hash map
	 * @param myHash
	 * @param newDelta
	 * @return the newly rebuilt HashMap
	 */
	private HashMap<HashCoordinate, Point> rebuildHash(HashMap<HashCoordinate, Point> myHash, float newDelta) {
		
		int newDim = (int) Math.ceil(1.0f / (0.5*newDelta));
		HashMap<HashCoordinate, Point> newHash = new HashMap<HashCoordinate, Point>();
		
		Iterator<Point> it = myHash.values().iterator();
		while (it.hasNext()) {
			Point p = (Point) it.next();
			
			HashCoordinate newBin = getBin(p, newDelta, newDim);
			newHash.put(newBin, p);
			
			numOperations++;
		}
		
		return newHash;
		
	}
	
	public void printNumOperations() {
		System.out.println("Rand Algo num operations: " + numOperations);
	}
	
	

	
	

	
}
