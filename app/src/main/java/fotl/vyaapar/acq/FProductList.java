/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Jk Designs Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@Jk Designsprojects.com
 * ====================================================================
 */
package fotl.vyaapar.acq;

import android.R.integer;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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


public class FProductList extends  BaseListActivity  {
	public static final String KEY_IMAGE = "image";
	public List<String> list2 = new ArrayList<String>();
	SQLiteDatabase myDB; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fproductlist);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********
		
		 WindowManager.LayoutParams params = getWindow().getAttributes();  
	        params.x = -100;  
	        params.height = 370;  
	        params.width = 1000;  
	        params.y = 550;
	        this.getWindow().setAttributes(params);  
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1,getModel());
		ListView pList = (ListView) findViewById(R.id.listview);
		//pList.setAdapter(adapter);
		setListAdapter(adapter);
		EditText filterEditText = (EditText) findViewById(R.id.filterText);
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
	
	 protected void onListItemClick(ListView l, View v, int position, long id) {
		 
         super.onListItemClick(l, v, position, id);
        // Toast.makeText(this, "Item Clicked: " + list2.get(position) ,Toast.LENGTH_LONG).show();
         
         Intent intent1 = new Intent();
         intent1.putExtra("returnKey1",list2.get(position));
         this.setResult(RESULT_OK, intent1);
         finish();
         //startActivity(intent1);
         
         
         
     }
	private List<String> getModel() {
		DbVyaapar db = new DbVyaapar(getApplicationContext());		
		//Toast.makeText(getApplicationContext(), "Item getting: "  , Toast.LENGTH_LONG).show();
		Cursor c = db.getProductList();
		if (c != null ) {
		    if  (c.moveToFirst()) {
		        do {
		            String firstName = c.getString(c.getColumnIndex("PCode"));		            
		            list2.add("" + firstName + ",Desc: " +c.getString(c.getColumnIndex("PDesc")));
		        }while (c.moveToNext());
		    }
		}
		c.close();
		db.closeConnection();
		
		/*list.add("Linux");
		list.add("Windows7");
		list.add("Suse");
		list.add("Eclipse");
		list.add("Ubuntu");
		list.add("Solaris");
		list.add("Android");
		list.add("iPhone");*/
		return list2;
	}
	/*public synchronized void openConnection(){
		myDB=this.openOrCreateDatabase("Vyaapar.db", 0 ,null);
	}
	public synchronized void closeConnection(){
		myDB.close();
	}*/
}
