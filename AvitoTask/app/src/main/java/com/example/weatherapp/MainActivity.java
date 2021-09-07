package com.example.weatherapp;

import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weatherapp.network.NetworkService;
import com.example.weatherapp.pojo.api_entities.Daily;
import com.example.weatherapp.pojo.api_entities.Request;
import com.example.weatherapp.pojo.our_entity.OurWeather;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<OurWeather> weatherList = new ArrayList<>();
    NetworkService networkService = NetworkService.getInstance();
    Button buttonDist;
    TextView textViewDist;
    Button buttonWeather;
    TextView textViewWeather;

    double lat1 = 55.693702;
    double lon1 = 37.658820;
    double lat2 = 56.102816;
    double lon2 = 40.379595;

    float result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDist = findViewById(R.id.buttonDist);
        textViewDist = findViewById(R.id.textViewDist);
        buttonWeather = findViewById(R.id.buttonWeather);
        textViewWeather = findViewById(R.id.textViewWeather);

        buttonDist.setOnClickListener(v -> {
            result = calcDistance(lat1, lon1, lat2, lon2);
            textViewDist.setText(String.valueOf(result));
        });

        buttonWeather.setOnClickListener(v -> request(25.964051, 18.322344));
    }

    private void request(double lat, double lon) {
        Call<Request> callList = networkService.getJSONApi().getWeather(lat, lon,
                getResources().getString(R.string.app_id));

        callList.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (!response.isSuccessful()){
                    textViewWeather.setText("Code: " + response.code());
                    return;
                }
                if (response.body().getDaily() != null){
                    List<Daily> list = response.body().getDaily();

                    for (int i = 0; i < list.size(); i++) {
                        Daily item = list.get(i);
                        OurWeather day = new OurWeather(
                            item.getDt(),
                            item.getTemp().getDay(),
                            item.getPressure(),
                            item.getHumidity(),
                            item.getWindSpeed(),
                            item.getWeather().get(0).getDescription());
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

    public void changeCity() {

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