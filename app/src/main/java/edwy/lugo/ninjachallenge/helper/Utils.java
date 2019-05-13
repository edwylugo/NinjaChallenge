package edwy.lugo.ninjachallenge.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import edwy.lugo.ninjachallenge.BuildConfig;

public class Utils {

    public static void log (String label, String value){
        if (BuildConfig.LOG_ENABLED){
            Log.i(label,value);
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null &&
                networkInfo.isAvailable() &&
                networkInfo.isConnectedOrConnecting() &&
                networkInfo.isConnected();
    }
}
