package attendance.netsurf.netsurfattendance;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

import attendance.netsurf.netsurfattendance.models.DataModel;


/**
 * Created by Sagar on 26-06-2018.
 */

public class DrawerListAdapter {/*extends ArrayAdapter<DataModel> {
{

    private Activity activity = null;
    private ArrayList<DataModel> data = new ArrayList<>();

    private LayoutInflater layoutInflater = null;
    private int resourceId = 0;
    private  int coreTypeId;


    public DrawerListAdapter(Activity activity, int resourceId, ArrayList<DataModel> data) {

        super(activity, resourceId, data);

        if (activity != null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            this.data = data;
            this.resourceId = resourceId;
            this.activity = activity;
        }
    }





    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        DataModel drawerModel = data.get(position);

        if (convertView == null) {
            convertView = layoutInflater.inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.textViewTitle = (TextView) convertView.findViewById(R.id
                    .txt_drawer_list_title);
            viewHolder.imageViewIcon = (ImageView) convertView.findViewById(R.id
                    .img_drawer_list_icon);
            viewHolder.imageViewArrow = (ImageView) convertView.findViewById(R.id
                    .img_drawer_list_arrow);
            viewHolder.relativeLayoutImage = (RelativeLayout) convertView.findViewById(R.id
                    .rel_img);
            viewHolder.notification = (TextView) convertView.findViewById(R.id
                    .text);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.textViewTitle.setText(drawerModel.getTitle());
        viewHolder.imageViewIcon.setImageDrawable(activity.getResources().getDrawable(drawerModel
                .getImageId()));


        if (!drawerModel.isSelected()) {
            viewHolder.relativeLayoutImage.setBackgroundColor(activity.getResources().getColor
                    (drawerModel
                            .getColorId()));
            viewHolder.textViewTitle.setBackgroundColor(activity.getResources().getColor(android
                    .R.color.white));
            viewHolder.textViewTitle.setTextColor(activity.getResources().getColor(android
                    .R.color.black));
            viewHolder.imageViewArrow.setImageDrawable(activity.getResources().getDrawable(R
                    .drawable.ic_arrow_right));

        } else {
            viewHolder.relativeLayoutImage.setBackgroundColor(activity.getResources().getColor(R
                    .color.drawer_selected_color));
            viewHolder.textViewTitle.setBackgroundColor(activity.getResources().getColor(R.color
                    .drawer_selected_color));
            viewHolder.imageViewArrow.setImageDrawable(activity.getResources().getDrawable(R
                    .drawable.ic_arrow_right));
            viewHolder.textViewTitle.setTextColor(activity.getResources().getColor(android
                    .R.color.white));
        }
        return convertView;
    }

    private static class ViewHolder {
        private RelativeLayout relativeLayoutImage;
        private TextView textViewTitle;
        private ImageView imageViewIcon;
        private ImageView imageViewArrow;
        private TextView notification;
    }*/
}