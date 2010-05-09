package gps2itm;

public class Convertor {

	/** Juicy part 1 ***************************************************************************/

	/**
	 * Ellipsoid for GRS80 (The refernce ellipsoid of ITM
	 * 
	 * @see http://en.wikipedia.org/wiki/GRS80
	 */
	static Ellipsoid GRS80 = new Ellipsoid(6378137d, 6356752.31414d);

	/**
	 * Ellipsoid for WGS84 (Used by GPS)
	 * 
	 * @see http://en.wikipedia.org/wiki/WGS80
	 */
	static Ellipsoid WGS84 = new Ellipsoid(6378137, 6356752.314245);

	/**
	 * Simple Translation from GRS80 to WGS84
	 * 
	 * @see http://spatialreference.org/ref/epsg/2039/
	 */
	static Translation GRS80toWGS84 = new Translation(-48, 55, 52);

	/**
	 * Translation back from WGS84 to GRS80
	 */
	static Translation WGS84toGRS80 = GRS80toWGS84.inverse();

	/**
	 * ITM (Israel Transverse Mercator) Projection
	 * 
	 * @see http://en.wikipedia.org/wiki/Israeli_Transverse_Mercator
	 */
	static TM ITM = new TM(GRS80, 219529.58400, 626907.38999,
			1.000006700000000, 31.734394, 35.204517);

	/** Juicy part 2 ***************************************************************************/

	/**
	 * Converts a point to an ITM reference.
	 * 
	 * @example JSITM.point2ItmRef(new JSITM.Point(200, 500)); // prints
	 *          "200000500000" JSITM.point2ItmRef(new JSITM.Point(200, 500), 3);
	 *          // prints "200500"
	 * @param point
	 * @param precision
	 *            3=km, 4=100 meter, 5=decameter 6=meter.
	 * @return the itm reference
	 */
	public static String point2ItmRef(Point point, int precision) {

		int p = precision;

		if (p < 3)
			p = 3;
		if (p > 6)
			p = 6;

		double div = MathUtilies.pow(10, (6 - p));
		int east = (int) (point.getX() / div);
		int north = (int) (point.getY() / div);
		return zeropad(east, p) + ' ' + zeropad(north, p);

	}

	private static String zeropad(int num, int len) {
		String str = new Integer(num).toString();
		while (str.length() < len) {
			str = '0' + str;
		}

		return str;
	}

	public static String point2ItmRef(Point point) {
		int p = 6;
		return point2ItmRef(point, p);

	}

	/** Juicy part 3 ***************************************************************************/

	/**
	 * Converts a Point on ITM to a LatLng on GPS
	 * 
	 * @param {JSITM.Point} point
	 * @return {JSITM.LatLng}
	 */
	public static LatLon itm2gps(Point point) {
		LatLon latlng = ITM.unproject(point); // however, latlng is still on
												// GRS80!
		return latlng.convertGrid(GRS80, WGS84, GRS80toWGS84);
	}

	/**
	 * Converts a LatLng on GPS to a Point on ITM
	 * 
	 * @param {JSITM.LatLng} latlng
	 * @return {JSITM.Point}
	 */

	public static Point gps2itm(LatLon latlng) {
		LatLon wgs84 = latlng.convertGrid(WGS84, GRS80, WGS84toGRS80); // first
																		// convert
																		// to
																		// GRS80
		return ITM.project(wgs84);
	}

	/** Juicy part 4 ***************************************************************************/

	/**
	 * Convert GPS coordinates to ITM string
	 * 
	 * @param latLon
	 *            The GPS coordinates
	 * @return The formatted itm coordinates.
	 */
	public static String convertGpsToITM(LatLon latLon) {

		return point2ItmRef(gps2itm(latLon));

	}

}
