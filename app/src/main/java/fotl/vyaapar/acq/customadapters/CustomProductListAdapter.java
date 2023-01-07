package fotl.vyaapar.acq.customadapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import fotl.vyaapar.acq.CustomerMaster;
import fotl.vyaapar.acq.ProductList;
import fotl.vyaapar.acq.ProductMaster;

public class CustomProductListAdapter extends ArrayAdapter<String> {

    private List<String> list2;
    private AppCompatActivity activity;

    public CustomProductListAdapter(AppCompatActivity activity, int resource, int textViewResourceId, List<String> objects) {
        super(activity, resource, textViewResourceId, objects);

        list2 = objects;
        this.activity = activity;

    }

    @android.support.annotation.NonNull
    @Override
    public View getView(int position, @android.support.annotation.NonNull View convertView, @android.support.annotation.NonNull ViewGroup parent) {

        View view = super.getView(position, convertView, parent);

        final int i = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(this, "Item Clicked: " + list2.get(position) ,Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(activity, ProductMaster.class );
                intent1.putExtra("returnKey1",list2.get(i));
                activity.startActivity(intent1);
            }
        });
        view.setTag(position);
        activity.registerForContextMenu(view);

        return view;
    }
}
