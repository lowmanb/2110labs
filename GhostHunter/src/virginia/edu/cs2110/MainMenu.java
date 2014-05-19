package virginia.edu.cs2110;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {

	private static Context context;
	private double ghostSpeed;
	private int maxGhosts;
	private int attackTime;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		SharedPreferences prefs = MainMenu.this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		int highScore = prefs.getInt("SCORE", 0);
		String highScoreName = prefs.getString("NAME", "null");
		
		TextView score = (TextView) findViewById(R.id.textView5);
		score.setText("High Score: " + Integer.toString(highScore) + "\nBy: " + highScoreName);
		
		MainMenu.context = getApplicationContext();
		
		final EditText editText = (EditText) findViewById(R.id.editText1);
		
		ghostSpeed = 2.5;
		maxGhosts = 12;
		attackTime = 8;
				
		SeekBar speed = (SeekBar) findViewById(R.id.seekBar1);
		
		speed.setMax(300);
		speed.setProgress(150);
		
		speed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                ghostSpeed = ((double) progress)/300 + 1;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {}
 
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
		
		SeekBar ghosts = (SeekBar) findViewById(R.id.seekBar2);
		
		ghosts.setMax(300);
		ghosts.setProgress(150);
		
		ghosts.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                maxGhosts = (int) (progress + 100)/20;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {}
 
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
		
		SeekBar attack = (SeekBar) findViewById(R.id.seekBar3);
		
		attack.setMax(300);
		attack.setProgress(150);
		
		attack.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
 
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                attackTime = (int) (progress)/25 + 4;
            }
 
            public void onStartTrackingTouch(SeekBar seekBar) {}
 
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        
		final Button startGame = (Button) findViewById(R.id.button1);
       
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String name = editText.getText().toString();
            	if(name.equals("")){
            		Toast.makeText(MainMenu.getContext(), "Please enter name", Toast.LENGTH_LONG).show();
            	}else if(isGPSEnabled() == false){
            		startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            		Toast.makeText(getBaseContext(), "Enable GPS\nPress back to return", Toast.LENGTH_LONG).show();
            	}
            	else if(isNetworkEnabled() == false){
            		startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
            		Toast.makeText(getBaseContext(), "Enable network connection (WiFi Recommended)\n Press back to return", Toast.LENGTH_LONG).show();
            	} else{
            		Intent intent = new Intent(MainMenu.this, Map.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("GHOST_SPEED", ghostSpeed);
                    Log.d("test", Double.toString(ghostSpeed));
                    intent.putExtra("MAX_GHOSTS", maxGhosts);
                    intent.putExtra("ATTACK_TIME", attackTime);
            		startActivity(intent);  
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            	}
              
            }
        });
        
        final Button help = (Button) findViewById(R.id.button2);
        
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              Intent intent = new Intent(MainMenu.this, Help.class);
              startActivity(intent);
              overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	
	public static Context getContext(){
		return context;
	}
	
	
	public boolean isGPSEnabled(){
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}
	
	public boolean isNetworkEnabled(){
		ConnectivityManager cm =
		        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		
		return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
	}
	
	
}

