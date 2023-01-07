/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import fotl.vyaapar.acq.customadapters.CustomerListAdapter;


public class CustomerList extends BaseActivity {
	public static final String KEY_IMAGE = "image";
	public List<String> list2 = new ArrayList<String>();
	SQLiteDatabase myDB; 
	ArrayAdapter<String> adapter= null;
	ListView lvlist;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customerlist);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********

		lvlist = findViewById(R.id.lvlist);
		LoadList();
//		registerForContextMenu(lvlist);

		EditText filterEditText = (EditText) findViewById(R.id.btnCustSearch);
		filterEditText.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,int count) {
				adapter.getFilter().filter(s.toString());
			}


			public void beforeTextChanged(CharSequence s, int start, int count,	int after) {
			}


			public void afterTextChanged(Editable s) {
			}
		});
		

	}

	@Override
	//Creating context menu for the Deletion of Customer
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

//		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle("Select");
		menu.add(0, (Integer) v.getTag(), 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

//		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//		String listItemName = list2.get(info.position);
		String listItemName = list2.get(item.getItemId());
		if(item.getTitle()=="Delete"){DeleteCustomer(listItemName);}
		else {return false;}
		return true;
	}
	
	 public void DeleteCustomer(String  ListItemName){  
	        Toast.makeText(this, "Customer "+ListItemName + " is Deleted",   Toast.LENGTH_SHORT).show();	        
	         DbVyaapar d = new DbVyaapar(getApplicationContext());
	         d.DeleteCustomer(ListItemName);
	         LoadList();
	         //Cursor cProduct= d.getProdcutDetails(PCode[0]);
	    }  
	 
	 public void LoadList()
	 {
		 if(adapter!= null)
			 adapter.clear();
		adapter = new CustomerListAdapter(this,android.R.layout.simple_list_item_2, android.R.id.text1,getModel());
		lvlist.setAdapter(adapter);
	 }
	


	private List<String> getModel() {
		//List<String> list2 = new ArrayList<String>();		
		DbVyaapar d = new DbVyaapar(getApplicationContext());		
		Cursor c = d.getCustomers();

		if (c != null ) {			
		    if  (c.moveToFirst()) {
		        do {		        	
		            String firstName = c.getString(c.getColumnIndex("CName"));		            
		            list2.add("" + firstName + "");		            
		        }while (c.moveToNext());
		    }c.close();
		}
		return list2;
	}
	
}

