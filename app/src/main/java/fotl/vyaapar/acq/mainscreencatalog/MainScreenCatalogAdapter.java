package fotl.vyaapar.acq.mainscreencatalog;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import fotl.vyaapar.acq.CompanyContact;
import fotl.vyaapar.acq.CustomerMaster;
import fotl.vyaapar.acq.InvoiceCreation;
import fotl.vyaapar.acq.ProductMaster;
import fotl.vyaapar.acq.R;

public class MainScreenCatalogAdapter implements ListAdapter {

    private ArrayList<Catalog> catalogs;
    private Context context;

    public MainScreenCatalogAdapter(ArrayList<Catalog> catalogs, Context context) {
        this.catalogs = catalogs;
        this.context = context;
    }

    class MyViewHolder
    {
        private View itemView;
        private ImageView ivCatalogImg;
        private TextView tvCatalogName;

        public MyViewHolder(View itemView, ImageView ivCatalogImg, TextView tvCatalogName) {
            this.itemView = itemView;
            this.ivCatalogImg = ivCatalogImg;
            this.tvCatalogName = tvCatalogName;
        }

        public void onItemClick(final int i)
        {
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(catalogs.get(i).getCatalogName().toLowerCase().equals("invoices"))
                        context.startActivity(new Intent(context, InvoiceCreation.class));
                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("products"))
                        context.startActivity(new Intent(context, ProductMaster.class));
                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("company"))
                        context.startActivity(new Intent(context, CompanyContact.class));
                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("customer"))
                        context.startActivity(new Intent(context, CustomerMaster.class));
//                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("supplier"))
//                        context.startActivity(new Intent(context, ProductList.class));
//                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("financials"))
//                        context.startActivity(new Intent(context, ProductMaster.class));
                    else if(catalogs.get(i).getCatalogName().toLowerCase().equals("help"))
                    {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        File file = new File("/sdcard/help.pdf");
                        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public boolean isEnabled(int i) {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    public int getCount() {

        return catalogs.size();
    }

    public Object getItem(int i) {

        return catalogs.get(i);
    }

    public long getItemId(int i) {
        return 0;
    }

    public boolean hasStableIds() {
        return false;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.catalogs2,viewGroup,false);
            view.setTag(new MyViewHolder(view,(ImageView) view.findViewById(R.id.ivCatalogImg),
                    (TextView) view.findViewById(R.id.tvCatalogName)));
        }
        MyViewHolder viewHolder = (MyViewHolder) view.getTag();
        viewHolder.tvCatalogName.setText(catalogs.get(i).getCatalogName());

        viewHolder.onItemClick(i);
        if(catalogs.get(i).getCatalogName().toLowerCase().equals("invoices"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.invoices2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("products"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.product2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("company"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.company2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("customer"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.customer2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("supplier"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.supplier2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("financials"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.financials2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("help"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.btnhelp2);
        else if(catalogs.get(i).getCatalogName().toLowerCase().equals("settings"))
            viewHolder.ivCatalogImg.setImageResource(R.drawable.setting2);
        else
            viewHolder.ivCatalogImg.setImageResource(R.drawable.btnhelp2);

        return view;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {

        if(catalogs.isEmpty())
            return true;
        else
            return false;
    }
}
