package com.example.afishaandroidapp.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.example.afishaandroidapp.R;
import com.example.afishaandroidapp.dao.Event;
import com.example.afishaandroidapp.dao.Repository;
import com.example.afishaandroidapp.eventActivity;
import com.example.afishaandroidapp.util.EventAdapter;

public class afishaFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "com.example.afishaandroid.MESSAGE";
    private static Repository repository = Repository.getInstance();
    private RecyclerView recyclerView;

    public afishaFragment() {
        super(R.layout.afisha_fragment);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.afisha_fragment, null);
        recyclerView = view.findViewById(R.id.recv);

        EventAdapter.OnItemClickListener stateClickLister = new EventAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Event event, int position) {
                openUserPage(event);
            }
        };

        EventAdapter adapter = new EventAdapter(stateClickLister, repository.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(getActivity().getDrawable(R.drawable.own_vertical_divider));
        recyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void openUserPage(Event event) {
        Intent intent = new Intent(getActivity(), eventActivity.class);
        intent.putExtra(EXTRA_MESSAGE, event);
        startActivity(intent);
    }
}
