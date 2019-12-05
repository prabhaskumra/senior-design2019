package com.example.searchscreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.viewpager.widget.PagerAdapter;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds = new int[] {R.drawable.tut1,R.drawable.tut2,R.drawable.tut3,R.drawable.tut4,R.drawable.tut5,R.drawable.tut6,R.drawable.tut7,R.drawable.tut8,R.drawable.tut9,R.drawable.tut10,R.drawable.tut11,R.drawable.tut12}; //List of pictures for tutorial

    ImageAdapter(Context context){
        mContext = context;

    }

    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){
        ImageView imageView= new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(mImageIds[position]);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView)object);
    }
}
