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
import java.lang.reflect.Field;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MenuForm extends Activity {



	MenuItem menu_item;
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        menu_item = (MenuItem)findViewById(R.menu.main_menu);
        onOptionsItemSelected(menu_item);


		final TextView txt_log = (TextView) findViewById(R.id.logo);
		txt_log.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{

				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
			}
		});

        DbVyaapar d = new DbVyaapar(getApplicationContext());
		Cursor cur=  d.getCompanycheck();
        Intent intent = new Intent();
		if (cur == null )
		{
			cur.close();
			d.closeConnection();
		intent.setClass(getApplicationContext(), Company.class);		
		startActivity(intent);
		finish();
		}
		cur.close();
		d.closeConnection();
        
		 TextView text = (TextView) findViewById(R.id.txtfooter);
	     text.setMovementMethod(LinkMovementMethod.getInstance());
	     text.setText(Html.fromHtml("    Version 1.1 Copy right@2011 All rights Reserved      &nbsp;&nbsp;Developed by <a href=https://www.linkedin.com/in/prabhakar-gadupudi-a7095227/> Jk Designs Solutions Pvt Ltd </a>"));
		
        final ImageButton  btninvoice = (ImageButton)findViewById(R.id.btninvoice);
        btninvoice.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		   		
        			startActivity(new Intent(MenuForm.this, InvoiceCreation.class));        		

        	}
        });
        final ImageButton  btnStartForm = (ImageButton)findViewById(R.id.btnproducts);
        btnStartForm.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MenuForm.this, ProductMaster.class));        		
        		//setContentView(R.layout.productmaster);    		
        	}
        });
        
        final ImageButton btnCustomer = (ImageButton)findViewById(R.id.btncustomer);
        btnCustomer.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MenuForm.this, CustomerMaster.class));        		
        		//setContentView(R.layout.customermaster);    		
        	}
        });
        final ImageButton btncompany = (ImageButton)findViewById(R.id.btncompany);
        btncompany.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MenuForm.this, CompanyContact.class));        		
        		//setContentView(R.layout.companycontact);    		
        	}
        });		
        final ImageButton btnsetting = (ImageButton)findViewById(R.id.btnsetting);
        btnsetting.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MenuForm.this, Company.class));        		
        		//setContentView(R.layout.company);    		
        	}
        });
        
        final ImageButton btnsupplier = (ImageButton)findViewById(R.id.btnsupplier);
        btnsupplier.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		//startActivity(new Intent(MenuForm.this, ProductList.class));        		
        		//setContentView(R.layout.productlist);    		
        	}
        });
       /* final ImageButton btnexit = (ImageButton)findViewById(R.id.btnexit);
        btnexit.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		/*startActivity(new Intent(MenuForm.this, CustomerList.class));
        		setContentView(R.layout.customerlist);
        		finish();
        		System.exit(0);
        	}
        });*/
        final ImageButton btFolders = (ImageButton)findViewById(R.id.btnfolders);
        btFolders.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{
        		startActivity(new Intent(MenuForm.this, Folders.class));        		
        		//setContentView(R.layout.folders);    		
        	}
        });
        
        final ImageButton btnHelp = (ImageButton)findViewById(R.id.btnhelp);
        btnHelp.setOnClickListener(new OnClickListener(){
        	public void onClick(View v)
        	{

        		Intent intent = new Intent();
        		intent.setAction(android.content.Intent.ACTION_VIEW);
        		File file = new File("/sdcard/help.pdf");
        		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        		startActivity(intent); 
        	}
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return  super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String iconTitle = "";
		try {
			iconTitle = item.getTitle().toString().toLowerCase();
			//if(iconTitle.length() > 2){/*Toast.makeText(getApplicationContext(), iconTitle, Toast.LENGTH_SHORT).show();*/}
		} catch (Exception e) {}
		Toast.makeText(getApplicationContext(), ">"+iconTitle, Toast.LENGTH_SHORT).show();

		if ("create invoice".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, InvoiceCreation.class));
			finish();
		}else if ("create products".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, ProductMaster.class));
			finish();
		}else if ("reports".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, Folders.class));
			finish();
		}else if ("settings".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, Company.class));
			finish();
		}else if ("create customer".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, CustomerMaster.class));
			finish();
		}else if ("create company".equals(iconTitle)) {
			startActivity(new Intent(MenuForm.this, Company.class));
			finish();
		}


		return super.onOptionsItemSelected(item);
	}

//	private void sayCommand(TextToSpeech tts, String SpeakText, boolean is_take_input) {
////		progressBar.setVisibility(View.INVISIBLE);
//		tts.speak(SpeakText, TextToSpeech.QUEUE_FLUSH, null);
//		// tts.playSilentUtterance(2000, TextToSpeech.QUEUE_ADD, null);
//		while (tts.isSpeaking()) {
//		}
//		// speech.startListening(recognizerIntent);
//
//		// progressBar.setVisibility(View.VISIBLE);
//		// progressBar.setIndeterminate(true);
//		if (is_take_input && tts.isSpeaking() == false) {
////			progressBar.setVisibility(View.VISIBLE);
////			recognizerIntent = get_recognized_intent();
//			speech.startListening(get_recognized_intent());
//		}
//	}
//	public Intent get_recognized_intent() {
//		speech = SpeechRecognizer.createSpeechRecognizer(this);
//		speech.setRecognitionListener(MenuForm.this);
//		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "us");
//		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
//		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
//		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
//		// progressBar.setVisibility(View.INVISIBLE);
//		return recognizerIntent;
//	}

	public void onReadyForSpeech(Bundle bundle) {
//		Toast.makeText(getApplicationContext(), "onready", Toast.LENGTH_LONG).show();
	}

	public void onBeginningOfSpeech() {
//		Toast.makeText(getApplicationContext(), "begining", Toast.LENGTH_LONG).show();
	}

	public void onRmsChanged(float v) {
//		Toast.makeText(getApplicationContext(), "rms", Toast.LENGTH_LONG).show();
	}

	public void onBufferReceived(byte[] bytes) {
//		Toast.makeText(getApplicationContext(), "buffer", Toast.LENGTH_LONG).show();
	}

	public void onEndOfSpeech() {
//		Toast.makeText(getApplicationContext(), "end", Toast.LENGTH_LONG).show();
	}

	public void onError(int i) {
//		Toast.makeText(getApplicationContext(), "on error "+i, Toast.LENGTH_LONG).show();
	}

	public void onResults(Bundle bundle) {
//		Toast.makeText(getApplicationContext(), "Found results",Toast.LENGTH_LONG).show();
		ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
		boolean is_show = false;
		boolean is_list = false;
		boolean is_indent = false;
		boolean is_create = false;
		String[] display_get = {"display", "show","get", "sorry", "shoe", "open", "all"};
		String[] display_list = {"list", "lost","let"};
		String[] create_list = {"case","cat", "car", "kids", "kase", "kese", "quiz","create", "file","add", "creature",
				"alcot", "cash", "kiss", "test", "alex", "guess","creative",
				"courier", "create", "theatre", "castrop","kodak", "clear",
				"chaotic","christopher","clear","connect", "degrees", "degree",
				"korea", "periodical", "cryptic",
				"theodore", "career", "scooter","editor","green", "spartacus"};
		String[] indents_list = {"voice", "in voice", "voice", "product","customer", "supplier"};
		String indent_keyword = "";

		for(String m:matches) {
			for (String key : create_list) {
				if (m.toLowerCase().contains(key.toLowerCase())) {
					is_create = true;
					break;
				}
			}
		}

		for(String m:matches) {
			for (String key : display_get) {
				if (m.toLowerCase().contains(key.toLowerCase())) {
					is_show = true;
					break;
				}
			}
		}
		for(String m:matches) {
			for (String key : display_list) {
				if (m.toLowerCase().contains(key.toLowerCase())) {
					is_list = true;
					break;
				}
			}
		}
		for(String m:matches) {
			for(String key: indents_list){
				if(m.toLowerCase().contains(key.toLowerCase())){
					is_indent= true;
					indent_keyword = key;
					break;
				}
			}
		}
		if(is_indent){
			if("invoice".contains(indent_keyword)){
				if(is_create){
					startActivity(new Intent(MenuForm.this, InvoiceCreation.class));
				}
				else if(is_show || is_list){
					startActivity(new Intent(MenuForm.this, InvoiceList.class));
				}
			}
			else if("products".contains(indent_keyword)){
				if(is_create){
					startActivity(new Intent(MenuForm.this, ProductMaster.class));
				}
				else if(is_show || is_list){
					startActivity(new Intent(MenuForm.this, ProductList.class));
				}
			}
			else if("customers".contains(indent_keyword)){
				if(is_create){
					startActivity(new Intent(MenuForm.this, CustomerMaster.class));
				}
				else if(is_show || is_list){
					startActivity(new Intent(MenuForm.this, CustomerList.class));
				}
			}
//			else if("suppliers".contains(indent_keyword)){
//				if(is_create){
//					startActivity(new Intent(MenuForm.this, Suppl.class));
//				}
//				else if(is_show || is_list){
//					startActivity(new Intent(MenuForm.this, CustomerList.class));
//				}
//			}


		}
//		Toast.makeText(getApplicationContext(), "-> is_show: "+is_show+"\n is_list: "+is_list, Toast.LENGTH_SHORT).show();
	}

	public void onPartialResults(Bundle bundle) {
//		Toast.makeText(getApplicationContext(), "partial", Toast.LENGTH_LONG).show();
	}

	public void onEvent(int i, Bundle bundle) {
		Toast.makeText(getApplicationContext(), "event", Toast.LENGTH_LONG).show();

	}
}
