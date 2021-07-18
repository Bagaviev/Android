package com.example.reclinetask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Random;

// todo more beautiful win/lose msg
// todo buttons and change color app
// todo icon
// todo score
// todo add win combination backlight
// todo pack button style to theme
// todo add sound
// todo add ads

// todo other tasks

public class MainActivity extends AppCompatActivity {
    static final int[] buttonIds = new int[] { R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8};
    static String LOSE_MESSAGE = "You lose, try again!";
    static String WIN_MESSAGE = "You win!";
    static String DRAW_MESSAGE = "It's a draw!";
    static final int DOTS_TO_WIN = 3;
    static final int MAX_MOVE_CNT = 9;
    static String PLAYER_SYM = "X";
    static String AI_SYM = "O";
    static String EMPTY = "";
    static int MOVE_CNT;

    private Button[][] buttons = new Button[3][3];
    TableLayout layout;
    TextView textView;
    Button startStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (TableLayout) findViewById(R.id.main_l);
        startStop = findViewById(R.id.buttonS);
        textView =  findViewById(R.id.textViewInfo);
        initMap();
    }

    @Override
    protected void onStart() {
        startStop.setOnClickListener(v -> restartGame());
        super.onStart();
    }

    private void initMap() {
        for (int i = 0, s = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = findViewById(buttonIds[s]);
                buttons[i][j].setOnClickListener(v -> doPlayerMove(v));
                s++;
            }
        }
    }

    private void freezeMap() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setOnClickListener(null);
            }
        }
    }

    private void restartGame() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j].setText(EMPTY);
                textView.setText(EMPTY);
                textView.setBackgroundColor(Color.parseColor("#EAEAEA"));
                MOVE_CNT = 0;
                initMap();
            }
        }
    }

    private void printMessage(String str) {
        if (str.equals(WIN_MESSAGE))
            textView.setBackgroundColor(Color.parseColor("#63ff85"));
        else if (str.equals(LOSE_MESSAGE))
            textView.setBackgroundColor(Color.parseColor("#ff7070"));
        else
            textView.setBackgroundColor(Color.parseColor("#c953c0"));

        textView.setText(str);
    }

    private void doPlayerMove(View v) {
        Button b = (Button) v;

        if (b.getText().equals(EMPTY)) {
            b.setText(PLAYER_SYM);
            MOVE_CNT++;

            if (checkWin(PLAYER_SYM)) {
                freezeMap();
                printMessage(WIN_MESSAGE);
                return;
            }
            doAiMove();
        }
    }

    private void doAiMove() {
        Random random = new Random();
        int x, y;

        while (true) {
            if (MOVE_CNT >= MAX_MOVE_CNT) {
                printMessage(DRAW_MESSAGE);
                break;
            }

            x = random.nextInt(buttons.length);
            y = random.nextInt(buttons.length);

            if (buttons[x][y].getText().equals(EMPTY)) {
                buttons[x][y].setText(AI_SYM);
                MOVE_CNT++;

                if (checkWin(AI_SYM)) {
                    printMessage(LOSE_MESSAGE);
                    freezeMap();
                }

                break;
            }
        }
    }

    private boolean checkWin(String symb) {
        int winToD1 = 0,  winToD2 = 0;

        for (int i = 0; i < buttons.length; i++) {
            int winToCol = 0, winToRow = 0;

            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText() == symb) winToRow++;
                if (buttons[j][i].getText() == symb) winToCol++;
            }

            if (winToRow == DOTS_TO_WIN || winToCol == DOTS_TO_WIN) return true;

            if (buttons[i][i].getText()  == symb) winToD1++;
            if (buttons[i][buttons[i].length-i-1].getText() == symb) winToD2++;
        }

        if (winToD1 == DOTS_TO_WIN || winToD2 == DOTS_TO_WIN) return  true;
        return false;
    }
}