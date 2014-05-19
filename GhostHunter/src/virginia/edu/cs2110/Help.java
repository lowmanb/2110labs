package virginia.edu.cs2110;

import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

public class Help extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}

	@Override 
	public void onBackPressed(){
		super.onBackPressed();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
}
