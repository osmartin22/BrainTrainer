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

    public void setCheckBoxDisplay(View view, String s){
        if(s.equals("1")){
            ((CheckBox)view).setChecked(true);
        }

        else{
            ((CheckBox)view).setChecked(false);
        }
    }

    public boolean checkCheckBoxStatus(View view){
        return ((CheckBox) view).isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

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

    public void save(View view){
        SharedPreferences.Editor editor = settings.edit();
        String name = ((CheckBox)view).getText().toString();

        if(checkCheckBoxStatus(view)){
            editor.putString(name, "1");
        }

        else{
            editor.putString(name, "0");
        }

        editor.apply();
    }

    @Override
    public void onPause() {
        super.onPause();

        save(checkBoxAddition);
        save(checkBoxSubtraction);
        save(checkBoxMultiplication);
        save(checkBoxDivision);
    }
}
