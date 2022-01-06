package com.example.barcodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class shoppingrecord extends Login {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingrecord);
        String[] field = new String[1];
        field[0] = "username";
        //Creating array for data
        String[] data = new String[1];
        data[0] = "3";
        PutData putData = new PutData("http://172.31.32.1/androidtest/shoppingrecord.php", "POST", field, data);
        if (putData.startPut()) {
            if (putData.onComplete()) {
                String result = putData.getResult();
                if(result.equals("successful")){
                    String res = putData.getResult();

                }
                else {
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}