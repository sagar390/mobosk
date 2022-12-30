package attendance.netsurf.netsurfattendance.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class NotificationModel  implements Parcelable {



    private int CustID;

    private String action;

    private String alert;

    private String title;

    private String Param1;

    private String Param2;

    private String Param3;

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String empID) {
        EmpID = empID;
    }

    public static ArrayList<NotificationModel> getNotificationModels() {
        return notificationModels;
    }

    public static void setNotificationModels(ArrayList<NotificationModel> notificationModels) {
        NotificationModel.notificationModels = notificationModels;
    }

    public static Creator<NotificationModel> getCREATOR() {
        return CREATOR;
    }

    private String EmpID;







    public static ArrayList<NotificationModel> notificationModels = new ArrayList<>();

    //  @PrimaryKey
    private String NotificationId;

    private int isRead = 0;

    public NotificationModel() {
    }


    public void setCustID(int CustID) {
        this.CustID = CustID;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setParam1(String Param1) {
        this.Param1 = Param1;
    }

    public void setParam2(String Param2) {
        this.Param2 = Param2;
    }

    public void setParam3(String Param3) {
        this.Param3 = Param3;
    }

    public void setNotificationId(String NotificationId) {
        this.NotificationId = NotificationId;
    }

    public int getCustID() {
        return CustID;
    }

    public String getAction() {
        return action;
    }

    public String getAlert() {
        return alert;
    }

    public String getTitle() {
        return title;
    }

    public String getParam1() {
        return Param1;
    }

    public String getParam2() {
        return Param2;
    }

    public String getParam3() {
        return Param3;
    }

    public String getNotificationId() {
        return NotificationId;
    }

    public int getIsRead() {
        return isRead;
    }

    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

/*

    public static ArrayList<NotificationModel> getAllureadNotification() {

        realm = Realm.getDefaultInstance();

        RealmResults<NotificationModel> resultcontacts = realm.where(NotificationModel.class).findAll();


        notificationModels.clear();
        for (int i = 0; i < resultcontacts.size(); i++) {
            notificationModels.add(resultcontacts.get(i));
        }

        try {

        } catch (NullPointerException ex) {
        }

    public static ArrayList<NotificationModel> getAllureadNotification() {

        realm = Realm.getDefaultInstance();

        RealmResults<NotificationModel> resultcontacts = realm.where(NotificationModel.class).findAll();


        notificationModels.clear();
        for (int i = 0; i < resultcontacts.size(); i++) {
            notificationModels.add(resultcontacts.get(i));
        }

        try {

        } catch (NullPointerException ex) {
        }

        return notificationModels;

    }


    public static void  deleteCurrentNotify(String notificationid) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NotificationModel> result = realm.where(NotificationModel.class).equalTo("NotificationId", notificationid).findAll();
                result.deleteAllFromRealm();
            }
        });

    }

        return notificationModels;

    }


    public static void  deleteCurrentNotify(String notificationid) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<NotificationModel> result = realm.where(NotificationModel.class).equalTo("NotificationId", notificationid).findAll();
                result.deleteAllFromRealm();
            }
        });

    }*/




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.CustID);
        dest.writeString(this.action);
        dest.writeString(this.alert);
        dest.writeString(this.title);
        dest.writeString(this.Param1);
        dest.writeString(this.Param2);
        dest.writeString(this.Param3);
        dest.writeString(this.EmpID);
        dest.writeString(this.NotificationId);
        dest.writeInt(this.isRead);
    }

    protected NotificationModel(Parcel in) {
        this.CustID = in.readInt();
        this.action = in.readString();
        this.alert =  in.readString();
        this.title =  in.readString();
        this.Param1 = in.readString();
        this.Param2 = in.readString();
        this.Param3 = in.readString();
        this.EmpID = in.readString();
        this.NotificationId = in.readString();
        this.isRead = in.readInt();
    }

    public static final Parcelable.Creator<NotificationModel> CREATOR = new Parcelable.Creator<NotificationModel>() {
        public NotificationModel createFromParcel(Parcel source) {
            return new NotificationModel(source);
        }

        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };


}


