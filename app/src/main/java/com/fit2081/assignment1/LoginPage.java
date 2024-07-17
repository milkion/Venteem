package com.fit2081.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        TextView tvUsername = findViewById(R.id.editTextLoginUsername);
        String usernameRes = sharedPreferences.getString("KEY_USERNAME", "DEFAULT_VALUE");

        boolean loginOnce = sharedPreferences.getBoolean("KEY_LOGIN_ONCE", false);

        if(loginOnce){
            tvUsername.setText(usernameRes);
        }
    }

    public void onLoginButtonClick(View view){

        TextView tvUsername = findViewById(R.id.editTextLoginUsername);
        TextView tvPassword = findViewById(R.id.editTextLoginPassword);

        String usernameStr = tvUsername.getText().toString();
        String passwordStr = tvPassword.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        String usernameRes = sharedPreferences.getString("KEY_USERNAME", "DEFAULT_VALUE");
        String passwordRes = sharedPreferences.getString("KEY_PASSWORD", "DEFAULT_USERNAME");

        String message = "";

        if (usernameStr.equals(usernameRes) && passwordStr.equals(passwordRes)){

            message = "Login successful.";

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("KEY_LOGIN_ONCE", true);
            editor.apply();

            Intent intentDashboard = new Intent(this, NewEvent.class);
            startActivity(intentDashboard);

            tvPassword.setText("");

        } else {
            message = "Login failed, password doesn't match.";
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}