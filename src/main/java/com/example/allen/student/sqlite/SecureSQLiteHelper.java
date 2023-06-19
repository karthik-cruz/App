package com.example.allen.student.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SecureSQLiteHelper extends SQLiteOpenHelper {

	public SecureSQLiteHelper(Context context) {
		super(context, "myhelper", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Execute create driver info SQL
		
		db.execSQL("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, keyword TEXT NOT NULL);");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
		//String DROP_USER_TABLE = "DROP TABLE IF EXISTS user";
		//db.execSQL(DROP_USER_TABLE);
		//onCreate(db);
	}

}
