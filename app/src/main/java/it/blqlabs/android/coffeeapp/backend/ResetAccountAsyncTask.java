package it.blqlabs.android.coffeeapp.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import it.blqlabs.appengine.coffeeappbackend.myApi.MyApi;
import it.blqlabs.appengine.coffeeappbackend.myApi.model.UserBean;

/**
 * Created by davide on 28/10/14.
 */
public class ResetAccountAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {

    private MyApi myApiService;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context,String>... params) {

        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        myApiService = builder.build();

        boolean response = false;
        context = params[0].first;

        try{
            UserBean user = new UserBean();
            user.setUserId(params[0].second);

            response = myApiService.clearClientHistory(user).execute().getConfirmed();
        } catch (IOException e) {
            Log.e(ResetAccountAsyncTask.class.getSimpleName(),
                    "Error when storing tasks", e);
        }

        return String.valueOf(response);

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "RESET " + result, Toast.LENGTH_SHORT).show();
    }
}
