package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import rationalduos.atulsoori.nofucksgiven.R;
import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;


/**
 * Created by atulr on 29/06/16.
 */
public class ImageCard extends GenericCard {
    SubsamplingScaleImageView imageView;
    String url;
    Bitmap imageBitmap;
    private LinearLayout spinner;
    private LinearLayout errorView;
    private Button retryButton;

    public void setImage(String URL) {
        new DownloadImage(spinner).execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg, Bundle savedInstanceState) {

        View view = super.onCreateView(inflater, vg, savedInstanceState);
        imageBitmap = null;
        imageView = (SubsamplingScaleImageView) view.findViewById(R.id.image_card_image);
        spinner = (LinearLayout) view.findViewById(R.id.progressBar);
        errorView = (LinearLayout) view.findViewById(R.id.error_view);
        retryButton = (Button) view.findViewById(R.id.retry_button);

        try {
            url = ((CardInfo)getArguments().getParcelable("cardInfo")).getData();
        } catch (Exception e) {
            url = "";
            Log.e("NFG", Log.getStackTraceString(e));
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImage(url);
            }
        });

        setImage(url);
        return view;
    }

    @Override
    public int getCardLayoutResource() {
        return R.layout.image_card_view;
    }

    @Override
    public String getCopyString() {
        return url;
    }

    @Override
    public void shareCardData() {
        if (imageBitmap == null){
            return;
        }
        /*
        Saves image to internal files dir and use content
        provider to share image
        */
        Context context = getContext();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,AppConstants.SHARE_SUBJECT);

        /*
         Create a dir in app's internal files dir, We will give read permissions on this dir
         No other files will go here. See manifest for content provider declaration
         */
        File shareImageDir = new File(context.getFilesDir(),AppConstants.APP_DIRECTORY);
        if (!shareImageDir.exists()) {
            shareImageDir.mkdirs();
        }
        File shareImageFile = new File(shareImageDir,AppConstants.TEMP_IMG_FILE);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        try {
            shareImageFile.createNewFile();
            FileOutputStream fo = new FileOutputStream(shareImageFile);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri image_uri = FileProvider.getUriForFile(context,"io.github.bucketdevelopers",
                                                        new File(shareImageDir, AppConstants.TEMP_IMG_FILE));
        shareIntent.putExtra(Intent.EXTRA_STREAM, image_uri);

        // Get all apps that can recieve our intent and give them permissions
        List<ResolveInfo> resInfoList = context.getPackageManager()
                                    .queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, image_uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivity(Intent.createChooser(shareIntent, AppConstants.SHARE_ACTIVITY_NAME));
        context.revokeUriPermission(image_uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        LinearLayout spinner;

        public DownloadImage(LinearLayout spinner) {
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
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                InputStream input = new java.net.URL(imageURL).openStream();
                imageBitmap = BitmapFactory.decodeStream(input,null,options);
            } catch (Exception e) {
                Log.e("NFG", Log.getStackTraceString(e));
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            try {
                imageView.setImage(ImageSource.bitmap(result));
            } catch (Exception e) {
                Log.e("NFG", Log.getStackTraceString(e));
                errorView.setVisibility(View.VISIBLE);
            }
            spinner.setVisibility(View.GONE);
        }
    }
}

