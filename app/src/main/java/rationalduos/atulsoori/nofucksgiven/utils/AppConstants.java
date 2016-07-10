package rationalduos.atulsoori.nofucksgiven.utils;

import android.graphics.Color;

import rationalduos.atulsoori.nofucksgiven.R;

/**
 * Created by ravio on 6/26/2016.
 */
public class AppConstants {

    /* Server stuff */
    public static String SERVER_URL = "https://bucketdevelopers.github.io/NFGAssets/";
    public static String INDEX_FILE = "index.json";

    /* Index json labels */
    public static String INDEX_BASEPATH = "basePath";
    public static String INDEX_CONFIGS = "configs";
    public static String INDEX_CONFIGS_NAME = "name";
    public static String INDEX_CONFIGS_FILE = "file";
    public static String INDEX_CONFIGS_MD5 = "md5";

    /* Other Config labels */
    public static String OTHER_CONFIGS_FILES = "files";
    public static String OTHER_CONFIGS_BASEPATH = "basePath";

    /* Other JSONCardData labels */
    public static String CARD_JSON_NAME = "name";
    public static String CARD_JSON_TEXT = "text";
    public static String CARD_JSON_FILE = "file";
    public static String CARD_JSON_URL = "url";
    public static String CARD_JSON_CONTRIBUTOR = "contributor";
    public static String CARD_JSON_ID = "id";
    public static String CARD_JSON_FAVOURITE = "favourite";

    /* Shared preference stuff */
    public static String PREF_NAME = "noFucksPreference";
    public static String PREF_INITIALIZED = "is_initialized";

    /*Card Holder Settings*/
    public static int MAX_PAGES_ON_PAGER = 5; //SHOULD BE AN ODD NUMBER GREATER THAN OR EQUAL TO 5

    /* Card Types */
    public static final int CARD_TYPE_INVALID = -1;
    public static final int CARD_TYPE_IMAGE = 1;
    public static final int CARD_TYPE_TEXT = 2;
    public static final int CARD_TYPE_TEXT_URL = 3;
    public static final int CARD_TYPE_ABOUT_US = 4;

    /* Card View related */
    public static String COPY_MSG = "Copied to Clipboard";
    public static String SHARE_ACTIVITY_NAME = "Share on";
    public static String SHARE_SUBJECT = "Shared using NoFucksGiven";

    /* Share image related */
    public static String APP_DIRECTORY = "noFucksDir";
    public static String TEMP_IMG_FILE = "temp_share_img.jpg";

    /* Launch screen internet messages */
    public static String NO_INTERNET_WARN = "No Internet. Only text cards are available.";
    public static String NO_INTERNET_ERROR = "First time usage requires Internet connectivity";

    /* Navigation drawer */
    public final static String NAVIGATION_GENERAL = "Random";
    public final static String NAVIGATION_IMAGES = "Images";
    public final static String NAVIGATION_TEXTS = "Text";
    public final static String NAVIGATION_FAVOURITES = "Favourites";
    public final static String NAVIGATION_ABOUT_US = "About us";
    public final static String[] NAVIGATION_ITEMS = {
            NAVIGATION_GENERAL,
            NAVIGATION_TEXTS,
            NAVIGATION_IMAGES,
            NAVIGATION_FAVOURITES,
            NAVIGATION_ABOUT_US
    };
    public final static int NAVIGATION_ICON_GENERAL = R.drawable.ic_apps_black_24dp;
    public final static int NAVIGATION_ICON_IMAGES = R.drawable.ic_image_black_24dp;
    public final static int NAVIGATION_ICON_TEXTS = R.drawable.ic_text_format_black_24dp;
    public final static int NAVIGATION_ICON_FAVOURITES = R.drawable.ic_favorite_black_24dp;
    public final static int NAVIGATION_ICON_ABOUT_US = R.drawable.ic_info_black_24dp;
    public final static int[] NAVIGATION_ITEMS_ICONS = {
            NAVIGATION_ICON_GENERAL,
            NAVIGATION_ICON_TEXTS,
            NAVIGATION_ICON_IMAGES,
            NAVIGATION_ICON_FAVOURITES,
            NAVIGATION_ICON_ABOUT_US
    };
}
