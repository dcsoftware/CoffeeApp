package it.blqlabs.android.coffeeapp.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import it.blqlabs.appengine.coffeeappbackend.myApi.MyApi;
import it.blqlabs.appengine.coffeeappbackend.myApi.model.KeyBean;

/**
 * Created by davide on 11/12/14.
 */
public class GetSecureKeyAsyncTask extends AsyncTask<Context, Void, String> {

    private MyApi myApiService;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        myApiService = builder.build();
        context = params[0];

        String key = "";

        try {

            key = myApiService.getTodayKey().execute().getKey();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "KEY: " + result, Toast.LENGTH_SHORT).show();
    }
}
