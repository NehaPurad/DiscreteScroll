package com.example.bcs.discretescroll;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

public class PageAdapter extends PagerAdapter {

    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public PageAdapter(Context context, ArrayList<Integer> IMAGES){
        this.context=context;
        this.IMAGES= IMAGES;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
    @Override
    public int getCount() {
        return IMAGES.size();
    }


    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.imagelayout, view, false);

        assert imageLayout != null;
        final RoundedImageView imageView = (RoundedImageView) imageLayout
                .findViewById(R.id.imageview);


        imageView.setImageResource(IMAGES.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
