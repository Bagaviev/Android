package com.example.firstexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.firstexample.MESSAGE";  // такой же подход используется для intents

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // метод у родителя взяли. В методах так можно делать. А еще другие нестатические методы своего же класса вызывать без создания объекта.

        TextView textView = findViewById(R.id.textView2);
        textView.setText("Main thread");

        // getResources().getString(R.string.button_send) так юзать ресурсы

        final Button crashButton = findViewById(R.id.button1);  // вот это дорогостоящая операция кстати
        crashButton.setOnClickListener(v -> {   // button click function by code
            throw new RuntimeException("poof!"); // callback - функция, которая должна быть выполнена после какой то другой
        });
    }

    public void sendMessage(View view) {   // button click function by attribute
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void doRequest(View view) {
        Intent intent = new Intent(this, RequestActivity.class);
        startActivity(intent);
    }
}
