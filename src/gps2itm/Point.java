package gps2itm;

public class Point {
	double x;
	double y;
	double z;

	/**
	 * Creates a new Point.
	 * 
	 * @class holds 2D/3D cartesian coordinates.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(double x2, double y2, double z2) {
		super();
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}


	 /**
	  * Returns a string containing the Point coordinates.
	  */
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", z=" + z + "]";
	}


	public double getX() {
		return x;
	}


	public double getY() {
		return y;
	}


	public double getZ() {
		return z;
	}

}
