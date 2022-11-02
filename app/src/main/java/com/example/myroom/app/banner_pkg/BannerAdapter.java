package com.example.myroom.app.banner_pkg;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myroom.R;
import com.example.myroom.app.home.RoomDetailsData;
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    ClicktPost clicktPost;
    ArrayList<RoomDetailsData> roomDetailsData = new ArrayList<>();
    //private List<Itemblogs> datumList;

    public BannerAdapter(Context context,ArrayList<RoomDetailsData> roomDetailsData ,ClicktPost clicktPost) {
        mContext = context;
      //  this.datumList = datumList;
        this.clicktPost = clicktPost;
        this.roomDetailsData = roomDetailsData;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.banner_layout, collection, false);
        ImageView  roomImage = layout.findViewById(R.id.image_banner);
        TextView  tv_title = layout.findViewById(R.id.tv_title);

        if(roomDetailsData.size()!=0)
            Glide.with(mContext).load(roomDetailsData.get(position).getImages().get(0).getImgName()).into(roomImage);

        collection.addView(layout);


        return layout;
    }
    public interface  ClicktPost
    {
        public  void  clickPostListner(int pos);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        //  return 3;
        return roomDetailsData.size()==0?1:roomDetailsData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

 /*   @Override
    public CharSequence getPageTitle(int position) {
        return slangList.get(position);
    }
*/
}