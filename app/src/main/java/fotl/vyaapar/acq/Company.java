/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import java.io.FileInputStream;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.ActivityCompat;

public class Company extends BaseActivity {
	private static final int SELECT_PICTURE = 1;	
	private String selectedImagePath;
	public static final String KEY_IMAGE = "image";
	public static String Exists ="0";

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE) && shouldShowRequestPermissionRationale(RECORD_AUDIO) && shouldShowRequestPermissionRationale(INTERNET)) {
				showMessageOKCancel("You need to allow access to both the permissions",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
									requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
											200);
								}
							}
						});
				return;
			}
		}
	}
	private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(Company.this)
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", null)
				.create()
				.show();
	}

	@SuppressLint("ResourceType")
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,ACCESS_WIFI_STATE,RECORD_AUDIO,INTERNET}, 200);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

        DbVyaapar d = new DbVyaapar(getApplicationContext());
        Cursor company = d.getCompany();
        EditText txtCompanyTIN = (EditText) findViewById(R.id.txtCompanyTIN);
        EditText txtCompanyTax = (EditText) findViewById(R.id.txtCompanyTax);
        EditText txtCompanyCountry = (EditText) findViewById(R.id.txtCompanyCountry);

        if(company != null)
        {
        	  if  (company.moveToFirst()) {
	 		        do { 	
	 		        	txtCompanyTIN.setText(company.getString(company.getColumnIndex("CTIN")).trim());
	 		        	txtCompanyTax.setText(company.getString(company.getColumnIndex("CTax")).trim());
	 		        	txtCompanyCountry.setText(company.getString(company.getColumnIndex("CCountry")).trim());
	 		        	Exists="1";
	 		        	}while(company.moveToNext());
	 		        }
        	  company.close();
        	  d.closeConnection();
        }

		 
        
        ((ImageButton) findViewById(R.id.btnHomeScreen)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                startActivity(new Intent(Company.this, MenuForm2.class));
            }
        });

        ((Button) findViewById(R.id.btnInvoiceOptions)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                startActivity(new Intent(Company.this, InvoiceOptions.class));
            }
        });

        ((Button) findViewById(R.id.btnCompanyLogo)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });
        
        ((Button) findViewById(R.id.btnCompanyCancel)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
                EditText txtCompanyTIN = (EditText) findViewById(R.id.txtCompanyTIN);
                EditText txtCompanyTax = (EditText) findViewById(R.id.txtCompanyTax);
                EditText txtCompanyCountry = (EditText) findViewById(R.id.txtCompanyCountry);
                txtCompanyTIN.setText("");
                txtCompanyTax.setText("");
            }
        });

        ((Button) findViewById(R.id.btnCompany)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(Company.this, CompanyContact.class));
        		//setContentView(R.layout.companycontact);
            }
        });

        ((Button) findViewById(R.id.btnContactInfo)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	startActivity(new Intent(Company.this, CompanyContact.class));
        		//setContentView(R.layout.companycontact);
            }
        });



        ((Button) findViewById(R.id.btnCompanySave)).setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // in onCreate or any event where your want the user to
                // select a file
            	 EditText txtCompanyTIN = (EditText) findViewById(R.id.txtCompanyTIN);
                 EditText txtCompanyTax = (EditText) findViewById(R.id.txtCompanyTax);
                 EditText txtCompanyCountry = (EditText) findViewById(R.id.txtCompanyCountry);
                 Toast.makeText(getApplicationContext(), "Updating...."  , Toast.LENGTH_LONG).show();
            	try
            	{
            	 FileInputStream fis = new FileInputStream(selectedImagePath);
	             byte[] hash = new byte[fis.available()];
	             fis.read(hash);
	             fis.close();
	             ContentValues initialValues = new ContentValues();
	             initialValues.put("CCountry", txtCompanyCountry.getText().toString() );
	             initialValues.put("CTax", txtCompanyTax.getText().toString());
	             initialValues.put("CTIN", txtCompanyTIN.getText().toString());
	             initialValues.put(KEY_IMAGE, hash);

	             DbVyaapar dv = new DbVyaapar(getApplicationContext());
	             if(Exists=="0")
	            	 dv.InsertCompany(initialValues);
	             else
	            	 dv.updateCompany(initialValues);
	             Toast.makeText(getApplicationContext(), "Updated Successfully"  , Toast.LENGTH_LONG).show();
	            	 // dv.InsertProduct(initialValues);
	             txtCompanyTIN.setText("");
	             txtCompanyTax.setText("");
            	}
            	catch (Exception e) {

					// TODO: handle exception
				}
            }
        });
        
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	String  strMsg="";
    	   if (resultCode == RESULT_OK) {
    	         if (requestCode == SELECT_PICTURE) {
    	             Uri selectedImageUri = data.getData();    	            
    	             selectedImagePath = getPath(selectedImageUri);
    	             Toast.makeText(this, "Your db has been inserted with cello gel." + selectedImagePath , Toast.LENGTH_LONG).show(); 
    	             String picName = selectedImagePath;
    	             picName = picName.replace(".png", "");
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
}
