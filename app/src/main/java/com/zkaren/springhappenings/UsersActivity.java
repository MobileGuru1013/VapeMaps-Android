package com.zkaren.springhappenings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends Activity {

	private List<ApplicationInfo> mAppList;
	private AppAdapter mAdapter;
	SwipeMenuListView listView ;
	private ArrayList<ParseUser> dataList;
	private ParseQuery<ParseUser> query;
	
	void loadData()
	{
	
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				
				if (arg1 == null)
				{
					dataList = new ArrayList<ParseUser>();
					for (ParseUser po : arg0)
					{
						String string = pref.getString(po.getObjectId(), "N");
						if (string.equals("N"))
							dataList.add(po);
					}
					mAdapter.notifyDataSetChanged();
					listView.onRefreshComplete();
				}
			}
		});
	}
	SharedPreferences pref;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users);
		pref = getSharedPreferences(Constant.prefName, 0);
		query = ParseUser.getQuery();
		query.orderByDescending("createdAt");
		dataList = new ArrayList<ParseUser>();
		loadData();

		listView = (SwipeMenuListView) findViewById(R.id.listView);
		mAdapter = new AppAdapter();
		listView.setAdapter(mAdapter);
		listView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				loadData();
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public ParseUser getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public int getViewTypeCount() {
			// menu type count
			return 1;
		}
		
		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.list_users_low, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ParseUser po = getItem(position);
//			ParseFile pf = po.getParseFile("news_image");
			int imgID = 0;
			Date date = po.getCreatedAt();
			String str = Util.getDifferenceDate(date);
			ParseFile pf = po.getParseFile(Constant.PHOTO);
			
//			holder.iv_icon.setImageResource(imgID);
			holder.tv_title.setText(po.getString(Constant.FIRST_NAME) + " " + po.getString(Constant.LAST_NAME));
			holder.tv_name.setText(po.getEmail());
            if (pf != null)
			    Picasso.with(UsersActivity.this).load(pf.getUrl()).error(R.drawable.default_image).into(holder.iv_icon);
            else
                Picasso.with(UsersActivity.this).load(R.drawable.default_image).into(holder.iv_icon);
			
			return convertView;
		}
		
		class ViewHolder {
			CircleImageView iv_icon;
			TextView tv_name;
			TextView tv_title;
			TextView tv_contents;
			TextView tv_time;
			public ViewHolder(View view) {
				iv_icon = (CircleImageView) view.findViewById(R.id.imageView1);
				tv_name = (TextView) view.findViewById(R.id.textView1);
				tv_title = (TextView) view.findViewById(R.id.textView2);

				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
