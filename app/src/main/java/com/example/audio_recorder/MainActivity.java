package com.example.audio_recorder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import android.view.View;
import android.Manifest;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MediaRecorder mediaRecorder;

    public static String fileName = "recorded.3gp";
    public static final Integer RecordAudioRequestCode = 1;

    String file = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + fileName;



    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            checkPermission();
        }

        textView = findViewById(R.id.textView);

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

            mediaRecorder.setOutputFile(file);
    }

    // https://stackoverflow.com/questions/37290752/java-lang-runtimeexception-setaudiosource-failed/39920574
    public void onClick(View v) {
        if(v.getId() == R.id.btnRecord) {

            //Record
            record();

        } else if(v.getId() == R.id.btnStop) {
            //Stop
            stopAudio();

        } else if(v.getId() == R.id.btnPlay) {
            // Play
            play();
        }
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},RecordAudioRequestCode);
        }
    }

    private void record() {
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText("Audio Recording ...");
    }

    private void stopAudio() {
        mediaRecorder.stop();
        mediaRecorder.release();
        textView.setText("Recording Stopped");

    }

    private void play() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(file);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        textView.setText("Playing Recorded Audio ...");

    }


}