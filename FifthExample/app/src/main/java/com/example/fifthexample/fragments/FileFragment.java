package com.example.fifthexample.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fifthexample.R;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileFragment extends Fragment {
    Button save;
    Button load;
    Button delete;
    TextView textViewInfo;
    TextView textViewShow;
    EditText editText;
    String filename = "file.txt";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_fragment, null);
        textViewInfo = view.findViewById(R.id.textView1);
        textViewShow = view.findViewById(R.id.textform);
        save = view.findViewById(R.id.save);
        load = view.findViewById(R.id.load);
        delete = view.findViewById(R.id.delete);
        editText = view.findViewById(R.id.editText);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        save.setOnClickListener((v) -> saveFile());
        load.setOnClickListener((v) -> loadFile());
        delete.setOnClickListener((v) -> deleteFile());
        super.onViewCreated(view, savedInstanceState);
    }

    public void saveFile() {   //  getFilesDir()  ->  /data/user/0/com.example.fifthexample/files
        String data = new String();
        Editable ed;

        try (FileOutputStream fos = getContext().openFileOutput(filename, Context.MODE_APPEND);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos))) {
            textViewInfo.setText("File created at: " + getContext().getFilesDir() + "/" + filename);

            if ((ed = editText.getText()) != null)
                data = editText.getText().toString() + "\n";

            bufferedWriter.write(data);
            bufferedWriter.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        try(FileInputStream fis = getContext().openFileInput(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis))) {
            textViewInfo.setText("File loaded from: " + getContext().getFilesDir() + "/" + filename);
            textViewShow.setText("");

            while (bufferedReader.ready())
                textViewShow.append(bufferedReader.readLine() + "\n");

        } catch (FileNotFoundException e) {
            textViewShow.setText(e.getMessage());
        } catch (IOException e) {
            textViewShow.setText(e.getMessage());
        }
    }

    public void deleteFile() {
        getContext().deleteFile(filename);
        textViewShow.setText("");
        textViewInfo.setText("File was deleted!");
    }
}
