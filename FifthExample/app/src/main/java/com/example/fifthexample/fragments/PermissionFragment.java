package com.example.fifthexample.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.health.PackageHealthStats;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.fifthexample.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class PermissionFragment extends Fragment {
    private final int GPS_PERMISSION_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location location;

    int cnt;
    FloatingActionButton buttonP;
    Button buttonS;
    TextView textViewData;
    TextView textViewGpsStatus;
    TextView textViewGpsCnt;
    TextView textViewPermission;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        View view = inflater.inflate(R.layout.permission_fragment, null);
        buttonP = view.findViewById(R.id.floatButton);
        buttonS = view.findViewById(R.id.buttonStop);
        textViewData = view.findViewById(R.id.textViewData);
        textViewGpsStatus = view.findViewById(R.id.textViewStatus);
        textViewGpsCnt = view.findViewById(R.id.textViewLocCnt);
        textViewPermission = view.findViewById(R.id.textViewPerm);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonP.setOnClickListener((v) -> handleGPS());
        buttonS.setOnClickListener((v) -> stop());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        if (checkPermission())
            textViewPermission.setText("Permission granted");
        else
            textViewPermission.setText("Permission not granted");

        if (gpsStatusCheck())
            textViewGpsStatus.setText("GPS enabled");   // кароче более красиво можно через broadcastreceiver сделать
        else
            textViewGpsStatus.setText("GPS not enabled");
        super.onResume();
    }

    @Override
    public void onPause() {
        if (locationListener != null)           // сворачиваем апп - апдейт останавливаем
            stop();
        super.onPause();
    }

    public void handleGPS() {
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                textViewGpsCnt.setText(String.valueOf(cnt));     // без обертки в строки не работает
                showLocation(location);
                cnt++;
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {   // костылек, чтобы не падал gpsStatusCheck()
                textViewGpsStatus.setText("GPS enabled");
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                textViewGpsStatus.setText("GPS not enabled");
            }
        };

        if (checkPermission()) {
            if (!gpsStatusCheck())
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            // чтобы красивее сделать надо юзать google play api - settings request но и вместе с гео оттуда

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            // надо делать такой updates, чтобы запускалось после отключения gps, а затем снова включения (иначе пустая ссылка)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION_CODE);
            // тут тонкость - если ActivityCompat.requestPermissions то будет работать только из активити
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    textViewPermission.setText("Permission granted");   // костылек, чтобы при согласии на permission поле обновилось
                    try {                                              // (тк вью уже отрисована и наш хелпер метод выше не поможет)
                        if (!gpsStatusCheck())
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
                        location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
                        // тоже костылек, чтобы не ругалось повторно на отсутствие проверки на права, тк тут уже проверено
                    } catch (SecurityException e) {
                        textViewPermission.setText(e.getMessage());
                    }
                    showLocation(location);
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getContext(), "We need that permission, bitch", Toast.LENGTH_LONG).show();
                } else {
                    textViewPermission.setText("Not working without permission");
                }
            }
            return;
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void showLocation(Location location) {
        // isSelectable textView сделать копируемым поле ввода текста
        textViewData.setText("lat: " + location.getLatitude()
                + "\n" + "lon: " + location.getLongitude());
    }

    public boolean gpsStatusCheck() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void stop() {
        if (locationListener != null)
            locationManager.removeUpdates(locationListener);    // стопорит только последний сервис, если наплодить, то не остановить все, которые раньше последнего
    }

    // todo разобраться что происходит с бекстеком при сворачивании приложения
}
