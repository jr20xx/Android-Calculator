package cu.lt.joe.calculator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import cu.lt.joe.calculator.models.HistoryItem;
import java.util.ArrayList;

public class HistoryDatabaseHandler extends SQLiteOpenHelper
{
    private final static String DB_NAME = "history.db";
    private final static int DB_VERSION = 1;
    private final static String HISTORY_TABLE = "HISTORY";
    private final static String ID_KEY = "ID";
    private final static String OPERATION_KEY = "OPERATION";
    private final static String RESULT_KEY = "RESULT";
    private OnDataTransactionListener data_listener;

    public HistoryDatabaseHandler(Context context, OnDataTransactionListener data_listener)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.data_listener = data_listener;
    }

    @Override
    public void onCreate(SQLiteDatabase p1)
    {
        p1.execSQL(" CREATE TABLE " + HISTORY_TABLE + 
                   "(" + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
                   + OPERATION_KEY + " TEXT, " + RESULT_KEY + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
    {}

    public long saveOperation(String operation, String result)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OPERATION_KEY, operation);
        values.put(RESULT_KEY, result);
        long returnValue = db.insert(HISTORY_TABLE, null, values);
        data_listener.onDataChanged();
        return returnValue;
    }

    public void deleteOperation(long id)
    {
        this.getWritableDatabase().delete(HISTORY_TABLE, "ID = " + id, null);
        data_listener.onDataChanged();
    }

    public ArrayList<HistoryItem> getOperationsHistory()
    {
        ArrayList<HistoryItem> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + HISTORY_TABLE + " ORDER BY " + ID_KEY + " DESC", null);
        int id = cursor.getColumnIndex(ID_KEY);
        int oid = cursor.getColumnIndex(OPERATION_KEY);
        int rid = cursor.getColumnIndex(RESULT_KEY);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            records.add(new HistoryItem(cursor.getLong(id), cursor.getString(oid), cursor.getString(rid)));
        
        cursor.close();
        db.close();
        return records;
    }

    public void clearRecords()
    {
        this.getWritableDatabase().execSQL("DELETE FROM " + HISTORY_TABLE + ";");
        data_listener.onDataChanged();
    }

    public interface OnDataTransactionListener
    {
        public void onDataChanged();
    }
}
