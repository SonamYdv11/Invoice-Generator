package fotl.vyaapar.acq;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String iconTitle = "";
        int id = item.getItemId();
        try {
            iconTitle = item.getTitle().toString().toLowerCase();
            //if(iconTitle.length() > 2){/*Toast.makeText(getApplicationContext(), iconTitle, Toast.LENGTH_SHORT).show();*/}
        } catch (Exception e) {}
//        Toast.makeText(getApplicationContext(), ">"+iconTitle, Toast.LENGTH_SHORT).show();

        if ("create invoice".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), InvoiceCreation.class));
            finish();
        }else if ("create products".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), ProductMaster.class));
            finish();
        }else if ("reports".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), Folders.class));
            finish();
        }
        else if (id == R.id.login) {
            Toast.makeText(getApplicationContext()," You have successfully Logged out ",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getBaseContext(), Login.class));
            finish();
        }
        else if ("create customer".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), CustomerMaster.class));
            finish();
        }else if ("create company".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), Company.class));
            finish();
        }
        else if ("about us".equals(iconTitle)) {
            startActivity(new Intent(getBaseContext(), AboutUS.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setElevation(20);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01569a")));

        actionBar.setCustomView(R.layout.actionbar);

        actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getBaseContext(), MenuForm2.class));
            }
        });

    }

}
