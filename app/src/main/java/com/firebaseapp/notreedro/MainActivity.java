package com.firebaseapp.notreedro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebaseapp.notreedro.utils.EmailValidator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText emailInput;
    EditText passwordInput;

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        firebaseAuth = FirebaseAuth.getInstance();

        TextView authRegisterButton = findViewById(R.id.authRegisterButton);
        authRegisterButton.setOnClickListener(view -> goToNextActivity(RegisterActivity.class));

        TextView authForgotPasswordButton = findViewById(R.id.authForgotPasswordButton);
        authForgotPasswordButton.setOnClickListener(view -> goToNextActivity(ForgotPasswordActivity.class));

        Button authSkipButton = findViewById(R.id.authSkipButton);
        authSkipButton.setOnClickListener(view -> setContentView(R.layout.note_list_page));


        loginButton = findViewById(R.id.authLoginButton);
        emailInput = findViewById(R.id.authLoginInput);
        passwordInput = findViewById(R.id.authPasswordInput);
        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = emailInput.getText().toString();

        String passwordS = passwordInput.getText().toString();

        if (email.isEmpty() || passwordS.isEmpty()) {
            if (email.isEmpty()) {
                emailInput.setError("Поле Email не заполнено");
            }
            if (passwordS.isEmpty()) {
                passwordInput.setError("Поле пароль не заполнено");
            }
        } else if (!EmailValidator.isValidEmail(email)) {
            emailInput.setError("Email не валидный");

        } else {
            firebaseAuth
                    .signInWithEmailAndPassword(email, passwordS)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Успешно",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                emailInput.setError("Пользователь не найден");
                                emailInput.requestFocus();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this,
                                        "Неизвестная ошибка: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void goToNextActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}