package attendance.netsurf.netsurfattendance.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay
import attendance.netsurf.netsurfattendance.Constants
import attendance.netsurf.netsurfattendance.NewDashboard.BaseApplication
import attendance.netsurf.netsurfattendance.NewDashboard.ItemAdapter
import attendance.netsurf.netsurfattendance.NewDashboard.MainActivity2
import attendance.netsurf.netsurfattendance.NewDashboard.PayrollItemModel
import attendance.netsurf.netsurfattendance.R
import attendance.netsurf.netsurfattendance.databinding.FragmentHomeBinding
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount
import mylab.android.pc_app_new.CommanListofOfficiers.viewmodel.ApiViewModel

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private var mList: MutableList<PayrollItemModel>? = null
    private var adapter: ItemAdapter? = null
     var root: View? = null
    lateinit var  commanlistviewmodel: ApiViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )


        if(root ==  null)
        {
            root = binding!!.root

        }





        return root!!
    }



    fun getLeavesCount() {


        Log.d("TAG", "getLeavesCount: ")

        var login_ojb: GetLeaveCompoffCount.Request = GetLeaveCompoffCount.Request( BaseApplication.Companion.SharedPref.read(
            Constants.EmployeeId, 0).toString())


        commanlistviewmodel?.getLeaveCountApi(login_ojb).observe(requireActivity(), object :
            Observer<ArrayList<GetLeaveCompoffCount.Response>> {
            override fun onChanged(obj_account: ArrayList<GetLeaveCompoffCount.Response>) {
                if (obj_account != null) {


                    val nestedList4: MutableList<String> = ArrayList()
                    nestedList4.add("Apply For Leave")

                    val nestedList5: MutableList<String> = ArrayList()
                    nestedList5.add("Apply For Comp off")

                    if(obj_account[0].IsLeaveApprover == 0)
                    {
                        nestedList4.add("Leave Status")
                        nestedList4.add("Pending Leaves for Approval")

                        nestedList5.add("Comp off Status")
                        nestedList5.add("Pending Comp-Off for Approval")
                    }






                    //list1
                    val nestedList1: MutableList<String> = ArrayList()
                    nestedList1.add("Daily Task")
                    nestedList1.add("Team Work")


                    val nestedList2: MutableList<String> = ArrayList()
                    nestedList2.add("Pay Slip Search")

                    val nestedList3: MutableList<String> = ArrayList()
                    nestedList3.add("Health / Accident policy")
                    nestedList3.add("Company Policy")




                    val nestedList6: MutableList<String> = ArrayList()
                    nestedList6.add("Holiday List")

                    val nestedList7: MutableList<String> = ArrayList()
                    nestedList7.add("Share Your Ideas")

                    val nestedList8: MutableList<String> = ArrayList()
                    nestedList8.add("Change Your Password")

                    val nestedList0: MutableList<String> = ArrayList()
                    nestedList0.add("Mark Attendance")

                    var   isuserOutsider = 0
                    isuserOutsider =  BaseApplication.Companion.SharedPref.read(Constants.IsOutstationEmployee, 0)


                    if(isuserOutsider == 1)
                    {
                        mList!!.add(PayrollItemModel(nestedList0, "Daily Attendance"))

                    }

                    mList!!.add(PayrollItemModel(nestedList1, "Daily Reporting"))
                    mList!!.add(PayrollItemModel(nestedList2, "Payment Information"))
                    mList!!.add(PayrollItemModel(nestedList3, "Policies Information"))
                    mList!!.add(PayrollItemModel(nestedList4, "Leave Information"))
                    mList!!.add(PayrollItemModel(nestedList5, "Comp Off Information"))
                    mList!!.add(PayrollItemModel(nestedList6, "Holiday Information"))
                    mList!!.add(PayrollItemModel(nestedList7, "Netsurf Innovation Cell"))
                    mList!!.add(PayrollItemModel(nestedList8, "Change Password"))






                    adapter = ItemAdapter(requireActivity(),mList as ArrayList<PayrollItemModel>)
                    recyclerView.setAdapter(adapter)



                    //   CommanAlertDialog.showToast(this@MainActivity2,""+obj_account[0].IsLeaveApprover)


                } else {

                    //      CommanAlertDialog.showToast(this@MainActivity2,"Incorrect login credientials")


                }


            }
        })



    }





    fun getCurrentStatusAttendance() {


        Log.d("TAG", "getLeavesCount: ")

        var login_ojb: AttendanceStatusCurrentDay.Request = AttendanceStatusCurrentDay.Request( BaseApplication.Companion.SharedPref.read(
            Constants.EmployeeId, 0).toString())


        commanlistviewmodel?.getCurrentStatusApi(login_ojb).observe(requireActivity(), object :
            Observer<ArrayList<AttendanceStatusCurrentDay.Response>> {
            override fun onChanged(obj_account: ArrayList<AttendanceStatusCurrentDay.Response>) {
                if (obj_account != null) {

                    if(obj_account[0].IsAttendanceDone == 0)
                    {

                    }




                } else {



                }


            }
        })



    }

    fun loginFromMobileError() {
        commanlistviewmodel.errorMessage.observe(requireActivity(), {
            Toast.makeText(requireActivity(), "No internet connection ", Toast.LENGTH_SHORT).show()

            //  CommanAlertDialog.showProgessdialogDismissAct(this@MainActivity)

        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.main_recyclervie)

        commanlistviewmodel = ViewModelProvider(this).get(ApiViewModel::class.java)

        loginFromMobileError()

        if(MainActivity2.isUpdatePage == false)
        {        recyclerView.setHasFixedSize(true)
            recyclerView.setLayoutManager(LinearLayoutManager(requireActivity()))
            mList = ArrayList<PayrollItemModel>()

            getLeavesCount()
            MainActivity2.isUpdatePage = true

        }

    }

    override fun onResume() {
        super.onResume()

        getCurrentStatusAttendance()
    }

    override fun onDetach() {
        super.onDetach()

        MainActivity2.isUpdatePage = false
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}