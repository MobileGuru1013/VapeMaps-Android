package com.zkaren.springhappenings;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class AdminActivity extends  TabActivity implements OnTabChangeListener {

	static TabHost tabHost ;
	public static int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		tabHost = getTabHost();
        tabHost.setOnTabChangedListener(this);
        setTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
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

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}
	private void setTabs()
	{
//		addTab("", R.drawable.tab_notifications, AdminNotifications.class);
		addTab("Notifications", R.drawable.tab_notifications, AdminNotifications.class);
		addTab("Post Banner", R.drawable.tab_postbanner, PostBanner.class);
		addTab("Post News", R.drawable.tab_postnews, PostNews.class);
		addTab("Users", R.drawable.tab_users, 	UsersActivity.class);
		addTab("Settings", R.drawable.tab_settings, 	AdminSettings.class);
//		addTab("", R.drawable.tab_lost, LossPetsActivity.class);
//		addTab("", R.drawable.tab_found, FoundPetsActivity.class);
//		addTab("", R.drawable.tab_reunion, ReuniousActivity.class);
//		addTab("", R.drawable.tab_map, MapActivity.class);
//		addTab("", R.drawable.tab_about, AboutActivity.class);
	}
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}

}
