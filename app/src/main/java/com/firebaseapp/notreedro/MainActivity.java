package com.firebaseapp.notreedro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        TextView authRegisterButton = findViewById(R.id.authRegisterButton);
        authRegisterButton.setOnClickListener(view -> goToNextActivity(RegisterActivity.class));

        TextView authForgotPasswordButton = findViewById(R.id.authForgotPasswordButton);
        authForgotPasswordButton.setOnClickListener(view -> goToNextActivity(ForgotPasswordActivity.class));


    }

    private void goToNextActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}