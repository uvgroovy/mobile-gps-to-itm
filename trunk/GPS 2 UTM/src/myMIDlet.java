import gps2itm.Convertor;
import gps2itm.LatLon;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class myMIDlet extends MIDlet {
	
	Display display;
	Form form;
	
	public myMIDlet() {
		// TODO Auto-generated constructor stub
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

	}

	protected void pauseApp() {
		// TODO Auto-generated method stub

	}

	protected void startApp() throws MIDletStateChangeException {
    
	form = new Form("GPS to ITM");
		
	LatLon ortalLatlon;
	try {
		ortalLatlon = getCoordinates();
		
		form.append(Convertor.numbersToLatLon(ortalLatlon) );
		
		
		display = Display.getDisplay(this);
	    display.setCurrent(form);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}

	public static LatLon getCoordinates() throws Exception
	{
	
	Criteria cr= new Criteria();
	
	cr.setHorizontalAccuracy(20);  // accurate to 20 meters horizontally

	
		// Now get an instance of the provider
		LocationProvider lp= LocationProvider.getInstance(cr);
		
		// Request the location, setting a one-minute timeout
		Location l = lp.getLocation(60);
		Coordinates c = l.getQualifiedCoordinates();
		
		if(c != null ) {
			// Get coordinate information
			double lat = c.getLatitude();
			double lon = c.getLongitude();

			// Now recenter map to given location, zoom to street level
			return (new LatLon(lat, lon));
		}	
		    throw new Exception();
	
	}
}
