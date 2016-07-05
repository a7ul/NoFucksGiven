package rationalduos.atulsoori.nofucksgiven.cardViews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import in.uncod.android.bypass.Bypass;
import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by atulr on 29/06/16.
 */
public class MarkDownCard extends Fragment {
    TextView textView;
    StringBuffer stringBuffer;
    private LinearLayout spinner;

    public void setMarkdown(String URL) {
        new DownloadFileFromURL(spinner).execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.markdown_card_fragment_layout, vg, false);
        textView = (TextView) view.findViewById(R.id.text_content);
        spinner = (LinearLayout) view.findViewById(R.id.progressBar);
        String markdownUrl;

        try {
            markdownUrl = getArguments().getString("markdownUrl");
        } catch (Exception e) {
            markdownUrl = "";
            Log.e("NFG",Log.getStackTraceString(e));
        }

        setMarkdown(markdownUrl);

        return view;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {
        LinearLayout spinner;
        public DownloadFileFromURL(LinearLayout spinner){
            this.spinner = spinner;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                stringBuffer = new StringBuffer("#Error while getting text !");
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            Bypass bypass = new Bypass(getActivity());
            SpannedString string = new SpannedString(bypass.markdownToSpannable(stringBuffer.toString()));
            textView.setText(string);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            spinner.setVisibility(View.GONE);
        }

    }

}
