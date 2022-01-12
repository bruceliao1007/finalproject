package com.example.barcodetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class shoppingcart extends MainActivity{
    ListView lshow;
    List<String> data;

    TextView sum;
    Button back, checkout;
    public List<String> newlist = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);

        back = findViewById(R.id.button7);

        data = new ArrayList<>();
        lshow = findViewById(R.id.list);
        sum = findViewById(R.id.textView11);
        checkout = findViewById(R.id.button6);
        List<Integer> temp = new ArrayList<>();
        for(String s:price)temp.add(Integer.valueOf(s));
        int[] numArray = temp.stream().mapToInt(i->i).toArray();
        int total = Arrays.stream(numArray).sum();
        sum.setText(Integer.toString(total));
        MyAdapter adapter = new MyAdapter(cart);

        lshow.setAdapter(adapter);
        /*
        lshow.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                for(String s:price)temp.add(Integer.valueOf(s));
                int[] numArray = temp.stream().mapToInt(k->k).toArray();
                int total = Arrays.stream(numArray).sum();
                sum.setText("Integer.toString(total)");
            }
        });*/
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(shoppingcart.this);
                alertDialog.setTitle("Order Confirmation");
                alertDialog.setMessage("Are you sure to checkout the order?");
                alertDialog.setPositiveButton("Yes!",((dialog, which) -> {}));
                alertDialog.setNeutralButton("back",((dialog, which) -> {}));
                AlertDialog dialog = alertDialog.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v -> {
                    if(shoppinglist.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please add some product into cart", Toast.LENGTH_SHORT).show();
                    }else {
                        String[] field = new String[5];
                        field[0] = "product1";
                        field[1] = "product2";
                        field[2] = "product3";
                        field[3] = "sum";
                        field[4] = "UID";
                        //Creating array for data
                        String[] data = new String[5];
                        shoppinglist.toArray(data);
                        for (int i = 0; i <= 2; i++) {
                            if (data[i] == null) {
                                data[i] = "nothing";
                            }
                        }
                        data[3] = Integer.toString(total);
                        data[4] = token;
                        PutData putData = new PutData("http://2e5a-2001-b400-e203-5338-990d-a2e7-d84-4935.ngrok.io/addrecord.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("Thank you")) {
                                    Toast.makeText(getApplicationContext(), "Thank you", Toast.LENGTH_SHORT).show();
                                    shoppinglist.clear();
                                    price.clear();
                                    cart.clear();
                                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                                }
                            } else
                                Toast.makeText(getApplicationContext(), "not complete", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "not start", Toast.LENGTH_SHORT).show();
                    }
                }));
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener((v -> {
                    dialog.dismiss();
                }));

                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
            }
        });

    }


    public class MyAdapter extends BaseAdapter implements View.OnClickListener{

        private Context context;
        private List<String> data;


        public MyAdapter(List<String> data){
            this.data = data;

        }

        @Override
        public int getCount() {
            return data == null ? 0 :data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(context == null){
                context = viewGroup.getContext();
            }
            if(view == null){
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv = (TextView) view.findViewById(R.id.mTv);
                viewHolder.mbtn = (Button)view.findViewById(R.id.mbtn);
                viewHolder.mbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        data.remove(i);
                        shoppinglist.remove(i);
                        price.remove(i);
                        notifyDataSetChanged();
                        Intent intent = getIntent();
                        overridePendingTransition(0, 0);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(intent);

                    }
                });
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.mTv.setText(data.get(i));
            viewHolder.mbtn.setText("delete");
            return view;
        }


        @Override
        public void onClick(View view) {
            switch(view.getId()){
                case R.id.mbtn:
                    Toast.makeText(context,"123",Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        class ViewHolder{
            TextView mTv;
            Button mbtn;
        }

    }

}