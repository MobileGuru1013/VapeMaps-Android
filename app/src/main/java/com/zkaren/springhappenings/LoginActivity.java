package com.zkaren.springhappenings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.G
//import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.parse.GetDataCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseInstallation;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

	EditText edit1, edit2;
	String email, password;
	ParseQuery<ParseUser> query;
	ProgressDialog progress;
	SharedPreferences pref;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    SignInButton button;
    boolean mSignInClicked;
    private static final int PROFILE_PIC_SIZE = 400;
//    ImageView imgProfilePic;
    ConnectionResult mConnectionResult;
    String personPhotoUrl = "";
    private void resolveSignInError() {

//        Toast.makeText(this,   "Button", Toast.LENGTH_SHORT).show();
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
        button = (SignInButton)findViewById(R.id.sign_in_button);
//        imgProfilePic = (ImageView)findViewById(R.id.imageView);
//        imgProfilePic.setImageResource(R.drawable.ic_launcher);
        button.setOnClickListener(this);
        button.setSize(SignInButton.SIZE_WIDE);
//        button.set
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!mGoogleApiClient.isConnecting())
//                {
//                    signInWithGplus();
//                }
//            }
//        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
		edit1 = (EditText)findViewById(R.id.editText1);
		edit2 = (EditText)findViewById(R.id.editText2);
		pref = getSharedPreferences(Constant.prefName, 0);
		progress = new ProgressDialog(this);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setMessage("Signing...");
        progress.setTitle("SpringHappenings");

        if (getIntent().hasExtra(Constant.LOGOUT))
        {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
        }
//        if (mGoogleApiClient.isConnected()) {
//            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
//            mGoogleApiClient.disconnect();
//            mGoogleApiClient.connect();
////            updateUI(false);
//        }
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                mSignInClicked = false;
            }
//            Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
//        else if ()
        else
            ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
//        updateUI(false);
    }

    @Override
    public void onConnected(Bundle arg0) {
        mSignInClicked = false;
//        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();

        // Update the UI after signin
//        updateUI(true);

    }

    public void OnFacebook(View v)
    {
        List<String> permissions = Arrays.asList("email", "public_profile", "user_friends");
        progress.show();
        ParseFacebookUtils.logIn(permissions, this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {

                if (err == null) {
                    Toast.makeText(LoginActivity.this, "Facebook Success", Toast.LENGTH_SHORT).show();
                    if (user == null) {
                        if (progress.isShowing()) progress.dismiss();
                        Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                        Toast.makeText(LoginActivity.this, "Facebook fail", Toast.LENGTH_SHORT).show();
                    } else if (user.isNew()) {
//                        Toast.makeText(LoginActivity.this, "Facebook Success 1", Toast.LENGTH_SHORT).show();
                        getFaceBookGraphObject();
                    } else {

//                        Toast.makeText(LoginActivity.this, "Facebook Success 2", Toast.LENGTH_SHORT).show();
//                        showHomeListActivity();
                        boolean val = user.getBoolean("is_admin");
                        if (progress.isShowing()) progress.dismiss();;
                        ParseInstallation install = ParseInstallation.getCurrentInstallation();
                        install.put("is_admin", user.getBoolean("is_admin"));
                        install.put("InstallationUser", user);
                        install.saveInBackground();
                        MainActivity.user = user;
                        if (val == true)
                        {

                            Editor edit = pref.edit();
                            edit.putInt(Constant.LOGIN, 1);
                            edit.commit();
                            Intent in = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(in);
                            finish();
                        }
                        else
                        {
                            Editor edit = pref.edit();
                            edit.putInt(Constant.LOGIN, 2);
                            edit.commit();
                            Intent in = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(in);
                            finish();
                        }
                        Log.d("MyApp", "User logged in through Facebook!");
                    }
                }
                else
                {
//                    Toast.makeText(LoginActivity.this, "Facebook failed " + err.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    ProgressDialog progressDialog;
    private void showHomeListActivity()
    {
        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Spring Happenings");
//
//        progressDialog.setMessage("Signing...");
//        progressDialog.show();;
        //Log.i(AnypicApplication.TAG, "entered showHomeListActivity");
        Log.d("tag", "facebook");
        Session session =  ParseFacebookUtils.getSession();

        Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
            @Override
            public void onCompleted(final GraphUser guser, Response response) {
                if (guser != null) {
//                    Log.d("")
                    Log.d("LastName", guser.getLastName());
                    Log.d("firstname", guser.getFirstName());
                    Log.d("facebookId", guser.getId());
                    Log.d("displayName", "");
//                    Toast.makeText(LoginActivity.this, guser.getLastName() + " " +guser.getFirstName() + guser.getId(), Toast.LENGTH_SHORT).show();
                    AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>() {
                        protected Bitmap doInBackground(Void... p) {
                            Bitmap bm = null;
                            try {
                                URL aURL = new URL("https://graph.facebook.com/" + guser.getId() + "/picture?type=large");
                                Log.d("tag", guser.getId());
                                URLConnection conn = aURL.openConnection();
                                conn.setUseCaches(true);
                                conn.connect();
                                InputStream is = conn.getInputStream();
                                BufferedInputStream bis = new BufferedInputStream(is);
                                bm = BitmapFactory.decodeStream(bis);
                                bis.close();
                                is.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return bm;
                        }

                        protected void onPostExecute(Bitmap bm) {
                            byte[] data = Util.bmpToByteArray(bm, false);
                            ParseFile file = new ParseFile("ProfileImage.png", data);
                            try {
                                file.save();
                                MainActivity.user.put("photo", file);
                                MainActivity.user.saveInBackground();
                                Editor edit = pref.edit();
                                edit.putInt(Constant.SPECIAL, 1);
                                edit.commit();
                                if (progress.isShowing()) progress.dismiss();
                                if (pref.getInt(Constant.LOGIN, 2) == 1) {
                                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish(); // This closes the login screen so it's not on the back stack
                                }
                                else
                                {
                                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                if (progress.isShowing()) progress.dismiss();
//                                progressDialog.dismiss();
                            }

                        }
                    };
                    t.execute();
                }
            }
        });


    }

    public void getFaceBookGraphObject(){


//        final GPSTracker tracker = new GPSTracker(HomeActivity.this);
        Session session =  ParseFacebookUtils.getSession();

        Request request=Request.newMeRequest(session, new Request.GraphUserCallback() {

            @Override
            public void onCompleted(GraphUser user, Response response) {


                if(user != null){
                    Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    String facebookid=user.getId();
                    email=(String) user.getProperty("email");
//                    username="unknown";
//                    name=user.getFirstName();
                    ParseUser currentUser=ParseUser.getCurrentUser();
//                    currentUser.put("facebookId", facebookid);
//                    currentUser.put("firstname", name);
                    String firstName = user.getFirstName();
                    String lastName = user.getLastName();

                    currentUser.put("first_name", firstName);
                    currentUser.put("last_name", lastName);

                    currentUser.setEmail(email);
//                    Toast.makeText(LoginActivity.this, email + " " + firstName + " " + lastName , Toast.LENGTH_SHORT).show();
                    Log.d("user", firstName + " " + lastName);
                    Log.d("mail", email);
//                    Log.d("name", name);
                    try {
                        currentUser.save();
                        boolean value = false;
                        if (email.equals("zach@springhappenings.com")) {
                            currentUser.put("is_admin", true);

                            value = true;
                        } else {
                            currentUser.put("is_admin", false);

                            value = false;
                        }
                        Editor edit = pref.edit();
                        if (value == true) {
                            edit.putInt(Constant.LOGIN, 1);
                        } else {
                            edit.putInt(Constant.LOGIN, 2);
                        }
                        edit.commit();
                        ParseInstallation install = ParseInstallation.getCurrentInstallation();
                        install.put("is_admin", value);
                        install.put("InstallationUser", currentUser);
                        install.saveInBackground();
                        MainActivity.user = currentUser;
//                        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//                        installation.put("user", currentUser);
//                        installation.put("notification", "true");
                        install.saveInBackground();
                        showHomeListActivity();

                    }
                        catch (ParseException e) {
//                        e.printStackTrace();
//                          if (e)
//                            e.getCode() == ParseException.E
                          {
//                              ParseUser.logInInBackground(email, "", new LogInCallback() {
//                                  @Override
//                                  public void done(ParseUser parseUser, ParseException e) {
//                                      if (e == null)
//                                      {
//                                          boolean value = false;
//                                          if (email.equals("zach@springhappenings.com")) {
//                                              parseUser.put("is_admin", true);
//
//                                              value = true;
//                                          } else {
//                                              parseUser.put("is_admin", false);
//                                              value = false;
//                                          }
//                                          Editor edit = pref.edit();
//                                          if (value == true) {
//                                              edit.putInt(Constant.LOGIN, 1);
//                                          } else {
//                                              edit.putInt(Constant.LOGIN, 2);
//                                          }
//                                          edit.commit();
//                                          try {
//                                              parseUser.save();
//                                              ParseInstallation install = ParseInstallation.getCurrentInstallation();
//                                              install.put("is_admin", value);
//                                              install.put("InstallationUser", parseUser);
//                                              install.saveInBackground();
//                                              MainActivity.user = parseUser;
//                                              edit.putInt(Constant.SPECIAL, 1);
//                                              edit.commit();
//                                              if (progress.isShowing()) progress.dismiss();
//                                              if (value == true)
//                                              {
//                                                  Intent in = new Intent(LoginActivity.this, AdminActivity.class);
//                                                  startActivity(in);
//                                                  finish();
//                                              }
//                                              else
//                                              {
//                                                  Intent in = new Intent(LoginActivity.this, UserActivity.class);
//                                                  startActivity(in);
//                                                  finish();
//                                              }
//                                          } catch (ParseException e1) {
//                                              e1.printStackTrace();
////                            Toast.makeText(LoginActivity.this, e1.toString(), Toast.LENGTH_SHORT).show();
//                                          }
//                                      }
//                                      else
//                                          AppGlobal.showMsgDialog(LoginActivity.this, "Spring Happenings", "Google login failed", "OK");
//                                  }
//                              });
                              if (progress.isShowing()) progress.dismiss();
                              Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                          }
                        Log.d("tag", e.toString());
                    }

                }
                else
                {
                    if (progress.isShowing()) progress.dismiss();
                    Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }

        });
        request.executeAsync();
    }

    public void OnRegister(View v)
	{
		Intent in = new Intent(this, RegisterActivity.class);
		startActivity(in);
		finish();
	}
	public void OnLogin(View v)
	{
		String email = edit1.getText().toString();
		String password = edit2.getText().toString();
		if (email.equals(""))
		{
			AppGlobal.showMsgDialog(this, "SpringHappenings", "Username con't be empty!" , "OK");

			return;
		}
		if (password.equals(""))
		{
			AppGlobal.showMsgDialog(this, "SpringHappenings", "Password con't be empty!" , "OK");
			return;
		}
		
		if (Util.isValidEmail(email))
		{
			progress.show();
			ParseUser.logInInBackground(email, password, new LogInCallback() {
				
				@Override
				public void done(ParseUser user, ParseException arg1) {
					// TODO Auto-generated method stub
					
					if (progress.isShowing()) progress.dismiss();
					if (arg1 == null)
					{
						boolean val = user.getBoolean("is_admin");
	
						ParseInstallation install = ParseInstallation.getCurrentInstallation();
						install.put("is_admin", user.getBoolean("is_admin"));
						install.put("InstallationUser", user);
						install.saveInBackground();
						MainActivity.user = user;
						
						if (val == true)
						{
							Editor edit = pref.edit();
							edit.putInt(Constant.LOGIN, 1);
							edit.commit();
							Intent in = new Intent(LoginActivity.this, AdminActivity.class);
							startActivity(in);
							finish();
						}
						else
						{
							Editor edit = pref.edit();
							edit.putInt(Constant.LOGIN, 2);
							edit.commit();
							Intent in = new Intent(LoginActivity.this, UserActivity.class);
							startActivity(in);
							finish();
						}
					}
					else
					{
						AppGlobal.showMsgDialog(LoginActivity.this, "Spring Happenings", "This user doesn't exist.\n " +
								"Please register by clicking the button below." , "OK");
						return;
					}
				}
			});
		}
		else
		{
			AppGlobal.showMsgDialog(this, "SpringHappenings", "The E-mail format isn't validate" , "OK");
			return;
		}
			
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

    private void getProfileInformation() {

        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
//                Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();
                String first_name = currentPerson.getName().getGivenName();
                String last_name = currentPerson.getName().getFamilyName();
                personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                ParseUser user = new ParseUser();
                user.setUsername(email);
                user.setEmail(email);
                user.setPassword("");
                user.put(Constant.FIRST_NAME, first_name);
                user.put(Constant.LAST_NAME, last_name);
//                Toast.makeText(this, "Name: " + first_name + ", plusProfile: "
//                        + personGooglePlusProfile + ", email: " + email
//                        + ", Image: " + personPhotoUrl, Toast.LENGTH_LONG).show();
                Log.d("GOOGLE", "Name: " + first_name + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null)
                          new LoadProfileImage().execute(personPhotoUrl);
                        else
                        {
                            ParseUser.logInInBackground(email, "", new LogInCallback() {
                                @Override
                                public void done(ParseUser parseUser, ParseException e) {
                                    if (e == null)
                                    {
                                        boolean value = false;
                                        if (email.equals("zach@springhappenings.com")) {
                                            parseUser.put("is_admin", true);

                                            value = true;
                                        } else {
                                            parseUser.put("is_admin", false);
                                            value = false;
                                        }
                                        Editor edit = pref.edit();
                                        if (value == true) {
                                            edit.putInt(Constant.LOGIN, 1);
                                        } else {
                                            edit.putInt(Constant.LOGIN, 2);
                                        }
                                        edit.commit();
                                        try {
                                            parseUser.save();
                                            ParseInstallation install = ParseInstallation.getCurrentInstallation();
                                            install.put("is_admin", value);
                                            install.put("InstallationUser", parseUser);
                                            install.saveInBackground();
                                            MainActivity.user = parseUser;
                                            edit.putInt(Constant.SPECIAL, 1);
                                            edit.commit();
                                            if (progress.isShowing()) progress.dismiss();
                                            if (value == true)
                                            {
                                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
                                                startActivity(in);
                                                finish();
                                            }
                                            else
                                            {
                                                Intent in = new Intent(LoginActivity.this, UserActivity.class);
                                                startActivity(in);
                                                finish();
                                            }
                                        } catch (ParseException e1) {
                                            e1.printStackTrace();
//                            Toast.makeText(LoginActivity.this, e1.toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                        AppGlobal.showMsgDialog(LoginActivity.this, "Spring Happenings", "Google login failed", "OK");
                                }
                            });

                        }
                    }
                });



            } else {
                if (progress.isShowing()) progress.dismiss();
//                Toast.makeText(getApplicationContext(),
//                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button)
        {
            progress.show();
            signInWithGplus();
        }
    }

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage() {

//            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            final ParseUser user = ParseUser.getCurrentUser();
//            Toast.makeText(LoginActivity.this, "User", Toast.LENGTH_SHORT).show();
//            if (result == null) {
//                AppGlobal.showMsgDialog(LoginActivity.this, "Spring Happenings", "Google login failed", "OK");
//                return;
//            }
            byte[] data;
            if (result != null) data = Util.bmpToByteArray(result, false);
            else data = new byte[0];
            final ParseFile pf = new ParseFile("ProfileImage.png", data);
//            pf = new ParseFile();
            pf.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (progress.isShowing()) progress.dismiss();
                    if (e == null)
                    {
                        user.put("email", email);
                        user.put("photo", pf);
//                        email = user.getEmail();
                        boolean value = false;
                        if (email.equals("zach@springhappenings.com")) {
                            user.put("is_admin", true);

                            value = true;
                        } else {
                            user.put("is_admin", false);
                            value = false;
                        }
                        Editor edit = pref.edit();
                        if (value == true) {
                            edit.putInt(Constant.LOGIN, 1);
                        } else {
                            edit.putInt(Constant.LOGIN, 2);
                        }
                        edit.commit();
                        try {
                            user.save();
                            ParseInstallation install = ParseInstallation.getCurrentInstallation();
                            install.put("is_admin", value);
                            install.put("InstallationUser", user);
                            install.saveInBackground();
                            MainActivity.user = user;
                            edit.putInt(Constant.SPECIAL, 1);
                            edit.commit();

                            if (value == true)
                            {
                                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(in);
                                finish();
                            }
                            else
                            {
                                Intent in = new Intent(LoginActivity.this, UserActivity.class);
                                startActivity(in);
                                finish();
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
//                            Toast.makeText(LoginActivity.this, e1.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        AppGlobal.showMsgDialog(LoginActivity.this, "Spring Happenings", "Google login failed", "OK");
                    }
                }
            });

//            bmImage.setImageBitmap(result);
        }
    }

}
