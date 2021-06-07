package com.example.secondexample;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerView extends AppCompatActivity {   // во первых создаем разметку для одного элемента списка в layout/rec_view_item.xml
    private List<State> states;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {    // затем
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recv);

        StateAdapter.OnItemClickListener stateClickLister = new StateAdapter.OnItemClickListener() {    // интерфейс внутренний, создаем экземпляр класса StateAdapter
            @Override                                                   // наследующий OnItemClickListener и переопределяем его поведение
            public void onItemClick(State state, int position) {
                Toast.makeText(getApplicationContext(), "Был выбран пункт " + state.getModel(),
                        Toast.LENGTH_SHORT).show();
            }
        };

        StateAdapter adapter = new StateAdapter(stateClickLister, getListResourses());
        recyclerView.setAdapter(adapter);

        // свой красивый разделитель делаем
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getDrawable(R.drawable.own_vertical_divider));
        recyclerView.addItemDecoration(itemDecoration);
    }

    public List<State> getListResourses() {
        states = new ArrayList<>();
        String[] carNames = getResources().getStringArray(R.array.Cars);

        states.add(new State(carNames[0], R.drawable.volvo));
        states.add(new State(carNames[1], R.drawable.ferra));
        states.add(new State(carNames[2], R.drawable.gtr));
        states.add(new State(carNames[3], R.drawable.minik));
        states.add(new State(carNames[4], R.drawable.mustang));
        states.add(new State(carNames[5], R.drawable.porsh));
        states.add(new State(carNames[6], R.drawable.wiesmann));

        return states;
    }
}