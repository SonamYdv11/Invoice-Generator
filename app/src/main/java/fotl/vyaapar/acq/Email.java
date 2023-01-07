/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;


public class Email extends  BaseActivity  {
	public static final String KEY_IMAGE = "image";
	public List<String> list2 = new ArrayList<String>();
	SQLiteDatabase myDB; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailpage);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

		final ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeScreen);
		 btnHome.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(Email.this, MenuForm.class));
	        	}
	        });
		
		 
		  final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
			 btnMnuInvoice.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v)
		        	{	
		        		startActivity(new Intent(Email.this, InvoiceCreation.class));	        		
		        		//finish();
		        	}
		        });
			 
			 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
			 btnMnuMasters.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v)
		        	{	
		        		startActivity(new Intent(Email.this, Masters.class));	        		
		        		//finish();
		        	}
		        });
			 
			 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
			 btnMnuLists.setOnClickListener(new OnClickListener(){
		        	public void onClick(View v)
		        	{	
		        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
		        		startActivity(new Intent(Email.this, Folders.class));	        		
		        		//finish();
		        	}
		        });
			 
		 
		 final Button btnSend = (Button) findViewById(R.id.btnSend);
		 btnSend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				EditText address = (EditText)findViewById(R.id.txtTo);
				EditText subject = (EditText)findViewById(R.id.txtSubject);
				EditText emailtext = (EditText)findViewById(R.id.txtDescription);
				final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
				emailIntent.setType("plain/text");
				String Customer= "";
                try
   				{
   					DbVyaapar d = new DbVyaapar(getApplicationContext());
   					Cursor cc= d.getCustomerEmailId(address.getText().toString());
   					
   					if (cc != null) {
   						Toast.makeText(getApplicationContext(), "check with customer Email address", Toast.LENGTH_SHORT).show();
						Customer = cc.getString(cc.getColumnIndex("CEMAIL"));
					}
   				}
   				catch (Exception e) {
   					// TODO: handle exception
   				}
              
                   emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ Customer });
            
                   emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject.getText());
            
                   emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext.getText());
    
                   Email.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                   
                  
                   
			}
		});
	}
	
	}

