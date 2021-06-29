package com.example.sixthexample.fragments.ContentProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sixthexample.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentProviderHelper extends Fragment {
    private final int CONTACTS_PERMISSION_CODE = 1;
    List<String> contacts = new ArrayList<>();
    ListView listView;
    TextView textView;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_provider_fragment, null);
        textView = view.findViewById(R.id.tv_1_CP);
        listView = view.findViewById(R.id.listViewCP);
        button = view.findViewById(R.id.buttonCP);

        if (checkPermission())
            loadContacts();
        else
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACTS_PERMISSION_CODE);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        button.setOnClickListener((v) -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, contacts);
            listView.setAdapter(adapter);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case CONTACTS_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        loadContacts();
                    } catch (SecurityException e) {
                    }
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(getContext(), "We need that permission, bitch", Toast.LENGTH_LONG).show();
                } else {
                    textView.setText("Not working without permission");
                }
            }
            return;
        }
    }

    public boolean checkPermission() {
        return ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                // получаем каждый контакт
                String contact = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                // добавляем контакт в список
                contacts.add(contact);
            }
            cursor.close();
        }
    }
}