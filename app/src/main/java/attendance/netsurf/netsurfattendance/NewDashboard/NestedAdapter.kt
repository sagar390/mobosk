package attendance.netsurf.netsurfattendance.NewDashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.MapActivity
import attendance.netsurf.netsurfattendance.R

class NestedAdapter(act : Activity,private val mList: List<String>) :
    RecyclerView.Adapter<NestedAdapter.NestedViewHolder>() {
    private val isCheckStatus: Boolean? = null
    var childCheckboxState = HashMap<Int, Int?>()
    private var act : Activity = Activity()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.nested_item, parent, false)
        return NestedViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NestedViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        var dashboardClickListener: DashboardClickListener? = null
        dashboardClickListener = act as MainActivity2
        holder.mTv.text = mList[position]


        holder.mTv.setOnClickListener {

            val bundle = Bundle()

            if (mList[position].equals("Mark Attendance", ignoreCase = true)) {
                val intent = Intent(act, MapActivity::class.java)
                act.startActivity(intent)
            } else if (mList[position].equals("Daily Task", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Daily Task")
                bundle.putString(Constants.PAGE_ID, "1")



            } else if (mList[position].equals("Team Work", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Team Work")
                bundle.putString(Constants.PAGE_ID, "2")

            } else if (mList[position].equals("Pay Slip Search", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Pay Slip Search")
                bundle.putString(Constants.PAGE_ID, "3")



            } else if (mList[position].equals("Health / Accident policy", ignoreCase = false)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Health/Accident policy")
                bundle.putString(Constants.PAGE_ID, "4")

            } else if (mList[position].equals("Company Policy", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Company Policy")
                bundle.putString(Constants.PAGE_ID, "5")

            } else if (mList[position].equals("Apply For Leave", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Apply For Leave")
                bundle.putString(Constants.PAGE_ID, "6")

            } else if (mList[position].equals("Leave Status", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Leave Status")
                bundle.putString(Constants.PAGE_ID, "7")
            } else if (mList[position].equals("Pending Leaves for Approval", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Pending Leaves for Approval")
                bundle.putString(Constants.PAGE_ID, "8")
            } else if (mList[position].equals("Apply For Comp off", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Apply For Comp off")
                bundle.putString(Constants.PAGE_ID, "9")
            }
            else if (mList[position].equals("Comp off Status", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Comp off Status")
                bundle.putString(Constants.PAGE_ID, "10")
            }
               else if (mList[position].equals("Pending Comp-Off for Approval", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Pending Comp-Off for Approval")
                bundle.putString(Constants.PAGE_ID, "11")
            }
 else if (mList[position].equals("Holiday List", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Holiday List")
                bundle.putString(Constants.PAGE_ID, "12")
            }
 else if (mList[position].equals("Share Your Ideas", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Share Your Ideas")
                bundle.putString(Constants.PAGE_ID, "13")
            }
 else if (mList[position].equals("Change Your Password", ignoreCase = true)) {

                bundle.putString(Constants.PAGE_URL_NAME, "Change Your Password")
                bundle.putString(Constants.PAGE_ID, "14")
            }


            dashboardClickListener.onPageWEbClickedEvenet(bundle)
            /*  holder.isStatus.setOnClickListener {
            if (holder.isStatus.isChecked) {
                childCheckboxState[position] = 1
            } else {
                childCheckboxState[position] = 0
            }
            notifyDataSetChanged()
        }
        if (childCheckboxState.size > 0) {
            if (childCheckboxState[position] != null) {
                if (childCheckboxState[position] == 0) {
                    holder.isStatus.isChecked = false
                    Log.e("STATUS", "FALSE")
                } else {
                    holder.isStatus.isChecked = true
                    Log.e("STATUS", "TRUE")
                }
            }*/
            //}
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }

    init {
        this.act = act
    }
    inner class NestedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTv: TextView
    //    val isStatus: CheckBox

        init {
            mTv = itemView.findViewById(R.id.nestedItemTv)
          //  isStatus = itemView.findViewById(R.id.filterCheckBox)
        }
    }
}