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

    public void setMarkdown(String URL) {
        new DownloadFileFromURL().execute(URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup vg,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_card_fragment_layout, vg, false);
        textView = (TextView) view.findViewById(R.id.text_content);

        String markdownUrl = getArguments().getString("markdownUrl");
        setMarkdown(markdownUrl);

        return view;
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... f_url) {
            try {
                System.out.println("Downloading");
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
        }

    }

}
