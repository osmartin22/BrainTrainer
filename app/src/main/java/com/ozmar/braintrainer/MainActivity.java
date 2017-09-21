package com.ozmar.braintrainer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // TODO:

    // Have a stop button when user is playing to stop game
    // Maybe a resume button
    // Rename stop button to pause button
    // If used a reset button will be needed
    // Or can use playAgain button

    // Maybe add reset button or modify playAgainButton
    // Allows user to play again immediately if the feel they are doing bad
    // Eliminates 1 button click from stop to play again button

    // Show high score at the end for each of the possibilities
    // Maybe just for the major ones, all options is a lot

    // Maybe
    // Have option for rounding
    // Have option for decimal multiplication

    Button startButton, playAgainButton, optionsButton, stopButton;
    Button button0, button1, button2, button3;
    TextView pointsTextView, questionTextView, resultTextView, timerTextView;
    ConstraintLayout gameRelativeLayout;

    ArrayList<Integer> answers = new ArrayList<>();
    ArrayList<Double> answersDouble = new ArrayList<>();
    ArrayList<String> availableQuestions = new ArrayList<>();

    CountDownTimer myCountDownTimer;

    String[] mathSymbols = new String[]{"Addition", "Subtraction", "Multiplication", "Division"};

    Random rand = new Random();

    int seconds = 7100;
    int locationOfCorrectAnswer;
    int score = 0;
    int numberOfQuestions = 0;
    int gameState = 0;  // 0 for not currently running, 1 for running
    int randNum = 31;

    // Timer
    // OnFinish: Shows playAgainButton and score, sets gameState to 0
    public void timer() {
        myCountDownTimer = new CountDownTimer(seconds, 1000) {

            @Override
            public void onTick(long millisecondsUntilFinished) {
                String time = String.valueOf(millisecondsUntilFinished / 1000) + getString(R.string.s);
                timerTextView.setText(time);
            }

            @Override
            public void onFinish() {
                gameState = 0;
                playAgainButton.setVisibility(View.VISIBLE);
                optionsButton.setVisibility(View.VISIBLE);
                timerTextView.setText(getString(R.string.timerSetText));
                resultTextView.setText("Your Score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            }
        }.start();
    } // timer() end

    // Start game over again
    public void playAgain(final View view) {
        gameState = 1;
        score = 0;
        numberOfQuestions = 0;
        pointsTextView.setText("0/0");
        resultTextView.setText("");
        playAgainButton.setVisibility(View.INVISIBLE);
        optionsButton.setVisibility(View.INVISIBLE);

        generateQuestion();
        timer();
    } // playAgain() end

    public void resetGame(View view) {
        myCountDownTimer.cancel();
        playAgain(view);
    } // resetGame() end

    public void stopPlaying(View view) {
        gameState = 0;
        myCountDownTimer.cancel();
        playAgainButton.setVisibility(View.VISIBLE);
        optionsButton.setVisibility(View.VISIBLE);
    } // stopPlaying() end

    // Create answers for division
    // Separate function due to creating double instead of int
    public void createAnswerDivision(int a, int b) {
        double incorrectAnswer;
        double result = (double) a / (double) b;
        double rangeMin = 0;
        double rangeMax = 20;

        result = Math.round(result * 10.0) / 10.0;
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answersDouble.add(result);
            } else {
                incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
                incorrectAnswer = Math.round(incorrectAnswer * 10.0) / 10.0;

                while (incorrectAnswer == result) {
                    incorrectAnswer = rangeMin + (rangeMax - rangeMin) * rand.nextDouble();
                    incorrectAnswer = Math.round(incorrectAnswer * 100.0) / 100.0;
                }

                answersDouble.add(incorrectAnswer);
            }
        }
    } // createAnswerDivision() end

    // Make a new question for +, -, *
    public void createAnswers(int symbol, int a, int b) {
        int incorrectAnswer;
        int result;

        for (int i = 0; i < 4; i++) {
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

            if (i == locationOfCorrectAnswer) {
                answers.add(result);
            } else {
                incorrectAnswer = rand.nextInt(randNum);
                while (incorrectAnswer == result) {
                    incorrectAnswer = rand.nextInt(randNum);
                }
                answers.add(incorrectAnswer);
            }
        }
    } // createAnswers() end

    public void generateQuestion() {
        int a = rand.nextInt(randNum);
        int b = rand.nextInt(randNum);

        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        answersDouble.clear();

        String chosenMathSymbol = availableQuestions.get(rand.nextInt(availableQuestions.size()));
        switch (chosenMathSymbol) {
            case "Addition":
                questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
                createAnswers(0, a, b);
                break;

            case "Subtraction":
                questionTextView.setText(Integer.toString(a) + " - " + Integer.toString(b));
                createAnswers(1, a, b);
                break;

            case "Multiplication":
                questionTextView.setText(Integer.toString(a) + " * " + Integer.toString(b));
                createAnswers(2, a, b);
                break;

            case "Division":
                while (b == 0) {
                    b = rand.nextInt(21);
                }
                questionTextView.setText(Integer.toString(a) + " / " + Integer.toString(b));
                createAnswerDivision(a, b);
                break;

            default:
                break;
        }

        if (chosenMathSymbol.equals("Division")) {
            button0.setText(String.format(java.util.Locale.US, "%.2f", answersDouble.get(0)));
            button1.setText(String.format(java.util.Locale.US, "%.2f", answersDouble.get(1)));
            button2.setText(String.format(java.util.Locale.US, "%.2f", answersDouble.get(2)));
            button3.setText(String.format(java.util.Locale.US, "%.2f", answersDouble.get(3)));
        } else {
            button0.setText(String.format(java.util.Locale.US, "%d", answers.get(0)));
            button1.setText(String.format(java.util.Locale.US, "%d", answers.get(1)));
            button2.setText(String.format(java.util.Locale.US, "%d", answers.get(2)));
            button3.setText(String.format(java.util.Locale.US, "%d", answers.get(3)));
        }
    } // generateQuestion() end

    // Choose Answer on screen
    // Update score and number of questions
    // Get new question
    public void chooseAnswer(View view) {
        if (gameState == 1) {
            if (view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {
                score++;
                resultTextView.setText(getString(R.string.resultSetTextCorrect));
            } else {
                resultTextView.setText(getString(R.string.resultSetTextWrong));
            }

            numberOfQuestions++;
            pointsTextView.setText(Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
            generateQuestion();
        }
    } // chooseAnswer() end


    // Start game button
    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        optionsButton.setVisibility(View.INVISIBLE);
        gameRelativeLayout.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgainButton));
        gameState = 1;
    } // start() end


    // Switch Screen to choose options
    public void options(View view) {
        Intent intent = new Intent(this, optionsActivity.class);
        startActivity(intent);
    } // options() end

    // Put user settings into an array to use for generating random questions
    public void getUserSettings() {
        SharedPreferences settings = getSharedPreferences("User Settings", Context.MODE_PRIVATE);
        String flagValue;

        for (String mathSymbol : mathSymbols) {
            flagValue = settings.getString(mathSymbol, "1");

            if (flagValue.equals("1")) {
                availableQuestions.add(mathSymbol);
            }
        }
    } // getUserSettings() end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//
//        Toast toast = Toast.makeText(getApplicationContext(), "onCreate()", Toast.LENGTH_SHORT);
//        toast.show();

        gameRelativeLayout = (ConstraintLayout) findViewById(R.id.gameConstraintLayout);

        startButton = (Button) findViewById(R.id.startButton);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        button0 = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        optionsButton = (Button) findViewById(R.id.optionsButton);
        stopButton = (Button) findViewById(R.id.stopButton);

    } // onCreate() end


    @Override
    protected void onStart() {
        super.onStart();

//        Toast toast = Toast.makeText(getApplicationContext(), "onStart()", Toast.LENGTH_SHORT);
//        toast.show();

        getUserSettings();
    } // onStart() end

    @Override
    protected void onResume() {
        super.onResume();

//        Toast toast = Toast.makeText(getApplicationContext(), "onResume()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onResume() end

    @Override
    protected void onPause() {
        super.onPause();

//        Toast toast = Toast.makeText(getApplicationContext(), "onPause()", Toast.LENGTH_SHORT);
//        toast.show();

        availableQuestions.clear();
    } // onPause() end

    @Override
    protected void onStop() {
        super.onStop();

//        Toast toast = Toast.makeText(getApplicationContext(), "onStop()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onStop() end

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Toast toast = Toast.makeText(getApplicationContext(), "onDestroy()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onDestroy() end

    // Settings button at the top
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    } // onCreateOptionsMenu() end
}
