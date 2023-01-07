package fotl.vyaapar.acq;

import android.app.ActionBar;
import android.app.ListActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;

public class BaseListActivity extends ListActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(20);
        actionBar.setIcon(R.drawable.header2);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01569a")));
        actionBar.setDisplayShowTitleEnabled(false);
    }

}
