package virginia.edu.cs2110;

import java.util.ArrayList;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends FragmentActivity {
	
	//making the context of an activiy static is apparently good practice. There is a get method below - 
	//It is used when "this" is not available (like in the location listener nested class)
	private static Context context;
	
	final private static int GHOST_UPDATE_INTERVAL = 100;
	
	public static Activity mapActivity;
	
	//google map object
	private GoogleMap mMap;
	//reference to system location provided ("taps" into gps or network location service)
	private LocationManager locationManager;
	//waits for updates from a Location Manager. In our case this is "locationManager" above
	private LocationListener locationListener;
	//human associated with the map
	private Human human;
	//Im thinking this can be used a flag to start dropping ghosts. It is tripped in onLocationChanged()
	private boolean locationLock;
	//dialog to notify user that gps lock is begin acquired 
	private boolean paused;
	
	private int attackStatus;
	
	private int maxGhosts;
	
	private double ghostSpeed;
	
	private int attackTime;
	
	private int updateCount;
	
	TextView scoreReadout;
	
	
	private ArrayList<Ghost> ghostList;

	
	
	AlertDialog gettingGPSDialog;
	AlertDialog pauseMenu;
	 

	
	/**
	 * Called when activity starts
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); 
        Map.context = getApplicationContext();
       
        mapActivity = this;
        
        attackStatus = -1;
        
        maxGhosts = getIntent().getExtras().getInt("MAX_GHOSTS");
        ghostSpeed = getIntent().getExtras().getDouble("GHOST_SPEED");
        Log.d("test", Double.toString(ghostSpeed));
        attackTime = getIntent().getExtras().getInt("ATTACK_TIME");
        
        String name = getIntent().getExtras().getString("NAME");
        
        ghostList = new ArrayList<Ghost>();
        
        //creating a new instance of human and giving it a name
        human = new Human(name);
        
        //no location lock yet. That happens in onStart()
        locationLock = false;
       
        //boolean use to pause game when exit dialog is shown
        paused = false;
        
        //set getGPS to null
        pauseMenu = null;
        instantiateGPSProgressBox();
        
        //getting map fragment referenced in the xml
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); // get handler to map fragment
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        
        scoreReadout =(TextView) findViewById(R.id.scoreReadout); 
        
       
        //creating a new instance of the location manager system service
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        	
        //creates and instantiates "locationListener" Location Listener
        instantiateLocationListener();
        
        
           
    }
    
    /**
     * called when activity is visible
     */
    protected void onStart(){
    	super.onStart();
    	
    	if(attackStatus != -1){
    		ghostList.remove(attackStatus);
    		
    		human.setScore(human.getScore()+10);
    		
    		new Handler().postDelayed(new Runnable() {
                public void run() {
                	ghostList.add(new Ghost(human, ghostSpeed));
                }
    		}, 8000);
    		
    		attackStatus = -1;
    	}
    	
    	// Register the listener with the Location Manager to receive location updates (start getting the location)
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    
    
    /**
     * called when activity is no longer visible
     */
    @Override
    protected void onStop() {
       //stop location updates
    	locationManager.removeUpdates(locationListener);
    	locationLock = false;
        super.onStop();
    }
    
    //returns the map context 
    public static Context getAppContext() {
        return Map.context;
    }
    
    //fancy slide animation
    @Override
	public void onBackPressed() {
    	if(pauseMenu == null)
    		instantiatePauseMenu();
    	else{
    		paused = true;
    		pauseMenu.show();
    	}
	    
	}
   
    //instantiates, prompts, and carries out appropriate action of quit dialog
	public void instantiatePauseMenu() {
		
		//pause rendering of map
		paused = true;
		mMap.stopAnimation();
		
		pauseMenu = new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_media_pause)
		.setTitle("Game Paused")
		.setMessage(
				"Current score: "
						+ human.getScore())
		.setPositiveButton("Quit",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						
						finish();
						overridePendingTransition(R.anim.push_down_in,
								R.anim.push_down_out);
					}

				})
		.setNegativeButton("Return", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				paused = false;
				if (!locationLock)
					gettingGPSDialog.show();
			}
		}).setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				paused = false;
				if (!locationLock)
					gettingGPSDialog.show();
			}

		}).show();
    }
            	            

	//instantiates "Acquiring GPS dialog"
	public void instantiateGPSProgressBox(){
		
        gettingGPSDialog = ProgressDialog.show(this, "", 
                "Acquiring GPS fix. Please wait...", true);
        gettingGPSDialog.setCancelable(true);
        gettingGPSDialog.setCanceledOnTouchOutside(false);
        gettingGPSDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        	@Override
        	public void onCancel(DialogInterface dialog){
        		onBackPressed();
        	}

		});
	}

	public void instantiateLocationListener(){
		
		locationListener = new LocationListener() {
	           
        	boolean firstLock = true;
			
        	// executes when location update is recieved from location manager
			public void onLocationChanged(Location location) {			
				
				if(paused)
					return;
					
					// sets location appropriate field in human
					human.setLoc(location);
					
					if(firstLock){
							for (int i = 0; i < maxGhosts; i++){
								Log.d("test", "ghost created");
					        	ghostList.add(new Ghost(human, ghostSpeed));
							}
						instantiateGhostUpdates();
						firstLock = false;
					}

					
					locationLock = true;
					gettingGPSDialog.dismiss();
					
					LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
					CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, (float) 18.5);
					    mMap.animateCamera(cameraUpdate);
					
				}
			

            //these are just methods associated with the LocationListener Class. onProviderDisabled is the most important as it
        	//tells the user if their gps is disabled
        	public void onStatusChanged(String provider, int status, Bundle extras) {}
        	
        	public void onProviderEnabled(String provider) {}
            
        	public void onProviderDisabled(String provider) {}
          };
	}

	public void instantiateGhostUpdates(){
		final Handler h = new Handler();
    	final Runnable r = new Runnable(){

			@Override
			public void run() {
				
				
				
				AsyncTask<Void, Void, Void> aTask = new AsyncTask<Void, Void, Void>(){
					
					@Override
					protected Void doInBackground(Void... params) {
						
						updateCount++;
						
						if(updateCount == (int) 1000/GHOST_UPDATE_INTERVAL){
							human.setScore(human.getScore()+1);
							updateCount = 0;
						}
						
						for(int i = 0; i < ghostList.size(); i++){
							ghostList.get(i).move(human);
							if(human.isBeingAttacked(ghostList.get(i))){
								attackStatus = i;
								break;	
							}
						}
							
						return null;
					}
					
					@Override
					protected void onPostExecute(Void v){
						
						mMap.clear();
						
						for(int i = 0; i < ghostList.size(); i++){
							LatLng latLng = new LatLng(ghostList.get(i).getLoc().getLatitude(), ghostList.get(i).getLoc().getLongitude());
							mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
	                        .fromResource(R.drawable.ghost_small)));
							
							
							
						}
						
						if(attackStatus != -1){
							Intent intent = new Intent(Map.getAppContext(), Combat.class);  
		                    intent.putExtra("SCORE", human.getScore());
		                    intent.putExtra("NAME", human.getName());
		                    intent.putExtra("MAX_GHOSTS", maxGhosts);
		                    intent.putExtra("GHOST_SPEED", ghostSpeed);
		                    intent.putExtra("ATTACK_TIME", attackTime);
		                    startActivity(intent);
		                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		                    onStop();
						}
						
						scoreReadout.setText("Score: " + Integer.toString(human.getScore()));
							
					}
				
				};
				
				if(attackStatus != -1 || paused){}
				else 
					aTask.execute();
					
				
				h.postDelayed(this, GHOST_UPDATE_INTERVAL);
			}
    	};
	
    	h.post(r);
	
	}
}


