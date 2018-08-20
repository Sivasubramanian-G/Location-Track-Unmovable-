package com.example.siva.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    //vars
    private Boolean mLocationPermissionsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=(Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION};

                if(ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                        FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this.getApplicationContext(),
                            COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        mLocationPermissionsGranted = true;
                        startActivity(new Intent(MainActivity.this,MapsActivity.class));
                    }else{
                        ActivityCompat.requestPermissions(MainActivity.this,
                                permissions,
                                LOCATION_PERMISSION_REQUEST_CODE);
                    }
                }else{
                    ActivityCompat.requestPermissions(MainActivity.this,
                            permissions,
                            LOCATION_PERMISSION_REQUEST_CODE);
                }


            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;

                            return;
                        }
                    }

                    mLocationPermissionsGranted = true;
                    //initialize our map
                    startActivity(new Intent(MainActivity.this,MapsActivity.class));

                }
            }
        }
    }


}
