package it.blqlabs.android.coffeeapp.backend;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import it.blqlabs.android.coffeeapp.database.TransactionEntity;
import it.blqlabs.appengine.coffeeappbackend.myApi.MyApi;
import it.blqlabs.appengine.coffeeappbackend.myApi.model.TransactionBean;

/**
 * Created by davide on 27/10/14.
 */
public class StoreTransactionAsyncTask extends AsyncTask<Pair<Context, TransactionEntity>, Void, String> {

    private MyApi myApiService;
    private Context context;

    @Override
    protected String doInBackground(Pair<Context, TransactionEntity>... transactionEntities) {
        MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        myApiService = builder.build();


        boolean response = false;

        context = transactionEntities[0].first;
        try {

            TransactionBean transactionBean = new TransactionBean();
            transactionBean.setUserId(transactionEntities[0].second.getUserId());
            transactionBean.setId(transactionEntities[0].second.getTransactionId());
            transactionBean.setAmount(transactionEntities[0].second.getAmount());
            transactionBean.setTimestamp(transactionEntities[0].second.getTimestamp());
            response = myApiService.storeClientTransaction(transactionBean).execute().getConfirmed();


        } catch (IOException e) {
            Log.e(StoreTransactionAsyncTask.class.getSimpleName(),
                    "Error when storing tasks", e);
        }

        return String.valueOf(response);

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, "INSERT " + result, Toast.LENGTH_SHORT).show();
    }
}
