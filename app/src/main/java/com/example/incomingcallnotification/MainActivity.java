package com.example.incomingcallnotification;

import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private NotificationManager notificationManager;
    private final HangUpReceiver hangUpReceiver = new HangUpReceiver();
    private IncomingCallNotificationBuilder incomingCallNotificationBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        incomingCallNotificationBuilder = new IncomingCallNotificationBuilder(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        hangUpReceiver.setAnsweredCallHangUpHandler(this::handleAnsweredCallHangUp);
        hangUpReceiver.setIncomingCallHangUpHandler(this::handleIncomingCallHangUp);
        final IntentFilter hangUpBroadcastFilter = new IntentFilter();
        hangUpBroadcastFilter.addAction(HangUpReceiver.ACTION_HANG_UP_INCOMING_CALL);
        hangUpBroadcastFilter.addAction(HangUpReceiver.ACTION_HANG_UP_ANSWERED_CALL);
        registerReceiver(hangUpReceiver, hangUpBroadcastFilter);
        findViewById(R.id.notify).setOnClickListener(__ -> sendNotification());

        findViewById(R.id.notify_delayed).setOnClickListener(btn -> btn.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification();
            }
        }, 5000));
        findViewById(R.id.cancel).setOnClickListener(__ -> cancelNotification());
    }

    private void handleAnsweredCallHangUp() { 
        Log.i(getLocalClassName(), "ANSWERED CALL HANGED UP");
    }

    private void handleIncomingCallHangUp() {
        cancelNotification();
    }

    private void sendNotification() {
        notificationManager.notify(R.integer.incoming_call_notification_id, incomingCallNotificationBuilder.build());
    }

    private void cancelNotification() {
        notificationManager.cancel(R.integer.incoming_call_notification_id);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(hangUpReceiver);
        super.onDestroy();
    }
}