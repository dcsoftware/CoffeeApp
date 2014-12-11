package it.blqlabs.android.coffeeapp;

import android.content.Context;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;
import it.blqlabs.android.coffeeapp.database.TransactionEntity;
import it.blqlabs.android.coffeeapp.database.TransactionProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HistoryFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static class TransactionAdapter extends CursorAdapter {

        private static class ViewHolder {
            private final TextView text1;
            private final TextView text2;

            public ViewHolder(View view) {
                text1 = (TextView) view.findViewById(android.R.id.text1);
                text2 = (TextView) view.findViewById(android.R.id.text2);
            }
        }


        public TransactionAdapter(Context context) {
            super(context, null);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, viewGroup, false);
            view.setTag(new ViewHolder(view));
            return view;

        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TransactionEntity transaction = cupboard().withCursor(cursor).get(TransactionEntity.class);
            // fetch the author entity too
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.text1.setText(transaction.timestamp);
            holder.text2.setText(transaction.amount);
        }
    }

    private TransactionAdapter mAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String type = "history";

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    // TODO: Rename and change types of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new TransactionAdapter(getActivity().getApplicationContext());
        setListAdapter(mAdapter);
        getLoaderManager().initLoader(0, null, this);
    }

    public String getType() {
        return type;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader = new CursorLoader(getActivity().getApplicationContext());
        loader.setUri(TransactionProvider.TRANSACTION_URI);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
