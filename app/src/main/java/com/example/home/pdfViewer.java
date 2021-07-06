package com.example.home;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URLEncoder;

public class pdfViewer extends AppCompatActivity {
    WebView pdfview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        pdfview=findViewById(R.id.pdfView);
        pdfview.getSettings().setJavaScriptEnabled(true);
        String url=getIntent().getStringExtra("pdfUrl");
        String filename=getIntent().getStringExtra("nameoffile");

        ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening File ... !!");

        pdfview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });
        String url1="";
        try {
            url1= URLEncoder.encode(url,"UTF-8");
        }catch (Exception ex) {

        }
        pdfview.loadUrl("https://drive.google.com/gview?embedded=true&url=" + url1);
    }
}