package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SigninPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSignUpButtonClick(View view){

        TextView username = findViewById(R.id.editTextLoginUsername);
        TextView password = findViewById(R.id.editTextLoginPassword);
        TextView passwordConfirm = findViewById(R.id.editTextPasswordConfirmation);

        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        String passwordConfirmStr = passwordConfirm.getText().toString();

        if (usernameStr.isEmpty() || passwordStr.isEmpty()){
            String empty = "Signup failed. Username or password field is empty!";
            Toast.makeText(this, empty, Toast.LENGTH_SHORT).show();
            return;
        }

        String message = passwordStr.equals(passwordConfirmStr) ? "Signup Successful" : "Signup Fail. Password doesn't match";

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        if (passwordStr.equals(passwordConfirmStr)){

            saveDataToSharedPreference(usernameStr, passwordStr);

            Intent intentLogin = new Intent(this, LoginPage.class);
            startActivity(intentLogin);
        }

    }

    public void onLoginButton(View view){

        Intent intentLogin = new Intent(this, LoginPage.class);
        startActivity(intentLogin);

    }

    private void saveDataToSharedPreference(String usernameVal, String passwordVal){

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_USERNAME", usernameVal);
        editor.putString("KEY_PASSWORD", passwordVal);

        editor.apply();
    }
}