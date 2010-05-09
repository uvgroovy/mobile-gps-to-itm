import gps2itm.Convertor;
import gps2itm.LatLon;

import java.util.Calendar;
import java.util.Date;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class myMIDlet extends MIDlet implements CommandListener,
		LocationListener {

	Alert alTest;
	Display display;
	Command exit, about;
	Form form;
	StringItem gpsCoordinates;

	StringItem height;
	StringItem itmCoordinates;
	StringItem lastUpdate;
	LocationProvider lp;

	public myMIDlet() throws LocationException {
		Criteria cr = new Criteria();
		cr.setHorizontalAccuracy(20); // accurate to 20 meters horizontally
		// cr.setVerticalAccuracy(20); DO NOT add this line, it causes the
		// software not to work!

		lp = LocationProvider.getInstance(cr); // Now get an instance of the

		// should be called after we tried to init the lp.
		initForm();
	}

	/**
	 * React to user commands.
	 */
	public void commandAction(Command c, Displayable d) {

		if (c == exit) {
			try {
				destroyApp(true);
			} catch (MIDletStateChangeException e) {
				e.printStackTrace();
			}
			notifyDestroyed();
		}
		if (c == about) {

			alTest = new Alert("About",
					"This ingenious program was made by Yuval & Yagel Kohavi",
					null, AlertType.INFO);
			display.setCurrent(alTest, form);
		}
	}

	protected void startApp() throws MIDletStateChangeException {
		if (display == null) {
			initApp();
		}
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	/**
	 * Init the application. call this method from startApp
	 */
	private void initApp() {
		display = Display.getDisplay(this);
		display.setCurrent(form);

		if (lp != null) {
			lp.setLocationListener(this, 180, -1, -1); // Notify us every 3
			// minutes
		}
	}

	/**
	 * Init the gui.
	 */
	private void initForm() {
		form = new Form("GPS to ITM");
		form.append("Welcome! Data updates every 3 minutes or so..\n");

		initCommands();

		if (lp == null) {
			form.append("No location provider fits. This is a problem.");
		} else {
			itmCoordinates = new StringItem("ITM:", "Please wait...");
			height = new StringItem("Height:", "Please wait...");
			lastUpdate = new StringItem("Last update:", "Please wait...");
			form.append(itmCoordinates);
			form.append(height);
			form.append(lastUpdate);
		}
	}

	/**
	 * Updates the GUI. called when we have new results from the GPS.
	 * 
	 * @param location
	 *            The location from the GPS
	 */
	private void updateDisplay(Location location) {

		lastUpdate.setText(getTimeAsString());
		try {
			itmCoordinates.setText(Convertor
					.convertGpsToITM(getCoordinates(location)));
			height.setText(location.getQualifiedCoordinates().getAltitude()
					+ "m");

		} catch (Exception e) {
			itmCoordinates.setText("An error has occoured.");
			height.setText("");
		}
	}

	/**
	 * @return Current hour and minute: "HH:MM"
	 */
	private String getTimeAsString() {
		Date d = new Date();

		Calendar c =  Calendar.getInstance();

		c.setTime(d);

		String minute = "" + c.get(Calendar.MINUTE);
		
		if (minute.length() == 1) {
			minute = "0" + minute;
		}
		
		String time = c.get(Calendar.HOUR_OF_DAY) + ":"
				+ minute;
		return time;
	}

	/**
	 * Initialize the commands for the user.
	 */
	private void initCommands() {
		about = new Command("about", Command.ITEM, 0);
		exit = new Command("Exit", Command.EXIT, 1);
		form.addCommand(about);
		form.addCommand(exit);

		form.setCommandListener(this);

	}

	/**
	 * Return the coordinates in LatLon object
	 * 
	 * @param l
	 *            The current location
	 * @return The lat and lon from the current location in LatLon object
	 * @throws Exception
	 *             If on coordinates were found in l.
	 */
	private LatLon getCoordinates(Location l) throws Exception {

		Coordinates c = l.getQualifiedCoordinates();

		if (c != null) {
			// Get coordinate information
			double lat = c.getLatitude();
			double lon = c.getLongitude();

			return (new LatLon(lat, lon));
		}
		throw new Exception();

	}

	/**
	 * This is a callback for the Location API. This is called when the location
	 * API has new location data.
	 * 
	 * @location Our current location
	 */
	public void locationUpdated(LocationProvider locationProvider,
			Location location) {
		// Only update if you have something valid to update.
		if (location != null && location.isValid())
			updateDisplay(location);

	}

	public void providerStateChanged(LocationProvider locationProvider,
			int newState) {
		// Intentionally left blank.

	}

}
