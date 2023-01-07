/** @preserve
 * ==================================================================== 
 * Designed and developed by 2011 Jk Designs Solutions Pvt Ltd,India,
 * Elite Projects Inc, USA
 * Phone:9866717777 
 * Email :Admin@eliteprojects.com
 * ====================================================================
 */

package fotl.vyaapar.acq;

import static fotl.vyaapar.acq.Constant.FIRST_COLUMN;
import static fotl.vyaapar.acq.Constant.SECOND_COLUMN;
import static fotl.vyaapar.acq.Constant.THIRD_COLUMN;
import static fotl.vyaapar.acq.Constant.FOURTH_COLUMN;
import static fotl.vyaapar.acq.Constant.FIFTH_COLUMN;
import static fotl.vyaapar.acq.Constant.SIXTH_COLUMN;
import static fotl.vyaapar.acq.Constant.ZEROTH_COLUMN;
import static fotl.vyaapar.acq.Constant.LAST_COLUMN;
import static fotl.vyaapar.acq.Constant.Image_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public final class listviewAdapter extends BaseAdapter
{
	public ArrayList<HashMap<String,String>> list;
	Activity activity;
	
	public listviewAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
		super();
		this.activity = activity;
		this.list = list;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}


	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}


	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class ViewHolder {
	       TextView txtFirst;
	       TextView txtSecond;
	       TextView txtThird;
	       TextView txtFourth;
	       TextView txtFifth;
	       TextView txtSixth;
	       TextView txtZero;
	       TextView txtLast;
	       ImageView imgProduct;
	  }

	public static Bitmap getImageFromBLOB(byte[] mBlob) {
		byte[] bb = mBlob;
		return BitmapFactory.decodeByteArray(bb, 0, bb.length);
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
				ViewHolder holder;
				LayoutInflater inflater =  activity.getLayoutInflater();

				if (convertView == null)
				{
					convertView = inflater.inflate(R.layout.listviewrow, null);
					holder = new ViewHolder();
					holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
					holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
					holder.txtThird = (TextView) convertView.findViewById(R.id.ThirdText);
					holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
					holder.txtFifth = (TextView)convertView.findViewById(R.id.FifthText);
					holder.txtSixth = (TextView)convertView.findViewById(R.id.SixthText);
					holder.txtZero = (TextView)convertView.findViewById(R.id.ZeroText);
					holder.txtLast = (TextView)convertView.findViewById(R.id.LastText);
					holder.imgProduct = (ImageView)convertView.findViewById(R.id.imageView1);
					convertView.setTag(holder);
				}
				else
				{
					holder = (ViewHolder) convertView.getTag();
				}  

				HashMap<String, String> map = list.get(position);
				holder.txtFirst.setText(map.get(FIRST_COLUMN));
				holder.txtSecond.setText(map.get(SECOND_COLUMN));
				holder.txtFifth.setText(map.get(FIFTH_COLUMN));
				holder.txtThird.setText(map.get(THIRD_COLUMN));
				holder.txtFourth.setText(map.get(FOURTH_COLUMN));
				
				holder.txtSixth.setText(map.get(SIXTH_COLUMN));
				holder.txtZero.setText(map.get(ZEROTH_COLUMN));
				holder.txtLast.setText(map.get(LAST_COLUMN));
				
				byte[] strImgs = getImage(map.get(SECOND_COLUMN) );
				String strImg = map.get(Image_COLUMN);
				try
				{
				if(strImgs!= null)
					holder.imgProduct.setImageBitmap(getImageFromBLOB(strImgs));
				}
				catch (Exception e) {
					// TODO: handle exception
				}

			return convertView;
	}

	public byte[] getImage(String PCode)
	{
		byte[] strImg = null;		
		try
		{
			
			DbVyaapar d = new DbVyaapar(activity.getApplicationContext());
			Cursor c = d.getProdcutImage(PCode);
			if(c!= null)
			{
				if (c.moveToFirst()) {
					do {
						if(!c.isNull(c.getColumnIndex("image")))
							strImg = c.getBlob(c.getColumnIndex("image"));
						//Toast.makeText(activity.getApplicationContext(),"  fotl got image in adapter "+strImg + "Tested ......."  , Toast.LENGTH_SHORT).show();
					} while (c.moveToNext());
				}c.close();				
			}
			d.closeConnection();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		return strImg;
	}
}
