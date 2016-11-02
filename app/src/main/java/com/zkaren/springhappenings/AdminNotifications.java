package com.zkaren.springhappenings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.baoyz.swipemenulistview.SwipeMenuListView.OnMenuItemClickListener;
import com.costum.android.widget.PullToRefreshListView.OnRefreshListener;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class AdminNotifications extends FragmentActivity {

	private List<ApplicationInfo> mAppList;
	private AppAdapter mAdapter;
	SwipeMenuListView listView ;
	private ArrayList<ParseObject> dataList;
	private ParseQuery<ParseObject> query;
	
	void loadData()
	{
		
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> arg0, ParseException arg1) {
				// TODO Auto-generated method stub
				
				if (arg1 == null)
				{
					dataList = new ArrayList<ParseObject>();
					for (ParseObject po : arg0)
					{
						String string = pref.getString(po.getObjectId(), "N");
						if (string.equals("N"))
						{
							String str = po.getString(Constant.CATEGORY);
							boolean val = pref.getBoolean(str, true);
							Log.d("balue", "" + val);
							if (val == true)
								dataList.add(po);
						}
					}
					mAdapter.notifyDataSetChanged();
					listView.onRefreshComplete();
//					mAdapter = new AppAdapter();
//					listView.setAdapter(mAdapter);
				}
			}
		});
	}
	SharedPreferences pref;
    public static FragmentManager fragmentManager;
    ImageView imageView;
    int index;
    int count;
    Handler handler = new Handler();
    void setImage()
    {
        ParseFile pf = null;
        if (index == count) index = 0;
//        if ()
        pf =  MainActivity.advImages.get(index).getParseFile(Constant.AD_IMAGE);
        Picasso.with(this).load(pf.getUrl()).into(imageView);
        index++;
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setImage();
        }
    };
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_notification);
		pref = getSharedPreferences(Constant.prefName, 0);
		query = new ParseQuery<ParseObject>("post_new");
        imageView = (ImageView) findViewById(R.id.imageView);
		query.orderByDescending("createdAt");
		dataList = new ArrayList<ParseObject>();
        count = MainActivity.advImages.size();
        handler.postAtTime(runnable, 1000);
		loadData();

//		dataList = new ArrayList<ParseObject>();
//		mAppList = getPackageManager().getInstalledApplications(0);

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
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// Create different menus depending on the view type
				switch (menu.getViewType()) {
				case 0:
					createMenu1(menu);
					break;
				case 1:
					createMenu2(menu);
					break;
//				case 2:
//					createMenu3(menu);
//					break;
				}
			}
			
			private void createMenu1(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(
						getApplicationContext());
				item1.setBackground(new ColorDrawable(getResources().getColor(R.color.baseColor)));
				item1.setWidth(dp2px(90));
//				item1.setIcon(R.drawable.ic_action_favorite);
				item1.setTitle("read full story");
				item1.setTitleSize(12);
				item1.setTitleColor(Color.WHITE);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
				item2.setWidth(dp2px(90));
				item2.setTitle("delete");
				item2.setTitleSize(12);
				item2.setTitleColor(Color.WHITE);
//				item2.setIcon(R.drawable.ic_action_good);
				menu.addMenuItem(item2);
			}
			
			private void createMenu2(SwipeMenu menu) {
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(getResources().getColor(R.color.red)));
				item2.setWidth(dp2px(90));
				item2.setTitle("delete");
				item2.setTitleSize(12);
				item2.setTitleColor(Color.WHITE);
//				item2.setIcon(R.drawable.ic_action_good);
				menu.addMenuItem(item2);
			}
			
			private void createMenu3(SwipeMenu menu) {
				SwipeMenuItem item1 = new SwipeMenuItem(
						getApplicationContext());
				item1.setBackground(new ColorDrawable(Color.rgb(0x30, 0xB1,
						0xF5)));
				item1.setWidth(dp2px(90));
				item1.setIcon(R.drawable.ic_action_about);
				menu.addMenuItem(item1);
				SwipeMenuItem item2 = new SwipeMenuItem(
						getApplicationContext());
				item2.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
						0xCE)));
				item2.setWidth(dp2px(90));
				item2.setIcon(R.drawable.ic_action_share);
				menu.addMenuItem(item2);
			}
		};
		// set creator
		listView.setMenuCreator(creator);

		// step 2. listener item click event
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
				// TODO Auto-generated method stub
				ParseObject po = dataList.get(position - 1);
                MoreInformation.timeStr = Util.getDifferenceDate(po.getCreatedAt());
                MoreInformation.contents = po.getString(Constant.CONTENTS);
                MoreInformation.title = po.getString(Constant.TITLE);
                ParseFile pf = po.getParseFile(Constant.NEWS_IMAGE);
                if (pf == null) MoreInformation.photoURL = null;
                else  MoreInformation.photoURL = pf.getUrl();
                MoreInformation.geoPoint = po.getParseGeoPoint(Constant.ADDRESS);
//                else MoreInformation.photoURL = pf.getUrl();
                MoreInformation.url = po.getString(Constant.URL);
                String url = MoreInformation.url;
//                if (pf == null)
//                Toast.makeText(AdminNotifications.this, MoreInformation.photoURL + " " + position, Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(AdminNotifications.this, "No NULL " + position, Toast.LENGTH_SHORT).show();
                if (url.equals("") || url == null) {
                    Intent in = new Intent(AdminNotifications.this, MoreInformation.class);
                    startActivity(in);
                }
                else
                {

                    Intent in = new Intent(AdminNotifications.this, WebViewActivity.class);
                    in.putExtra(Constant.URL, url);

                    startActivity(in);
                }
			}
		});
		listView.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
				ParseObject item = dataList.get(position);
				String url = item.getString(Constant.URL);
				switch (index) {
				case 0:
					// open
					if (url.equals(""))
					{
                        int kind = pref.getInt(Constant.LOGIN, 0);
                        if (kind == 1)
                        {
                            item.deleteInBackground();
                        }
                        else {
                            Editor edit = pref.edit();
                            edit.putString(item.getObjectId(), "Y");
                            edit.commit();
                        }
						dataList.remove(position);
						mAdapter.notifyDataSetChanged();
						break;
					}
					else
					{
						Intent in = new Intent(AdminNotifications.this, DetailActivity.class);
						in.putExtra(Constant.URL, item.getString(Constant.URL));
						startActivity(in);
					}
					break;
				case 1:
					// delete
//					delete(item);
                    int kind = pref.getInt(Constant.LOGIN, 0);
                    if (kind == 1)
                        item.deleteInBackground();
                    else {
                        Editor edit = pref.edit();
                        edit.putString(item.getObjectId(), "Y");
                        edit.commit();
                    }
					dataList.remove(position);
					mAdapter.notifyDataSetChanged();
					break;
				}
				return false;
			}
		});

	}

	class AppAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public ParseObject getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public int getViewTypeCount() {
			// menu type count
			return 2;
		}
		
		@Override
		public int getItemViewType(int position) {
			// current menu type
//			return position % 3;
			ParseObject po = getItem(position);
			String url = po.getString(Constant.URL);
			if (url.equals(""))
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(),
						R.layout.item_list_app, null);
				new ViewHolder(convertView);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			ParseObject po = getItem(position);
//			ParseFile pf = po.getParseFile("news_image");
			String category = po.getString(Constant.CATEGORY);
			int imgID = 0;
			Date date = po.getCreatedAt();
			String str = Util.getDifferenceDate(date);
			
			if (category.equals(Constant.BREAKING))
			{
				imgID = R.drawable.breaking_news;
			}
			else if (category.equals(Constant.FIRE)) imgID = R.drawable.fire;
			else if (category.equals(Constant.WEATHER)) imgID = R.drawable.weather;
			else if (category.equals(Constant.LOCAL_NEWS)) imgID = R.drawable.local_news;
			else if (category.equals(Constant.TRAFFIC)) imgID = R.drawable.traffic_alert;
			holder.iv_icon.setImageResource(imgID);
//			holder.tv_contents.setText(po.getString(Constant.CONTENTS));
            holder.tv_contents.setText("");
			holder.tv_name.setText(po.getString(Constant.CATEGORY));
			holder.tv_title.setText(po.getString(Constant.TITLE));
			holder.tv_time.setText(str);
			return convertView;
		}
		
		class ViewHolder {
			ImageView iv_icon;
			TextView tv_name;
			TextView tv_title;
			TextView tv_contents;
			TextView tv_time;
			public ViewHolder(View view) {
				iv_icon = (ImageView) view.findViewById(R.id.imageView1);
				tv_name = (TextView) view.findViewById(R.id.textView1);
				tv_title = (TextView) view.findViewById(R.id.textView2);
				tv_contents = (TextView) view.findViewById(R.id.textView3);
				tv_time = (TextView) view.findViewById(R.id.textView4);
				view.setTag(this);
			}
		}
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}
}
