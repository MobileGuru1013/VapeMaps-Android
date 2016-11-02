package com.zkaren.springhappenings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;

public class Util {
	public static boolean isValidEmail(String string) {
		Pattern pattern;
		Matcher matcher;
		// final String EMAIL_PATTERN =
		// "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		final String EMAIL_PATTERN = "(?:[a-zA-Z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}"
				+ "~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\"
				+ "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-"
				+ "z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"
				+ "]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"
				+ "9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"
				+ "-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(string);
		return matcher.matches();
	}
	public static JSONObject getLocationInfo(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        try {

        address = address.replaceAll(" ","%20");

        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        stringBuilder = new StringBuilder();


            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }
    public static Bitmap byteTobmp(byte[] data)
    {
        Bitmap bmimage = BitmapFactory.decodeByteArray(data, 0,
                data.length);
        return bmimage;
    }
	public static LatLng getLocationFromAddress(Context context, String address)
	{
//		JSONObject json = getLocationInfo(address);
//		return getLatLong(json);
        Geocoder gc=new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        LatLng latLng; //= new LatLng();
        try {
            addresses=gc.getFromLocationName(address,5);
            if (addresses.size() > 0) {
                latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
                return latLng;
            }
        }
        catch (  IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
	}
	public static LatLng getLatLong(JSONObject jsonObject) {

		double longitute = 0.0, latitude = 0.0;
		
        try {
            longitute = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");

            latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");
            return new LatLng(latitude, longitute);
        } catch (JSONException e) {
            return new LatLng(0, 0);
        }
    }
	public static String getDifferenceDate(Date date1) {
		String str = "";
		Date date0 = Calendar.getInstance().getTime();
		long diff = (date0.getTime() - date1.getTime()) / 1000;
		long hours = diff / 3600;
		if (hours > 0)
		{
			if (hours < 24)
			{
				if (hours == 1)
				{
					str = String.format("%d hour ago", hours);
				}
				else
					str = String.format("%d hours ago", hours);
			}
			else
			{
				long day = hours / 24;
				if (day == 1)
					str = String.format("%d day ago", day);
				else
					str = String.format("%d days ago", day);
			}
				
		}
		else 
		{
			hours = diff / 60;
			if (hours > 0)
			{
				if (hours == 1)
				{
					str = String.format("%d minute ago", hours);
				}
				else
				{
					str = String.format("%d minutes ago", hours);
				}
			}
			else 
				str = "just now";
			
		}
		return str;
	}

	public static byte[] bmpToByteArray(final Bitmap bmp,
			final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
