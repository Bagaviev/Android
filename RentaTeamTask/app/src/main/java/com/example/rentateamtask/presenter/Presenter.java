package com.example.rentateamtask.presenter;

import android.content.Context;
import com.example.rentateamtask.Contract;
import com.example.rentateamtask.model.pojo.UserData;
import com.example.rentateamtask.presenter.utils.ListAdapter;
import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contract.Presenter {
    private List<UserData> userListState = new ArrayList<>();
    private Contract.Model model;
    private Contract.View view;
    private UserData selectedUser;
    private ListAdapter adapter;

    public Presenter(Contract.Model model, Contract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void sendMessage(String message) {
        view.showMessage(message);
    }

    @Override
    public void enableProgress() {
        view.showProgress();
    }

    @Override
    public void disableProgress() {
        view.hideProgress();
    }

    @Override
    public void getData() {
        model.loadData();
    }

    @Override
    public List<UserData> getUserListState() {
        return userListState;
    }

    @Override
    public ListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public ListAdapter createAdapter(Context context) {
        adapter = new ListAdapter(context, userListState);
        return adapter;
    }

    @Override
    public UserData getSelectedUser() {
        return selectedUser;
    }

    @Override
    public void setSelectedUser(UserData selectedUser) {
        this.selectedUser = selectedUser;
    }
}
