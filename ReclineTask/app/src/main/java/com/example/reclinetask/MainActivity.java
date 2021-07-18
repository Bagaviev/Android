package com.example.reclinetask;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

// todo more beautiful win/lose msg
// todo buttons and change color app
// todo score
// todo pack button style to theme

// todo remove appbar
// todo other tasks

public class MainActivity extends AppCompatActivity {
    private final int[] buttonIds = new int[] { R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8};

    private final static String LOSE_MESSAGE = "You lose, try again!";
    private final static String WIN_MESSAGE = "You win!";
    private final static String DRAW_MESSAGE = "It's a draw!";
    private final static String CLICK = "Click";
    private static final int DOTS_TO_WIN = 3;
    private static final int MAX_MOVE_CNT = 9;
    private static String PLAYER_SYM = "X";
    private static String AI_SYM = "O";
    private static String EMPTY = "";
    private static int MOVE_CNT;

    private Button[][] buttons = new Button[3][3];
    private MediaPlayer mediaPlayer;
    private TableLayout layout;
    private TextView textView;
    private Button startStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (TableLayout) findViewById(R.id.main_l);
        startStop = findViewById(R.id.buttonS);
        textView = findViewById(R.id.textViewInfo);
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
                buttons[i][j].setBackgroundColor(Color.parseColor("#FF6200EE"));
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
            initAudio(CLICK);
            mediaPlayer.start();
            b.setText(PLAYER_SYM);
            MOVE_CNT++;

            if (checkWin(PLAYER_SYM)) {
                freezeMap();
                initAudio(WIN_MESSAGE);
                mediaPlayer.start();
                printMessage(WIN_MESSAGE);
                colorize(PLAYER_SYM);
                return;
            }
            doAiMove();
        }
    }

    private void initAudio(String type) {
        switch (type) {
            case DRAW_MESSAGE:
                mediaPlayer = MediaPlayer.create(this, R.raw.draw);
                break;
            case WIN_MESSAGE:
                mediaPlayer = MediaPlayer.create(this, R.raw.win);
                break;
            case LOSE_MESSAGE:
                mediaPlayer = MediaPlayer.create(this, R.raw.lose);
                break;
            default:
                mediaPlayer = MediaPlayer.create(this, R.raw.click);
                break;
        }
    }

    private void doAiMove() {
        Random random = new Random();
        int x, y;

        while (true) {
            if (MOVE_CNT >= MAX_MOVE_CNT) {
                printMessage(DRAW_MESSAGE);
                initAudio(DRAW_MESSAGE);
                mediaPlayer.start();
                freezeMap();
                return;
            }

            x = random.nextInt(buttons.length);
            y = random.nextInt(buttons.length);

            if (buttons[x][y].getText().equals(EMPTY)) {
                buttons[x][y].setText(AI_SYM);
                MOVE_CNT++;

                if (checkWin(AI_SYM)) {
                    freezeMap();
                    initAudio(LOSE_MESSAGE);
                    mediaPlayer.start();
                    printMessage(LOSE_MESSAGE);
                    colorize(AI_SYM);
                }

                break;
            }
        }
    }

    private boolean checkWin(String symb) {
        int winToD1 = 0, winToD2 = 0;

        for (int i = 0; i < buttons.length; i++) {
            int winToCol = 0, winToRow = 0;

            for (int j = 0; j < buttons[i].length; j++) {
                if (buttons[i][j].getText() == symb)
                    winToRow++;

                if (buttons[j][i].getText() == symb)
                    winToCol++;
            }

            if (winToRow == DOTS_TO_WIN || winToCol == DOTS_TO_WIN) return true;

            if (buttons[i][i].getText() == symb)
                winToD1++;

            if (buttons[i][buttons[i].length-i-1].getText() == symb)
                winToD2++;
        }

        if (winToD1 == DOTS_TO_WIN || winToD2 == DOTS_TO_WIN) return true;
        return false;
    }

    private String countWinStreak(String sym) {
        String winStreak = new String();

        for (int i = 0, j = i; i < buttons.length; i++) {
            boolean horizontalCondition = buttons[i][j].getText().equals(sym)
                    & buttons[i][j + 1].getText().equals(sym)
                    & buttons[i][j + 2].getText().equals(sym);

            boolean verticalCondition  = buttons[j][i].getText().equals(sym)
                    & buttons[j + 1][i].getText().equals(sym)
                    & buttons[j + 2][i].getText().equals(sym);

            boolean mainDiagCondition = buttons[j][j].getText().equals(sym)
                                      & buttons[j + 1][j + 1].getText().equals(sym)
                                      & buttons[j + 2][j + 2].getText().equals(sym);

            boolean diagCondition = buttons[j][j + 2].getText().equals(sym)
                                  & buttons[j + 1][j + 1].getText().equals(sym)
                                  & buttons[j + 2][j].getText().equals(sym);

            if (horizontalCondition & i == 0)
                winStreak = "h0";
            else if (horizontalCondition & i == 1)
                winStreak = "h1";
            else if (horizontalCondition & i == 2)
                winStreak = "h2";

            if (verticalCondition & i == 0)
                winStreak = "v0";
            else if (verticalCondition & i == 1)
                winStreak = "v1";
            else if (verticalCondition & i == 2)
                winStreak = "v2";

            if (mainDiagCondition)
                winStreak = "d1";
            else if (diagCondition)
                winStreak = "d2";
        }
        return winStreak;
    }

    private void iterator(int i, int j) {
        if (j == 3) {
            for (int k = 0; k < j; k++)
                buttons[i][k].setBackgroundColor(Color.parseColor("#ffe08a"));
        } else if (i == 3) {
            for (int k = 0; k < i; k++)
                buttons[k][j].setBackgroundColor(Color.parseColor("#ffe08a"));
        } else if (i == j) {
            for (int k = 0; k <= i; k++)
                buttons[k][k].setBackgroundColor(Color.parseColor("#ffe08a"));
        } else {
            for (int k = 0, m = j; k <= j & m >= 0; k++, m--)
                buttons[k][m].setBackgroundColor(Color.parseColor("#ffe08a"));
        }
    }

    private void colorize(String sym) {
        switch (countWinStreak(sym)) {
            case "h0":
                iterator(0, 3);
                break;
            case "h1":
                iterator(1, 3);
                break;
            case "h2":
                iterator(2, 3);
                break;
            case "v0":
                iterator(3, 0);
                break;
            case "v1":
                iterator(3, 1);
                break;
            case "v2":
                iterator(3, 2);
                break;
            case "d1":
                iterator(2, 2);
                break;
            case "d2":
                iterator(0, 2);
                break;
        }
    }
}