package voice.text;

import java.util.ArrayList;




import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TextAdapter extends ArrayAdapter<String> {

	private LayoutInflater mInflater;
	
	private ArrayList<String> mStrings;
	private int mViewResourceId;
	
	public TextAdapter(Context context, int viewResourceId,
			ArrayList<String> objects) {
		super(context, viewResourceId, objects);
		// TODO Auto-generated constructor stub
		mInflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		
		mStrings = objects;
		mViewResourceId = viewResourceId;
		
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public String getItem(int position) {
		return mStrings.get(position);
	}
	@Override
	public long getItemId(int position) {
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		
		TextView tv=(TextView)convertView.findViewById(R.id.listText);
		tv.setText(mStrings.get(position));
		return convertView;
	}

}
