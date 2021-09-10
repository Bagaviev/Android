package com.example.weatherapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    NetworkService networkService;

    static List<OurWeather> weatherList = new ArrayList<>();
    static List<City> cityList = new ArrayList<>();

    ListView listViewMain;
    TextView textViewMcity;     // log textView, all errors will be there
    TextView textViewMhum;
    TextView textViewMtemp;
    TextView textViewMpres;
    TextView textViewMdesc;
    TextView textViewMwind;
    TextView textViewMdt;
    Button buttonIntent;
    ProgressBar progressBar1;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkService = NetworkService.getInstance();

        listViewMain = findViewById(R.id.listView);
        textViewMcity = findViewById(R.id.textViewMcityLog1);
        textViewMhum = findViewById(R.id.textViewMhum);
        textViewMtemp = findViewById(R.id.textViewMtemp);
        textViewMpres = findViewById(R.id.textViewMpress);
        textViewMdesc = findViewById(R.id.textViewMdesc);
        textViewMwind = findViewById(R.id.textViewMwind);
        textViewMdt = findViewById(R.id.textViewMdt);
        buttonIntent = findViewById(R.id.buttonIntent);
        progressBar1 = findViewById(R.id.progressBar1);

        hideElements(progressBar1, true);
        hideElements(listViewMain, false);

        if (cityList.size() == 0)       // спасло от SIGSEGV. Видимо памяти не хватало. Это второй кейс когда встречается эта ебала. Первый был когда много данных грузил в sqlite (или корявые данные были)
            readCsvFromRaw();

        readFromPrefs();

        if (LocationActivity.curLat != 0 & LocationActivity.curLon != 0 & LocationActivity.curCityName != "0")
            request(LocationActivity.curLat, LocationActivity.curLon);      // state по гео хранится во втором классе
        else
            openCitySelectorActivity();

        buttonIntent.setOnClickListener(v -> openCitySelectorActivity());
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
                    hideElements(progressBar1, false);
                    hideElements(listViewMain, true);
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                textViewMcity.setText("Нет интернета");
                hideElements(progressBar1, false);
            }
        });
    }

    public void initData() {
        OurWeather today = weatherList.get(0);
        textViewMhum.setText(today.getHumidity() + "%");
        textViewMtemp.setText(today.getTemperature() + "°C");
        textViewMpres.setText(today.getPressure() + "мм.рт");
        textViewMdesc.setText(String.valueOf(today.getDescription()));
        textViewMwind.setText(today.getWindSpeed() + "м/с");
        textViewMdt.setText(String.valueOf(today.getDay()));
        textViewMcity.setText(LocationActivity.curCityName);

        listViewMain.setAdapter(new ListAdapter(getApplicationContext(), weatherList.subList(1, weatherList.size() - 1)));
        listViewMain.setOnItemClickListener((parent, view, position, id) -> {
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

    public void openCitySelectorActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void readFromPrefs() {
        LocationActivity.curLat = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("lat", "0"));
        LocationActivity.curLon = Double.parseDouble(getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("lon", "0"));
        LocationActivity.curCityName = getSharedPreferences("MY_PREFERENCE", MODE_PRIVATE).getString("cityname", "0");
    }

    public static void hideElements(View view, Boolean marker) {
        if (marker) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        networkService = null;
    }
}