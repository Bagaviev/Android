package com.example.fifthexample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class DialogFragment extends Fragment {
    Button buttonD;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, null);
        buttonD = view.findViewById(R.id.buttonD);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonD.setOnClickListener((v) -> showDialog());
        super.onViewCreated(view, savedInstanceState);
    }

    public void showDialog() {      // вообще кошерно делать fragmentDialog, а не напрямую диалог, но у нас тут итак уже базовый класс - фрагмент
        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setTitle("Хочешь стать программистом?");
        adb.setMessage("Да ебись ты в рот!");
        adb.setPositiveButton("Пойти нахуй", null);
        dialog = adb.create();
        dialog.show();      // todo обработка кнопки и детали в доках дочитать
    }
}
