package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import rationalduos.atulsoori.nofucksgiven.CardFragment;
import rationalduos.atulsoori.nofucksgiven.JsonReader;
import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by atulr on 29/06/16.
 */
public class TextCard  extends Fragment{
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment_layout, vg, false);
        textView = (TextView) view.findViewById(R.id.text_content);
        Random r = new Random();
        int tt = r.nextInt();
        textView.setText("random" + tt);
        return view;
    }
}
