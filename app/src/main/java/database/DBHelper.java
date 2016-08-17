package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
	
	
	private static final String DBNAME = "CarreFrour1200penppprr";
	private static final int DBVERSION = 1;
	private static final String DBCREATE = "create table custumerdb (id integer primary key autoincrement," +
			"shop text not null,item text not null,quantity text not null,uom text not null,itemid text not null);";

	
	
	
	//private static final String DBURL="create table urldb (u_id integer primary key not null,url text not null,flag text not null);";
	
	
	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DBCREATE);
		

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS custumerdb");
		
		onCreate(db);
		
	}
	
	
	
	


}
