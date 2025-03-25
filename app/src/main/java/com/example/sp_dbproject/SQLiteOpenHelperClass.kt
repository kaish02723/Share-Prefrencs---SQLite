package com.example.sp_dbproject

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory

class SQLiteOpenHelperClass(private var context: Context):SQLiteOpenHelper(context, DB_NAME, null, VERSION) {
    companion object{
        private const val DB_NAME="Edugaon"
        private const val TABLE_NAME="Students"
        private const val VERSION=2
        private const val COL_ID="id"
        private const val COL_NAME="name"
        private const val COL_EMAIL="email"
        private const val COL_IMAGE = "image"



    }
    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COL_NAME TEXT,$COL_EMAIL TEXT,$COL_IMAGE BLOB)"
        p0?.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        p0?.execSQL(dropQuery)
        onCreate(p0)
    }

    fun insertData(userModelClass: UserModelClass){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COL_NAME,userModelClass.name)
            put(COL_EMAIL,userModelClass.email)
            put(COL_IMAGE, userModelClass.image)
        }
        db.insert(TABLE_NAME,null,values)
    }

    fun viewDataFromListView():ArrayList<UserModelClass>{
        val db=writableDatabase
        val arrayList= arrayListOf<UserModelClass>()
        val selectQuery="SELECT * FROM $TABLE_NAME"
        val x=db.rawQuery(selectQuery,null)

        while (x.moveToNext()){
            val c_id=x.getInt(x.getColumnIndexOrThrow(COL_ID))
            val c_name=x.getString(x.getColumnIndexOrThrow(COL_NAME))
            val c_email=x.getString(x.getColumnIndexOrThrow(COL_EMAIL))
            val c_image = x.getBlob(x.getColumnIndexOrThrow(COL_IMAGE))



            val model=UserModelClass(c_id,c_name,c_email,c_image)
           arrayList.add(model)
        }
        return arrayList
    }

    fun deletedUser(id: Int){
        val whereArm= arrayOf(id.toString())
        val db=writableDatabase
        val where="$COL_ID = ?"
        db.delete(TABLE_NAME,where,whereArm)
    }

    fun updateFunction(userModelClass: UserModelClass){
        val db=writableDatabase
        val array= arrayOf(userModelClass.id.toString())
        val where= "$COL_ID = ?"
        val updateValue=ContentValues().apply {
            put(COL_ID,userModelClass.id)
            put(COL_NAME,userModelClass.name)
            put(COL_EMAIL,userModelClass.email)
            put(COL_IMAGE, userModelClass.image)
        }

        db.update(TABLE_NAME,updateValue,where,array)
        db.close()
    }

    }

