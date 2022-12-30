package attendance.netsurf.netsurfattendance;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import attendance.netsurf.netsurfattendance.database.DatabaseHelper;
import attendance.netsurf.netsurfattendance.models.NotificationModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static attendance.netsurf.netsurfattendance.Constants.DATA_NOT_AVAILABLE_VIEW;
import static attendance.netsurf.netsurfattendance.Constants.DETAILS_VIEW;
import static attendance.netsurf.netsurfattendance.Constants.PROGRESS_VIEW;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UNReadNotificationAdapter mUNReadNotificationAdapter = null;

    private ArrayList<NotificationModel> mUnReadNotificationModels = new ArrayList<>();

    Intent intent;
    String nametoolbar;
    String status;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.name_title)
    TextView name_user;

    @BindView(R.id.emp_id)
    TextView emp_id;

    @BindView(R.id.fab_delete)
    FloatingActionButton fab_delete;


    AlertDialog.Builder builder;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setUpToolBar();
        intent = getIntent();

        nametoolbar = intent.getStringExtra("Toolbar");
        status = intent.getStringExtra("status");
        pref = getApplicationContext().getSharedPreferences("Attendance", 0);


        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/roboto_condensed_regular.ttf");
        name_user.setTypeface(face);
        emp_id.setTypeface(face);

        name_user.setText("Welcome: " + pref.getString("Name", null));
        emp_id.setText("Emp Id : " + pref.getInt("EmployeeId", 0));

     //   builder  = new AlertDialog.Builder(NotificationActivity.this,R.style.MyDialogTheme);
        recyclerView = findViewById(R.id.recycler_view);



        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setHasFixedSize(true);

        final DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());

        mUnReadNotificationModels = databaseHelper.getAppNotificationDetail();

        if(mUnReadNotificationModels.size() > 0)

        {
            fab_delete.show();

        }else
        {
            fab_delete.hide();

        }

        fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setTitle("Clear All Notification!");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to Clear all notifications ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                DatabaseHelper databaseHelperdel = new DatabaseHelper(getApplicationContext());
                                databaseHelperdel.deleteAllNotification();

                                setDataNotAvailable();


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
                alert.setTitle("Clear All Notification!");
                alert.show();




            }
        });

        applyRegionListDataToView();
    }



    private void setUpToolBar() {
        // toolbar.setTitle(nametoolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.monsoon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();


    }



    private void applyRegionListDataToView() {


        Timber.d("region list response size is %s ", "" + mUnReadNotificationModels.size());



        mUNReadNotificationAdapter = new UNReadNotificationAdapter(NotificationActivity.this, R
                .layout.row_notifications, mUnReadNotificationModels, false);
        recyclerView.setAdapter(mUNReadNotificationAdapter);


    }


    private void setDataAvailable() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void setDataNotAvailable() {
        mUNReadNotificationAdapter = new UNReadNotificationAdapter(NotificationActivity.this, R
                .layout.row_notifications, null, false);
        recyclerView.setAdapter(mUNReadNotificationAdapter);
    }


    private void showProgressView() {
        mUNReadNotificationAdapter = new UNReadNotificationAdapter(NotificationActivity.this, R
                .layout.row_notifications, null, true);
        recyclerView.setAdapter(mUNReadNotificationAdapter);
    }


    public class UNReadNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Activity activity;
        private int resourceId;
        private ArrayList<NotificationModel> unReadNotificationList;

        private boolean isLoadingData = false;

        public UNReadNotificationAdapter(Activity activity, int resourceId,
                                         ArrayList<NotificationModel> unReadNotificationList,
                                         boolean
                                                 isLoadingData) {
            this.activity = activity;
            this.unReadNotificationList = unReadNotificationList;
            this.resourceId = resourceId;
            this.isLoadingData = isLoadingData;
        }

        @Override
        public int getItemCount() {
            if (unReadNotificationList != null) {
                if (unReadNotificationList.size() > 0) {
                    return unReadNotificationList.size();
                } else {
                    return 1; // for data not available
                }
            } else {
                return 1; // for data not available
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (!isLoadingData) {
                if (unReadNotificationList != null) {
                    if (unReadNotificationList.size() > 0) {
                        return DETAILS_VIEW;
                    } else {
                        return DATA_NOT_AVAILABLE_VIEW;
                    }
                } else {
                    return DATA_NOT_AVAILABLE_VIEW;
                }
            } else {
                return PROGRESS_VIEW;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case DETAILS_VIEW:
                    view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent,
                            false);
                    return new DetailsViewHolder(view);

                case PROGRESS_VIEW:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                                    .layout_progress, parent,
                            false);
                    return new ProgressViewHolder(view);


                case DATA_NOT_AVAILABLE_VIEW:
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout
                                    .layout_data_not_available, parent,
                            false);
                    return new DataNotAvailableViewHolder(view);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

            int viewType = getItemViewType(position);

            Timber.d("OnBindViewHolder for position %d", position);
            Timber.d("OnBindViewHolder for view Holder get position %d", viewHolder.getPosition());

            switch (viewType) {
                case DATA_NOT_AVAILABLE_VIEW:
                    break;

                case PROGRESS_VIEW:
                    break;

                default:
                    initDetailsView(((DetailsViewHolder) viewHolder), position);
                    break;
            }
        }


        private void initDetailsView(DetailsViewHolder viewHolder, int position) {

            NotificationModel readNotificationModel = unReadNotificationList.get(position);

            Typeface face = Typeface.createFromAsset(getAssets(), "fonts/roboto_condensed_regular.ttf");
            Typeface facetxt = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");
            viewHolder.textViewNotificationDetails.setTypeface(face);
            viewHolder.textViewNotificationTitle.setTypeface(facetxt);

            viewHolder.textViewNotificationDetails.setText("" + readNotificationModel.getAlert());
            viewHolder.textViewNotificationTitle.setText("" + readNotificationModel.getTitle());

        }

        public class DataNotAvailableViewHolder extends RecyclerView.ViewHolder {

            ImageView dataNotAvailable;

            public DataNotAvailableViewHolder(View headerView) {
                super(headerView);
                // dataNotAvailable = (TextViewFont) headerView.findViewById(R.id.txt_data_not_available);

                dataNotAvailable = (ImageView) headerView.findViewById(R.id.imageView6);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        dataNotAvailable.getLayoutParams();
                int topMargin = Utils.getDeviceCenterPoint(NotificationActivity.this);
                layoutParams.setMargins(0, topMargin, 0, 0);
                dataNotAvailable.setLayoutParams(layoutParams);
            }
        }

        public class ProgressViewHolder extends RecyclerView.ViewHolder {

            ProgressBar progressWheel;

            public ProgressViewHolder(View progressView) {
                super(progressView);
                progressWheel = (ProgressBar) progressView.findViewById(R.id.progress_wheel);
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        progressWheel.getLayoutParams();
                int topMargin = Utils.getDeviceCenterPoint(NotificationActivity.this);
                layoutParams.setMargins(0, topMargin, 0, 0);
                progressWheel.setLayoutParams(layoutParams);
            }
        }


        public class DetailsViewHolder extends RecyclerView.ViewHolder implements View
                .OnClickListener {

            TextView textViewNotificationTitle;
            TextView

                    textViewNotificationDetails;

            public DetailsViewHolder(View viewHolder) {
                super(viewHolder);

                textViewNotificationDetails = (TextView) viewHolder.findViewById(R.id
                        .txt_notification_description);

                textViewNotificationTitle = (TextView) viewHolder.findViewById(R.id
                        .txt_notification_title);

                viewHolder.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                try {

                    //  handleNotificationAction(unReadNotificationList.get(getAdapterPosition()));


/*
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            RealmResults<NotificationModel> result = realm.where(NotificationModel.class).equalTo("NotificationId", unReadNotificationList.get(getAdapterPosition()).getNotificationId()).findAll();
                            result.deleteAllFromRealm();
                        }
                    });*/

                    DatabaseHelper databaseHelperdel = new DatabaseHelper(getApplicationContext());
                    databaseHelperdel.deleteNotification(unReadNotificationList.get(getAdapterPosition()).getNotificationId());

                    unReadNotificationList.remove(getAdapterPosition());
                    notifyDataSetChanged();

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }



        }
    }






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
