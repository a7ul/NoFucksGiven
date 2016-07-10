package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by ravio on 7/8/2016.
 */
public class AboutUsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, vg, false);
        WebView wv = (WebView) view.findViewById(R.id.web_view_gif);
        wv.loadUrl("file:///android_asset/gif_webview.html");
        return view;
    }
}
