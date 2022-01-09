package com.example.barcodetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;

public class MainActivity extends Login {
    SurfaceView surfaceView;
    TextView textView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    Button back, shoppingcart;
    public static String[] shoppinglist = new String[3];
    public static int[] price = new int[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissionCamera();
        back = findViewById(R.id.button5);
        surfaceView=(SurfaceView)findViewById(R.id.surface_view);
        textView=(TextView)findViewById(R.id.barcode_text);

        shoppingcart = findViewById(R.id.shoppingcart);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource=new CameraSource.Builder(this,barcodeDetector)
                .setRequestedPreviewSize(300,300).build();



        BarcodeInfo barcodeInfo = null;


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA)
                        !=PackageManager.PERMISSION_GRANTED)
                    return;
                try{
                    cameraSource.start(surfaceHolder);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }

        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){
            @Override
            public void release() {
            }
            BarcodeInfo barcodeInfo = null;
            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode>qrCodes=detections.getDetectedItems();
                if(qrCodes.size()!=0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(qrCodes.valueAt(0).displayValue);
                            if(textView.getText()!="請進行掃碼"){
                                try {
                                    barcodeInfo.barcode_text=textView.getText().toString();
                                    if(barcodeInfo.getHavedata())Thread.sleep(100);//Delay 100 ms
                                }catch (InterruptedException e) {
                                    return;
                                }
                                String[] barcode_textPOST=new String[1];
                                String[] title = new String[1];
                                title[0] = "title";
                                barcode_textPOST[0]=barcodeInfo.getBarcode_text();
                                PutData putData = new PutData("http://dc33-1-171-45-153.ngrok.io/androidtest/searchproduct.php", "POST", title,barcode_textPOST );
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        int price = Integer.parseInt(putData.getResult());
                                        Toast.makeText(getApplicationContext(),"商品："+barcode_textPOST[0]+" 價格："+price+" 加入購物車", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    });
                }
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
        shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),shoppingcart.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void getPermissionCamera(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("需要相機權限")
                    .setMessage("同意相機權限以進行掃碼")
                    .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                        }
                    })
                    .show();
        }
    }

}