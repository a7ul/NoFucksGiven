package rationalduos.atulsoori.nofucksgiven.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.cardViews.ImageCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.MarkDownCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.TextCard;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;

import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_IMAGE;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_MARKDOWN;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT;

/**
 * Created by atulr on 28/06/16.
 */
public class DynamicPagerAdapter extends FragmentPagerAdapter {

    public DynamicPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private ArrayList<Fragment> fragmentsList = new ArrayList<>();
    private long baseId = 0;

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

    @Override
    public Fragment getItem(int pos) {
        return fragmentsList.get(pos);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

}
