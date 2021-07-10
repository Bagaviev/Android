package com.example.rentateamtask.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.rentateamtask.R;
import com.example.rentateamtask.pojo.UserData;
import java.util.List;

public class ListAdapter extends ArrayAdapter<UserData> {
    LayoutInflater mInflater;

    public ListAdapter(Context context, List<UserData> users) {
        super(context, R.layout.list_item, users);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = mInflater.inflate(R.layout.list_item, parent, false);
        TextView textViewFn = view.findViewById(R.id.textViewFisrtname);
        TextView textViewLn = view.findViewById(R.id.textViewLastname);

        UserData user = getItem(position);      // тут логика скрыта
        textViewFn.setText(user.getFirstName());
        textViewLn.setText(user.getLastName());
        Log.e("LOGG", "внутри getView");
        return view;
    }
}
