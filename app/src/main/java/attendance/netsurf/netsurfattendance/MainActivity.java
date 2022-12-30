package attendance.netsurf.netsurfattendance;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import attendance.netsurf.netsurfattendance.ui.LoginActivity;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;

    public static final String mypreference = "Attendance";
    public static final String Name = "Name";
    public static final String Email = "emailKey";
    private static final long SPLASH_LENGTH = 2200l;
    //  private static final long SPLASH_LENGTH = 2000;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private boolean isLoadingFinished = true;
    private boolean isTimerExpired = false;
    private boolean isData = false;

    AlertDialog.Builder builder;
    String namestr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        namestr = sharedpreferences.getString(Name,null);

     //   android.widget.Toast.makeText(MainActivity.this, " "+namestr, android.widget.Toast.LENGTH_LONG).show();


        try {

            if (isOnline()) {

                //do whatever you want to do

                if (namestr == null) {

               /*    List<UnCategorizedContactModel> data = UnCategorizedContactModel.getAllContacts();

                        if (data == null || data.size() == 0) {
                        isLoadingFinished = false;
                        new LoadContactsFromDevide(getApplicationContext()).execute();

                    }*/

               timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {

                            isTimerExpired = true;
                            if(isLoadingFinished) {
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    };
                    timer.schedule(timerTask, SPLASH_LENGTH);



                } else {


                    try {


                        timer = new Timer();
                        timerTask = new TimerTask() {
                            @Override
                            public void run() {

                                isTimerExpired = true;
                                if(isLoadingFinished) {
                                    Intent intent = new Intent(MainActivity.this, ProfileDashBoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        };
                        timer.schedule(timerTask, SPLASH_LENGTH);

                    }catch (Exception e)
                    {

                    }
                    //  isFetchedData();

                }
            } else {



               // builder  = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);



                //Setting message manually and performing action on button click
                builder.setMessage("No internet connection..")
                        .setCancelable(false)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                             //   MainActivity.this.reCreate();


                                finish();
                                startActivity(getIntent());



                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                finish();
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();

                //Setting the title manually
                alert.setTitle("Failed to connect?");
                alert.show();
            }
        }catch (Exception e)
        {

        }




    }


    public boolean isOnline() {
        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }
        return true;
    }
}
