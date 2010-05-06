package gps2itm;

public class LatLon {
	double lat;
	double lng;
	int alt;

	/**
	 * Creates a new LatLng.
	 * 
	 * @class holds geographic coordinates measured in degrees.<br/>
	 *        <a
	 *        href="http://en.wikipedia.org/wiki/Geographic_coordinate">http:/
	 *        /en.wikipedia.org/wiki/Geographic_coordinate</a>
	 * 
	 * @constructor
	 * @param lat
	 *            latitude in degrees ( http://en.wikipedia.org/wiki/Latitude )
	 * @param lng
	 *            longtitude in degrees ( http://en.wikipedia.org/wiki/Longitude
	 *            )
	 */
	public LatLon(double lat, double lng) {
		this.lat = lat;
		this.lng = lng;
		this.alt = 0; // altitude in meters above the uesd geoid surface - this
		// was not used or tested - please keep this always 0.
	}

	/**
	 * Returns Latlng as string.
	 * 
	 * @return {String}
	 */
	public String toString() {
		return this.lat + " " + this.lng;
	}

	/**
	 * Creates a new LatLng by converting coordinates from a source ellipsoid to
	 * a target one.
	 * <p>
	 * This process involves:
	 * <p>
	 * 1. converting the angular latlng to cartesian coordinates using
	 * latLngToPoint()
	 * <p>
	 * 2. translating the XYZ coordinates using a translation, with special
	 * values supplied to this translation.
	 * <p>
	 * 3. converting the translated XYZ coords back to a new angular LatLng
	 * <p>
	 *<p>
	 * for example refer to {@link JSITM.itm2gps}
	 * 
	 * @param {JSITM.Ellipsoid} from source elli
	 * @param {JSITM.Ellipsoid} to
	 * @param {JSITM.Translation} translation
	 * @return {JSITM.LatLng}
	 */

	public LatLon convertGrid(Ellipsoid from, Ellipsoid to,
			Translation translation) {

		Point point = from.latLngToPoint(this);

		// removed 7 point Helmert translation (not needed in Israel's grids)
		Point translated = translation.translate(point);

		return to.pointToLatLng(translated);
	}

}
