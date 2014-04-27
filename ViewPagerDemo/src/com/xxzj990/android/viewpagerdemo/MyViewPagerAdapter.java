package com.xxzj990.android.viewpagerdemo;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyViewPagerAdapter extends PagerAdapter {

    private List<View> mChilds;

    public MyViewPagerAdapter(List<View> mChilds) {
        this.mChilds = mChilds;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mChilds.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mChilds.get(position));
        return mChilds.get(position);
    }

    @Override
    public int getCount() {
        return mChilds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
