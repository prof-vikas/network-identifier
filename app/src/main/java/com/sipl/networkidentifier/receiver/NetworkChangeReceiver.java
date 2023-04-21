package com.sipl.networkidentifier.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.sipl.networkidentifier.MainActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {
    public Boolean isWifiEnable;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            Toast.makeText(context, "Connected to Wi-Fi network", Toast.LENGTH_SHORT).show();
            ((MainActivity) context) .setNetworkMode("Wifi Network", "Wifi Network Api result :");
            isWifiEnable = true;

        }else if (activeNetwork != null && activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
            ((MainActivity) context) .setNetworkMode("Mobile Network", "Mobile Network Api result :");
            Toast.makeText(context, "Connected to mobile network", Toast.LENGTH_SHORT).show();
            isWifiEnable = false;
        }
    }

}
