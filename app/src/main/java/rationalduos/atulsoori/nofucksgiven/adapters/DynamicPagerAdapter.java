package rationalduos.atulsoori.nofucksgiven.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by atulr on 28/06/16.
 */
public class DynamicPagerAdapter extends FragmentPagerAdapter {

    public DynamicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private long baseId = 0;
    private int currentFragmentPos;

    public int getCurrentFragmentPosition() {
        return currentFragmentPos;
    }

    public void addFragment(Fragment f) {
        fragmentsList.add(f);
        notifyDataSetChanged();
    }

    public void addFragment(Fragment f, int pos) {
        fragmentsList.add(pos, f);
        notifyDataSetChanged();
    }

    public void removeFragment(ViewPager pager) {
        fragmentsList.remove(0);
        notifyDataSetChanged();
    }

    public void removeFragment(ViewPager pager, int pos) {
        pager.setAdapter(null);
        fragmentsList.remove(pos);
        notifyDataSetChanged();
        pager.setAdapter(this);
    }

    public int getPosition(Fragment f) {
        return fragmentsList.indexOf(f);
    }

    @Override
    public Fragment getItem(int pos) {
        return fragmentsList.get(pos);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        Log.d("NFG","setPrimary item " + object.getClass().toString() );
        Log.d("NFG","setPrimary id " + container.getContext().getResources().getResourceName(container.getId()));
        Log.d("NFG1","setPrimary item " + position );
        currentFragmentPos = position;
        super.setPrimaryItem(container, position, object);
    }



}
