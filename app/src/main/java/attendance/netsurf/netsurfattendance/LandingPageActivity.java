package attendance.netsurf.netsurfattendance;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LandingPageActivity extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.name_title)
    TextView name_user;

    @BindView(R.id.emp_id)
    TextView emp_id;

    SharedPreferences pref;

    @BindView(R.id.progressBar_web)
    ProgressBar webView_progress;



    Intent intent;
    String nametoolbar;
    String status;
    String params = "";
    private float m_downX;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        ButterKnife.bind(this);

        intent = getIntent();

        nametoolbar = intent.getStringExtra("Toolbar");
        status = intent.getStringExtra("status");
        pref = getApplicationContext().getSharedPreferences("Attendance", 0);
        params = intent.getStringExtra("Param");


        Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/roboto_condensed_regular.ttf");
        name_user.setTypeface(face);
        emp_id.setTypeface(face);

        name_user.setText("Welcome: " + pref.getString("Name", null));
        emp_id.setText("Emp Id : " + pref.getInt("EmployeeId", 0));

        setUpToolBar();


    //    pd = ProgressDialog.show(LandingPageActivity.this,"","Please wait..",false,false);
      //  pd = new ProgressDialog(LandingPageActivity.this,R.style.AppCompatAlertDialogStyle);
        pd.setTitle("Please Wait..");
        initWebView();




        if (params == null) {
            if (status.equalsIgnoreCase("one")) {

                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveApply&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);

                Log.d("1 url", "" + url);



            } else if (status.equalsIgnoreCase("two")) {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveList&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);
                Log.d("2 url", "" + url);

            } else if (status.equalsIgnoreCase("three")) {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveApprove&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);
                Log.d("3 url", "" + url);


            } else if (status.equalsIgnoreCase("four"))

            {


                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffRequest&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);

                Log.d("4 url", "" + url);


            } else if (status.equalsIgnoreCase("five")) {

                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffList&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);
                Log.d("5 url", "" + url);

            } else if (status.equalsIgnoreCase("six"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffApprove&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }
         else if (status.equalsIgnoreCase("seven"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=../tasksheet/Addtask&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }         else if (status.equalsIgnoreCase("10"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=../tasksheet/TeamReport&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }
       else if (status.equalsIgnoreCase("Eight"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=MedicalInsurancePolicy&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }
       else if (status.equalsIgnoreCase("Nine"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ViewPolicyReadOnly&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }

       else if (status.equalsIgnoreCase("ten"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ViewPolicyReadOnly&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }

       else if (status.equalsIgnoreCase("11"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=PayslipSearch&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");


              webView.loadUrl(url);

            /*    Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                finish();*/

                Log.d("6 url", "" + url);
            }

       else if (status.equalsIgnoreCase("12"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=HolidayList&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }
       else if (status.equalsIgnoreCase("13"))

            {
             //   String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ViewPolicyReadOnly&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");
                String url = Constants.BASE_PAGE_SHAREIDEA_URL + pref.getInt("EmployeeId", 0);


                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }
       else if (status.equalsIgnoreCase("14"))

            {
                String url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ChangePassword&userLogin=" + pref.getString("Username", "") + "&password=" + pref.getString("Password", "");

                webView.loadUrl(url);

                Log.d("6 url", "" + url);
            }



        } else {


            webView.loadUrl(params);

            Log.d("1 ", "" + params);
        }


        checkDownloadPermission();

/*
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                Log.d("PayslipDownload Url", "onDownloadStart: " + url);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                finish();



          *//*      DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));


                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.setDescription("Downloading file...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading...", Toast.LENGTH_SHORT).show();
                registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            }
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Toast.makeText(getApplicationContext(), "Downloading Complete", Toast.LENGTH_SHORT).show();
                }
            };*//*
            }
        });*/

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                //------------------------COOKIE!!------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE!!------------------------
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });
    }





    private void setUpToolBar() {
       // toolbar.setTitle(nametoolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.monsoon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void checkDownloadPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(LandingPageActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(LandingPageActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(LandingPageActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }


    private void initWebView() {

        webView.setWebChromeClient(new MyWebChromeClient(this));

    //    webView.setInitialScale(100);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            //    webView_progress.setVisibility(View.VISIBLE);


         //     pd = new ProgressDialog(LandingPageActivity.this,R.style.AppCompatAlertDialogStyle);
                pd.show();

                invalidateOptionsMenu();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            //  webView_progress.setVisibility(View.GONE);

                Log.d("onPageFinished Url", "onDownloadStart: " + url);

                pd.dismiss();
                invalidateOptionsMenu();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
         //       webView_progress.setVisibility(View.GONE);
                pd.dismiss();
                invalidateOptionsMenu();
            }
        });

        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
      //  webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setUseWideViewPort(true);











       // webView.getSettings().setLoadsImagesAutomatically(true);
       // webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);

//       webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        // Configure the client to use when opening URLs
        // root.web_view!!.setWebViewClient(WebViewClient())
        // root.web_view.getSettings().setUseWideViewPort(true);
        //root.web_view.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(true);



   /*     webView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getPointerCount() > 1) {
                    //Multi touch detected
                    return true;
                }

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        // save the x
                        m_downX = event.getX();
                    }
                    break;

                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP: {
                        // set x so that it doesn't move
                        event.setLocation(m_downX, event.getY());
                    }
                    break;
                }

                return false;
            }
        });*/
    }





    private class MyWebChromeClient extends WebChromeClient {
        Context context;

        public MyWebChromeClient(Context context) {
            super();
            this.context = context;
        }


    }



    @Override
    public void onResume() {
        super.onResume();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();


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
        webView.destroy();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }
}

