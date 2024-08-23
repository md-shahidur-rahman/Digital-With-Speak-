package com.post.digitaltasbih;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int SPEECH_REQUEST_CODE = 0;
    private TextView allahuAkberCount;
    private TextView subhanallahCount;
    private TextView alhamdulillahCount;
    private int allahuAkberCounter = 0;
    private int subhanallahCounter = 0;
    private int alhamdulillahCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allahuAkberCount = findViewById(R.id.allahuAkberCount);
        subhanallahCount = findViewById(R.id.subhanallahCount);
        alhamdulillahCount = findViewById(R.id.alhamdulillahCount);
        Button startListeningButton = findViewById(R.id.startListeningButton);

        startListeningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startListening();
            }
        });
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a word");

        /// I changed it for offline
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String spokenText = results.get(0).toLowerCase();

            if (spokenText.contains("allah hu akbar") || spokenText.contains("allahu akbar")) {
                allahuAkberCounter++;
                allahuAkberCount.setText("Allahu Akber: " + allahuAkberCounter);
            } else if (spokenText.contains("subhanallah")) {
                subhanallahCounter++;
                subhanallahCount.setText("Subhanallah: " + subhanallahCounter);
            } else if (spokenText.contains("alhamdulillah")) {
                alhamdulillahCounter++;
                alhamdulillahCount.setText("Alhamdulillah: " + alhamdulillahCounter);
            }
        }
    }
}
