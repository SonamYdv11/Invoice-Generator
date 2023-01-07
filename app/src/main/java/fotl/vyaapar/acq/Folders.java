/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */
package fotl.vyaapar.acq;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Folders extends BaseActivity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.folders);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********
        
        ((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	//finish();
            	startActivity(new Intent(Folders.this, MenuForm.class));
        		//setContentView(R.layout.mainscreen);   
            }
        });
   
        final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
		 btnMnuInvoice.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(Folders.this, InvoiceCreation.class));	        		
	        		//finish();
	        	}
	        });
		 
		 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
		 btnMnuMasters.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(Folders.this, Masters.class));	        		
	        		//finish();
	        	}
	        });
		 
		 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
		 btnMnuLists.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
	        		startActivity(new Intent(Folders.this, Folders.class));	        		
	        		//finish();
	        	}
	        });
		 

            
        ((Button) findViewById(R.id.btnInvoiceList)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(Folders.this, InvoiceList.class));
        		//setContentView(R.layout.invoicelist);   
            }
        });
        
        ((Button) findViewById(R.id.btnProductList)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(Folders.this, ProductList.class));
        		//setContentView(R.layout.productlist);   
            }
        });
           
        ((Button) findViewById(R.id.btnCustomerList)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(Folders.this, CustomerList.class));
        		//setContentView(R.layout.customerlist);   
            }
        });
           
        
	}
	@Override
	public void onBackPressed() {
	// check if page 2 is open	    
	    {
	        super.onBackPressed(); // allows standard use of backbutton for page 1
	    }

	}
}


