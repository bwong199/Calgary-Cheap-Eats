package com.benwong.cheapeatscalgary;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by benwong on 2016-08-14.
 */
public class Utility {

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
