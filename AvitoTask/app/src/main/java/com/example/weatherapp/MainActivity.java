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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.weatherapp.network.NetworkService;
import com.example.weatherapp.pojo.City;
import com.example.weatherapp.pojo.OurWeather;
import com.example.weatherapp.pojo.api_entities.Daily;
import com.example.weatherapp.pojo.api_entities.Request;
import com.example.weatherapp.utils.ListAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final int GPS_PERMISSION_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location locationObj;

    NetworkService networkService;

    List<OurWeather> weatherList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();

    Button buttonCity;
    Button buttonLocate;
    TextView textViewCity;
    TextView textViewLocate;
    ListView listView;
    TextView textViewMcity;     // log textView, all errors will be there
    TextView textViewMhum;
    TextView textViewMtemp;
    TextView textViewMpres;
    TextView textViewMdesc;
    TextView textViewMwind;
    TextView textViewMdt;

    static Double curLat;       // most logic holder variables
    static Double curLon;
    static String curCityName;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkService = NetworkService.getInstance();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        buttonCity = findViewById(R.id.buttonCity);
        buttonLocate = findViewById(R.id.buttonLocate);
        textViewCity = findViewById(R.id.textViewCity);
        textViewLocate = findViewById(R.id.textViewLocate);
        listView = findViewById(R.id.listView);
        textViewMcity = findViewById(R.id.textViewMcity);
        textViewMhum = findViewById(R.id.textViewMhum);
        textViewMtemp = findViewById(R.id.textViewMtemp);
        textViewMpres = findViewById(R.id.textViewMpress);
        textViewMdesc = findViewById(R.id.textViewMdesc);
        textViewMwind = findViewById(R.id.textViewMwind);
        textViewMdt = findViewById(R.id.textViewMdt);

        readCsvFromRaw();
        readFromPrefs();

        if (curLat != null & curLon != null & curCityName != null)
            request(curLat, curLon);
        else
            textViewMcity.setText("Не указан город!");

        buttonCity.setOnClickListener(v -> {
            List<City> list = findCity("kazan");
            textViewCity.setText(list.toString());
        });

        buttonLocate.setOnClickListener(v -> {
            handleGPS();
        });
    }

    private void request(double lat, double lon) {
        Call<Request> callList = networkService.getJSONApi().getWeather(lat, lon,
                getResources().getString(R.string.app_id));

        callList.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (!response.isSuccessful()) {
                    textViewMcity.setText("Code: " + response.code());
                    return;
                }
                if (response.body().getDaily() != null){
                    List<Daily> list = response.body().getDaily();

                    for (int i = 0; i < list.size(); i++) {
                        Daily item = list.get(i);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM", new Locale("ru"));
                        String date = dateFormat.format( new Date(item.getDt() * 1000));
                        String descr = item.getWeather().get(0).getDescription().toLowerCase();

                        OurWeather day = new OurWeather(
                                date.substring(0, 1).toUpperCase() + date.substring(1),
                                String.format("%.0f", item.getTemp().getDay()),
                                String.format("%.0f", item.getPressure() / 1.333),
                                item.getHumidity(),
                                String.format("%.0f", item.getWindSpeed()),
                                descr.substring(0, 1).toUpperCase() + descr.substring(1));
                        weatherList.add(day);
                    }

                    initData();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                textViewMcity.setText("Failure: " + t);
            }
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

    public void initData() {
        OurWeather today = weatherList.get(0);
        textViewMhum.setText(String.valueOf(today.getHumidity()) + "%");
        textViewMtemp.setText(String.valueOf(today.getTemperature()) + "°C");
        textViewMpres.setText(String.valueOf(today.getPressure()) + "мм.рт");
        textViewMdesc.setText(String.valueOf(today.getDescription()));
        textViewMwind.setText(String.valueOf(today.getWindSpeed()) + "м/с");
        textViewMdt.setText(String.valueOf(today.getDay()));
        textViewMcity.setText(curCityName);

        listView.setAdapter(new ListAdapter(getApplicationContext(), weatherList.subList(1, weatherList.size() - 1)));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(getApplicationContext(), "Это будет хороший день!", Toast.LENGTH_SHORT).show();
        });
    }

    public void readCsvFromRaw() {
        new Thread(() -> {
            InputStream is = getResources().openRawResource(R.raw.worldcities_clear);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
                while (reader.ready()) {
                    String[] data = reader.readLine().split(";");
                    cityList.add(new City(
                            Double.valueOf(data[0]),
                            data[1],
                            data[2],
                            Double.valueOf(data[3]),
                            Double.valueOf(data[4])));
                }
            } catch (IOException e) {
                textViewMcity.setText(e.getMessage());
            }
        }).start();
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
                textViewMcity.setText("GPS not enabled");
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
                        textViewMcity.setText(e.getMessage());
                    }

                    updLocation(locationObj);

                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(this, "We need that permission", Toast.LENGTH_LONG).show();
                } else {
                    textViewMcity.setText("Not working without permission");
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
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("lat", String.valueOf(curLat)).apply();
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("lon", String.valueOf(curLon)).apply();
        getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).edit().putString("cityname", String.valueOf(curCityName)).apply();
    }

    public void readFromPrefs() {
        curLat = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("lat", "0"));
        curLon = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("lon", "0"));
        curCityName = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("cityname", "0");
    }
}