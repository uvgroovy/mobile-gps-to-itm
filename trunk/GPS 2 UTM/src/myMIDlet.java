import gps2itm.Convertor;
import gps2itm.LatLon;

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

	Command exit, about;
	Display display;
  	Form form;
	LocationProvider lp;
	Alert alTest;
	
	StringItem coordinates;
	StringItem height;

	public myMIDlet() throws LocationException {
		Criteria cr = new Criteria();
		cr.setHorizontalAccuracy(20); // accurate to 20 meters horizontally
		// cr.setVerticalAccuracy(20); DO NOT add this line, it causes the software not to work!

		lp = LocationProvider.getInstance(cr); // Now get an instance of the

		initForm();
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	private void initApp() {
		display = Display.getDisplay(this);

		display.setCurrent(form);

		form.setCommandListener(this);

		if (lp == null) {
			form.append("No location provider. This is a problem.");
		} else {

			lp.setLocationListener(this, 180, -1, -1); // Notify us every 3
														// minutes
		}
	}

	private void initForm() {
		form = new Form("GPS to ITM");
		form.append("Welcome! Data updates every 3 minutes or so..\n");
	
		coordinates = new StringItem("ITM:", "Please wait...");
		height = new StringItem("Height:", "Please wait...");
		
		form.append(coordinates);
		form.append(height);
		initiateCommands();
	}

	protected void startApp() throws MIDletStateChangeException {

		if (display == null) {
			initApp();

		}

	}

	private void updateDisplay(Location location) {
		LatLon ortalLatlon;
		try {
			ortalLatlon = getCoordinates(location);
			coordinates.setText(Convertor.convertGpsToITM(ortalLatlon));
			height.setText(location.getQualifiedCoordinates().getAltitude() + "m");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initiateCommands() {
		about = new Command("about", Command.ITEM, 0);
		exit = new Command("Exit", Command.EXIT, 1);
		form.addCommand(about);
		form.addCommand(exit);

	}

	public LatLon getCoordinates(Location l) throws Exception {

		Coordinates c = l.getQualifiedCoordinates();

		if (c != null) {
			// Get coordinate information
			double lat = c.getLatitude();
			double lon = c.getLongitude();
			
			return (new LatLon(lat, lon));
		}
		throw new Exception();

	}

	public void commandAction(Command c, Displayable d) {

		if (c == exit) {
			try {
				destroyApp(true);
			} catch (MIDletStateChangeException e) {
				// TODO Auto-generated catch block
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
