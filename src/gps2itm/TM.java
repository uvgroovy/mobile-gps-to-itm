package gps2itm;

public class TM {
	private Ellipsoid ellipsoid;
	private double e0;
	private double n0;
	private double f0;
	private double lat0;
	private double lng0;
	private double radlat0;
	private double radlng0;
	private double bf0;
	private double af0;
	private double e2;
	private double n;
	private double n2;
	private double n3;

	/**
	 * Cretaes a new TM (Transverse Mercator Projection).<br>
	 * For more info see <a
	 * href="http://en.wikipedia.org/wiki/Transverse_Mercator"
	 * >http://en.wikipedia.org/wiki/Transverse_Mercator</a>
	 * 
	 * @constructor
	 * @param {JSITM.Ellipsoid} reference ellipsoid
	 * @param {Number} e0 eastings of false origin in meters
	 * @param {Number} n0 northings of false origin in meters
	 * @param {Number} f0 central meridian scale factor
	 * @param {Number} lat0 latitude of false origin in decimal degrees.
	 * @param {Number} lng0 longitude of false origin in decimal degrees.
	 */
	public TM(Ellipsoid ellipsoid, double e0, double n0, double f0,
			double lat0, double lng0) {
		// eastings (e0) and northings (n0) of false origin in meters; _
		// central meridian scale factor (f0) and _
		// latitude (lat0) and longitude (lng0) of false origin in decimal
		// degrees.

		this.ellipsoid = ellipsoid;
		this.e0 = e0;
		this.n0 = n0;
		this.f0 = f0;
		this.lat0 = lat0;
		this.lng0 = lng0;

		this.radlat0 = lat0 * (Math.PI / 180);
		this.radlng0 = lng0 * (Math.PI / 180);

		this.af0 = ellipsoid.a * f0;
		this.bf0 = ellipsoid.b * f0;
		this.e2 = (MathUtilies.pow(this.af0, 2) - MathUtilies.pow(this.bf0, 2))
				/ MathUtilies.pow(this.af0, 2);
		this.n = (this.af0 - this.bf0) / (this.af0 + this.bf0);
		this.n2 = this.n * this.n; // for optimizing and clarity of Marc()
		this.n3 = this.n2 * this.n; // for optimizing and clarity of Marc()

	}

	/**
	 * Project Latitude and longitude to Transverse Mercator coordinates
	 * 
	 * @param {JSITM.LatLng} latitude and longtitude to convert
	 * @return {JSITM.Point} projected coordinates
	 */
	public Point project(LatLon latlng) {
		// Convert angle measures to radians
		double RadPHI = latlng.lat * (Math.PI / 180);
		double RadLAM = latlng.lng * (Math.PI / 180);
		double nu = this.af0
				/ (Math.sqrt(1 - (this.e2 * MathUtilies
						.pow(Math.sin(RadPHI), 2))));
		double rho = (nu * (1 - this.e2))
				/ (1 - (this.e2 * MathUtilies.pow(Math.sin(RadPHI), 2)));
		double eta2 = (nu / rho) - 1;
		double p = RadLAM - this.radlng0;
		double M = this.Marc(RadPHI);

		double I = M + this.n0;
		double II = (nu / 2) * (Math.sin(RadPHI)) * (Math.cos(RadPHI));
		double III = ((nu / 24) * (Math.sin(RadPHI)) * (MathUtilies.pow(Math
				.cos(RadPHI), 3)))
				* (5 - (MathUtilies.pow(Math.tan(RadPHI), 2)) + (9 * eta2));
		double IIIA = ((nu / 720) * (Math.sin(RadPHI)) * (MathUtilies.pow(Math
				.cos(RadPHI), 5)))
				* (61 - (58 * (MathUtilies.pow(Math.tan(RadPHI), 2))) + (MathUtilies
						.pow(Math.tan(RadPHI), 4)));

		double y = I + (MathUtilies.pow(p, 2) * II)
				+ (MathUtilies.pow(p, 4) * III)
				+ (MathUtilies.pow(p, 6) * IIIA);

		double IV = nu * (Math.cos(RadPHI));
		double V = (nu / 6) * (MathUtilies.pow(Math.cos(RadPHI), 3))
				* ((nu / rho) - (MathUtilies.pow(Math.tan(RadPHI), 2)));
		double VI = (nu / 120)
				* (MathUtilies.pow(Math.cos(RadPHI), 5))
				* (5 - (18 * (MathUtilies.pow(Math.tan(RadPHI), 2)))
						+ (MathUtilies.pow(Math.tan(RadPHI), 4)) + (14 * eta2) - (58 * (MathUtilies
						.pow(Math.tan(RadPHI), 2)) * eta2));
		double x = this.e0 + (p * IV) + (MathUtilies.pow(p, 3) * V)
				+ (MathUtilies.pow(p, 5) * VI);

		return new Point(x, y, 0);
	}

	/**
	 * Compute meridional arc
	 * 
	 * @private
	 * @param {Number} radlat latitude of meridian in radians
	 * @return {Number}
	 */
	public double Marc(double radlat) {

		return (this.bf0 * (((1 + this.n + ((5 / 4) * this.n2) + ((5 / 4) * this.n3)) * (radlat - this.radlat0))
				- (((3 * this.n) + (3 * this.n2) + ((21 / 8) * this.n3))
						* (Math.sin(radlat - this.radlat0)) * (Math.cos(radlat
						+ this.radlat0)))
				+ ((((15 / 8) * this.n2) + ((15 / 8) * this.n3))
						* (Math.sin(2 * (radlat - this.radlat0))) * (Math
						.cos(2 * (radlat + this.radlat0)))) - (((35 / 24) * this.n3)
				* (Math.sin(3 * (radlat - this.radlat0))) * (Math
				.cos(3 * (radlat + this.radlat0))))));
	}
	
	 /**
	  * Returns the  initial value for Latitude in radians.
	  * @private
	  * @param {Number} y northings of point
	  * @return {Number}
	  */
	public double InitialLat (double y)
	{
	   
	     double radlat1 = ((y - this.n0) / this.af0) + this.radlat0;
	 
	     double M = this.Marc(radlat1);
	     
	     double radlat2 = ((y - this.n0 - M) / this.af0) + radlat1;
	     
	     while (Math.abs(y - this.n0 - M) > 0.00001) {
	         radlat2 = ((y - this.n0 - M) / this.af0) + radlat1;
	         M = this.Marc(radlat2);
	         radlat1 = radlat2;
	     }
	     return radlat2;
	 }
	 /**
	  * Un-project Transverse Mercator eastings and northings back to latitude and longtitude.
	  * 
	  * @param {JSITM.Point} point
	  * @return {JSITM.LatLng} latitude and longtitude on the refernced ellipsoid's surface
	  */
	 
	 public LatLon unproject (Point point){
	     //
	     //Input: - _
	     
	     //Compute Et
	     double Et = point.x - this.e0;
	     
	     //Compute initial value for latitude (PHI) in radians
	     double PHId = this.InitialLat(point.y);
	     
	     //Compute nu, rho and eta2 using value for PHId
	     double nu = this.af0 / (Math.sqrt(1 - (this.e2 * (MathUtilies.pow(Math.sin(PHId), 2)))));
	     double rho = (nu * (1 - this.e2)) / (1 - (this.e2 * MathUtilies.pow(Math.sin(PHId), 2)));
	     double eta2 = (nu / rho) - 1;
	     
	     //Compute Latitude
	     double VII = (Math.tan(PHId)) / (2 * rho * nu);
	     double VIII = ((Math.tan(PHId)) / (24 * rho * MathUtilies.pow(nu, 3))) * (5 + (3 * (MathUtilies.pow(Math.tan(PHId), 2))) + eta2 - (9 * eta2 * (MathUtilies.pow(Math.tan(PHId), 2))));
	     double IX = ((Math.tan(PHId)) / (720 * rho * MathUtilies.pow(nu, 5))) * (61 + (90* (MathUtilies.pow(Math.tan(	PHId), 2)) + (45 * (MathUtilies.pow(Math.tan(PHId), 4))))); // remember
	     
	     double lat = (180 / Math.PI) * (PHId - (MathUtilies.pow(Et, 2) * VII) + (MathUtilies.pow(Et, 4) * VIII) - (MathUtilies.pow(Et , 6) * IX));
	     
	     //Compute Longitude
	     double X = (MathUtilies.pow(Math.cos(PHId), -1)) / nu;
	     double XI = ((MathUtilies.pow(Math.cos(PHId), -1)) / (6 * MathUtilies.pow(nu, 3))) * ((nu / rho) + (2 * (MathUtilies.pow(Math.tan(PHId), 2))));
	     double XII = ((MathUtilies.pow(Math.cos(PHId), -1)) / (120 * MathUtilies.pow(nu, 5))) * (5 + (28 * (MathUtilies.pow(Math.tan(PHId), 2))) + (24 * (MathUtilies.pow(Math.tan(PHId), 4))));
	     double XIIA = ((MathUtilies.pow(Math.cos(PHId), -1)) / (5040 * MathUtilies.pow(nu, 7))) * (61 + (662 * (MathUtilies.pow(Math.tan(PHId), 2))) + (1320 * (MathUtilies.pow(Math.tan(PHId), 4))) + (720 * (MathUtilies.pow(Math.tan(PHId), 6))));
	     
	     double lng = (180 / Math.PI) * (this.radlng0 + (Et * X) - (MathUtilies.pow(Et, 3) * XI) + (MathUtilies.pow(Et, 5) * XII) - (MathUtilies.pow(Et, 7) * XIIA));
	     
	     LatLon latlng = new LatLon(lat, lng);
	     
	     return (latlng);
	 }
	 
	 
}
