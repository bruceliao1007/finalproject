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

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class shoppingrecord extends Login {
    ListView lvshow;
    Button back;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingrecord);
        lvshow = findViewById(R.id.listview);
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
                data[0] = token;
                PutData putData = new PutData("http://706d-2001-b400-e203-5338-a9b9-fae2-109f-3488.ngrok.io/androidtest/shoppingrecord.php", "GET", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        //End ProgressBar (Set visibility to GONE)
                        String[] str = result.split("!");
                        ArrayAdapter adapter=
                                new ArrayAdapter(shoppingrecord.this,android.R.layout.simple_list_item_1,str);
                        lvshow.setAdapter(adapter);


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