package com.example.voiceandtext;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class VoiceToText extends AppCompatActivity {
    TextView txtvoice;
    ImageView imgmic;
    private final int REQUEST_CODE_VOICE_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_to_text);
        txtvoice = findViewById(R.id.txt_voice);
        imgmic = findViewById(R.id.img_mic);
        imgmic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVoiceInput();
            }
        });
    }

    private void getVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Cho tôi nghe giọng nói của bạn!");
        startActivityForResult(intent, REQUEST_CODE_VOICE_INPUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_VOICE_INPUT) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null) {
                    txtvoice.setText(result.get(0));
                    final Handler handler = new Handler();
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VoiceToText.this, "Vui lòng chọn trình duyệt tìm kiếm!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com.vn/#q=" + txtvoice.getText())));
                        }
                    };
                    handler.postDelayed(runnable, 1000);
                }
            }
        }
    }
}

