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

// todo refactor somehow
// todo more beautiful win/lose msg
// todo more strong ai

public class GameActivity extends AppCompatActivity {
    private final int[] buttonIds = new int[] { R.id.b0, R.id.b1, R.id.b2, R.id.b3, R.id.b4, R.id.b5, R.id.b6, R.id.b7, R.id.b8};

    private final static String WIN_MESSAGE = "You win!";
    private final static String LOSE_MESSAGE = "You lose, try again!";
    private final static String DRAW_MESSAGE = "It's a draw!";
    private final static String CLICK = "Click";
    private static final int DOTS_TO_WIN = 3;
    private static final int MAX_MOVE_CNT = 9;
    private static String PLAYER_SYM = "X";
    private static String AI_SYM = "O";
    private static String EMPTY = "";
    private static int MOVE_CNT;
    private static int WIN_CNT;
    private static int LOSE_CNT;
    private static int DRAW_CNT;

    private Button[][] buttons = new Button[3][3];
    private MediaPlayer mediaPlayer;
    private TableLayout layout;
    private TextView textView;
    private TextView tvWins;
    private TextView tvLoses;
    private TextView tvDraws;
    private Button startStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        layout = findViewById(R.id.main_l);
        startStop = findViewById(R.id.buttonS);
        textView = findViewById(R.id.textViewInfo);
        tvWins = findViewById(R.id.textViewWin);
        tvLoses = findViewById(R.id.textViewLos);
        tvDraws = findViewById(R.id.textViewDraw);
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
                buttons[i][j].setBackgroundColor(Color.parseColor("#bd9bd1"));    // тут если ресурсы юзать почему то углы херятся у кнопок
                textView.setText(EMPTY);
                textView.setBackgroundResource(R.color.main_background);
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
                WIN_CNT++;
                updateScore(WIN_MESSAGE);
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
                mediaPlayer = MediaPlayer.create(this, R.raw.draw); // кароче тут была страшная ебка - оказывается есть
                break;                                                      // какие то аудио файлы, которые не может переварить
            case WIN_MESSAGE:                                               // mediaPlayer, и он кидает NPE, хотя формат файлов один и тот же
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
                DRAW_CNT++;
                updateScore(DRAW_MESSAGE);      // миленько получилось. Наверно тут какойто паттерн применить нужно, чтобы
                initAudio(DRAW_MESSAGE);        // сгруппировать эти однотипные параметры
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
                    LOSE_CNT++;
                    updateScore(LOSE_MESSAGE);
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
                buttons[i][k].setBackgroundColor(Color.parseColor("#f5e3b0"));
        } else if (i == 3) {
            for (int k = 0; k < i; k++)
                buttons[k][j].setBackgroundColor(Color.parseColor("#f5e3b0"));
        } else if (i == j) {
            for (int k = 0; k <= i; k++)
                buttons[k][k].setBackgroundColor(Color.parseColor("#f5e3b0"));
        } else {
            for (int k = 0, m = j; k <= j & m >= 0; k++, m--)
                buttons[k][m].setBackgroundColor(Color.parseColor("#f5e3b0"));
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

    private void updateScore(String type) {
        switch (type) {
            case WIN_MESSAGE:
                tvWins.setText(String.valueOf(WIN_CNT));    // тут то мы уже на опыте, надо кастить в стринг, прежде чем толкать в сеттер
                break;
            case LOSE_MESSAGE:
                tvLoses.setText(String.valueOf(LOSE_CNT));
                break;
            default:
                tvDraws.setText(String.valueOf(DRAW_CNT));
                break;
        }
    }
}