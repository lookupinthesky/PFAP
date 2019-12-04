package com.example.pfa_p.Database.Sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;



/**
 * https://github.com/codepath/android_guides/wiki/Server-Synchronization-(SyncAdapter) - Shubham
 *
 *
 * This is used only by Android to run our {@link AccountAuthenticator}.
 */
public class AuthenticatorService extends Service {
    private AccountAuthenticator authenticator;


    @Override
    public void onCreate() {
        // Instantiate our authenticator when the service is created
        this.authenticator = new AccountAuthenticator(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Return the authenticator's IBinder
        return authenticator.getIBinder();
    }
}