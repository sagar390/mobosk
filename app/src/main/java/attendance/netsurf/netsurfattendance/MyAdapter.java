package attendance.netsurf.netsurfattendance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import attendance.netsurf.netsurfattendance.models.FlowerData;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.FlowerViewHolder> {

     Context mContext;
     int cnutner;
    private List<FlowerData> mFlowerList;

    MyAdapter(Context mContext,int cnutner, List<FlowerData> mFlowerList) {
        this.mContext = mContext;
        this.mFlowerList = mFlowerList;
        this.cnutner = cnutner;
    }

    @Override
    public FlowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_header, parent, false);
        return new FlowerViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final FlowerViewHolder holder, final int position) {
        holder.mImage.setImageResource(mFlowerList.get(position).getFlowerImage());
        holder.mTitle.setText(mFlowerList.get(position).getFlowerName()+" ");



        if(mFlowerList.get(position).getFlowerName().equalsIgnoreCase("Attendance"))

        {



            holder.mCardView.getBackground().setTint(Color.GREEN);
            holder.mTitle.setTextColor(Color.WHITE);


        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mFlowerList.get(position).getFlowerName().equalsIgnoreCase("Attendance"))

                {

                    Intent mIntent = new Intent(mContext, MapActivity.class);

                    mContext.startActivity(mIntent);

                }else
                {

                    Intent mIntent = new Intent(mContext, LandingPageActivity.class);

                    mContext.startActivity(mIntent);
                }

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