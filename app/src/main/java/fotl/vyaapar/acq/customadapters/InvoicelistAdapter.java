/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq.customadapters;

import static fotl.vyaapar.acq.Constant.Customer_COLUMN;
import static fotl.vyaapar.acq.Constant.Date_COLUMN;
import static fotl.vyaapar.acq.Constant.Amount_COLUMN;
import static fotl.vyaapar.acq.Constant.Status_COLUMN;
import static fotl.vyaapar.acq.Constant.Number_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import fotl.vyaapar.acq.R;

public final class InvoicelistAdapter extends BaseAdapter
{
	public ArrayList<HashMap<String,String>> Invoicelist;
	Activity activity;
	
	public InvoicelistAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
		super();
		this.activity = activity;
		this.Invoicelist = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return Invoicelist.size();
	}


	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Invoicelist.get(position);
	}


	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
	       TextView txtInvoiceCustomer;
	       TextView txtInvoiceDate;
	       TextView txtInvoiceStatus;
	       TextView txtInvoiceAmount;	 
	       TextView txtInvoiceNumber;
	       ImageView imgProdcut;
	       
	  }

	  
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
				ViewHolder holder;
				LayoutInflater inflater =  activity.getLayoutInflater();

				if (convertView == null)
				{
					convertView = inflater.inflate(R.layout.invoicelistviewrow , null);
					holder = new ViewHolder();
					holder.txtInvoiceCustomer = (TextView) convertView.findViewById(R.id.InvoiceCustomer);
					holder.txtInvoiceAmount = (TextView) convertView.findViewById(R.id.InvoiceAmounts);
					holder.txtInvoiceStatus = (TextView) convertView.findViewById(R.id.InvoiceStatus);
					holder.txtInvoiceDate = (TextView) convertView.findViewById(R.id.InvoiceDate);
					holder.txtInvoiceNumber = (TextView) convertView.findViewById(R.id.InvoiceNumber);
					convertView.setTag(holder);
				}
				else
				{
					holder = (ViewHolder) convertView.getTag();
				}  

				HashMap<String, String> map = Invoicelist.get(position);
				holder.txtInvoiceCustomer.setText(map.get(Customer_COLUMN));
				holder.txtInvoiceDate.setText(map.get(Date_COLUMN));
				holder.txtInvoiceAmount.setText(map.get(Amount_COLUMN));
				holder.txtInvoiceStatus.setText(map.get(Status_COLUMN));
				holder.txtInvoiceNumber.setText(map.get(Number_COLUMN));
				

			return convertView;
	}

}


