package it.blqlabs.android.coffeeapp;

/**
 * Created by davide on 05/08/14.
 */
public class Constants {

    public static final String USER_SHARED_PREF = "userSharedPref";
    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_CREDIT = "userCredit";
    public static final String USER_ID = "userId";
    public static final String GCM_SENDER_ID = "724341685796";
    public static final String PREF_KEY_DATE = "key_date";
    public static final String PREF_SECRET_KEY = "secret_key";

    public static final String M_SHARED_PREF = "mSharedPref";
    public static final String IS_FIRST_RUN = "isFirstRun";
    public static final String OTP_KEY = "abcdefghilmnopqrstuvz";

    public static final String WEB_CLIENT_ID = "824332171015-s7c5iqhlgki64gdcs19ne4ssnn9adtrg.apps.googleusercontent.com";
    public static final String ANDROID_ID = "824332171015-dnnfuu28nvcud4rs9pjome3a6n50sv5o.apps.googleusercontent.com";
    public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
    public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

    public static enum State {
        DISCONNECTED("disconnected", 0), CONNECTED("connected", 1), APP_SELECTED("app_selected", 2), AUTHENTICATED("authenticated", 3),
        LOGGED_IN("logged_in", 4), READING_STATUS("reading_status", 5), DATA_UPDATED("data_updated", 6),
        RELEASED("released", 7);

        private String stringValue;
        private int intValue;

        private State(String s, int i) {
            stringValue = s;
            intValue = i;
        }

        @Override
        public String toString() {
            return stringValue;
        }
    }

}
