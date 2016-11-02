package com.zkaren.springhappenings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.plus.Plus;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

public class UserSettings extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 10;
	ToggleButton but1, but2, but3, but4, but5;
	SharedPreferences pref;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
	void init()
	{
//		boolean value1 = pref.getBoolean(Constant.LOCAL_NEWS, false);
		but1.setChecked(pref.getBoolean(Constant.LOCAL_NEWS, true));
		but2.setChecked(pref.getBoolean(Constant.TRAFFIC, true));
		but3.setChecked(pref.getBoolean(Constant.BREAKING, true));
		but4.setChecked(pref.getBoolean(Constant.WEATHER, true));
		but5.setChecked(pref.getBoolean(Constant.PUSH, true));
		Picasso.with(this).load(user.getParseFile(Constant.PHOTO).getUrl()).error(R.drawable.default_image).into(imageView);
		txt.setText("Welcome " + user.getString(Constant.FIRST_NAME) + " " + user.getString(Constant.LAST_NAME));
//		but5.setChecked(pref.getBoolean(Constant.TRAFFIC, false));
	}
	ParseUser user;
	CircleImageView imageView;
	TextView txt;
    private GoogleApiClient mGoogleApiClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_settings);
		user = MainActivity.user;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();

		pref = getSharedPreferences(Constant.prefName, 0);
		but1 = (ToggleButton)findViewById(R.id.toggleButton1);
		but2 = (ToggleButton)findViewById(R.id.toggleButton2);
		but3 = (ToggleButton)findViewById(R.id.toggleButton3);
		but4 = (ToggleButton)findViewById(R.id.toggleButton4);
		but5 = (ToggleButton)findViewById(R.id.toggleButton5);
		txt = (TextView) findViewById(R.id.textView3);
		imageView = (CircleImageView)findViewById(R.id.imageView1);
		if (user != null) init();
		but1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				edit.putBoolean(Constant.LOCAL_NEWS, isChecked);
				edit.commit();
			}
		});
		but2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				edit.putBoolean(Constant.TRAFFIC, isChecked);
				edit.commit();
			}
		});
		but3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				edit.putBoolean(Constant.BREAKING, isChecked);
				edit.commit();
			}
		});
		but4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				edit.putBoolean(Constant.WEATHER, isChecked);
				edit.commit();
			}
		});
		but5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Editor edit = pref.edit();
				edit.putBoolean(Constant.LOCAL_NEWS, isChecked);
				edit.commit();
				ParseInstallation install = ParseInstallation.getCurrentInstallation();
				install.put("flag", isChecked);
				install.saveInBackground();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_settings, menu);
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
//        if (!mGoogleApiClient.isConnecting())
//        {
//            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
//            mGoogleApiClient.connect();
////            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//
//        }
        signOutFromGplus();
//		if (ki > 0) MainActivity.user.deleteInBackground();
			ParseUser.logOut();
        Editor edit = pref.edit();
        edit.putInt(Constant.LOGIN, 0);
        edit.commit();
//		pref.edit().clear();
		Intent in = new Intent(this, LoginActivity.class);
		startActivity(in);
//        in.putExtra(Constant.LOGOUT, "Y");
		finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
//                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
//        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }
    private void signOutFromGplus() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
//            updateUI(false);
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

//            if (mSignInClicked) {
//                // The user has already clicked 'sign-in' so we attempt to
//                // resolve all
//                // errors until the user is signed in, or they cancel.
//                resolveSignInError();
//            }
        }
    }
}
