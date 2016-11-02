package com.zkaren.springhappenings;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends ActionBarActivity {

    String url;
    WebView webview;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        url = getIntent().getExtras().getString(Constant.URL, "");
        webview = (WebView)findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new Callback());
        if (!url.equals(""))
        {
            if (url.startsWith("http"))
                webview.loadUrl(url);
            else
                webview.loadUrl("https://" + url);
        }
    }

    public void OnLogout(View v)
    {
        finish();
        this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }
    private class Callback extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            progressDialog.show();
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            //   progress.dismiss();
//            send_point();
//            AppGlobal.hideLoadingView();
            if (progressDialog.isShowing()) progressDialog.dismiss();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
