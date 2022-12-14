package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mBase;
    //private StorageReference mStorageRef;
    //private Uri uploadUri;

    private String DEFAULT_ROLE = "User";
    private String currentUser;

    private EditText usernameTB, emailTB, passwordTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        mBase = FirebaseDatabase.getInstance().getReference("Users");
        //mStorageRef = FirebaseStorage.getInstance().getReference("images");

        usernameTB = findViewById(R.id.usernameRegister);
        emailTB = findViewById(R.id.emailRegister);
        passwordTB = findViewById(R.id.passwordRegister);
    }

    public void registerBtnClick(View view) {
        if (usernameTB.getText().toString().isEmpty() || emailTB.getText().toString().isEmpty() || passwordTB.getText().toString().isEmpty()){
            Toast.makeText(Register.this, "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
        }
        else
        {
            mAuth.createUserWithEmailAndPassword(emailTB.getText().toString(), passwordTB.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //uploadUri = task.getResult();
                        try {
                            currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //Toast.makeText(Register.this, currentUser, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(Register.this, "Ошибка: нулевой UID получен", Toast.LENGTH_SHORT).show();
                        }

                        saveUser();
                        Intent intent = new Intent(Register.this, MainActivity.class);
                        intent.putExtra("UserName", currentUser);
                        intent.putExtra("UserRole", DEFAULT_ROLE);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Register.this, "Ошибка при регистрации", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void saveUser()
    {
        /*

        Возможно, полное говно. ID в реалтайме совпадает с UID в аутентификационной таблице.
        Это облегчит поиск пользователя, но в теории может вызвать коллизии

        Так же нужно будет в идеале сохранять фото профиля

        */

        //String id = mBase.push().getKey();
        String id = currentUser;
        String username = usernameTB.getText().toString();
        String email = emailTB.getText().toString();
        String password = passwordTB.getText().toString();
        AddUser newUserAdd = new AddUser(username, email, password, DEFAULT_ROLE);
        //AddUser newUserAdd = new AddUser(id,username,email,password,uploadUri.toString());
        mBase.child(id).setValue(newUserAdd);
        Toast.makeText(Register.this,"Новый пользователь",Toast.LENGTH_SHORT).show();
    }
}