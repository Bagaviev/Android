package com.example.fourthexample.fragments;

import android.net.IpSecManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fourthexample.R;

public class HandlerFragment extends Fragment {
    /*
     Кароче это более низкоуровневая ебень, есть handler - прослойка через которую можно отправлять мессаджи
     в очередь и ей же обрабатывать .sendMessage() .post() ну и сам handleMessage()
     Там где создается handler тот поток и будет обслуживать очередь (если констр без парам, то это UI main looper)
     можно вручную создать looper для потока (нежелательно) и он обеспечит бесконечный цикл для повторного использования этого
     потока извне.

     Вообще стандартный Use case такой, что есть handler для UI куда толкаются результаты из бекграунда для отрисовки
     прямо таки как AsyncTask

     Более общие подходы: (1) сделать задачу не в UI потоке, (2) отложить выполнение задачи на опр время

     Tip: handler подсасывается к какому то looper'у - будь то кастомному для своего треда (наверно типа когда отдельный
     поток обслуживает задачи тоже runnabl'ы и далее эти результаты в них отправляются в дефолтный handler -
     пример аналог самописный threadpoolExecutor'a, см пример mail.ru технополис) или же main ui

     а еще есть прослойка HandlerThread уже готовая, но да и хер с ней

     я тут заюзаю простой кейс messages и handler на ui
     */

    TextView textView;
    ProgressBar progressBar;
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.handler_fragment, null);
        textView = view.findViewById(R.id.textView2);
        progressBar = view.findViewById(R.id.progressBar2);
        button = view.findViewById(R.id.button2);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button.setOnClickListener(v -> {
            textView.setText("Started");
            progressBar.setVisibility(View.VISIBLE);
            Thread thread = new Thread(new WorkingClass());
            thread.start();
        });
    }

    public class WorkingClass implements Runnable {
        final static int SUCCESS = 1;
        final static int FAIL = 0;

        @Override
        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    Message msg = Message.obtain();
                        msg.what = SUCCESS;
                        msg.arg1 = i;

                    handler.sendMessage(msg);
                    Thread.sleep(200);
                }
                handler.sendEmptyMessage(2);
            } catch (InterruptedException e) {
                handler.sendEmptyMessage(FAIL);
            }

        }

        public final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case SUCCESS:
                        progressBar.setProgress(msg.arg1);
                        break;
                    case FAIL:
                        textView.setText("Some error occurred, sorry");
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        textView.setText("Finished");
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                }
                return false;
            }
        });
    }
}
