package com.newspaper.chaudang.newspaper.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chau Dang on 7/21/2017.
 */

public class ViewPagerColorAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList = new ArrayList<>();
    private String tabTitle[] = new String[]{
            "Thời sự",
            "Góc nhìn",
            "Thế giới",
            "Kinh doanh",
            "Giải trí",
            "Thể thao",
            "Pháp luật",
            "Giáo dục",
            "Sức khỏe",
            "Gia đình",
    };

    public ViewPagerColorAdapter(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {

        return fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    //title TabLayout
    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitle[position];

    }
}
