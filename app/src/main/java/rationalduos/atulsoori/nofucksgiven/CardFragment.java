package rationalduos.atulsoori.nofucksgiven;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by atulr on 26/06/16.
 */
public class CardFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.card_fragment_layout, vg, false);
    }
}