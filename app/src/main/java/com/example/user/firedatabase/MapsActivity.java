package com.example.user.firedatabase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng location;
    double Lat, Lng;
    Uri locate;
    String label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final Bundle bundle = this.getIntent().getExtras();
        String address = bundle.get("地址").toString();
        label = bundle.get("Name").toString();

        address = "台灣" + address;
        List<Address> addressLocation = null;
        Geocoder geoCoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            addressLocation = geoCoder.getFromLocationName(address, 1);
            Lat = addressLocation.get(0).getLatitude();
            Lng = addressLocation.get(0).getLongitude();
            location = new LatLng(Lat, Lng);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(location).title(Lat + " , " + Lng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
        GoogleMap.OnMarkerClickListener myClick;
        myClick = new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker m) {
                new AlertDialog.Builder(MapsActivity.this)
                        .setTitle("是否在地圖中開啟")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                locate = Uri.parse("geo:0,0?q=" + Lat + "," + Lng + "("+label+")?z=18");
//                                locate = Uri.parse("geo:" + Lat + "," + Lng + "?z=18");
//                                Uri locate = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, locate);
                                mapIntent.setPackage("com.google.android.apps.maps");
// Verify it resolves
                                PackageManager packageManager = getPackageManager();
                                List<ResolveInfo> activities = packageManager.queryIntentActivities(mapIntent, 0);
                                boolean isIntentSafe = activities.size() > 0;
// Start an activity if it's safe
                                if (isIntentSafe) {
                                    startActivity(mapIntent);

                                }
                            }
                        }).show();
                return true;
            }
        };
        mMap.setOnMarkerClickListener(myClick);
    }
}
