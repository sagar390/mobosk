package attendance.netsurf.netsurfattendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class WooHooMapActivity extends FragmentActivity implements OnMapReadyCallback{
        //GoogleMap.OnCameraChangeListener {

    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    public static final String EXTRA_PINCODE = "extra_pincode";
    private static final String GOOGLE_BASE_URL = "http://maps.googleapis" +
            ".com/maps/api/geocode/";
    private GoogleMap mMap;
    private double selectedLatitude, selectedLongitude;
    private String selectedPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        selectedLatitude = -1;
        selectedLongitude = -1;
        selectedPincode = getIntent().getStringExtra(EXTRA_PINCODE);
        setUpMapIfNeeded();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }
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
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
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
                .radius(1000)
                .strokeWidth(2)
                .strokeColor(Color.parseColor("#a1cdff"))
                .fillColor(Color.parseColor("#88a1cdff"));
        mMap.clear();
        mMap.addCircle(circleOptions);
        selectedLatitude = latitude;
        selectedLongitude = longitude;
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        changeCircularColor(latitude, longitude);
    }

 /*   @Override
    public void onCameraChange(CameraPosition position) {
        LatLng center = mMap.getCameraPosition().target;
        changeCircularColor(center.latitude, center.longitude);
    }*/

    @OnClick(R.id.imageView_info_next)
    public void onSelectClicked() {
        Timber.d("Clicked");
        Intent intent = new Intent();
        intent.putExtra(EXTRA_LATITUDE, selectedLatitude);
        intent.putExtra(EXTRA_LONGITUDE, selectedLongitude);
        setResult(RESULT_OK, intent);
        finish();
    }




}
