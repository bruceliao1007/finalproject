package com.example.barcodetest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class shoppingrecord extends Login {
    TextView textview;
    Button back;
    ProgressBar progressBar;
    //String str[] = new String[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingrecord);
        textview = findViewById(R.id.textView2);
        back = findViewById(R.id.button4);
        progressBar = findViewById(R.id.progress);
        String[] field = new String[1];
        field[0] = "username";
        //Creating array for data
        String[] data = new String[1];
        data[0] = "3";

        //progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[1];
                field[0] = "username";

                //Creating array for data
                String[] data = new String[1];
                data[0] = "3";
                PutData putData = new PutData("http://172.29.96.1/androidtest/shoppingrecord.php", "GET", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        textview.setText(result);
                    }
                }
                //End Write and Read data with URL
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Menu.class);
                startActivity(intent);
                finish();
            }
        });

    }
}