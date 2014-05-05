package utility;

import java.util.Random;
import representation.Point;

public class Utility {

	public static Point generatePoint() {
		
		return new Point(1,1);
	}
	
	public static Point[] generateKnownPoints() {
		
		String xString = "0.30685312," + 
				"0.048676863," + 
				"0.6684613," + 
				"0.9034869," + 
				"0.07551482," + 
				"0.7855057," + 
				"1.0," + 
				"0.87646806," + 
				"0.10861616," + 
				"0.9495053," + 
				"0.9782111," + 
				"0.633928," + 
				"0.7634738," + 
				"0.891973," + 
				"0.8088224";
		String[] xValues = xString.split(",");
		
		String yString = "0.490775," + 
				"0.14487593," + 
				"0.5028458," + 
				"0.43453354," + 
				"0.8683127," + 
				"0.099942535," + 
				"0.18227573," + 
				"0.817093," + 
				"0.014803829," + 
				"0.67128325," + 
				"0.22449857," + 
				"0.3435744," + 
				"0.37961432," + 
				"0.9956443," + 
				"0.83227986";
				
		String[] yValues = yString.split(",");
		
		Point[] res = new Point[xValues.length];
		for (int i=0; i < xValues.length; i++) {
			res[i] = new Point(Float.parseFloat(xValues[i]), Float.parseFloat(yValues[i]));
		}
		
		return res;
	}
	
	
	public static Point[] generateRandomPoints(int howMany, float xmin, float ymin, float xmax, float ymax) {
		Point[] res = new Point[howMany];
		
		for (int i=0; i < howMany; i++) {
			
			res[i] = new Point( xmin + (float)(Math.random() * (xmax - xmin)),
								ymin + (float)(Math.random() * (ymax - ymin))
								);
			
		}
		return res;
	}
	
	public static Point[] generateStrictlyCloserPoints(int howMany, float xmin, float ymin, float increment) {
		
		Point[] res = new Point[howMany];
		
		for (int i=0; i < howMany; i++) {
			
			res[i] = new Point( xmin + increment,
								ymin + increment
								);
			increment *= 0.995;
			
		}
		return res;
		
		
	}
	
	public static void printPoints(Point[] p) {

		for (int i=0; i < p.length; i++) {
			System.out.println("P"+i+" x:" + p[i].x + " y:" + p[i].y);
		}
	}
	
	public static void randomPermutePoints(Point[] p) {
		
		Random rdm = new Random();
		
		int tail = p.length - 1;
		while (tail > 0) {
			int randomIndex = rdm.nextInt(tail);
			Point temp = p[randomIndex];
			p[randomIndex] = p[tail];
			p[tail] = temp;
			tail--;
			
		}
		
	}
	
	
	public static void scaleOneByOne(Point[] input) {
		
		float xmax = Float.MIN_VALUE;
		float ymax = Float.MIN_VALUE;
		float xmin = Float.MAX_VALUE;
		float ymin = Float.MAX_VALUE;
		
		for (int i=0; i < input.length; i++) {
			if (input[i].x > xmax)
				xmax = input[i].x;
			if (input[i].y > ymax)
				ymax = input[i].y;
			if (input[i].x < xmin)
				xmin = input[i].x;
			if (input[i].y < ymin)
				ymin = input[i].y;
		}
		
		float xRange = xmax - xmin;
		float yRange = ymax - ymin;
		
		
		for (int i=0; i < input.length; i++) {
			input[i].x = ((input[i].x - xmin) / xRange);
			input[i].y = ((input[i].y - ymin) / yRange);
		}
		
	}
	
	public static float distance(Point p1, Point p2) {
		return (float) Math.sqrt( Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2) );
	}
	
}
