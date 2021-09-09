package com.example.weatherapp.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.weatherapp.R;
import com.example.weatherapp.pojo.OurWeather;

import java.util.List;

public class ListAdapter extends ArrayAdapter<OurWeather> {
    LayoutInflater mInflater;

    public ListAdapter(Context context, List<OurWeather> weather) {
        super(context, R.layout.list_item, weather);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = mInflater.inflate(R.layout.list_item, parent, false);

        TextView textViewDt = view.findViewById(R.id.textViewDay);
        TextView textViewDescr = view.findViewById(R.id.textViewDescr);
        TextView textViewTemp = view.findViewById(R.id.textViewTemp);

        OurWeather weather = getItem(position);
        textViewDt.setText(weather.getDay());
        textViewDescr.setText(weather.getDescription());
        textViewTemp.setText(weather.getTemperature() + "°C");
        return view;
    }
}

