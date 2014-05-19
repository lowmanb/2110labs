package virginia.edu.cs2110;

import java.io.Serializable;
import java.util.ArrayList;

import android.location.Location;
import android.os.Bundle;


public class Human {

	final private static double ATTACK_THRESH = 0.00005;
	
	private String name;
	private int score;
	private Location loc;
	
	
	public Human(String name){
		this.name = name;
		score = 0;
		loc = null;
		
	}
	
	public Human(String name, int score){
		this.name = name;
		this.score = score;
	}

	
	
	public boolean isBeingAttacked(Ghost g) {

			double x1 = loc.getLongitude();
			double x2 = g.getLoc().getLongitude();
			double y1 = loc.getLatitude(); 
			double y2 = g.getLoc().getLatitude();

			return distance2(x1, x2, y1, y2) < ATTACK_THRESH*ATTACK_THRESH;
			
		}

				
	public static double distance2(double x1, double x2, double y1, double y2){
		return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

}
