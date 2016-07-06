package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by atulr on 29/06/16.
 */
public class TextCard extends GenericCard {
    TextView textView;
    ViewStub cardContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater,vg,savedInstanceState);
        textView = (TextView) view.findViewById(R.id.text_content);

        try {
            textView.setText(getArguments().getString("textContent"));
        } catch (Exception e) {
            Log.e("NFG",Log.getStackTraceString(e));
            textView.setText(R.string.no_text);
        }

        return view;
    }

    @Override
    public int getCardLayoutResource(){
        return R.layout.text_card_view;
    }

    @Override
    public String getCopyString() {
        return textView.getText().toString();
    }
}
