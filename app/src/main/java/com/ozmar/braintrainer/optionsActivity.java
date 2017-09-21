package com.ozmar.braintrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class optionsActivity extends AppCompatActivity {
    String addition, subtraction, multiplication, division, timer;
    EditText timerEditText;
    CheckBox checkBoxAddition, checkBoxSubtraction, checkBoxMultiplication, checkBoxDivision;
    SharedPreferences settings;

    // Show which check boxes were displayed from user settings
    public void setCheckBoxDisplay(View view, String s) {
        if(s.equals("1")) {
            ((CheckBox)view).setChecked(true);
        }

        else {
            ((CheckBox)view).setChecked(false);
        }
    } // setCheckBoxDisplay() end

    // Check if CheckBox was checked
    public boolean checkCheckBoxStatus(View view) {
        return ((CheckBox) view).isChecked();
    } // checkCheckBoxStatus() end

    // Saving seems to be working fine
    // If it seems to fail at some point then use a OnSharedPreferenceChangeListener
    public void saveUserSettings(View view) {
        saveCheckBoxStatus(checkBoxAddition);
        saveCheckBoxStatus(checkBoxSubtraction);
        saveCheckBoxStatus(checkBoxMultiplication);
        saveCheckBoxStatus(checkBoxDivision);
        saveTimer(timerEditText);
    } // saveUserSettings() end

    // Save user input for timer
    protected void saveTimer(View view){
        SharedPreferences.Editor editor = settings.edit();
        String name = ((EditText)view).getText().toString();
        if(!name.equals("")) {
            editor.putString("Timer", name);
        }

        else{
            editor.putString("Timer", "0");
        }

        editor.apply();
    } // saveTimer() end

    // Save user input for CheckBoxes of questions wanted
    protected void saveCheckBoxStatus(View view) {
        SharedPreferences.Editor editor = settings.edit();
        String name = ((CheckBox)view).getText().toString();

        if(checkCheckBoxStatus(view)) {
            editor.putString(name, "1");
        }

        else {
            editor.putString(name, "0");
        }

        editor.apply();
    } // saveCheckBoxStatus() end

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

//        View decorView = getWindow().getDecorView();
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(uiOptions);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

//        Toast toast = Toast.makeText(getApplicationContext(), "onCreate2()", Toast.LENGTH_SHORT);
//        toast.show();

        checkBoxAddition = (CheckBox) findViewById(R.id.checkBoxAddition);
        checkBoxSubtraction = (CheckBox) findViewById(R.id.checkBoxSubtraction);
        checkBoxMultiplication = (CheckBox) findViewById(R.id.checkBoxMultiplication);
        checkBoxDivision = (CheckBox) findViewById(R.id.checkBoxDivision);
        timerEditText = (EditText) findViewById(R.id.timerEditText);

        settings = getSharedPreferences("User Settings", Context.MODE_PRIVATE);

        addition = settings.getString("Addition", "1");
        subtraction = settings.getString("Subtraction", "0");
        multiplication = settings.getString("Multiplication", "0");
        division = settings.getString("Division", "0");
        timer = settings.getString("Timer", "10");

        timerEditText.setText(timer);

        setCheckBoxDisplay(checkBoxAddition, addition);
        setCheckBoxDisplay(checkBoxSubtraction, subtraction);
        setCheckBoxDisplay(checkBoxMultiplication, multiplication);
        setCheckBoxDisplay(checkBoxDivision, division);
    } // onCreate() end

    @Override
    protected void onStart() {
        super.onStart();

//        Toast toast = Toast.makeText(getApplicationContext(), "onStart2()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onStart() end

    @Override
    protected void onResume() {
        super.onResume();

//        Toast toast = Toast.makeText(getApplicationContext(), "onResume2()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onResume() end

    @Override
    protected void onPause() {
        super.onPause();

//        Toast toast = Toast.makeText(getApplicationContext(), "onPause2()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onPause() end

    @Override
    protected void onStop() {
        super.onStop();

//        Toast toast = Toast.makeText(getApplicationContext(), "onStop2()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onStop() end

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Toast toast = Toast.makeText(getApplicationContext(), "onDestroy2()", Toast.LENGTH_SHORT);
//        toast.show();
    } // onDestroy() end
}
