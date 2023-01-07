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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class CustomerMaster extends BaseActivity {
	public static String Edit = "0";
	public static String EFName = "";
	String Exists="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customermaster);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

        Spinner spinner = (Spinner) findViewById(R.id.spCustType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.CustTypeArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        
        Bundle b=getIntent().getExtras();
        String value="";
        if(b!= null)
        {
        	
         value=b.getString("returnKey1");
         Edit = "1";
         EFName = value;         
         DbVyaapar d = new DbVyaapar(getApplicationContext());
         Cursor cCustomer= d.getCustomerDetails(EFName);
         EditText txtCustName  =(EditText) findViewById(R.id.txtCustName);
     	EditText txtBranch  =(EditText) findViewById(R.id.txtBranch);
     	EditText txtCustEmail  =(EditText) findViewById(R.id.txtCustEmail);
     	EditText autoTxtCustCity  =(EditText) findViewById(R.id.autoTxtCustCity);
     	EditText autotxtCustZip  =(EditText) findViewById(R.id.autotxtCustZip);
     	EditText txtCustMobile  =(EditText) findViewById(R.id.txtCustMobile);
     	EditText txtCustLandLine  =(EditText) findViewById(R.id.txtCustLandLine);
     	EditText txtCustAddr1  =(EditText) findViewById(R.id.txtCustAddr1);
     	EditText txtCustAddr2  =(EditText) findViewById(R.id.txtCustAddr2);
     	EditText txtCustTIN  =(EditText) findViewById(R.id.txtCustTIN);
     	EditText txtCustNotes  =(EditText) findViewById(R.id.txtCustNotes);
     	Spinner spCusType = (Spinner) findViewById(R.id.spCustType);
         if(cCustomer!= null)
         {
        	 if(cCustomer.moveToFirst())
        	 {
	        	 do
	        	 { 
	        		 //Toast.makeText(getApplicationContext(), "You had clicked................"+value  , Toast.LENGTH_LONG).show();
	        		 txtCustName.setText(cCustomer.getString(cCustomer.getColumnIndex("CName")));
	        		 txtBranch.setText(cCustomer.getString(cCustomer.getColumnIndex("cBranch")));
	        		 txtCustEmail.setText(cCustomer.getString(cCustomer.getColumnIndex("CEmail")));
	        		 autoTxtCustCity.setText(cCustomer.getString(cCustomer.getColumnIndex("CCity")));
	        		 
	        		 autotxtCustZip.setText(cCustomer.getString(cCustomer.getColumnIndex("CZip")));
	        		 txtCustMobile.setText(cCustomer.getString(cCustomer.getColumnIndex("CMobile")));
	        		 txtCustLandLine.setText(cCustomer.getString(cCustomer.getColumnIndex("CLandline")));
	        		 txtCustAddr1.setText(cCustomer.getString(cCustomer.getColumnIndex("CAddr1")));	        		 
	        		 txtCustAddr2.setText(cCustomer.getString(cCustomer.getColumnIndex("CAddr2")));
	        		 txtCustTIN.setText(cCustomer.getString(cCustomer.getColumnIndex("CTIN")));
	        		 txtCustNotes.setText(cCustomer.getString(cCustomer.getColumnIndex("CNotes")));
	        		 if(cCustomer.getString(cCustomer.getColumnIndex("CType"))=="Credit")
	        		 spCusType.setSelection(1);
	        		 else
	        			 spCusType.setSelection(0);
	        		 //spCusType.setId(idcCustomer.getString(cCustomer.getColumnIndex("CType")));
	        		 //spCusType.setText(cCustomer.getString(cCustomer.getColumnIndex("CType")));	        		 
	        	 }while(cCustomer.moveToNext());
        	 }cCustomer.close();             
         }
         d.closeConnection();
        }
        
        
//        final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
//		 btnMnuInvoice.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		startActivity(new Intent(CustomerMaster.this, InvoiceCreation.class));
//	        		//finish();
//	        	}
//	        });
		 
//		 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
//		 btnMnuMasters.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		startActivity(new Intent(CustomerMaster.this, Masters.class));
//	        		//finish();
//	        	}
//	        });
//
//		 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
//		 btnMnuLists.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
//	        		startActivity(new Intent(CustomerMaster.this, Folders.class));
//	        		//finish();
//	        	}
//	        });
		 
        
        ((Button) findViewById(R.id.btnCustomerSave)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	EditText txtCustName  =(EditText) findViewById(R.id.txtCustName);
            	EditText txtBranch  =(EditText) findViewById(R.id.txtBranch);
            	EditText txtCustEmail  =(EditText) findViewById(R.id.txtCustEmail);
            	EditText autoTxtCustCity  =(EditText) findViewById(R.id.autoTxtCustCity);
            	EditText autotxtCustZip  =(EditText) findViewById(R.id.autotxtCustZip);
            	EditText txtCustMobile  =(EditText) findViewById(R.id.txtCustMobile);
            	EditText txtCustLandLine  =(EditText) findViewById(R.id.txtCustLandLine);
            	EditText txtCustAddr1  =(EditText) findViewById(R.id.txtCustAddr1);
            	EditText txtCustAddr2  =(EditText) findViewById(R.id.txtCustAddr2);
            	EditText txtCustTIN  =(EditText) findViewById(R.id.txtCustTIN);
            	EditText txtCustNotes  =(EditText) findViewById(R.id.txtCustNotes);
            	Spinner spCusType = (Spinner) findViewById(R.id.spCustType);
            	String str="";
            	String Empty="";
            	/*if(txtBranch.getText().toString().trim().equals(Empty) )
        		{
            		txtBranch.setError("Please Enter Customer Branch");   	
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Branch!" ,Toast.LENGTH_LONG).show();
        			return;
        		}  */  
            	if(txtCustName.getText().toString().trim().equals(Empty) )
        		{
            		txtCustName.setError("Please Enter Customer Name");   	        	    	
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Name!" ,Toast.LENGTH_LONG).show();
        			return;
        		}            		
            	/*if(txtCustAddr1.getText().toString().trim().equals(Empty) )
        		{
            		txtCustAddr1.setError("Please Enter Customer Address");   	
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Address!" ,Toast.LENGTH_LONG).show();
        			return;
        		} */
            	if(autoTxtCustCity.getText().toString().trim().equals(Empty) )
        		{
            		autoTxtCustCity.setError("Please Enter Customer City");   	   
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer City!" ,Toast.LENGTH_LONG).show();
        			return;
        		} 
            	/*if(autotxtCustZip.getText().toString().trim().equals(Empty) )
        		{
            		autotxtCustZip.setError("Please Enter Customer Zip");   	
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Zip!" ,Toast.LENGTH_LONG).show();
        			return;
        		} 
            	if(txtCustMobile.getText().toString().trim().equals(Empty) )
        		{
            		txtCustMobile.setError("Please Enter Customer Mobile");   
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Mobile!" ,Toast.LENGTH_LONG).show();
        			return;
        		} 
            	if(txtCustEmail.getText().toString().trim().equals(Empty) )
        		{
            		txtCustEmail.setError("Please Enter Customer Email");   
        			Toast.makeText(getApplicationContext(), "Please Fill Up Customer Email!" ,Toast.LENGTH_LONG).show();
        			return;
        		} */
            	ContentValues cValues = new ContentValues();
            	cValues.put("CName", txtCustName.getText().toString());
            	cValues.put("cBranch", txtBranch.getText().toString());
            	cValues.put("CType",spCusType.getSelectedItem().toString());  //txtCustName.getText().toString());
            	cValues.put("CEmail", txtCustEmail.getText().toString());
            	cValues.put("CAddr1", txtCustAddr1.getText().toString());
            	cValues.put("CAddr2", txtCustAddr2.getText().toString());
            	cValues.put("CTIN", txtCustTIN.getText().toString());
            	cValues.put("CCity", autoTxtCustCity.getText().toString());
            	cValues.put("CZip", autotxtCustZip.getText().toString());
            	cValues.put("CMobile", txtCustMobile.getText().toString());
            	cValues.put("CLandline", txtCustLandLine.getText().toString());
            	cValues.put("CNotes", txtCustNotes.getText().toString());            	
            	try
            	{
            		DbVyaapar dv = new DbVyaapar(getApplicationContext());
            		if(Edit=="0")
            			str= dv.InsertCustomer( txtCustName.getText().toString() , txtBranch.getText().toString(),spCusType.getSelectedItem().toString(), txtCustEmail.getText().toString(), txtCustAddr1.getText().toString(), txtCustAddr2.getText().toString(), txtCustTIN.getText().toString(), autoTxtCustCity.getText().toString(), autotxtCustZip.getText().toString(), txtCustMobile.getText().toString(), txtCustLandLine.getText().toString() , txtCustNotes.getText().toString());
            		else
            		{
            			dv.UpdateCustomer(cValues, EFName);
            			finish();
            		}
            		 
            		txtCustAddr1.setText("");
                 	txtCustName.setText("");
                 	txtBranch.setText("");
                 	txtCustEmail.setText("");
                 	autoTxtCustCity.setText("");
                 	autotxtCustZip.setText("");
                 	txtCustMobile.setText("");
                 	txtCustLandLine.setText("");
                 	txtCustAddr1.setText("");
                 	txtCustAddr2.setText("");
                 	txtCustTIN.setText("");
                 	txtCustNotes.setText("");
                 	finish();
            	}
            	catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        
        
        
        
        ((Button) findViewById(R.id.btnCustomerCancel)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	
            	EditText txtCustName  =(EditText) findViewById(R.id.txtCustName);
            	EditText txtBranch  =(EditText) findViewById(R.id.txtBranch);
            	EditText txtCustEmail  =(EditText) findViewById(R.id.txtCustEmail);
            	EditText autoTxtCustCity  =(EditText) findViewById(R.id.autoTxtCustCity);
            	EditText autotxtCustZip  =(EditText) findViewById(R.id.autotxtCustZip);
            	EditText txtCustMobile  =(EditText) findViewById(R.id.txtCustMobile);
            	EditText txtCustLandLine  =(EditText) findViewById(R.id.txtCustLandLine);
            	EditText txtCustAddr1  =(EditText) findViewById(R.id.txtCustAddr1);
            	EditText txtCustAddr2  =(EditText) findViewById(R.id.txtCustAddr2);
            	EditText txtCustTIN  =(EditText) findViewById(R.id.txtCustTIN);
            	EditText txtCustNotes  =(EditText) findViewById(R.id.txtCustNotes);
            	//Spinner spCusType = (Spinner) findViewById(R.id.spCustType);
            	
            	txtCustAddr1.setText("");
            	txtCustName.setText("");
            	txtBranch.setText("");
            	txtCustEmail.setText("");
            	autoTxtCustCity.setText("");
            	autotxtCustZip.setText("");
            	txtCustMobile.setText("");
            	txtCustLandLine.setText("");
            	txtCustAddr1.setText("");
            	txtCustAddr2.setText("");
            	txtCustTIN.setText("");
            	txtCustNotes.setText("");
            	//Spinner spCusType = (Spinner) findViewById(R.id.spCustType);
            	 //Toast.makeText(this, "You had cleaned the Customer page"  , Toast.LENGTH_LONG).show();
            	
            	if(Edit!= "0")
            		finish();
               
            }
        });
	
        ((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(CustomerMaster.this, MenuForm.class));
            }
        });
	
        
        
        final EditText txtCustName = (EditText)findViewById(R.id.txtCustName);
        txtCustName.addTextChangedListener(new TextWatcher(){
   	        public void afterTextChanged(Editable s) {
   	          //implement code
   	        	
   	        }
   	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
   	        public void onTextChanged(CharSequence s, int start, int before, int count)
   	        {
   	        	String CustName = txtCustName.getText().toString().trim();
   	        	if(CustName.contentEquals(""))
   	        	{
   	        		txtCustName.setError("Please Enter Customer Name");   	        	    	        	        	
   	        	}
   	        }
   	    }); 
        txtCustName.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String CustName = txtCustName.getText().toString();
   	        	if(CustName.contentEquals(""))
   	        	{
   	        		txtCustName.setError("Please Enter Customer Name");   	        	    	        	        	
   	        	}
   	        	try
   	        	{
	   	        	DbVyaapar d= new 	DbVyaapar(getApplicationContext());
	   	        	Cursor cur = d.getProduct(CustName);
	   	        	if (cur != null )
	   	        	{
	   	        	 if(cur.moveToFirst())
	   	        	 {
	   	        		 do{
	   	        			 Exists="Yes";
	   	        			txtCustName.setError("Customer Already Exists!!!");
	   	        		 }while(cur.moveToNext());
	   	        	 }else
		   	        		Exists="";
	   	        		
	   	        	}
	   	        	else
	   	        		Exists="";
	   	        	cur.close();
	   	 			d.closeConnection();
   	        	}
   	        	catch (Exception e) {
					// TODO: handle exception
				}
            }
        });
        
        final EditText txtBranch  =(EditText) findViewById(R.id.txtBranch);
        txtBranch.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	/*String Branch = txtBranch.getText().toString();
   	        	if(Branch.contentEquals(""))
   	        	{
   	        		txtBranch.setError("Please Enter Customer Branch");   	        	    	        	        	
   	        	} */  	        	
            }
        });
        
        final EditText txtCustAddr1  =(EditText) findViewById(R.id.txtCustAddr1);
        txtCustAddr1.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	/*String CustAddr1 = txtCustAddr1.getText().toString();
   	        	if(CustAddr1.contentEquals(""))
   	        	{
   	        		txtCustAddr1.setError("Please Enter Customer Address");   	        	    	        	        	
   	        	} */  	        	
            }
        });
        
        final EditText autoTxtCustCity  =(EditText) findViewById(R.id.autoTxtCustCity);
        autoTxtCustCity.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String City = autoTxtCustCity.getText().toString();
   	        	if(City.contentEquals(""))
   	        	{
   	        		autoTxtCustCity.setError("Please Enter Customer City");   	        	    	        	        	
   	        	}   	        	
            }
        });
                
        final EditText autotxtCustZip  =(EditText) findViewById(R.id.autotxtCustZip);
        autotxtCustZip.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	/*String City = autotxtCustZip.getText().toString();
   	        	if(City.contentEquals(""))
   	        	{
   	        		autotxtCustZip.setError("Please Enter Customer Zip");   	        	    	        	        	
   	        	} */  	        	
            }
        });
        
        final EditText txtCustMobile  =(EditText) findViewById(R.id.txtCustMobile);
        txtCustMobile.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	/*String Mobile = txtCustMobile.getText().toString();
   	        	if(Mobile.contentEquals(""))
   	        	{
   	        		txtCustMobile.setError("Please Enter Customer Mobile");   	        	    	        	        	
   	        	} */  	        	
            }
        });
        
        final EditText txtCustEmail  =(EditText) findViewById(R.id.txtCustEmail);
        txtCustEmail.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	/*String Mobile = txtCustEmail.getText().toString();
   	        	if(Mobile.contentEquals(""))
   	        	{
   	        		txtCustEmail.setError("Please Enter Customer Email");   	        	    	        	        	
   	        	} */  	        	
            }
        });
	}
}
