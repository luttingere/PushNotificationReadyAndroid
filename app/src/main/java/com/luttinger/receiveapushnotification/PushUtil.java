package com.luttinger.receiveapushnotification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by Eduardo Luttinger on 29/01/2016.
 *
 *  Utility class made to handle all operations with GCM
 *
 */
public class PushUtil {

    /**
     * Identifier of this class for log purpose
     */
    private static final String TAG = PushUtil.class.getName();

    /**
     * Unique id for the request
     */
    public static int PLAY_SERVICES_RESOLUTION_REQUEST = 1001;

    /**
     * Descriptor of the device id registered
     */
    public static String REGISTRATION_ID = "REGISTRATION_ID";

    /**
     * Boolean Indicator if the app already store the devide token in the app server
     */
    public static String REGISTRATION_DONE = "REGISTRATION_DONE";

    /**
     * Validate if the Google play Service are active in the app, this method also
     * hanlde and show the errors to the user
     * @param context the host Activity
     * @return available or nor available (TRUE OR FALSE)
     */
    public static Boolean checkGooglePlayServices(Context context){
        Boolean isAvailable = Boolean.TRUE;
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,(Activity)context,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else{
                Log.e(TAG,"This device is not supported");
            }
            isAvailable = Boolean.FALSE;
        }
        return isAvailable;
    }

    /**
     * Search for the registration id in the user preferences cache
     * @param context Activity who host this functionality
     * @return registration id
     */
    public static String getRegistrationId(Context context){
        String registrationId = "";
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        registrationId = preferences.getString(REGISTRATION_ID,"");
        return registrationId;
    }

    /**
     * Validate if the apps are already registered for push notifications
     * @param context
     * @return True(Already registered) or False (Not registered)
     */
    public static Boolean isTheDeviceRegisteredForPushNotification(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(PushUtil.REGISTRATION_DONE,Boolean.FALSE);
    }

    /**
     * Save the token id in the user preference local storage
     * @param token
     * @param context
     */
    public static void saveTheDeviceToken(String token, Context context){
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putString(REGISTRATION_ID,token).apply();
    }


    /**
     * Launch the register push service
     * @param context
     */
    public static void registerInBackGround(Context context){
        Intent intent = new Intent(context,PushRegistrationService.class);
        context.startService(intent);
    }


}
