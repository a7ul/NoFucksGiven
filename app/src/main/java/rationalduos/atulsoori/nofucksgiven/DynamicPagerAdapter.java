package rationalduos.atulsoori.nofucksgiven;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

/**
 * Created by atulr on 28/06/16.
 */
public class DynamicPagerAdapter extends FragmentPagerAdapter {

    public DynamicPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    private ArrayList<Fragment> fragmentsList = new ArrayList<>();

    public void addFragment(Fragment f){
        fragmentsList.add(f);
        notifyDataSetChanged();
    }

    public void removeFragment(ViewPager pager, int pos){
        pager.setAdapter(null);
        fragmentsList.remove(pos);
        notifyDataSetChanged();
        pager.setAdapter(this);
    }

    @Override
    public Fragment getItem(int pos) {
        return fragmentsList.get(pos);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
}
