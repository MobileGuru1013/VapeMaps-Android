package com.zkaren.springhappenings;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ShowPopUp extends Activity implements OnClickListener {

	Button ok;
	Button cancel;
	
	boolean click = true;
    public static String title;
    public static String category;
    TextView txtTitle;
    TextView txtContents;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Cupon");
		setContentView(R.layout.popupdialog);
		ok = (Button)findViewById(R.id.popOkB);
		ok.setOnClickListener(this);
		cancel = (Button)findViewById(R.id.popCancelB);
        txtTitle = (TextView)findViewById(R.id.durationTitle);
        txtContents = (TextView)findViewById(R.id.textView14);
//        txtTitle.setText("");
        txtContents.setText(title);
		cancel.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
        int id = arg0.getId();
        if (id == R.id.popCancelB)
        {
            finish();


        }
        else if (id == R.id.popOkB)
        {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Constant.TABLE_POST);
            query.whereEqualTo(Constant.TITLE, title);
            query.whereEqualTo(Constant.CATEGORY, category);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (e == null)
                    {
                        MoreInformation.url = parseObject.getString(Constant.URL);
                        MoreInformation.contents = parseObject.getString(Constant.CONTENTS);
                        ParseFile pf = parseObject.getParseFile(Constant.NEWS_IMAGE);
                        if (pf == null) MoreInformation.photoURL = null;
                        else MoreInformation.photoURL = pf.getUrl();
                        MoreInformation.geoPoint = parseObject.getParseGeoPoint(Constant.LOCATION);
                        MoreInformation.timeStr = Util.getDifferenceDate(parseObject.getCreatedAt());
                        Intent in = new Intent(ShowPopUp.this, MoreInformation.class);
                        startActivity(in);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }
//		finish();
	}
}