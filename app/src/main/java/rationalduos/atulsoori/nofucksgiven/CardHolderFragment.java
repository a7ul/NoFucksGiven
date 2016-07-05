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

import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_IMAGE;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_MARKDOWN;
import static rationalduos.atulsoori.nofucksgiven.utils.AppConstants.CARD_TYPE_TEXT;

public class CardHolderFragment extends Fragment {
    ArrayList<CardInfo> listOfCards;

    private ImageCard createImageCard(String Url) {
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", Url);
        ImageCard mCard = new ImageCard();
        mCard.setArguments(bundle);
        return mCard;
    }

    private MarkDownCard createMarkDownCard(String Url) {
        Bundle bundle = new Bundle();
        bundle.putString("markdownUrl", Url);
        MarkDownCard mkCard = new MarkDownCard();
        mkCard.setArguments(bundle);
        return mkCard;
    }

    private TextCard createTextCard(String textContent) {
        Bundle bundle = new Bundle();
        bundle.putString("textContent", textContent);
        TextCard tCard = new TextCard();
        tCard.setArguments(bundle);
        return tCard;
    }

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

        for (CardInfo cardInfo : listOfCards) {
            switch (cardInfo.getType()) {
                case CARD_TYPE_TEXT:
                    mDynamicPagerAdapter.addFragment(createTextCard(cardInfo.getData()));
                    break;
                case CARD_TYPE_IMAGE:
                    mDynamicPagerAdapter.addFragment(createImageCard(cardInfo.getData()));
                    break;
                case CARD_TYPE_MARKDOWN:
                    mDynamicPagerAdapter.addFragment(createMarkDownCard(cardInfo.getData()));
                    break;
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
