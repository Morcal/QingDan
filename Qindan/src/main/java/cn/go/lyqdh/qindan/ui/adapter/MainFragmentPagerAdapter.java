package cn.go.lyqdh.qindan.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.go.lyqdh.qindan.app.Constant;
import cn.go.lyqdh.qindan.ui.module.main.MainFragment;

/**
 * Created by lyqdhgo on 2016/2/13.
 */
public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"音乐", "书单", "旅行", "设计", "阅读"};
    private Context context;

    public MainFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MainFragment musicFragment = new MainFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", Constant.MUSIC);
                musicFragment.setArguments(bundle);
                return musicFragment;
            case 1:
                MainFragment bookFragment = new MainFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("type", Constant.BOOKS);
                bookFragment.setArguments(bundle1);
                return bookFragment;
            case 2:
                MainFragment travelFragment=new MainFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", Constant.TRAVEL);
                travelFragment.setArguments(bundle2);
                return travelFragment;
            case 3:
                MainFragment designFragment=new MainFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("type", Constant.DESIGN);
                designFragment.setArguments(bundle3);
                return designFragment;
            case 4:
                MainFragment readFragment=new MainFragment();
                Bundle bundle4 = new Bundle();
                bundle4.putString("type", Constant.READ);
                readFragment.setArguments(bundle4);
                return readFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}
