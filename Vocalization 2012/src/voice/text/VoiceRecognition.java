package voice.text;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class VoiceRecognition extends Activity implements OnClickListener {
	
	 private static final String TAG = "VoiceRecognition";

	    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

	    private ListView mList;

	    private Handler mHandler;

	    //private Spinner mSupportedLanguageView;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	try{
    	 mHandler = new Handler();
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voicerecognition);
        
        TextView tv=(TextView)findViewById(R.id.voicetextTV);
        tv.setAnimation((Animation)AnimationUtils.
        		loadAnimation(getApplicationContext(), R.anim.animation));
        
        Button speakButton = (Button) findViewById(R.id.speakButton);

        mList = (ListView) findViewById(R.id.list);

       // mSupportedLanguageView = (Spinner) findViewById(R.id.spinnerLang);
        

        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
        	
            speakButton.setOnClickListener(this);
        } 
        else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
        
       
        
     // Most of the applications do not have to handle the voice settings. If the application
        // does not require a recognition in a specific language (i.e., different from the system
        // locale), the application does not need to read the voice settings.
        refreshVoiceSettings();
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}

        
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
		 if (v.getId() == R.id.speakButton) {
	            startVoiceRecognitionActivity();
	        }
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
    	try{
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        // Specify the recognition language. This parameter has to be specified only if the
        // recognition has to be done in a specific language and not the default one (i.e., the
        // system locale). Most of the applications do not have to set this parameter.
//        if (!mSupportedLanguageView.getSelectedItem().toString().equals("Default")) {
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
//                    mSupportedLanguageView.getSelectedItem().toString());
//        }

        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    	}
    	catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
    }
    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            final ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new TextAdapter(this, R.layout.list,
                    matches));
            
            mList.setOnItemClickListener(new OnItemClickListener() {

    			@Override
    			public void onItemClick(AdapterView<?> arg0, View view, int position,
    					long arg3) {
    				// TODO Auto-generated method stub
    				String selected=matches.get(position);
    				Intent i=new Intent(view.getContext(),WebViewClass.class);
    				i.putExtra("Search",selected);
    				startActivity(i);
    			}
    		});
        }

         super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void refreshVoiceSettings() {
        Log.i(TAG, "Sending broadcast");
//        sendOrderedBroadcast(RecognizerIntent.getVoiceDetailsIntent(this), null,
//                new SupportedLanguageBroadcastReceiver(), null, Activity.RESULT_OK, null, null);
        try{
        sendOrderedBroadcast(RecognizerIntent.getVoiceDetailsIntent(this),null,new SupportedLanguageBroadcastReceiver(),null,Activity.RESULT_OK,null,null);
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
       // sendOrderedBroadcast(RecognizerIntent.getVoiceDetailsIntent(this),null);
    }

    private void updateSupportedLanguages(List<String> languages) {
        // We add "Default" at the beginning of the list to simulate default language.
        languages.add(0, "Default");

        SpinnerAdapter adapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, languages.toArray(
                        new String[languages.size()]));
      //  mSupportedLanguageView.setAdapter(adapter);
    }

    private void updateLanguagePreference(String language) {
//        TextView textView = (TextView) findViewById(R.id.language);
//        textView.setText(language);
    }
    
    /**
     * Handles the response of the broadcast request about the recognizer supported languages.
     *
     * The receiver is required only if the application wants to do recognition in a specific
     * language.
     */
    private class SupportedLanguageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, final Intent intent) {
            Log.i(TAG, "Receiving broadcast " + intent);

            final Bundle extra = getResultExtras(false);

            if (getResultCode() != Activity.RESULT_OK) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("Error code:" + getResultCode());
                    }
                });
            }

            if (extra == null) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        showToast("No extra");
                    }
                });
            }

            if (extra.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        updateSupportedLanguages(extra.getStringArrayList(
                                RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES));
                    }
                });
            }

            if (extra.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        updateLanguagePreference(
                                extra.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE));
                    }
                });
            }
        }

        private void showToast(String text) {
            Toast.makeText(VoiceRecognition.this, text, 1000).show();
        }

		
    }
    
    @Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
	           
			 startActivity(new Intent(VoiceRecognition.this,Vocalization.class));
	        }
	        return super.onKeyDown(keyCode, event);
	}
}