package com.newspaper.chaudang.newspaper.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.newspaper.chaudang.newspaper.R;

public class LogoActivity extends AppCompatActivity {

    ImageView imgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        imgLogo = (ImageView) findViewById(R.id.logoTinTuc);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(LogoActivity.this, MainActivity.class);
                LogoActivity.this.startActivity(mainIntent);
                LogoActivity.this.finish();
            }
        }, 3000);

    }

    public class Handler extends android.os.Handler {

    }

}
