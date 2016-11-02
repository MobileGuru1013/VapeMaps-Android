package com.zkaren.springhappenings;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;


import info.hoang8f.android.segmented.SegmentedGroup;

public class MoreInformation extends FragmentActivity {

    SegmentedGroup group;
    public static FragmentManager fragmentManager;
    public static ParseObject newsObject;
    boolean mapflag, imageflag;
    public static String url, timeStr, title, contents, photoURL;
    public static ParseGeoPoint geoPoint;
//    ImageView ad_imageView;
    ParseQuery<ParseObject> ad_po;
    ArrayList<ParseObject> ads;
    int index = 0;
    int count = 0;
    SharedPreferences pref;
    public void OnShare(View v)
    {
         Intent intent = new Intent(Intent.ACTION_SEND);
        String subject, text;

        subject = getString(R.string.app_name);
        text = title + " via @SpringHappenings www.springhappennings.com to learn mode";
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(intent, "SELECT APP"));
    }

//    int kind
    int kind ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_information);
        mapflag = true; imageflag = true;
        pref = getSharedPreferences(Constant.prefName, 0);
        fragmentManager =  getSupportFragmentManager();
//        ad_po = new ParseQuery<ParseObject>(Constant.TABLE_IMAGE);
//        ad_imageView = (ImageView)findViewById(R.id.image);
        group = (SegmentedGroup)findViewById(R.id.segmented3);
        kind = 0;
        addButton(group);
        if (geoPoint != null && (geoPoint.getLatitude() != 0.0 || geoPoint.getLongitude() != 0.0))         {addButton(group); kind = 1;}
        if (photoURL != null)            {addButton(group); kind = 2;}


//        if (geoPoint == null) {group.removeViewAt(1); mapflag = false;}
//        else if (geoPoint.getLatitude() == 0.0 && geoPoint.getLongitude() == 0.0)
//        {
//            group.removeViewAt(1);
//            mapflag = false;
//        }
//        if (photoURL == null)
//        {
//            imageflag = false;
//            if (mapflag) group.removeViewAt(2); else group.removeViewAt(1);
//        }
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.detailID)
                {
                    replace(0);
                }
                else if (checkedId == R.id.mapID)
                {
                    replace(1);
                }
                else if (checkedId == R.id.photoID)
                    replace(2);
            }
        });

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.framelayout, new DetailFragment());
        ft.add(R.id.adv, new AdvImageFragment());
        ft.commit();


//        LoadAds();
//        addButton(group);
//        addButton(group);
//        addButton(group);

//        group.removeViewAt(2);
//        group.updateBackground();
	}

    public void OnBack(View v)
    {
        int kind = pref.getInt(Constant.LOGIN, 0);
        Intent in = null;
        if (kind == 1)
        {
            in = new Intent(this, AdminActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        else
        {
            in = new Intent(this, UserActivity.class);
            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        finish();
        startActivity(in);
        this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    public void replace(int kind)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (kind == 0)
        {
            ft.replace(R.id.framelayout, new DetailFragment());
        }
        else if (kind == 1)
        {
            ft.replace(R.id.framelayout, new MapFragment());
        }
        else if (kind == 2)
        {
            ft.replace(R.id.framelayout, new PhotoFragment());
        }
        ft.commit();
    }
    private void removeButton(SegmentedGroup group, int index, int count) {
//        if (group.getChildCount() < 1) return;
        group.removeViewAt(index);
        group.updateBackground();

        //Update margin for last item
        if (group.getChildCount() < 1) return;
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 0);
        group.getChildAt(index).setLayoutParams(layoutParams);
    }
    private void addButton(SegmentedGroup group) {
        RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.radio_button_item, null);
        if (group.getChildCount() == 0)
        {
            radioButton.setText("Details");
//            radioButton.setTag(DETAIL_ID);
            radioButton.setId(R.id.detailID);
        }
        else if (group.getChildCount() == 1)
        {
            if (kind == 1) {
                radioButton.setText("Map");
                radioButton.setId(R.id.mapID);
            }
            else
            {
                radioButton.setText("Photo");
                radioButton.setId(R.id.photoID);
            }
        }
        else if (group.getChildCount() == 2)
        {
            radioButton.setText("Photo");
            radioButton.setId(R.id.photoID);
        }
//        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(100, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.setMargins(0, 0, 0, 0);
//        radioButton.setLayoutParams(layoutParams);
        group.addView(radioButton);
        group.updateBackground();
        group.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.more_information, menu);
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
}
