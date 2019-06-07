package android2.emelyanovsergey.anroid2_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private static MyDataBaseHelper instance;

    private static final String DATABASE_NAME = "weather.db";
    private static final int DATABASE_VER = 4;
    static final String TABLE_CITY = "city";
    static final String CREATE_TABLE_CITY =
            "CREATE TABLE " + TABLE_CITY + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "CITY_NAME TEXT," +
                    "WEATHER TEXT," +
                    "UP_TO_DATE TEXT" +
                    ");";

    public static MyDataBaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MyDataBaseHelper(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VER);
        }
        return instance;
    }

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d("TAG", "oldVersion=" + oldVersion + "; newVersion=" + newVersion);
        if ((oldVersion < newVersion)) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
            sqLiteDatabase.execSQL(CREATE_TABLE_CITY);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CITY);

    }

    //обновить запись в DB
    public City updCity(City city) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        String upDateTime = sdf.format(new Date());
        city.setUpToDate(upDateTime);

        SQLiteDatabase db = getWritableDatabase();
        if (city.getID() == 0) {
            ContentValues values = new ContentValues();
            values.put("CITY_NAME", city.getName());
            values.put("WEATHER", city.getWeather());
            values.put("UP_TO_DATE", city.getUpToDate());
            // Добавление записи
            long insertId = db.insert(
                    TABLE_CITY,
                    null,
                    values);
            city.setID(insertId);
        } else {
            db.execSQL("UPDATE " + TABLE_CITY + " SET WEATHER='" + city.getWeather() + "', UP_TO_DATE='" + city.getUpToDate() + "' WHERE ID=" + city.getID());
        }

        return city;
    }

    public void delCity(City city) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CITY + " WHERE ID=" + city.getID());
    }

    public City getCity(String cityName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, CITY_NAME, WEATHER FROM " + TABLE_CITY + " where CITY_NAME='" + cityName + "'", null);
        City city = new City(0, cityName);

        try {

            if (cursor.moveToFirst()) {
                city.setID(cursor.getInt(0));
                city.setWeather(cursor.getString(2));
            } else {
                return new City(0, cityName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return city;
    }

    public ArrayList<City> query() {
        ArrayList<City> citys = new ArrayList<City>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID, CITY_NAME, WEATHER, UP_TO_DATE FROM " + TABLE_CITY + " ORDER BY CITY_NAME", null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                citys.add(new City(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
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
