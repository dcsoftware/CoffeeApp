package it.blqlabs.android.coffeeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MainFragment extends Fragment {

    private TextView textView, statusTextView, creditTextView, logTextView;
    private SharedPreferences cardSetting;
    private String userCredit = "";
    private String type = "card";


    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View v = inflater.inflate(R.layout.fragment_main, container, false);
        View v = inflater.inflate(R.layout.fragment_credit, container, false);
        if(v != null) {
            cardSetting = MainActivity.getContext().getSharedPreferences(Constants.USER_SHARED_PREF, Context.MODE_PRIVATE);
            userCredit = cardSetting.getString(Constants.USER_CREDIT, "");
            statusTextView = (TextView) v.findViewById(R.id.statusTV);
            statusTextView.setText("Waiting connection...");
            creditTextView = (TextView) v.findViewById(R.id.creditTV);
            creditTextView.setText("€ " + userCredit);
            logTextView = (TextView) v.findViewById(R.id.logTV);
            logTextView.setMovementMethod(new ScrollingMovementMethod());
            //textView = (TextView) v.findViewById(R.id.textView);
            //textView.setMovementMethod(new ScrollingMovementMethod());
            //textView.setText("Waiting...");
        }
        return  v;
    }

    public String getType() {
        return type;
    }

    public void updateLogText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //textView.append("\n" + text);
                logTextView.append("\n" + text);
            }
        });

    }

    public void updateCreditText(final String credit) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                creditTextView.setText("€ " + credit);
            }
        });
    }

    public void updateStatusText(final String status) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                statusTextView.setText(status);
            }
        });
    }

}
