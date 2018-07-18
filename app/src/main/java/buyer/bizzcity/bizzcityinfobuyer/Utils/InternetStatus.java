package buyer.bizzcity.bizzcityinfobuyer.Utils;

/**
 * Created by bsuser on 19-09-2015.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetStatus {
    private Context _context;


    public static boolean isConnectingToInternet(Context context1) {

        ConnectivityManager cm = (ConnectivityManager) context1.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        } else {
            // not connected to the internet
            Toast.makeText(context1, "No Internet Connection", Toast.LENGTH_SHORT).show();
            return false;
        }



        return false;
    }
}