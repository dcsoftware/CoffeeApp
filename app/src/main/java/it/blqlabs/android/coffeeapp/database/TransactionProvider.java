package it.blqlabs.android.coffeeapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TransactionProvider extends ContentProvider {

    public static String AUTHORITY = "it.blqlabs.android.coffeeapp.provider";
    public static final Uri TRANSACTION_URI = Uri.parse("content://" + AUTHORITY + "/transaction");

    private TransactionsDBOpenHelper mDatabaseHelper;
    private static UriMatcher sMathcer = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int TRANSACTION = 0;
    private static final int TRANSACTIONS = 1;

    static {
        sMathcer.addURI(AUTHORITY, "transaction/#", TRANSACTION);
        sMathcer.addURI(AUTHORITY, "transaction", TRANSACTIONS);
    }

    public TransactionProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new TransactionsDBOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        switch (sMathcer.match(uri)) {
            case TRANSACTIONS:
                return cupboard().withDatabase(db).query(TransactionEntity.class).
                        withProjection(projection).
                        withSelection(selection, selectionArgs).orderBy(sortOrder).getCursor();
            case TRANSACTION:
                return cupboard().withDatabase(db).query(TransactionEntity.class).
                        byId(ContentUris.parseId(uri)).getCursor();
        }
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
