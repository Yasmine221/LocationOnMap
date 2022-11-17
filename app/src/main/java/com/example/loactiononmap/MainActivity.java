package com.example.loactiononmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.loactiononmap.utils.MyLocationProvider;

public class MainActivity extends AppCompatActivity {

    MyLocationProvider myLocationProvider;
    Location location;
   private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocationProvider = new MyLocationProvider(this);
        
        if (locationPermissionOnAllowed()) {
          location = myLocationProvider.getCurrentLocation(null);
            Log.d("ddddddddddd","dddddddddddddddddttttttttttt");
        }
        else {
            askLocationPermission();
        }
    }

    private void askLocationPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale
                (this ,Manifest.permission.ACCESS_FINE_LOCATION)) {

            showUIMessage("Please, Enter your Location");
        } else {

            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                    , LOCATION_PERMISSION_REQUEST_CODE);

        }



    }

    private void showUIMessage(String message) {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(message);
        alertDialog.setTitle("Location Service");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                , LOCATION_PERMISSION_REQUEST_CODE);
            }
        });
    }

    private boolean locationPermissionOnAllowed() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    location = myLocationProvider.getCurrentLocation(null);
                    Log.d("ddddddddddd","dddddddddddddddddttttttttttt");
                }
                else
                    Toast.makeText(this, "Loaction not Valid", Toast.LENGTH_SHORT).show();
                return;
        }
    }
}