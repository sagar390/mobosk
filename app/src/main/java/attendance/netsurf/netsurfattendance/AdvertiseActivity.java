package attendance.netsurf.netsurfattendance;
/**
 * Created by sagar on last update 1/28/16.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;


import androidx.fragment.app.Fragment;

import java.util.ArrayList;


import butterknife.BindView;
import rx.Observable;
import rx.Subscription;

public class AdvertiseActivity extends AppCompatActivity {

    @BindView(R.id.imageview)
    ImageView imageView;
    @BindView(R.id.progress_wheel)
    ProgressBar progressWheel;

    private int status_code;
    private String datas;

    Uri uri;


    private Fragment currentFragment;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private Observable<ArrayList<AdvertiseModel.Response>> observableadvertise;
    private AdvertiseModel.Response addDetails;
    private String imagepath;
    private ProgressBar spinner;
    Subscription subscription;
    private Button view_details,skip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //GETTING tOKEN NO FOR URL ECECUTION
        view_details = (Button)findViewById(R.id.button_view) ;
        skip = (Button)findViewById(R.id.button_skip) ;
        SharedPreferences preferences = getSharedPreferences(MyPREFERENCES, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        spinner=(ProgressBar)findViewById(R.id.progressBar2);

     //   fetchData();
    }

   /* private void fetchData() {

        UsApiInterface netsurfApiInterface = NetsurfApiClient.getApiClient(this, true);
        if (netsurfApiInterface != null) {
            observableadvertise = netsurfApiInterface.getAdvertiseDetails();
            subscription = observableadvertise.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ArrayList<AdvertiseModel.Response>>() {

                        @Override
                        public void onCompleted() {

                            AdvertiseModel.Response offerDetails = addData.get(0);
                            imagepath = offerDetails.getimage_path();
                            String url = offerDetails.getimage_path();
                            //  "http://m.sales.netsurfnetwork.com/offerBanners/AsmitaLanding.jpg";
                            Timber.i("paimg is %s ", "" + offerDetails.getimage_path());
                            ImageView img = (ImageView) findViewById(R.id.imageview);
                            Picasso.with(getApplication())
                                    .load(url)
                                    .fit().centerCrop()
                                    .error(R.drawable.abc_btn_check_material)      // optional.resize(600, 300)
                                    .into(img, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            spinner.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });

                        }

                        @Override
                        public void onError(Throwable e) {
                            try {
                            } catch (NullPointerException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            } catch (IllegalStateException ex) {
                                Timber.i("error while setting data not available  %s ",
                                        "" + ex.getMessage());
                            }
                        }

                        @Override
                        public void onNext(ArrayList<AdvertiseModel.Response>
                                                   respons) {
                            try {
                                if (respons != null && respons.size() > 0) {
                                    addData.clear();
                                    addData.addAll(respons);
                                    view_details.setText(addData.get(0).getButtonText());

                                } else {
                                    Timber.d("Addvt Data response%s ", "empty.");
                                }
                            } catch (NullPointerException ex) {
                                ex.printStackTrace();
                            } catch (IllegalStateException ex) {

                            }
                        }
                    });

        } else {
        }

    }


*/
    public void skip(View v) {


            Intent intent = new Intent(AdvertiseActivity.this, ProfileDashBoardActivity.class);
            startActivity(intent);
            finish();

    }


    /*public void view(View v) {

        final AdvertiseModel.Response offerDetails = addData.get(0);
        String webUrl = offerDetails.getWebURL();
        String param1 = offerDetails.getParam1();
        String param2 = offerDetails.getParam2();
        String param3 = offerDetails.getParam3();
           Timber.i("param id is %s ", "" + offerDetails.getWebURL());
           Timber.i("param1 is %s ", "" + offerDetails.getParam1());
           Timber.i("param2 is %s ", "" + offerDetails.getParam2());

            if (!webUrl.equals("")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
               startActivity(browserIntent);
            }
            else
            {
                Toast.makeText(AdvertiseActivity.this, "Feature Not Available", Toast.LENGTH_LONG).show();
            }
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


}


