package rationalduos.atulsoori.nofucksgiven;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import rationalduos.atulsoori.nofucksgiven.utils.AppConstants;
import rationalduos.atulsoori.nofucksgiven.utils.DatabaseHandler;
import rationalduos.atulsoori.nofucksgiven.utils.JsonReader;

public class LoadScreen extends AppCompatActivity {
    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        dbHandler = new DatabaseHandler(this);

        Thread loader = new Thread(new LoadRunner(this));
        loader.start();
    }

    public DatabaseHandler getDbHandler() {
        return this.dbHandler;
    }
}

class LoadRunner implements Runnable {
    LoadScreen activity;

    LoadRunner(LoadScreen actvt) {
        this.activity = actvt;
    }

    @Override
    public void run() {
        try {
            // Get index json
            JSONObject indexJson = new JsonReader(AppConstants.SERVER_URL + AppConstants.INDEX_FILE).getJson();
            downloadFile(AppConstants.SERVER_URL + AppConstants.INDEX_FILE, AppConstants.INDEX_FILE); // added by atul
            SharedPreferences settings = activity.getSharedPreferences(AppConstants.PREF_NAME, 0);

            Boolean isAppInitialized = settings.getBoolean(AppConstants.PREF_INITIALIZED, false);
            Log.d("NFG", indexJson.toString());
            verifyConfigs(indexJson, settings);
            Intent i = new Intent(activity.getApplicationContext(), MainActivity.class);
            activity.startActivity(i);
            activity.finish();
        } catch (IOException e) {
            //TODO
            // No internet connection?
            Log.e("NFG", e.getMessage());
        } catch (JSONException e) {
            //TODO
            // What's this?
            Log.e("NFG", e.getMessage());
        }
    }

    private void verifyConfigs(JSONObject indexJson, SharedPreferences settings) throws JSONException, IOException {
        String basePath = indexJson.getString(AppConstants.INDEX_BASEPATH);
        JSONArray configsArray = indexJson.getJSONArray(AppConstants.INDEX_CONFIGS);
        SharedPreferences.Editor editor = settings.edit();

        for (int i = 0; i < configsArray.length(); i++) {
            JSONObject config = configsArray.getJSONObject(i);
            String config_fname = config.getString(AppConstants.INDEX_CONFIGS_FILE);
            String config_md5 = config.getString(AppConstants.INDEX_CONFIGS_MD5);
            String cur_md5 = settings.getString(config_fname + "_md5", "00");
            Log.d("NFG", "Config: " + config_fname);
            Log.d("NFG", "Config md5: " + config_md5);
            Log.d("NFG", "Current md5: " + cur_md5);
            if (!cur_md5.equals(config_md5)) {
                downloadFile(AppConstants.SERVER_URL + basePath + config_fname, config_fname);
                editor.putString(config_fname + "_md5", config_md5);
            }
        }
        // Initialized app; Remember
        editor.putBoolean(AppConstants.PREF_INITIALIZED, true);
        editor.apply();

        //  Following block is just for testing
        for (int i = 0; i < configsArray.length(); i++) {
            JSONObject config = configsArray.getJSONObject(i);
            String config_fname = config.getString(AppConstants.INDEX_CONFIGS_FILE);
            FileInputStream fis = activity.openFileInput(config_fname);
            String raw = new JsonReader(fis).toString();
            Log.d("NFG", "CONFIG: " + config_fname + " --> " + raw);
        }
    }

    private void downloadFile(String url, String fname) throws IOException {
        Log.d("NFG", "Download file: " + fname);
        byte[] buffer = new byte[1024];
        int bytesRead;
        InputStream input = new URL(url).openStream();
        FileOutputStream output = activity.openFileOutput(fname, Context.MODE_PRIVATE);
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.close();
    }
}