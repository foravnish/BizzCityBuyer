package buyer.bizzcity.bizzcityinfobuyer.FCM;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import buyer.bizzcity.bizzcityinfobuyer.Activites.HomeAct;
import buyer.bizzcity.bizzcityinfobuyer.R;


/**
 * Created by Andriod Avnish on 06-Mar-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    //  private NotificationUtils notificationUtils;

    Bitmap image;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "Fromgdfhgdfghfgjf: " + remoteMessage.getFrom());
        Log.e(TAG, "Fromgdfhgdfghfgjf: " + remoteMessage.getData());
        Log.e(TAG, "Fromgdfhgdfghfgjf: " + remoteMessage.getMessageType());


        String data= String.valueOf(remoteMessage.getData());
        String org=data.replace("message=","");

        //org.substring(1, org.length()-1);
        //Log.d("ffdgdfgdfgd",  org.substring(1, org.length()-1).toString());

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(org.substring(1, org.length()-1).toString());
            Log.d("ffdgdfgdfgd",jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        JSONObject jsonObject1=jsonObject.optJSONObject("data");
//        Log.d("fsdgdgsdfgfdgdf",jsonObject1.optString("title"));
//        Log.d("fsdgdgsdfgfdgdf",jsonObject1.toString());
        //Log.d("fgdfgdfhdfhfghf",MyPrefrences.getNotiStatus(getApplicationContext())+"");


        //Todo notification
       // if (MyPrefrences.getNotiStatus(getApplicationContext())==true) {

        try {
            URL url = new URL(jsonObject.optString("image").toString());
            Log.d("fgdgdfgd",jsonObject.optString("image").toString());
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }


            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine(jsonObject.optString("message").toString());
            Notification notification;
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    getApplicationContext());
            Intent notificationIntent = new Intent(getApplicationContext(), HomeAct.class);
            PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
            notification = mBuilder.setSmallIcon(R.drawable.bci_small).setTicker("New Matching Lead").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(jsonObject.optString("title").toString())
                    .setTicker(jsonObject.optString("title").toString())
//                .setContentIntent(resultPendingIntent)
                    .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
                    .setSmallIcon(R.drawable.bci_small)
                    .setContentIntent(contentIntent)
                    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
//                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(jsonObject.optString("message").toString())
                    .build();


            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notification);
     //   }

    }


//    private void handleNotification(String message) {
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        }else{
//            // If the app is in background, firebase itself handles the notification
//        }
//    }

//    private void handleDataMessage(JSONObject json) {
//        Log.e(TAG, "push json: " + json.toString());
//
//        try {
//            JSONObject data = json.getJSONObject("data");
//
//            String title = data.getString("title");
//            String message = data.getString("message");
//            boolean isBackground = data.getBoolean("is_background");
//            String imageUrl = data.getString("image");
//            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");
//
//            Log.e(TAG, "title: " + title);
//            Log.e(TAG, "message: " + message);
//            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
//            Log.e(TAG, "imageUrl: " + imageUrl);
//            Log.e(TAG, "timestamp: " + timestamp);
//
//
//            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//                // app is in foreground, broadcast the push message
//                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//                pushNotification.putExtra("message", message);
//                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//
//                // play notification sound
//                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//                notificationUtils.playNotificationSound();
//            } else {
//                // app is in background, show the notification in notification tray
//                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
//                resultIntent.putExtra("message", message);
//
//                // check for image attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    // image is present, show notification with image
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
//            }
//        } catch (JSONException e) {
//            Log.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            Log.e(TAG, "Exception: " + e.getMessage());
//        }
//    }

    /**
     * Showing notification with text only
     */
//    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
//    }

    /**
     * Showing notification with text and image
     */
//    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
//    }
}