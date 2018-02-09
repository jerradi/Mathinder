package com.undersnow.mathinder;

import java.util.Random;

/**
 * Created by dc on 12/10/2017.
 */

public class Question {
     String question;
    boolean isCorrect;
    int score = 1;

    Question(String q, boolean answer) {
      question=q;
        isCorrect = answer;
    }

    @Override
    public String toString() {
        return  question  ;
    }

}





