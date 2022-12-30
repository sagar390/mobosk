package attendance.netsurf.netsurfattendance;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import android.widget.Toast;


public class HomeFragment extends Fragment
{


    GridView androidGridView;
    String[] web = {
            "Company Policy",
            "Health Policy",
            "Annual Leave Planner",
            "Leave Application",

            "Comp off Request",
            "Holiday List",
            "Share your Ideas",

            "PMS"



    } ;
    int[] imageId = {
            R.drawable.netsurf_logo,
            R.drawable.health_policy,
            R.drawable.netsurf_logo,
            R.drawable.netsurf_logo,

            R.drawable.netsurf_logo,
            R.drawable.netsurf_logo,
            R.drawable.netsurf_logo,

            R.drawable.netsurf_logo



    };



    @Override
    public android.view.View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, android.os.Bundle savedInstanceState) {

         android.view.View rootView = inflater.inflate(R.layout.home_grid_layout, container, false);

        androidGridView = (GridView)rootView.findViewById(R.id.gridview_android_example);



       DashboardAdapter adapter = new DashboardAdapter(getActivity(), web, imageId);

        androidGridView.setAdapter(adapter);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getActivity(), "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }



    public interface FragmentPositionListener {
        public void onClickFragment(int fragmentPosition);
    }

}
