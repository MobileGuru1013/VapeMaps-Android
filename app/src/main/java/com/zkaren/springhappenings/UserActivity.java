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
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class UserActivity extends TabActivity implements OnTabChangeListener {

	static TabHost tabHost ;
	public static int index = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
		tabHost = getTabHost();
//		 tabHost = getTabHost();
	        tabHost.setOnTabChangedListener(this);
		setTabs();
	}
	public static void ChangeTab(int curIndex)
	{
		tabHost.setCurrentTab(curIndex);
		index = curIndex;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user, menu);
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
	public void OnNotification(View v)
	{
		Intent in = new Intent(UserActivity.this, AdminNotifications.class);
		startActivity(in);
		finish();
	}
	public void OnSubmit(View v)
	{
		Intent in = new Intent(UserActivity.this, SubmitActivity.class);
		startActivity(in);
		finish();
	}
	public void OnUserSettings(View v)
	{
		
	}
	private void setTabs()
	{
		addTab("Notifications", R.drawable.tab_notifications, AdminNotifications.class);
		addTab("Submit Tip", R.drawable.tab_submit, SubmitActivity.class);
		addTab("Settings", R.drawable.tab_settings, 	UserSettings.class);
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
	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		
	}
	
}
