package attendance.netsurf.netsurfattendance;


import android.content.Context;

import timber.log.Timber;

/**
 * Created by chetan on 14/05/15.
 */
public class Utils {

    private static final String MOBILE_NUMBER_MATCHER = "^(\\+91[\\-\\s]?)?[789]\\d{9}$";
    private static final String LANDLINE_NUMBER_MATCHER = "^[0-9]\\d{2,4}[\\-\\s]?\\d{6,8}$";

    private static int centerPoint = 0;

    public static boolean isNetworkAvailable(android.app.Activity context) {
        try {
            android.net.ConnectivityManager connManager = (android.net.ConnectivityManager) context.getSystemService
                    (android.content.Context.CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                return true;
            }
        } catch (Exception ex) {

            ex.printStackTrace();
            return false;
        }
        return false;
    }

    public static int getDeviceCenterPoint(Context context) {
        if (centerPoint == 0) {
            float heightPixel = context.getResources().getDisplayMetrics().heightPixels / 2;
            float density = context.getResources().getDisplayMetrics().density;
            centerPoint = (int) (heightPixel / density);
        }
        Timber.d("center point of device is %s ", "" + centerPoint);
        return centerPoint;
    }

    public static void hideKeyboard(android.content.Context context, android.view.View editText) {
        try {
            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) context.getSystemService(android.content.Context
                    .INPUT_METHOD_SERVICE);
            if (editText != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                editText.clearFocus();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}