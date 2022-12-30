package mylab.android.pc_app_new.Utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import attendance.netsurf.netsurfattendance.MainActivity
import attendance.netsurf.netsurfattendance.R


class CommanAlertDialog private constructor() {

    companion object {

        lateinit var builder: AlertDialog.Builder
        lateinit var dialog: AlertDialog


        private var currentToast: Toast? = null
        private var currentMessage: String? = null

        private var context: Context? = null
        fun init(context: Context?) {
            if (this.context == null) {
                this.context = context
            }
        }

        fun showProgessdialog(fragment: FragmentActivity) {

            builder = android.app.AlertDialog.Builder(fragment)
            builder.setCancelable(true) // if you want user to wait for some process to finish,
            builder.setView(R.layout.layout_loading_dialog)
            dialog = builder.create()
//            dialog.show()


        }

        fun showProgessdialogDismiss(fragment: FragmentActivity) {

            dialog.cancel()


        }

        fun showProgessdialogAct(activity: Activity) {

            builder = android.app.AlertDialog.Builder(activity)
            builder.setCancelable(true) // if you want user to wait for some process to finish,
            builder.setView(R.layout.layout_loading_dialog)
            dialog = builder.create()
            dialog.show()


        }

        fun showProgessdialogDismissAct(activity: Activity) {

            dialog.cancel()
            dialog.dismiss()


        }

        fun showToast(context: Context?, message: String) {
 /*           if (message == currentMessage) {
                currentToast!!.cancel()
            }
            currentToast = Toast.makeText(context, message, Toast.LENGTH_LONG)

            currentToast!!.show()
            currentMessage = message*/


            val layoutInflater = LayoutInflater.from(context)

            val customToastLayout = layoutInflater.inflate(R.layout.custom_toast_layout,null)
            val customToast = Toast(context)

            val tv = customToastLayout.findViewById(R.id.textView_msg) as TextView

            tv.setText(message)
            customToast.view = customToastLayout
            customToast.setGravity(Gravity.BOTTOM,0,350)
            customToast.duration = Toast.LENGTH_SHORT
            customToast.show()


        }

        fun AlertDialog(mainActivity: MainActivity) {
            val alertDialogBuilder = androidx.appcompat.app.AlertDialog.Builder(mainActivity)
            alertDialogBuilder.setMessage("You are offline now. Please check your internet connection..")
            alertDialogBuilder.setPositiveButton(
                "Ok"
            ) { arg0, arg1 ->
                Toast.makeText(mainActivity, "You clicked yes button", Toast.LENGTH_LONG).show()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, which -> mainActivity.finish() }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }


    }
}