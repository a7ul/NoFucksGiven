package rationalduos.atulsoori.nofucksgiven.cardViews;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import rationalduos.atulsoori.nofucksgiven.R;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;
import rationalduos.atulsoori.nofucksgiven.utils.DatabaseHandler;

/**
 * Created by ravio on 7/6/2016.
 */
public abstract class GenericCard extends Fragment {
    private String cardId = null;
    private DatabaseHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View containerView = inflater.inflate(R.layout.generic_card_fragment, vg, false);
        Button shareButton = (Button) containerView.findViewById(R.id.share_button);
        Button copyButton = (Button) containerView.findViewById(R.id.copy_button);
        ToggleButton favToggle = (ToggleButton) containerView.findViewById(R.id.fav_toggle);
        dbHandler = new DatabaseHandler(getContext());

        try {
            cardId = ((CardInfo)getArguments().getParcelable("cardInfo")).getId();
        } catch (Exception e) {
            Log.e("NFG", Log.getStackTraceString(e));
        }

        CardInfo cI = dbHandler.getFuck(cardId);

        favToggle.setChecked(false);

        if (cI.getFavourites() == 1) {
            favToggle.setChecked(true);
        }

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = getCopyString();
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager)
                            getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(text);
                } else {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager)
                            getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getActivity().getBaseContext(), AppConstants.COPY_MSG,
                        Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareCardData();
            }
        });

        favToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dbHandler.updateFuckFav(cardId, 1);
                } else {
                    dbHandler.updateFuckFav(cardId, 0);
                }
            }
        });

        int cardLayoutResource = getCardLayoutResource();
        if (cardLayoutResource != 0) {
            ViewStub innerStub = (ViewStub) containerView.findViewById(R.id.card_container);
            innerStub.setLayoutResource(cardLayoutResource);
            innerStub.inflate();
        }
        return containerView;
    }

    public abstract int getCardLayoutResource();

    public abstract String getCopyString();

    public abstract void shareCardData();

}
