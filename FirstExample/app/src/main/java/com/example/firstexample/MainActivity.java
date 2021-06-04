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
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // метод у родителя взяли. В методах так можно делать. А еще другие нестатические методы своего же класса вызывать без создания объекта.

        textView = findViewById(R.id.textView2);
        textView.setText("Main thread");

        final Button crashButton = findViewById(R.id.button1);  // вот это дорогостоящая операция кстати
        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new RuntimeException("poof!"); // callback - функция, которая должна быть выполнена после какой то другой
            }
        });

        Thread thread = new Thread(new WorkingThread());
        thread.start();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    class WorkingThread implements Runnable {   // отдельный поток
        @Override
        public void run() {
            try {
                Thread.sleep(3000); // imagine that this long processs
                textView.post(() -> textView.setText("From another thread"));       // вернули резалт в main UI
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
