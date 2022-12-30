package attendance.netsurf.netsurfattendance.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import attendance.netsurf.netsurfattendance.models.NotificationModel;

public class DatabaseHelper  extends SQLiteOpenHelper {

    private static final String TAG = "RouteDbHelper";

    // Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private  static final String DATABASE_NAME = "NotificationDb";

/*SELECT  * FROM RouteTable
 where CGI in
(select Distinct CGI from RouteTable) order by id asc LIMIT 1*/

    private static final String TABLE_NAME = "NotificationTBl";

  //  private static final String MCC = "MCC";
    private static final String EmpID = "EmpID";
    private static final String Actions = "Actions";
    private static final String Alert = "Alert";
    private static final String Title = "Title";

    private static final String Param1 = "Param1";
    private static final String Param2 = "Param2";
    private static final String Param3 = "Param3";
    private static final String NotificationId = "NotificationId";




    SQLiteDatabase routedb = null;
    String RouteDbPath = "";
    String CommandText = "";


    public DatabaseHelper(Context context) {

         super(context, DATABASE_NAME, null, 5);
     //   super(context, DB_PATH, null, DATABASE_VERSION);

    }




    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {

        try
        {

            String CREATE_MOBILEGI_TABLE = "CREATE TABLE " + TABLE_NAME + "(Id INTEGER PRIMARY KEY, EmpID TEXT, Actions TEXT, Alert TEXT, Title TEXT, Param1 TEXT, Param2 TEXT, Param3 TEXT, NotificationId TEXT)";
            db.execSQL(CREATE_MOBILEGI_TABLE);


        }catch (Exception e) {
            Log.d("notifyDBCreateErr", e.getMessage());


        }
    }



    public  String FilterRoute() {

        String strfilter = "";

        String cmdtxt = "";
        try
        {




        }catch (Exception e) {
            Log.d("filter", e.getMessage());



        }

        return  strfilter;
    }


    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


    public boolean saveNotification(NotificationModel information) {

        boolean createSuccessful = false;

        ContentValues values = new ContentValues();

        values.put(EmpID, information.getCustID());
        values.put(Actions, information.getAction());
        values.put(Alert, information.getAlert());
        values.put(Title, information.getTitle());
        values.put(Param1, information.getParam1());
        values.put(Param2, information.getParam2());
        values.put(Param3, information.getParam3());
        values.put(NotificationId, information.getNotificationId());

        SQLiteDatabase db = this.getWritableDatabase();

        createSuccessful = db.insert(TABLE_NAME, null, values) > 0;
        db.close();

        return createSuccessful;

    }





    public ArrayList<NotificationModel> getAppNotificationDetail() {


        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        ArrayList<NotificationModel> data = new ArrayList<NotificationModel>();

        if (cursor.moveToFirst()) {
            do {
                // get the data into array, or class variable

                NotificationModel notificationModel = new NotificationModel();
                notificationModel.setEmpID(cursor.getString(1));
                notificationModel.setAction(cursor.getString(2));
                notificationModel.setAlert(cursor.getString(3));
                notificationModel.setTitle(cursor.getString(4));
                notificationModel.setParam1(cursor.getString(5));
                notificationModel.setParam2(cursor.getString(6));
                notificationModel.setParam3(cursor.getString(7));
               // notificationModel.setEmpID(cursor.getString(1));

                data.add(notificationModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }


    public void deleteNotification(String nid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_NAME,
                NotificationId + " = ?",
                new String[]{nid});

        // 3. close
        db.close();

    }


    public void deleteAllNotification() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
      /*  db.delete(TABLE_NAME,
                Title + " = ?",
                new String[]{nid});
*/
        // 3. close

        db.delete(TABLE_NAME, null, null);



        db.close();

    }




    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //    db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_EXPIRY);
        //   db.execSQL("DROP TABLE IF EXISTS " + TABLE_APP_MOBILE);
        // Create tables again
        onCreate(db);
    }

}
