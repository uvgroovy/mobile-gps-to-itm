import gps2itm.Convertor;
import gps2itm.LatLon;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class myMIDlet extends MIDlet implements CommandListener , LocationListener {

	Command exit, about;
	Display display;
	Form form;
	LocationProvider lp;
	Alert alTest;
	public myMIDlet() throws LocationException {
		form = new Form("GPS to ITM");
		initiateCommands();
		form.setCommandListener(this);
		Criteria cr = new Criteria();
		cr.setHorizontalAccuracy(20);		// accurate to 20 meters horizontally
		lp = LocationProvider.getInstance(cr);		// Now get an instance of the provider
		lp.setLocationListener(this, 180, -1, -1);
		
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {

		display = Display.getDisplay(this);
		display.setCurrent(form);
		

	}

	private void updateDisplay(Location location) {
		LatLon ortalLatlon;
		try {
			ortalLatlon = getCoordinates(location);

			form.append(Convertor.numbersToLatLon(ortalLatlon));

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

	public  LatLon getCoordinates(Location l) throws Exception {

		
		Coordinates c = l.getQualifiedCoordinates();

		if (c != null) {
			// Get coordinate information
			double lat = c.getLatitude();
			double lon = c.getLongitude();

			// Now recenter map to given location, zoom to street level
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
		      notifyDestroyed();		}
		if (c == about) {
			
			alTest = new Alert("About", "This inguines program was made by Yuval & Yagel Kohavi", null,
                     AlertType.INFO);
			display.setCurrent(alTest,form);
		}
	}

	public void locationUpdated(LocationProvider locationProvider, Location location) {

		
		updateDisplay(location);
		
	}

	public void providerStateChanged(LocationProvider locationProvider, int newState ) {
		// TODO Auto-generated method stub
		
	}
}
