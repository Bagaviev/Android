package com.example.weatherapp;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.weatherapp.pojo.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LocationActivity extends AppCompatActivity {
    private final int GPS_PERMISSION_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location locationObj;

    ImageButton buttonGps;
    Button buttonSearch;
    EditText editTextCity;
    ListView listViewCity;
    ProgressBar progressBar2;
    TextView textViewLog2;
    TextView textViewGpsInfo;

    static Double curLat;       // most logic holder variables
    static Double curLon;
    static String curCityName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        textViewLog2 = findViewById(R.id.textViewLog2);
        textViewGpsInfo = findViewById(R.id.textViewGpsStatus);
        progressBar2 = findViewById(R.id.progressBar2);
        editTextCity = findViewById(R.id.editTextCity);
        buttonGps = findViewById(R.id.imageButtonGps);
        buttonSearch = findViewById(R.id.buttonSearch);
        listViewCity = findViewById(R.id.listView2);
        MainActivity.hideElements(progressBar2, false);

        buttonSearch.setOnClickListener(v -> {
            textViewLog2.setText("");
            String input = editTextCity.getText().toString();

            if (input.length() > 0) {
                MainActivity.hideElements(progressBar2, true);
                List<City> list = findCity(editTextCity.getText().toString());
                if (list.size() == 0) {
                    textViewLog2.setText("Ничего не нашлось");
                    MainActivity.hideElements(progressBar2, false);
                } else
                    configureListView(list);
            } else {
                textViewLog2.setText("Название пустое");
                MainActivity.hideElements(progressBar2, false);
            }
        });

        buttonGps.setOnClickListener(v -> {
            MainActivity.weatherList.clear();
            MainActivity.hideElements(progressBar2, true);
            handleGPS();
        });
    }

    public void configureListView(List<City> searchCityList) {
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchCityList);
        listViewCity.setAdapter(adapter);
        MainActivity.hideElements(progressBar2, false);

        listViewCity.setOnItemClickListener((parent, view, position, id) -> {
            MainActivity.weatherList.clear();
            City selectedItem = searchCityList.get(position);
            updLocation(selectedItem);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public City getClosestCity(double lat, double lon) {
        Map<Float, City> map = new TreeMap<>();

        for (int i = 0; i < MainActivity.cityList.size(); i++) {     // state по погоде хранится в первом классе
            float distance = calcDistance(lat, lon, MainActivity.cityList.get(i).getLat(), MainActivity.cityList.get(i).getLon());
            map.put(distance, MainActivity.cityList.get(i));
        }

        Map.Entry<Float, City> actualValue = map.entrySet()
                .stream()
                .findFirst()
                .get();

        return actualValue.getValue();
    }

    public List<City> findCity(String cityName) {       // sqlite чето работал херово, обходились как могли
        List<City> founded = new ArrayList<>();
        for (int i = 0; i < MainActivity.cityList.size(); i++) {
            if (MainActivity.cityList.get(i).getName().toLowerCase().contains(cityName.toLowerCase())) {
                founded.add(MainActivity.cityList.get(i));
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
                textViewLog2.setText("GPS not enabled");
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
                        textViewLog2.setText(e.getMessage());
                    }

                    updLocation(locationObj);

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "We need that permission", Toast.LENGTH_LONG).show();
                } else {
                    textViewLog2.setText("Not working without permission");
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
        curCityName = match.getName();
        saveToPrefs();
        MainActivity.hideElements(progressBar2, false);
        textViewGpsInfo.setText("Ок");
    }

    public void updLocation(City city) {    // core method
        curLat = city.getLat();
        curLon = city.getLon();
        curCityName = city.getName();
        saveToPrefs();
        textViewGpsInfo.setText("Ок");
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean gpsStatusCheck() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public void saveToPrefs() {
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("lat", String.valueOf(curLat)).commit();
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("lon", String.valueOf(curLon)).commit();
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("cityname", String.valueOf(curCityName)).apply();
    }
}
