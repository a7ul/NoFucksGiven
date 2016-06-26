package rationalduos.atulsoori.nofucksgiven;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                RandomFragment randomTab = new RandomFragment();
                return randomTab;
            case 1:
                TextFragment textTab = new TextFragment();
                return textTab;
            case 2:
                ImageFragment imageTab = new ImageFragment();
                return imageTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}