package ash.fb.fortunetree.test;

import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{
	
	
		ImageView pic , button;
		Facebook fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String App_id=getString(R.string.APP_ID);

    	fb = new Facebook(App_id);

        button = (ImageView) findViewById(R.id.login);
        pic = (ImageView) findViewById(R.id.picture);
        button.setOnClickListener(this);
        UpdateButtonImage();
        
    }

	private void UpdateButtonImage() {
		// TODO Auto-generated method stub
		if(fb.isSessionValid()){
			button.setImageResource(R.drawable.icon);
		}else{
			button.setImageResource(R.drawable.fb_logo);
		}
	}

	@Override
	public void onClick(View v) {
		
		if(fb.isSessionValid()){
			//button to close the session - log out from fb 
			try {
				fb.logout(getApplicationContext());
				UpdateButtonImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			fb.authorize(MainActivity.this,new String[]{"email"}, new DialogListener() {
				
				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "FB Error  ", Toast.LENGTH_SHORT);
				}
				
				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "On Cancel ", Toast.LENGTH_SHORT);
				}
				
				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					UpdateButtonImage();
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "On Cancel ", Toast.LENGTH_SHORT);
					
				}
			} );
		}
		
	}


  }
