package attendance.netsurf.netsurfattendance;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import attendance.netsurf.netsurfattendance.models.DataModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import timber.log.Timber;

public class DrawerActvity extends AppCompatActivity implements HomeFragment.FragmentPositionListener
        {

private static final String TAG = DrawerActvity.class.getName();

DrawerLayout drawerLayout;
ListView listView;
@BindView(R.id.name)
TextView name_user;
@BindView(R.id.emailid)
TextView email_user;
/*@InjectView(R.id.txt_version_name)
TextView version_name;*/
SharedPreferences.Editor editor;
private DrawerListAdapter drawerListAdapter;
private ArrayList<String> titlesArrayList = new ArrayList<>();
private ArrayList<Integer> imageIdsArrayList = new ArrayList<>();
private ArrayList<Integer> colorIdsArrayList = new ArrayList<>();
private ArrayList<DataModel> drawerModels = new ArrayList<>();
private int currentFragmentPosition = Constants.HOME;
private Fragment currentFragment;
private HomeFragment homeFragment =  null;
/*private ReceiptDiscountFragment receiptDiscountFragment =  null;
private ScanFragment scanFragment =  null;
private BoxQrFragment boxQrFragment =  null;
private LocationFragment locationFragment =  null;*/



private SharedPreferences pref;
private String regIds;
private String versionName;
private Toolbar toolbar;
private ActionBarDrawerToggle drawerToggle;
Subscription subscription;
int ids = 1;
private List<String> list;
private String text_to_details;
private Subscription timerSubscribption = null;
private Subscription autoRefreshSubscribption = null;
public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

String lastKnownLocation;
private String versionStr;
private String update;
private String version;
private String dbupdatedate;
private int db_result;
private String strDatefile = "";
private String db_date;
private String db_msg;
private String webUrl = "http://cdr.netsurf.co.in/mobile/NetsurfLiveCellTrack.apk";


 private Menu mainitemmenu;
 public static final String MyPREFERENCES = "MyPrefs" ;



            public static final String iResult = "iResult";
            public static final String sValidationKey = "sValidationKey";
            public static final String sResult = "sResult";
            public static final String RegistrationDate = "RegistrationDate";
            public static final String iReportLevelId = "iReportLevelId";
            public static final String sKeyType = "sKeyType";
            public static final String KeyFinalExpDate = "KeyFinalExpDate";
            public static final String Customerid = "custid";
            public static final String Registrationkey = "regkey";

            public static final String USERNAME = "username";
            public static final String MOBILE = "mobile";
            public static final String EMAIL = "email";
            public static final String STATION = "station";
            public static final String DATEJOIN = "date";
            public static final String VERSION = "VERSION";

            final static String TARGET_BASE_PATH = "/sdcard/NetsurfLiveCellTrack/TowerCellAddresses_V2/";

            final static String TARGET_ASSSET_PATH = "/sdcard/NetsurfLiveCellTrack/TowerCellAddresses_V2/MeatDB";
            SharedPreferences sharedpreferences;
            String regist_key;


            String Officename = "";
            String UserNmae = "";

            public static final String Latitude = "Latitude";
            public static final String Longitude = "Longitude";
            public static final String OfficeId = "OfficeId";
            public static final String OfficeName = "OfficeName";
            public static final String OfficeStoreId = "OfficeStoreId";
            public static final String UserId = "UserId" ;
            public static final String UserName = "UserName";
            public static final String Passward = "Passward";
            public static final String UserLogIn = "UserLogIn";
            public static final String GSTStartDate = "GSTStartDate";
            public static final String RolId = "RolId";
            public static final String Approved = "Approved";
            public static final String IsPaidProcessinprogress = "IsPaidProcessinprogress";
            public static final String MinDeposit = "MinDeposit";





            @Override
protected void onResume() {
        super.onResume();
}

@Override
protected void onPause() {
        super.onPause();
        }

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Timber.d(TAG + " onCreate");
        ButterKnife.bind(this);
        Intent in = getIntent();
        Uri data = in.getData();
    try {


       /* sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Officename = sharedpreferences.getString(OfficeName, null);

        UserNmae = sharedpreferences.getString(UserName, null);

        if(Officename.isEmpty())
        {
            name_user.setText(""+Officename);
        }else
        {


        }


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);*/
        Configuration config = getBaseContext().getResources().getConfiguration();
        Intent i_video = getIntent();
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        list = (Arrays.asList(getApplicationContext().getResources().getStringArray(R.array.nav_drawer_items)));
        titlesArrayList.addAll(list);


        getIds();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        int fragmentPosition = getIntent().getIntExtra("position", Constants.HOME);
        if (fragmentPosition != Constants.HOME) {
            toolbar.setTitle("" + titlesArrayList.get(fragmentPosition));
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initDrawer();
       // initUi();

        name_user.setText("Sagar Kardak");
        email_user.setText("sagar.kardak@netsurfnetwork.com");


    }catch (Exception e)
    {

    }

        }



    @Override
    protected void onDestroy() {
           super.onDestroy();


    }



    @Override
    public void onBackPressed() {

        try {


        } catch (Exception e) {

        }
    }


    private void initDrawer() {

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                hideKeyBoard();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyBoard();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    private void hideKeyBoard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(toolbar.getWindowToken(), 0);
        } catch (Exception ignore) {

        }
    }

   /* private void initUi() {

        //todo change it according to selected item position


            drawerListAdapter = new DrawerListAdapter(DrawerActvity.this, R.layout.layout_drawer_list, drawerModels);


        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);



        } catch (PackageManager.NameNotFoundException ex) {} catch(Exception e){}


        listView.setAdapter(drawerListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (int i = 0; i < drawerModels.size(); i++) {
                    drawerModels.get(i).setSelected(false);
                }
                drawerModels.get(position).setSelected(true);
                drawerListAdapter.notifyDataSetChanged();

                setFragment(position);
                drawerLayout.closeDrawer(Gravity.LEFT);

            }
        });

        Intent data = getIntent();
        if (data != null) {
            int fragmentPosition = data.getIntExtra("position", Constants.HOME);

            if (fragmentPosition != Constants.HOME) {
                Timber.d("fragment position is %s ", "" + fragmentPosition);
                for (int i = 0; i < drawerModels.size(); i++) {
                    drawerModels.get(i).setSelected(false);
                }
                drawerModels.get(fragmentPosition).setSelected(true);
                drawerListAdapter.notifyDataSetChanged();
                setFragment(fragmentPosition);
            } else {
                setFragment(Constants.HOME);
            }

        } else {
            Timber.d(" intent data is null ", " setting club netsurf fragment ");
            setFragment(Constants.HOME);
        }

    }
*/


    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    private void getIds() {

        imageIdsArrayList.add(R.drawable.ic_arrow_right);
        imageIdsArrayList.add(R.drawable.ic_arrow_right);//locate_sp
        imageIdsArrayList.add(R.drawable.ic_arrow_right);
        imageIdsArrayList.add(R.drawable.ic_arrow_right);
        //imageIdsArrayList.add(R.drawable.ic_my_region);
       // imageIdsArrayList.add(R.drawable.ic_my_region);
      //  imageIdsArrayList.add(R.drawable.ic_my_region);
     //   imageIdsArrayList.add(R.drawable.ic_my_region);  //shop online*/


        colorIdsArrayList.add(R.color.cl_vio);
        colorIdsArrayList.add(R.color.cl_ind);
        colorIdsArrayList.add(R.color.cl_blu);
        colorIdsArrayList.add(R.color.cl_gre);

        // colorIdsArrayList.add(R.color.cl_gre);
     //   colorIdsArrayList.add(R.color.cl_ylo);
       // colorIdsArrayList.add(R.color.cl_ora);
       // colorIdsArrayList.add(R.color.cl_red);



        int startPosition = 0;


        for (int i = startPosition; i < imageIdsArrayList.size(); i++) {
            DataModel drawerModel = new DataModel();
            drawerModel.setColorId(colorIdsArrayList.get(i));
            drawerModel.setImageId(imageIdsArrayList.get(i));
            drawerModel.setTitle(titlesArrayList.get(i));
            drawerModel.setSelected(false);
            drawerModels.add(drawerModel);
        }

    }


    public void setFragment(int position) {

        boolean isSameFragment;
        if (currentFragmentPosition == position) {
            isSameFragment = true;
        } else {
            isSameFragment = false;
        }




        switch (position) {


            case Constants.HOME:
                homeFragment = new HomeFragment();
                currentFragment = homeFragment;

             /*   android.content.Intent startLogin = new android.content.Intent(DrawerActvity.this, MapActivity.class);
                startActivity(startLogin);*/
                //return;

            break;

                case Constants.DAILY:


               android.content.Intent startLogin = new android.content.Intent(DrawerActvity.this, MapActivity.class);
                startActivity(startLogin);
                    return;



            case Constants.PRODUCTBOX:


              break;


            case Constants.LOGOUT:
                try {



                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                return;


            default:
                homeFragment = new HomeFragment();
                currentFragment = homeFragment;

                break;

        }

        currentFragmentPosition = position;

            toolbar.setVisibility(View.VISIBLE);

            setSupportActionBar(toolbar);
            initDrawer();
            drawerToggle.syncState();


        Timber.d("before setting fragment there are total %s fragments in backstack ", "" +
                getSupportFragmentManager().getBackStackEntryCount());

        if (position != Constants.HOME) {

            if (!isSameFragment) {
                toolbar.setTitle("" + titlesArrayList.get(position));
                toolbar.setBackgroundColor(getResources().getColor(R.color.accent));

                FragmentTransaction fragmentTransaction;

                if (getSupportFragmentManager().getBackStackEntryCount() == 1) {

                    Timber.d("adding fragment in back stack ");
                    fragmentTransaction = getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.container, currentFragment, String.valueOf(position))
                            .addToBackStack(null);

                    fragmentTransaction.commit();
                } else {
                    Timber.d("replacing fragment in back stack ");
                    try {
                        getSupportFragmentManager().popBackStack();
                        fragmentTransaction = getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, currentFragment, String.valueOf(position))
                                .addToBackStack(null);
                        fragmentTransaction.commit();
                    } catch (Exception e) {

                    }
                }

            } else {
                Timber.d("same fragment detected for position %s ", "" + position);
            }

        } else {

            FragmentTransaction fragmentTransaction
                    = getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, currentFragment, String.valueOf(position))
                    .addToBackStack(null);

            fragmentTransaction.commit();
        }

        Timber.d("after setting fragment there are total %s fragments in backstack ",
                "" + getSupportFragmentManager().getBackStackEntryCount());

    }



    @Override
    public void onClickFragment(int fragmentPosition) {
        try {
            //perform list item click programatically.
            listView.performItemClick(
                    listView.getAdapter().getView(fragmentPosition, null, null),
                    fragmentPosition,
                    listView.getAdapter().getItemId(fragmentPosition));

        } catch (Exception ex) {
            Timber.d("error while selecting fragment from onClickFragment() position was %s ", ""
                    + fragmentPosition);
        }
    }





public interface ConnectivityStatusListener {
    void onConnectivityStatusChange(boolean isConnected);
}

    public Toolbar getToolbar() {
        return toolbar;
    }



/*




            public void logoutacts()
            {

                LayoutInflater li = LayoutInflater.from(DrawerActvity.this);
                View promptsView = li.inflate(R.layout.layout_change_mobile, null);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(DrawerActvity.this,R.style.MyDialogTheme);
                builder1.setView(promptsView);

                final EditText edittext = (EditText) promptsView.findViewById(R.id.edit_name);

                final TextView old_pass = (TextView) promptsView.findViewById(R.id.textView_logout);
                old_pass.setTextColor(getResources().getColor(R.color.gray));

                old_pass.setVisibility(View.VISIBLE);
                edittext.setVisibility(View.GONE);

                builder1.setMessage("Logout!");

                builder1.setCancelable(true);
                builder1.setPositiveButton("Logout",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id) {

                                editor.clear();
                                editor.commit();

                                Intent intentReg = new Intent(DrawerActvity.this, RegisterActivity.class);
                                startActivity(intentReg);
                                finish();


                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                dialog.dismiss();

                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }


*/

/*
            private boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        int call = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        int SMS = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        int Wcalendar = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR);
        int Rcalendar = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        //   int contacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (storage1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (call != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (SMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }
        if (contacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (Wcalendar != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALENDAR);
        }
        if (Rcalendar != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALENDAR);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }*/
}
