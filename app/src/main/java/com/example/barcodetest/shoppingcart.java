package com.example.barcodetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class shoppingcart extends MainActivity{
    ListView lshow;
    List<String> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        data = new ArrayList<>();
        lshow = findViewById(R.id.list);
        data.add("good");
        MyAdapter adapter = new MyAdapter(data);
        lshow.setAdapter(adapter);
        lshow.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j){
                Toast.makeText(com.example.barcodetest.shoppingcart.this,"123",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static class MyAdapter extends BaseAdapter implements View.OnClickListener{
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
            ViewHolder viewHolder = null;
            if(context == null){
                context = viewGroup.getContext();
            }
            if(view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.mTv = (TextView) view.findViewById(R.id.mTv);
                viewHolder.mbtn = (Button)view.findViewById(R.id.mbtn);
                view.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) view.getTag();
            viewHolder.mTv.setText(data.get(i));
            viewHolder.mbtn.setText("123");
            viewHolder.mbtn.setOnClickListener(this);
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

        static class ViewHolder{
            TextView mTv;
            Button mbtn;
        }
    }


}