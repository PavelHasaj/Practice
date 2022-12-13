package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String userName, role;
    private ImageView playerPlayBtn, likedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerPlayBtn = findViewById(R.id.playerPlayBtn);
        likedBtn = findViewById(R.id.likedBtn);
        Intent i = this.getIntent();
        if (i!=null){

            userName = i.getStringExtra("UserName");
            role = i.getStringExtra("UserRole");
            int a = 0;
        }

        if (role.equals("Admin")){
            Toast.makeText(MainActivity.this, "help Admin", Toast.LENGTH_SHORT).show();
            playerPlayBtn.setVisibility(View.GONE);
        }
        else if (role == "User")
        {
            Toast.makeText(MainActivity.this, "help not Admin", Toast.LENGTH_SHORT).show();
            likedBtn.setVisibility(View.GONE);
        }
        else {
            Toast.makeText(MainActivity.this, "fig znaet kto", Toast.LENGTH_SHORT).show();
        }

    }

    public void searchBtnClick(View view) {
        //TODO код для поиска треков
    }

    public void playBtnClick(View view) {
        Intent intent = new Intent(MainActivity.this, Player.class);
        startActivity(intent);
    }

    public void likedBtnClick(View view) {
        //TODO переход в список понравившихся треков
    }

    public void playlistBtnClick(View view) {
        //TODO открыть текущий плейлист
    }
}