package gps2itm;


public class Ellipsoid {
	private double a,b , e2;
	

	public double getA() {
		return a;
	}

	public double getB() {
		return b;
	}

	/**
	 * Creates a new Ellipsoid.
	 * for more info see <a href="http://en.wikipedia.org/wiki/Reference_ellipsoid">http://en.wikipedia.org/wiki/Reference_ellipsoid</a>
	 *
	 * @param a length of the equatorial radius (the semi-major axis) in meters
	 * @param b length of the polar radius (the semi-minor axis) in meters
	 */
	public Ellipsoid(double a, double b) {
		super();
		this.a = a;
		this.b = b;
		this.e2 = ((a*a) - (b* b)) / (a*a);
	}
	
	 /**
	  * Creates a new LatLng containing an angular representation of a cartesian Point on the surface of the Ellipsoid.
	  *
	  * @param {JSITM.Point} point
	  * @return {JSITM.LatLng}
	  */
	public LatLon pointToLatLng (Point point)
	{

	     double RootXYSqr = Math.sqrt( MathUtilies.pow(point.getX(), 2) + MathUtilies.pow(point.getY(), 2) );
	     
	     double radlat1 = MathUtilies.aTan2(point.getZ(), (RootXYSqr * (1 - this.e2)));
	     double radlat2;
	     do {
	    	 double sinRadlat1 = Math.sin(radlat1);
	         double V = this.a / (Math.sqrt(1 - (this.e2 * (sinRadlat1 *sinRadlat1))));
	         
	          radlat2 = MathUtilies.aTan2((point.getZ() + (this.e2 * V * (sinRadlat1))), RootXYSqr);
	         if (Math.abs(radlat1 - radlat2) > 0.000000001) {
	             radlat1 = radlat2;
	         }
	         else {
	             break;
	         }
	     }
	     while (true);
	     
	     double lat = radlat2 * (180 / Math.PI);
	     double lng = MathUtilies.aTan2(point.getY(), point.getX()) * (180 / Math.PI);
	     
	     return new LatLon(lat, lng);
	}


	
	  
	 /**
	  * Creates a new Point object containing a cartesian representation of an angular LatLng on the surface of the Ellipsoid.
	  *
	  * @param {JSITM.LatLng} latlng
	  * @return {JSITM.Point}
	  */	
	public Point latLngToPoint(LatLon latlng )
	{
			 double radlat = latlng.getLat() * (Math.PI / 180);
			 double radlng = latlng.getLng() * (Math.PI / 180);
		     
		     // Compute nu
			 double V = this.a / (Math.sqrt(1 - (this.e2 * ( (Math.sin(radlat)* Math.sin(radlat))))));
		     
		     // Compute XYZ
			 double x = (V + latlng.getAlt()) * (Math.cos(radlat)) * (Math.cos(radlng));
			 double y = (V + latlng.getAlt()) * (Math.cos(radlat)) * (Math.sin(radlng));
			 double z = ((V * (1 - this.e2)) + latlng.getAlt()) * (Math.sin(radlat));
		     
		     return new Point(x, y, z);
		
	}
}
