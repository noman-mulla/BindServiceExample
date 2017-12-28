package mmulla4.project2.uic.edu.fedcash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noman on 04/12/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    Context mContext;
    /* DATABASE VERSION */
    private static final int database_VERSION = 1;
    /* DATABASE NAME*/
    private static final String database_NAME = "ApiResultsDB";


    private static final String CREATE_API_TABLE = "Create table api_table (" + " api_name TEXT," +
            "api_values Text, " +
            "api_result Text )";

    private static final String TABLE_NAME = "api_table";
    private static final String API_NAME = "api_name";
    private static final String API_VALUES = "api_values";
    private static final String API_RESULT = "api_result";


    public DatabaseHandler(Context context) {
        super(context, database_NAME, null, database_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_API_TABLE);

    }

    public void insertRow(ApiResult apiResult) {
        SQLiteDatabase db = this.getWritableDatabase();
        // make values to be inserted
        ContentValues values = new ContentValues();
        values.put(API_NAME, apiResult.getApiCall());
        values.put(API_VALUES, apiResult.getApiValues());
        values.put(API_RESULT, apiResult.getApiResult());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<ApiResult> getAllResults() {

        List<ApiResult> apiResults = new ArrayList<>();
        // select table query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // get reference of the BookDB database
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor == null) {
            Log.i("testing", "cursor is null");
        } else {
            Log.i("testing", "cursor is  not null");
        }

        ApiResult apiResult = null;
        if (cursor.moveToFirst()) {
            Log.i("testing", "moveToFirst");
            do {
                // Log.i("testing","do loop "+cursor.getString(1));
                apiResult = new ApiResult();
                apiResult.setApiCall(cursor.getString(0));
                apiResult.setApiValues(cursor.getString(1));
                apiResult.setApiResult(cursor.getString(2));
                apiResults.add(apiResult);

            } while (cursor.moveToNext());
        }
        return apiResults;
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
