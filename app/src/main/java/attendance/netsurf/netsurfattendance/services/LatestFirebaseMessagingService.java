package attendance.netsurf.netsurfattendance.services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import attendance.netsurf.netsurfattendance.NotificationUtils;
import attendance.netsurf.netsurfattendance.ProfileDashBoardActivity;
import attendance.netsurf.netsurfattendance.database.DatabaseHelper;
import attendance.netsurf.netsurfattendance.models.NotificationModel;


public class LatestFirebaseMessagingService extends FirebaseMessagingService {



    NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("FirebaseMessaging", "From: " + remoteMessage.getData());
        // Check if message contains a data payload.


        /*{"to" : "fm0NSO_TnjQ:APA91bHwW5CDU1cwdU2ERRa8lMWlR1y8djusf4Z_lmHQaKAukBS9TIyAOMLUIuVDgKV0saNdtrMEVAX_sX1d0rxTiwuLZ0STNME5Y-wo477ipF2eXl9kNaMp_As4pkRoomv9Sp1NM7Kv", "priority" : "high","data": {"CustID": "0","Title":"sagar","Alert":"hdh","Action": "2","Param1":"1","Param2": "","Param3": "","NotificationId": "180232"}}*/


        if (remoteMessage.getData().size() > 0) {
         //   Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
               // SharedPreferences mPrefs = getApplicationContext().getPreferences(MODE_PRIVATE);
                JSONObject json = new JSONObject(remoteMessage.getData());

                final   NotificationModel notification = new NotificationModel();
                notification.setEmpID(json.get("CustID").toString());
                notification.setAction(json.get("Action").toString());
                notification.setAlert(json.get("Alert").toString());
                notification.setTitle(json.get("Title").toString());
                notification.setParam1(json.get("Param1").toString());
                notification.setParam2(json.get("Param2").toString());
                notification.setParam3(json.get("Param3").toString());
                notification.setNotificationId(json.get("NotificationId").toString());

                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                databaseHelper.saveNotification(notification);

                //Log.e(TAG, "Saved--------- "+databaseHelper.saveNotification(notification));

                //saveNotification(notification);
                showNotificationMessage(getApplicationContext(), notification);
                //  Toast.makeText(getApplicationContext(), "Push showing", Toast.LENGTH_LONG).show();
                //  handleDataMessage(json);

            } catch (Exception e) {
                //Log.e(TAG, "Exception: " + e.getMessage());

            }
        }
    }

    private void showNotificationMessage(Context context, NotificationModel notificationModel) {

        Intent intent = new Intent(context, ProfileDashBoardActivity.class);
        notificationUtils = new NotificationUtils(context);
        //intent.putExtras(parseIntent.getExtras());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // notificationUtils.showNotification(notificationModel, intent);
        notificationUtils.showNotificationMessage(notificationModel, intent);
        //Log.e(TAG, "Exception: ");

    }

    /*{"to" : "fNlSiKSfrCc:APA91bHkfzrGaPpSU2h5a8BNFQTSUysnMRD4elWBSc68PQijUoYc2ODoMj0xfnZc81PU8QFifSTnMMl_D9Awxj-Er3RS3xrSOtb3OYX21dw7mitax0T21o_Q5WGb6xn98HwbCuxZAmBE", "priority" : "high","notification" : {"body" : " This is to inform that leave application has been raised by Shruti Taneja from 15/11/2014 to 19/11/2014.Regards,Team HR","title" : "PAYROLL:Leave Request", },
    "data": {"CustID": "","action": "","Param1": "http://172.16.1.208:726/NewUI/RoutMe.aspx?pagename=1&userLogin=218&password=123&Url=LeaveApprove.aspx?FromApp=1~EL_Id=14","Param2": "","Param3": "","NotificationId": ""}}*/
}

