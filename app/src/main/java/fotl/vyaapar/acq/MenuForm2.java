package fotl.vyaapar.acq;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fotl.vyaapar.acq.mainscreencatalog.Catalog;
import fotl.vyaapar.acq.mainscreencatalog.MainScreenCatalogAdapter;

public class MenuForm2 extends BaseActivity {

    private ImageView btnsetting,btnfolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen3);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //*********
        LinearLayout ll = findViewById(R.id.linearLayout44);
        ll.setVisibility(View.GONE);
        //*********

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
        text.setText(Html.fromHtml("    Version 1.1 Copy right@2011 All rights Reserved  &nbsp;Developed by <a href=https://www.linkedin.com/in/prabhakar-gadupudi-a7095227/> Jk Designs Solutions Pvt Ltd </a>"));

        GridView catalogsGrid = findViewById(R.id.gvCatalogGrid);
        catalogsGrid.setNumColumns(2);
        catalogsGrid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        ArrayList<Catalog> catalogs = new ArrayList<Catalog>();
        catalogs.add(new Catalog("Invoices"));
        catalogs.add(new Catalog("Products"));
        catalogs.add(new Catalog("Company"));
        catalogs.add(new Catalog("Customer"));

        catalogsGrid.setAdapter(new MainScreenCatalogAdapter(catalogs, MenuForm2.this));

        final ArrayList<Catalog> dropDownMenuItems = new ArrayList<>();
        dropDownMenuItems.add(new Catalog("Home"));
        dropDownMenuItems.add(new Catalog("Invoice"));
        dropDownMenuItems.add(new Catalog("Masters"));
        dropDownMenuItems.add(new Catalog("Lists"));

        btnsetting = findViewById(R.id.btnsetting);
        btnfolders = findViewById(R.id.btnfolders);

        btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuForm2.this, Company.class));
            }
        });

        btnfolders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuForm2.this, Folders.class));
            }
        });
    }
}
