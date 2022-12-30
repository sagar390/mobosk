package attendance.netsurf.netsurfattendance.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import attendance.netsurf.netsurfattendance.models.AttendanceStatusCurrentDay;
import attendance.netsurf.netsurfattendance.R;
import attendance.netsurf.netsurfattendance.models.GetLeaveCompoffCount;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    java.util.ArrayList<GetLeaveCompoffCount.Response>
            cntleaves ;

    java.util.ArrayList<AttendanceStatusCurrentDay.Response>
            cntleavesattendance ;


    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail,ArrayList<GetLeaveCompoffCount.Response>
                                               cntleaves,ArrayList<AttendanceStatusCurrentDay.Response>
                                               cntleavesattendance) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
        this.cntleavesattendance = cntleavesattendance;
        this.cntleaves = cntleaves;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);

        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto_condensed_bold.ttf");
        expandedListTextView.setTypeface(face);


/*
            if(expandedListText.trim().equalsIgnoreCase("Pending Leaves for Approval") )
            {

                if(cntleaves.get(0).getIsLeaveApprover() == 0) {

                    expandedListTextView.setVisibility(View.GONE);
                }else
                {

                    expandedListTextView.setText(expandedListText + "  (" + cntleaves.get(0).getLeaveCount() + ")");

                }


            }else


                if(expandedListText.trim().equalsIgnoreCase("Pending Comp-Off for Approval"))

            {


                if(cntleaves.get(0).getIsLeaveApprover() == 0) {

                    expandedListTextView.setVisibility(View.GONE);
                }else
                {

                    expandedListTextView.setText(expandedListText+"  ("+cntleaves.get(0).getCompoffCount()+")");

                }


            }else
            {

                expandedListTextView.setText(expandedListText);
            }*/



        if(expandedListText.trim().equalsIgnoreCase("Pending Leaves for Approval") )
        {


                expandedListTextView.setText(expandedListText + "  (" + cntleaves.get(0).getLeaveCount() + ")");




        }else


        if(expandedListText.trim().equalsIgnoreCase("Pending Comp-Off for Approval"))

        {
                expandedListTextView.setText(expandedListText+"  ("+cntleaves.get(0).getCompoffCount()+")");



        }else
        {

            expandedListTextView.setText(expandedListText);
        }








        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);

        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/roboto_condensed_regular.ttf");
        listTitleTextView.setTypeface(face);


        if(listTitle.equalsIgnoreCase("Daily Attendance") )
        {

            if(cntleavesattendance.get(0).getIsAttendanceDone() == 0 )

            {
                listTitleTextView.setText(listTitle);
                listTitleTextView.setTextColor(context.getResources().getColor(R.color.cl_red));


            }else
            {

                listTitleTextView.setText(listTitle);
                listTitleTextView.setTextColor(context.getResources().getColor(R.color.btn__started));

            }

        }else
        {

            listTitleTextView.setText(listTitle);
            listTitleTextView.setTextColor(context.getResources().getColor(R.color.white));
        }



       // listTitleTextView.setTypeface(null, Typeface.BOLD);





        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}