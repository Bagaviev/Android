package com.example.rentateamtask;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;

import com.example.rentateamtask.model.pojo.UserData;
import com.example.rentateamtask.presenter.utils.ListAdapter;
import java.util.List;

public interface Contract {
    interface View {
        void showMessage(String message);
        void showProgress();
        void hideProgress();
    }

    interface Presenter {
        void sendMessage(String message);
        void enableProgress();
        void disableProgress();
        void getData();
        ListAdapter createAdapter(Context context);
        ListAdapter getAdapter();
        List<UserData> getUserListState();
        UserData getSelectedUser();
        void setSelectedUser(UserData selectedUser);
    }

    interface Model {
        void loadData();
        void dbRequest();
        void netRequest();
    }
}
