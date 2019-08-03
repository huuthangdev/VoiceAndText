package com.example.voiceandtext;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TextToVoice extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextToSpeech textToSpeech;
    ImageView btnsummit;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_voice);
        textToSpeech = new TextToSpeech(this, this);
        btnsummit = findViewById(R.id.img_summit);
        edt = findViewById(R.id.edt_text);
        edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    voiceOutput();
                }
                return false;
            }
        });
        btnsummit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceOutput();
            }
        });
    }

    private void voiceOutput() {
        CharSequence text = edt.getText();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id1");
    }

    @Override
    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            btnsummit.setEnabled(true);
            voiceOutput();
        }

    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
