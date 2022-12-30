package attendance.netsurf.netsurfattendance;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import attendance.netsurf.netsurfattendance.models.NotificationModel;
import timber.log.Timber;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by shivam on 9/2/15.
 */
public class NotificationUtils {

    private String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils() {
    }

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(NotificationModel notificationModel, Intent intent) {

        Log.e("creating notification", "for value " + notificationModel.getTitle());
        // Check for empty push message
        if (TextUtils.isEmpty(notificationModel.getTitle()))
            return;

      //  ArrayList<String> unReadMessages = getPreviousUnReadMessage();
     //   Log.e("creating notification", "for value " + unReadMessages);
        /*if( unReadMessages.size() > 1 ){
            showCombinedNotification(intent, unReadMessages, notificationModel);
            Timber.d("Showing combined notification");
        }else {
            showNotification(intent, notificationModel);

       }*/

        showNotification(intent, notificationModel);

    }

    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am
                    .getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo
                        .IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


/*

    private ArrayList<String> getPreviousUnReadMessage(){
        ArrayList<String> unReadMessages = new ArrayList<>();
        List<NotificationModel> list = NotificationModel.getAllureadNotification();
        if(list != null && list.size() != 0 ){
            for( int i = 0; i < list.size(); i++){
                unReadMessages.add(list.get(i).getTitle());
            }
        }
        return unReadMessages;
    }*/




    public void showNotification(Intent intent,NotificationModel notificationModel) {


        Timber.d("Showing single notification");
        int icon = R.drawable.netsurf_logo;
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(R.mipmap.agreement)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentTitle(notificationModel.getTitle())
                .setAutoCancel(true)
                .setContentText(notificationModel.getAlert());
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        intent.putExtra(Constants.INTENT_TAG_DATA, notificationModel);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }



    public void showNormalNotification(Intent intent, NotificationModel notificationModel){


        //Toast.makeText(mContext, "Push showing", Toast.LENGTH_LONG).show();

        Timber.d("Showing single notification");
        int icon = R.drawable.netsurf_logo;
        intent.putExtra(Constants.INTENT_TAG_COMBO_FLAVOUR, false);
        intent.putExtra(Constants.INTENT_TAG_DATA, notificationModel);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker(notificationModel.getTitle()).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(notificationModel.getAlert())
                .setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(notificationModel.getTitle())
                .build();
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);


        //new code

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = mContext.getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, notificationModel.getTitle(), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(notificationModel.getAlert());
            notificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

*/



       /* PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0 *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = mContext.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, channelId)
                        .setSmallIcon(icon)
                        .setContentTitle(notificationModel.getTitle())
                        .setContentText(notificationModel.getAlert())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManagers =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManagers.createNotificationChannel(channel);
        }

        notificationManagers.notify(0 *//* ID of notification *//*, notificationBuilder.build());*/













    }


    private void showCombinedNotification(Intent intent, ArrayList<String> messages, NotificationModel notificationModel){

      /*  int icon = R.drawable.ic_launcher;
        intent.putExtra(Constants.INTENT_TAG_COMBO_FLAVOUR, true);
        intent.putExtra(Constants.INTENT_TAG_DATA, notificationModel);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for(int i = 0; i < messages.size(); i++){
            inboxStyle.addLine(""+messages.get(i));
        }
        inboxStyle.setBigContentTitle(messages.size() + " new messages from Netsurf");
        inboxStyle.setSummaryText(messages.size() + "  messages");

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);
        Notification notification = mBuilder.setSmallIcon(icon).setTicker("Netsurf").setWhen(0)
                .setAutoCancel(true)

                .setContentTitle("Netsurf Direct")
                .setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(notificationModel.getAlert())
                .build();

        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);*/





        int icon = R.drawable.netsurf_logo;

        Timber.d("Showing single notification");
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for(int i = 0; i < messages.size(); i++){
            inboxStyle.addLine(""+messages.get(i));
        }
        inboxStyle.setBigContentTitle(messages.size() + " new messages from Netsurf");
        inboxStyle.setSummaryText(messages.size() + "  messages");


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(R.mipmap.agreement)
                .setTicker("Netsurf Payroll")
                .setWhen(0)
                .setContentTitle(""+notificationModel.getAlert())
                .setStyle(inboxStyle)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(notificationModel.getTitle())
                .setAutoCancel(true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
        intent.putExtra(Constants.INTENT_TAG_COMBO_FLAVOUR, true);

        intent.putExtra(Constants.INTENT_TAG_DATA, notificationModel);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());


    }


}