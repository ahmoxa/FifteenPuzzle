package ru.mydroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import ru.mydroid.MyView.SquareLayout;

import java.util.ArrayList;
import java.util.Random;

public class FifteenActivity extends Activity implements OnClickListener{

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


	private int[][] array = new int[4][4];


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initilization();
		array = gameHelper.generateArray();
		gameHelper.paintTable();
        timer.start();

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Button button = gameHelper.getButtons()[i][j];
				button.setOnClickListener(this);
			}
		}
	}

    private void updateSteps(){
        gameHelper.incrStep();
        String str =  String.format(getString(R.string.steps), gameHelper.getSteps());
        viewSteps.setText(str);
    }

	private void initilization() {
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

        int mWidthForButton = squareLayout.getWidth();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               buttons[i][j].setWidth(mWidthForButton);
            }
        }
	}

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;
        Point clickedPoint = gameHelper.getClickedPoint(clickedButton);
        if (clickedPoint != null && gameHelper.canMove(clickedPoint)) {
            clickedButton.setVisibility(View.INVISIBLE);
            String numberStr = clickedButton.getText().toString();
            clickedButton.setText(" ");

            Button button = gameHelper.getButtons()[gameHelper.getEmptySpace().x][gameHelper.getEmptySpace().y];
            button.setVisibility(View.VISIBLE);
            button.setText(numberStr);

            gameHelper.getEmptySpace().x = clickedPoint.x;
            gameHelper.getEmptySpace().y = clickedPoint.y;

            updateSteps();
        }
    }
}