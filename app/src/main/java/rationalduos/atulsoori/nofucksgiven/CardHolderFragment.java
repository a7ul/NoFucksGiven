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
import rationalduos.atulsoori.nofucksgiven.utils.CardTransformer;
import rationalduos.atulsoori.nofucksgiven.utils.HackyViewPager;


public class CardHolderFragment extends Fragment {
    ArrayList<CardInfo> listOfCards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_holder_fragment_layout, vg, false);
        HackyViewPager pager = (HackyViewPager) view.findViewById(R.id.viewPager);

        try {
            listOfCards = getArguments().getParcelableArrayList("cardsList");
        } catch (Exception e) {
            listOfCards = new ArrayList<>();
            Log.e("NFG", Log.getStackTraceString(e));
        }

        DynamicPagerAdapter mDynamicPagerAdapter = new DynamicPagerAdapter(getChildFragmentManager());

        for (int i = 0; (i < listOfCards.size()); ++i) {
            try {
                mDynamicPagerAdapter.addFragment(CardTransformer.cardInfoToFragment(listOfCards.get(i)));
            } catch (Exception e) {
                Log.e("NFG", Log.getStackTraceString(e));
            }
        }

        try {
            pager.setAdapter(mDynamicPagerAdapter);

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                pager.setPageTransformer(true, new RotateUpTransformer());
            }

        } catch (Exception e) {
            Log.d("NFG", Log.getStackTraceString(e));
        }
        return view;
    }

}
