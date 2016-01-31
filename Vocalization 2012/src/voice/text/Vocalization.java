package voice.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class Vocalization extends Activity {
    /** Called when the activity is first created. */
	ImageView click;
	TextView scrollText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
        click=(ImageView)findViewById(R.id.clickImage);
        scrollText=(TextView)findViewById(R.id.scrollText);
        scrollText.setAnimation((Animation)AnimationUtils
        		.loadAnimation(getApplicationContext(), R.anim.animation));
        
      click.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i=new Intent(v.getContext(),VoiceRecognition.class);
			startActivity(i);
		}
	});
         
    }
}