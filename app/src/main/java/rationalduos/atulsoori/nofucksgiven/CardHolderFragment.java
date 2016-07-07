package rationalduos.atulsoori.nofucksgiven;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eftimoff.viewpagertransformers.RotateUpTransformer;

import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.adapters.DynamicPagerAdapter;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;
import rationalduos.atulsoori.nofucksgiven.utils.CardTransformer;


public class CardHolderFragment extends Fragment {
    ArrayList<CardInfo> listOfCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_holder_fragment_layout, vg, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.viewPager);

        try {
            listOfCards = getArguments().getParcelableArrayList("cardsList");
        } catch (Exception e) {
            listOfCards = new ArrayList<>();
            Log.e("NFG", Log.getStackTraceString(e));
        }

        DynamicPagerAdapter mDynamicPagerAdapter = new DynamicPagerAdapter(getChildFragmentManager());


        for (int i = 0; (i < listOfCards.size()
//                && i < AppConstants.MAX_PAGES_ON_PAGER
        ); ++i) {
            try {
                mDynamicPagerAdapter.addFragment(CardTransformer.cardInfoToFragment(listOfCards.get(i)));
            } catch (Exception e) {
                Log.e("NFG", Log.getStackTraceString(e));
            }
        }

        try {
            pager.setAdapter(mDynamicPagerAdapter);

//            pager.addOnPageChangeListener(new customPageChangeListener(pager, mDynamicPagerAdapter, listOfCards));

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                pager.setPageTransformer(true, new RotateUpTransformer());
            }

        } catch (Exception e) {
            Log.d("NFG", Log.getStackTraceString(e));
        }
        return view;
    }

}

class customPageChangeListener implements ViewPager.OnPageChangeListener {

    DynamicPagerAdapter pagerAdapter;
    ViewPager pager;
    ArrayList<CardInfo> listOfCards;
    private int currentListOfCardsPosition = 0;
    private int previousPosition = 0;
    private int CENTER_PAGE = (AppConstants.MAX_PAGES_ON_PAGER / 2) + 1;
    private int MAX_DISTANCE_FROM_CENTER = AppConstants.MAX_PAGES_ON_PAGER - CENTER_PAGE;

    customPageChangeListener(ViewPager pager, DynamicPagerAdapter pagerAdapter, ArrayList<CardInfo> listOfCards) {
        this.pagerAdapter = pagerAdapter;
        this.pager = pager;
        this.listOfCards = listOfCards;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (previousPosition > position) {
            --currentListOfCardsPosition;
        } else {
            ++currentListOfCardsPosition;
            Log.d("NFG"," position:"+position+" currentListOfCardsPosition:"+currentListOfCardsPosition);
        }
        previousPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}