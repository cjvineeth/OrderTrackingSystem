package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DBAdapter {
	
	//Table Custumer Data Base
	
	private static final String CID   = "id";
	private static final String SHOPENAME= "shop";
	private static final String   Item = "item";
	private static final String QUATNITY = "quantity";
	private static final String UOM= "uom";
	private static final String ITEMID= "itemid";

	private static final String TNAME = "custumerdb";
	
	
	//UserAccount Data
	
				
				
	private Context context;
	private DBHelper dbhelper;
	SQLiteDatabase db;      
	
	
	
	

	
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		dbhelper = new DBHelper(context);
	}

	// open database to enter data
	public DBAdapter open() throws SQLiteException{
	
		db = dbhelper.getWritableDatabase();
		return this;
	}
	
	// close data base operation
	public void close(){
		dbhelper.close();
	}
	
	// to insert 
	public long addItemData(String shopname,String item,String quantity,String uom,String itemid){
	
		ContentValues values = new ContentValues();
		values.put(SHOPENAME, shopname);
		values.put(Item, item);
		values.put(QUATNITY, quantity);
		values.put(UOM,uom);
		values.put(ITEMID, itemid);
		
		return db.insert(TNAME, null, values);
		
	}
	
	// retrieves all	
	public Cursor getallItemsData(){
		return db.query(TNAME, new String[]{CID, SHOPENAME, Item, QUATNITY, UOM, ITEMID}, null, null, null,
				null, null);
		
	}
	

	 public boolean updateData (String quatity,int id)
	   {
	      SQLiteDatabase db = dbhelper.getWritableDatabase();
	      ContentValues contentValues = new ContentValues();
	      contentValues.put(QUATNITY, quatity);
	      
	      db.update(TNAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
	      return true;
	   }



	public boolean updateDataUom (String quatity,String uom,int id)
	{
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(QUATNITY, quatity);
		contentValues.put(UOM,uom);

		db.update(TNAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
		return true;
	}



	 
	 public Cursor  CheckTableisEmpty() {
		 
		 
		 SQLiteDatabase db = dbhelper.getWritableDatabase();
		 String count = "SELECT count(*) FROM "+TNAME;
		return db.rawQuery(count, null);
		
		 
		
	}
	 
	 
	 
	 
	 
	 
	 
	 public void deleteAlltype(){

			db=dbhelper.getWritableDatabase();
			db.delete(TNAME, null, null);
		 db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + TNAME + "'");



			
		}


	 public boolean deleteRow(String name) 
	 {
	     return db.delete(TNAME, CID + "=" + name, null) > 0;
	 }


}
