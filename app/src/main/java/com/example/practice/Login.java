package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    private EditText email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String currentUser, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void loginBtnClick(View view) {
        if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
            Toast.makeText(Login.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        currentUser = mAuth.getCurrentUser().getUid();
                        Toast.makeText(Login.this, currentUser, Toast.LENGTH_SHORT).show();

                        //getUserRole(currentUser);
                        mDatabase.child("Users").child(currentUser).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task2) {
                                if (!task2.isSuccessful()) {
                                    Toast.makeText(Login.this, "Ошибка получения роли пользователя", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    role = task2.getResult().getValue().toString();
                                    Toast.makeText(Login.this, "Вы вошли под ролью " + role, Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("UserName", currentUser);
                                    intent.putExtra("UserRole", role);
                                    startActivity(intent);
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Login.this, "Пользователь не найден или пароль неверный", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void getUserRole(String userId){
        //в этом месте не работает, просто сразу выходит из метода
        mDatabase.child("Users").child(userId).child("role").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Ошибка получения роли пользователя", Toast.LENGTH_SHORT).show();
                }
                else {
                    role = task.getResult().getValue().toString();
                    Toast.makeText(Login.this, "Вы вошли под ролью " + role, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void registerBtnClick(View view) {
        startActivity(new Intent(Login.this, Register.class));
    }
}