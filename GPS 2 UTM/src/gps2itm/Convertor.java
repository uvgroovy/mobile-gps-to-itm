package gps2itm;


public class Convertor {
	 /**
	  * @fileOverview js-itm: a Javascript library for converting between Israel Transverse Mercator (ITM) and GPS (WGS84) coordinates.<p>
	  * <a href="http://code.google.com/p/js-itm/">http://code.google.com/p/js-itm/</a>
	  * @author Udi Oron (udioron at g mail dot com)
	  * @author forked from <a href="http://www.nearby.org.uk/tests/GeoTools.html">GeoTools</a> by Paul Dixon
	  * @copyright <a href="http://www.gnu.org/copyleft/gpl.html">GPL</a>
	  * @version 0.1.1 ($Rev: 7 $ $Date: 2008-12-13 23:25:47 +0200 (Sat, 13 Dec 2008) $)
	  */
	 
	 /**
	  * Parent namespace for the entire library
	  * @namespace
	  */
	 
	 /**************************************************************/
	 
	 /**
	  * Creates a new LatLng.
	  * 
	  * @class holds geographic coordinates measured in degrees.<br/>
	  * <a href="http://en.wikipedia.org/wiki/Geographic_coordinate">http://en.wikipedia.org/wiki/Geographic_coordinate</a>
	  *
	  * @constructor
	  * @param {Number} lat latitude in degrees ( http://en.wikipedia.org/wiki/Latitude )
	  * @param {Number} lng longtitude in degrees ( http://en.wikipedia.org/wiki/Longitude )
	  * @param {Number} alt altitude in meters above the uesd geoid surface - this was not used or tested - please keep this always 0.
	  * @param {Number} precision number of digits after the decimal point, used in printout. see toString().
	  */
	 
	 /**
	  * Returns Latlng as string, using the defined preccision
	  * @return {String}
	  */
	
	 
	 /**
	  * Parses latitude and longtitude in a string into a new Latlng
	  * @param {String} s
	  * @return {JSITM.LatLng}
	  */
	
	/*
	 public static LatLon latlngFromString (String s) {
		 RegExp pattern = new RegExp("^(-?\\d+(?:\\.\\d*)?)(?:(?:\\s*[:,]?\\s*)|\\s+)(-?\\d+(?:\\.\\d*)?)$", "i");
	     boolean latlng = s.match(pattern);
	     if (latlng) {
	         double lat = parseFloat(latlng[1], 10);
	         double lng = parseFloat(latlng[2], 10);
	         return new LatLon(lat, lng);
	     }
	 
	 	throw ("could not parse latlng");
	 }
	 */
	 
	 /**************************************************************/
	 
	 /**
	  * Creates a new Point.
	  * @class holds 2D/3D cartesian coordinates.
	  * 
	  * @constructor
	  * @param {Number} x
	  * @param {Number} y
	  * @param {Number} z
	  * @return {JSITM.Point}
	  */
	 
	 
	 
	 /**
	  * Returns a string containing the Point coordinates in meters
	  * @return {String}
	  */
	 
	 /**************************************************************/
	 
	 /**
	  * Creates a new Translation.
	  * <p>
	  * (Helmert translation were depracted since they are not used in the ITM - feel free to add them back from geotools if you need them! :-) )
	  * 
	  * @constructor 
	  * @param {Number} dx
	  * @param {Number} dy
	  * @param {Number} dz
	  */

	 /**
	  * Return a new translated Point. (Original point kept intact)
	  *
	  * @param {JSITM.Point} point original point.
	  * @return {JSITM.Point}
	  */
	 

	 
	 /**************************************************************/
	 
	 /**
	  * Creates a new Ellipsoid.<p>
	  * for more info see <a href="http://en.wikipedia.org/wiki/Reference_ellipsoid">http://en.wikipedia.org/wiki/Reference_ellipsoid</a>
	  *
	  * @constructor
	  * @param {Number} a length of the equatorial radius (the semi-major axis) in meters
	  * @param {Number} b length of the polar radius (the semi-minor axis) in meters
	  */
	     
	 
	 
	 /**************************************************************/
	 
	 /**
	  * Creates a new LatLng containing an angular representation of a cartesian Point on the surface of the Ellipsoid.
	  *
	  * @param {JSITM.Point} point
	  * @return {JSITM.LatLng}
	  */
	 

	
	 

	 

	 

	 
	

	 
	 /** Juicy part 1 ***************************************************************************/
	 
	 /**
	  * Ellipsoid for GRS80 (The refernce ellipsoid of ITM
	  * @see http://en.wikipedia.org/wiki/GRS80
	  * @type JSITM.Ellipsoid
	  */
	 static Ellipsoid GRS80 = new Ellipsoid(6378137d, 6356752.31414d); 
	 
	 /**
	  * Ellipsoid for WGS84 (Used by GPS)
	  * @see http://en.wikipedia.org/wiki/WGS80
	  * @type JSITM.Ellipsoid
	  */
	 static Ellipsoid WGS84 = new Ellipsoid(6378137, 6356752.314245);
	 
	 /**
	  * Simple Translation from GRS80 to WGS84
	  * @see http://spatialreference.org/ref/epsg/2039/
	  * @type JSITM.Translation
	  */
	 static Translation GRS80toWGS84 = new Translation(-48, 55, 52); 
	 
	 /**
	  * Translation back from WGS84 to GRS80
	  * @type JSITM.Translation
	  */
	 static Translation WGS84toGRS80 = GRS80toWGS84.inverse();
	 
	 /**
	  * ITM (Israel Transverse Mercator) Projection 
	  * @see http://en.wikipedia.org/wiki/Israeli_Transverse_Mercator
	  * @type JSITM.TM
	  */
	 static TM ITM = new TM( GRS80, 219529.58400, 626907.38999, 1.000006700000000, 31.734394, 35.204517);
	 
	 
	 /** Juicy part 2 ***************************************************************************/
	 
	 /**
	  * Converts a point to an ITM reference.
	  * 
	  * @example 
	  *   JSITM.point2ItmRef(new JSITM.Point(200, 500)); // prints "200000500000"
	  *   JSITM.point2ItmRef(new JSITM.Point(200, 500), 3); // prints "200500"
	  * @param {JSITM.Point} point
	  * @param {Number} precision 3=km, 4=100 meter, 5=decameter 6=meter.  optional, default is 6,
	  * @return {String}
	  */
	 public static String point2ItmRef (Point point,int precision)
	 {	 
		 
	 	
	 	
	 	int p = precision; 
	 
	     if (p < 3) 
	         p = 3;
	     if (p > 6) 
	         p = 6;
	     
	     double div = MathUtilies.pow(10, (6 - p));
	     int east = (int) (point.x / div) ;
	     int north = (int) (point.y / div);
	     	return zeropad(east, p) + ' ' + zeropad(north, p);
	 	
	 }
	 private static  String zeropad(int num,int len){
	 	    String str = new Integer(num).toString() ;
	 	    while (str.length() < len) {
	 	        str = '0' + str;
	 	    }
	 	    
	 	    return str;
	 	}
	 
	 public static String point2ItmRef (Point point)
	 {
		 int p = 6;
		 return point2ItmRef(point , p);
		 
	 }
	 /**
	  * Parses an ITM reference and returns a Point object.<p>
	  * throws an exception for invalid refernce!<p>
	  * <p>
	  * @example valid inputs:
	  *  200500
	  *  20005000
	  *  2000000500000
	  *  130:540
	  *  131550:44000
	  *  131 400
	  *  210222 432111
	  * 
	  * @param {String} s
	  * @return {JSITM.Point}
	  */
	 /*
	 public static Point itmRef2Point (String s){
	     
	     int precision;
	     
	     for (precision = 6; precision >= 3; precision--) {
	         var pattern = new RegExp("^(\\d{" + precision + "})\\s*:?\\s*(\\d{" + precision + "})$", "i")
	         var ref = s.match(pattern);
	         if (ref) {
	             if (precision > 0) {
	                 double mult = MathUtilies.pow(10, 6 - precision);
	                 var x = parseInt(ref[1], 10) * mult;
	                 var y = parseInt(ref[2], 10) * mult;
	                 return new JSITM.Point(x, y);
	             }
	         }
	     }
	     
	 	throw "Could not parse reference";
	 }
	 */
	 
	 /** Juicy part 3 ***************************************************************************/
	 
	 /**
	  * Converts a Point on ITM to a LatLng on GPS 
	  * @param {JSITM.Point} point
	  * @return {JSITM.LatLng}
	  */
	 public static LatLon itm2gps (Point point){
	     LatLon latlng = ITM.unproject(point); //however, latlng is still on GRS80!
	     return latlng.convertGrid(GRS80, WGS84, GRS80toWGS84);
	 }
	 
	 /**
	  * Converts a LatLng on GPS to a Point on ITM
	  * @param {JSITM.LatLng} latlng
	  * @return {JSITM.Point}
	  */
	 
	 public static Point gps2itm (LatLon latlng){
	     LatLon wgs84 = latlng.convertGrid(WGS84, GRS80, WGS84toGRS80); //first convert to GRS80
	     return ITM.project(wgs84);
	 }
	 
	 /** Juicy part 4 ***************************************************************************/
	 
	 /**
	  * Converts an ITM grid refernece in 6, 8, 10 or 12 digits to a GPS angular Point instace
	  * @param {String} s
	  * @return {JSITM.Point}
	  */
	 
	 /*
	 public static LatLon itmRef2gps (String s){   // fixed the return type from Point to LatLon 
	 	Point point = itmRef2Point(s);
	 	return itm2gps(point);
	 }*/
	 
	 /**
	  * Converts an ITM grid refernece in 6, 8, 10 or 12 digits to a GPS angular reference
	  * @param {String} s
	  * @return {String}
	  */
	 /*
	 public static String itmRef2gpsRef (String s){
	 	return itmRef2gps(s).toString();
	 }*/
	 
	 /**
	  * Converts a GPS angular reference to an ITM LatLng instance
	  * @param {String} s
	  * @return {JSITM.LatLng} 
	  */
	 
	 /*
	 public static Point gpsRef2itm (String s){ // fixed the return type from LatLon to Point 
	 	LatLon latlng = latlngFromString(s);
	 	return gps2itm(latlng);
	 }
	 /*
	 
	 /**
	  * Converts a GPS angular reference to an ITM grid refernece in 6, 8, 10 or 12 digits
	  * @param {String} s
	  * @param {Number} precision 3=km, 4=100 meter, 5=decameter 6=meter. Optional.  Default value is 6=meter
	  * @return {String} 
	  */
	 /*
	 public static String gpsRef2itmRef (String s, int precision){
	 	return point2ItmRef( gpsRef2itm(s), (( precision ==0) ? 6 :precision  )  );
	 }
	 */
	 public static String numbersToLatLon (LatLon latLon)
	 {
		 
		return point2ItmRef( gps2itm(latLon) );
		
	 }

}
