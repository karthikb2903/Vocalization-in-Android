package voice.text;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewClass extends Activity {

	WebView mWebView;
	private static final int SELECTTEXT_MENU_ID = Menu.FIRST; 
	private static final int SPEAK_MENU_ID = Menu.FIRST+1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		Bundle b=this.getIntent().getExtras();
		String s=b.getString("Search");
		mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
		
        mWebView.loadUrl("http://en.m.wikipedia.org/wiki/"+s);
      
        mWebView.setWebViewClient(new HelloWebViewClient());
        mWebView.setHapticFeedbackEnabled(true);
        mWebView.setLongClickable(true);
	}
	private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 super.onCreateOptionsMenu(menu);
		 menu.add(0, SELECTTEXT_MENU_ID, 0, "Select Text");
		 menu.add(0,SPEAK_MENU_ID,0,"Speak");
		 
		 return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		return true;
	}
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
	 	case SELECTTEXT_MENU_ID: 
			SelectText();
			return true;
		case SPEAK_MENU_ID :
			Intent i=new Intent(WebViewClass.this,TextToSpeechAct.class);
			startActivity(i);
		}
		return true;
		}
	public void SelectText(){ 
		 try{  
	        //  WebView.class.getMethod("selectText").invoke(this);

		      KeyEvent shiftPressEvent =   
		               new KeyEvent(0, 0,KeyEvent.ACTION_DOWN,       
		               KeyEvent.KEYCODE_SHIFT_LEFT, 0, 0);  
		      shiftPressEvent.dispatch(mWebView);  
		  }catch(Exception e){  
			  e.printStackTrace();
		      throw new AssertionError(e);  
		  }  
	}
	
	
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		 if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
	           
			 startActivity(new Intent(WebViewClass.this,VoiceRecognition.class));
	        }
	        return super.onKeyDown(keyCode, event);
	}
	

}
