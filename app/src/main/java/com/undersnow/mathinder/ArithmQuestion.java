package com.undersnow.mathinder;

import java.util.Random;

/**
 * Created by dc on 12/10/2017.
 */

public class ArithmQuestion {
    public static final int ADD = 0, SUB = 1, MUL = 2, DIV = 3, RAND = -2;
    int a, b, op, result;
    boolean isCorrect;
    int score = 10;

    ArithmQuestion(int o) {
        isCorrect = Math.random() > 0.5f;
        if (o == RAND)
            op = randInt(0, 3);
        else op = o;
        switch (op) {
            case MUL:
                a = randInt(1, 10);
                b = randInt(1, 9);
                result = a * (b + (!isCorrect ? 1 : 0));
                break;
            case DIV:
                b = randInt(2, 10);
                a = b * randInt(2, 9);
                result = a / (b - (!isCorrect ? 1 : 0));
                break;

            case SUB:
                a = randInt(5, 50);
                b = randInt(1, a);
                result = a - (b + (!isCorrect ? 1 : 0));
                break;
            default:
                ADD:
                a = randInt(5, 50);
                b = randInt(5, 50);
                result = a + (b - (!isCorrect ? 1 : 0));
                break;
        }
    }
    public static int colorByOp(int op) {
        switch (op) {
            case ADD:
                return R.color.opColor1;
            case MUL:
                return R.color.opColor2;
            case SUB:
                return R.color.opColor3;
            case DIV:
                return R.color.opColor4;
        }
        return R.color.opColor1;
    }
    @Override
    public String toString() {
        return a + " " + getOp(op) + " " + b ;
    }

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    private String getOp(int op) {
        switch (op) {
            case MUL:
                return "×";
            case DIV:
                return "÷";
            case SUB:
                return "-";
            case ADD:
                return "+";

        }
        return "UNKNOWN ";// this should never happen
    }

    public String getOp() {
        return getOp(this.op);
    }

    public int getOpColor() {
        return colorByOp(op);
    }
}



