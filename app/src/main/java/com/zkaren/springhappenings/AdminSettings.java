package com.zkaren.springhappenings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import de.hdodenhof.circleimageview.CircleImageView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class AdminSettings extends Activity implements GoogleApiClient.ConnectionCallbacks{

	ParseUser user;
    CircleImageView imageView;
	TextView txt;

	ToggleButton but1, but2, but3, but4, but5;
	SharedPreferences pref;
    private GoogleApiClient mGoogleApiClient;
	void init() {
		// boolean value1 = pref.getBoolean(Constant.LOCAL_NEWS, false);
		but5.setChecked(pref.getBoolean(Constant.PUSH, true));
//		Log.d("tag", user.getParseFile(Constant.PHOTO).getUrl());
        ParseFile pf = user.getParseFile(Constant.PHOTO);
        if (pf != null) {
            Picasso.with(this).load(pf.getUrl())
                    .error(R.drawable.default_image).into(imageView);
        }
        else
        {
            Picasso.with(this).load(R.drawable.default_image)
                    .error(R.drawable.default_image).into(imageView);
        }
		txt.setText("Welcome " + user.getString(Constant.FIRST_NAME) + " "
				+ user.getString(Constant.LAST_NAME));
		// but5.setChecked(pref.getBoolean(Constant.TRAFFIC, false));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin_settings);
		user = MainActivity.user;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .build();
        if (mGoogleApiClient.isConnected())
        {
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
        }
        pref = getSharedPreferences(Constant.prefName, 0);
		but5 = (ToggleButton) findViewById(R.id.toggleButton5);
		txt = (TextView) findViewById(R.id.textView3);
		imageView = (CircleImageView) findViewById(R.id.imageView1);
		init();

		but5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean(Constant.PUSH, isChecked);
                edit.commit();

				ParseInstallation install = ParseInstallation
						.getCurrentInstallation();
				install.put("flag", isChecked);
				install.saveInBackground();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin_settings, menu);
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

	public void OnLogout(View v) {
		int ki = pref.getInt(Constant.SPECIAL, 0);
		if (ki > 0) MainActivity.user.deleteInBackground();
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(Constant.LOGIN, 0);
        edit.commit();
		ParseUser.logOut();
		pref.edit().clear();
		Intent in = new Intent(this, LoginActivity.class);
		startActivity(in);
		finish();

	}

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
