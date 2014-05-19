package virginia.edu.cs2110;

import java.io.Serializable;
import java.util.Random;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Combat extends Activity {

	
	private static final int CLICKS_REQUIRED = 30;
	private static Context context;
	private Human human;
	public int clicks;
	private CountDownTimer timer;
	private int attackTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.context;
		setContentView(R.layout.activity_combat);
		
		attackTime = getIntent().getExtras().getInt("ATTACK_TIME");
		
		
		final TextView mTicker   = (TextView)findViewById(R.id.seconds);
		final Button attack = (Button)findViewById(R.id.attack);
		
		human = new Human(getIntent().getExtras().getString("NAME"), getIntent().getExtras().getInt("SCORE"));
		
		timer = new CountDownTimer(attackTime*1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				 mTicker.setText((Integer.toString( (int) millisUntilFinished / 1000)));
				}

			@Override
			public void onFinish() {
				
				Map.mapActivity.finish();
				new AlertDialog.Builder(Combat.this)
				.setTitle("Game Over")
				.setMessage(
						"Final score: "
								+ human.getScore())
				.setPositiveButton("Main Menu",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(Combat.this, MainMenu.class);
				                startActivity(intent);  
				                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
				              
				                SharedPreferences prefs = Combat.this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
				                Editor editor = prefs.edit();
				                int highScore = prefs.getInt("SCORE", 0);
				                if(human.getScore() > highScore){
				                	editor.putInt("SCORE", human.getScore());
				                	editor.putString("NAME", human.getName());
				                	editor.commit();
				                	Toast.makeText(getBaseContext(), "New High Score!!", Toast.LENGTH_LONG).show();
				                }
				                finish();
							}

						})
				.setNegativeButton("Replay", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Combat.this, Map.class);
						intent.putExtra("NAME", human.getName());
						intent.putExtra("GHOST_SPEED", getIntent().getExtras().getDouble("GHOST_SPEED"));
						intent.putExtra("MAX_GHOSTS", getIntent().getExtras().getInt("MAX_GHOSTS"));
						intent.putExtra("ATTACK_TIME", attackTime);
						intent.putExtra("SCORE", human.getScore());
						startActivity(intent);
						overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
						finish();
					}
				}).setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						Intent intent = new Intent(Combat.this, MainMenu.class);
		                startActivity(intent);  
		                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
		                SharedPreferences prefs = Combat.this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		                Editor editor = prefs.edit();
		                int highScore = prefs.getInt("SCORE", 0);
		                if(human.getScore() > highScore){
		                	editor.putInt("SCORE", human.getScore());
		                	editor.putString("NAME", human.getName());
		                	editor.commit();
		                	Toast.makeText(getBaseContext(), "New High Score!!", Toast.LENGTH_LONG).show();
		                }
		                finish();
					}

				}).show();
			}
		  }.start();
		  
		  clicks = 0;
		  
		  attack.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
		           clicks++;
		           if(clicks == CLICKS_REQUIRED){
		           timer.cancel();
		           finish();
		           overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		        	   
	            }
	            }
	        });
	
	}
	
	public static Context getAppContext(){
		return context;
	}

	@Override 
	public void onBackPressed(){
		return;
	}
}
