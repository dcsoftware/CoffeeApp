package it.blqlabs.android.coffeeapp.backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import it.blqlabs.android.coffeeapp.Constants;
import it.blqlabs.appengine.coffeeappbackend.myApi.MyApi;
import it.blqlabs.appengine.coffeeappbackend.myApi.model.KeyBean;

/**
 * Created by davide on 11/12/14.
 */
public class GetSecureKeyAsyncTask extends AsyncTask<Context, Void, KeyBean> {

    private MyApi myApiService;
    private Context context;

    @Override
    protected KeyBean doInBackground(Context... params) {

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        myApiService = builder.build();
        context = params[0];

        String[] response = new String[2];

        KeyBean responseBean = new KeyBean();

        try {

            responseBean = myApiService.getTodayKey().execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseBean;
    }

    @Override
    protected void onPostExecute(KeyBean result) {
        SharedPreferences prefs = context.getSharedPreferences(Constants.M_SHARED_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putString(Constants.PREF_KEY_DATE, result.getDate());
        editor.putString(Constants.PREF_SECRET_KEY, result.getKey());
        editor.commit();

        Toast.makeText(context, "KEY: " + result.getKey(), Toast.LENGTH_SHORT).show();
    }
}
