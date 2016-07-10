package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import rationalduos.atulsoori.nofucksgiven.R;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;

/**
 * Created by ravio on 7/8/2016.
 */
public class AboutUsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us, vg, false);
        WebView wv = (WebView) view.findViewById(R.id.web_view_gif);
        wv.loadUrl("file:///android_asset/gif_webview.html");
        wv.setOnTouchListener(new CheckForClickTouchLister());
        return view;
    }

    private class CheckForClickTouchLister implements View.OnTouchListener {
        private final static long MAX_TOUCH_DURATION = 100;
        private long m_DownTime;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    m_DownTime = event.getEventTime(); //init time

                    break;

                case MotionEvent.ACTION_UP:
                    if (event.getEventTime() - m_DownTime <= MAX_TOUCH_DURATION) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(AppConstants.AUTHOR_URL));
                        startActivity(i);
                    }

                    break;

                default:
                    break; //No-Op

            }
            return false;
        }
    }
}
