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
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.InputStream;

import rationalduos.atulsoori.nofucksgiven.R;


/**
 * Created by atulr on 29/06/16.
 */
public class ImageCard extends Fragment {
    SubsamplingScaleImageView imageView;
    String url;
    private LinearLayout spinner;
    private LinearLayout errorView;
    private Button retryButton;

    public void setImage(String URL) {
        new DownloadImage(spinner).execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {
        // Load card_fragment_layout and load innerView with image_card_view
        View containerView = inflater.inflate(R.layout.card_view_fragment, vg, false);
        ViewStub cardContainer = (ViewStub) containerView.findViewById(R.id.card_container);
        cardContainer.setLayoutResource(R.layout.image_card_view);
        View innerView = cardContainer.inflate();


        imageView = (SubsamplingScaleImageView) innerView.findViewById(R.id.image_card_image);
        spinner = (LinearLayout) innerView.findViewById(R.id.progressBar);
        errorView = (LinearLayout) innerView.findViewById(R.id.error_view);
        retryButton = (Button) innerView.findViewById(R.id.retry_button);

        try {
            url = getArguments().getString("imageUrl");
        } catch (Exception e) {
            url = "";
            Log.e("NFG",Log.getStackTraceString(e));
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage(url);
            }
        });

        setImage(url);
        return containerView;
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        LinearLayout spinner;
        public DownloadImage(LinearLayout spinner){
            this.spinner = spinner;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            errorView.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                Log.e("NFG",Log.getStackTraceString(e));
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try{
                imageView.setImage(ImageSource.bitmap(result));
            }catch (Exception e){
                Log.e("NFG",Log.getStackTraceString(e));
                errorView.setVisibility(View.VISIBLE);
            }
            spinner.setVisibility(View.GONE);
        }
    }
}

