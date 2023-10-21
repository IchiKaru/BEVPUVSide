package com.example.puvclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.puvclient.databinding.ActivityMainBinding;
import okhttp3.*;
import java.io.IOException;

import android.widget.ToggleButton;

const { MongoClient, ServerApiVersion } = require('mongodb');
const uri = "mongodb+srv://ClientSide:SDpJGwDBlEJLTtKZ@rt-location.ahaubf7.mongodb.net/?retryWrites=true&w=majority";

public class MapsActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private boolean isTracking = false;

    private final int locationPermissionCode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        final ToggleButton toggleButton = binding.toggleButton;

        // Declare isTracking as a final variable
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isTracking = isChecked;
            if (isTracking) {
                startLocationUpdates();
            } else {
                stopLocationUpdates();
            }
        });
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (isTracking) {
                sendLocationToServer(location.getLatitude(), location.getLongitude());
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        @Override
        public void onProviderEnabled(String provider) {}
        @Override
        public void onProviderDisabled(String provider) {}
    };

    private void startLocationUpdates() {
        if (checkPermission()) {
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0f, locationListener);
            } catch (SecurityException e) {
                Log.e("MainActivity", "SecurityException: " + e.getMessage());
            }
        } else {
            requestPermission();
        }
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, locationPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == locationPermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            } else {
                Log.e("MainActivity", "Permission denied");
            }
        }
    }

    private void sendLocationToServer(double latitude, double longitude) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("latitude", String.valueOf(latitude))
                .add("longitude", String.valueOf(longitude))
                .build();
        String serverUrl = uri;
        Request request = new Request.Builder()
                .url(serverUrl)
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("MainActivity", "Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("MainActivity", "Request succeeded: " + response.body().string());
            }
        });
    }
}
