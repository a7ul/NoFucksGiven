package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by ravio on 7/10/2016.
 */
public class NothingToShowFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nothing_to_show, vg, false);
        return view;
    }
}
