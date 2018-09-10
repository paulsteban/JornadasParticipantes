package com.edu.epn.jisicv01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    String email;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        email = (getIntent().getExtras().getString("email"));
        textView =(TextView) findViewById(R.id.textView2);
        textView.setText(email);
    }
}
