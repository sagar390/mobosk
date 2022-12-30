package attendance.netsurf.netsurfattendance.NewDashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.R

class DashBoardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var mList: MutableList<PayrollItemModel>? = null
    private var adapter: ItemAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        recyclerView = findViewById(R.id.main_recyclervie)
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        mList = ArrayList<PayrollItemModel>()



        //list1
        val nestedList1: MutableList<String> = ArrayList()
        nestedList1.add("Daily Task")
        nestedList1.add("Team Work")


        val nestedList2: MutableList<String> = ArrayList()
        nestedList2.add(" Pay Sleep Search")

        val nestedList3: MutableList<String> = ArrayList()
        nestedList3.add("Health / Accident policy")
        nestedList3.add(" Company Policy")


        val nestedList4: MutableList<String> = ArrayList()
        nestedList4.add("Apply For Leave")
        nestedList4.add("Leave Status")

        val nestedList5: MutableList<String> = ArrayList()
        nestedList5.add("Apply For Comp off")
        nestedList5.add("Comp Off Status")

        val nestedList6: MutableList<String> = ArrayList()
        nestedList6.add("Holiday List")

        val nestedList7: MutableList<String> = ArrayList()
        nestedList7.add("Share Your Ideas")

        val nestedList8: MutableList<String> = ArrayList()
        nestedList8.add("Change Your Password")

        val nestedList9: MutableList<String> = ArrayList()
        nestedList9.add("Mark Attendance")


        mList!!.add(PayrollItemModel(nestedList1, "Daily Reporting"))
        mList!!.add(PayrollItemModel(nestedList2, "Payment Information"))
        mList!!.add(PayrollItemModel(nestedList3, "Policies Information"))
        mList!!.add(PayrollItemModel(nestedList4, "Leave Information"))
        mList!!.add(PayrollItemModel(nestedList5, "Comp Off Information"))
        mList!!.add(PayrollItemModel(nestedList6, "Holiday Information"))
        mList!!.add(PayrollItemModel(nestedList7, "Netsurf Innovation Cell"))
        mList!!.add(PayrollItemModel(nestedList8, "Change Password"))

       var isuserlogged =  BaseApplication.Companion.SharedPref.read(Constants.IsOutstationEmployee, 0)


        mList!!.add(PayrollItemModel(nestedList9, "Daily Attendance"))




        adapter = ItemAdapter(this,mList as ArrayList<PayrollItemModel>)
        recyclerView.setAdapter(adapter)

        /*
        List<String>  task = new ArrayList<String>();
        task.add(" Daily Task");
        task.add(" Team Report");
/*        attendance.add("Australia");
        attendance.add("England");
        attendance.add("South Africa");*/

        List<String> leaves = new ArrayList<String>();
        leaves.add("   Apply For Leave");
        leaves.add("   Leave Status");


        List<String> compoff = new ArrayList<String>();
        compoff.add("   Apply For Comp off");
        compoff.add("   Comp Off Status");


        List<String> comp_policy = new ArrayList<String>();
        comp_policy.add("   Health / Accident policy");
        comp_policy.add("   Company Policy");

        List<String> comp_innovation = new ArrayList<String>();
        comp_innovation.add("   Share Your Ideas");

        List<String> chnage_pawd = new ArrayList<String>();
        chnage_pawd.add("   Change Your Password");


        List<String> holiday_list = new ArrayList<String>();
        holiday_list.add("   Holiday List");

        List<String> pay_slip = new ArrayList<String>();
        pay_slip.add("   Pay Sleep Search");

        List<String> share_idea = new ArrayList<String>();
        share_idea.add("   Share Your Ideas");

        List<String> attendance = new ArrayList<String>();
        attendance.add("   Mark Attendance");


        expandableListDetail.put("Leave Information", leaves);
        expandableListDetail.put("Daily Reporting", task);
        expandableListDetail.put("Comp Off Information", compoff);
        expandableListDetail.put("Policies Information", comp_policy);
        expandableListDetail.put("Change Password", chnage_pawd);
        expandableListDetail.put("Holiday Information", holiday_list);
        expandableListDetail.put("Payment Information", pay_slip);
        expandableListDetail.put("Netsurf Innovation Cell", share_idea);
        expandableListDetail.put("Daily Attendance", attendance);*/
    }
}