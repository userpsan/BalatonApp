package com.example.balatonapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(context,
                "BalatonApp emlékeztető",
                "Ne felejtsd el megnézni a kedvenceidet!");
    }
}
