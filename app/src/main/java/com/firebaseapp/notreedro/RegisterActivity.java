package com.firebaseapp.notreedro;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebaseapp.notreedro.utils.EmailValidator;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText emailInput;
    EditText passwordFirstInput;
    EditText passwordSecondInput;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        firebaseAuth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.authRegisterLoginInput);
        passwordFirstInput = findViewById(R.id.authRegisterPasswordFirstInput);
        passwordSecondInput = findViewById(R.id.authRegisterPasswordSecondInput);
        registerButton = findViewById(R.id.authRegisterButton);

        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String email = emailInput.getText().toString();

        String passwordF = passwordFirstInput.getText().toString();
        String passwordS = passwordSecondInput.getText().toString();

        if (email.isEmpty() || passwordF.isEmpty() || passwordS.isEmpty()) {
            if (email.isEmpty()) {
                emailInput.setError("Поле Email не заполнено");
            }
            if (passwordF.isEmpty()) {
                passwordFirstInput.setError("Поле пароль не заполнено");
            }
            if (passwordS.isEmpty()) {
                passwordSecondInput.setError("Поле подтверждение пароля не заполнено");
            }
        } else if (!EmailValidator.isValidEmail(email) || passwordF.length() < 8 || !passwordF.equals(passwordS)) {
            if (!EmailValidator.isValidEmail(email)) {
                emailInput.setError("Email не валидный");
            }
            if (passwordF.length() < 8) {
                passwordFirstInput.setError("Пароль должен содержать 8 символов");
            }
            if (!passwordF.equals(passwordS)) {
                passwordSecondInput.setError("Пароли не совпадают");
            }
        } else {
            firebaseAuth
                    .createUserWithEmailAndPassword(email, passwordS)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Успешно",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(RegisterActivity.this,
                                    "Ошибка регистрации: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
