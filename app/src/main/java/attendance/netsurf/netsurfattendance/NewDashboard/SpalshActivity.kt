package attendance.netsurf.netsurfattendance.NewDashboard

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.R
import com.google.android.gms.common.wrappers.InstantApps
import com.google.firebase.analytics.FirebaseAnalytics
import mylab.android.pc_app_new.Utils.CommanAlertDialog


class SpalshActivity  : AppCompatActivity() {

    var isuserlogged : Int = 0


    val STATUS_INSTALLED = "installed"
    val STATUS_INSTANT = "instant"
    val ANALYTICS_USER_PROP = "app_type"
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onResume() {
        super.onResume()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spalsh)



        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        // Determine the current app context, either installed or instant, then
        // set the corresponding user property for Google Analytics.
        if (InstantApps.isInstantApp(this)) {
            firebaseAnalytics.setUserProperty(ANALYTICS_USER_PROP, STATUS_INSTANT)
        } else {
            firebaseAnalytics.setUserProperty(ANALYTICS_USER_PROP, STATUS_INSTALLED)
        }



        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        isuserlogged =  BaseApplication.Companion.SharedPref.read(Constants.EmployeeId, 0)
        Log.d("TAG", "onCreate: "+isuserlogged)



        if(checkForInternet(this)) {


            Handler().postDelayed({


                if(isuserlogged == 0 )

                {
                    val intent = Intent(this, LoginPageActivity::class.java)
                    startActivity(intent)
                    finish()

                }else {
                   val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3000) // 3000 is the delayed time in milliseconds.


        }else
        {
            CommanAlertDialog.showToast(this@SpalshActivity,"No internet connection")


        }

    }



    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager!!.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}