package virginia.edu.cs2110;

import android.location.Location;
import android.os.Bundle;
import java.util.*;

public class Ghost {
	
	final private static double MOVE_DELTA = 0.000001;
	
	private Location loc;
	private int health;
	private double speed;
	

	public Ghost(Human h, Double speed) {
		health = 100;
		this.speed = speed;
		setRandomLocation(h);
	}

	public void setRandomLocation(Human h) {
		
		Random generator = new Random();

		Location ghostLoc = new Location(h.getLoc());

		double latitude, longitude;
		int x, y;
		int temp1, temp2;

		x = generator.nextInt(501) + 200;
		y = generator.nextInt(501) + 200;

		latitude = ((double) x) / 1000000.0;
		longitude = ((double) y) / 1000000.0;

		temp1 = generator.nextInt(3) + 1;
		temp2 = generator.nextInt(3) + 1;

		if (temp1 == 1) {
			latitude = latitude * (-1.0);
		}

		if (temp2 == 1){
			longitude = longitude * (-1.0);
		}

		ghostLoc.setLatitude(h.getLoc().getLatitude() + latitude);
		ghostLoc.setLongitude(h.getLoc().getLongitude() + longitude);

		loc = ghostLoc;

	}

	public void move(Human h) {
		double latDiff = h.getLoc().getLatitude() - loc.getLatitude();
		double longDiff  = h.getLoc().getLongitude() - loc.getLongitude();
		
		
		//ne
		if(latDiff > 0 && longDiff > 0){
			loc.setLongitude(loc.getLongitude() + MOVE_DELTA*speed);
			loc.setLatitude(loc.getLatitude() + MOVE_DELTA*speed);
			
		}
		
		//nw
		else if( latDiff > 0 && longDiff < 0){
			loc.setLongitude(loc.getLongitude() - MOVE_DELTA*speed);
			loc.setLatitude(loc.getLatitude() + MOVE_DELTA*speed);
		}
		
		//se
		else if( latDiff < 0 && longDiff > 0){
			loc.setLongitude(loc.getLongitude() + MOVE_DELTA*speed);
			loc.setLatitude(loc.getLatitude() - MOVE_DELTA*speed);
		}
		
		//sw
		else if( latDiff < 0 && longDiff < 0){
			loc.setLongitude(loc.getLongitude() - MOVE_DELTA*speed);
			loc.setLatitude(loc.getLatitude() - MOVE_DELTA*speed);
		}
	
	}

	public Location getLoc() {
		return loc;
	}

}
