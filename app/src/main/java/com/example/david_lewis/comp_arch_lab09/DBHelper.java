package com.example.david_lewis.comp_arch_lab09;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.StringBuilderPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-lewis on 4/12/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Define all the variables needed for a database!  Database Name, Table Name, Column Names(Attributes for the table)

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "contacts";
    public static final String CONTACT_ID = "id"; // this will be the primary key
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_PHONE = "phone";
    public static final String CONTACT_STREET = "street";
    public static final String CONTACT_EMAIL = "email";
    public static final String CONTACT_CITY = "city";

    public DBHelper(Context context)
    {
        // constructor and pass arguments to the super contructor.
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    { // let's create the table in the database now. *SQL QUERY*
        //Qeury Format below:
        /* CREATE TABLE table_Name
           (
              attribute_name1 data_type,
              attribute_name2 data_type,
              attribute_name3 data_type,
              .....
           )
           NOTE: If a constraint is needed (i.e. primary key, Not Null, foreign key, etc...)
                 tag the constraint just after you declare the data type.
                 example: attribute_name1 data_type PRIMARY KEY
         */

        StringBuilder queryBuilder = new StringBuilder(); // String builder to build the above query(could use string concat but choose not to)
        queryBuilder.append(String.format("CREATE TABLE %s\n",TABLE_NAME));
        queryBuilder.append("(");
        queryBuilder.append(String.format("%s INTEGER PRIMARY KEY,\n",CONTACT_ID));
        queryBuilder.append(String.format("%s text NOT NULL,\n",CONTACT_NAME)); // Name can't be null
        queryBuilder.append(String.format("%s text,\n",CONTACT_PHONE));
        queryBuilder.append(String.format("%s text,\n",CONTACT_STREET));
        queryBuilder.append(String.format("%s text,\n",CONTACT_EMAIL));
        queryBuilder.append(String.format("%s text\n",CONTACT_CITY)); // make sure there's no comma on the last attribute OR ERRORRR!!!!
        queryBuilder.append(")");

        // execute the query
        db.execSQL(queryBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

        db.execSQL(String.format("DROP TABLE IF EXISTS %s",TABLE_NAME));
        onCreate(db);
    }

    public boolean insertContact(String name, String phone, String street, String email, String city)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); // this is needed to insert/update a row.

        contentValues.put(CONTACT_NAME,name);
        contentValues.put(CONTACT_PHONE,phone);
        contentValues.put(CONTACT_STREET,street);
        contentValues.put(CONTACT_EMAIL,email);
        contentValues.put(CONTACT_CITY,city);

        db.insert(TABLE_NAME,null,contentValues);
        return true;
    }
    public boolean updateContact(Integer id, String name, String phone, String street, String email, String city)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTACT_NAME,name);
        contentValues.put(CONTACT_PHONE,phone);
        contentValues.put(CONTACT_STREET,street);
        contentValues.put(CONTACT_EMAIL,email);
        contentValues.put(CONTACT_CITY,city);

        db.update(TABLE_NAME,contentValues, "id = ? ",new String[]{Integer.toString(id)}); // Update contentVales WHERE id = id;
        return true;
    }

    public Cursor getContact(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorData = db.rawQuery(String.format("SELECT * FROM %s WHERE id="+id+"",TABLE_NAME),null);

        cursorData.close();
        return cursorData;
    }

    public int totalRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,TABLE_NAME); // casted to int cause function returns a long
    }

    public ArrayList<String> getAllContacts()
    {
        ArrayList<String> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery(String.format("SELECT * FROM %s",TABLE_NAME),null);

        if(cursor.moveToFirst())
        {
            do
            {
                contacts.add(cursor.getString(cursor.getColumnIndex(CONTACT_NAME)));
            }
            while(cursor.moveToNext());
        }
        cursor.close();
        return contacts;
    }

    public Integer deleteContact(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(id)});
    }


}
