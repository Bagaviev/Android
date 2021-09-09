/*
package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.network.NetworkService;
import com.example.weatherapp.pojo.City;
import com.example.weatherapp.pojo.OurWeather;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocationActivity extends AppCompatActivity {
    private final int GPS_PERMISSION_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location locationObj;

    Button buttonCity;
    Button buttonLocate;
    TextView textViewCity;
    TextView textViewLocate;

    List<OurWeather> weatherList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        buttonCity = findViewById(R.id.buttonCity);
        buttonLocate = findViewById(R.id.buttonLocate);
        textViewCity = findViewById(R.id.textViewCity);
        textViewLocate = findViewById(R.id.textViewLocate);

        buttonCity.setOnClickListener(v -> {
            List<City> list = findCity("kazan");
            textViewCity.setText(list.toString());
        });

        buttonLocate.setOnClickListener(v -> {
            handleGPS();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public City getClosestCity(double lat, double lon) {
        Map<Float, City> map = new TreeMap<>();

        for (int i = 0; i < cityList.size(); i++) {
            float distance = calcDistance(lat, lon, cityList.get(i).getLat(), cityList.get(i).getLon());
            map.put(distance, cityList.get(i));
        }

        Map.Entry<Float, City> actualValue = map.entrySet()
                .stream()
                .findFirst()
                .get();

        return actualValue.getValue();
    }

    public List<City> findCity(String cityName) {       // sqlite чето работал херово, обходились как могли
        List<City> founded = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            if (cityList.get(i).getName().toLowerCase().contains(cityName.toLowerCase())) {
                founded.add(cityList.get(i));
            }
        }
        return founded;
    }

    public float calcDistance(double lat1, double lon1, double lat2, double lon2) {
        Location locA = new Location("point A");
        Location locB = new Location("point B");
        locA.setLatitude(lat1);
        locA.setLongitude(lon1);
        locB.setLatitude(lat2);
        locB.setLongitude(lon2);
        return locA.distanceTo(locB);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void handleGPS() {
        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updLocation(location);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                textViewLocate.setText("GPS not enabled");
            }
        };

        if (checkPermission()) {
            if (!gpsStatusCheck())
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
            locationObj = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_CODE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GPS_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        if (!gpsStatusCheck())
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null);
                        locationObj = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                    } catch (SecurityException e) {
                        textViewLocate.setText(e.getMessage());
                    }

                    updLocation(locationObj);

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "We need that permission", Toast.LENGTH_LONG).show();
                } else {
                    textViewLocate.setText("Not working without permission");
                }
            }
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updLocation(Location location) {    // core method
        City match = getClosestCity(location.getLatitude(), location.getLongitude());
        curLat = match.getLat();
        curLon = match.getLon();
        saveToPrefs();
        textViewLocate.setText(match.getName() + ", " + match.getCountry());
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean gpsStatusCheck() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void saveToPrefs() {
        getSharedPreferences("lat", MODE_PRIVATE).edit().putString("double", String.valueOf(curLat)).apply();
        getSharedPreferences("lon", MODE_PRIVATE).edit().putString("double", String.valueOf(curLon)).apply();
    }

    public void readFromPrefs() {
        curLat = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("double", "0"));
        curLon = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("double", "0"));
    }
}*/
