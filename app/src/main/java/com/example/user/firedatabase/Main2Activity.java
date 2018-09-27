package com.example.user.firedatabase;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    String type;
    SimpleAdapter adapter;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = findViewById(R.id.listView);
        linearLayout = findViewById(R.id.layoutBackground);
        final Bundle bundle = this.getIntent().getExtras();
        type = bundle.getString("type").toString();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        final StorageReference imagesRef = storageRef.child("images");
        final StorageReference spaceRef = imagesRef.child("dog.jpg");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference getContactsRef = database.getReference();

        getContactsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
                HashMap<String, Object> hashmap = new HashMap<>();
                for (DataSnapshot dataSnapshot : snapshot.child(type).getChildren()) {
                    hashmap.put("Name", dataSnapshot.child("name").getValue());
                    hashmap.put("Address", dataSnapshot.child("address").getValue());
                    hashmap.put("Rate", dataSnapshot.child("rating").getValue());
                    hashmap.put("Score", dataSnapshot.child("score").getValue());
                    arraylist.add(hashmap);
                    hashmap = new HashMap<String, Object>();
                }
                adapter = new SimpleAdapter(getApplicationContext(), arraylist, R.layout.item, new String[]{"Name", "Address", "Score","Rate"}, new int[]{R.id.name, R.id.address, R.id.score,R.id.rate});
                listView.setAdapter(adapter);
                linearLayout.setBackground(getDrawable(R.drawable.gradation2));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2,long arg3) {
                Map<String,String> map;
                map = (Map<String, String>) arg0.getItemAtPosition(arg2);
                Intent it = new Intent();
                it.setClass(Main2Activity.this,Main4Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name",map.get("Name"));
                bundle.putString("Address",map.get("Address"));
                bundle.putString("Rate",map.get("Rate"));
                bundle.putString("Score",map.get("Score"));
                it.putExtras(bundle);
                startActivity(it);
            }
        });



    }

    private void downloadImg(final StorageReference ref ,final ImageView Img){
        if(ref == null){
            Toast.makeText(Main2Activity.this, R.string.plz_upload_img, Toast.LENGTH_SHORT).show();
            return;
        }
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(Main2Activity.this)
                        .using(new FirebaseImageLoader())
                        .load(ref)
                        .into(Img);
                Toast.makeText(getApplicationContext(),R.string.download_success,Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(),exception.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
