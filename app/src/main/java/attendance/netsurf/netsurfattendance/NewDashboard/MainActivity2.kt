package attendance.netsurf.netsurfattendance.NewDashboard


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.LandingPageActivity
import attendance.netsurf.netsurfattendance.R
import attendance.netsurf.netsurfattendance.databinding.ActivityMain2Binding
import attendance.netsurf.netsurfattendance.models.InsertUpdateDeviceTokenId
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import mylab.android.pc_app_new.CommanListofOfficiers.viewmodel.ApiViewModel
import mylab.android.pc_app_new.Utils.CommanAlertDialog


class MainActivity2 : AppCompatActivity() ,DashboardClickListener{
    private var mAppBarConfiguration: AppBarConfiguration? = null
    private var binding: ActivityMain2Binding? = null
    lateinit var navController: NavController
    lateinit var  commanlistviewmodel: ApiViewModel
    private var Token_App = ""


    companion object{

       var isUpdatePage : Boolean = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(
            layoutInflater
        )


        commanlistviewmodel = ViewModelProvider(this).get(ApiViewModel::class.java)
        setContentView(binding!!.root)
        setSupportActionBar(binding!!.appBarMain.toolbar)

        val drawer = binding!!.drawerLayout
        val navigationView = binding!!.navView



        val hView: View = navigationView.getHeaderView(0)
        val nav_user = hView.findViewById<View>(R.id.name_txt) as TextView
        val nav_user_ref_no = hView.findViewById<View>(R.id.textView_email) as TextView

        nav_user.setText(BaseApplication.Companion.SharedPref.read(Constants.LoginId, ""))
        nav_user_ref_no.setText("Emp Id : "+BaseApplication.Companion.SharedPref.read(Constants.EmployeeId, 0))



        Token_App = FirebaseMessaging.getInstance().token.toString()

        binding!!.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            saveToken(Token_App)

        }

        Log.d("Token_App is ", "onCreate 55: $Token_App")




        //    saveToken(FirebaseInstanceId.getInstance().getToken());





        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_page)
            .setOpenableLayout(drawer)
            .build()

         navController = findNavController(this, R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener { it: MenuItem ->
            when (it.itemId) {


                R.id.nav_logout -> {
                    logoutdialog()

                    true
                }
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)

                    true
                }

                R.id.nav_gallery -> {

                    true
                }



                else -> {

                    true
                }


            }

            drawer!!.closeDrawer(GravityCompat.START)
            true

        }



    }




    private fun logoutdialog() {

        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Logout?")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, which ->

                try {
                    BaseApplication.Companion.SharedPref.clearData()


                    val i = Intent(this, LoginPageActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                    finish()


                } catch (e: Exception) {

                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }

            .show()

    }






    fun saveToken(token_id : String) {



        var login_ojb: InsertUpdateDeviceTokenId.Request = InsertUpdateDeviceTokenId.Request( token_id,BaseApplication.Companion.SharedPref.read(Constants.EmployeeId, 0).toString())


        commanlistviewmodel?.insertUpdateDeviceTokenApi(login_ojb).observe(this, object :
            Observer<ArrayList<InsertUpdateDeviceTokenId.Response>> {
            override fun onChanged(obj_account: ArrayList<InsertUpdateDeviceTokenId.Response>) {
                if (obj_account != null) {


                    //   progressWheel.setVisibility(android.view.View.GONE);
                    FirebaseMessaging.getInstance().subscribeToTopic("All")


                        CommanAlertDialog.showToast(this@MainActivity2,""+obj_account[0].DeviceTokenId)


                    } else {

                        CommanAlertDialog.showToast(this@MainActivity2,"Incorrect login credientials")


                    }


            }
        })



    }


    public fun setValidLogin() {
        getLogin()

    }



    fun getLogin() {

        CommanAlertDialog.showProgessdialogAct(this)


        var login_ojb: LoggedInUser.Request = LoggedInUser.Request(BaseApplication.Companion.SharedPref.read(Constants.LoginId, "").toString().trim(),
            BaseApplication.Companion.SharedPref.read(Constants.Password, "").toString())

        commanlistviewmodel?.getLoginApi(login_ojb).observe(this, object :
            Observer<java.util.ArrayList<LoggedInUser.Response>> {
            override fun onChanged(obj_account: java.util.ArrayList<LoggedInUser.Response>) {
                if (obj_account != null) {

                    CommanAlertDialog.showProgessdialogDismissAct(this@MainActivity2)

                    if(obj_account.size > 0) {



                        BaseApplication.Companion.SharedPref.write(Constants.CompId,obj_account!![0].CompId)
                        BaseApplication.Companion.SharedPref.write(Constants.EmployeeId,obj_account!![0].EmployeeId!!.toInt())
                        BaseApplication.Companion.SharedPref.write(Constants.UserType,obj_account!![0].UserType)
                        obj_account!![0].Active?.let {
                            BaseApplication.Companion.SharedPref.write(Constants.Active,
                                it
                            )
                        }
                        obj_account!![0].IsPoliciesAccepted?.let {
                            BaseApplication.Companion.SharedPref.write(Constants.IsPoliciesAccepted,
                                it
                            )
                        }
                        BaseApplication.Companion.SharedPref.write(Constants.LoginId,obj_account!![0].LoginId)
                        BaseApplication.Companion.SharedPref.write(Constants.Name,obj_account!![0].Name)
                        BaseApplication.Companion.SharedPref.write(Constants.IsReporty,obj_account!![0].IsReporty)
                        BaseApplication.Companion.SharedPref.write(Constants.IsOutstationEmployee,obj_account!![0].IsOutstationEmployee)
                        BaseApplication.Companion.SharedPref.write(Constants.Name,BaseApplication.Companion.SharedPref.read(Constants.LoginId, 0).toString().trim())
                        BaseApplication.Companion.SharedPref.write(Constants.Password,BaseApplication.Companion.SharedPref.read(Constants.Password, 0).toString().trim())




                        val intent = Intent(this@MainActivity2, MainActivity2::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        CommanAlertDialog.showToast(this@MainActivity2,"Incorrect login credientials")


                    }

                }
            }
        })



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_activity2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(this, R.id.nav_host_fragment_content_main)
        return (navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onPageWEbClickedEvenet(bundle: Bundle) {
        navController.navigate(R.id.nav_page,bundle)

    }

    override fun onResume() {
        super.onResume()


        if(BaseApplication.Companion.SharedPref.read(Constants.IsPoliciesAccepted, false) == false)
        {

           binding!!.appBarMain.toolbar.visibility = View.GONE

            val bundle = Bundle()
            bundle.putString(Constants.PAGE_URL_NAME, "Approve Policy")
            bundle.putString(Constants.PAGE_ID, "0")
            onPageWEbClickedEvenet(bundle)

     }else
        {
            binding!!.appBarMain.toolbar.visibility = View.VISIBLE



        }

    }
}