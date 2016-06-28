package rationalduos.atulsoori.nofucksgiven;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by atulr on 26/06/16.
 */
public class CardFragment extends Fragment {
    Button b;
    TextView t;

    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment_layout, vg, false);
        b = (Button) view.findViewById(R.id.sample_button);
        t = (TextView) view.findViewById(R.id.sample_text);
        Random r = new Random();
        int tt = r.nextInt();
        t.setText("random" + tt);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}