package representation;

public class Point {

	//Simple emulation of a C-struct with x and y values
	//We deliberately make the fields public, for this reason
	
	public float x;
	public float y;
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
