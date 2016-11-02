package com.zkaren.springhappenings;




import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class DetailActivity extends Activity {

	String url;
	WebView webView;
	ProgressDialog progress;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		progress = new ProgressDialog(this);
		progress.setTitle("Loading...");
		webView = (WebView)findViewById(R.id.webView1);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebViewClient(new Callback());
		url = getIntent().getExtras().getString(Constant.URL);
		if (url.equals(""))
		{
			
		}
		else
		{
			webView.loadUrl(url);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detail, menu);
		return true;
	}
	
	public void OnBack(View v)
	{
		finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
	private class Callback extends WebViewClient
	{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
//            AppGlobal.showLoadingView(ChangePasswordActivity.this);
        	progress.show();
        }


          @Override
          public void onPageFinished(WebView view, String url) {
           //   progress.dismiss();
//        	  send_point();
//        	  AppGlobal.hideLoadingView();
        	  progress.dismiss();
          }
	}
}
