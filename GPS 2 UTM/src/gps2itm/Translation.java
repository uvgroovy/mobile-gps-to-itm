package gps2itm;

public class Translation {
	private double dx, dy, dz;

	public String toString() {
		return "Translation [dx=" + dx + ", dy=" + dy + ", dz=" + dz + "]";
	}

	 /**
	  * Creates a new Translation.
	  * 
	  * (Helmert translation were deprecated since they are not used in the ITM - feel free to add them back from geotools if you need them! :-) )
	  * 
	  * @param  dx
	  * @param  dy
	  * @param  dz
	  */
	public Translation(double dx, double dy, double dz) {
		super();
		this.dx = dx;
		this.dy = dy;
		this.dz = dz;
	}
	
	 /**
	  * Return a new translated Point.
	  *
	  * @param point original point.
	  * @return The translated point
	  */
	public Point translate (Point point)
	{
	     return new Point(point.getX() + this.dx, point.getY() + this.dy, point.getZ() + this.dz);
	 }
	
	 /**
	  * Returns an new Translation with inverse values.
	  * @return {JSITM.Translation}
	  */
	public Translation inverse (){
	     return new Translation(-this.dx, -this.dy, -this.dz);
	}
}
