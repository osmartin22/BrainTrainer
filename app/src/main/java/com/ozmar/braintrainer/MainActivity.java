package com.ozmar.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView  pointsTextView, sumTextView, resultTextView, timerTextView;
    Button button0, button1, button2, button3, playAgainButton;
    RelativeLayout gameRelativeLayout;

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Double> answersDouble = new ArrayList<>();

    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    int gameState = 0;  // 0 for not currently running, 1 for running

    // Timer
    // OnFinish: Shows playAgainButton and score, sets gameState to 0
    public void timer(){
        new CountDownTimer(7100, 1000) {    // 30100

            @Override
            public void onTick(long millisecondsUntilFinished) {
                timerTextView.setText(String.valueOf(millisecondsUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                gameState = 0;
                playAgainButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
        }.start();
    }

    public void playAgain(final View view) {
        gameState = 1;
        score = 0;
        numberOfQuestions = 0;
        //timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);

        if(gameState == 1) {
            generateQuestion();
        }

        timer();

    }

    public void createAnswerDivision(int a, int b) {
        Random rand = new Random();
        locationOfCorrectAnswer = rand.nextInt(4);
        double incorrectAnswer;
        double result = (double)a / (double)b;

        double rangeMin = 0;
        double rangeMax = 40;

        for(int i = 0; i < 4; i++) {
            if(i == locationOfCorrectAnswer) {
                answersDouble.add(result);
            }

            else {
                incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();

                while(incorrectAnswer == result){
                    incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
                }

                answersDouble.add(incorrectAnswer);
            }
        }

    }

    public void createAnswers(int symbol, int a, int b) {
        Random rand = new Random();
        locationOfCorrectAnswer = rand.nextInt(4);
        int incorrectAnswer;
        int result;

        for(int i = 0; i < 4; i++) {

            switch (symbol) {
                case 0:
                    result = a + b;
                    break;

                case 1:
                    result = a - b;
                    break;

                case 2:
                    result = a * b;
                    break;

                default:
                    result = 0;
                    break;
            }

            if(i == locationOfCorrectAnswer) {
                answers.add(result);

            }

            else {
                incorrectAnswer = rand.nextInt(41);

                while(incorrectAnswer == result){
                    incorrectAnswer = rand.nextInt(41);
                }

                answers.add(incorrectAnswer);
            }
        }
    }


    public void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();

        // 0 == "+"     1 == "-"    2 == "*"    3 == "/"
        int mathSymbol = rand.nextInt(4);
//        mathSymbol = 0;

        switch(mathSymbol) {
            case 0:
                sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
                createAnswers(0, a, b);
                break;

            case 1:
                sumTextView.setText(Integer.toString(a) + " - " + Integer.toString(b));
                createAnswers(1, a, b);
                break;

            case 2:
                sumTextView.setText(Integer.toString(a) + " * " + Integer.toString(b));
                createAnswers(2, a, b);
                break;

            case 3:
                while(b == 0){
                    b = rand.nextInt(21);
                }
                sumTextView.setText(Integer.toString(a) + " / " + Integer.toString(b));
                //createAnswers(2, a, b);
                createAnswerDivision(a, b);
                break;

            default:
                break;

        }

        if(mathSymbol == 3) {
            button0.setText(Double.toString((answers.get(0))));
            button1.setText(Double.toString((answers.get(1))));
            button2.setText(Double.toString((answers.get(2))));
            button3.setText(Double.toString((answers.get(3))));
        }

        else {
            button0.setText(Integer.toString((answers.get(0))));
            button1.setText(Integer.toString((answers.get(1))));
            button2.setText(Integer.toString((answers.get(2))));
            button3.setText(Integer.toString((answers.get(3))));
        }

    }

    // Choose Answer on screen
    // Update score and number of questions
    // Get new question
    public void chooseAnswer(View view) {
        if(gameState == 1) {
            if(view.getTag().toString().equals( Integer.toString(locationOfCorrectAnswer) )) {
                score++;
                resultTextView.setText("Correct!");
            }

            else {
                resultTextView.setText("Wrong!");
            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();
        }
    }


    // Start game
    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        gameState = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameRelativeLayout = (RelativeLayout)findViewById(R.id.gameRelativeLayout);

        startButton = (Button)findViewById(R.id.startButton);
        pointsTextView = (TextView)findViewById(R.id.pointsTextView);
        sumTextView = (TextView)findViewById(R.id.sumTextView);
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        button0 = (Button)findViewById(R.id.button);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        playAgainButton = (Button)findViewById(R.id.playAgainButton);
    }
}
