/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */
package fotl.vyaapar.acq;

import static fotl.vyaapar.acq.Constant.FIFTH_COLUMN;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.StrictMode;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import static fotl.vyaapar.acq.Constant.FIRST_COLUMN;
import static fotl.vyaapar.acq.Constant.FOURTH_COLUMN;
import static fotl.vyaapar.acq.Constant.SECOND_COLUMN;
import static fotl.vyaapar.acq.Constant.SIXTH_COLUMN;
import static fotl.vyaapar.acq.Constant.THIRD_COLUMN;
import static fotl.vyaapar.acq.Constant.ZEROTH_COLUMN;
import static fotl.vyaapar.acq.Constant.LAST_COLUMN;
import static fotl.vyaapar.acq.Constant.Image_COLUMN;
import static java.security.AccessController.getContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Font.FontFamily;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.PageSize;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class InvoiceCreation extends BaseActivity {
	public List<String> list2 = new ArrayList<String>();
	static final int DATE_DIALOG_ID = 0;
	static final int Product_Dialog_id = 10;
	static final int Shipping_Dialog_id = 20;
	static final int AddItems_Dialog_id = 30;
	Dialog screendialoge;
	Dialog AddItemsdialoge;
	Dialog Shipdialog;
	Dialog AddItems;
	DbVyaapar d;
	static InvoiceCreation obj;
	private ArrayList<HashMap<String, String>> list;
	public static final String KEY_IMAGE = "image";
	private int mYear;
	private int mMonth;
	private int mDay;
	public static String lstInvDetId = "0";
	public static String lstProdcode = "";
	public static String lstQuantity = "0";
	public static String lstDiscount = "0.0";
	public static String lstRate = "0.0";
	public static String lstFlag = "0";
	public static String lstInvId = "0";
 	double ITotal = 0.0;
 	double ITDisc = 0.0;
 	double ITShipping = 0.0;
 	double ITSales=0.0;
 	double aftot=0.0;
 	double qty =0.0;
 	double discount = 0.0;
 	double Rate = 0.0;
 	double Amt =0.0;
 	double ITpkg =0.0;
 	double ITamt= 0.0;
 	double AdDisc = 0.0;
 	public String CustomerEmail = "";
 	private SimpleCursorAdapter mCursorAdapter;
 	private TextToSpeech tts;
 	private EditText txtInvoiceNumber;
 	private EditText txtInvoiceDate;
	private Button btn_add_items;
	private LinearLayout linear_item_bar, linear_item_bar2;

	ArrayList<HashMap<String, String>> al_Customers = new ArrayList<HashMap<String, String>>();
	AutoCompleteTextView AutoDesc;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		obj=this;
		setContentView(R.layout.invoicecreation);
		txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

		final DbVyaapar d = new DbVyaapar(getApplicationContext());
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		AutoDesc  = (AutoCompleteTextView) findViewById(R.id.autotxtInvProdDesc);
		Cursor crp = d.getProducts();



		String[] cols = new String[] { "PCode2" };		
		int[] to1 = new int[] { android.R.id.text1 };
		SimpleCursorAdapter sadapter1 = new SimpleCursorAdapter(getApplicationContext(),android.R.layout.simple_dropdown_item_1line, crp, cols,to1);
		sadapter1.setDropDownViewResource(android.R.layout.two_line_list_item);
		//mCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, crp, cols, to1);
		AutoDesc.setThreshold(1);
		
		final Spinner sps1 = (Spinner) this.findViewById(R.id.spinvEditProductSelect);
		if(crp== null)
			Toast.makeText(getApplicationContext()," returned null ",Toast.LENGTH_SHORT).show();
		else
		{
			//Toast.makeText(	getApplicationContext() ,"done getting  Product... " , Toast.LENGTH_SHORT).show();
			sps1.setAdapter(sadapter1);			
			//AutoDesc.setAdapter(mCursorAdapter);
			AutoDesc.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getModel()));

		}

        AutoDesc.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view,int position, long id) {
                // Get the cursor, positioned to the corresponding row in the
                // result set
            	String sitem ="";
            	int pos = getposition();
            	String stext  = AutoDesc.getText().toString().trim();
            			

                EditText txtProdcutDesc = (EditText) findViewById(R.id.txtInvProdDesc);
                txtProdcutDesc.setText(stext);
            }
        });

       
        
        
	    AutoDesc.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	          //implement code
	        	//Toast.makeText(	getApplicationContext() ,"fired now... " , Toast.LENGTH_SHORT).show();
	        
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){ 	
	        }
	    }); 
	    // initialize AutocompleteTextView
	
		sps1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {				
				String spString = "";
				String spDesc = "";
				String spCode = "";
				String spSupplier = "";
				String spRate = "";
				
				Cursor cc = (Cursor) (sps1.getSelectedItem());
				//Toast.makeText(parentView.getContext(),"Setting Rate and discount Done "+ spString, Toast.LENGTH_SHORT).show();

				if (cc != null) {
					spString = cc.getString(cc.getColumnIndex("_id"));
					spDesc = cc.getString(cc.getColumnIndex("PDesc"));
					spCode = cc.getString(cc.getColumnIndex("PCode"));
					spSupplier = cc.getString(cc.getColumnIndex("Supplier"));
					spRate = cc.getString(cc.getColumnIndex("Rate"));

					//EditText txtProdcutCode = (EditText) findViewById(R.id.txtInvProdDesc);
					//txtProdcutCode.setText(spCode);
					
					lstProdcode = spCode;

					EditText txtProdcutDesc = (EditText) findViewById(R.id.txtInvProdDesc);
					txtProdcutDesc.setText(spDesc);								
					{
					ImageView mImageView = (ImageView) findViewById(R.id.pInvImage);
					mImageView.setImageBitmap(getImageFromBLOB(getImage(spCode) ));
					}
					EditText txtRate = (EditText) findViewById(R.id.txtInvProdRate);
					EditText txtDiscount = (EditText) findViewById(R.id.txtProdDiscount);
					if (lstFlag == "0") {
						txtRate.setText(spRate);
					} else {						
						txtRate.setText(lstRate);
						txtDiscount.setText(lstDiscount);
						lstFlag = "0";
					}
				}
			}

			public void onNothingSelected(AdapterView<?> parentView) {
				// your code here
			}

		});

		final EditText txtProdQty = (EditText)findViewById(R.id.txtProdQty);
		final EditText txtInvProdRate = (EditText)findViewById(R.id.txtInvProdRate);
		final EditText txtProdAmount = (EditText)findViewById(R.id.txtProdAmount);
		final EditText txtProdDiscount = (EditText)findViewById(R.id.txtProdDiscount);


		ListView lv = (ListView) findViewById(R.id.listview);
		lv.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}
		});




	    txtProdQty.addTextChangedListener(new TextWatcher(){
	    	int timer = 0;
	        public void afterTextChanged(Editable s) {
	            Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                    	if(timer != 0){
							sayCommand(tts, txtProdQty.getText().toString());
						}
                    	timer = 0;
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(runnable, 2500);
				timer = 1;
	          //implement code
	        	//Toast.makeText(	getApplicationContext() ,"fired now... " , Toast.LENGTH_SHORT).show();
	        	if(!txtProdQty.getText().toString().contentEquals(""))
	        	{
	        		qty = Double.valueOf(txtProdQty.getText().toString()).doubleValue();
		        	if(!txtInvProdRate.getText().toString().contentEquals(""))
		        	{
			        	Rate=Double.valueOf(txtInvProdRate.getText().toString()).doubleValue() ;
			        	if(!txtProdDiscount.getText().toString().contentEquals(""))
			        	{
			        	discount = Double.valueOf(txtProdDiscount.getText().toString()).doubleValue();
			        	}
			        	else
			        		discount = 0.0;
			        	discount = (qty*Rate)* (discount/100);
				        discount = (qty*Rate) - (discount) ;
				        DecimalFormat df = new DecimalFormat("###.##");
			        	txtProdAmount.setText( df.format(discount)+ "") ;
//			        	Toast.makeText(getApplicationContext(),"welcome", Toast.LENGTH_SHORT).show();

		        	}
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){timer=0;}
	        public void onTextChanged(CharSequence s, int start, int before, int count){
				timer = 0;
	        }
	    });
		  
	    txtInvProdRate.addTextChangedListener(new TextWatcher(){
			int timer = 0;
	        public void afterTextChanged(Editable s) {
	          //implement code
				timer = 1;

				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if(timer !=0){
							sayCommand(tts, txtInvProdRate.getText().toString());
						}
						timer = 0;

					}
				};
	        	Handler handler = new Handler();
	        	handler.postDelayed(runnable, 2500);
	        	if(!txtProdQty.getText().toString().contentEquals(""))
	        	{
	        		qty = Double.valueOf(txtProdQty.getText().toString()).doubleValue();
		        	if(!txtInvProdRate.getText().toString().contentEquals(""))
		        	{
		        		Rate=Double.valueOf(txtInvProdRate.getText().toString()).doubleValue() ;
			        	if(!txtProdDiscount.getText().toString().contentEquals(""))
			        	{
			        	discount = Double.valueOf(txtProdDiscount.getText().toString()).doubleValue();
			        	}
			        	else
			        		discount = 0.0;
			        	discount = (qty*Rate)* (discount/100);			        	
				        discount = (qty*Rate) -(discount) ;
				        DecimalFormat df = new DecimalFormat("###.##");				        
			        	txtProdAmount.setText( df.format(discount)+ "") ;
//						sayCommand(tts, txtInvProdRate.getText().toString());
		        	}
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){timer = 0;}
	        public void onTextChanged(CharSequence s, int start, int before, int count){timer = 0;}
	    }); 
	    
	    txtProdDiscount.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	          //implement code
	        	
	        	if(!txtProdQty.getText().toString().contentEquals(""))
	        	{
	        		qty = Double.valueOf(txtProdQty.getText().toString()).doubleValue();
		        	if(!txtInvProdRate.getText().toString().contentEquals(""))
		        	{
		        		Rate=Double.valueOf(txtInvProdRate.getText().toString()).doubleValue() ;
			        	if(!txtProdDiscount.getText().toString().contentEquals(""))
			        	{
			        	discount = Double.valueOf(txtProdDiscount.getText().toString()).doubleValue();
			        	}
			        	else
			        		discount = 0.0;
			        	discount = (qty*Rate)* (discount/100);			        	
				        discount = (qty*Rate) -(discount) ;		
				        DecimalFormat df = new DecimalFormat("###.##");
				        
			        	txtProdAmount.setText( df.format(discount)+ "") ;	        	
		        	}
	        	}
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 


	    
	    AutoDesc.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable s) {
	          //implement code
	        	//mCursorAdapter.runQueryOnBackgroundThread(s);
	        	
	        	//Toast.makeText(	getApplicationContext() ,"filtered... " , Toast.LENGTH_SHORT).show();
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
	
	   
	    //final Spinner spss = (Spinner) findViewById(R.id.spinvEditProductSelect);
	    EditText txtProdDisc = (EditText)findViewById(R.id.txtProdDiscount);
	     final Button btnAdd2 =  ((Button) findViewById(R.id.btnInvProdAdd));
	    txtInvProdRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {           
                // TODO Auto-generated method stub
            	if(!hasFocus)
            	{
            		//Toast.makeText(	getApplicationContext() ,"focus changed " , Toast.LENGTH_SHORT).show();
            		btnAdd2.performClick();
            		//txtProdQty.requestFocus();
            		//((Button) findViewById(R.id.btnInvProdAdd)).performClick();            		
            	}
            	//EditText txtProdDisc = (EditText)findViewById(R.id.txtProdDiscount);
            	//txtProdDisc.requestFocus();    
            }
	    });

		btn_add_items = (Button)findViewById(R.id.add_items);
		btn_add_items.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				linear_item_bar = (LinearLayout)findViewById(R.id.item_bar);
				linear_item_bar2 = (LinearLayout)findViewById(R.id.item_bar2);
				linear_item_bar.setVisibility(View.VISIBLE);
				linear_item_bar2.setVisibility(View.VISIBLE);
				btn_add_items.setVisibility(View.GONE);
			}
		});

	    final Button btn_share = (Button)findViewById(R.id.btnShare);
		final Context context=this;
	    btn_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            	final String pdff="samplepdf.pdf";
				try
				{

					// creating file
//					File file =new File("/storage/emulated/0/Android/data/fotl.vyaapar.acq/files/"+filename);
//					file.createNewFile();
//					FileOutputStream fout =new FileOutputStream(file);
//					OutputStreamWriter myOutWriter = new OutputStreamWriter(fout);
//					myOutWriter.write(msg);
//					myOutWriter.close();
//					fout.close();

					//Creating pdf
					Document document=new Document();
					String Fpath="/storage/emulated/0/Android/data/fotl.vyaapar.acq/files/"+pdff;
					File pdfFile=new File(Fpath);
					PdfWriter.getInstance(document,new FileOutputStream(Fpath));
					document.open();
					document.add(createFirstTable());
					document.close();
                    Toast.makeText(getApplicationContext(), "File saved Successfully at path "+Fpath, Toast.LENGTH_SHORT).show();

					//To share text file

//					Intent sendIntent = new Intent(Intent.ACTION_SEND);
//					sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//					sendIntent.setType("text/*");
//					Uri uri=FileProvider.getUriForFile(InvoiceCreation.this,"fotl.vyaapar.acq.provider",file);
//					//Uri.parse("file://"+file.getAbsolutePath())
//					sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
//					startActivity(Intent.createChooser( sendIntent,"share the file with"));

					//to share pdf file
					Intent sendIntent = new Intent(Intent.ACTION_SEND);
					sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					sendIntent.setType("application/pdf");
					Uri uri=FileProvider.getUriForFile(InvoiceCreation.this,"fotl.vyaapar.acq.provider",pdfFile);
					sendIntent.putExtra(Intent.EXTRA_STREAM,uri);
					startActivity(Intent.createChooser( sendIntent,"share the file with"));
					d.closeConnection();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					Toast.makeText(getApplicationContext(),"share failed ,the error message is : "+e.getMessage(),Toast.LENGTH_LONG).show();
				}


               /* String msg = "Invoice number: "+txtInvoiceNumber.getText().toString()+"\n Invoice Date" + txtInvoiceDate.getText().toString()+"\n Invoice type : "+Invoicetype.getSelectedItem().toString() + "\n Customer : " + Customer.getSelectedItem().toString() +"\n Invoice TIN  : "+tin.getText().toString() + "\n description : " +des.getText().toString()+ " \n Sub Total : "+subtotal.getText().toString()  + "\n Discount : "+txtInvDisc.getText().toString()+"\n Shipping charger : "+ shipping.getText().toString() +"\n Sales tax : "+ salestax.getText().toString()+"\n Packing chargers : "+packcharge.getText().toString()+ " \n Total Amt  : "+txtInvTotal.getText().toString()+"\n Total Amount Received : "+amtreceived.getText().toString()+"\n Total Balance :"+ balance.getText().toString();
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, msg.toString());
				sendIntent.setType("text/plain");
//				sendIntent.setType("text/csv");
				startActivity(sendIntent);
				*/

			}
        });

	    
	    final Button btnAdd = (Button)findViewById(R.id.btnInvProdAdd);
	    btnAdd.setOnClickListener(new OnClickListener(){
	    public void onClick(View arg0) {						
			try
			{
				//Toast.makeText(	getApplicationContext() ,"focus adding " , Toast.LENGTH_SHORT).show();
			EditText txtProdCode = (EditText) findViewById(R.id.txtInvProdDesc);
			EditText txtQuantity = (EditText) findViewById(R.id.txtProdQty);
			EditText txtRate = (EditText) findViewById(R.id.txtInvProdRate);
			EditText txtDiscount = (EditText) findViewById(R.id.txtProdDiscount);
			lstInvId = txtInvoiceNumber.getText().toString();
			String Empty="";
			String EmptyInit="Select One";
			if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
			{
				//Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
				return;
			}
			if(txtProdQty.getText().toString().contentEquals(""))
			{
				txtProdQty.setText("");
				return;
			}
			if(txtInvProdRate.getText().toString().contentEquals(""))
			{
				txtInvProdRate.setText("");
				return;
			}
			if(txtDiscount.getText().toString().contentEquals(""))
			{
				txtDiscount.setText("");
				return;
			}
			ContentValues cc = new ContentValues();		
			cc.put("ProductCode", lstProdcode);
			cc.put("ProductDesc", txtProdCode.getText().toString());
			cc.put("Quantity", txtQuantity.getText().toString());
			cc.put("Rate", txtRate.getText().toString());
			cc.put("Discount", txtDiscount.getText().toString());
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			Cursor cProduct = d.getProdcutDetails(lstInvId);
			String ProdcutCode = txtProdCode.getText().toString();
			String Quantity = txtQuantity.getText().toString();
			String Rate = txtRate.getText().toString();
			String Discount = txtDiscount.getText().toString();
			d.closeConnection();
			if( Discount =="")
				Discount = "0";
			ContentValues CCContent = new ContentValues();
			CCContent.put("InvoiceId", lstInvId);			
			CCContent.put("ProductCode", lstProdcode );
			CCContent.put("ProductDesc", txtProdCode.getText().toString());
			CCContent.put("Quantity", Quantity);
			CCContent.put("Rate", Rate);
			CCContent.put("Discount", Discount);
			d.InsertInvoiceDetail(CCContent);				
			Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(CInvDetail);
			d.closeConnection();
			//txtQuantity.requestFocus();
			//txtDiscount.setText("0");
			//txtProdQty.setText("0");
			}
			catch (Exception e) {
				// TODO: handle exception
			}
	    }
		});
	    
	    final Button btnProdDelete = (Button)findViewById(R.id.btnInvProdDelete);
	    btnProdDelete.setOnClickListener(new OnClickListener(){
	    public void onClick(View arg0) {						
			try
			{
			EditText txtProdCode = (EditText) findViewById(R.id.txtInvProdDesc);
			EditText txtQuantity = (EditText) findViewById(R.id.txtProdQty);
			EditText txtRate = (EditText) findViewById(R.id.txtInvProdRate);
			EditText txtDiscount = (EditText) findViewById(R.id.txtProdDiscount);
				
		
			String Empty="";
			String EmptyInit="Select One";
			if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
			{
				Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
				return;
			}
			
			ContentValues cc = new ContentValues();
		
			cc.put("ProductCode", lstProdcode);
			cc.put("ProductDesc", txtProdCode.getText().toString());
			cc.put("Quantity", txtQuantity.getText().toString());
			cc.put("Rate", txtRate.getText().toString());
			cc.put("Discount", txtDiscount.getText().toString());
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			d.DeleteInvoiceDetail(cc, "InvoiceId = " + lstInvId + " And InvDetailId= " + lstInvDetId);
			Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(CInvDetail);
			d.closeConnection();
			txtDiscount.setText("0");
			txtQuantity.setText("0");
			
			}
			catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(	getApplicationContext() ,"Deleted Product... " , Toast.LENGTH_SHORT).show();
			}
	    }
		});
	    
	    
	    final Button btnEdit = (Button)findViewById(R.id.btnInvProdEdit);
	    btnEdit.setOnClickListener(new OnClickListener(){
	    public void onClick(View arg0) {						
			try
			{
			EditText txtProdCode = (EditText) findViewById(R.id.txtInvProdDesc);
			EditText txtQuantity = (EditText) findViewById(R.id.txtProdQty);
			EditText txtRate = (EditText) findViewById(R.id.txtInvProdRate);
			EditText txtDiscount = (EditText) findViewById(R.id.txtProdDiscount);
				
		
			String Empty="";
			String EmptyInit="Select One";
			if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
			{
				Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
				return;
			}
			
			ContentValues cc = new ContentValues();
		
			cc.put("ProductCode", lstProdcode);
			cc.put("ProductDesc", txtProdCode.getText().toString());
			cc.put("Quantity", txtQuantity.getText().toString());
			cc.put("Rate", txtRate.getText().toString());
			cc.put("Discount", txtDiscount.getText().toString());
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			d.UpdateInvoiceDetail(cc, "InvoiceId = " + lstInvId + " And InvDetailId= " + lstInvDetId);
			Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(CInvDetail);
			d.closeConnection();
			txtDiscount.setText("0");
			txtQuantity.setText("0");
			
			}
			catch (Exception e) {
				// TODO: handle exception
			}
	    }
		});
	    
	    
		final ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeScreen);
		 btnHome.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{	
	        		startActivity(new Intent(InvoiceCreation.this, MenuForm.class));	        		
	        		//finish();
	        	}
	        });
		
		 
//		 final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
//		 btnMnuInvoice.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		startActivity(new Intent(InvoiceCreation.this, InvoiceCreation.class));
//	        		//finish();
//	        	}
//	        });
		 
//		 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
//		 btnMnuMasters.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		startActivity(new Intent(InvoiceCreation.this, Masters.class));
//	        		//finish();
//	        	}
//	        });
		 
//		 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
//		 btnMnuLists.setOnClickListener(new OnClickListener(){
//	        	public void onClick(View v)
//	        	{
//	        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
//	        		startActivity(new Intent(InvoiceCreation.this, Folders.class));
//	        		//finish();
//	        	}
//	        });
		 
		final Button  btnSubmit = (Button) findViewById(R.id.btnSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
				((Button) findViewById(R.id.btnInvoiceSave)).performClick();
				createAttachment();
				EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);
				//startActivity(new Intent(InvoiceCreation.this, Email.class));
				 Intent sendIntent = new Intent(Intent.ACTION_SEND); 
				 Spinner spCustomer = (Spinner) findViewById(R.id.spInvoiceCustomer);

				 String CE = "";

                 sendIntent.putExtra(Intent.EXTRA_SUBJECT, "INVOICE DETAILS FROM VYAAPAR"  );
                 try
    				{
    					DbVyaapar d = new DbVyaapar(getApplicationContext());
    					Cursor cEmail= d.getCustomerEmailId(lstInvId.toString());
    					 //Toast.makeText(getApplicationContext(), "before CE over"+CE, Toast.LENGTH_SHORT).show();
    					if (cEmail != null) {    						
    						if (cEmail.moveToFirst()) {
    		    			do { 
    		    				CE = cEmail.getString(cEmail.getColumnIndex("CEmail"));
    		    				}while (cEmail.moveToNext());
    						}cEmail.close();
 						
    					}
 					}
    				catch (Exception e) {
						// TODO: handle exception
    					Toast.makeText(getApplicationContext(), "CE Error"+ e.getMessage().toString(), Toast.LENGTH_SHORT).show();
					}
                 sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{CE  });
                 sendIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi Please find the attachment for the invoice" + txtInvoiceNumber.getText() + " Details From Vyaapar.  ");
                 
                 sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse               		 
                 ("file://"+Environment.getExternalStorageDirectory()+"/Invoice"+ txtInvoiceNumber.getText().toString() +".pdf") );
                 Toast.makeText(getApplicationContext(), "file://"+Environment.getExternalStorageDirectory()+"/Invoice"+ txtInvoiceNumber.getText().toString() +".pdf", Toast.LENGTH_LONG).show();
                 sendIntent.setType("text/PDF");
                 startActivity(new Intent(InvoiceCreation.this, MenuForm2.class));
                 startActivity(sendIntent);
                 
                
                // startActivity(new Intent(InvoiceCreation.this, Email.class));
			}
		});
		 
		
		/*For the Menu items*/
		
		/* End for the Menu Items */
		
		
		
		ContentValues CCContent = new ContentValues();
		CCContent.put("InvoiceType", "Order Estimate Not Executed ");
		CCContent.put("CustomerId", 1);
		CCContent.put("InvoiceDate",DateFormat.getDateInstance().format(new Date()));
		CCContent.put("Sales", ITSales);
		CCContent.put("PkgCharges", ITpkg);
		CCContent.put("Shipping", ITShipping);
		CCContent.put("TotalAmount",ITotal );
		CCContent.put("AmountReceived", ITamt);
		CCContent.put("Balance",  (ITotal-ITamt));
		CCContent.put("Terms", "");
		CCContent.put("TIN", "");
		CCContent.put("Status", "Draft");
		
		//EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvNo);
		EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);
		txtInvoiceDate = (EditText) findViewById(R.id.txtInvoiceDate);
		//EditText txtInvNo = (EditText) findViewById(R.id.txtInvNo);
		
		Bundle b=getIntent().getExtras();
		String value = "";
		if(b != null)
        value=b.getString("Key1");	
		
        if(value.trim() == "")
        {
        	String detailNo ="";
        	String MainNo = "0";
        	
        	try
        	{ 
        		//Toast.makeText(	getApplicationContext() ,"came here... " , Toast.LENGTH_SHORT).show();		
        	Cursor CDInv = d.getInvoiceDetailsNumber();
        	//Toast.makeText(	getApplicationContext() ,"got data... " , Toast.LENGTH_SHORT).show();
        	if (CDInv != null) {
        		//Toast.makeText(	getApplicationContext() ,"not null... " , Toast.LENGTH_SHORT).show();
    			if (CDInv.moveToFirst()) {
    				do {
    					detailNo = CDInv.getString(CDInv.getColumnIndex("_id"));
    					//Toast.makeText(	getApplicationContext() ,"not null... " + detailNo, Toast.LENGTH_SHORT).show();
    					if(detailNo.trim() == "0")
    					{
    						//Toast.makeText(	getApplicationContext() ,"creating not null... " + detailNo, Toast.LENGTH_SHORT).show();
    						d.CreateInvoice(CCContent);
    		        		detailNo="0";    						
    					}
    				}while(CDInv.moveToNext());
    				CDInv.close();
    				}
        	}
        	else
        	{
        		d.CreateInvoice(CCContent);
        		detailNo="0";
        	}
        	}catch (Exception e) {
        		//Toast.makeText(	getApplicationContext() ,"came here.into exception.. " , Toast.LENGTH_SHORT).show();
        		//d.CreateInvoice(CCContent);
			}
        	
        	try
        	{
        	Cursor CIn= d.getInvoiceNumber();
        	if (CIn != null) {
    			if (CIn.moveToFirst()) {
    				do {
    					MainNo = CIn.getString(CIn.getColumnIndex("_id"));    				
    				}while(CIn.moveToNext());
    				CIn.close();
    				}
        	}
	        	if(MainNo.contentEquals(detailNo))
	    		{
	    			d.CreateInvoice(CCContent);    			
	    		}
        	}
        	catch (Exception e) {
				// TODO: handle exception
			}
        	Cursor CInv=null;
    		
    		try
    		{
    		CInv= d.getInvoiceNumber();
        	if (CInv != null) {
    			if (CInv.moveToFirst()) {
    				do {
    					//txtInvNo.setText(CInv.getString(CInv.getColumnIndex("_id")));    					
    					EditText txtSalesTax = (EditText)findViewById(R.id.edInvSalesTax);
    					EditText txtPkgChrg = (EditText)findViewById(R.id.edInvPkChrg);
    					TextView txtShipping  = (TextView)findViewById(R.id.txtInvShipping);
    					TextView txtBalance  = (TextView)findViewById(R.id.txtInvBalance);
    					EditText txtAmntRx = (EditText)findViewById(R.id.edInvAmtReceived);
    					
    					txtInvoiceNumber.setText(CInv.getString(CInv.getColumnIndex("_id")));
    					txtInvoiceDate.setText(CInv.getString(CInv.getColumnIndex("InvoiceDate")));
    					String InvoiceType = CInv.getString(CInv.getColumnIndex("InvoiceType"));
    					String Customer = CInv.getString(CInv.getColumnIndex("CustomerId"));
    					
    					txtSalesTax.setText(CInv.getString(CInv.getColumnIndex("Sales")));
    					txtPkgChrg.setText(CInv.getString(CInv.getColumnIndex("PkgCharges")));
    					txtShipping.setText(CInv.getString(CInv.getColumnIndex("Shipping")));
    					txtAmntRx.setText(CInv.getString(CInv.getColumnIndex("AmountReceived")));
    					txtBalance.setText(CInv.getString(CInv.getColumnIndex("Balance")));    					
    			
    				} while (CInv.moveToNext());
    			}CInv.close();
        	}
    		}
    		catch (Exception e) {
				// TODO: handle exception
			}
    		
        }
        else
        {
        	try
        	{
        		int invno = Integer.parseInt(value);
        		txtInvoiceNumber.setText(value);
        	}
        	catch (Exception e) {
				// TODO: handle exception
        		
			}
        //txtInvoiceNumber.setText(value);
        }
		ListView lview = (ListView) findViewById(R.id.listview);
		lview.setOnItemClickListener(new ListView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> a, View v, int position,long id) {
				TextView tv1 = (TextView) v.findViewById(R.id.SecondText);
				TextView tv2 = (TextView) v.findViewById(R.id.ThirdText);
				
				TextView tv3 = (TextView) v.findViewById(R.id.FourthText);
				TextView tv4 = (TextView) v.findViewById(R.id.FifthText);
				TextView tv0 = (TextView) v.findViewById(R.id.ZeroText);
				TextView Invoice = (TextView) findViewById(R.id.txtInvoiceNumber);
				ImageView tImageView = (ImageView) v.findViewById(R.id.imageView1);

				//tImageView.setImageBitmap(getImageFromBLOB(cc.getBlob(cc.getColumnIndex("image"))));
				tImageView.setImageBitmap(getImageFromBLOB(getImage(tv1.getText().toString()) ));
				lstInvDetId = tv0.getText().toString();
				lstProdcode = tv1.getText().toString();
				lstQuantity = tv3.getText().toString();//
				lstRate = tv2.getText().toString();
				lstDiscount =  tv4.getText().toString();
				lstFlag = "1";
				lstInvId = Invoice.getText().toString();
				
			
				
				txtProdDiscount.setText(lstDiscount+"");
				txtProdQty.setText(lstQuantity+"");
				txtInvProdRate.setText(lstRate+"");
				final Spinner sps = (Spinner) findViewById(R.id.spinvEditProductSelect);				
				if (lstFlag != "0") {			
					for (int i = 0; i < sps.getCount(); i++) {
						{
							Cursor cc2 = (Cursor) (sps.getItemAtPosition(i));
							String SpnVal = cc2.getString(cc2.getColumnIndex("PCode"));
							if (lstProdcode.equals(SpnVal)) {
								sps.setSelection(i);
							}
						}
					}

				}
				//d.closeConnection();
				/*if(lstInvDetId == "")
					showDialog(AddItems_Dialog_id);	 //Toast.makeText(getApplicationContext()," Please Add Items to the Invoice using AddItems " ,Toast.LENGTH_SHORT).show();
				else
				showDialog(Product_Dialog_id);*/
				
			}
		});
		 
		Cursor cInvDetails = d.getInvoiceDetails(txtInvoiceNumber.getText().toString());
		GetInvoiceDetails(cInvDetails);
		d.closeConnection();
		
		/*
		ListView lviewm = (ListView) findViewById(R.id.listview);
		TextView tv1 = (TextView) lviewm.findViewById(R.id.SecondText);
		tv1.setVisibility(lviewm.INVISIBLE);
		*/
		
		final DbVyaapar dv = new DbVyaapar(getApplicationContext());
		al_Customers = dv.getCustomer();
		Cursor customerd = dv.getCustomerd();
		ArrayList<String> marraylist=new ArrayList<String>();

		if (customerd !=null){
			if(customerd.moveToNext())
			{

				do{
					String data=customerd.getString(customerd.getColumnIndex("CName"));
					marraylist.add(data);

				}while(customerd.moveToNext());
			}
		}

		Spinner spinner = (Spinner) findViewById(R.id.spInvoiceType);
		ArrayAdapter<CharSequence> invadapter = ArrayAdapter.createFromResource(this, R.array.InvoiceTypeArray,android.R.layout.simple_spinner_item);
		invadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(invadapter);

		Spinner spnState = (Spinner) findViewById(R.id.spInvoiceCustomer);
		String[] columns = new String[] { "CName" };
		int[] to = new int[] {android.R.id.text1 };
		ArrayAdapter cusadapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,marraylist);
		cusadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnState.setAdapter(cusadapter);


		Cursor cInvoice = d.getInvoiceMasterDetails(txtInvoiceNumber.getText().toString());
		if (cInvoice != null) {
			if (cInvoice.moveToFirst()) {
				do {
					EditText txtSalesTax = (EditText)findViewById(R.id.edInvSalesTax);
					EditText txtPkgChrg = (EditText)findViewById(R.id.edInvPkChrg);
					TextView txtShipping  = (TextView)findViewById(R.id.txtInvShipping);
					TextView txtBalance  = (TextView)findViewById(R.id.txtInvBalance);
					EditText txtAmntRx = (EditText)findViewById(R.id.edInvAmtReceived);

					txtInvoiceNumber.setText(cInvoice.getString(cInvoice.getColumnIndex("_id")));
					txtInvoiceDate.setText(cInvoice.getString(cInvoice.getColumnIndex("InvoiceDate")));
					String InvoiceType = cInvoice.getString(cInvoice.getColumnIndex("InvoiceType"));
					String Customer = cInvoice.getString(cInvoice.getColumnIndex("CustomerId"));
					
					txtSalesTax.setText(cInvoice.getString(cInvoice.getColumnIndex("Sales")));
					txtPkgChrg.setText(cInvoice.getString(cInvoice.getColumnIndex("PkgCharges")));
					txtShipping.setText(cInvoice.getString(cInvoice.getColumnIndex("Shipping")));
					txtAmntRx.setText(cInvoice.getString(cInvoice.getColumnIndex("AmountReceived")));
					txtBalance.setText(cInvoice.getString(cInvoice.getColumnIndex("Balance")));
					
					ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); // cast to ArrayAdapter
					int spinnerPosition = myAdap.getPosition(InvoiceType);
					spinner.setSelection(spinnerPosition);

					for (int i = 0; i < cusadapter.getCount(); i++) {
						{
							//Toast.makeText(getApplicationContext()," setting cname" ,Toast.LENGTH_SHORT).show();
							//Cursor cc2 = (Cursor) (spnState.getItemAtPosition(i));
							//String SpnVal = cc2.getString(cc2.getColumnIndex("CName"));

							if (spnState.getItemAtPosition(i).equals(Customer)) {
								//Toast.makeText(getApplicationContext()," sudha setting cname"+SpnVal ,Toast.LENGTH_SHORT).show();
								spnState.setSelection(i);
							}
						}
					}
					
					//int spinnerCustomerPosition = myCustomer.getPosition(Customer);
					//spnState.setSelection(spinnerCustomerPosition);

				} while (cInvoice.moveToNext());
			}cInvoice.close();
		}		
		d.closeConnection();
		
		
			final EditText txtSales = (EditText)findViewById(R.id.edInvSalesTax);
		    txtSales.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		          //implement code
		        	
		        	if(!txtSales.getText().toString().contentEquals(""))
		        	{
		        	ITSales = Double.valueOf(txtSales.getText().toString()).doubleValue();
		        	SetTotals();
		        	}
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
		    }); 
		
		    final EditText txtPacking = (EditText)findViewById(R.id.edInvPkChrg);
		    txtPacking.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		          //implement code
		        	if(!txtPacking.getText().toString().contentEquals(""))
		        	{
		        	ITpkg= Double.valueOf(txtPacking.getText().toString()).doubleValue();
		        	SetTotals();	
		        	}
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
		    }); 
		    final EditText txtAmountReceived = (EditText)findViewById(R.id.edInvAmtReceived);
		    txtAmountReceived.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		          //implement code
		        	if(!txtAmountReceived.getText().toString().contentEquals(""))
		        	{
		        	ITamt= Double.valueOf(txtAmountReceived.getText().toString()).doubleValue();
		        	SetTotals();	
		        	}
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
		    });
		    
		    final EditText txtInvDisc = (EditText)findViewById(R.id.txtInvDisc);
		    txtInvDisc.addTextChangedListener(new TextWatcher(){
		        public void afterTextChanged(Editable s) {
		          //implement code
		        	if(!txtInvDisc.getText().toString().contentEquals(""))
		        	{
		        	//ITamt= Double.valueOf(txtAmountReceived.getText().toString()).doubleValue();
		        	SetTotals();	
		        	}
		        }
		        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
		        public void onTextChanged(CharSequence s, int start, int before, int count){}
		    }); 

		((Button) findViewById(R.id.btnInvoiceSave))
				.setOnClickListener(new OnClickListener() {

					public void onClick(View arg0) {
						// in onCreate or any event where your want the user to
						// select a file
						DbVyaapar d = new DbVyaapar(getApplicationContext());						

						EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);
						EditText txtInvoiceDate = (EditText) findViewById(R.id.txtInvoiceDate);
						Spinner spCustomer = (Spinner) findViewById(R.id.spInvoiceCustomer);
						Spinner spInvoiceType = (Spinner) findViewById(R.id.spInvoiceType);
						//String Customer = spCustomer.getSelectedItem().toString();

						//Cursor cc = (Cursor)(spCustomer.getSelectedItem());
//						if (cc != null) {
//							Customer = cc.getString(cc.getColumnIndex("CName"));
//						}

						
						//Toast.makeText(getApplicationContext(),"fotl Customer Id is " + Customer ,Toast.LENGTH_LONG).show();
						ContentValues CCContentValues = new ContentValues();
						CCContentValues.put("InvoiceType", spInvoiceType.getSelectedItem().toString());
						CCContentValues.put("CustomerId", spCustomer.getSelectedItem().toString());
						CCContentValues.put("InvoiceDate", txtInvoiceDate.getText().toString());
						CCContentValues.put("Sales", ITSales);
						CCContentValues.put("PkgCharges", ITpkg);
						CCContentValues.put("Shipping", ITShipping);
						CCContentValues.put("TotalAmount",ITotal );
						CCContentValues.put("AmountReceived", ITamt);
						CCContentValues.put("Balance",  (ITotal-ITamt));
						CCContentValues.put("Terms", "");
						CCContentValues.put("TIN", "");
						CCContentValues.put("Status", "Draft");
						
						String invno = txtInvoiceNumber.getText().toString();
						int flag = d.UpdateInvoiceMaster(CCContentValues, invno);
						Toast.makeText(getApplicationContext(),"Details Saved",Toast.LENGTH_LONG).show();

						dv.closeConnection();
					}
				});

		((EditText) findViewById(R.id.txtInvoiceDate)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		((Button) findViewById(R.id.btnInvoiceDate))
				.setOnClickListener(new OnClickListener() {
					public void onClick(View arg0) {
						// in onCreate or any event where your want the user to
						// select a file
						showDialog(DATE_DIALOG_ID);
					}
				});

		// get the current date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// display the current date (this method is below)
		updateDisplay();

		final Button btnInvAddItems = (Button) findViewById(R.id.btnInvAddItems);
		btnInvAddItems.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Context context = getApplicationContext();
				ListView lview = (ListView) findViewById(R.id.listview);
				//Toast.makeText(context,"Item place to add: " + lview.getSelectedItemId(),Toast.LENGTH_LONG).show();
				Intent i = new Intent(context, FProductList.class);
				startActivityForResult(i, 10);
				// setContentView(R.layout.productlist); // Knowledge

				/*
				 * if(b.getString("ProID")!= null) value=b.getString("ProID");
				 */
				// Toast.makeText(context, "Item Clicked: " + value ,
				// Toast.LENGTH_LONG).show();
			}
		});

		final Button btnShippingDetails = (Button) findViewById(R.id.btnShipCharges);
		btnShippingDetails.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(),"showing shipping details",Toast.LENGTH_SHORT).show();
				showDialog(Shipping_Dialog_id);
			}
		});
	}

    private PdfPTable createFirstTable() {

			EditText txtInvDisc = (EditText) findViewById(R.id.txtInvDisc);
			TextView txtInvTotal= (TextView) findViewById(R.id.txtInvTotal);
			EditText des =(EditText)findViewById(R.id.autotxtInvProdDesc);
			EditText tin=(EditText)findViewById(R.id.txtInvoiceTIN);
			TextView subtotal= (TextView) findViewById(R.id.txtInvoiceSubTotal);
			TextView shipping= (TextView) findViewById(R.id.txtInvShipping);
			TextView salestax= (TextView) findViewById(R.id.edInvSalesTax);
			TextView packcharge= (TextView) findViewById(R.id.edInvPkChrg);
			TextView amtreceived= (TextView) findViewById(R.id.edInvAmtReceived);
			TextView balance= (TextView) findViewById(R.id.txtInvBalance);
			Spinner Invoicetype =(Spinner)findViewById(R.id.spInvoiceType);
			Spinner Customer =(Spinner)findViewById(R.id.spInvoiceCustomer);

            PdfPTable table = new PdfPTable(2);
            PdfPCell cell;
            // we add a cell with colspan 2
            cell = new PdfPCell(new Phrase("                                    Invoice Details                                    "));
            cell.setColspan(2);
            table.addCell(cell);
            table.addCell(" Invoice number ");table.addCell(txtInvoiceNumber.getText().toString());
            table.addCell(" Invoice Date ");table.addCell(txtInvoiceDate.getText().toString());
			table.addCell(" Invoice type ");table.addCell(Invoicetype.getSelectedItem().toString());
			table.addCell(" Customer ");table.addCell(Customer.getSelectedItem().toString());
			table.addCell(" Invoice TIN   ");table.addCell(tin.getText().toString());
			table.addCell(" description ");table.addCell(des.getText().toString());
			table.addCell(" Sub Total ");table.addCell(subtotal.getText().toString() );
			table.addCell(" Discount  ");table.addCell(txtInvDisc.getText().toString());
			table.addCell(" Shipping charger  ");table.addCell(shipping.getText().toString() );
			table.addCell(" Sales tax  ");table.addCell(salestax.getText().toString());
			table.addCell(" Packing chargers  ");table.addCell(packcharge.getText().toString());
			table.addCell(" Total Amt  ");table.addCell(txtInvTotal.getText().toString());
			table.addCell(" Total Amount Received  ");table.addCell(amtreceived.getText().toString());
			table.addCell(" Total Balance ");table.addCell(balance.getText().toString());
            return table;
        }



    private void sayCommand(TextToSpeech tts, String SpeakText) {
		try {
			if(SpeakText.toString().contains("0.0") || SpeakText.toString().equals("0")){

			}else{
				tts.speak(SpeakText, TextToSpeech.QUEUE_FLUSH, null);
				while (tts.isSpeaking()) {
				}
			}
		}
		catch (Exception er){}

//		if (is_take_input && tts.isSpeaking() == false) {
//			speech.startListening(get_recognized_intent());
//		}
	}

	public void onActivityResult(int reqCode, int resCode, Intent data) {
		/*
		 * String value; Context context= getApplicationContext(); Bundle
		 * b=getIntent().getExtras(); Toast.makeText(context, "Display Over " ,
		 * Toast.LENGTH_LONG).show();
		 */
		String ProductCode = "";
		String[] returnCodes;
		if (resCode == RESULT_OK && reqCode == 10) {
			if (data.hasExtra("returnKey1")) {
				ProductCode = data.getExtras().getString("returnKey1");
				returnCodes = ProductCode.split(",Desc:");
				//Toast.makeText(this,returnCodes[0] + "  fotl  " + returnCodes[1],Toast.LENGTH_SHORT).show();
			}
		}

		//EditText txtInvoiceNo = (EditText) findViewById(R.id.txtInvNo);
		EditText txtInvoiceNo = (EditText) findViewById(R.id.txtInvoiceNumber);
		String Invoiceno = txtInvoiceNo.getText().toString();
		returnCodes = ProductCode.split(",Desc:");
		DbVyaapar d = new DbVyaapar(getApplicationContext());
		Cursor cProduct = d.getProdcutDetails(returnCodes[0]);
		String ProdcutCode = "";
		String Quantity = "0";
		String Rate = "0.0";
		if (cProduct != null) {
			if (cProduct.moveToFirst()) {
				do {
					ProdcutCode = cProduct.getString(cProduct
							.getColumnIndex("_id"));
					Rate = cProduct.getString(cProduct.getColumnIndex("Rate"));
				} while (cProduct.moveToNext());
				cProduct.close();

			}
			//Toast.makeText(this,returnCodes[0] + "  fotl product not null  " + Rate+ returnCodes[1], Toast.LENGTH_SHORT).show();
		} else
			//Toast.makeText(this,returnCodes[0] + "  fotl product null  " + Rate+ returnCodes[0], Toast.LENGTH_SHORT).show();
		d.closeConnection();
		ContentValues CCContent = new ContentValues();
		CCContent.put("InvoiceId", Invoiceno);
		CCContent.put("ProductCode", returnCodes[0]);
		CCContent.put("Quantity", Quantity);
		CCContent.put("Rate", Rate);
		CCContent.put("Discount", 0.0);
		d.InsertInvoiceDetail(CCContent);

		Cursor CInvDetail = d.getInvoiceDetails(Invoiceno);
		GetInvoiceDetails(CInvDetail);
		//Toast.makeText(this,returnCodes[0] + "  fotl NEW DETAILS CAME  " + Rate+ returnCodes[1], Toast.LENGTH_SHORT).show();
		d.closeConnection();

	}

	// updates the date in the TextView
	private void updateDisplay() {
		EditText mDateDisplay = (EditText) findViewById(R.id.txtInvoiceDate);
		mDateDisplay.setText(new StringBuilder()
				// Month is 0 based so add 1
				.append(mMonth + 1).append("-").append(mDay).append("-")
				.append(mYear).append(" "));
	}

	// the callback received when the user "sets" the date in the dialog
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};

	protected Dialog onCreateDialog(int id) {
		// screendialoge = null;
		DbVyaapar d = new DbVyaapar(getApplicationContext());
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,mDay);
		case Product_Dialog_id:			
			screendialoge = new Dialog(this);
			/*WindowManager.LayoutParams params = getWindow().getAttributes();  
			 params.x = -100;  
		        params.height = 370;  
		        params.width = 1000;  
		        params.y = 550;
	        screendialoge.getWindow().setAttributes(params); */ 
			screendialoge.setContentView(R.layout.editinvoiceproduct);
			screendialoge.setTitle("Vyaapar: Invoice Item Edit");
			screendialoge.setCancelable(true);
			
	        
			Button btnInvEditProdCancel = (Button) screendialoge.findViewById(R.id.btnInvEditProdCancel);
			btnInvEditProdCancel.setOnClickListener(btnScreenDialog_OKOnClickListener);
			
			Button btnInvEditProdNext = (Button) screendialoge.findViewById(R.id.btnInvEditProdNext);
			btnInvEditProdNext.setOnClickListener(btnScreenDialog_NextOnClickListener);
			
			Cursor cp = d.getProducts();
			// startManagingCursor(cp);
			
			String[] columns = new String[] { "PCode" };
			int[] to = new int[] { android.R.id.text1 };
			SimpleCursorAdapter sadapter = new SimpleCursorAdapter(screendialoge.getContext(),android.R.layout.simple_dropdown_item_1line, cp, columns,to);
			sadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			final Spinner sps = (Spinner) screendialoge.findViewById(R.id.spinvEditProductSelect);
			if(cp== null)
				Toast.makeText(getApplicationContext()," returned null ",Toast.LENGTH_SHORT).show();
			else
			{
				//Toast.makeText(getApplicationContext(),"cursor is not  null ",Toast.LENGTH_SHORT).show();
			sps.setAdapter(sadapter);
			}

			/*if (lstFlag != "0") {
				//Toast.makeText(getApplicationContext(),"Settingspinner when lstfalg not eql to zero",Toast.LENGTH_SHORT).show();
				for (int i = 0; i < sps.getCount(); i++) {
					{
						Cursor cc2 = (Cursor) (sps.getItemAtPosition(i));
						String SpnVal = cc2.getString(cc2.getColumnIndex("PCode"));
						if (lstProdcode.equals(SpnVal)) {
							sps.setSelection(i);
						}
					}
				}

			}*/
			// lstProdcode;
			
			sps.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
					// your code here
					String spString = "";
					String spDesc = "";
					String spCode = "";
					String spSupplier = "";
					String spRate = "";

					//Toast.makeText(parentView.getContext(),"Getting Data "+ parentView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
					Cursor cc = (Cursor) (sps.getSelectedItem());

					if (cc != null) {
						spString = cc.getString(cc.getColumnIndex("_id"));
						spDesc = cc.getString(cc.getColumnIndex("PDesc"));
						spCode = cc.getString(cc.getColumnIndex("PCode"));
						spSupplier = cc.getString(cc.getColumnIndex("Supplier"));
						spRate = cc.getString(cc.getColumnIndex("Rate"));

						EditText txtProdcutCode = (EditText) screendialoge.findViewById(R.id.txtInvEditProdCode);
						txtProdcutCode.setText(spCode);

						EditText txtProdcutDesc = (EditText) screendialoge.findViewById(R.id.txtInvProdDesc);
						txtProdcutDesc.setText(spDesc);

						EditText txtSuppluer = (EditText) screendialoge.findViewById(R.id.txtInvProdSupplier);
						txtSuppluer.setText(spSupplier);
						//if(!cc.isNull(cc.getColumnIndex("image")))
						{
						ImageView mImageView = (ImageView) screendialoge.findViewById(R.id.pInvImage);
						mImageView.setImageBitmap(getImageFromBLOB(getImage(spCode) ));
						}
						EditText txtRate = (EditText) screendialoge.findViewById(R.id.txtInvProdRate);
						EditText txtDiscount = (EditText) screendialoge.findViewById(R.id.txtProdDiscount);
						if (lstFlag == "0") {
							txtRate.setText(spRate);
						} else {
							//Toast.makeText(parentView.getContext(),"Setting Rate and discount Done "+ spString, Toast.LENGTH_SHORT).show();
							txtRate.setText(lstRate);
							txtDiscount.setText(lstDiscount);
							lstFlag = "0";
						}
						EditText txtQuantity = (EditText) findViewById(R.id.txtProdQty);
						//txtQuantity.requestFocus();
					}
					
					//Toast.makeText(parentView.getContext(),"Getting Done " + spString, Toast.LENGTH_SHORT).show();

				}

				public void onNothingSelected(AdapterView<?> parentView) {
					// your code here
				}

			});
			d.closeConnection();
			return screendialoge;
		case Shipping_Dialog_id:
			Shipdialog = new Dialog(this);
			Shipdialog.setContentView(R.layout.shippingdetails);
			int sflag =0;
			Shipdialog.setTitle("Vyaapar: Invoice Shipping Details");
			Shipdialog.setCancelable(true);
			TextView Invoice = (TextView) findViewById(R.id.txtInvoiceNumber);
			//TextView Invoice = (TextView) findViewById(R.id.txtInvNo);
			lstInvId = Invoice.getText().toString();
			Button btnInvShipSave = (Button) Shipdialog.findViewById(R.id.btnShipSave);
			btnInvShipSave.setOnClickListener(btnShipdialog_OKOnClickListener);			
			Cursor CS = d.getShippingDetails(lstInvId);
			EditText txtShipAmount = (EditText) Shipdialog.findViewById(R.id.txtShipAmount);
			EditText txtShipDate = (EditText) Shipdialog.findViewById(R.id.txtShipDate);
			EditText txtShipVia = (EditText) Shipdialog.findViewById(R.id.txtShipVia);
			EditText txtShipTrack = (EditText) Shipdialog.findViewById(R.id.txtShipTrack);
			EditText txtShipNotes = (EditText) Shipdialog.findViewById(R.id.txtShipNotes);
			if(CS!= null)
			{
				//Toast.makeText(getApplicationContext(),"showing shipping not null",Toast.LENGTH_SHORT).show();
				if (CS.moveToFirst()) {
					do {
						sflag = 1;
						//Toast.makeText(getApplicationContext(),"showing shipping first rec",Toast.LENGTH_SHORT).show();
						txtShipAmount.setText( CS.getString(CS.getColumnIndex("ShipAmount")));
						txtShipDate.setText( CS.getString(CS.getColumnIndex("ShipDate")));
						txtShipVia.setText( CS.getString(CS.getColumnIndex("ShipVia")));
						txtShipTrack.setText( CS.getString(CS.getColumnIndex("Tracking")));
						txtShipNotes.setText( CS.getString(CS.getColumnIndex("ShipNotes")));						
					} while(CS.moveToNext());
				//	Toast.makeText(getApplicationContext(),"showing shipping insert"+ sflag,Toast.LENGTH_SHORT).show();					
					}				
				CS.close();
				d.closeConnection();				
			}
			if(sflag==0)
			{
			//Toast.makeText(getApplicationContext(),"showing shipping insert",Toast.LENGTH_SHORT).show();
			ContentValues ccS = new ContentValues();			
				ccS.put("InvoiceId", lstInvId);
				ccS.put("ShipAmount", 0.0);
				ccS.put("ShipDate", txtShipDate.getText().toString());
				ccS.put("ShipVia", "");
				ccS.put("Tracking", "");
				ccS.put("ShipNotes", "");							
				d.InsertShipping(ccS);
				d.closeConnection();
			}
			return Shipdialog;
		case AddItems_Dialog_id:
			/*AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Vyaaper Alert...");
			alertDialog.setMessage("Please add Items with Add Items Button");
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
			}});
			alertDialog.show();
			return alertDialog;*/
			
			AddItemsdialoge = new Dialog(this);
			AddItemsdialoge.setContentView(R.layout.editinvoiceproduct);
			AddItemsdialoge.setTitle("Vyaapar: Invoice Item Add");
			AddItemsdialoge.setCancelable(true);
			/*WindowManager.LayoutParams params2 = getWindow().getAttributes();  
			 params2.x = -100;  
		        params2.height = 370;  
		        params2.width = 1000;  
		        params2.y = 550;
	        AddItemsdialoge.getWindow().setAttributes(params2);  */
			Button btnInvEditProdNext2 = (Button) AddItemsdialoge.findViewById(R.id.btnInvEditProdNext);
			btnInvEditProdNext2.setOnClickListener(AddItemsdialoge_NextOnClickListener);
			
			
			Button btnInvAddProdCancel1 = (Button) AddItemsdialoge.findViewById(R.id.btnInvEditProdCancel);
			btnInvAddProdCancel1.setOnClickListener(btnAddItemsdialoge_OKOnClickListener1);
			Cursor crp = d.getProducts();
			String[] cols = new String[] { "PCode" };
			int[] to1 = new int[] { android.R.id.text1 };
			SimpleCursorAdapter sadapter1 = new SimpleCursorAdapter(AddItemsdialoge.getContext(),android.R.layout.simple_dropdown_item_1line, crp, cols,to1);
			sadapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			final Spinner sps1 = (Spinner) AddItemsdialoge.findViewById(R.id.spinvEditProductSelect);
			if(crp== null)
				Toast.makeText(getApplicationContext(),"cursor returned null ",Toast.LENGTH_SHORT).show();
			else
			{
				//Toast.makeText(getApplicationContext(),"cursor is not  null ",Toast.LENGTH_SHORT).show();
			sps1.setAdapter(sadapter1);
			}

			/*if (lstFlag != "0") {
				//Toast.makeText(getApplicationContext(),"Settingspinner when lstfalg not eql to zero",Toast.LENGTH_SHORT).show();
				for (int i = 0; i < sps.getCount(); i++) {
					{
						Cursor cc2 = (Cursor) (sps.getItemAtPosition(i));
						String SpnVal = cc2.getString(cc2.getColumnIndex("PCode"));
						if (lstProdcode.equals(SpnVal)) {
							sps.setSelection(i);
						}
					}
				}

			}*/
			// lstProdcode;
			
			sps1.setOnItemSelectedListener(new OnItemSelectedListener() {

				public void onItemSelected(AdapterView<?> parentView,View selectedItemView, int position, long id) {
					// your code here
					String spString = "";
					String spDesc = "";
					String spCode = "";
					String spSupplier = "";
					String spRate = "";

					//Toast.makeText(parentView.getContext(),"Getting Data "+ parentView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
					Cursor cc = (Cursor) (sps1.getSelectedItem());

					if (cc != null) {						
						spString = cc.getString(cc.getColumnIndex("_id"));
						spDesc = cc.getString(cc.getColumnIndex("PDesc"));
						spCode = cc.getString(cc.getColumnIndex("PCode"));
						spSupplier = cc.getString(cc.getColumnIndex("Supplier"));
						spRate = cc.getString(cc.getColumnIndex("Rate"));

						EditText txtProdcutCode = (EditText) AddItemsdialoge.findViewById(R.id.txtInvEditProdCode);
						txtProdcutCode.setText(spCode);

						EditText txtProdcutDesc = (EditText) AddItemsdialoge.findViewById(R.id.txtInvProdDesc);
						txtProdcutDesc.setText(spDesc);

						EditText txtSuppluer = (EditText) AddItemsdialoge.findViewById(R.id.txtInvProdSupplier);
						txtSuppluer.setText(spSupplier);
						//if(!cc.isNull(cc.getColumnIndex("image")))
						{
						ImageView mImageView = (ImageView) AddItemsdialoge.findViewById(R.id.pInvImage);
						mImageView.setImageBitmap(getImageFromBLOB(getImage(spCode) ));
						}
						EditText txtRate = (EditText) AddItemsdialoge.findViewById(R.id.txtInvProdRate);
						EditText txtDiscount = (EditText) AddItemsdialoge.findViewById(R.id.txtProdDiscount);
						if (lstFlag == "0") {
							txtRate.setText(spRate);
						} else {
							//Toast.makeText(parentView.getContext(),"Setting Rate and discount Done "+ spString, Toast.LENGTH_SHORT).show();
							txtRate.setText(lstRate);
							txtDiscount.setText(lstDiscount);
							lstFlag = "0";
						}
					}
					//Toast.makeText(parentView.getContext(),"Getting Done " + spString, Toast.LENGTH_SHORT).show();

				}

				public void onNothingSelected(AdapterView<?> parentView) {
					// your code here
				}

			});
			d.closeConnection();
			return AddItemsdialoge;
		}
		
		
		return null;
	}
	
	protected void onPrepareDialog ( int id, Dialog dialog ) {
	    switch ( id ) {
	        case Product_Dialog_id:
	        	try
	        	{
	        	//Toast.makeText(getApplicationContext(),	"List in "+ lstInvDetId  +"  prepare dialog " + lstProdcode, Toast.LENGTH_SHORT).show();
	        	final Spinner sps = (Spinner) screendialoge	.findViewById(R.id.spinvEditProductSelect);
				
				if (lstFlag != "0") {
					//Toast.makeText(getApplicationContext(),"Settingspinner when lstfalg not eql to zero",Toast.LENGTH_SHORT).show();					
					for (int i = 0; i < sps.getCount(); i++) {
						{
							Cursor cc2 = (Cursor) (sps.getItemAtPosition(i));
							String SpnVal = cc2.getString(cc2.getColumnIndex("PCode"));
							if (lstProdcode.equals(SpnVal)) {
								sps.setSelection(i);
							}
						}
					}

				}
				
				// lstProdcode;
				d.closeConnection();
	        	}
	        	catch (Exception e) {
					// TODO: handle exception
				}
	    }
	    super.onPrepareDialog( id, dialog );
	}
	
	private Button.OnClickListener btnAddItemsdialoge_OKOnClickListener1 = new Button.OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			try
			{
			EditText txtProdCode = (EditText) AddItemsdialoge.findViewById(R.id.txtInvEditProdCode);
			EditText txtQuantity = (EditText) AddItemsdialoge.findViewById(R.id.txtProdQty);
			EditText txtRate = (EditText) AddItemsdialoge.findViewById(R.id.txtInvProdRate);
			EditText txtDiscount = (EditText) AddItemsdialoge.findViewById(R.id.txtProdDiscount);
			
			String Empty="";
			String EmptyInit="Select One";
			if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
			{
				Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
				return;
			}
			ContentValues cc = new ContentValues();
		
			cc.put("ProductCode", txtProdCode.getText().toString());
			cc.put("Quantity", txtQuantity.getText().toString());
			cc.put("Rate", txtRate.getText().toString());
			cc.put("Discount", txtDiscount.getText().toString());
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			Cursor cProduct = d.getProdcutDetails(lstInvId);
			String ProdcutCode = txtProdCode.getText().toString();
			String Quantity = txtQuantity.getText().toString();
			String Rate = txtRate.getText().toString();
			String Discount = txtDiscount.getText().toString();
			/*if (cProduct != null) {
				if (cProduct.moveToFirst()) {
					do {
						ProdcutCode = cProduct.getString(cProduct.getColumnIndex("_id"));
						Rate = cProduct.getString(cProduct.getColumnIndex("Rate"));
					} while (cProduct.moveToNext());
					cProduct.close();

				}
				//Toast.makeText(this,returnCodes[0] + "  fotl product not null  " + Rate+ returnCodes[1], Toast.LENGTH_SHORT).show();
			} else
				//Toast.makeText(this,returnCodes[0] + "  fotl product null  " + Rate+ returnCodes[0], Toast.LENGTH_SHORT).show();
			*/
			d.closeConnection();
			ContentValues CCContent = new ContentValues();
			CCContent.put("InvoiceId", lstInvId);
			CCContent.put("ProductCode", ProdcutCode );
			CCContent.put("Quantity", Quantity);
			CCContent.put("Rate", Rate);
			CCContent.put("Discount", Discount);
			d.InsertInvoiceDetail(CCContent);
			CCContent.put("Quantity", "0.0");
			d.InsertInvoiceDetail(CCContent);
			//Toast.makeText(getApplicationContext(), lstInvId+ "  fotl product inserted " , Toast.LENGTH_SHORT).show();

			Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(CInvDetail);
			//Toast.makeText(this,returnCodes[0] + "  fotl NEW DETAILS CAME  " + Rate+ returnCodes[1], Toast.LENGTH_SHORT).show();
			d.closeConnection();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			/*Cursor cInvDetails = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(cInvDetails);*/
			AddItemsdialoge.dismiss();
		}
	};
	
	
	private Button.OnClickListener AddItemsdialoge_NextOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			try
			{
				//Toast.makeText(	getApplicationContext() ," Updating next details... " , Toast.LENGTH_SHORT).show();
				EditText txtProdCode = (EditText) AddItemsdialoge.findViewById(R.id.txtInvEditProdCode);
				EditText txtQuantity = (EditText) AddItemsdialoge.findViewById(R.id.txtProdQty);
				EditText txtRate = (EditText) AddItemsdialoge.findViewById(R.id.txtInvProdRate);
				EditText txtDiscount = (EditText) AddItemsdialoge.findViewById(R.id.txtProdDiscount);
				
				String Empty="";
				String EmptyInit="Select One";
				if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
				{
					Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
					return;
				}
				
				ContentValues cc = new ContentValues();			
				cc.put("ProductCode", txtProdCode.getText().toString());
				cc.put("Quantity", txtQuantity.getText().toString());
				cc.put("Rate", txtRate.getText().toString());
				cc.put("Discount", txtDiscount.getText().toString());
				DbVyaapar d = new DbVyaapar(getApplicationContext());
				//d.UpdateInvoiceDetail(cc, "InvoiceId = " + lstInvId + " And InvDetailId= " + lstInvDetId);
				if(lstQuantity.contentEquals("0"))
				{
				ContentValues CCContent = new ContentValues();
				CCContent.put("InvoiceId", lstInvId);
				CCContent.put("ProductCode", txtProdCode.getText().toString() );
				CCContent.put("Quantity", txtQuantity.getText().toString());
				CCContent.put("Rate",  txtRate.getText().toString());
				CCContent.put("Discount", txtDiscount.getText().toString());
				d.InsertInvoiceDetail(CCContent);				
				Toast.makeText(	getApplicationContext() ," Inserted into Invoice details... " , Toast.LENGTH_SHORT).show();
				}
				Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
				GetInvoiceDetails(CInvDetail);			
				d.closeConnection();
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			
					
		}
	};

	private Button.OnClickListener btnScreenDialog_NextOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			try
			{
				//Toast.makeText(	getApplicationContext() ," Updating next details... " , Toast.LENGTH_SHORT).show();
				EditText txtProdCode = (EditText) screendialoge.findViewById(R.id.txtInvEditProdCode);
				EditText txtQuantity = (EditText) screendialoge.findViewById(R.id.txtProdQty);
				EditText txtRate = (EditText) screendialoge.findViewById(R.id.txtInvProdRate);
				EditText txtDiscount = (EditText) screendialoge.findViewById(R.id.txtProdDiscount);
				
				String Empty="";
				String EmptyInit="Select One";
				if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
				{
					Toast.makeText(	getApplicationContext() ," Select Product... " , Toast.LENGTH_SHORT).show();
					return;
				}
				
				ContentValues cc = new ContentValues();			
				cc.put("ProductCode", txtProdCode.getText().toString());
				cc.put("Quantity", txtQuantity.getText().toString());
				cc.put("Rate", txtRate.getText().toString());
				cc.put("Discount", txtDiscount.getText().toString());
				DbVyaapar d = new DbVyaapar(getApplicationContext());
				//d.UpdateInvoiceDetail(cc, "InvoiceId = " + lstInvId + " And InvDetailId= " + lstInvDetId);
				if(lstQuantity.contentEquals("0"))
				{
				ContentValues CCContent = new ContentValues();
				CCContent.put("InvoiceId", lstInvId);
				CCContent.put("ProductCode", txtProdCode.getText().toString() );
				CCContent.put("Quantity", txtQuantity.getText().toString());
				CCContent.put("Rate",  txtRate.getText().toString());
				CCContent.put("Discount", txtDiscount.getText().toString());
				d.InsertInvoiceDetail(CCContent);				
				Toast.makeText(	getApplicationContext() ," Inserted into Invoice details... " , Toast.LENGTH_SHORT).show();
				}
				Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
				GetInvoiceDetails(CInvDetail);			
				d.closeConnection();
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			
					
		}
	};
	
	
	private Button.OnClickListener btnScreenDialog_OKOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			try
			{				
			EditText txtProdCode = (EditText) screendialoge.findViewById(R.id.txtInvEditProdCode);
			EditText txtQuantity = (EditText) screendialoge.findViewById(R.id.txtProdQty);
			EditText txtRate = (EditText) screendialoge.findViewById(R.id.txtInvProdRate);
			EditText txtDiscount = (EditText) screendialoge.findViewById(R.id.txtProdDiscount);
		
			String Empty="";
			String EmptyInit="Select One";
			if(txtProdCode.getText().toString().trim().equals(Empty)||txtProdCode.getText().toString().trim().equals(EmptyInit) )
			{
				Toast.makeText(	getApplicationContext() ,"Select Product... " , Toast.LENGTH_SHORT).show();
				return;
			}
			
			ContentValues cc = new ContentValues();
		
			cc.put("ProductCode", txtProdCode.getText().toString());
			cc.put("Quantity", txtQuantity.getText().toString());
			cc.put("Rate", txtRate.getText().toString());
			cc.put("Discount", txtDiscount.getText().toString());
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			d.UpdateInvoiceDetail(cc, "InvoiceId = " + lstInvId + " And InvDetailId= " + lstInvDetId);	
			
			if(lstQuantity.contentEquals("0"))
			{
			ContentValues CCContent = new ContentValues();
			CCContent.put("InvoiceId", lstInvId);
			CCContent.put("ProductCode", txtProdCode.getText().toString() );
			CCContent.put("Quantity", 0);
			CCContent.put("Rate",  txtRate.getText().toString());
			CCContent.put("Discount", "0.0");
			d.InsertInvoiceDetail(CCContent);				
			}
			Cursor CInvDetail = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(CInvDetail);			
			d.closeConnection();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			/*Cursor cInvDetails = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(cInvDetails);*/
			screendialoge.dismiss();
		}
	};
	private Button.OnClickListener btnShipdialog_OKOnClickListener = new Button.OnClickListener() {

		public void onClick(View arg0) {
			// TODO Auto-generated method stub			
			try
			{
			EditText txtShipAmount = (EditText) Shipdialog.findViewById(R.id.txtShipAmount);
			EditText txtShipDate = (EditText) Shipdialog.findViewById(R.id.txtShipDate);
			EditText txtShipVia = (EditText) Shipdialog.findViewById(R.id.txtShipVia);
			EditText txtShipTrack = (EditText) Shipdialog.findViewById(R.id.txtShipTrack);
			EditText txtShipNotes = (EditText) Shipdialog.findViewById(R.id.txtShipNotes);
			TextView txtShipCharges = (TextView) findViewById(R.id.txtInvShipping);
			ContentValues cc = new ContentValues();
		/*InvShipId INTEGER PRIMARY KEY AUTOINCREMENT ,
			InvoiceId Integer,
			ShipAmount float, 
			ShipDate varchar(50), 
			ShipVia varchar(100), 
			Tracking varchar(50), 
			ShipNotes varchar(100);*/
			cc.put("ShipAmount", txtShipAmount.getText().toString());
			cc.put("ShipDate", txtShipDate.getText().toString());
			cc.put("ShipVia", txtShipVia.getText().toString());
			cc.put("Tracking", txtShipTrack.getText().toString());
			cc.put("ShipNotes", txtShipNotes.getText().toString());
			cc.put("InvoiceId", lstInvId);
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			d.UpdateInvoiceShipping(cc, lstInvId);
			txtShipCharges.setText(txtShipAmount.getText().toString());
			ITShipping =  Double.valueOf(txtShipCharges.getText().toString()).doubleValue();
			SetTotals();
			//Toast.makeText(	getApplicationContext() ," Updating shipping details... " , Toast.LENGTH_SHORT).show();
			d.closeConnection();
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			/*Cursor cInvDetails = d.getInvoiceDetails(lstInvId);
			GetInvoiceDetails(cInvDetails);*/
			Shipdialog.dismiss();
		}
	};

	private void populateList() {

		list = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put(FIRST_COLUMN, "1");
		temp.put(SECOND_COLUMN, "Colored Notebooks By NavNeet");
		temp.put(THIRD_COLUMN, "200");
		temp.put(FOURTH_COLUMN, "10");
		temp.put(FIFTH_COLUMN, "10");
		temp.put(SIXTH_COLUMN, "1800");
		list.add(temp);

		HashMap<String, String> temp1 = new HashMap<String, String>();
		temp1.put(FIRST_COLUMN, "2");
		temp1.put(SECOND_COLUMN, "DiariesBy Amee Products");
		temp1.put(THIRD_COLUMN, "400");
		temp1.put(FOURTH_COLUMN, "20");
		temp1.put(FIFTH_COLUMN, "0");
		temp1.put(SIXTH_COLUMN, "8000");
		list.add(temp1);

		HashMap<String, String> temp2 = new HashMap<String, String>();
		temp2.put(FIRST_COLUMN, "3");
		temp2.put(SECOND_COLUMN, "Note Books and Stationery By National");
		temp2.put(THIRD_COLUMN, "100");
		temp2.put(FOURTH_COLUMN, "25");
		temp2.put(FIFTH_COLUMN, "0");
		temp2.put(SIXTH_COLUMN, "2500");
		list.add(temp2);

		HashMap<String, String> temp3 = new HashMap<String, String>();
		temp3.put(FIRST_COLUMN, "4");
		temp3.put(SECOND_COLUMN, "Corporate Diaries By rakashan");
		temp3.put(THIRD_COLUMN, "800");
		temp3.put(FOURTH_COLUMN, "20");
		temp3.put(FIFTH_COLUMN, "0");
		temp3.put(SIXTH_COLUMN, "16000");
		list.add(temp3);

		HashMap<String, String> temp4 = new HashMap<String, String>();
		temp4.put(FIRST_COLUMN, "5");
		temp4.put(SECOND_COLUMN, "Writing Pad By Techno");
		temp4.put(THIRD_COLUMN, "100");
		temp4.put(FOURTH_COLUMN, "5");
		temp4.put(FIFTH_COLUMN, "0");
		temp4.put(SIXTH_COLUMN, "500");
		list.add(temp4);
	}

	public static Bitmap getImageFromBLOB(byte[] mBlob) {
		byte[] bb = mBlob;
		if (mBlob == null)
				return null;
		return BitmapFactory.decodeByteArray(bb, 0, bb.length);
	}

	public void GetInvoiceDetails(Cursor CInvDetails) {
		double IDisc=0.0;
		double RTotal = 0.0;
		
		double RSubTotal = 0.0; 
		double Qty = 0.0;
		double Rate = 0.0;
		double disc = 0.0;
		double Temp = 0.0;
		int Exists  = 0;
		ITotal = 0.0;
		Blob b ;
		try {

			if (CInvDetails != null) {				
				list = new ArrayList<HashMap<String, String>>();
				ListView lview = (ListView) findViewById(R.id.listview);
				int i = 0;
				if (CInvDetails.moveToFirst()) {
					do {
						i++;
						Exists =1;
						String InvId = CInvDetails.getString(CInvDetails.getColumnIndex("_id"));
						String InvProductCode = CInvDetails.getString(CInvDetails.getColumnIndex("ProductCode"));
						String InvProductDesc = CInvDetails.getString(CInvDetails.getColumnIndex("ProductDesc"));
						String InvQuantity = CInvDetails.getString(CInvDetails.getColumnIndex("Quantity"));
						String InvRate = CInvDetails.getString(CInvDetails.getColumnIndex("Rate"));
						String InvDiscount = CInvDetails.getString(CInvDetails.getColumnIndex("Discount"));
						String InvImage = CInvDetails.getString(CInvDetails.getColumnIndex("Discount"));
						if(InvQuantity.trim()!= "")
							Qty = Double.valueOf(InvQuantity).doubleValue();
						if(InvRate.trim()!= "")
							Rate = Double.valueOf(InvRate).doubleValue();
						if(InvDiscount.trim()!= "")
							IDisc = Double.valueOf(InvDiscount).doubleValue();
						
						//Temp  = ((Qty*Rate)/100 )* disc;
						Temp = disc/100;
						ITDisc += Temp;
						RSubTotal = (Qty * Rate)- (Qty * Rate)*(IDisc/100);						
						ITotal += RSubTotal;
						DecimalFormat df = new DecimalFormat("###.##");
				        
			        		        
						//Toast.makeText(	getApplicationContext() ,"  fotl got image in invoice details "+ RSubTotal  , Toast.LENGTH_SHORT).show();
						String StrImage = "";//getImage(InvProductCode);						
						//Toast.makeText(	getApplicationContext() ,"  fotl got image in text format "+StrImage + "Tested"  , Toast.LENGTH_SHORT).show();
						HashMap<String, String> temp = new HashMap<String, String>();

						temp.put(ZEROTH_COLUMN, InvId);
						temp.put(FIRST_COLUMN, "" + i);
						temp.put(SECOND_COLUMN, InvProductCode);
						temp.put(LAST_COLUMN, InvProductDesc);						
						temp.put(THIRD_COLUMN,InvRate  );						
						temp.put(FOURTH_COLUMN, InvQuantity );		
						temp.put(FIFTH_COLUMN, InvDiscount );
						temp.put(SIXTH_COLUMN, "" + df.format(RSubTotal));
						temp.put(Image_COLUMN, StrImage);
						list.add(temp);
						SetTotals();
						
					}  while (CInvDetails.moveToNext());
				}
				//Toast.makeText(	getApplicationContext() ,"  fotl displaying invoice details fron getinvoice detail after do "+Exists , Toast.LENGTH_SHORT).show();Toast.makeText(	getApplicationContext() ,"  fotl displaying invoice details fron getinvoice detail in do " , Toast.LENGTH_SHORT).show();
				if(Exists!= 1)
				{
					for(int j = 0;j<3;j++)
					{
					HashMap<String, String> temp = new HashMap<String, String>();
					temp.put(ZEROTH_COLUMN, "");
					temp.put(FIRST_COLUMN, "" + (j+1));
					temp.put(SECOND_COLUMN, "");
					temp.put(FIFTH_COLUMN, ""+0);
					temp.put(THIRD_COLUMN, ""+0.0);
					temp.put(FOURTH_COLUMN, ""+0.0);					
					temp.put(SIXTH_COLUMN, "" + 0.0);					
					list.add(temp);
					}
				}

				listviewAdapter lvadapter = new listviewAdapter(this, list);
				lview.setAdapter(lvadapter);
				CInvDetails.close();
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	public View getView(int position, View lviewm, ViewGroup parent) {
		TextView tv1 = (TextView) lviewm.findViewById(R.id.SecondText);
		tv1.setVisibility(lviewm.INVISIBLE);
		
		TextView tv2 = (TextView) lviewm.findViewById(R.id.ThirdText);
		tv2.setVisibility(lviewm.INVISIBLE);
		return lviewm;
	}
	
	
	public byte[] getImage(String PCode)
	{
		byte[] strImg = null;
		try
		{
			DbVyaapar d = new DbVyaapar(getApplicationContext());
			Cursor c = d.getProdcutImage(PCode);
			if(c!= null)
			{
				if (c.moveToFirst()) {
					do {
						if(!c.isNull(c.getColumnIndex("image")))
							strImg = c.getBlob(c.getColumnIndex("image"));
						//Toast.makeText(	getApplicationContext() ,"  fotl got image in text format "+strImg + "Tested ......."  , Toast.LENGTH_SHORT).show();
					} while (c.moveToNext());
				}c.close();				
			}
			d.closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return strImg;
	}
	
	public void SetTotals()
	{
		try
		{
		TextView txtInvSubTotal = (TextView) findViewById(R.id.txtInvoiceSubTotal);
		txtInvSubTotal.setText("" + ITotal);
		EditText txtInvDisc = (EditText) findViewById(R.id.txtInvDisc);
		aftot = (ITotal+ ITShipping+ ITSales+ITpkg);
		AdDisc = Double.parseDouble(txtInvDisc.getText().toString());
		AdDisc = AdDisc/100;
		double aftot2 = ITotal * AdDisc;
		aftot = aftot- aftot2;
		//txtInvDisc.setText(""+ITDisc );
		TextView txtInvTotal= (TextView) findViewById(R.id.txtInvTotal);
		
		txtInvTotal.setText(""+ aftot);
		TextView txtBalance= (TextView) findViewById(R.id.txtInvBalance);
		txtBalance.setText("" +((aftot) - ITamt ));
		}
		catch (Exception e) {}
	}

	public ContentValues GetInvoiceMasterContent() {
		ContentValues CCContentValues = null;
		try {
			EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);
			//EditText txtInvNo = (EditText) findViewById(R.id.txtInvNo);
			EditText txtInvoiceDate = (EditText) findViewById(R.id.txtInvoiceDate);
			Spinner spCustomer = (Spinner) findViewById(R.id.spInvoiceCustomer);
			Spinner spInvoiceType = (Spinner) findViewById(R.id.spInvoiceType);
			/*
			 * InvoiceId INTEGER PRIMARY KEY AUTOINCREMENT ,InvoiceType
			 * varchar(50),CustomerId Integer,InvoiceDate varchar(50),
			 * TotalAmount Float,AmountReceived Float,Balance Float,Terms
			 * varchar(50),TIN varchar(50),Status Varchar(50));";
			 */
			String Customer ="";	
			Cursor cc = (Cursor)(spCustomer.getSelectedItem());
			if (cc != null) {
				Customer = cc.getString(cc.getColumnIndex("CName"));
			}
			CCContentValues.put("InvoiceType", spInvoiceType.getSelectedItem().toString());
			CCContentValues.put("CustomerId", Customer);
			CCContentValues.put("InvoiceDate", txtInvoiceDate.getText().toString());
			CCContentValues.put("Sales", ITSales);
			CCContentValues.put("PkgCharges", ITpkg);
			CCContentValues.put("Shipping", ITShipping);
			CCContentValues.put("TotalAmount",ITotal );
			CCContentValues.put("AmountReceived", ITamt);
			CCContentValues.put("Balance",  (ITotal-ITamt));
			CCContentValues.put("Terms", "");
			CCContentValues.put("TIN", "");
			CCContentValues.put("Status", "Draft");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return CCContentValues;
	}

	public void createAttachment()
	{
		EditText txtInvoiceNumber = (EditText) findViewById(R.id.txtInvoiceNumber);
		try
		{
			String path = "/sdcard/"+"Invoice"+  txtInvoiceNumber.getText().toString()  +".pdf";
			File file=new File("/sdcard/");
			File f = new File(file, "Invoice"+ txtInvoiceNumber.getText().toString() +".pdf");
			 if (!f.exists())
            {
             f.createNewFile();
            }
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			document.add(createFirstTable());
			document.close();
			Toast.makeText(getApplicationContext(),"Document created and saved at "+path,Toast.LENGTH_SHORT).show();
		}
		catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(),"Document create exception "+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
		}
	}

	private List<String> getModel() {
		try
		{		
		DbVyaapar db = new DbVyaapar(getApplicationContext());		
		Cursor c = db.getProductList();
		if (c != null ) {
		    if  (c.moveToFirst()) {
		        do {		            		            
		            list2.add(c.getString(c.getColumnIndex("PDesc")));
		        }while (c.moveToNext());
		    }
		}
		c.close();
		}
		catch (Exception e) {
			
		}
		return list2;
	}
	
	 public int getposition()
     {
     	String sitem ="";
     	int pos = 0;
     	int ori=0;
     	String stext  = AutoDesc.getText().toString().trim();
     	try        	
     	{
     		final Spinner sps1 = (Spinner) this.findViewById(R.id.spinvEditProductSelect);
     	DbVyaapar db = new DbVyaapar(getApplicationContext());		
 		Cursor c = db.getProductList();
 		if (c != null ) {
 		    if  (c.moveToFirst()) {
 		        do {		
 		        	
 		           sitem= c.getString(c.getColumnIndex("PDesc")).trim();
 		           if(sitem.contentEquals(stext))
 		           {
 		        	   ori= c.getPosition();// ("PDesc");
 		        	  
 		        	   sps1.setSelection(++ori);
 		        	   
 		        	  //Toast.makeText(	getApplicationContext() ,"focus setted  Product... "+ pos , Toast.LENGTH_SHORT).show();
 		           c.close();
 		           return ori;
 		           }
 		           else
 		           {
 		        	   pos++;
 		        	 // Toast.makeText(	getApplicationContext() ,sitem + "focus not eql  Product... "+ pos+"   "+stext , Toast.LENGTH_SHORT).show();
 		           }
 		        }while (c.moveToNext());
 		        c.close();
 		    }
 		}
     	}
     	catch(Exception ex)
     	{}
     	return pos;
     }
}


