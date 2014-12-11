package it.blqlabs.android.coffeeapp.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by davide on 24/10/14.
 */
public class TransactionsDBOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "coffeeapp.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    static {
        cupboard().register(TransactionEntity.class);
    }

    public TransactionsDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
        db = sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }

    public void reset () throws SQLException {
        db = this.getWritableDatabase ();
        db.execSQL ("delete from TransactionEntity");
    }
}
