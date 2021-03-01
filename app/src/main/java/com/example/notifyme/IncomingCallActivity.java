package com.example.notifyme;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

public class IncomingCallActivity extends FullScreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);
        findViewById(R.id.activity_answer_call_button).setOnClickListener(__ -> {
            startActivity(new Intent(this, AnsweredCallActivity.class));
            finish();
        });
        findViewById(R.id.activity_hang_up_button).setOnClickListener(__ -> {
            sendBroadcast(new Intent(HangUpReceiver.ACTION_HANG_UP_INCOMING_CALL));
            finish();
        });
    }


}