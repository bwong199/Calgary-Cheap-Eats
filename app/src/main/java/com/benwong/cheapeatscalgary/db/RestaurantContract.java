package com.benwong.cheapeatscalgary.db;

import android.provider.BaseColumns;

/**
 * Created by benwong on 2016-07-11.
 */
public class RestaurantContract {
    public static final String DB_NAME = "com.benwong.cheapeatscalgary.db";
    public static final int DB_VERSION = 1;

    public class RestaurantEntry implements BaseColumns {
        public static final String TABLE = "restaurants";
        public static final String COL_TASK_RESTAURANT_NAME = "name";
        public static final String COL_TASK_RESTAURANT_ADDRESS = "address";
        public static final String COL_TASK_RESTAURANT_CUISINE = "cuisine";
        public static final String COL_TASK_RESTAURANT_DISTANCE = "distance";
        public static final String COL_TASK_RESTAURANT_LATITUDE = "latitude";
        public static final String COL_TASK_RESTAURANT_LONGITUDE = "longitude";
    }
}
