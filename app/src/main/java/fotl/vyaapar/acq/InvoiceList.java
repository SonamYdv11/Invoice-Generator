/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import static fotl.vyaapar.acq.Constant.Customer_COLUMN;
import static fotl.vyaapar.acq.Constant.Date_COLUMN;
import static fotl.vyaapar.acq.Constant.Amount_COLUMN;
import static fotl.vyaapar.acq.Constant.Status_COLUMN;
import static fotl.vyaapar.acq.Constant.Number_COLUMN;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import fotl.vyaapar.acq.customadapters.InvoicelistAdapter;

public class InvoiceList extends BaseActivity {
	public static final String KEY_IMAGE = "image";
	public List<String> list2 = new ArrayList<String>();
	private ArrayList<HashMap<String, String>> Invlist;
	private EditText search_filter;
	private String search_key = "";
	ListView IList= null;
	String listItemId =  "";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoicelist);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********







		IList = (ListView)findViewById(R.id.InvoiceListView);

		search_filter = (EditText)findViewById(R.id.invoiceFilterText);
		search_filter.addTextChangedListener(new TextWatcher() {
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//				Toast.makeText(getBaseContext(), charSequence.toString(), Toast.LENGTH_LONG).show();
//				LoadList();
			}

			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//				Toast.makeText(getBaseContext(), charSequence.toString(), Toast.LENGTH_LONG).show();
				search_key = charSequence.toString();
				LoadList();
			}

			public void afterTextChanged(Editable editable) {

			}
		});

		IList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		IList.setItemChecked(2, true);
		LoadList();
		registerForContextMenu(IList);
		
		IList.setOnItemClickListener(new ListView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,long id) {
				// super.onListItemClick(l, v, position, id);				
				TextView tv1 = (TextView) v.findViewById(R.id.InvoiceNumber);
				//Toast.makeText(getApplicationContext(),"List item clicked"+tv1.getText().toString()+"fotl",Toast.LENGTH_SHORT).show();
				Intent intent1 = new Intent(getBaseContext(), InvoiceCreation.class);
				intent1.putExtra("Key1", tv1.getText().toString());
				startActivity(intent1);		
				//finish();
			}
		});
		
		IList.setOnItemLongClickListener(new ListView.OnItemLongClickListener(){

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 //activity.openContextMenu(arg1);

				TextView tv1 = (TextView) arg1.findViewById(R.id.InvoiceNumber);
				listItemId = tv1.getText().toString();
				//Toast.makeText(getApplicationContext(),"Long click listener"+ tv1.getText().toString(),Toast.LENGTH_SHORT).show();
				return false;
			}
		}  );
		
		final ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeScreen);
		 btnHome.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v)
	        	{
	        		startActivity(new Intent(InvoiceList.this, MenuForm.class));
	        		//finish();
	        	}
	        });
//
//		  final Button btnMnuInvoice = (Button)findViewById(R.id.BtnMnuInvoice);
//			 btnMnuInvoice.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		startActivity(new Intent(InvoiceList.this, InvoiceCreation.class));
//		        		//finish();
//		        	}
//		        });
//
//			 final Button btnMnuMasters = (Button)findViewById(R.id.BtnMnuMasters);
//			 btnMnuMasters.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		startActivity(new Intent(InvoiceList.this, Masters.class));
//		        		//finish();
//		        	}
//		        });
//
//			 final Button btnMnuLists = (Button)findViewById(R.id.BtnMnuLists);
//			 btnMnuLists.setOnClickListener(new OnClickListener(){
//		        	public void onClick(View v)
//		        	{
//		        		//Toast.makeText(getApplicationContext(),"Directory  "+"fotl",Toast.LENGTH_SHORT).show();
//		        		startActivity(new Intent(InvoiceList.this, Folders.class));
//		        		//finish();
//		        	}
//		        });
			 
		 
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		    super.onCreateContextMenu(menu, v, menuInfo);  
		        
		            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		            menu.setHeaderTitle("Select");
		            menu.add(0, v.getId(), 0, "Delete");    
		        
		    } 
	
	 public boolean onContextItemSelected(MenuItem item) {  
		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		 if(item.getTitle()=="Delete"){DeleteInvoice();}
	        else {return false;}  
	    return true;  
	    } 
	
	 public void DeleteInvoice(){  
	        Toast.makeText(this, "Invoice Number "+listItemId + " is Deleted",   Toast.LENGTH_SHORT).show();	        
	         DbVyaapar d = new DbVyaapar(getApplicationContext());
	         d.DeleteInvoice(listItemId);
	         LoadList();
	    }
//    public Date ConvertToDate( String date1) throws Exception
//    {
//
//        Date date2=new SimpleDateFormat( "MM/dd/yyyy").parse(date1);
//        return date2;
//    }
	public void LoadList()
	{

	ListView IList = (ListView) findViewById(R.id.InvoiceListView);
	Invlist = new ArrayList<HashMap<String, String>>();
	DbVyaapar d = new DbVyaapar(getApplicationContext());

	Cursor cInvoicelist = d.getInvoiceList();
	if (cInvoicelist != null) {
		if (cInvoicelist.moveToFirst()) {
			do {
				String nm = cInvoicelist.getString(cInvoicelist.getColumnIndex("CustomerId"));
				String nid = cInvoicelist.getString(cInvoicelist.getColumnIndex("_id"));
//				String p_date = cInvoicelist.getString(cInvoicelist.getColumnIndex("InvoiceDate"));
//				try {
//					Date d3=ConvertToDate(p_date);
					//Toast.makeText(getApplicationContext()," p date"+p_date,Toast.LENGTH_LONG).show();
					if (nm.toLowerCase().contains(search_key.toLowerCase())) {
//							if(ConvertToDate(tvFromDate.getText().toString()).before( ConvertToDate(p_date)) &&
//										ConvertToDate(tvToDate.getText().toString()).after( ConvertToDate(p_date)))
//                    		{
//						Toast.makeText(getApplicationContext(), "--> Cust ID: " + nm + "Number :" + nid + "\n" + search_key, Toast.LENGTH_LONG).show();
						//Toast.makeText(getApplicationContext(),"List item adding "+ cInvoicelist.getString(cInvoicelist.getColumnIndex("TotalAmount")),Toast.LENGTH_SHORT).show();
						HashMap<String, String> temp = new HashMap<String, String>();
						temp.put(Number_COLUMN, nid);
						temp.put(Customer_COLUMN, nm);
						temp.put(Date_COLUMN, cInvoicelist.getString(cInvoicelist.getColumnIndex("InvoiceDate")));
						temp.put(Amount_COLUMN, cInvoicelist.getString(cInvoicelist.getColumnIndex("TotalAmount")));
						temp.put(Status_COLUMN, cInvoicelist.getString(cInvoicelist.getColumnIndex("Status")));
						Invlist.add(temp);
					}
			} while (cInvoicelist.moveToNext());
		}
	}
	InvoicelistAdapter lvadAdapter = new InvoicelistAdapter(this, Invlist);
	IList.setAdapter(lvadAdapter);
	cInvoicelist.close();
	d.closeConnection();
	}
}
