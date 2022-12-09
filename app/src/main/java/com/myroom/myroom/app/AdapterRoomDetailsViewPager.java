package com.myroom.myroom.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.myroom.myroom.R;
import com.myroom.myroom.app.banner_pkg.BannerAdapter;
import com.myroom.myroom.app.roomdetails.RoomDetailsImage;

import java.util.ArrayList;

public class AdapterRoomDetailsViewPager  extends PagerAdapter {
    private Context mContext;
    BannerAdapter.ClicktPost clicktPost;
    private ArrayList<RoomDetailsImage> roomDetailsImages ;
    //private List<Itemblogs> datumList;

    public AdapterRoomDetailsViewPager(Context context, BannerAdapter.ClicktPost clicktPost,ArrayList<RoomDetailsImage> roomDetailsImages) {
        mContext = context;
        //  this.datumList = datumList;
        this.clicktPost = clicktPost;
        this.roomDetailsImages = roomDetailsImages;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.bannerlayoutfordetails, collection, false);
        ImageView banner = layout.findViewById(R.id.image_banner);

        if(roomDetailsImages.size()!=0)
        Glide.with(mContext).load(roomDetailsImages.get(position).getImgName()).placeholder(R.drawable.placeholder).into(banner);
     banner.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             clicktPost.clickPostListner(position);
         }
     });
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
        return roomDetailsImages.size();
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
