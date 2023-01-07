/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;


public class InvoiceOptions extends  BaseActivity  {
	public static final String KEY_IMAGE = "image";

	SQLiteDatabase myDB; 
	 ContentValues initialValues;
	public int Qty=1,Shipping=0, PCode=1 , Rate=1 , Discount=0 , Tax=0 , PDesc=0 , Pkg=0 , IHeader=0 , PmntDet=0 ,PImages=0 ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoiceoptions);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

		 //Qty ,Shipping , PCode , Rate , Discount , Tax , PDesc , Pkg , IHeader , PmntDet ,PImage   //For Content Values 
		//Qty=0,Shipping=0, PCode=0 , Rate=0 , Discount=0 , Tax=0 , PDesc=0 , Pkg=0 , IHeader=0 , PmntDet=0 ,PImages=0
		
		// GetCheckedDetails();
		 initialValues = new ContentValues();
         initialValues.put("Qty", Qty );
         initialValues.put("Shipping", Shipping);
         initialValues.put("PCode", PCode);
         initialValues.put("Rate", Rate);
         initialValues.put("Discount", Discount);
         initialValues.put("Tax", Tax);
         initialValues.put("PDesc", PDesc);
         initialValues.put("Pkg", Pkg);
         initialValues.put("IHeader", IHeader);
         initialValues.put("PmntDet", PmntDet);
         initialValues.put("PImage", PImages);
         GetOptionsFromDB();
         GetCheckedDetails();
        
         //Toast.makeText(getApplicationContext(), "Item checked: " , Toast.LENGTH_LONG).show();    
         final ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeScreen);
		 btnHome.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(InvoiceOptions.this, MenuForm.class));	        		
	        		//finish();
	        	}
	        });
		 final Button btnInvOptCancel = (Button)findViewById(R.id.btnInvOptCancel);
		 btnInvOptCancel.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	        		
	        		//Toast.makeText(getApplicationContext(), "Item place to add: " , Toast.LENGTH_LONG).show();        		
	        		//finish();
	        	}
	        });
//		  final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
//			 btnMnuInvoice.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		startActivity(new Intent(InvoiceOptions.this, InvoiceCreation.class));
//		        		//finish();
//		        	}
//		        });
			 
//			 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
//			 btnMnuMasters.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		startActivity(new Intent(InvoiceOptions.this, Masters.class));
//		        		//finish();
//		        	}
//		        });
			 
//			 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
//			 btnMnuLists.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
//		        		startActivity(new Intent(InvoiceOptions.this, Folders.class));
//		        		//finish();
//		        	}
//		        });
			 
		 final Button btnInvOptSave = (Button)findViewById(R.id.btnInvOptSave);
		 btnInvOptSave.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	        		
	        		
	        		GetCheckedDetails();
	        		initialValues = new ContentValues();
	                initialValues.put("Qty", Qty );
	                initialValues.put("Shipping", Shipping);
	                initialValues.put("PCode", PCode);
	                initialValues.put("Rate", Rate);
	                initialValues.put("Discount", Discount);
	                initialValues.put("Tax", Tax);
	                initialValues.put("PDesc", PDesc);
	                initialValues.put("Pkg", Pkg);
	                initialValues.put("IHeader", IHeader);
	                initialValues.put("PmntDet", PmntDet);
	                initialValues.put("PImage", PImages);
	                DbVyaapar d = new DbVyaapar(getApplicationContext());
	                d.UpdateInvoiceOptions(initialValues);
	                d.closeConnection();
	                //Toast.makeText(getApplicationContext(), "Options are Updated" , Toast.LENGTH_LONG).show();
	        		finish();
	        	}
	        });
	}

	//Variables assigned from checkboxes
	public void GetCheckedDetails()
	{
		//Qty,Shipping, PCode , Rate , Discount , Tax , PDesc , Pkg , IHeader , PmntDet ,PImages ;
		CheckBox ChkBxQty =( CheckBox ) findViewById( R.id.chkQty );
		CheckBox  ChkBxShipping =( CheckBox ) findViewById( R.id.chkShipping );
		CheckBox  ChkBxPCode =( CheckBox ) findViewById( R.id.chkProductcode );
		CheckBox  ChkBxRate =( CheckBox ) findViewById( R.id.chkRate );
		CheckBox  ChkBxDiscount =( CheckBox ) findViewById( R.id.chkDiscount );
		CheckBox  ChkBxTax =( CheckBox ) findViewById( R.id.chkTax );
		CheckBox  ChkBxPDesc =( CheckBox ) findViewById( R.id.chkPdesc );
		CheckBox  ChkBxPkg =( CheckBox ) findViewById( R.id.chkpkg );
		CheckBox  ChkBxIHeader =( CheckBox ) findViewById( R.id.chkIH );
		CheckBox  ChkBxPmntDet =( CheckBox ) findViewById( R.id.chkPmt );
		CheckBox  ChkBxPImages =( CheckBox ) findViewById( R.id.chkPImg );
		if(ChkBxQty.isChecked())
			Qty= 1;
		if(ChkBxShipping.isChecked())
			Shipping=1;
		else
			Shipping=0;
		if(ChkBxPCode.isChecked())
			PCode=1;		
		if(ChkBxRate.isChecked())
			Rate=1;
		if(ChkBxDiscount.isChecked())
			Discount =1;
		else
			Discount=0;
		if(ChkBxTax.isChecked())
			Tax=1;
		else
			Tax=0;
		if(ChkBxPDesc.isChecked())
			PDesc=1;
		else
			PDesc=0;
		if(ChkBxPkg.isChecked())
			Pkg=1;
		else
			Pkg=0;
		if(ChkBxIHeader.isChecked())
			IHeader=1;
		else
			IHeader=0;
		if(ChkBxPmntDet.isChecked())
			PmntDet=1;
		else
			PmntDet=0;
		if(ChkBxPImages.isChecked())
			PImages=1;
		else
			PImages=0;
		SetCheckedDetails();
	}
	
	//Check boxes assaigned from variables
	public void SetCheckedDetails()
	{
		//Qty,Shipping, PCode , Rate , Discount , Tax , PDesc , Pkg , IHeader , PmntDet ,PImages ;
		CheckBox ChkBxQty =( CheckBox ) findViewById( R.id.chkQty );
		  CheckBox  ChkBxShipping =( CheckBox ) findViewById( R.id.chkShipping );
		CheckBox  ChkBxPCode =( CheckBox ) findViewById( R.id.chkProductcode );
		CheckBox  ChkBxRate =( CheckBox ) findViewById( R.id.chkRate );
		CheckBox  ChkBxDiscount =( CheckBox ) findViewById( R.id.chkDiscount );
		CheckBox  ChkBxTax =( CheckBox ) findViewById( R.id.chkTax );
		CheckBox  ChkBxPDesc =( CheckBox ) findViewById( R.id.chkPdesc );
		CheckBox  ChkBxPkg =( CheckBox ) findViewById( R.id.chkpkg );
		CheckBox  ChkBxIHeader =( CheckBox ) findViewById( R.id.chkIH );
		CheckBox  ChkBxPmntDet =( CheckBox ) findViewById( R.id.chkPmt );
		CheckBox  ChkBxPImages =( CheckBox ) findViewById( R.id.chkPImg );
		/*if(Qty== 1)
			ChkBxQty.setChecked(true);
		else
			ChkBxQty.setChecked(false);		*/  
		if(Shipping==1)
			ChkBxShipping.setChecked(true);
		else
			ChkBxShipping.setChecked(false);
		//Toast.makeText(getApplicationContext(), "Item checked: from set " +Shipping + "chk" + ChkBxShipping.isChecked()  , Toast.LENGTH_LONG).show();	
		/*if(PCode==1)
			ChkBxPCode.setChecked(true);
		else
			ChkBxPCode.setChecked(false);			
		if(Rate==1)
			ChkBxRate.setChecked(true);
		else
			ChkBxRate.setChecked(false);	*/	
		if(Discount ==1)
			ChkBxDiscount.setChecked(true);
		else
			ChkBxDiscount.setChecked(false);
		if(Tax==1)
			ChkBxTax.setChecked(true);
		else
			ChkBxTax.setChecked(false);
		if(PDesc==1)
			ChkBxPDesc.setChecked(true);
		else
			ChkBxPDesc.setChecked(false);
		if(Pkg==1)
			ChkBxPkg.setChecked(true);
		else
			ChkBxPkg.setChecked(false);
		if(	IHeader==1)
			ChkBxIHeader.setChecked(true);
		else
			ChkBxIHeader.setChecked(false);
		if(PmntDet==1)
			ChkBxPmntDet.setChecked(true);
		else
			ChkBxPmntDet.setChecked(false);
		if(PImages==1)
			ChkBxPImages.setChecked(true);
		else
			ChkBxPImages.setChecked(false);
		
	}

	//variables assaigned from database
	public void GetOptionsFromDB() {
		int ExistsFlag = 0;
		DbVyaapar d = new DbVyaapar(getApplicationContext());
		try
		{			 
	         Cursor cInvOpt = d.getInvoiceOptions();	         
	         if (cInvOpt != null ) {
	 		    if  (cInvOpt.moveToFirst()) {
	 		        do { 	
	 		        	ExistsFlag =1;
	 		        if(cInvOpt.getString(cInvOpt.getColumnIndex("Qty"))== "1")
	 		   			Qty= 1; else Qty = 0;
	 		      //Toast.makeText(getApplicationContext(), "Item ....................  place from DB: " + cInvOpt.getString(cInvOpt.getColumnIndex("Shipping")), Toast.LENGTH_LONG).show();
	 		      Shipping=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("Shipping")));
	 		      /*CheckBox  ChkBxShipping =( CheckBox ) findViewById( R.id.chkShipping );
	 		      if(Shipping==1)
	 		      ChkBxShipping.setChecked(true);
	 		      else
	 		    	 ChkBxShipping.setChecked(false);*/
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("PCode"))== "1")
	 		   			PCode= Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("PCode")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("Rate"))== "1")
	 		   			Rate=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("Rate")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("Discount"))== "1")
	 		   			Discount =Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("Discount")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("Tax"))== "1")
	 		   			Tax=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("Tax")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("PDesc"))== "1")
	 		   			PDesc=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("PDesc")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("Pkg"))== "1")
	 		   			Pkg=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("Pkg")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("IHeader"))== "1")
	 		   			IHeader=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("IHeader")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("PmntDet"))== "1")
	 		   			PmntDet=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("PmntDet")));
	 		   		//if(cInvOpt.getString(cInvOpt.getColumnIndex("PImage"))== "0")
	 		   			PImages=Integer.parseInt(cInvOpt.getString(cInvOpt.getColumnIndex("PImage")));
	 		   	//Toast.makeText(getApplicationContext(), "Item checked from get db: "+ PImages + cInvOpt.getString(cInvOpt.getColumnIndex("PImage"))+ "Imagefotl", Toast.LENGTH_LONG).show();
	 		        }while (cInvOpt.moveToNext());
	 		    }cInvOpt.close();
	 		}
	         if(ExistsFlag != 1)
	        	 d.CreateInvoiceOptions(initialValues);
			d.closeConnection();
			SetCheckedDetails();
		}
		catch (Exception e) {
			// TODO: handle exception
			 if(ExistsFlag != 1)
	        	 d.CreateInvoiceOptions(initialValues);
		}
		
	}

}

