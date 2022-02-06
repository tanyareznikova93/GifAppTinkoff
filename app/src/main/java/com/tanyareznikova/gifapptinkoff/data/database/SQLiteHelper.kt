package com.weather.openweatherapp.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "post_database.db"
        private const val TABLE_POST = "post_table"
        private const val ID = "id"
        private const val GIF_URL = "gifURL"
        private const val AUTHOR = "author"
        private const val DATE = "date"
        private const val DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val createTablePost = ("CREATE TABLE " + TABLE_POST + "("
                + ID + " INTEGER PRIMARY KEY, " + GIF_URL + " TEXT, "
                + AUTHOR + " TEXT, " + DATE + " TEXT, " + DESCRIPTION + " TEXT " + ")")
        db?.execSQL(createTablePost)
    }//onCreate

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_POST")
        onCreate(db)
    }//onUpgrade

    fun insert(post:PostModel): Long {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,post.id)
        contentValues.put(GIF_URL,post.gifURL)
        contentValues.put(AUTHOR,post.author)
        contentValues.put(DATE,post.date)
        contentValues.put(DESCRIPTION,post.description)

        val success = db.insert(TABLE_POST, null, contentValues)
        db.close()
        return success

    }//Insert


    fun getAll(): ArrayList<PostModel>{

        val pList:ArrayList<PostModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_POST ORDER BY $ID ASC"
        val db = this.readableDatabase

        val cursor:Cursor?

        try {

            cursor = db.rawQuery(selectQuery,null)

        } catch (e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id:Int
        var gifURL:String
        var author:String
        var date:String
        var description:String

        if(cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                gifURL = cursor.getString(cursor.getColumnIndexOrThrow("gifURL"))
                author = cursor.getString(cursor.getColumnIndexOrThrow("author"))
                date = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val post = PostModel(id = id, gifURL = gifURL, author = author, date = date, description = description)
                pList.add(post)
            }while (cursor.moveToNext())
        }
        return pList

    }//getAll


    fun update(post: PostModel): Int {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,post.id)
        contentValues.put(GIF_URL,post.gifURL)
        contentValues.put(AUTHOR,post.author)
        contentValues.put(DATE,post.date)
        contentValues.put(DESCRIPTION,post.description)

        val success = db.update(TABLE_POST, contentValues, "id=" + post.id, null)
        db.close()
        return success

    }//update

    fun deleteById(id:Int): Int {

        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,id)

        val success = db.delete(TABLE_POST, "id=$id", null)
        db.close()
        return success

    }//deleteById

}//SQLiteHelper