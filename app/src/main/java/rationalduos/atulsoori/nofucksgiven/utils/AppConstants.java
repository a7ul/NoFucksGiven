package rationalduos.atulsoori.nofucksgiven.utils;

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
    public static final String CARD_TYPE_IMAGE = "image";
    public static final String CARD_TYPE_TEXT = "text";
    public static final String CARD_TYPE_MARKDOWN = "markdown";

    /* Card View related */
    public static String COPY_MSG = "Copied to Clipboard";
    public static String SHARE_ACTIVITY_NAME = "Share on";
    public static String SHARE_SUBJECT = "Shared using NoFucksGiven";

    public static String APP_DIRECTORY = "noFucksDir";
    public static String TEMP_IMG_FILE = "temp_share_img.jpg";
}
