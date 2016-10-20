package com.example.asus.mycar;

/**
 * Created by ASUS on 11-10-2016.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "cardetails";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String COMPANY = "company";
    public static final String TYPE ="type";
    public static final String TORQUE="torque";
    public static final String BHP="bhp";
    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table cardetails " +
                        "(id integer primary key, name text,company text,type text, torque text,bhp text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS cardetails");
        onCreate(db);
    }

    public boolean insertContact  (String name, String company, String type, String torque,String bhp)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("company", company);
        contentValues.put("type", type);
        contentValues.put("torque", torque);
        contentValues.put("bhp", bhp);
        db.insert("cardetails", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(NAME)));
            res.moveToNext();
        }
        return array_list;
    }




    public String[] getname(String value)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> namel = new ArrayList<String>();

        Cursor cursor = db.rawQuery("select * from cardetails where NAME like %'"+value+"'%", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            namel.add(cursor.getString(1));
            cursor.moveToNext();

        }
        cursor.close();

        String[] name = new String[namel.size()];
        name = namel.toArray(name);

        return name;
    }
}