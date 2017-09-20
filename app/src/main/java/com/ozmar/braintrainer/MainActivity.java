package com.ozmar.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // TODO:
    // Add menu option that allows user to choose various settings
        // Like just addition or addition and subtraction
        // Options will have to be stored in data

    // Show high score at the end for each of the possibilities
        // Maybe just for the major ones, all options is a lot

    // Maybe
        // Have option for rounding
        // Have option for decimal multiplication

    Button startButton, playAgainButton, optionsButton;
    Button button0, button1, button2, button3;
    TextView  pointsTextView, sumTextView, resultTextView, timerTextView;
    RelativeLayout gameRelativeLayout;

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Double> answersDouble = new ArrayList<>();

    int seconds = 7100;
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    int gameState = 0;  // 0 for not currently running, 1 for running
    int randNum = 31;

    // Timer
    // OnFinish: Shows playAgainButton and score, sets gameState to 0
    public void timer(){
        new CountDownTimer(seconds, 1000) {

            @Override
            public void onTick(long millisecondsUntilFinished) {
                timerTextView.setText(String.valueOf(millisecondsUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                gameState = 0;
                playAgainButton.setVisibility(View.VISIBLE);
                optionsButton.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
        }.start();
    }

    // Start game over again
    public void playAgain(final View view) {
        gameState = 1;
        score = 0;
        numberOfQuestions = 0;
        //timerTextView.setText("30s");
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        optionsButton.setVisibility(View.INVISIBLE);

        generateQuestion();
        timer();
    }

    // Create answers for division
    // Separate function due to creating double instead of int
    public void createAnswerDivision(int a, int b) {
        Random rand = new Random();
        double incorrectAnswer;
        double result = (double)a / (double)b;

        result = Math.round(result*10.0)/10.0;

        double rangeMin = 0;
        double rangeMax = 20;

        for(int i = 0; i < 4; i++) {
            if(i == locationOfCorrectAnswer) {
                answersDouble.add(result);
            }

            else {
                incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
                incorrectAnswer = Math.round(incorrectAnswer*10.0)/10.0;

                while(incorrectAnswer == result){
                    incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
                    incorrectAnswer = Math.round(incorrectAnswer*100.0)/100.0;
                }

                answersDouble.add(incorrectAnswer);
            }
        }

    }

    // Make a new question for +, -, *
    public void createAnswers(int symbol, int a, int b) {
        Random rand = new Random();
        int incorrectAnswer;
        int result;

        for(int i = 0; i < 4; i++) {
            switch (symbol) {
                case 0:             // Addition
                    result = a + b;
                    break;
                case 1:             // Subtraction
                    result = a - b;
                    break;
                case 2:             // Multiplication
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
                incorrectAnswer = rand.nextInt(randNum);
                while(incorrectAnswer == result){
                    incorrectAnswer = rand.nextInt(randNum);
                }
                answers.add(incorrectAnswer);
            }
        }
    }


    public void generateQuestion() {
        Random rand = new Random();
        int a = rand.nextInt(randNum);
        int b = rand.nextInt(randNum);

        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        answersDouble.clear();

        // 0 == "+"     1 == "-"    2 == "*"    3 == "/"
        int mathSymbol = rand.nextInt(4);

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
                createAnswerDivision(a, b);
                break;

            default:
                break;

        }

        if(mathSymbol == 3) {
            button0.setText(Double.toString((answersDouble.get(0))));
            button1.setText(Double.toString((answersDouble.get(1))));
            button2.setText(Double.toString((answersDouble.get(2))));
            button3.setText(Double.toString((answersDouble.get(3))));
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


    // Start game button
    public void start(View view){
        startButton.setVisibility(View.INVISIBLE);
        optionsButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        gameState = 1;
    }


    // Switch Screen to choose options
    public void options(View view){
        Intent intent = new Intent(this, optionsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast = Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT);
        toast.show();

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
        optionsButton = (Button)findViewById(R.id.optionsButton);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast toast = Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast toast = Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast toast = Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast toast = Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT);
        toast.show();
    }

    // Settings button at the top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}

//
//    Toast toast = Toast.makeText(getApplicationContext(), "Subtraction Unchecked", Toast.LENGTH_SHORT);
//    toast.show();
