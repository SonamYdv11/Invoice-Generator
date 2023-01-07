/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class CompanyContact extends BaseActivity {
	
	public static String Exists="0";

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.companycontact);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

        DbVyaapar d = new DbVyaapar(getApplicationContext());
        Cursor CContact= d.getCompanyContact();
        
        if (CContact != null ) {
		    if  (CContact.moveToFirst()) {
		        do {
		        	Exists ="1";
		        	EditText txtCompanyName  =(EditText) findViewById(R.id.txtCompanyName);
	            	EditText txtCompanyContactAddr1  =(EditText) findViewById(R.id.txtCompanyContactAddr1);
	            	EditText txtCompanyContactAddr2  =(EditText) findViewById(R.id.txtCompanyContactAddr2);
	            	EditText txtCompanyContactAddr3  =(EditText) findViewById(R.id.txtCompanyContactAddr3);
	            	EditText txtCompanyContactState  =(EditText) findViewById(R.id.txtCompanyContactState);
	            	EditText txtCompanyContactZip    =(EditText) findViewById(R.id.txtCompanyContactZip);
	            	EditText txtCompanyContactSales  =(EditText) findViewById(R.id.txtCompanyContactSales);
	            	EditText txtCompanyContactOffice =(EditText) findViewById(R.id.txtCompanyContactOffice);
	            	EditText txtCompanyContactMobile =(EditText) findViewById(R.id.txtCompanyContactMobile);
	            	EditText txtCompanyContactEmail  =(EditText) findViewById(R.id.txtCompanyContactEmail);
	            	EditText txtCompanyContactWebsite=(EditText) findViewById(R.id.txtCompanyContactWebsite);
	            	
	            	txtCompanyName.setText(CContact.getString(CContact.getColumnIndex("CCName")));
	            	txtCompanyContactAddr1.setText(CContact.getString(CContact.getColumnIndex("CCAddr1")));
	            	txtCompanyContactAddr2.setText(CContact.getString(CContact.getColumnIndex("CCAddr2")));
	            	txtCompanyContactAddr3.setText(CContact.getString(CContact.getColumnIndex("CCAddr3")));
	            	txtCompanyContactState.setText(CContact.getString(CContact.getColumnIndex("CCState")));
	            	txtCompanyContactZip.setText(CContact.getString(CContact.getColumnIndex("CCZip")));
	            	txtCompanyContactSales.setText(CContact.getString(CContact.getColumnIndex("CCSales")));
	            	txtCompanyContactOffice.setText(CContact.getString(CContact.getColumnIndex("CCOffice")));
	            	txtCompanyContactMobile.setText(CContact.getString(CContact.getColumnIndex("CCMobile")));
	            	txtCompanyContactEmail.setText(CContact.getString(CContact.getColumnIndex("CCEmail")));
	            	txtCompanyContactWebsite.setText(CContact.getString(CContact.getColumnIndex("CCWebsite")));		            
		        }while (CContact.moveToNext());
		    }
		    CContact.close();
		    d.closeConnection();
		}
        

        final EditText txtCompanyContactAddr1  =(EditText) findViewById(R.id.txtCompanyContactAddr1);
        txtCompanyContactAddr1.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String Branch = txtCompanyContactAddr1.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtCompanyContactAddr1.setError("Please Enter Company Address");   	        	    	        	        	
   	        	}   	        	
            }
        });

        final EditText txtCompanyName  =(EditText) findViewById(R.id.txtCompanyName);
        txtCompanyName.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String Branch = txtCompanyName.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtCompanyName.setError("Please Enter Company Name");   	        	    	        	        	
   	        	}   	        	
            }
        });
        
        final EditText txtCompanyContactEmail  =(EditText) findViewById(R.id.txtCompanyContactEmail);
        txtCompanyContactEmail.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String Branch = txtCompanyContactEmail.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtCompanyContactEmail.setError("Please Enter Company Email");   	        	    	        	        	
   	        	}   	        	
            }
        });
        
        final EditText txtCompanyContactSales  =(EditText) findViewById(R.id.txtCompanyContactSales);
        txtCompanyContactSales.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String Branch = txtCompanyContactSales.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtCompanyContactSales.setError("Please Enter Company Sales");   	        	    	        	        	
   	        	}   	        	
            }
        });
        
        final EditText txtCompanyContactOffice  =(EditText) findViewById(R.id.txtCompanyContactOffice);
        txtCompanyContactOffice.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String Branch = txtCompanyContactOffice.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtCompanyContactOffice.setError("Please Enter Company Office Phone");   	        	    	        	        	
   	        	}   	        	
            }
        });
        
        ((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                startActivity(new Intent(CompanyContact.this, MenuForm.class));
            }
        });
        ((Button) findViewById(R.id.btnCompanyContactSave)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	EditText txtCompanyName  =(EditText) findViewById(R.id.txtCompanyName);
            	EditText txtCompanyContactAddr1  =(EditText) findViewById(R.id.txtCompanyContactAddr1);
            	EditText txtCompanyContactAddr2  =(EditText) findViewById(R.id.txtCompanyContactAddr2);
            	EditText txtCompanyContactAddr3  =(EditText) findViewById(R.id.txtCompanyContactAddr3);
            	EditText txtCompanyContactState  =(EditText) findViewById(R.id.txtCompanyContactState);
            	EditText txtCompanyContactZip    =(EditText) findViewById(R.id.txtCompanyContactZip);
            	EditText txtCompanyContactSales  =(EditText) findViewById(R.id.txtCompanyContactSales);
            	EditText txtCompanyContactOffice =(EditText) findViewById(R.id.txtCompanyContactOffice);
            	EditText txtCompanyContactMobile =(EditText) findViewById(R.id.txtCompanyContactMobile);
            	EditText txtCompanyContactEmail  =(EditText) findViewById(R.id.txtCompanyContactEmail);
            	EditText txtCompanyContactWebsite=(EditText) findViewById(R.id.txtCompanyContactWebsite);
            	String Empty="";
            	try
            	{
            		
            		if(txtCompanyContactAddr1.getText().toString().trim().equals(Empty) )
            		{
            			txtCompanyContactAddr1.setError("Please Enter Company Address");      	
            			Toast.makeText(getApplicationContext(), "Please Fill Up Company Address!" ,Toast.LENGTH_LONG).show();
            			return;
            		}  
            		if(txtCompanyName.getText().toString().trim().equals(Empty) )
            		{
            			txtCompanyName.setError("Please Enter Company Name");   	
            			Toast.makeText(getApplicationContext(), "Please Fill Up Company Name!" ,Toast.LENGTH_LONG).show();
            			return;
            		}  
            		if(txtCompanyContactEmail.getText().toString().trim().equals(Empty) )
            		{
            			txtCompanyContactEmail.setError("Please Enter Company Contact Email");   	
            			Toast.makeText(getApplicationContext(), "Please Fill Up Company Contact Email!" ,Toast.LENGTH_LONG).show();
            			return;
            		}  
            		if(txtCompanyContactSales.getText().toString().trim().equals(Empty) )
            		{
            			txtCompanyContactSales.setError("Please Enter  Company Contact for Sales");   	
            			Toast.makeText(getApplicationContext(), "Please Fill Up Company Contact for Sales!" ,Toast.LENGTH_LONG).show();
            			return;
            		}  
            		if(txtCompanyContactOffice.getText().toString().trim().equals(Empty) )
            		{
            			txtCompanyContactOffice.setError("Please Enter Company Contact For Office");   	
            			Toast.makeText(getApplicationContext(), "Please Fill Up Company Contact For Office!" ,Toast.LENGTH_LONG).show();
            			return;
            		}  
            		
            		DbVyaapar dv = new DbVyaapar(getApplicationContext());
            		ContentValues CCContent = new ContentValues();
            		
            		
            		
            		
            		CCContent.put("CCName", txtCompanyName.getText().toString());
            		CCContent.put("CCAddr1", txtCompanyContactAddr1.getText().toString());
            		CCContent.put("CCAddr2", txtCompanyContactAddr2.getText().toString());
            		CCContent.put("CCAddr3", txtCompanyContactAddr3.getText().toString());
            		CCContent.put("CCState", txtCompanyContactState.getText().toString());
            		CCContent.put("CCZip", txtCompanyContactZip.getText().toString());
            		CCContent.put("CCSales", txtCompanyContactSales.getText().toString());
            		CCContent.put("CCOffice", txtCompanyContactOffice.getText().toString());
            		CCContent.put("CCMobile", txtCompanyContactMobile.getText().toString());
            		CCContent.put("CCEmail", txtCompanyContactEmail.getText().toString());
            		CCContent.put("CCWebsite", txtCompanyContactWebsite.getText().toString());
            		 
            		if(Exists=="0")
            		dv.InsertCompanyContact(CCContent);
            		else
            			dv.UpdateCompanyContact(CCContent);
            		
            		txtCompanyName.setText("");
                	txtCompanyContactAddr1.setText("");
                	txtCompanyContactAddr2.setText("");
                	txtCompanyContactAddr3.setText("");
                	txtCompanyContactState.setText("");
                	txtCompanyContactZip.setText("");
                	txtCompanyContactSales.setText("");
                	txtCompanyContactOffice.setText("");
                	txtCompanyContactMobile.setText("");
                	txtCompanyContactEmail.setText("");
                	txtCompanyContactWebsite.setText("");
                	 Toast.makeText(getApplicationContext(), "Details are Updated" , Toast.LENGTH_LONG).show();
                	finish();
            	}
            	catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        
        ((Button) findViewById(R.id.btnCompanyContactCancel)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	
            	EditText txtCompanyName  =(EditText) findViewById(R.id.txtCompanyName);
            	EditText txtCompanyContactAddr1  =(EditText) findViewById(R.id.txtCompanyContactAddr1);
            	EditText txtCompanyContactAddr2  =(EditText) findViewById(R.id.txtCompanyContactAddr2);
            	EditText txtCompanyContactAddr3  =(EditText) findViewById(R.id.txtCompanyContactAddr3);
            	EditText txtCompanyContactState  =(EditText) findViewById(R.id.txtCompanyContactState);
            	EditText txtCompanyContactZip    =(EditText) findViewById(R.id.txtCompanyContactZip);
            	EditText txtCompanyContactSales  =(EditText) findViewById(R.id.txtCompanyContactSales);
            	EditText txtCompanyContactOffice =(EditText) findViewById(R.id.txtCompanyContactOffice);
            	EditText txtCompanyContactMobile =(EditText) findViewById(R.id.txtCompanyContactMobile);
            	EditText txtCompanyContactEmail  =(EditText) findViewById(R.id.txtCompanyContactEmail);
            	EditText txtCompanyContactWebsite=(EditText) findViewById(R.id.txtCompanyContactWebsite);
            	            	
            	txtCompanyName.setText("");
            	txtCompanyContactAddr1.setText("");
            	txtCompanyContactAddr2.setText("");
            	txtCompanyContactAddr3.setText("");
            	txtCompanyContactState.setText("");
            	txtCompanyContactZip.setText("");
            	txtCompanyContactSales.setText("");
            	txtCompanyContactOffice.setText("");
            	txtCompanyContactMobile.setText("");
            	txtCompanyContactEmail.setText("");
            	txtCompanyContactWebsite.setText("");            	
            	finish();
            }
        });
	}
}
