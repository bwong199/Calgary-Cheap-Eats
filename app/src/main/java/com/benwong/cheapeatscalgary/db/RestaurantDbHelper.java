package com.benwong.cheapeatscalgary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.benwong.cheapeatscalgary.GilchristFragment;
import com.benwong.cheapeatscalgary.Restaurant;

import java.util.Collections;


/**
 * Created by benwong on 2016-07-11.
 */
public class RestaurantDbHelper extends SQLiteOpenHelper {

    public RestaurantDbHelper(Context context) {
        super(context, RestaurantContract.DB_NAME, null, RestaurantContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE IF NOT EXISTS " + RestaurantContract.RestaurantEntry.TABLE + " ("
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_NAME + " VARCHAR, "
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_ADDRESS + " VARCHAR, "
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_CUISINE + " VARCHAR, "
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LATITUDE + " REAL, "
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LONGITUDE + " REAL, "
                + RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_DISTANCE + " REAL)";
        System.out.println("Create table " + createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.RestaurantEntry.TABLE);
        onCreate(db);
    }

    public void deleteDatabase() {

        this.deleteRestaurantDB();
    }

    public void deleteRestaurantDB() {
        String deleteScript = "delete from " + RestaurantContract.RestaurantEntry.TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteScript);
    }

    public void insert(String name, String address, String cuisine, Double distance
            , Double latitude, Double longitude
    ) {

        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("RESTAURANT name in DB Helper " + name);

        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_NAME, name);
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_ADDRESS, address);
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_CUISINE, cuisine);
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_DISTANCE, distance);
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LATITUDE, latitude);
        values.put(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LONGITUDE, longitude);
        db.insertWithOnConflict(RestaurantContract.RestaurantEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close(); // Closing database connection
    }

//    public void updateSale(String productName, int quantityChange) {

//        SQLiteDatabase db = this.getWritableDatabase();
//
//        System.out.println("Update method habit name " + productName);
//        Cursor c = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE + " WHERE " + InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME + " = " + "'" + productName + "'", null);
//
//        try {
//            int productIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME);
//            int quantityIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_QUANTITY);
//            int priceIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_PRICE);
//
//            if (c != null && c.moveToFirst()) {
//                do {
//                    System.out.println("Update method " + c.getString(productIndex));
//                    System.out.println("Update method " + Integer.toString(c.getInt(quantityIndex)));
//                    int updatedQuantity = c.getInt(quantityIndex) + quantityChange;
//                    System.out.println("Updated quantity " + updatedQuantity);
//                    if (updatedQuantity > 0) {
//                        ContentValues values = new ContentValues();
//                        values.put(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME, c.getString(productIndex));
//                        values.put(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_PRICE, c.getString(priceIndex));
//                        values.put(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_QUANTITY, updatedQuantity);
//
//                        db.update(InventoryContract.InventoryEntry.TABLE, values, InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME + " = ?",
//                                new String[]{String.valueOf(c.getString(productIndex))});
//                    }
//                } while (c.moveToNext());
//            }
//
//            c.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void read() {
        try {

            SQLiteDatabase db = this.getWritableDatabase();

            String queryString = "SELECT * FROM " + RestaurantContract.RestaurantEntry.TABLE;
            Cursor c = db.rawQuery(queryString, null);

            int nameIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_NAME);
            int addressIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_ADDRESS);
            int cuisineIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_CUISINE);
            int distanceIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_DISTANCE);
            int latitudeIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LATITUDE);
            int longitudeIndex = c.getColumnIndex(RestaurantContract.RestaurantEntry.COL_TASK_RESTAURANT_LONGITUDE);

            if (c != null && c.moveToFirst()) {
                do {
                    System.out.println("READ NAME " + c.getString(nameIndex));
                    System.out.println("READ ADDRESS " + c.getString(addressIndex));
                    System.out.println("READ CUISINE " + c.getString(cuisineIndex));
                    System.out.println("READ DISTANCE " + c.getString(distanceIndex));
//                    String singleProduct = c.getString(productIndex) + " : " + Integer.toString(c.getInt(priceIndex)) + " - " + Integer.toString(c.getInt(quantityIndex));

                    Restaurant eachRestaurant = new Restaurant();
                    eachRestaurant.setName(c.getString(nameIndex));
                    eachRestaurant.setAddress(c.getString(addressIndex));
                    eachRestaurant.setCuisine(c.getString(cuisineIndex));
                    eachRestaurant.setDistance(c.getDouble(distanceIndex));
                    eachRestaurant.setLatitude(c.getDouble(latitudeIndex));
                    eachRestaurant.setLongitude(c.getDouble(longitudeIndex));

                    GilchristFragment.restaurants.add(eachRestaurant);
                    Collections.sort(GilchristFragment.restaurants);
                } while (c.moveToNext());
            }

            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void readSingleProduct(String productName) {

//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//
//            System.out.println("Update method habit name " + productName);
//            Cursor c = db.rawQuery("SELECT * FROM " + InventoryContract.InventoryEntry.TABLE + " WHERE " + InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME + " = " + "'" + productName + "'", null);
//
//            int productIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME);
//            int priceIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_PRICE);
//            int quantityIndex = c.getColumnIndex(InventoryContract.InventoryEntry.COL_TASK_PRODUCT_QUANTITY);
//
//            if (c != null && c.moveToFirst()) {
//                do {
////                    System.out.println("READ single product " + c.getString(productIndex));
////                    System.out.println("READ single price " + Integer.toString(c.getInt(priceIndex)));
////                    System.out.println("READ single quantity " + Integer.toString(c.getInt(quantityIndex)));
//                    String singleProduct = c.getString(productIndex) + " : " + Integer.toString(c.getInt(priceIndex)) + " - " + Integer.toString(c.getInt(quantityIndex));
//
//                    int updatedQuantity = Integer.parseInt(String.valueOf(c.getInt(quantityIndex)));
//
//                    DetailActivity.quantityTV.setText("Current Quantity: " + String.valueOf(updatedQuantity));
//
//                } while (c.moveToNext());
//            }
//
//            c.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void deleteProduct(String productName) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(InventoryContract.InventoryEntry.TABLE,
//                InventoryContract.InventoryEntry.COL_TASK_PRODUCT_NAME + " = ?",
//                new String[]{productName});
//        db.close();
    }
}
