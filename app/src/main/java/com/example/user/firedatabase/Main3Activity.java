package com.example.user.firedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main3Activity extends AppCompatActivity {
    ImageButton b1,b2,b3,b4;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        b1 = findViewById(R.id.eatbut);
        b2 = findViewById(R.id.dressbut);
        b3 = findViewById(R.id.staybut);
        b4 = findViewById(R.id.playbut);
        button = findViewById(R.id.addbut);

        b1.setOnClickListener(btnListener);
        b2.setOnClickListener(btnListener);
        b3.setOnClickListener(btnListener);
        b4.setOnClickListener(btnListener);
        button.setOnClickListener(btnListener);

    }

    private Button.OnClickListener btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            String type = null;
            intent.setClass(Main3Activity.this, Main2Activity.class);
            switch (v.getId()){
                case(R.id.eatbut):
                    type = "eat";
                    break;
                case(R.id.dressbut):
                    type = "dress";
                    break;
                case(R.id.staybut):
                    type = "stay";
                    break;
                case(R.id.playbut):
                    type = "play";
                    break;
                case(R.id.addbut):
                    intent.setClass(Main3Activity.this, MainActivity.class);
                    break;
            }
            intent.putExtra("type",type);
            startActivity(intent);
        }
    };
}
