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
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;

import fotl.vyaapar.acq.customadapters.CustomProductListAdapter;


public class ProductList extends  BaseActivity  {
	public static final String KEY_IMAGE = "image";
	public List<String> list2 = new ArrayList<String>();
	SQLiteDatabase myDB;
	ArrayAdapter<String> adapter=null;
	private ListView lvList;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.productlist);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		//*********
		LinearLayout ll = findViewById(R.id.linearLayout44);
		ll.setVisibility(View.GONE);
		//*********


		lvList = findViewById(R.id.lvList);
		LoadList();
//		registerForContextMenu(lvList);
		try
		{
			TextView text = (TextView) findViewById(android.R.id.text1);
			text.setTextColor(Color.BLACK);
			Toast.makeText(this, "Item " ,Toast.LENGTH_LONG).show();
		}
		catch (Exception e) {
			// TODO: handle exception
		}

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
		final ImageButton btnHome = (ImageButton)findViewById(R.id.btnHomeScreen);
		btnHome.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				startActivity(new Intent(ProductList.this, MenuForm.class));
				//finish();
			}
		});




	}


	//For the Deletion of Product Item
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

//		            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		menu.setHeaderTitle("Select");
		menu.add(0, (Integer) v.getTag(), 0, "Delete");

	}

	public boolean onContextItemSelected(MenuItem item) {
//		 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		String listItemName = list2.get(item.getItemId());
		if(item.getTitle()=="Delete"){DeleteProduct(listItemName);}
		else {return false;}
		return true;
	}

	public void DeleteProduct(String  ListItemName){
		Toast.makeText(this, "Item "+ListItemName + " is Deleted",   Toast.LENGTH_SHORT).show();
		String[] PCode = ListItemName.split(",-");
		String EPcode=PCode[0];
		DbVyaapar d = new DbVyaapar(getApplicationContext());
		d.DeleteProduct(EPcode);
		LoadList();
		//Cursor cProduct= d.getProdcutDetails(PCode[0]);
	}


	public void LoadList()
	{
		if(adapter!= null)
			adapter.clear();
		adapter = new CustomProductListAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1,getModel());
		ListView pList = (ListView) findViewById(R.id.listview);
		//pList.setAdapter(adapter);
		lvList.setAdapter(adapter);

	}


	private List<String> getModel() {
		try
		{
		/*openConnection();
		Cursor c = myDB.rawQuery("SELECT PCode, PDesc FROM ProductMaster ", null);*/
			DbVyaapar db = new DbVyaapar(getApplicationContext());
			Cursor c = db.getProductList();
			if (c != null ) {
				if  (c.moveToFirst()) {
					do {
						String firstName = c.getString(c.getColumnIndex("PCode"));
						list2.add("" + firstName + ",- " +c.getString(c.getColumnIndex("PDesc")));
					}while (c.moveToNext());
				}
			}
	/*	else
		{
			list2.add("NO ITEM EXISTS" );
		}*/
			c.close();
			closeConnection();

		}
		catch (Exception e) {
			// TODO: handle exception
		}
		return list2;
	}
	public synchronized void openConnection(){
		myDB=this.openOrCreateDatabase("Vyaapar.db", 0 ,null);
	}
	public synchronized void closeConnection(){
		myDB.close();
	}
}

