package cn.go.lyqdh.qindan.View;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.go.lyqdh.qindan.Constant;

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
                return new MainFragment(Constant.MUSIC);
            case 1:
                return new MainFragment(Constant.BOOKS);
            case 2:
                return new MainFragment(Constant.TRAVEL);
            case 3:
                return new MainFragment(Constant.DESIGN);
            case 4:
                return new MainFragment(Constant.READ);
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
