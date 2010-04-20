package gps2itm;


public class Ellipsoid {
	double a,b , e2;
	

	public Ellipsoid(double a, double b) {
		super();
		this.a = a;
		this.b = b;
		this.e2 = ((a*a) - (b* b)) / (a*a);
	}
	public LatLon pointToLatLng (Point point)
	{

	     double RootXYSqr = Math.sqrt( (point.x*point.x) + (point.y*point.y) );
	     
	     double radlat1 = aTan2 (point.z, (RootXYSqr * (1 - this.e2)));
	     double radlat2;
	     do {
	    	 double sinRadlat1 = Math.sin(radlat1);
	         double V = this.a / (Math.sqrt(1 - (this.e2 * (sinRadlat1 *sinRadlat1))));
	         
	          radlat2 = aTan2((point.z + (this.e2 * V * (sinRadlat1))), RootXYSqr);
	         if (Math.abs(radlat1 - radlat2) > 0.000000001) {
	             radlat1 = radlat2;
	         }
	         else {
	             break;
	         }
	     }
	     while (true);
	     
	     double lat = radlat2 * (180 / Math.PI);
	     double lng = aTan2(point.y, point.x) * (180 / Math.PI);
	     
	     return new LatLon(lat, lng);
	}

	 static public double aTan2(double y, double x) {
		return mMath.atan2(y, x);
	}
	
	  
	 /**
	  * Creates a new Point object containing a cartesian representation of an angular LatLng on the surface of the Ellipsoid.
	  *
	  * @param {JSITM.LatLng} latlng
	  * @return {JSITM.Point}
	  */	
	public Point latLngToPoint(LatLon latlng )
	{
			 double radlat = latlng.lat * (Math.PI / 180);
			 double radlng = latlng.lng * (Math.PI / 180);
		     
		     // Compute nu
			 double V = this.a / (Math.sqrt(1 - (this.e2 * ( (Math.sin(radlat)* Math.sin(radlat))))));
		     
		     // Compute XYZ
			 double x = (V + latlng.alt) * (Math.cos(radlat)) * (Math.cos(radlng));
			 double y = (V + latlng.alt) * (Math.cos(radlat)) * (Math.sin(radlng));
			 double z = ((V * (1 - this.e2)) + latlng.alt) * (Math.sin(radlat));
		     
		     return new Point(x, y, z);
		
	}
}
