package rationalduos.atulsoori.nofucksgiven;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import rationalduos.atulsoori.nofucksgiven.adapters.DynamicPagerAdapter;
import rationalduos.atulsoori.nofucksgiven.cardViews.TextCard;

public class FirstPage extends Fragment {

    public static TextCard getRandomTextCard(){
        Bundle bundle = new Bundle();
        Random t = new Random();
        bundle.putString("textContent", "Random "+t.nextInt());
        TextCard tCard = new TextCard();
        tCard.setArguments(bundle);
        return tCard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_page_layout, vg, false);
        ViewPager pager = (ViewPager) view.findViewById(R.id.viewPager);

        DynamicPagerAdapter mDynamicPagerAdapter = new DynamicPagerAdapter(getActivity().getSupportFragmentManager());

        mDynamicPagerAdapter.addFragment(getRandomTextCard());
        mDynamicPagerAdapter.addFragment(getRandomTextCard());
        mDynamicPagerAdapter.addFragment(getRandomTextCard());

        if (pager != null) {
            pager.setAdapter(mDynamicPagerAdapter);
        } else {
            Log.d("NFG", "Pager not found");
        }
        return view;
    }

}
