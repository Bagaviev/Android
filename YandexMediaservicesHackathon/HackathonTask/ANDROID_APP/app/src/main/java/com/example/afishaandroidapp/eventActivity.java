package com.example.afishaandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.afishaandroidapp.dao.Event;
import com.example.afishaandroidapp.fragments.afishaFragment;

import static com.example.afishaandroidapp.util.EventAdapter.df;

public class eventActivity extends AppCompatActivity {
    private Button buttonBuy;
    private ImageView imageViewCard;
    private TextView textViewNameCard;
    private TextView textViewDateCard;
    private TextView textViewTypeCar;
    private TextView textViewPriceCard;
    private TextView textViewDescr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        buttonBuy = findViewById(R.id.buttonBuy);
        imageViewCard = findViewById(R.id.imageViewCard);
        textViewNameCard = findViewById(R.id.textViewNameCard);
        textViewDateCard = findViewById(R.id.textViewDateCard);
        textViewTypeCar = findViewById(R.id.textViewTypeCard);
        textViewPriceCard = findViewById(R.id.textViewPriceCard);
        textViewDescr = findViewById(R.id.textViewDescrCard);
    }

    @Override
    protected void onStart() {
        Event event = (Event) getIntent().getSerializableExtra(afishaFragment.EXTRA_MESSAGE);
        imageViewCard.setImageResource(event.getFlagResImg());
        textViewNameCard.setText(event.getName());
        textViewDateCard.setText("Когда: " + df.format(event.getDate()));
        textViewTypeCar.setText("Категория: " + String.valueOf(event.getType().getName()));
        textViewPriceCard.setText("Цена: " + String.valueOf(event.getPrice()));
        textViewDescr.setMovementMethod(new ScrollingMovementMethod());
        textViewDescr.setText("Описание\n" + event.getDescription());
        super.onStart();
    }
}