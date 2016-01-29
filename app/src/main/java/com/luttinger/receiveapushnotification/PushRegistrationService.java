package com.luttinger.receiveapushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by Eduardo Luttinger on 29/01/2016.
 */
public class PushRegistrationService extends IntentService {

    private static final String TAG = "PushIDListenerService";
    private static final String[] TOPICS = {"global"};

    public PushRegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        InstanceID instanceID = InstanceID.getInstance(this);
        String token;
        try {

            token = instanceID.getToken(getString(R.string.pushSenderID),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);

            //Save ur token id in your api server HERE
            sendRegistrationToServer(token);

            // Subscribe to topic channels
            //subscribeTopics(token);

            sharedPreferences.edit().putBoolean(PushUtil.REGISTRATION_DONE, Boolean.TRUE).apply();

        } catch (IOException ioe) {
            Log.d(TAG, "Failed to complete token refresh", ioe);
            // If an exception happens while fetching the new token or updating our registration data
            // on a our server, this ensures that we will attempt the update later.
            sharedPreferences.edit().putBoolean(PushUtil.REGISTRATION_DONE, Boolean.FALSE).apply();
        }
    }

    /**
     * Persist registration in the api service.
     *
     * @param token
     */
    private void sendRegistrationToServer(String token){
        Log.i(TAG,"*****App Token created: "+token);
        PushUtil.saveTheDeviceToken(token,this);
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

}
