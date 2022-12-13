package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Player extends AppCompatActivity {

    SeekBar seekBar;
    TextView timeNow, timeTotal;
    MediaPlayer mediaPlayer;
    ImageView playBtn, pauseBtn;

    DateFormat df = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        seekBar = findViewById(R.id.seekBar);
        timeNow = findViewById(R.id.timeNow);
        timeTotal = findViewById(R.id.timeTotal);
        playBtn = findViewById(R.id.playBtn);
        pauseBtn = findViewById(R.id.pauseBtn);

        //mediaPlayer

        mediaPlayer = MediaPlayer.create(Player.this, R.raw.track_01);

        int duration = mediaPlayer.getDuration() / 1000;
        timeTotal.setText(df.format(duration));
        seekBar.setMax(duration);
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/practice-427d4.appspot.com/o/music%2Frave-the-reqviem-fuck-the-universe.mp3?alt=media&token=ace301b2-aa22-4d12-a28f-d43613161ea5");
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
        }
        catch (Exception e){
            Toast.makeText(Player.this, "Произошла ошибка медиаплеера", Toast.LENGTH_SHORT).show();
        }

        // Сделать отображение указателя на слайдере, и перемотку по слайдеру
        //
        // mediaPlayer.listener

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = seekBar.getProgress() * 1000; //изначально в миллисекундах, так что нужно умножить на 1000
                timeNow.setText(df.format(time));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    public void playBtnClick(View view) {
        mediaPlayer.start();
        pauseBtn.setVisibility(View.VISIBLE);
        pauseBtn.setClickable(true);
        playBtn.setVisibility(View.GONE);
        playBtn.setClickable(false);
    }

    public void pauseBtnClick(View view){
        mediaPlayer.pause();
        pauseBtn.setVisibility(View.GONE);
        pauseBtn.setClickable(false);
        playBtn.setVisibility(View.VISIBLE);
        playBtn.setClickable(true);
    }
}