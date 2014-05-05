package representation;

public class HashCoordinate {

	//To emulate a 2D hash with x and y values
	//We deliberately make the fields public, for this reason
	
	public int x;
	public int y;
	
	public HashCoordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashCoordinate other = (HashCoordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	

	
	
}
