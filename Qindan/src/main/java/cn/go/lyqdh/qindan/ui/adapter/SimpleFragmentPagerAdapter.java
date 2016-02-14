package cn.go.lyqdh.qindan.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.go.lyqdh.qindan.ui.module.collect.CollectFragment;
import cn.go.lyqdh.qindan.ui.module.collect.ReadChartFragment;

/**
 * Created by lyqdhgo on 2016/2/11.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"收藏", "浏览"};
    private Context context;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CollectFragment();
            case 1:
                return new ReadChartFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
