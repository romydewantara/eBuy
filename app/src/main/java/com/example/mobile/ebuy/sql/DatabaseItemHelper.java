package com.example.mobile.ebuy.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mobile.ebuy.modal.Item;
import com.example.mobile.ebuy.modal.Transaksi;

import java.util.ArrayList;
import java.util.List;


public class DatabaseItemHelper extends SQLiteOpenHelper {


    public DatabaseItemHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "ItemManager.db";

    // Item table name
    private static final String TABLE_ITEM = "item";

    // Item Table Columns names
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_IMAGE = "item_image";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_PRICE = "item_price";
    private static final String COLUMN_ITEM_CONFIRM = "item_confirm"; //set for confirm payment

    // create table sql query
    private String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
            + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ITEM_IMAGE + " INTEGER," + COLUMN_ITEM_NAME + " TEXT,"
            + COLUMN_ITEM_PRICE + " TEXT," + COLUMN_ITEM_CONFIRM + " TEXT" + ")";

    // drop table sql query
    private String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + TABLE_ITEM;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseItemHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEM_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop Item Table if exist
        db.execSQL(DROP_ITEM_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param item
     */
    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_IMAGE, item.getImageId());
        values.put(COLUMN_ITEM_NAME, item.getProduct());
        values.put(COLUMN_ITEM_PRICE, item.getPrice());
        values.put(COLUMN_ITEM_CONFIRM, item.getState());

        // Inserting Row
        db.insert(TABLE_ITEM, null, values);
        db.close();
    }

    /**
     * This method to update item record
     *
     * @param item
     */
    public Integer updateStatus(Item item) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_CONFIRM, item.getState());

        // updating row
        return db.update(TABLE_ITEM, values, COLUMN_ITEM_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    /**
     * This method is to delete item record
     *
     * @param id
     */
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ITEM, "item_id = ?",new String[] {id});
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Item> getAllItem() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ITEM_ID,
                COLUMN_ITEM_IMAGE,
                COLUMN_ITEM_NAME,
                COLUMN_ITEM_PRICE,
                COLUMN_ITEM_CONFIRM
        };
        // sorting orders
        String sortOrder =
                COLUMN_ITEM_NAME + " ASC";
        List<Item> itemList = new ArrayList<Item>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor;
        cursor = db.query(TABLE_ITEM, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID))));
                item.setImageId(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_IMAGE)));
                item.setProduct(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                item.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PRICE)));
                item.setState(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_CONFIRM)));

                // Adding user record to list
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return itemList;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<Transaksi> getTransaksiItem() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_ITEM_ID,
                COLUMN_ITEM_IMAGE,
                COLUMN_ITEM_NAME,
                COLUMN_ITEM_PRICE,
                COLUMN_ITEM_CONFIRM
        };
        // sorting orders
        String sortOrder =
                COLUMN_ITEM_NAME + " ASC";
        List<Transaksi> transaksiList = new ArrayList<Transaksi>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor;
        cursor = db.query(TABLE_ITEM, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Transaksi transaksi = new Transaksi();
                transaksi.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID))));
                transaksi.setImageId(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_IMAGE)));
                transaksi.setProduct(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
                transaksi.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PRICE)));
                transaksi.setState(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_CONFIRM)));
                // Adding user record to list
                transaksiList.add(transaksi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return transaksiList;
    }
}
