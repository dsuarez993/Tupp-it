package com.example.david.apitest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        String result = extras.getString("result");

        TextView tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result.setText(result);
    }
}
