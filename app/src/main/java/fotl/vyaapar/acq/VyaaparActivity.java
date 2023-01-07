/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class VyaaparActivity extends Activity {
    /** Called when the activity is first created. */
	 private Thread mSplashThread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        final VyaaparActivity sPlashScreen = this;
    	
    	// The thread to wait for splash screen events
    	mSplashThread =  new Thread(){
    		@Override
    		public void run(){
    			try {
    				synchronized(this){
    					// Wait given period of time or exit on touch
    					wait(1000);
    				}
    			} 
    			catch(InterruptedException ex){    				
    			}

    			finish();
    			
    			 DbVyaapar d = new DbVyaapar(getApplicationContext());
    				Cursor cur=  d.getCompanycheck();
    				Intent intent = new Intent();
    			String strVal = "";
    			if (cur != null )
    			{
    				if (cur.moveToFirst()) {
    					do{
    						strVal = cur.getString(cur.getColumnIndex("_id"));
    					}while(cur.moveToNext());
    				}    			
    			}
//    			cur.close();
//    			d.closeConnection();
//    			if(strVal.contentEquals("0"))
//    				intent.setClass(sPlashScreen, Company.class);
//    			else
//    				intent.setClass(sPlashScreen, MenuForm2.class);
				intent.setClass(sPlashScreen, Login.class);

    			startActivity(intent);
//    			stop();
    		}
    	};
    	
    	mSplashThread.start();
        
        
        
        final Button btn_Enter=(Button)findViewById(R.id.btnEnter);
        btn_Enter.setOnClickListener(new OnClickListener() {	
            
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(VyaaparActivity.this,MenuForm2.class));
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				}
		});
      
    }
}
