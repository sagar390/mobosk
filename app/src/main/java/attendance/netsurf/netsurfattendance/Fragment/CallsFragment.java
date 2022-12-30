package attendance.netsurf.netsurfattendance.Fragment;


import android.content.Context;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import attendance.netsurf.netsurfattendance.models.FlowerData;
import attendance.netsurf.netsurfattendance.ui.LoginActivity;
import attendance.netsurf.netsurfattendance.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallsFragment extends Fragment {

    Toolbar mToolbar;
    RecyclerView mRecyclerView;
    private  List<FlowerData> mFlowerList;
    FlowerData mFlowerData;

    ImageView log_off;
    ImageView  bell;
    TextView username;
    TextView  remaining;
    TextView  total;
    MyAdapter myAdapter;


    public CallsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
     //   getActivity().recreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        android.view.View rootView = inflater.inflate(R.layout.layout_collapse, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        log_off = (ImageView) rootView.findViewById(R.id.id_logoff);
        bell = (ImageView) rootView.findViewById(R.id.id_bell);
        username = (TextView) rootView.findViewById(R.id.name);
        remaining = (TextView) rootView.findViewById(R.id.leaves_remain);
        total = (TextView) rootView.findViewById(R.id.leaves_remain);

        log_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.content.Intent intent = new android.content.Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });


        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout_collaps);
        AppBarLayout appBarLayout = (AppBarLayout)rootView.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Netsurf Attendance");

                    mToolbar.setVisibility(View.VISIBLE);
                    //image.setVisibility(View.INVISIBLE);
                    isShow = true;
                } else if(isShow) {
                    mToolbar.setVisibility(View.INVISIBLE);
                    // spinner_regions.setVisibility(View.VISIBLE);
                    //image.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

        mFlowerList = new ArrayList<>();
        mFlowerData = new FlowerData("Company Policy","",
                R.mipmap.companypolicy);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Health Policy", "",
                R.mipmap.healthpolicy);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Annual Leave Planner", "",
                R.mipmap.annualleaveplanner);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Leave Application", "",
                R.mipmap.leaveapplication);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Comp off Request", "",
                R.mipmap.compoffrequest);
        mFlowerList.add(mFlowerData);
        mFlowerData = new FlowerData("Holiday List", "",
                R.mipmap.holiday_ist);
        mFlowerList.add(mFlowerData);

        mFlowerData = new FlowerData("Share your Ideas", "",
                R.mipmap.share_dea);
        mFlowerList.add(mFlowerData);

        mFlowerData = new FlowerData("PMS", "",
                R.mipmap.share_dea);
        mFlowerList.add(mFlowerData);


      //   myAdapter = new MyAdapter(this, mFlowerList);
        MyAdapter myAdapter = new MyAdapter(getActivity(),mFlowerList);

        mRecyclerView.setAdapter(myAdapter);
        return rootView;
    }



    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FlowerViewHolder> {

        Context mContext;
        private List<FlowerData> mFlowerList;

        MyAdapter(Context mContext, List<FlowerData> mFlowerList) {
            this.mContext = mContext;
            this.mFlowerList = mFlowerList;
        }

        @Override
        public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false);
            return  new FlowerViewHolder(mView);
        }

        @Override
        public void onBindViewHolder(final MyAdapter.FlowerViewHolder holder, int position) {
            holder.mImage.setImageResource(mFlowerList.get(position).getFlowerImage());
            holder.mTitle.setText(mFlowerList.get(position).getFlowerName());
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              /*  Intent mIntent = new Intent(mContext, DetailActivity.class);
                mIntent.putExtra("Title", mFlowerList.get(holder.getAdapterPosition()).getFlowerName());
                mIntent.putExtra("Description", mFlowerList.get(holder.getAdapterPosition()).getFlowerDescription());
                mIntent.putExtra("Image", mFlowerList.get(holder.getAdapterPosition()).getFlowerImage());
                mContext.startActivity(mIntent);*/
                }
            });
        }

        @Override
        public int getItemCount() {
            return mFlowerList.size();
        }


        class FlowerViewHolder extends RecyclerView.ViewHolder {

            ImageView mImage;
            TextView mTitle;
            CardView mCardView;

            FlowerViewHolder(View itemView) {
                super(itemView);

                mImage = itemView.findViewById(R.id.ivImage);
                mTitle = itemView.findViewById(R.id.tvTitle);
                mCardView = itemView.findViewById(R.id.cardview);
            }
        }

    }
    }



