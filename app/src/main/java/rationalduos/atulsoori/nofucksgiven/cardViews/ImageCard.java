package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.InputStream;
import java.util.ArrayList;

import rationalduos.atulsoori.nofucksgiven.R;


/**
 * Created by atulr on 29/06/16.
 */
public class ImageCard extends Fragment {
    SubsamplingScaleImageView imageView;
    String url;

    public void setImage(String URL) {
        new DownloadImage().execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_card_fragment_layout, vg, false);

        imageView = (SubsamplingScaleImageView) view.findViewById(R.id.image_card_image);

        try {
            url = getArguments().getString("imageUrl");
        } catch (Exception e) {
            url = "";
            Log.e("NFG",Log.getStackTraceString(e));
        }

        setImage(url);
        return view;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImage(ImageSource.bitmap(result));
        }
    }
}

