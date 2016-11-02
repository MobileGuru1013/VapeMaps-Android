package com.zkaren.springhappenings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    private String TAG = "com.springhappenings.com";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        try{
                if (intent == null)
                {

                }
                else
                {
                    String action = intent.getAction();

                    if (action.equals("com.zkaren.springhappenings.UPDATE_STATUS"))
                    {
                        String str = intent.getExtras().getString("com.parse.Data");
//                        Toast.makeText(context.getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                        JSONObject json = new JSONObject(str);
                        if (json.has("tit"))
                        {
                            Intent pupInt = new Intent(context, ShowPopUp.class);
                            ShowPopUp.category = json.getString("category");
                            ShowPopUp.title = json.getString("tit");

                                pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                context.getApplicationContext().startActivity(pupInt);
                        }

//                        String title = json.getString("tit");
//                        String category = json.getString("category");
//
//                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(Constant.TABLE_POST);
//                        query.whereEqualTo(Constant.TITLE, title);
//                        query.whereEqualTo(Constant.CATEGORY, category);
//                        query.getFirstInBackground(new GetCallback<ParseObject>() {
//                            @Override
//                            public void done(ParseObject parseObject, ParseException e) {
//                                if (e == null)
//                                {
//
//                                }
//                            }
//                        });


//                        Iterator itr = json.keys();
//                        while (itr.hasNext()) {
//                            String key = (String) itr.next();
//                            if (key.equals("message"))
//                            {
//                                Intent pupInt = new Intent(context, ShowPopUp.class);
//                                pupInt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
//                                context.getApplicationContext().startActivity(pupInt);
//                            }
//                            Log.d(TAG, "..." + key + " => " + json.getString(key));
//                        }
//                        Intent in = new Intent(context, AdminActivity.class);


//                        context.getApplicationContext().startActivity(in);
                    }
                    else
                    {
                        Toast.makeText(context.getApplicationContext(), "Push Error", Toast.LENGTH_SHORT).show();
                    }
                }
        }
        catch (JSONException e){

        }

//        Toast.makeText(context, "Receive", Toast.LENGTH_SHORT).show();
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
