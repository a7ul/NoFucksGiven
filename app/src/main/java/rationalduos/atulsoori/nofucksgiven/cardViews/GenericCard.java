package rationalduos.atulsoori.nofucksgiven.cardViews;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by ravio on 7/6/2016.
 */
public abstract class GenericCard extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.card_view_fragment, vg, false);
        int cardLayoutResource = getCardLayoutResource();
        if(cardLayoutResource != 0) {
            ViewStub innerStub = (ViewStub) containerView.findViewById(R.id.card_container);
            innerStub.setLayoutResource(cardLayoutResource);
            innerStub.inflate();
        }
        return containerView;
    }

    public abstract int getCardLayoutResource();
}
