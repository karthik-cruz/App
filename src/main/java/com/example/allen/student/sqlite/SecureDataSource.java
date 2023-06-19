package com.example.allen.student.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SecureDataSource {

	private SQLiteDatabase db;
	private SecureSQLiteHelper dbHelper;

	public SecureDataSource(Context context) {
		dbHelper = new SecureSQLiteHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	// Close the db
	public void close() {
		db.close();
	}

	
	public boolean isRegistered() {
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			cursor = db.rawQuery("SELECT * FROM user", null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("error", e.getMessage());
		}
		if ((cursor != null) && (cursor.getCount() > 0)) {
			cursor.close();
			close();
			return true;
		} else {
			cursor.close();
			//close();
			return false;
		}
	}
		
	public void insertdata(ContentValues values, String sTableName) {
		try {
			db.insert(sTableName, null, values);
			Log.i("DBin1","Created");
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("error123", e.getMessage());
		}finally{
			Log.i("error1NO", "error1NO");

			db.close();
		}

	}

	public Cursor retrievedata(String tableName) {

		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT * FROM " + tableName, null);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("error", e.getMessage());
		}
		return cursor;

	}
	public void deletedata(String tableName) {
		try {
			db.delete(tableName, null, null);
			Log.i("Delete error", "Deleted");
		} catch (Exception e) {
			Log.i("Delete error", e.getMessage());
			// TODO: handle exception
		}
	}
	
	public String[] getkeyword() {
		String imageNames[] = null;
		Cursor cursor = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		try {
			cursor = db.rawQuery("SELECT * FROM user", null);
			if (cursor.moveToFirst()){
	       	   while(!cursor.isAfterLast()){ 
	       		String[] names = {cursor.getString(cursor.getColumnIndex("username")),
						cursor.getString(cursor.getColumnIndex("keyword"))};
	       			imageNames = names;
	       		   cursor.moveToNext();
	       	   }
		    }
		} catch (Exception e) {
			Log.i("error", e.getMessage());
		}
		//cursor.close();
		//close();
		return imageNames;
	}
}
