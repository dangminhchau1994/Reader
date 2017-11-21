package com.newspaper.chaudang.newspaper.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.newspaper.chaudang.newspaper.Model.ThoiSu;
import com.newspaper.chaudang.newspaper.R;
import com.newspaper.chaudang.newspaper.Utils.CheckConnection;

public class WebViewThoiSu extends AppCompatActivity {

    private WebView webView;
    private Toolbar toolbar;
    private DatabaseReference mDatabase;
    private String tenThoiSu;
    private String hinhThoiSu;
    private String link;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_thoi_su);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        webView = (WebView) findViewById(R.id.webThoiSu);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setEnableSmoothTransition(true);
        webView.setWebViewClient(new WebViewClient());

        toolbar = (Toolbar) findViewById(R.id.toolWebVieThoiSu);
        actionBar();
        Intent intent = getIntent();

        tenThoiSu = intent.getStringExtra("tenThoiSu");
        hinhThoiSu = intent.getStringExtra("HinhThoiSu");

        if (intent != null) {
                link = intent.getStringExtra("linkThoiSu");
                webView.loadUrl(link);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_webview, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.luutin:
                ThoiSu thoiSu = new ThoiSu(tenThoiSu, hinhThoiSu, link);
                mDatabase.child("TinLưu").push().setValue(thoiSu, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        if (databaseError == null) {

                            CheckConnection.showToast_short(getApplicationContext(), "Tin đã lưu");
                        } else {

                            CheckConnection.showToast_short(getApplicationContext(), "Lưu tin thất bại");
                        }
                    }
                });

                break;

            case R.id.sharetin:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Chia sẻ với"));
                break;

            case R.id.font:
                Dialog dialog = new Dialog(WebViewThoiSu.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_font);

                Button btnSmall = dialog.findViewById(R.id.btnSmall);
                Button btnMedium = dialog.findViewById(R.id.btnMedium);
                Button btnLarge = dialog.findViewById(R.id.btnLarge);
                Button btnSuper = dialog.findViewById(R.id.btnSuperLarge);

                btnSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    }
                });

                btnMedium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                    }
                });

                btnLarge.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webSettings.setTextSize(WebSettings.TextSize.LARGER);
                    }
                });

                btnSuper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                    }
                });

                dialog.show();
                break;


        }

        return super.onOptionsItemSelected(item);

    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



}
