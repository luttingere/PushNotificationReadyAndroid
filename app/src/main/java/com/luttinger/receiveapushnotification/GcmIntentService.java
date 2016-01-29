package com.luttinger.receiveapushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Eduardo Luttinger on 29/01/2016.
 *
 *  Service to handle Push Notifications
 *
 */
public class GcmIntentService extends IntentService {


    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        handleTheNotification(extras);
    }

    /**
     * Handle all Push Notification Operations
     *
     * @param pushParameters (Map of parameters)
     */
    private void handleTheNotification(Bundle pushParameters){

    }
}
