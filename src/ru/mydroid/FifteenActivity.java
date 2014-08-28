package ru.mydroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import ru.mydroid.MyView.SquareLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class FifteenActivity extends Activity implements OnClickListener{
    final static String LOG_TAG = "myLogs";
    final static String STEPS = "steps";
    final static String TIME = "time";
    final static String ARRAY_1 = "array_1";
    final static String ARRAY_2 = "array_2";
    final static String ARRAY_3 = "array_3";
    final static String ARRAY_4 = "array_4";
    final static int WIN_DIALOG = 1;

	private Button b11;
	private Button b12;
	private Button b13;
	private Button b14;

	private Button b21;
	private Button b22;
	private Button b23;
	private Button b24;

	private Button b31;
	private Button b32;
	private Button b33;
	private Button b34;

	private Button b41;
	private Button b42;
	private Button b43;
	private Button b44;

    TextView viewSteps;
    Chronometer timer;
    SquareLayout squareLayout;

    GameHelper gameHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        initialize();
        gameHelper.generateEasyArray();
		gameHelper.paintTable();
        timer.start();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Button button = gameHelper.getButtons()[i][j];
				button.setOnClickListener(this);
			}
		}
	}

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Сохраняем шаги, время, игровое поле
        outState.putInt(STEPS, gameHelper.getSteps());
        outState.putLong(TIME, SystemClock.elapsedRealtime() - timer.getBase());
        outState.putIntArray(ARRAY_1, gameHelper.getArray()[0]);
        outState.putIntArray(ARRAY_2, gameHelper.getArray()[1]);
        outState.putIntArray(ARRAY_3, gameHelper.getArray()[2]);
        outState.putIntArray(ARRAY_4, gameHelper.getArray()[3]);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //Восстонавливаем и затем обновляем шаги
        gameHelper.setSteps(savedInstanceState.getInt(STEPS));
        updateSteps();

        //Восстонавливаем и сетим в timer время
        long prevTime = savedInstanceState.getLong(TIME);
        timer.setBase(SystemClock.elapsedRealtime() - prevTime);
        super.onRestoreInstanceState(savedInstanceState);

        //Восстонавливаем и рисуем игровое поле
        int[][] prev_array = new int[4][4];
        prev_array[0] = savedInstanceState.getIntArray(ARRAY_1);
        prev_array[1] = savedInstanceState.getIntArray(ARRAY_2);
        prev_array[2] = savedInstanceState.getIntArray(ARRAY_3);
        prev_array[3] = savedInstanceState.getIntArray(ARRAY_4);
        gameHelper.setArray(prev_array);
        gameHelper.paintTable();
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        Point clickedPoint = gameHelper.getClickedPoint(clickedButton);
        if (clickedPoint != null && gameHelper.canMove(clickedPoint)) {
            int[][] tmp_array = gameHelper.getArray();
            tmp_array[gameHelper.getEmptySpace().x][gameHelper.getEmptySpace().y] = Integer.parseInt(clickedButton.getText().toString());
            tmp_array[clickedPoint.x][clickedPoint.y] = gameHelper.EMPTY_POINT;
            gameHelper.setArray(tmp_array);
            gameHelper.paintTable();
            updateStepsIncr();

            if (gameHelper.checkForWin()){
                timer.stop();
                showDialog(WIN_DIALOG);
            }
        }
    }

    private void updateSteps(){
        String str =  String.format(getString(R.string.steps), gameHelper.getSteps());
        viewSteps.setText(str);
    }

    private void updateStepsIncr() {
        gameHelper.incrStep();
        updateSteps();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == WIN_DIALOG){

            //Записываем время из timer в переменную time
            long mills = SystemClock.elapsedRealtime() - timer.getBase();
            DateFormat TIMESTAMP = new SimpleDateFormat("mm:ss");
            String time = TIMESTAMP.format(new Date(mills));

            //Создаем dialog http://stackoverflow.com/questions/3995058/android-custom-alertdialog-background-color
            final Dialog myDialog = new Dialog(this);
            myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            myDialog.setContentView(R.layout.dialog_win);


            //Устанавливаем сообщение о победе
            TextView dlgWin_tv = (TextView) myDialog.findViewById(R.id.dlgWin_tv);
            dlgWin_tv.setText(String.format(getString(R.string.win_dialog_message), gameHelper.getSteps(), time));

            //Рестартим игру по клику на кнопку
            Button dlgWin_btn = (Button) myDialog.findViewById(R.id.dlgWin_btn);
            dlgWin_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    restartGame();
                    myDialog.cancel();
                }
            });
            myDialog.show();
        }
        return super.onCreateDialog(id);
    }

    private void restartGame(){
        gameHelper.setSteps(0);
        updateSteps();
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        gameHelper.generateEasyArray();
        gameHelper.paintTable();
    }

    private void initialize() {
        Button[][] buttons = new Button[4][4];
		b11 = (Button) findViewById(R.id.button11);
		buttons[0][0] = b11;
		b12 = (Button) findViewById(R.id.button12);
		buttons[0][1] = b12;
		b13 = (Button) findViewById(R.id.button13);
		buttons[0][2] = b13;
		b14 = (Button) findViewById(R.id.button14);
		buttons[0][3] = b14;

		b21 = (Button) findViewById(R.id.button21);
		buttons[1][0] = b21;
		b22 = (Button) findViewById(R.id.button22);
		buttons[1][1] = b22;
		b23 = (Button) findViewById(R.id.button23);
		buttons[1][2] = b23;
		b24 = (Button) findViewById(R.id.button24);
		buttons[1][3] = b24;

		b31 = (Button) findViewById(R.id.button31);
		buttons[2][0] = b31;
		b32 = (Button) findViewById(R.id.button32);
		buttons[2][1] = b32;
		b33 = (Button) findViewById(R.id.button33);
		buttons[2][2] = b33;
		b34 = (Button) findViewById(R.id.button34);
		buttons[2][3] = b34;

		b41 = (Button) findViewById(R.id.button41);
		buttons[3][0] = b41;
		b42 = (Button) findViewById(R.id.button42);
		buttons[3][1] = b42;
		b43 = (Button) findViewById(R.id.button43);
		buttons[3][2] = b43;
		b44 = (Button) findViewById(R.id.button44);
		buttons[3][3] = b44;
        gameHelper = new GameHelper(buttons);

        viewSteps = (TextView) findViewById(R.id.viewSteps);
        String str =  String.format(getString(R.string.steps), gameHelper.getSteps());
        viewSteps.setText(str);

        timer = (Chronometer) findViewById(R.id.time);
        squareLayout = (SquareLayout)findViewById(R.id.squareLayout);

        //Делаем кнопки квадратными
        int mWidthForButton = squareLayout.getWidth()/4;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               buttons[i][j].setWidth(mWidthForButton);

            }
        }
	}
}