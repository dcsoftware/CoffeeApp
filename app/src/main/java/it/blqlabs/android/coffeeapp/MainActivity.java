package it.blqlabs.android.coffeeapp;


import android.nfc.tech.Ndef;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.blqlabs.android.coffeeapp.backend.GetSecureKeyAsyncTask;
import it.blqlabs.android.coffeeapp.backend.ResetAccountAsyncTask;
import it.blqlabs.android.coffeeapp.database.TransactionsDBOpenHelper;


public class MainActivity extends FragmentActivity implements CardReader.ActionCallback{

    public static final String TAG = "MainActivity";
    public CardReader reader;
    public MainFragment fragment;

    private static MainActivity mMainActivity;
    private static Tag mTag;
    private static Context mContext;
    SharedPreferences mSharedPref;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");

    public Messenger mMessenger = new Messenger(new MessageHandler(this));
    private class MessageHandler extends Handler {
        private Context c;

        MessageHandler(Context c){
            this.c = c;
        }
        @Override
        public void handleMessage(Message msg) {
            onActionReceived(msg.obj.toString());
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = new MainFragment();
        mSharedPref = getSharedPreferences(Constants.M_SHARED_PREF, MODE_PRIVATE);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mMainActivity = this;
        mContext = this.getApplicationContext();
        isFirstRun();
        getSecureKey();
    }

    public void getSecureKey() {

        String storedDate = mSharedPref.getString(Constants.PREF_KEY_DATE, "00000000");
        String currentDate = format.format(c.getTime());

        if(!storedDate.equals(currentDate)) {
            Toast.makeText(this, "GETTING KEY", Toast.LENGTH_SHORT).show();
            new GetSecureKeyAsyncTask().execute(this);
        } else {
            Toast.makeText(this, "KEY already stored:" + mSharedPref.getString(Constants.PREF_SECRET_KEY, "00000000"), Toast.LENGTH_SHORT).show();
        }
    }

    public static MainActivity getMainActivity() {
        return mMainActivity;
    }

    public void isFirstRun() {
        if (mSharedPref.getBoolean(Constants.IS_FIRST_RUN, true)) {

            SharedPreferences userSharedPref = getSharedPreferences(Constants.USER_SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor userPrefEditor = userSharedPref.edit();
            userPrefEditor.putString(Constants.USER_NAME, "Davide");
            userPrefEditor.putString(Constants.USER_SURNAME, "Corradini");
            userPrefEditor.putString(Constants.USER_CREDIT, "0");
            userPrefEditor.putString(Constants.USER_ID, "123456789");
            userPrefEditor.commit();

            SharedPreferences.Editor mPrefEditor = mSharedPref.edit();
            mPrefEditor.putBoolean(Constants.IS_FIRST_RUN, false);
            mPrefEditor.commit();
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    public void onResume() {
        super.onResume();
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    private void processIntent(Intent intent) {
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Log.i(TAG, "tech discovered");
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            if(mTag != null) {
                Log.i(TAG, "Tag found");
                /*reader = new CardReader(this, mTag, this);

                reader.StartTransaction();*/
                //reader.Authenticate();
                Intent serv = new Intent(MainActivity.this, ComService.class);
                startService(serv);
            }

        }
    }

    public static Tag getTag() {
        return mTag;
    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onActionReceived(String text) {
        fragment.updateLogText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_reset) {
            SharedPreferences cardSetting = getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE);
            cardSetting.edit().putString(Constants.USER_CREDIT, "0").commit();
            new TransactionsDBOpenHelper(getContext()).reset();
            new ResetAccountAsyncTask().execute(new Pair<Context, String>(this, mSharedPref.getString(Constants.USER_ID,"")));
            return true;
        }
        if (id == R.id.action_history) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new HistoryFragment()).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        private String type = "main";

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            SharedPreferences cardSetting = getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE);

            View rootView = inflater.inflate(R.layout.fragment_start, container, false);
            TextView text = (TextView) rootView.findViewById(R.id.creTV);
            text.setText(cardSetting.getString(Constants.USER_CREDIT, ""));
            return rootView;
        }

        public String getType() {
            return type;
        }
    }
}
