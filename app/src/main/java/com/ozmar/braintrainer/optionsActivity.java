package com.ozmar.braintrainer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class optionsActivity extends AppCompatActivity {
    String addition, subtraction, multiplication, division;

    CheckBox checkBoxAddition, checkBoxSubtraction, checkBoxMultiplication, checkBoxDivision;

    SharedPreferences settings;

    public void setCheckBoxDisplay(View view, String s){
        if(s.equals("1")){ ((CheckBox)view).setChecked(true); }

        else{ ((CheckBox)view).setChecked(false); }
    }

    public boolean checkCheckBoxStatus(View view){
        return ((CheckBox) view).isChecked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        EditText testView = (EditText) findViewById(R.id.testTextView);

        checkBoxAddition = (CheckBox) findViewById(R.id.checkBoxAddition);
        checkBoxSubtraction = (CheckBox) findViewById(R.id.checkBoxSubtraction);
        checkBoxMultiplication = (CheckBox) findViewById(R.id.checkBoxMultiplication);
        checkBoxDivision = (CheckBox) findViewById(R.id.checkBoxDivision);

        settings = getSharedPreferences("User Settings", Context.MODE_PRIVATE);
        String testString = settings.getString("myString", "This Is Working");
        testView.setText(testString);

        addition = settings.getString("Addition", "");
        subtraction = settings.getString("Subtraction", "");
        multiplication = settings.getString("Multiplication", "");
        division = settings.getString("Division", "");

        setCheckBoxDisplay(checkBoxAddition, addition);
        setCheckBoxDisplay(checkBoxSubtraction, subtraction);
        setCheckBoxDisplay(checkBoxMultiplication, multiplication);
        setCheckBoxDisplay(checkBoxDivision, division);
    }

    public void onCheckBoxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBoxAddition:
                if(checked){
                    Toast toast = Toast.makeText(getApplicationContext(), "Addition Checked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Addition Unchecked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.checkBoxSubtraction:
                if(checked){
                    Toast toast = Toast.makeText(getApplicationContext(), "Subtraction Checked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Subtraction Unchecked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case R.id.checkBoxMultiplication:
                if(checked){
                    Toast toast = Toast.makeText(getApplicationContext(), "Multiplication Checked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Multiplication Unchecked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

            case  R.id.checkBoxDivision:
                if(checked){
                    Toast toast = Toast.makeText(getApplicationContext(), "Division Checked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Division Unchecked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;

        }

        //if(view.getTag().toString().equals("Addition")){

        //}
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = settings.edit();

        if( checkCheckBoxStatus(checkBoxAddition)){
            editor.putString("Addition", "1");
        }
        else{
            editor.putString("Addition", "0");
        }

        if( checkCheckBoxStatus(checkBoxSubtraction)){
            editor.putString("Subtraction", "1");
        }
        else{
            editor.putString("Subtraction", "0");
        }

        if( checkCheckBoxStatus(checkBoxMultiplication)){
            editor.putString("Multiplication", "1");
        }
        else{
            editor.putString("Multiplication", "0");
        }

        if( checkCheckBoxStatus(checkBoxDivision)){
            editor.putString("Division", "1");
        }
        else{
            editor.putString("Division", "0");
        }

        editor.apply();
    }

}
