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
import rationalduos.atulsoori.nofucksgiven.cardViews.ImageCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.MarkDownCard;
import rationalduos.atulsoori.nofucksgiven.cardViews.TextCard;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.CardTransformer;

import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_IMAGE;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_MARKDOWN;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT;


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

        int maxPages = (listOfCards.size() > 5) ? 5 : listOfCards.size();

        for(int i=0;i<listOfCards.size();++i){
            try {
                mDynamicPagerAdapter.addFragment(CardTransformer.cardInfoToFragment(listOfCards.get(i)));
            } catch (Exception e) {
                Log.e("NFG",Log.getStackTraceString(e));
            }
        }

        try {
            pager.setAdapter(mDynamicPagerAdapter);
//            pager.addOnPageChangeListener(new customPageChangeListener(pager, mDynamicPagerAdapter));

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

    customPageChangeListener(ViewPager pager, DynamicPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
        this.pager = pager;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("NFG", "Page Selected " + String.valueOf(position));
        int size = pagerAdapter.getCount();
        if (position + 3 < size) {
            pagerAdapter.addFragment(new ImageCard());
        }
        if (position - 3 >= 0) {
            pagerAdapter.removeFragment(pager);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}