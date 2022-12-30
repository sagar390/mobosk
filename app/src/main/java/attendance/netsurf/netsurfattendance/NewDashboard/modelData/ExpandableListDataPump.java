package attendance.netsurf.netsurfattendance.NewDashboard.modelData;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>>  getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String>  attendance = new ArrayList<String>();
        expandableListDetail.put("Attendance", attendance);

/*        attendance.add("India");
        attendance.add("Pakistan");
        attendance.add("Australia");
        attendance.add("England");
        attendance.add("South Africa");*/

        List<String> leaves = new ArrayList<String>();
        leaves.add("Apply For Leave");
        leaves.add("Leave Status");
        leaves.add("Pending Leaves for Approval");

        List<String> compoff = new ArrayList<String>();
        compoff.add("Apply For Comp off");
        compoff.add("Comp off Status");
        compoff.add("Pending Comp-Off for Approval");


        expandableListDetail.put("Daily Attendance", attendance);
        expandableListDetail.put("Leaves", leaves);
        expandableListDetail.put("Comp off", compoff);

        return expandableListDetail;
    }




    public static HashMap<String, List<String>>  getDataapprove() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

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
        expandableListDetail.put("Daily Attendance", attendance);

        return expandableListDetail;
    }



}

