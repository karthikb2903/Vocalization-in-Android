package voice.text;

import java.util.Locale;



import android.app.Activity;
import android.content.Intent;
import android.speech.tts.*;
import android.text.ClipboardManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TextToSpeechAct extends Activity {
    /** Called when the activity is first created. */
	private TextToSpeech txt2Spch;
	Button spButn;
	TextView selectedText;
	String s;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.texttospeech);
        try{
        ClipboardManager clipboard=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        s= clipboard.getText().toString();
        
        selectedText=(TextView)findViewById(R.id.selectedText);
        selectedText.setText(s);
        
        spButn=(Button)findViewById(R.id.spkButn);
       
        
           spButn.setOnClickListener(new View.OnClickListener() {
        	Txt2SpchInitListener txtListener;
        	
            public void onClick(View v) {

            	if(txtListener == null)
            		txtListener = new Txt2SpchInitListener();
            	if(txt2Spch == null)
            	txt2Spch = new TextToSpeech(getApplicationContext(),
            			                       txtListener);
            	if(txtListener.status == TextToSpeech.SUCCESS) {
            		txt2Spch.setLanguage(Locale.US);
            		txt2Spch.speak(s,
                                       TextToSpeech.QUEUE_FLUSH, null);
            	}
             }
        });
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
    }
 // Added @Override below, on suggestions from
 // one of our visitor to this site.
     @Override
     public void onDestroy() {
 	    super.onDestroy(); // Added this line on suggestions from
 		                   // one of our visitor to this site.
     	txt2Spch.shutdown();
     }
     
     @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
	           
			 startActivity(new Intent(TextToSpeechAct.this,WebViewClass.class));
	        }
	        return super.onKeyDown(keyCode, event);
	}
 }

 final class Txt2SpchInitListener implements TextToSpeech.OnInitListener {
     int status;
 	public void onInit(int status) {
 		this.status = status;
 	}
 	
 }
