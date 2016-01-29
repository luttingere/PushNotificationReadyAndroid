package com.luttinger.receiveapushnotification;

import android.app.Application;
import android.util.Log;

/**
 * Created by Eduardo Luttinger on 29/01/2016.
 */
public class App extends Application {

    private static final String TAG = App.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        if(!PushUtil.isTheDeviceRegisteredForPushNotification(this)){
            PushUtil.registerInBackGround(this);
        }else{
            String token  = PushUtil.getRegistrationId(this);
            Log.i(TAG,"Android device ID for Push = "+token);
        }
    }
}
