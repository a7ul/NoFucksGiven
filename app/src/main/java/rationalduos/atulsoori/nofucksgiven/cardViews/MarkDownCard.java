package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import in.uncod.android.bypass.Bypass;
import rationalduos.atulsoori.nofucksgiven.R;
import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;

/**
 * Created by atulr on 29/06/16.
 */
public class MarkDownCard extends GenericCard {
    TextView textView;
    StringBuffer stringBuffer = null;
    String markdownUrl;
    private LinearLayout spinner;
    private LinearLayout errorView;
    private Button retryButton;

    public void setMarkdown(String URL) {
        new DownloadFileFromURL(spinner).execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, vg, savedInstanceState);


        textView = (TextView) view.findViewById(R.id.text_content);
        spinner = (LinearLayout) view.findViewById(R.id.progressBar);
        errorView = (LinearLayout) view.findViewById(R.id.error_view);
        retryButton = (Button) view.findViewById(R.id.retry_button);

        try {
            markdownUrl = getArguments().getString("cardContent");
        } catch (Exception e) {
            markdownUrl = "";
            Log.e("NFG", Log.getStackTraceString(e));
        }

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMarkdown(markdownUrl);
            }
        });

        setMarkdown(markdownUrl);
        return view;
    }

    @Override
    public int getCardLayoutResource() {
        return R.layout.markdown_card_view;
    }

    @Override
    public String getCopyString() {
        return textView.getText().toString();
    }

    @Override
    public void shareCardData() {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = getCopyString();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,AppConstants.SHARE_SUBJECT);
            startActivity(Intent.createChooser(sharingIntent, AppConstants.SHARE_ACTIVITY_NAME));
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        LinearLayout spinner;

        public DownloadFileFromURL(LinearLayout spinner) {
            this.spinner = spinner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            errorView.setVisibility(View.GONE);
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... f_url) {
            try {
                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader((url.openStream())));
                String inputLine = in.readLine();
                stringBuffer = new StringBuffer(inputLine);

                while ((inputLine = in.readLine()) != null) {
                    stringBuffer.append(inputLine);
                    stringBuffer.append("\n");
                }
                in.close();

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            try {
                Bypass bypass = new Bypass(getActivity());
                SpannedString string = new SpannedString(bypass.markdownToSpannable(stringBuffer.toString()));
                textView.setText(string);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
            } catch (Exception e) {
                errorView.setVisibility(View.VISIBLE);
            }
            spinner.setVisibility(View.GONE);
        }

    }

}
