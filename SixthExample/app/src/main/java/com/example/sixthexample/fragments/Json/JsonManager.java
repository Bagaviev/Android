package com.example.sixthexample.fragments.Json;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.sixthexample.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonManager extends Fragment {     // в JavaRush мы сериализовали объект в json и обратно
    final static String urlAPI = "https://jsonplaceholder.typicode.com/todos/1";    // настоящее тестовое api
    OkHttpClient okHttpClient = new OkHttpClient();
    TextView textView;                          // здесь же мы готовый json запросим от сервиса и просто посмотрим на него
    Button buttonGet;                           // ну опционально может post сделаем на сервис
    Button buttonPost;                          // либо bundled JSONObject либо GSON lib

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.json_manager_fragment, null);
        textView = view.findViewById(R.id.textViewJM);
        buttonGet = view.findViewById(R.id.buttonGet);
        buttonPost = view.findViewById(R.id.buttonPost);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        buttonGet.setOnClickListener((v) -> goGet());
        buttonPost.setOnClickListener((v) -> goPost());
    }

    private void goGet() {      // сделали запрос GET для 1-го поста в API, сервер отдал json, вывели его
        new Thread(() -> {      // потом распарсили, чтобы показать, что мы крутые, и снова вывели
            try {
                textView.post(() -> textView.setText(""));
                Request request = new Request.Builder()
                        .url(urlAPI)
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                String result = response.body().string();       // странно, что формат читаемый удобно, наверно так api отдает уже

                textView.post(() -> {
                    textView.setText(result);
                    textView.append("\n=========\n");
                    textView.append(parseJson(result));
                });
            } catch (IOException e) {
                textView.post(() -> textView.setText(e.toString()));
            }
        }).start();
    }

    private void goPost() {     // взяли сконструировали json, и PUT запрос на сервер, он отдал ответ - вывели
        new Thread(() -> {
            try {
                textView.post(() -> textView.setText(""));
                String json = constructJson(1, 1, "UPD", false).toString();

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json"), json);

                Request request = new Request.Builder()
                        .url(urlAPI)
                        .method("PUT", body)  // .post(body) для обычных POST запросов
                        .addHeader("Content-type", "application/json; charset=UTF-8")
                        .build();

                Response response = okHttpClient.newCall(request).execute();
                String result = response.body().string();

                textView.post(() -> textView.setText(result));

            } catch (IOException e) {
                textView.post(() -> textView.setText(e.toString()));
            }
        }).start();
    }

    private String parseJson(String jsonString) {       // расчленяет json до атрибутов (хардкодная версия, может быть
        StringBuilder sb = new StringBuilder();;        // сделана лучше через Jackson)
        try {
            JSONObject obj = new JSONObject(jsonString);    // native Android, not lib
            sb.append("userId = " + obj.getInt("userId") + "\n");
            sb.append("id = " + obj.getInt("id") + "\n");
            sb.append("title = " + obj.getString("title") + "\n");
            sb.append("completed = " + obj.getBoolean("completed") + "\n");
        } catch (JSONException e) {
            textView.setText(e.toString());
        }
        return sb.toString();
    }

    private JSONObject constructJson(int userId, int id, String title, boolean completed) {     // создали объект json руками
        JSONObject obj = null;
        try {
            obj = new JSONObject();
            obj.put("userId", userId);
            obj.put("id", id);
            obj.put("title", title);
            obj.put("completed", completed);
        } catch (JSONException e) {
            textView.setText(e.toString());
        }
        return obj;
    }
}
