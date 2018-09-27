package com.example.user.firedatabase;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4;
    List<Address> addressLocation;
    String address,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        final Bundle bundle = this.getIntent().getExtras();
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        address = bundle.get("Address").toString();
        name = bundle.get("Name").toString();

        textView1.setText("店名  " + name);
        textView2.setText("地址  " + address);
        textView3.setText("評價 \n"+ bundle.get("Rate").toString());
        textView4.setText("評分  " + bundle.get("Score").toString());
    }

    public void checkpos(View view) {
        String newaddress = "台灣" + address;
        Intent it  = new Intent();
        it.setClass(Main4Activity.this,MapsActivity.class);
        it.putExtra("地址",newaddress).putExtra("Name",name);

        List<Address> addressLocation = null;
        Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addressLocation = geoCoder.getFromLocationName(newaddress, 1);
            if (addressLocation.size() == 0){
                Toast.makeText(getApplicationContext(),"地圖找不到"+address,Toast.LENGTH_SHORT).show();
            }else {
                startActivity(it);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"地圖找不到"+address,Toast.LENGTH_SHORT).show();
        }

    }
}
