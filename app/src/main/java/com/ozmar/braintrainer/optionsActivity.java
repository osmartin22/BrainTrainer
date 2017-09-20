package com.ozmar.braintrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class optionsActivity extends AppCompatActivity {
    String addition, subtraction, multiplication, division;
    CheckBox checkBoxAddition, checkBoxSubtraction, checkBoxMultiplication, checkBoxDivision;
    SharedPreferences settings;

    public void setCheckBoxDisplay(View view, String s) {
        if(s.equals("1")) {
            ((CheckBox)view).setChecked(true);
        }

        else {
            ((CheckBox)view).setChecked(false);
        }
    }

    public boolean checkCheckBoxStatus(View view) {
        return ((CheckBox) view).isChecked();
    }

    // Saving seems to be working fine
    // If it seems to fail at some point then use a OnSharedPreferenceChangeListener
    protected void saveUserSettings(View view) {
        saveCheckBoxStatus(checkBoxAddition);
        saveCheckBoxStatus(checkBoxSubtraction);
        saveCheckBoxStatus(checkBoxMultiplication);
        saveCheckBoxStatus(checkBoxDivision);
    }

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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

//        Toast toast = Toast.makeText(getApplicationContext(), "onCreate2()", Toast.LENGTH_SHORT);
//        toast.show();

        checkBoxAddition = (CheckBox) findViewById(R.id.checkBoxAddition);
        checkBoxSubtraction = (CheckBox) findViewById(R.id.checkBoxSubtraction);
        checkBoxMultiplication = (CheckBox) findViewById(R.id.checkBoxMultiplication);
        checkBoxDivision = (CheckBox) findViewById(R.id.checkBoxDivision);

        settings = getSharedPreferences("User Settings", Context.MODE_PRIVATE);

        addition = settings.getString("Addition", "");
        subtraction = settings.getString("Subtraction", "");
        multiplication = settings.getString("Multiplication", "");
        division = settings.getString("Division", "");

        setCheckBoxDisplay(checkBoxAddition, addition);
        setCheckBoxDisplay(checkBoxSubtraction, subtraction);
        setCheckBoxDisplay(checkBoxMultiplication, multiplication);
        setCheckBoxDisplay(checkBoxDivision, division);
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast toast = Toast.makeText(getApplicationContext(), "onStart2()", Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast toast = Toast.makeText(getApplicationContext(), "onResume2()", Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//        Toast toast = Toast.makeText(getApplicationContext(), "onPause2()", Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast toast = Toast.makeText(getApplicationContext(), "onStop2()", Toast.LENGTH_SHORT);
//        toast.show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast toast = Toast.makeText(getApplicationContext(), "onDestroy2()", Toast.LENGTH_SHORT);
//        toast.show();
//    }
}
