package com.michelbarbosa.liveon.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.michelbarbosa.liveon.R;
import com.michelbarbosa.liveon.data.entities.ImageVehicleEntity;

import java.util.List;

public class VehicleImageAdapter extends PagerAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<ImageVehicleEntity> mItens;

    public VehicleImageAdapter(Context context, List<ImageVehicleEntity> resources) {
        mContext = context;
        mItens = resources;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItens != null ? mItens.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_vehicle, container, false);
        setViews(itemView, position);
        container.addView(itemView);
        return itemView;
    }

    private void setViews(View itemView, int position) {
        ImageView ivVehicle = itemView.findViewById(R.id.iv_image_vehicle);
        Glide.with(mContext)
                .load(mItens.get(position).getImageUrl())
                .into(ivVehicle);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout) object);
    }

}
