package com.example.rentateamtask.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.rentateamtask.Contract;
import com.example.rentateamtask.R;
import com.example.rentateamtask.model.Model;
import com.example.rentateamtask.model.pojo.UserData;
import com.example.rentateamtask.model.service.network.NetworkService;
import com.example.rentateamtask.model.service.db.AppDatabase;
import com.example.rentateamtask.model.service.db.UserDao;
import com.example.rentateamtask.presenter.Presenter;
import com.example.rentateamtask.view.UserPage;
import com.example.rentateamtask.presenter.utils.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment implements Contract.View {
    public static final String EXTRA_MESSAGE = "com.example.rentateamtask.MESSAGE";
    private Contract.Presenter presenter;
    ProgressBar progressBar;
    TextView textViewMsg;
    ListView listView;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Model model = Model.getInstance(getActivity());      // DI moment
        presenter = new Presenter(model, this);
        model.setPresenter(presenter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userlist_fragment, null);
        listView = view.findViewById(R.id.listview);
        button = view.findViewById(R.id.button);
        progressBar = view.findViewById(R.id.progressBar);
        textViewMsg = view.findViewById(R.id.textViewMsg);
        textViewMsg.setMovementMethod(new ScrollingMovementMethod());
        hideProgress();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView.setAdapter(presenter.createAdapter(getContext()));

        button.setOnClickListener(v -> {
            showMessage("");
            presenter.getData();
        });

        listView.setOnItemClickListener((parent, viewL, position, id) -> {
            presenter.setSelectedUser(presenter.getUserListState().get(position));
            openUserPage();
            showMessage("Выбран: " + presenter.getSelectedUser().getFirstName());
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void openUserPage() {
        Intent intent = new Intent(getActivity(), UserPage.class);
        intent.putExtra(EXTRA_MESSAGE, presenter.getSelectedUser());
        startActivity(intent);
    }

    @Override
    public void showMessage(String message) {
        textViewMsg.setText(message);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        // тут могло бы быть зануление ссылки на контекст, но не разобрался с этим, а при это работает неплохо
        super.onDestroy();
    }
}
