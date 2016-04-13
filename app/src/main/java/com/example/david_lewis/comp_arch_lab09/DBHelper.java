package com.example.david_lewis.comp_arch_lab09;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.design.widget.TabLayout;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by david-lewis on 4/12/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    //Define all the variables needed for a database!  myDb Name, Table Name, Column Names(Attributes for the table)

    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String TABLE_NAME = "contacts";
    public static final String CONTACT_ID = "id"; // this will be the primary key
    public static final String CONTACT_NAME = "name";
    public static final String CONTACT_PHONE = "phone";
    public static final String CONTACT_STREET = "street";
    public static final String CONTACT_EMAIL = "email";
    public static final String CONTACT_CITY = "city";


    // These values will hold the data coming from the database to update Contact Object
    private int id;
    private String name;
    private String phone;
    private String street;
    private String email;
    private String city;

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

    public boolean insertContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues(); // this is needed to insert/update a row.

        contentValues.put(CONTACT_NAME,contact.getName());
        contentValues.put(CONTACT_PHONE,contact.getPhone());
        contentValues.put(CONTACT_STREET,contact.getStreet());
        contentValues.put(CONTACT_EMAIL,contact.getEmail());
        contentValues.put(CONTACT_CITY,contact.getCity());

        db.insert(TABLE_NAME,null,contentValues);
        db.close();
        return true;
    }
    public boolean updateContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CONTACT_NAME,contact.getName());
        contentValues.put(CONTACT_PHONE,contact.getPhone());
        contentValues.put(CONTACT_STREET,contact.getStreet());
        contentValues.put(CONTACT_EMAIL,contact.getEmail());
        contentValues.put(CONTACT_CITY,contact.getCity());

        db.update(TABLE_NAME,contentValues, "id = ? ",new String[]{Integer.toString(contact.getId())}); // Update contentVales WHERE id = id;
        db.close();
        return true;
    }

    public Contact getContact(int id)
    {
        Contact contact; // Contact to be returned.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorData = db.rawQuery("SELECT * FROM contacts WHERE id= ?",new String[]{String.valueOf(id)});

        if(cursorData.moveToFirst()) // <<< MAKE SURE YOU HAVE THAT OR YOU'LL GET CURSOR OUT OF BOUNDS ERROR!!(TRUST ME I KNOW!)
        {
            // Get all the necessary data from the database about a particular contact
            this.id = cursorData.getInt(cursorData.getColumnIndex(CONTACT_ID));
            name = cursorData.getString(cursorData.getColumnIndex(CONTACT_NAME));
            phone = cursorData.getString(cursorData.getColumnIndex(CONTACT_PHONE));
            street = cursorData.getString(cursorData.getColumnIndex(CONTACT_STREET));
            email = cursorData.getString(cursorData.getColumnIndex(CONTACT_EMAIL));
            city = cursorData.getString(cursorData.getColumnIndex(CONTACT_CITY));
            cursorData.close(); // Read somewhere that you're suppose to do this.. Not sure


            // Give the data to the contact object and return it.
            contact = new Contact(this.id, name, phone, street, email, city);
            db.close();
            return contact;
        }
        else
        {
            //ERROR!!
            return null;
        }



    }

    public int totalRows()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,TABLE_NAME); // casted to int cause function returns a long
    }

    public ArrayList<Contact> getAllContacts()
    {
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursorData = db.rawQuery(String.format("SELECT * FROM %s",TABLE_NAME),null);
        Contact tempContact; // this contact will be used to iterate over all Contacts and added to list

        if(cursorData.moveToFirst())
        {
            do
            {
                // Grab Data from database about a particular contact
                this.id = cursorData.getInt(cursorData.getColumnIndex(CONTACT_ID));
                name = cursorData.getString(cursorData.getColumnIndex(CONTACT_NAME));
                phone = cursorData.getString(cursorData.getColumnIndex(CONTACT_PHONE));
                street = cursorData.getString(cursorData.getColumnIndex(CONTACT_STREET));
                email = cursorData.getString(cursorData.getColumnIndex(CONTACT_EMAIL));
                city = cursorData.getString(cursorData.getColumnIndex(CONTACT_CITY));

                tempContact = new Contact(this.id,name,phone,street,email,city);
                contacts.add(tempContact);
            }
            while(cursorData.moveToNext());
        }
        cursorData.close();
        db.close();
        return contacts;
    }

    public Integer deleteContact(Contact contact)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{Integer.toString(contact.getId())});

    }

    public void deleteAllContacts()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s",TABLE_NAME),null);

        while(cursor.moveToNext())
        {
            db.delete(TABLE_NAME,"id = ?", new String[]{Integer.toString(cursor.getInt(cursor.getColumnIndex(CONTACT_ID)))});
        }
        db.close();
    }


}
