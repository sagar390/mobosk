package attendance.netsurf.netsurfattendance.NewDashboard

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.databinding.FragmentWebPageBinding


class WebPageFragment : Fragment() {

    private var _binding_pay_layout: FragmentWebPageBinding? = null


    lateinit var root: View


    lateinit var  title : String
    lateinit var  shipping_id : String
    lateinit var  pid : String
    lateinit var  page_url : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding_pay_layout = FragmentWebPageBinding.inflate(inflater, container, false)
        root = _binding_pay_layout!!.root



        title = requireArguments().getString(Constants.PAGE_URL_NAME).toString()
        pid = requireArguments().getString(Constants.PAGE_ID).toString()

        (requireActivity() as AppCompatActivity).supportActionBar?.title = "" + title


        if(pid.equals("0"))
        {



            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ViewPolicy&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")


        }else  if(pid.equals("1"))
        {

            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=../tasksheet/Addtask&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("2"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=../tasksheet/TeamReport&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("3"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=PayslipSearch&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")


}else  if(pid.equals("4"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=MedicalInsurancePolicy&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")



}else  if(pid.equals("5"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ViewPolicyReadOnly&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("6"))
        {








            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveApply&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("7"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveList&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("8"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=LeaveApprove&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("9"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffRequest&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("10"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffList&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  if(pid.equals("11"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=CompOffApprove&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }
else  if(pid.equals("12"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=HolidayList&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }
else  if(pid.equals("13"))
        {


            page_url = Constants.BASE_PAGE_SHAREIDEA_URL + BaseApplication.Companion.SharedPref.read(Constants.EmployeeId, 0).toString()


        }
else  if(pid.equals("14"))
        {
            page_url = Constants.BASE_PAGE_URL_LEAVES_URL + "NewUI/RoutMe.aspx?pagename=ChangePassword&userLogin=" + BaseApplication.Companion.SharedPref.read(Constants.LoginId, "") + "&password="+ BaseApplication.Companion.SharedPref.read(Constants.Password, "")

        }else  {
        }



       Log.d("finish_url ", "onCreate: "+page_url)







        _binding_pay_layout!!.butPayment.setOnClickListener {


            val i = Intent(requireActivity(), MainActivity2::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            requireActivity().finish()

        }



        _binding_pay_layout!!.webview.loadUrl(page_url)


        _binding_pay_layout!!.webview.settings.javaScriptEnabled = true


        _binding_pay_layout!!.webview.webViewClient = WebViewClient()



        // Set web view client
        _binding_pay_layout!!.webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                // Page loading started
                // Do something
                // textview.setText("Page Loading Started ...")

                //  Log.d("finish_url ", "onCreate: "+url)



                var uri = Uri.parse(url)

                if(uri.lastPathSegment.equals("ccReqResponse"))

                {
                   //val myAct: MainActivity = requireActivity() as MainActivity
               //     myAct.settBadge()
                }else{

                }
            }

            override fun onPageFinished(view: WebView, url: String) {
                // Page loading finished
                // Enable disable back forward button
                //   textview.setText("Page Loading Finished ....")
            }
        }


        root!!.isFocusableInTouchMode = true
        root!!.requestFocus()

        root!!.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.getAction() === KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {


                        AlertDialog.Builder(requireActivity())
                            .setTitle("Please Confirm?")
                            .setMessage("Are you sure you want to close this transaction?")
                            .setPositiveButton("Confirm") { dialog, which ->
                                // Clear all the Application Cache, Web SQL Database and the HTML5 Web Storage
                                WebStorage.getInstance().deleteAllData();

                                // Clear all the cookies
                                CookieManager.getInstance().removeAllCookies(null);
                                CookieManager.getInstance().flush();

                                _binding_pay_layout!!.webview.clearHistory()
                                _binding_pay_layout!!.webview.clearFormData()
                                _binding_pay_layout!!.webview.clearSslPreferences()
                                _binding_pay_layout!!.webview.clearCache(true)

                                if(pid.equals("0")) {

                                    val myAct: MainActivity2 = requireActivity() as MainActivity2
                                    myAct.setValidLogin()

                                    requireActivity().finish()
                                }else{
                                  requireActivity().onBackPressed()
                                }






                            }

                            .setNegativeButton("Cancel") { dialog, which ->

                                dialog.dismiss()

                            }

                            .show()

                        return true
                    }
                }
                return false
            }
        })

        return root
    }





    override fun onResume() {
        super.onResume()


    }

}
