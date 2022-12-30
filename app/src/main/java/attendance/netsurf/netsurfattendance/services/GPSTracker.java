package attendance.netsurf.netsurfattendance.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import java.text.DecimalFormat;

public class GPSTracker extends Service implements LocationListener {
 
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    public static boolean canGetLocation = false;
 
    Location location; // location
    public static double latitude; // latitude
    public static double longitude; // longitude
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 5 meters
 
    // The minimum time between updates in milliseconds
    public static long MIN_TIME = 5; // 5 seconds
    private static long MIN_TIME_BW_UPDATES = 1000 * MIN_TIME; // seconds
	public static final String LOCUPDATE_ACTION = "GPSLOCATIONUPDATE";
	public final static String LAT = "lat";
	public final static String LONG = "long";
    public final static String ACCURACY = "accuracy";
	protected LocationManager locationManager;
	private Intent intent;
	private static final String TAG = "GPSTracker";
    
	@Override
    public void onCreate() {
        super.onCreate();

    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
 
		getLocation();
    	
        return START_STICKY;
    }
    
    public Location getLocation() {
        try {

			if (locationManager == null) {

				locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
			}

			if (locationManager != null) {
				// getting GPS status
				isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

				// getting network status
				isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


				if (!isGPSEnabled && !isNetworkEnabled) {


					this.canGetLocation = false;
				} else {
					this.canGetLocation = true;


					// First get location from Network Provider
					if (isNetworkEnabled) {

try {
    locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            MIN_TIME_BW_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

}catch (SecurityException e)
{

}
						Log.d("GPSTracker", "Network");

						if (locationManager != null) {


							if (location != null) {


								latitude = location.getLatitude();
								longitude = location.getLongitude();
							} else {

							}
						} else {

						}

					}
					// if GPS Enabled get lat/long using GPS Services
					if (isGPSEnabled) {

                        if (location == null) {
                            try
                            {
							locationManager.requestLocationUpdates(
									LocationManager.GPS_PROVIDER,
									MIN_TIME_BW_UPDATES,
									MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        }catch (SecurityException e)
                        {}
                        Log.d("GPSTracker", "Network");

							Log.d("GPSTracker", "GPS Enabled");

							if (locationManager != null) {

								if (location != null) {


									latitude = location.getLatitude();
									longitude = location.getLongitude();
								} else {

								}
							} else {

							}
						}
                    }
				}
			} else {

			}



        } catch (Exception e) {
        	

            e.printStackTrace();
        }
 
        return location;
    }
      
    
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
    	
    	Log.d("GPSTracker", "stopUsingGPS");
    	
        if(locationManager != null){
            try{
            locationManager.removeUpdates(GPSTracker.this);
        }catch (SecurityException e)
        {

        }
        Log.d("GPSTracker", "Network");

        this.canGetLocation = false;
        }       
    }
    
    @Override
    public void onDestroy() {
    	 super.onDestroy();
    	
    	 Log.d("GPSTracker", "onDestroy() Start");

    	 if(locationManager != null){
             try{
             locationManager.removeUpdates(GPSTracker.this);
         }catch (SecurityException e)
        {

        }
             this.canGetLocation = false;
         }     
    	 
    	 Log.d("GPSTracker", "onDestroy() End");
    }
     
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
     

    public boolean CanGetLocation() {
        return this.canGetLocation;
    }
     

    @Override
    public void onLocationChanged(Location location) {
    
		intent = new Intent(LOCUPDATE_ACTION);

		DecimalFormat dFormat = new DecimalFormat("#.#####");
		double d= location.getLatitude();
		double d1= location.getLongitude();
        double accu= location.getAccuracy();

		d= Double.valueOf(d);
		d1=Double.valueOf(d1);

		String latStr = String.valueOf(dFormat.format(d));
		String longStr = String.valueOf(dFormat.format(d1));
   //	String accuracy = Double.toString(location.getAccuracy());
        /*	String longStr = Double.toString(location.getLongitude());
*/
        intent.putExtra(LAT, ""+d);
        intent.putExtra(LONG, ""+d1);
        intent.putExtra(ACCURACY, ""+accu);

		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        

    }
    
    @Override
    public void onProviderDisabled(String provider) {
    	
    	getLocation();
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    	
    	getLocation();
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
 
}
