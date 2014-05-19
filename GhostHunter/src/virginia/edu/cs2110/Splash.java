package virginia.edu.cs2110;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class Splash extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Font path
        String fontPath = "fonts/deathrattlebb_reg.ttf";
 
        // text view label
        TextView txtGhost = (TextView) findViewById(R.id.textView5);
 
        // Loading Font Face
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
 
        // Applying font
        txtGhost.setTypeface(tf);
        
        
        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
                public void run() {
                        startActivity(new Intent(Splash.this, MainMenu.class));
                        finish();
                }
        }, secondsDelayed * 1000);
    }
}
