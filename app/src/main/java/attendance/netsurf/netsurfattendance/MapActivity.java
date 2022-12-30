package attendance.netsurf.netsurfattendance;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import attendance.netsurf.netsurfattendance.models.MarkAttendance;
import attendance.netsurf.netsurfattendance.services.GPSTracker;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener  {


/*
    @BindView(R.id.toolbar)
    Toolbar toolbar;*/

    @BindView(R.id.text_date_time)
    TextView time_date;

    @BindView(R.id.button_present)
    Button btn_present;

    @BindView(R.id.button_absent)
    Button btn_absent;



    @BindView(R.id.txt_wait)
    TextView txt_wait;




    @BindView(R.id.edt_location)
    EditText edit_location;

    @BindView(R.id.edt_task)
    EditText edit_task;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.imageView_start_date)
    TextView img_edit_date;

    @BindView(R.id.relative_work_task)
    LinearLayout lin_set_attendnaNCE;

    @BindView(R.id.already_set_txt)
    TextView txt_set_attendance;



    String latStr = "";
    String longStr = "";
    String address ;
    String city;

    String originaladd;

    double latd ;
    double longd ;




    private SupportMapFragment mapFragment;
    GoogleMap mGoogleMap;
    Subscription subscription;

    Handler handler;

    SharedPreferences pref;

    SharedPreferences sharedpreferences;

    public static final String mypreference = "Attendance";

    private String lat = "";
    private String longs ="";
    private String range ="";
    private String  pname ="";
    private int empid  = 0;
    Intent gpsintent;
    Intent gpsintentnew;
    private boolean isbroadcastReceiverRegistered = false;

    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String EXTRA_PINCODE = "extra_pincode";
    private static final String GOOGLE_BASE_URL = "http://maps.googleapis" +
            ".com/maps/api/geocode/";
    private double selectedLatitude, selectedLongitude;
    private String selectedPincode;
    ProgressDialog pd;

    private Observable<ArrayList<MarkAttendance.Response>> observablemarkattendnace;


    MaterialCalendarView widget;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");


    public static String select_date_val = "";
    /*

        @BindView(R.id.viewPager)
        ViewPager viewPager;
    */
    public static int current_day;
    public static int current_mon;
    public static int current_year;

    private static String selectedD = "";
    private static String selectedA = "";

    AlertDialog.Builder builder = null;

    int  ISset_Attendnace = 0;


    private   static TextView txt_daily_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_all_mapp);

        ButterKnife.bind(this);
        setUpToolBar();

     //   builder  = new AlertDialog.Builder(MapActivity.this,R.style.MyDialogTheme);


        txt_daily_date= (TextView)findViewById(R.id.textView_selected_date) ;


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        empid = sharedpreferences.getInt("EmployeeId",0);

        ISset_Attendnace  = sharedpreferences.getInt("SET_ATTENDANCE",0);

        if(ISset_Attendnace == 0)
        {
            txt_set_attendance.setVisibility(View.GONE);
            lin_set_attendnaNCE.setVisibility(View.VISIBLE);
        }else
        {
            txt_set_attendance.setVisibility(View.VISIBLE);
            lin_set_attendnaNCE.setVisibility(View.GONE);
        }

       // checkAndRequestPermissions();
        initializeMap();
        selectedLatitude = -1;
        selectedLongitude = -1;
        selectedPincode = getIntent().getStringExtra(EXTRA_PINCODE);
        setUpMapIfNeeded();



        DateFormat df = new SimpleDateFormat("hh:mm:a"); //format time
        String time = df.format(Calendar.getInstance().getTime());

        DateFormat df1=new SimpleDateFormat("dd MMM yyyy");//foramt date
        String date=df1.format(Calendar.getInstance().getTime());

        time_date.setText(date+"  "+time);

        txt_daily_date.setText(date);


        gpsintent = new Intent(MapActivity.this, GPSTracker.class);
        GPSTracker.MIN_TIME = 5;

        StartGPSService();

        /*
*/

        img_edit_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedD = "";
                select_date_val = "";
                new SimpleCalendarDialogFragment().show(getSupportFragmentManager(), "test-simple-calendar");

            }
        });

        btn_present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd = ProgressDialog.show(MapActivity.this,"","Please wait..",false,false);
                pd.show();

                if(edit_task.getText().toString().equals("") )
                {
                    pd.dismiss();

                    Toast.makeText(getApplicationContext(), "Specify Your Work", Toast.LENGTH_LONG).show();



                }/*else if(edit_location.getText().toString().equals("")) {

                    pd.dismiss();

                    Toast.makeText(getApplicationContext(), "Kindly Enable Your GPS or Add Your Location & Try Again", Toast.LENGTH_LONG).show();


                }else if( latStr == null || longStr == null)
                {
                  //  setAttendance("","",edit_location.getText().toString(),"1",empid,edit_task.getText().toString(),originaladd);
                    setAttendance("","","","1",empid,edit_task.getText().toString(),"");

                }*/
                else
                {

                    //Uncomment the below code to Set the message and title from the strings.xml file
                    builder.setTitle("Absent/Present!");

                    //Setting message manually and performing action on button click
                    builder.setMessage("Are you sure want to Present today ?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                //    setAttendance(latStr,longStr,edit_location.getText().toString(),"1",empid,edit_task.getText().toString(),originaladd);
                                    setAttendance("","","","1",empid,edit_task.getText().toString(),"");




                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    pd.dismiss();
                                    dialog.cancel();

                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();

                    //Setting the title manually
                    alert.setTitle("Absent/Present!");
                    alert.show();
                }


            }
        });

        btn_absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = ProgressDialog.show(MapActivity.this,"","Please wait..",false,false);
                pd.show();


                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setTitle("Absent/Present!");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to Absent today ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                             //  setAttendance(latStr,longStr,edit_location.getText().toString(),"0",empid,edit_task.getText().toString(),originaladd);
                              //   setAttendance("","","","0",empid,edit_task.getText().toString(),"");

                                pd.dismiss();

                                android.content.Intent intent = new android.content.Intent(MapActivity.this, LandingPageActivity.class);
                                intent.putExtra("Toolbar","Apply Leave");
                                intent.putExtra("status","one");

                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                pd.dismiss();
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();

                //Setting the title manually
                alert.setTitle("Absent/Present!");
                alert.show();



            }
        });




    }


  /*  protected Marker createMarker(double latitude, double longitude, String title, String snippet, int iconResID) {

        return mGoogleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(R.drawable.netsurf_logo);
    }
*/



    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent gpsintent) {

            gpsintentnew = gpsintent;
             latStr = gpsintent.getStringExtra("lat");
            longStr = gpsintent.getStringExtra("long");
           String accuracy = gpsintent.getStringExtra("accuracy");

            latd = Double.parseDouble(gpsintent.getStringExtra("lat"));
             longd = Double.parseDouble(gpsintent.getStringExtra("long"));

       //     createMarker(latd,longd,"Current Position","Netsurf Office",1);
            LatLng p2 = new LatLng(latd ,longd);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2, 15));

            Marker khadki = mGoogleMap.addMarker(new MarkerOptions()
                    .title("Current Position")
                    .snippet("")
                    .position(p2));



           // Toast.makeText(getApplicationContext(), "lat "+latStr, Toast.LENGTH_LONG).show();

            // float accuracy = location.getAccuracy();

           /* edit_enter_latitude.setText( accuracy+" "+latStr);
            edit__enter_longitude.setText(""+longStr);



            accuracy_txt.setText("Accuracy\n"+accuracy+" meters");

        */

            //StartCGIService();

        }
    };





    private void StartGPSService() {

        if (GPSTracker.canGetLocation == false) {

            startService(gpsintent);
        } else {

        }

        if (!isbroadcastReceiverRegistered) {

            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(GPSTracker.LOCUPDATE_ACTION));

            isbroadcastReceiverRegistered = true;
        }
    }

    public static class SimpleCalendarDialogFragment extends AppCompatDialogFragment implements OnDateSelectedListener {

        private TextView textView;


        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater inflater = getActivity().getLayoutInflater();

            //inflate custom layout and get views
            //pass null as parent view because will be in dialog layout
            View view = inflater.inflate(R.layout.dialog_basic, null);

            textView = view.findViewById(R.id.textView);

            MaterialCalendarView widget = view.findViewById(R.id.calendarView);

            Calendar calendar1 = Calendar.getInstance();
            Date today = calendar1.getTime();

            calendar1.add(Calendar.DAY_OF_YEAR, 2);

            Date tomorrow = calendar1.getTime();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String todayAsString = dateFormat.format(today);
            String tomorrowAsString = dateFormat.format(tomorrow);

            String[]  splitdate = tomorrowAsString.split("-");

            current_day = Integer.parseInt(splitdate[2])-5;
            current_mon = Integer.parseInt(splitdate[1]);
            current_year = Integer.parseInt(splitdate[0]);


            widget.addDecorator(new disbalepreviousdates());
            final LocalDate instance = LocalDate.now();
            widget.setSelectedDate(instance);
            widget.setOnDateChangedListener(this);

            return new android.app.AlertDialog.Builder(getActivity())
                    .setTitle("Choose Starting Date")
                    .setView(view)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }

        @Override
        public void onDateSelected(
                @NonNull MaterialCalendarView widget,
                @NonNull CalendarDay date,
                boolean selected) {


          //  selectdeselweekly =  date;

            selectedD = date.getYear()+"-"+date.getMonth()+"-"+date.getDay();
            textView.setText(FORMATTER.format(date.getDate()));

            select_date_val = FORMATTER.format(date.getDate());

            txt_daily_date.setVisibility(View.VISIBLE);


            txt_daily_date.setText("("+select_date_val+")");


            // textView.setText(selectedD);
        }
    }

    private  static  class  disbalepreviousdates implements DayViewDecorator
    {
        @Override
        public boolean shouldDecorate(CalendarDay day) {

            CalendarDay date =  CalendarDay.today();
            // CalendarDay date =  CalendarDay.from(current_year,current_mon,current_day);

            CalendarDay date1 =  CalendarDay.from(current_year,current_mon,current_day);



            Log.d("dates now ",current_year+"-"+current_mon+"-"+current_day);

            return (day.isBefore(date1))  ? true : false;
        }



        @Override
        public void decorate(DayViewFacade view) {

            view.setDaysDisabled(true);


        }
    }


/*

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            if (mMap != null) {
                computeCurrentLocation();
                mMap.setMyLocationEnabled(true);
                mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                    @Override
                    public void onCameraChange(CameraPosition cameraPosition) {
                        LatLng center = mMap.getCameraPosition().target;
                        changeCircularColor(center.latitude, center.longitude);
                    }
                });
                animateToThisPoint(18.5203, 73.8567);
                if (selectedPincode != null) {
                    Timber.d("selectedPincode : " + selectedPincode);

                }
            }
        }catch (SecurityException E)
        {}
    }*/

    private void setUpMapIfNeeded() {
        if (mGoogleMap == null) {
            try{
                ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                        .getMapAsync(this);
         /*   mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map).getMap());
            if (mMap != null) {
                computeCurrentLocation();
                mMap.setMyLocationEnabled(true);
                mMap.setOnCameraChangeListener(this);
                animateToThisPoint(18.5203, 73.8567);
                if (selectedPincode != null) {
                    Timber.d("selectedPincode : " + selectedPincode);
                    getSelectedPinCodeLocation(selectedPincode);
                }
            }*/
            }catch (SecurityException e)
            {

            }
        }
    }

    private void changeCircularColor(double latitude, double longitude) {
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(latitude, longitude))
                .radius(300)
                .strokeWidth(2)
                .strokeColor(Color.parseColor("#a1cdff"))
                .fillColor(Color.parseColor("#88a1cdff"));
        mGoogleMap.clear();
        mGoogleMap.addCircle(circleOptions);
        selectedLatitude = latitude;
        selectedLongitude = longitude;


        try {

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(selectedLatitude, selectedLongitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

             address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             city = addresses.get(0).getLocality();



            originaladd  = address + "," + city;

            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            edit_location.setText(address+","+city);
        }catch (Exception e)
        {


        }
    }


    private void computeCurrentLocation() {
        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context
                    .LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location location = null;
            for (int i = providers.size() - 1; i >= 0; i--) {
                location = locationManager.getLastKnownLocation(providers.get(i));
                if (location != null) break;
            }
            if (location != null) {
                animateToThisPoint(location.getLatitude(), location.getLongitude());
            }
        } catch (SecurityException ex) {
            Timber.e(Log.getStackTraceString(ex));
        }
    }

    private void animateToThisPoint(double latitude, double longitude) {
        LatLng start = new LatLng(latitude, longitude);
        selectedLatitude = latitude;
        selectedLongitude = longitude;
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        changeCircularColor(latitude, longitude);
    }

 /*   @Override
    public void onCameraChange(CameraPosition position) {
        LatLng center = mMap.getCameraPosition().target;
        changeCircularColor(center.latitude, center.longitude);
    }*/

   /* @OnClick(R.id.imageView_info_next)
    public void onSelectClicked() {
        Timber.d("Clicked");
        Intent intent = new Intent();
        intent.putExtra(EXTRA_LATITUDE, selectedLatitude);
        intent.putExtra(EXTRA_LONGITUDE, selectedLongitude);
        setResult(RESULT_OK, intent);
        finish();
    }*/



    @Override
    public void onMapReady(final GoogleMap map) {
        // DO WHATEVER YOU WANT WITH GOOGLEMAP
        mGoogleMap = map;
        // map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.se(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                LatLng center = map.getCameraPosition().target;
                changeCircularColor(center.latitude, center.longitude);
            }
        });
        animateToThisPoint(18.5681, 73.7717);
        if (selectedPincode != null) {
            Timber.d("selectedPincode : " + selectedPincode);

        }
        map.getUiSettings().setZoomControlsEnabled(true);

    }


    private void initializeMap() {
        if(mapFragment == null) {
            mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            handler = new Handler();
            final OnMapReadyCallback onMapReadyCallback = this;
            mapFragment.getMapAsync(this);
            txt_wait.setVisibility(View.GONE);

            handler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    mapFragment.getMapAsync(onMapReadyCallback);
                }
            }, getResources().getInteger(android.R.integer.config_longAnimTime));
        }
    }







    private void setUpToolBar() {

        toolbar.setTitle("    DAILY ATTENDANCE!");
        toolbar.setTitleTextColor(getResources().getColor(R.color.monsoon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }





    private void setAttendance(String latStr_new,String longStr_new,String location_new,String isworking,int empid,String task,String actualLoc) {



        UsApiInterface netsurfApiInterface = NetsurfApiClient.getApiClient(this, true);

        //  progressWheel.setVisibility(android.view.View.VISIBLE);
        if (netsurfApiInterface != null) {
            MarkAttendance.Request logReq = new MarkAttendance.Request();
            logReq.setActualMapLocation(actualLoc);
            logReq.setEmployeeID(""+empid);
         //   logReq.setIsWorking(isworking);
            logReq.setLatitude(latStr_new);
            logReq.setLongitude(longStr_new);
            logReq.setUserLocation(location_new);
            logReq.setUserRemarks(task);
            //  logReq.setRoleID(9);
            observablemarkattendnace = netsurfApiInterface.markAttendance(logReq);
            subscription = observablemarkattendnace.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<MarkAttendance.Response>>() {

                        @Override
                        public void onCompleted() {

                            pd.dismiss();

                        }

                        @Override
                        public void onError(Throwable e) {
                            pd.dismiss();
                            //     progressWheel.setVisibility(android.view.View.GONE);
                            try {
                                android.widget.Toast.makeText(MapActivity.this, "Failed " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                            } catch (NullPointerException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            } catch (IllegalStateException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            }
                        }

                        @Override
                        public void onNext(java.util.ArrayList<MarkAttendance.Response>
                                                   respons) {


                            //   progressWheel.setVisibility(android.view.View.GONE);

                            pd.dismiss();
                            if (respons.get(0).getRetu_Value() == 1)
                            {

                                android.widget.Toast.makeText(MapActivity.this, " "+respons.get(0).getRetu_message(), android.widget.Toast.LENGTH_LONG).show();


                            } else {
                                android.widget.Toast.makeText(MapActivity.this, ""+respons.get(0).getRetu_message() , android.widget.Toast.LENGTH_LONG).show();

                            }



                        }
                    });

        } else {
            //  android.widget.Toast.makeText(AdvertiseActivity.this, "web url is", android.widget.Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onMarkerClick (Marker marker) {

        // mGoogleMap.clear();
        //Toast.makeText(getApplicationContext(), "Id is :"+index,Toast.LENGTH_SHORT).show();
     /*   if(classObject != null)
        {

          //  Toast.makeText(getApplicationContext(), "Id is :",Toast.LENGTH_SHORT).show();

        }else {

            marker.showInfoWindow();
            MarkerOptions markerOptions = new MarkerOptions();
            int index = mMarkerMap.get(marker);


                try {
                    marker.showInfoWindow();

                    Paint p = new Paint();
                    p.setColor(Color.TRANSPARENT);
                    Rect rect = new Rect(0, 0, bm.getWidth(), bm.getHeight());

                    if (rect == null) {
                        RectF rectF = new RectF(rect);
                        c1.drawOval(rectF, p);
                        int color2 = getResources().getColor(R.color.abc_background_cache_hint_selector_material_light);
                        c1.drawColor(color2);
                        int color1 = getResources().getColor(R.color.opacity);
                        p.setColor(color1);
                        //  p.setColor(Color.RED);
                        p.setAntiAlias(true);
                        p.setStrokeWidth(5);
                    }

                    int azimuth = Integer.parseInt(ccgilist.get(index).getsAzimuth());
                    float beamwidth = Integer.parseInt(ccgilist.get(index).getsBeamWidth());

                    c1.drawArc(rectF, azimuth - 90, beamwidth, true, p);
                    // c1.drawArc(rectF, 90,180, true, p);
                    BitmapDescriptor bmD = BitmapDescriptorFactory.fromBitmap(bm);
                    p2 = new LatLng(ccgilist.get(index).getdLatitude(), ccgilist.get(index).getdLongitude());
                    //p0 = new LatLng(locationList.get(0).getLatitude(), locationList.get(0).getLongitude());
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p2, 13));
                    //         Toast.makeText(getActivity(), "Complete.." + locationList.get(position).getBeamWidth(), Toast.LENGTH_LONG).show();
                    mGoogleMap.addGroundOverlay(new GroundOverlayOptions().
                            image(bmD).
                            position(p2, radiusM * 2, radiusM * 2).
                            transparency(0.4f));




                } catch (Exception e) {



            }
        }*/

        return false;
    }


/*
    private  boolean checkAndRequestPermissions() {
        int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int storage = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int phonestate = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);

        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (phonestate != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_PHONE_STATE);
        }

      *//*  if (Wcalendar != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_CALENDAR);
        }
        if (Rcalendar != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_CALENDAR);
        }


        if (phone_camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
        }*//*

        if (!listPermissionsNeeded.isEmpty())
        {
            ActivityCompat.requestPermissions(MapActivity.this,listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]),1);
            return false;
        }

        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();



    }
}
