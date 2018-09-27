package com.example.user.firedatabase;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient client;
    private DatabaseReference mDatabase;

    TextView textView;
    EditText textdate, text1, text2, text3, text4, text5;
    ArrayAdapter<String> fileDBAdapter;
    RadioButton r1,r2,r3,r4;
    RadioGroup rg;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text5 = findViewById(R.id.text5);

        r1 = findViewById(R.id.eat);
        r2 = findViewById(R.id.dress);
        r3 = findViewById(R.id.stay);
        r4 = findViewById(R.id.play);

        rg = findViewById(R.id.radio);


    }

    private String checkType(RadioGroup rg) {
        String Type = null;
        switch(rg.getCheckedRadioButtonId()){
            case R.id.eat:
                Type = "eat";
                break;
            case R.id.dress:
                Type = "dress";
                break;
            case R.id.stay:
                Type = "stay";
                break;
            case R.id.play:
                Type = "play";
                break;
        }
        return Type;
    }


    public void addInfo(View view) {
        String name = text1.getText().toString();
        String address = text2.getText().toString();
        String rate = text3.getText().toString();
        String score = text5.getText().toString();
        type = checkType(rg);
        User user = new User(name, address, rate, score);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(type);
        mDatabase.push().setValue(user);

        text1.setText("");
        text2.setText("");
        text3.setText("");
        text5.setText("");

        Toast.makeText(getApplicationContext(), "新增完成", Toast.LENGTH_SHORT).show();
    }


}

