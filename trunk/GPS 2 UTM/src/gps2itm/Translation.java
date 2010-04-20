package gps2itm;

public class Translation {
	float dx, dy, dz;

	public String toString() {
		return "Translation [dx=" + dx + ", dy=" + dy + ", dz=" + dz + "]";
	}

	public Translation(float dx, float dy, float dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	public Point translate (Point point)
	{
	     return new Point(point.x + this.dx, point.y + this.dy, point.z + this.dz);
	 }
	
	 /**
	  * Returns an new Translation with inverse values.
	  * @return {JSITM.Translation}
	  */
	public Translation inverse (){
	     return new Translation(-this.dx, -this.dy, -this.dz);
	}
}
