package ru.mydroid;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class GameHelper {

    final static int EMPTY_POINT = -1;

    Button[][] buttons;
    int[][] array;
    int steps;

    GameHelper(Button[][] buttons){
        this.buttons = buttons;
        array = new int[4][4];
        steps = 0;
    }

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

    protected Point getEmptySpace(){
        Point emptyPoint = new Point();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (array[i][j] == EMPTY_POINT){
                    emptyPoint.x = i;
                    emptyPoint.y = j;
                    return  emptyPoint;
                }
            }
        }
        return  null;
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
                    array[i][j] = EMPTY_POINT;
                } else {
                    array[i][j] = value;
                }
                randomValues.remove(index);
            }
        }
        return array;
    }

    protected void generateEasyArray(){
        int[][] winArray = {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12}, {13, 14, EMPTY_POINT, 15}};
        setArray(winArray);
    }

    protected void paintTable() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button button = this.getButtons()[i][j];
                int number = array[i][j];
                if(number == EMPTY_POINT) {
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

    protected boolean checkForWin(){
       int[][] winArray = {{1, 2, 3, 4},{5, 6, 7, 8},{9, 10, 11, 12}, {13, 14, 15, EMPTY_POINT}};
       for (int i = 0; i < 4; i++){
           for (int j = 0; j < 4; j++){
               if (getArray()[i][j] != winArray[i][j]){
                   return false;
               }
           }
       }
        return true;
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
