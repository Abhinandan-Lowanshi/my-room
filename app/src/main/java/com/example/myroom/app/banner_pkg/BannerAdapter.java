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
import com.makeramen.roundedimageview.RoundedImageView;


import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private Context mContext;
    ClicktPost clicktPost;
    //private List<Itemblogs> datumList;

    public BannerAdapter(Context context,ClicktPost clicktPost) {
        mContext = context;
      //  this.datumList = datumList;
        this.clicktPost = clicktPost;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.banner_layout, collection, false);


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
        return 3;
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