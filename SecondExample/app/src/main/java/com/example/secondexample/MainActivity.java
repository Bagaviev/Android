package com.example.secondexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] catNames = getResources().getStringArray(R.array.Cats);    // это просто список строк будет
        ListView listView = findViewById(R.id.listview);    // чтобы чтото посерьезнее (напр картинки и пара текстов) отображать, нужно
        TextView textView = findViewById(R.id.textView3);   // делать класс state и его отображать, нужно делать класс state и его в адаптер помещать

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, catNames);
        listView.setAdapter(adapter);           // сюда еще можно прикрутить оптимизацию в виде viewHolder

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = catNames[position];
                textView.setText("Выбран: " + selectedItem);
            }
        });
    }

    public void changeActivity(View view) {
        Intent intent = new Intent(this, MyRecyclerView.class);
        startActivity(intent);
    }
}