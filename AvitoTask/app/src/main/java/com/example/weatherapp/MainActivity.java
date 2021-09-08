package com.example.weatherapp;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.network.NetworkService;
import com.example.weatherapp.pojo.City;
import com.example.weatherapp.pojo.OurWeather;
import com.example.weatherapp.pojo.api_entities.Daily;
import com.example.weatherapp.pojo.api_entities.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NetworkService networkService;

    List<OurWeather> weatherList = new ArrayList<>();
    List<City> cityList = new ArrayList<>();

    Button buttonDist;
    Button buttonWeather;
    Button buttonCity;
    TextView textViewDist;
    TextView textViewWeather;
    TextView textViewCity;

    double lat1 = 55.693702;
    double lon1 = 37.658820;
    double lat2 = 56.102816;
    double lon2 = 40.379595;

    float result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkService = NetworkService.getInstance();

        buttonDist = findViewById(R.id.buttonDist);
        buttonWeather = findViewById(R.id.buttonWeather);
        buttonCity = findViewById(R.id.buttonCity);
        textViewDist = findViewById(R.id.textViewDist);
        textViewWeather = findViewById(R.id.textViewWeather);
        textViewCity = findViewById(R.id.textViewCity);

        readCsvFromRaw();

        buttonDist.setOnClickListener(v -> {
            result = calcDistance(lat1, lon1, lat2, lon2);
            textViewDist.setText(String.valueOf(result));
        });

        buttonWeather.setOnClickListener(v -> request(25.964051, 18.322344));

        buttonCity.setOnClickListener(v -> {
            List<City> list = findCity("kazan");
            textViewCity.setText(list.toString());
        });
    }

    private void request(double lat, double lon) {
        Call<Request> callList = networkService.getJSONApi().getWeather(lat, lon,
                getResources().getString(R.string.app_id));

        callList.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (!response.isSuccessful()) {
                    textViewWeather.setText("Code: " + response.code());
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
                    textViewWeather.setText(weatherList.toString());
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                textViewWeather.setText("Failure: " + t);
            }
        });
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
                e.printStackTrace();
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
}