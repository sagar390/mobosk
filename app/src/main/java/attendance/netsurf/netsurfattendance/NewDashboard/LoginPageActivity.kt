package attendance.netsurf.netsurfattendance.NewDashboard

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.R
import attendance.netsurf.netsurfattendance.databinding.ActivityLandingPageBinding
import attendance.netsurf.netsurfattendance.databinding.ActivityLoginPageBinding
import attendance.netsurf.netsurfattendance.models.LoggedInUser
import mylab.android.pc_app_new.CommanListofOfficiers.viewmodel.ApiViewModel
import mylab.android.pc_app_new.Utils.CommanAlertDialog
import java.util.ArrayList

class LoginPageActivity : AppCompatActivity(){

    lateinit var  commanlistviewmodel: ApiViewModel

    var  chk_user_id: String =""
    var  user_type: String =""

    private var _binding_login: ActivityLoginPageBinding? = null

    var builder: AlertDialog?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //   setContentView(R.layout.activity_login)

        commanlistviewmodel = ViewModelProvider(this).get(ApiViewModel::class.java)

        _binding_login = ActivityLoginPageBinding.inflate(layoutInflater)
        val root: View = _binding_login!!.root
        setContentView(root)




        _binding_login!!.btnLoginPg!!.setOnClickListener {



            if(_binding_login!!.edtUseridPg!!.text.toString().trim().isEmpty())
            {
                CommanAlertDialog.showToast(this@LoginPageActivity,"Enter a User Id")

            }else  if(_binding_login!!.editPasswordPg!!.text.toString().trim().isEmpty())
            {
                CommanAlertDialog.showToast(this@LoginPageActivity,"Enter your Password")

            }else
            {
                loginFromMobileError()
                getLogin()
            }



        }


       
        _binding_login!!.showPassBtn!!.setOnClickListener {
            _binding_login!!.showPassBtn?.let { it1 -> ShowHidePass(it1) }



        }









    }

    fun loginFromMobileError() {
        commanlistviewmodel.errorMessage.observe(this, {
            Toast.makeText(
                this,
                "Make sure you have an active data connection ",
                Toast.LENGTH_SHORT
            )
                .show()

        })
    }



    fun ShowHidePass(view: View) {
        if (view.id == R.id.show_pass_btn) {
            if (_binding_login!!.editPasswordPg!!.getTransformationMethod()
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                //   _binding_login!!.showPassBtn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.eye_line, 0, 0, 0);
                _binding_login!!.showPassBtn!!.setImageResource(R.drawable.eye_line);


                //Show Password
                _binding_login!!.editPasswordPg!!.setTransformationMethod(
                    HideReturnsTransformationMethod.getInstance())
            } else {
                _binding_login!!.showPassBtn!!.setImageResource(R.drawable.eye_off_line);

                //Hide Password
                _binding_login!!.editPasswordPg!!.setTransformationMethod(PasswordTransformationMethod.getInstance())
            }
        }
    }



    fun getLogin() {

        CommanAlertDialog.showProgessdialogAct(this)


        var login_ojb: LoggedInUser.Request = LoggedInUser.Request(_binding_login!!.edtUseridPg!!.text.toString().trim(),_binding_login!!.editPasswordPg!!.text.toString().trim())


        commanlistviewmodel?.getLoginApi(login_ojb).observe(this, object :
            Observer<ArrayList<LoggedInUser.Response>> {
            override fun onChanged(obj_account: ArrayList<LoggedInUser.Response>) {
                if (obj_account != null) {

                    CommanAlertDialog.showProgessdialogDismissAct(this@LoginPageActivity)

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
                        BaseApplication.Companion.SharedPref.write(Constants.UserName,_binding_login!!.edtUseridPg!!.text.toString().trim())
                        BaseApplication.Companion.SharedPref.write(Constants.Password,_binding_login!!.editPasswordPg!!.text.toString().trim())




                        val intent = Intent(this@LoginPageActivity, MainActivity2::class.java)
                        startActivity(intent)
                        finish()

                    } else {

                        CommanAlertDialog.showToast(this@LoginPageActivity,"Incorrect login credientials")


                    }

                }
            }
        })



    }





}
