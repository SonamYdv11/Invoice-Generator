/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Masters extends BaseActivity  {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masters);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********
        
        DbVyaapar d = new DbVyaapar(getApplicationContext());
		Cursor cur=  d.getCompanycheck();
        Intent intent = new Intent();
		if (cur == null )
		{
			cur.close();
			d.closeConnection();
		intent.setClass(getApplicationContext(), Company.class);		
		startActivity(intent);
		finish();
		}
		cur.close();
		d.closeConnection();
    
		((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	//finish();
            	startActivity(new Intent(Masters.this, MenuForm.class));
        		//setContentView(R.layout.mainscreen);   
            }
        });
		
		final ImageButton btncompany = (ImageButton)findViewById(R.id.btncompany);
        btncompany.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(Masters.this, CompanyContact.class));        		
        		//setContentView(R.layout.companycontact);    		
        	}
        });	
	final ImageButton  btninvoice = (ImageButton)findViewById(R.id.btninvoice);
        btninvoice.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		   		
        			startActivity(new Intent(Masters.this, InvoiceCreation.class));        		
        	/*	DbVyaapar d = new DbVyaapar(getApplicationContext());
        		d.DeleteInvoiceMaster();*/        		
        		//setContentView(R.layout.invoicecreation);    		
        	}
        });
        final ImageButton  btnStartForm = (ImageButton)findViewById(R.id.btnproducts);
        btnStartForm.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(Masters.this, ProductMaster.class));        		
        		//setContentView(R.layout.productmaster);    		
        	}
        });
        
        final ImageButton btnCustomer = (ImageButton)findViewById(R.id.btncustomer);
        btnCustomer.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(Masters.this, CustomerMaster.class));        		
        		//setContentView(R.layout.customermaster);    		
        	}
        });

        
      
        final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
		 btnMnuInvoice.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(Masters.this, InvoiceCreation.class));	        		
	        		//finish();
	        	}
	        });
		 
		 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
		 btnMnuMasters.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(Masters.this, Masters.class));	        		
	        		//finish();
	        	}
	        });
		 
		 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
		 btnMnuLists.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
	        		startActivity(new Intent(Masters.this, Folders.class));	        		
	        		//finish();
	        	}
	        });
		 
        
	}
}
