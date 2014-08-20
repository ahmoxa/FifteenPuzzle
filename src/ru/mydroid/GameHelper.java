package ru.mydroid;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;


public class GameHelper {

    Button[][] buttons;
    int[][] array;
    Point emptySpace;
    int steps;

    GameHelper(Button[][] buttons){
        this.buttons = buttons;
        array = new int[4][4];
        emptySpace = new Point();
        steps = 0;
    }

    protected Point getEmptySpace(){
        return this.emptySpace;
    }

    protected void setEmptySpace(Point emptySpace){this.emptySpace = emptySpace;};

    protected Button[][] getButtons(){
        return buttons;
    }

    protected int getSteps(){
        return this.steps;
    }

    protected void setSteps(int steps) {this.steps = steps; }

    protected int[][] getArray(){ return this.array; };

    protected void setArray(int[][] array) {this.array = array; }

    protected void incrStep(){
        steps++;
    }

    protected Point getClickedPoint(Button clickedButton) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (clickedButton == buttons[i][j]) {
                    Point point = new Point();
                    point.x = i;
                    point.y = j;
                    return point;
                }
            }
        }
        return null;
    }

    protected int[][] generateArray() {
        ArrayList<Integer> randomValues = new ArrayList<Integer>();
        for(int i = 1; i <= 16; i++){
            randomValues.add(i);
        }
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int index = random.nextInt(randomValues.size());
                int value = randomValues.get(index);
                if (value >= 16) {
                    emptySpace.x = i;
                    emptySpace.y = j;
                    array[i][j] = -1;
                } else {
                    array[i][j] = value;
                }
                randomValues.remove(index);
            }
        }
        return array;
    }

    protected void paintTable() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button button = this.getButtons()[i][j];
                int number = array[i][j];
                if(number == -1) {
                    button.setText(" ");
                    button.setVisibility(View.INVISIBLE);
                }
                else {
                    button.setText(String.valueOf(number));
                    button.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    protected boolean canMove(Point clicked) {
        if (clicked.equals(getEmptySpace())) {
            return false;
        }
        if (clicked.x == getEmptySpace().x) {
            int diff = Math.abs(clicked.y - getEmptySpace().y);
            if (diff == 1) {
                return true;
            }
        } else if (clicked.y == getEmptySpace().y) {
            int diff = Math.abs(clicked.x - getEmptySpace().x);
            if (diff == 1) {
                return true;
            }
        }
        return false;
    }

    protected void logArray(int[][] array){

        String msg = "Array:\n";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
               msg = msg + array[i][j] + " ";
            }
            msg = msg + "\n";
        }
        Log.d(FifteenActivity.LOG_TAG, msg);
    }

}
