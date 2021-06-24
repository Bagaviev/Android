package com.example.fifthexample.fragments;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.fifthexample.R;

import static android.content.Context.MODE_PRIVATE;

public class DBFragment extends Fragment {
    SQLiteDatabase db;
    Cursor query;
    ToggleButton buttonConnect;
    Button buttonCreate;
    Button buttonInsert;
    Button buttonSelect;
    Button buttonDelete;
    EditText editText;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.db_fragment, null);
        buttonConnect = view.findViewById(R.id.buttonConnect);
        editText = view.findViewById(R.id.editTextFilter);      // вместо атрибута text нужно юзать hint!
        buttonCreate = view.findViewById(R.id.buttonCreate);
        buttonInsert = view.findViewById(R.id.buttonInsert);
        buttonSelect = view.findViewById(R.id.buttonSelect);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
//        textView.setText(String.valueOf((int) Runtime.getRuntime().maxMemory() / (1024 * 1024)));     размер JVM Heap
        buttonConnect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) connect();
            else close();
        });

        buttonCreate.setOnClickListener((v) -> create());
        buttonInsert.setOnClickListener((v) -> insert());
        buttonSelect.setOnClickListener((v) -> select());
        buttonDelete.setOnClickListener((v) -> delete());
        super.onViewCreated(view, savedInstanceState);
    }

    public void connect() {
        db = getActivity().getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        textView.setText("Connection established at:\n" + getContext().getDatabasePath("app.db"));
        // не нужно обращаться к ip localhost, существует абстракция над этим - Room (типа для удобства)
    }

    public void close() {   // todo можно еще прехватывать java.lang.IllegalStateException
        if (db != null) {
            db.close();
            textView.setText("Connection closed");
        }
    }

    public void create() {
        if (db != null) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)");
                textView.setText("Tables were created");
            } catch (SQLException e) {
                textView.setText(e.getMessage());
            }
        }
    }

    public void insert() {
        if (db != null) {
            try {
                db.execSQL("INSERT INTO users VALUES ('Tom Smith', 23);");
                db.execSQL("INSERT INTO users VALUES ('Bulat Bagaviev', 24);");
                db.execSQL("INSERT INTO users VALUES ('Dirty Whore', 32);");
                textView.setText("Values were inserted");
            } catch (SQLException e) {
                textView.setText(e.getMessage());
            }
        }
    }

    public void select() {
        textView.setText("");
        if (db != null) {
            try {
                String filterAge = "0";
                if (editText.getText().length() > 0)
                    filterAge = String.valueOf(editText.getText());

                query = db.rawQuery("SELECT * FROM users WHERE age > ?;", new String[] {filterAge});
                while (query.moveToNext()) {
                    String name = query.getString(0);
                    int age = query.getInt(1);
                    textView.append("Name: " + name + " Age: " + age + "\n");
                }
                query.close();
            } catch (SQLException e) {
                textView.setText(e.getMessage());
            }
        }
    }

    public void delete() {
        if (db != null) {
            try {
                db.execSQL("DROP TABLE IF EXISTS users;");
                textView.setText("Tables were dropped");
            } catch (SQLException e) {
                textView.setText(e.getMessage());
            }
        }
    }
}
