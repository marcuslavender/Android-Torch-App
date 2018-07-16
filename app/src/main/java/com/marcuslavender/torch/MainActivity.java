package com.marcuslavender.torch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


    }


    public void onSplashPageClick(View view) {
        Intent intent = new Intent(this, MainScreen.class);
        startActivity(intent);


    }
}
