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
import java.io.FileInputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ProductMaster extends BaseActivity {
	private static final int SELECT_PICTURE = 1;	
	private String selectedImagePath;
	public static final String KEY_IMAGE = "image";
	public static String Edit="0";
	public static  byte[] hashimage = null;
	public static String EPcode="";
	public String ChangedImage = "0";
	public String Exists="";

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productmaster);
    	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

        Bundle b=getIntent().getExtras();
        String value="";
        if(b!= null)
        {
         value=b.getString("returnKey1");
         Edit = "1";
         String[] PCode = value.split(",-"); 
         EPcode=PCode[0];
         DbVyaapar d = new DbVyaapar(getApplicationContext());
         Cursor cProduct= d.getProdcutDetails(PCode[0]);
         EditText txtProdCode = (EditText) findViewById(R.id.txtProdCode);
         EditText txtProdRate = (EditText) findViewById(R.id.txtProdRate);
         EditText txtProdDesc = (EditText) findViewById(R.id.txtProdDesc);
         EditText txtProdSupplier = (EditText) findViewById(R.id.txtProdSupplier);
         ImageView mImageView = (ImageView) findViewById(R.id.imageView1);

         if(cProduct!= null)
         {
        	 if(cProduct.moveToFirst())
        	 {
	        	 do
	        	 {
	        		
	        		 txtProdCode.setText(cProduct.getString(cProduct.getColumnIndex("_id")));
	        		 txtProdDesc.setText(cProduct.getString(cProduct.getColumnIndex("PCode")));
	        		 txtProdRate.setText(cProduct.getString(cProduct.getColumnIndex("Rate")));
	        		 txtProdSupplier.setText(cProduct.getString(cProduct.getColumnIndex("Supplier")));
	     			
	     			if(!cProduct.isNull(cProduct.getColumnIndex("image")))
	     			{
	     			hashimage = cProduct.getBlob(cProduct.getColumnIndex("image"));
	        		 mImageView.setImageBitmap(getImageFromBLOB(cProduct.getBlob(cProduct.getColumnIndex("image"))));
	     			}
	     			
	        	 }while(cProduct.moveToNext());
        	 }cProduct.close();             
         }
         d.closeConnection();
        }       
        
        
        final EditText txtProdCode = (EditText)findViewById(R.id.txtProdCode);
        txtProdCode.addTextChangedListener(new TextWatcher(){
   	        public void afterTextChanged(Editable s) {
   	          //implement code
   	        	
   	        }
   	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
   	        public void onTextChanged(CharSequence s, int start, int before, int count)
   	        {
   	        	String ProdCode = txtProdCode.getText().toString().trim();
   	        	if(ProdCode.contentEquals(""))
   	        	{
   	        		//txtProdCode.setError("Please Enter Product Code");
   	        	    //Toast.makeText(getApplicationContext(), " Product Code already exists in the database"  , Toast.LENGTH_LONG).show();	        	        	
   	        	}
   	        }
   	    }); 
        txtProdCode.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                // Your validation code goes here
            	String ProdCode = txtProdCode.getText().toString();
   	        	if(ProdCode.contentEquals(""))
   	        	{
   	        		//txtProdCode.setError("Please Enter Product Code");   	        	    	        	        	
   	        	}
   	        	try
   	        	{
	   	        	DbVyaapar d= new 	DbVyaapar(getApplicationContext());
	   	        	Cursor cur = d.getProduct(ProdCode);
	   	        	if (cur != null )
	   	        	{
	   	        	 if(cur.moveToFirst())
	   	        	 {
	   	        		 do{
	   	        			 Exists="Yes";
	   	        			 //txtProdCode.setError("Product Already Exists!!!");
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
        

        
        ((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(ProductMaster.this, MenuForm.class));
            }
        });
        ((Button) findViewById(R.id.btnProdpick)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file            	
                Intent intent = new Intent();
                ChangedImage = "1";
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });
        
        ((Button) findViewById(R.id.btnProdCancel)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file            	
                EditText txtProdCode = (EditText) findViewById(R.id.txtProdCode);
                EditText txtProdRate = (EditText) findViewById(R.id.txtProdRate);
                EditText txtProdDesc = (EditText) findViewById(R.id.txtProdDesc);
                EditText txtProdSupplier = (EditText) findViewById(R.id.txtProdSupplier);
                
                txtProdCode.setText("");
                txtProdDesc.setText("");
                txtProdRate.setText("");
                txtProdSupplier.setText("");
            	
                if(Edit=="1")
                	finish();
            }
        });


		 
        ((Button) findViewById(R.id.btnProdSave)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	  EditText txtProdCode = (EditText) findViewById(R.id.txtProdCode);
                  EditText txtProdRate = (EditText) findViewById(R.id.txtProdRate);
                  EditText txtProdDesc = (EditText) findViewById(R.id.txtProdDesc);
                  EditText txtProdSupplier = (EditText) findViewById(R.id.txtProdSupplier);
                  byte[] hash;
                  String Empty="";
                  String Error="Product Already Exists!!!";
            	try
            	{   
            		
            		if(txtProdCode.getText().toString().trim().equals(Empty) )
            		{
            			Toast.makeText(getApplicationContext(), "Please Fill Up All details!" ,Toast.LENGTH_LONG).show();
            			return;
            		}            		
            		if(Exists.equals("Yes") )
            		{
            			Edit = "1";
            			//Toast.makeText(getApplicationContext(), "Product Already exists!" ,Toast.LENGTH_LONG).show();            			
            			//return;
            		}	
            		
            		//if(txtProdRate.getText().toString().trim().equals(Empty) )
            		//{
            		//	Toast.makeText(getApplicationContext(), "Please Fill Up All details!" ,Toast.LENGTH_LONG).show();
            		//	return;
            		//}
            		if(ChangedImage=="1")
            		{
            			FileInputStream fis = new FileInputStream(selectedImagePath);
            			hash = new byte[fis.available()]; 
            			fis.read(hash); 
            			fis.close();
            		}
            		else
            		{
            			hash = hashimage; 
            		}
            		String Rate = txtProdRate.getText().toString();
            		if(Rate.contentEquals(""))
            			Rate = "0";
	             ContentValues initialValues = new ContentValues();
	             initialValues.put("PCode", txtProdCode.getText().toString() );
	             initialValues.put("PDesc", txtProdDesc.getText().toString());
	             initialValues.put("Supplier", txtProdSupplier.getText().toString());
	             initialValues.put("Rate",Rate );
	             initialValues.put(KEY_IMAGE, hash);	             
	             DbVyaapar dv = new DbVyaapar(getApplicationContext());
	             if(Edit=="0")
         		{	            	 
	             dv.InsertProduct(initialValues);
         		}
	             else
	             {
	            	 dv.updateProductMaster(initialValues,EPcode );
	            	 Toast.makeText(getApplicationContext(), "Updated Successfully!!!" ,Toast.LENGTH_LONG).show();
	            	 Edit="0";
	            			 
	            	 finish();
	             }
            	}
            	catch (Exception e) {
            		
					// TODO: handle exception
				}
            	finish();
            }
        });
        
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	String  strMsg="";
    	   if (resultCode == RESULT_OK) {
    	         if (requestCode == SELECT_PICTURE) {
    	             Uri selectedImageUri = data.getData();
    	             File f = new File(""+selectedImageUri);
    	             selectedImagePath = getPath(selectedImageUri);
    	             //Toast.makeText(this, "Your db has been inserted with cello gel." + selectedImagePath , Toast.LENGTH_LONG).show(); 
    	             String picName = selectedImagePath;
    	             picName = picName.replace(".png", "");    	             
    	             Resources r = getResources();
    	             int picId = r.getIdentifier(picName, "drawable", "com.vyaapar.Invoice");
    	             //Toast.makeText(this, "Your db has been inserted with cello gel." + picId , Toast.LENGTH_LONG).show(); 
    	             ImageView image = (ImageView)findViewById(R.id.imageView1 );
    	             image.setImageURI(selectedImageUri);
    	         }
    	   }
	}
	
	
	
	 public String getPath(Uri uri) {
	        String[] projection = { MediaStore.Images.Media.DATA };
	        Cursor cursor = managedQuery(uri, projection, null, null, null);
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        
	        cursor.moveToFirst();
	        return cursor.getString(column_index);
	    }
	 
	 public static Bitmap getImageFromBLOB(byte[] mBlob) {
			byte[] bb = mBlob;
			return BitmapFactory.decodeByteArray(bb, 0, bb.length);
		}
}
