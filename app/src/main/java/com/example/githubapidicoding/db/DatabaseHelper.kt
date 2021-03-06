package com.example.githubapidicoding.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubapidicoding.db.DatabaseContract.FavoriteColumns.*
import com.example.githubapidicoding.db.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavuser"
        private const val DATABASE_VERSION = 1
        private val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${DatabaseContract.FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.FavoriteColumns.USERNAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.NAME} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.AVATAR} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.COMPANY} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.LOCATION} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWERS} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FOLLOWING} TEXT NOT NULL," +
                " ${DatabaseContract.FavoriteColumns.FAVORITE} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}