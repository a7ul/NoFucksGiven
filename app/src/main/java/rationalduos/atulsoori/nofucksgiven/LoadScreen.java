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
import java.util.ArrayList;
import java.util.List;

import rationalduos.atulsoori.nofucksgiven.models.CardInfo;
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
    private LoadScreen activity;
    private DatabaseHandler appDbHandler;

    public LoadRunner(LoadScreen actvt) {
        this.activity = actvt;
        this.appDbHandler = activity.getDbHandler();
    }

    @Override
    public void run() {
        try {
            // Get index json
            downloadFile(AppConstants.SERVER_URL + AppConstants.INDEX_FILE, AppConstants.INDEX_FILE); // added by atul
            JSONObject indexJson = new JsonReader(activity.openFileInput(AppConstants.INDEX_FILE)).getJson();
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
        String indexBasePath = indexJson.getString(AppConstants.INDEX_BASEPATH);
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
                downloadFile(AppConstants.SERVER_URL + indexBasePath + config_fname, config_fname);
                editor.putString(config_fname + "_md5", config_md5);
            }
        }
        // Initialized app; Remember
        editor.putBoolean(AppConstants.PREF_INITIALIZED, true);
        editor.apply();

        //  Following block is just for testing
        appDbHandler.deleteAllFucks();
        for (int i = 0; i < configsArray.length(); i++) {
            JSONObject config = configsArray.getJSONObject(i);
            String config_fname = config.getString(AppConstants.INDEX_CONFIGS_FILE);
            String config_name = config.getString(AppConstants.INDEX_CONFIGS_NAME);
            FileInputStream fis = activity.openFileInput(config_fname);

            JSONObject configJson = new JsonReader(fis).getJson();
            List<CardInfo> listOfCards = getListOfCardsFromJson(config_name, configJson);
            Log.d("NFG", listOfCards.toString());
            appDbHandler.addListOfFucks(listOfCards);
        }
    }

    private List<CardInfo> getListOfCardsFromJson(String config_name, JSONObject configJson) throws JSONException {

        JSONArray cardDataFromJsonList = configJson.getJSONArray(AppConstants.OTHER_CONFIGS_FILES);
        String configBasePath = configJson.getString(AppConstants.OTHER_CONFIGS_BASEPATH);

        List<CardInfo> cardInfoList = new ArrayList<>();

        for (int dataIndex = 0; dataIndex < cardDataFromJsonList.length(); ++dataIndex) {
            JSONObject cardDataFromJson = cardDataFromJsonList.getJSONObject(dataIndex);
            String type;
            String id;
            switch (config_name) {
                case "images":
                    type = AppConstants.CARD_TYPE_IMAGE;
                    id = "i" + dataIndex;
                    break;
                case "texts":
                    type = AppConstants.CARD_TYPE_MARKDOWN;
                    id = "t" + dataIndex;
                    break;
                default:
                    type = null;
                    id = null;
                    break;
            }

            try {
                cardInfoList.add(getCardInfoFromCardJsonObject(id, type, cardDataFromJson, configBasePath));
            } catch (Exception e) {
                Log.e("NFG", Log.getStackTraceString(e));
            }
        }
        return cardInfoList;
    }

    private CardInfo getCardInfoFromCardJsonObject(String id, String _type, JSONObject cardDataFromJson, String basePath) throws JSONException {

        String name = "";
        String contributor = "";
        String data = null;
        String type = _type;

        try {
            name = cardDataFromJson.getString(AppConstants.CARD_JSON_NAME);
        } catch (Exception ignored) {
        }

        try {
            contributor = cardDataFromJson.getString(AppConstants.CARD_JSON_CONTRIBUTOR);
        } catch (Exception ignored) {
        }

        try {
            data = cardDataFromJson.getString(AppConstants.CARD_JSON_TEXT);
            type = AppConstants.CARD_TYPE_TEXT;
        } catch (Exception ignored) {
        }

        try {
            data = AppConstants.SERVER_URL + basePath + cardDataFromJson.getString(AppConstants.CARD_JSON_FILE);
        } catch (Exception ignored) {
        }

        try {
            data = cardDataFromJson.getString(AppConstants.CARD_JSON_URL);
        } catch (Exception ignored) {
        }

        if (data == null || type == null || id == null) {
            throw new JSONException("Incorrect card Json data" + id + type + cardDataFromJson.toString());
        }

        return new CardInfo(id, name, contributor, type, data);
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