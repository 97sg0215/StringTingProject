package com.string.leeyun.stringting_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.tsengvn.typekit.TypekitContextWrapper;

public class Report_pop extends Activity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_report_pop);
    }
    public void cancle(View v){
        super.onBackPressed();
    }

    public void confirm(View v){
        //신고후 어쩌지
    }
}
