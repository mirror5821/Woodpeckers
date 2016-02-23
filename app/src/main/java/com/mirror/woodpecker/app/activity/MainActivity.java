package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mirror.woodpecker.app.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        startActivity(new Intent(MainActivity.this, MainTabActivity.class));
//        startActivity(new Intent(MainActivity.this, TestCameraActivity.class));
        startActivity(new Intent(MainActivity.this, TestRecyclerViewActivity.class));
//        startActivity(new Intent(MainActivity.this, TestPhoneInfoActivity.class));
//        startActivity(new Intent(MainActivity.this, TestSelectContactsActivity.class));
//        startActivity(new Intent(MainActivity.this, TestQQServiceActivity.class));


    }
}
