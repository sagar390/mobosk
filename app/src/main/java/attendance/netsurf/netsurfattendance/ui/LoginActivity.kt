package attendance.netsurf.netsurfattendance.ui

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import butterknife.BindView
import attendance.netsurf.netsurfattendance.R
import android.widget.EditText
import android.widget.RelativeLayout
import android.content.SharedPreferences
import android.os.Bundle
import butterknife.ButterKnife
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import attendance.netsurf.netsurfattendance.NetsurfApiClient
import rx.schedulers.Schedulers
import rx.android.schedulers.AndroidSchedulers
import timber.log.Timber
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import attendance.netsurf.netsurfattendance.ProfileDashBoardActivity
import rx.Observable
import rx.Observer
import rx.Subscription
import java.lang.IllegalStateException
import java.lang.NullPointerException
import java.util.ArrayList

class LoginActivity : AppCompatActivity() {
    private var observableadvertise: Observable<ArrayList<LoggedInUser.Response>>? = null
    var subscription: Subscription? = null

    @JvmField
    @BindView(R.id.login_username)
    var edit_username: EditText? = null

    @JvmField
    @BindView(R.id.login_password)
    var edit_password: EditText? = null

    @JvmField
    @BindView(R.id.img_login)
    var btn_login: RelativeLayout? = null

    @JvmField
    @BindView(R.id.progressBar_login)
    var progressBar: ProgressBar? = null

    private var mLoginId = ""
    private var mPassword = ""
    var pref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ButterKnife.bind(this)
        checkAndRequestPermissions()
        pref = applicationContext.getSharedPreferences("Attendance", 0)
        btn_login!!.setOnClickListener {
            mLoginId = edit_username!!.text.toString().trim { it <= ' ' }
            mPassword = edit_password!!.text.toString().trim { it <= ' ' }
            var valid = true
            if (mLoginId.isEmpty()) {
                edit_username!!.error = "Please enter Username"
                valid = false
            } else {
                edit_username!!.error = null
            }
            if (mPassword.isEmpty()) {
                edit_password!!.error = "Please enter Password"
                valid = false
            } else {
                edit_password!!.error = null
            }
            if (valid) {
                progressBar!!.visibility = View.VISIBLE
                //startLoginProcedure1(mLoginId, mPassword)
            }
        }
    }

    private fun checkAndRequestPermissions(): Boolean {
        val camera = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val storage =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val loc =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val loc2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                listPermissionsNeeded.toTypedArray(),
                REQUEST_ID_MULTIPLE_PERMISSIONS
            )
            return false
        }
        return true
    }

/*
    private fun startLoginProcedure1(uname: String, Pwd: String) {
        val netsurfApiInterface = NetsurfApiClient.getApiClient(this, true)

        //  progressWheel.setVisibility(android.view.View.VISIBLE);
        if (netsurfApiInterface != null) {
            val logReq = LoggedInUser.Request()
            logReq.loginid = uname
            logReq.password = Pwd
            //  logReq.setRoleID(9);
            observableadvertise = netsurfApiInterface.login(logReq)
            subscription = observableadvertise.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ArrayList<LoggedInUser.Response>> {
                    override fun onCompleted() {
                        progressBar!!.visibility = View.INVISIBLE
                    }

                    override fun onError(e: Throwable) {
                        //     progressWheel.setVisibility(android.view.View.GONE);
                        progressBar!!.visibility = View.INVISIBLE
                        try {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login Failed " + e.message,
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (ex: NullPointerException) {
                            Timber.i(
                                "error while setting data not available  %s ",
                                "" + ex.message
                            )
                        } catch (ex: IllegalStateException) {
                            Timber.i(
                                "error while setting data not available  %s ",
                                "" + ex.message
                            )
                        }
                    }

                    override fun onNext(respons: ArrayList<LoggedInUser.Response>) {
                        progressBar!!.visibility = View.INVISIBLE

                        //   progressWheel.setVisibility(android.view.View.GONE);
                        if (respons[0].name != null || respons[0].officeId != 0) {
                            Toast.makeText(this@LoginActivity, "Login Success ", Toast.LENGTH_LONG)
                                .show()
                            val editor = pref!!.edit()
                            editor.putInt("CompId", respons[0].compId) // Storing string
                            editor.putInt("EmployeeId", respons[0].employeeId) // Storing string
                            editor.putInt("OfficeId", respons[0].officeId) // Storing string
                            editor.putString("UserType", respons[0].userType) // Storing string
                            editor.putBoolean("Active", respons[0].isActive) // Storing string
                            editor.putBoolean(
                                "IsPoliciesAccepted",
                                respons[0].isPoliciesAccepted
                            ) // Storing string
                            editor.putString("LoginId", respons[0].loginId) // Storing string
                            editor.putString("Name", respons[0].name) // Storing string
                            editor.putString("IsReporty", respons[0].isReporty) // Storing string
                            editor.putString("Username", uname) // Storing string
                            editor.putString("Password", Pwd) // Storing string
                            editor.commit()
                            if (respons[0].isReporty == "1") {
                            }
                            if (respons[0].isReporty == "2") {
                            }
                            val intent =
                                Intent(this@LoginActivity, ProfileDashBoardActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                })
        } else {
            //  android.widget.Toast.makeText(AdvertiseActivity.this, "web url is", android.widget.Toast.LENGTH_LONG).show();
        }
    }
*/

    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
    }
}