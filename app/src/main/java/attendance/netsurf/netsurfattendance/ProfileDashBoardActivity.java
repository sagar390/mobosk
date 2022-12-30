package attendance.netsurf.netsurfattendance;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import attendance.netsurf.netsurfattendance.adapter.CustomExpandableListAdapter;
import attendance.netsurf.netsurfattendance.database.DatabaseHelper;
import attendance.netsurf.netsurfattendance.NewDashboard.modelData.ExpandableListDataPump;
import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay;
import attendance.netsurf.netsurfattendance.models.FlowerData;
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount;
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId;
import attendance.netsurf.netsurfattendance.models.NotificationModel;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ProfileDashBoardActivity extends AppCompatActivity {

        ExpandableListView expandableListView;
        ExpandableListAdapter expandableListAdapter;
        List<String> expandableListTitle;
        HashMap<String, List<String>> expandableListDetail;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    HashMap<String, List<String>> expandableListDetailnew =  new  HashMap<String, List<String>>();
    SharedPreferences pref;

    SharedPreferences preffid;

    Toolbar mToolbar;
    //  RecyclerView mRecyclerView;
    List<FlowerData> mFlowerList;
    FlowerData mFlowerData;
    ImageView log_off;

    AlertDialog.Builder builder;
    TextView username;
    TextView  total;
    ImageView home;
    ImageView password;
    ImageView attendance;
    ImageView  expand;
    Subscription subscription;
    ProgressDialog pd;


    private String Token_App ="";

    TextView text_counter;
    FrameLayout frameLayout;

    boolean  expandval = false;
    java.util.ArrayList<GetLeaveCompoffCount.Response> cntleaves = new ArrayList<GetLeaveCompoffCount.Response>();
    java.util.ArrayList<AttendanceStatusCurrentDay.Response> arraylistattendance = new ArrayList<AttendanceStatusCurrentDay.Response>();
    int cnt = 0;
    int cnt_leave = 0;
    int cnt_copmoff = 0;
    int cnt_total = 0;

    String empid = "" ;

    NotificationModel notification;

    DatabaseHelper databaseHelper = null;

    String regIds;

    int dataitems = 0;
    private Observable<ArrayList<AttendanceStatusCurrentDay.Response>> observablemarkattendnaceday;

    private Observable<ArrayList<InsertUpdateDeviceTokenId.Response>> observabletoken;
    private Observable<ArrayList<GetLeaveCompoffCount.Response>> observable_count;
    ImageView  bell;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile_dash_board);
            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            mToolbar = findViewById(R.id.budget_app_bar);
            mToolbar.setTitle(getResources().getString(R.string.app_name));
            text_counter = findViewById(R.id.cart_badge);
            frameLayout = findViewById(R.id.frame_click);



            //mRecyclerView = findViewById(R.id.my_recycler_view);
            log_off = findViewById(R.id.id_logoff);
            bell = findViewById(R.id.id_bell);
            username = findViewById(R.id.name);

            total = findViewById(R.id.leaves_total);


             databaseHelper = new DatabaseHelper(this);


             dataitems = databaseHelper.getAppNotificationDetail().size();

           // android.widget.Toast.makeText(ProfileDashBoardActivity.this, "token is " + dataitems, android.widget.Toast.LENGTH_LONG).show();



            if(dataitems > 0)
            {
                text_counter.setVisibility(View.VISIBLE);
                text_counter.setText(""+dataitems);

            }else
            {
                text_counter.setVisibility(View.INVISIBLE);

            }





            if (getIntent() != null) {

                notification = getIntent().getParcelableExtra(Constants.INTENT_TAG_DATA);

                if (notification != null) {


                        handleNotificationAction(notification);



                }

            }


            try {
                preffid = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);

                regIds = preffid.getString("regId", null);
                Log.e("ProfileDashBoard", "Firebase reg id: " + regIds);
                if (regIds == null) {
                    registerfirebase();
                } else {
                }





           //     Log.e("ProfileDashBoard", "TOKEN IS " + FirebaseInstanceId.getInstance().getToken());




            } catch (Exception e) {

            }




           // builder  = new AlertDialog.Builder(ProfileDashBoardActivity.this,R.style.MyDialogTheme);


     //       pd = ProgressDialog.show(ProfileDashBoardActivity.this,"","Please wait..",false,false);
       //     pd = new ProgressDialog(ProfileDashBoardActivity.this,R.style.AppCompatAlertDialogStyle);
            pd.setTitle("Please Wait..");

            pd.show();

            pref = getApplicationContext().getSharedPreferences("Attendance", 0);


            empid =  ""+pref.getInt("EmployeeId",0);


            Token_App = FirebaseMessaging.getInstance().getToken().toString();



            Log.d("Token_App is ", "onCreate 55: "+Token_App);


        //    saveToken(FirebaseInstanceId.getInstance().getToken());
            saveToken(Token_App);

            cnt = getAttendanceCount(-1);




            Typeface face = Typeface.createFromAsset(getAssets(),
                    "fonts/roboto_condensed_bold.ttf");
            username.setTypeface(face);
            total.setTypeface(face);

            username.setText("Welcome:  "+pref.getString("Name",null));
            total.setText("Emp. Id: "+pref.getInt("EmployeeId",0));






           /* expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                @Override
                public void onGroupExpand(int groupPosition) {
               *//*    Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Expanded."+groupPosition,
                            Toast.LENGTH_SHORT).show();*//*



                    if(expandableListTitle.get(groupPosition).equals("Daily Attendance"))
                    {

                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, MapActivity.class);
                        startActivityForResult(intent,0);


                    }

                }
            });
*/

            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, NotificationActivity.class);
                    startActivityForResult(intent,8);
                }
            });







            final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout_collaps);
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = true;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        collapsingToolbarLayout.setTitle("Netsurf Attendance");

                        mToolbar.setVisibility(View.VISIBLE);
                        //image.setVisibility(View.INVISIBLE);
                        isShow = true;
                    } else if(isShow) {
                        mToolbar.setVisibility(View.INVISIBLE);
                        // spinner_regions.setVisibility(View.VISIBLE);
                        //image.setVisibility(View.VISIBLE);
                        collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                        isShow = false;
                    }
                }
            });


            log_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Uncomment the below code to Set the message and title from the strings.xml file


                    builder.setTitle("Logout!");

                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to Logout this application ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    pref.edit().clear().commit();

                                    DatabaseHelper databaseHelperdel = new DatabaseHelper(getApplicationContext());
                                    databaseHelperdel.deleteAllNotification();


                                    android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();




                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();

                    //Setting the title manually
                    alert.setTitle("Logout!");
                    alert.show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                 /*   Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();*/
/*

                    if(expandableListTitle.get(groupPosition).trim().toString().equalsIgnoreCase("Daily Attendance"));
                    {


                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, MapActivity.class);
                        startActivity(intent);

                    }
*/




                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {


               /*     Toast.makeText(
                            getApplicationContext(),
                            expandableListTitle.get(groupPosition)
                                    + " -> "
                                    + expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT
                    ).show();

*/

                    String val = ""+expandableListDetail.get(
                            expandableListTitle.get(groupPosition)).get(
                            childPosition).trim().toString();


                    if(val.equalsIgnoreCase("Apply For Leave"))
                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Apply Leave");
                        intent.putExtra("status","one");
                       // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                        startActivityForResult(intent,1);


                    }else if(val.equalsIgnoreCase("Leave Status"))
                    {
                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Leave Status");
                        intent.putExtra("status","two");

                      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);


                        startActivityForResult(intent,1);

                    }else if(val.equalsIgnoreCase("Pending Leaves for Approval"))
                    {
                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Approved Leaves");
                        intent.putExtra("status","three");
                     //   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,1);




                    }else if(val.equalsIgnoreCase("Apply For Comp off"))

                    {
                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Apply Comp Leave");
                        intent.putExtra("status","four");
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,2);


                    }else  if(val.equalsIgnoreCase("Comp off Status"))
                    {

                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Leave Status");
                        intent.putExtra("status","five");
                  //      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,2);


                    }else  if(val.equalsIgnoreCase("Pending Comp-Off for Approval"))

                    {


                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Comp Off Approved Leaves");
                        intent.putExtra("status","six");
                      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,2);


                    }else  if(val.equalsIgnoreCase("Daily Task"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Daily Task");
                        intent.putExtra("status","seven");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,3);




               }else  if(val.equalsIgnoreCase("Team Report"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Team Report");
                        intent.putExtra("status","10");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,3);




               }else  if(val.equalsIgnoreCase("Health / Accident policy"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Health / Accident policy");
                        intent.putExtra("status","Eight");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,4);


                    }
                  else  if(val.equalsIgnoreCase("Company Policy"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Company Policy");
                        intent.putExtra("status","Nine");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,4);


                    }      else  if(val.equalsIgnoreCase("Pay Sleep Search"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Pay Sleep Search");
                        intent.putExtra("status","11");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,5);


                    }

 else  if(val.equalsIgnoreCase("Holiday List"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Holiday List");
                        intent.putExtra("status","12");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,6);


                    }
 else  if(val.equalsIgnoreCase("Share Your Ideas"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Share Your Ideas");
                        intent.putExtra("status","13");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,7);


                    }
 else  if(val.equalsIgnoreCase("Change your Password"))

                    {



                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                        intent.putExtra("Toolbar","Change your Password");
                        intent.putExtra("status","14");
                        //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivityForResult(intent,9);


                    }else if(val.equalsIgnoreCase("Mark Attendance"))
                    {
                        android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, MapActivity.class);
                        startActivityForResult(intent,0);

                    }



                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                 " tested failed",
                                Toast.LENGTH_SHORT).show();

                    }





                    return false;
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        /*
            Toast.makeText(getApplicationContext(),
                      " List Expanded.",
                    Toast.LENGTH_SHORT).show();*/


        if(requestCode == 1)
        {

            getAttendanceCount(0);


            }else    if(requestCode == 2)
        {

            getAttendanceCount(1);



        }else    if(requestCode == 3)
        {

            getAttendanceCount(6);



        }else    if(requestCode == 4)
        {

            getAttendanceCount(7);



        }else    if(requestCode == 5)
        {

            getAttendanceCount(5);



           }else    if(requestCode == 6) {

            getAttendanceCount(2);


        }  else    if(requestCode == 9)
        {

            getAttendanceCount(3);



           }else    if(requestCode == 7)
        {

            getAttendanceCount(4);



        }else  if(requestCode == 0)

        {
            getAttendanceCount(-1);

        }else if(requestCode == 8)
        {


         int   totalcnt = databaseHelper.getAppNotificationDetail().size();

            if(totalcnt > 0)
            {
                text_counter.setVisibility(View.VISIBLE);
                text_counter.setText(""+totalcnt);

            }else
            {
                text_counter.setVisibility(View.INVISIBLE);

            }
        }
    }



    public static String returnMeFCMtoken() {
        final String[] token = {""};
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isComplete()){
                    token[0] = task.getResult();
                    Log.e("Token_App", "onComplete: new Token got: "+token[0] );

                }
            }
        });
        return token[0];
    }

    @Override
    protected void onResume() {
        super.onResume();

       // expandableListView.setAdapter(expandableListAdapter);
   //

    }

    private void getLeavesCount(final int clickid) {



        UsApiInterface netsurfApiInterface = NetsurfApiClient.getApiClient(this, true);

        //  progressWheel.setVisibility(android.view.View.VISIBLE);
        if (netsurfApiInterface != null) {
            GetLeaveCompoffCount.Request logReq = new GetLeaveCompoffCount.Request();
            logReq.setEmp_Id(""+pref.getInt("EmployeeId",0));

            observable_count = netsurfApiInterface.getLeaveCount(logReq);
            subscription = observable_count.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<GetLeaveCompoffCount.Response>>() {

                        @Override
                        public void onCompleted() {

                            pd.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
                            //     progressWheel.setVisibility(android.view.View.GONE);
                            try {
                                android.widget.Toast.makeText(ProfileDashBoardActivity.this, "Failed " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                            } catch (NullPointerException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            } catch (IllegalStateException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            }
                        }

                        @Override
                        public void onNext(java.util.ArrayList<GetLeaveCompoffCount.Response>
                                                   respons) {


                            //   progressWheel.setVisibility(android.view.View.GONE);

                            try {
                                pd.dismiss();

                                cntleaves.clear();
                                cntleaves.addAll(respons);

                                cnt_leave = respons.get(0).getLeaveCount();
                                cnt_copmoff = respons.get(0).getCompoffCount();
                            //    cnt_total = respons.get(0).getIsLeaveApprover();


                                if (cnt_total == 0) {
                                    expandableListDetail = ExpandableListDataPump.getDataapprove();

                                } else {
                                    expandableListDetail = ExpandableListDataPump.getData();
                                }
                                pref.edit().putInt("SET_ATTENDANCE",arraylistattendance.get(0).getIsAttendanceDone()).apply();


                                expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());

                                expandableListAdapter = new CustomExpandableListAdapter(ProfileDashBoardActivity.this, expandableListTitle, expandableListDetail, cntleaves, arraylistattendance);
                                expandableListView.setAdapter(expandableListAdapter);

                                expandableListView.expandGroup(clickid);


                            }catch (Exception e)
                            {


                            }


                        }
                    });

        } else {
            //  android.widget.Toast.makeText(AdvertiseActivity.this, "web url is", android.widget.Toast.LENGTH_LONG).show();
        }
    }





    private void registerfirebase() {



        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    //  FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    String message = intent.getStringExtra("message");


                }
            }
        };

        displayFirebaseRegId();

    }




    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        Log.e("ProfileDashboard", "Firebase reg id: " + regId);
    }

    private int getAttendanceCount(final int clickcode) {



        UsApiInterface netsurfApiInterface = NetsurfApiClient.getApiClient(this, true);

        //  progressWheel.setVisibility(android.view.View.VISIBLE);
        if (netsurfApiInterface != null) {
            AttendanceStatusCurrentDay.Request logReq = new AttendanceStatusCurrentDay.Request();
            logReq.setEmployeeID(""+pref.getInt("EmployeeId",0));



            //  logReq.setRoleID(9);
            observablemarkattendnaceday = netsurfApiInterface.CountAttendance(logReq);
            subscription = observablemarkattendnaceday.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<AttendanceStatusCurrentDay.Response>>() {

                        @Override
                        public void onCompleted() {

                            pd.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
                            //     progressWheel.setVisibility(android.view.View.GONE);
                            try {
                                android.widget.Toast.makeText(ProfileDashBoardActivity.this, "Failed " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                            } catch (NullPointerException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            } catch (IllegalStateException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            }
                        }

                        @Override
                        public void onNext(java.util.ArrayList<AttendanceStatusCurrentDay.Response>
                                                   respons) {


                            //   progressWheel.setVisibility(android.view.View.GONE);
                            pd.dismiss();

                            arraylistattendance.clear();
                            arraylistattendance.addAll(respons);

                            getLeavesCount(clickcode);


                            cnt = respons.get(0).getIsAttendanceDone();




                         /*   if (respons.get(0).getIsAttendanceDone() > 0)
                            {

                                android.widget.Toast.makeText(ProfileDashBoardActivity.this, " "+respons.get(0).getIsAttendanceDone(), android.widget.Toast.LENGTH_LONG).show();


                            } else {
                                android.widget.Toast.makeText(ProfileDashBoardActivity.this, ""+respons.get(0).getIsAttendanceDone() , android.widget.Toast.LENGTH_LONG).show();

                            }*/



                        }
                    });

        } else {
            //  android.widget.Toast.makeText(AdvertiseActivity.this, "web url is", android.widget.Toast.LENGTH_LONG).show();
        }
        return cnt;
    }

    private void handleNotificationAction(NotificationModel notificationModel) {


            if(notificationModel.getParam1().isEmpty())
            {

                final NetsurfDialog dialog = new NetsurfDialog(ProfileDashBoardActivity.this,
                        ""+notificationModel.getTitle(),
                        notificationModel.getAlert(),
                        "Ok",
                        "Cancel");
                dialog.setCancelable(false);

                dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                       {

                        dialog.dismiss();

                        }
                });

                dialog.show();

            }

            else
            {
                android.content.Intent intent = new android.content.Intent(ProfileDashBoardActivity.this, LandingPageActivity.class);
                intent.putExtra("Toolbar","Approved Leaves");
                intent.putExtra("status","-1");
                intent.putExtra("Param",""+notificationModel.getParam1());
                startActivityForResult(intent,-1);
            }

    }

    private int saveToken(final String tokenid) {



        UsApiInterface netsurfApiInterface = NetsurfApiClient.getApiClient(this, true);

        //  progressWheel.setVisibility(android.view.View.VISIBLE);
        if (netsurfApiInterface != null) {
            InsertUpdateDeviceTokenId.Request tokenReq = new InsertUpdateDeviceTokenId.Request();
            tokenReq.setEmployeeId(""+pref.getInt("EmployeeId",0));
            tokenReq.setDeviceTokenId("android_"+tokenid);

            //  logReq.setRoleID(9);
            observabletoken = netsurfApiInterface.insertToken(tokenReq);
            subscription = observabletoken.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<InsertUpdateDeviceTokenId.Response>>() {

                        @Override
                        public void onCompleted() {

                            pd.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
                            //     progressWheel.setVisibility(android.view.View.GONE);

                         //   9623441789
                            try {
                                android.widget.Toast.makeText(ProfileDashBoardActivity.this, "Failed " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                            } catch (NullPointerException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            } catch (IllegalStateException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            }
                        }

                        @Override
                        public void onNext(java.util.ArrayList<InsertUpdateDeviceTokenId.Response>
                                                   respons) {


                            //   progressWheel.setVisibility(android.view.View.GONE);

                            FirebaseMessaging.getInstance().subscribeToTopic("All");

                      //   android.widget.Toast.makeText(ProfileDashBoardActivity.this, "Response "+respons.get(0).getDeviceTokenId(), android.widget.Toast.LENGTH_LONG).show();


                            pd.dismiss();





                        }
                    });

        } else {
        }
        return cnt;
    }

    }
