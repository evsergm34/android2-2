package android2.emelyanovsergey.anroid2_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Currency;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static MyDataBaseHelper instance;

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VER = 1;
    static final String TABLE_CITY = "city";
    static final String TABLE_WEATHER = "weather";
    static final String CREATE_TABLE_CITY =
            "CREATE TABLE " + TABLE_CITY + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "CITY_NAME TEXT"+
                    ");";
    static final String CREATE_TABLE_WEATHER =
            "CREATE TABLE " + TABLE_WEATHER + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "ID_CITY INTEGER,"+
                    "WEATHER TEXT"+
                    ");";

    public static MyDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDataBaseHelper(context.getApplicationContext(),DATABASE_NAME,null,DATABASE_VER);
        }
        return instance;
    }

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY);
        sqLiteDatabase.execSQL(CREATE_TABLE_WEATHER);
    }


    // Добавить новую запись
    public City addCity(String cityName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CITY_NAME", cityName);
        // Добавление записи
        long insertId = db.insert(
                TABLE_CITY,
                null,
                values);

        City city = new City(insertId,cityName);
        return city;
    }

    public ArrayList<City> query() {
        ArrayList<City> citys = new ArrayList<City>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, CITY_NAME FROM " + TABLE_CITY + " ORDER BY CITY_NAME", null);
        try {

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                citys.add(new City(cursor.getInt(0), cursor.getString(1)));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return citys;

    }

}
