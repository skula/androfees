package com.skula.androfees.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.skula.androfees.models.Category;
import com.skula.androfees.models.Fee;
import com.skula.androfees.models.FeeList;
import com.skula.androfees.models.GraphItem;
import com.skula.androfees.utils.CalendarUtils;
import com.skula.androfees.utils.GraphItemFactory;

public class DatabaseService {
	private static final String DATABASE_NAME = "androfees.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLE_NAME_FEE = "fee";
	private static final String TABLE_NAME_CATEGORY = "category";
	private static final String TABLE_NAME_LOCATION = "location";

	private Context context;
	private SQLiteDatabase database;
	private SQLiteStatement statement;

	public DatabaseService(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.database = openHelper.getWritableDatabase();
	}

	public void bouchon() {
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FEE);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOCATION);

		database.execSQL("CREATE TABLE " + TABLE_NAME_CATEGORY + "(id INTEGER PRIMARY KEY, label TEXT)");
		database.execSQL("CREATE TABLE "
				+ TABLE_NAME_FEE
				+ "(id INTEGER PRIMARY KEY, label TEXT, amount FLOAT, idcategory INTEGER, location TEXT, dateinsr DATE, day INTEGER, month INTEGER, year INTEGER)");
		database.execSQL("CREATE TABLE " + TABLE_NAME_LOCATION + "(label TEXT)");

		insertCategory("Divers");
		insertCategory("Courses");
		insertCategory("Sorties");
		insertFee(new Fee("", "Soirée biduleaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "9999.42", "Divers", "27/10/2012", "Simply"));
		insertFee(new Fee("", "Soirée bidule", "28.42", "Divers", "27/10/2012", "Simply"));
		
		insertFee(new Fee("", "Soirée bidule", "28.42", "Courses", "28/10/2012", "Simply"));
		insertFee(new Fee("", "Soirée bidule", "28.42", "Courses", "28/10/2012", "Simply"));
		insertFee(new Fee("", "Soirée bidule", "28.42", "Courses", "28/10/2012", "Simply"));
		
		insertFee(new Fee("", "Soirée bidule", "28.42", "Sorties", "29/10/2012", "Simply"));
	}

	public void insertFee(Fee fee) {
		String date = CalendarUtils.frenchToSqlite(fee.getDate());

		String slices[] = date.split("-");
		int day = Integer.valueOf(slices[0]);
		int month = Integer.valueOf(slices[1]);
		int year = Integer.valueOf(slices[2]);

		String sql = "insert into " + TABLE_NAME_FEE
				+ "(id, label, amount, idcategory, dateinsr, location, day, month, year) values (?,?,?,?,date(?),?,?,?,?)";
		statement = database.compileStatement(sql);

		statement.bindLong(1, getNextFeeId() + 1);
		statement.bindString(2, fee.getLabel());
		statement.bindString(3, fee.getAmount());
		statement.bindLong(4, getCategoryId(fee.getCategory()));
		statement.bindString(5, date);
		statement.bindString(6, fee.getLocation());
		statement.bindLong(7, day);
		statement.bindLong(8, month);
		statement.bindLong(9, year);
		statement.executeInsert();

		if (!fee.getLocation().equals("")) {
			insertLocation(fee.getLocation());
		}
	}

	public void updateFee(String id, Fee newFee) {
		ContentValues args = new ContentValues();
		args.put("label", newFee.getLabel());
		args.put("amount", Double.valueOf(newFee.getAmount()));
		args.put("idcategory", getCategoryId(newFee.getCategory()));
		args.put("dateinsr", newFee.getDate());
		args.put("location", newFee.getLocation());
		database.update(TABLE_NAME_FEE, args, "id=" + id, null);
	}

	public void deleteFee(String id) {
		database.delete(TABLE_NAME_FEE, "id=" + id, null);
	}

	public Fee getFee(String id) {
		Cursor cursor = database.query(TABLE_NAME_FEE, new String[] { "label", "amount", "idcategory", "location", "dateinsr" }, "id=" + id, null,
				null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Fee fee = new Fee();
				fee.setId(id);
				fee.setLabel(cursor.getString(0));
				fee.setAmount(cursor.getString(1));
				fee.setCategory(getCategoryLabel(cursor.getInt(2)));
				fee.setLocation(cursor.getString(3));
				fee.setDate(CalendarUtils.sqliteToFrench(cursor.getString(4)));

				return fee;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return null;
	}

	public FeeList getFees(String dateFrom, String dateTo) {
		List<Category> categories = getCategories();
		Map<String, List<Fee>> map = new HashMap<String, List<Fee>>();
		double amount = 0.0;

		Cursor cursor = database.query(TABLE_NAME_FEE, new String[] { "id", "label", "amount", "idcategory", "location", "dateinsr" },
				"dateinsr>=date('" + dateFrom + "') and dateinsr<=date('" + dateTo + "')", null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Fee fee = new Fee();
				fee.setId(cursor.getString(0));
				fee.setLabel(cursor.getString(1));
				fee.setAmount(cursor.getString(2));
				amount += cursor.getDouble(2);
				for (Category category : categories) {
					if (cursor.getInt(3) == category.getId()) {
						fee.setCategory(category.getLabel());
						break;
					}
				}
				fee.setLocation(cursor.getString(4));
				fee.setDate(cursor.getString(5));

				if (map.containsKey(fee.getCategory())) {
					map.get(fee.getCategory()).add(fee);
				} else {
					List<Fee> list = new ArrayList<Fee>();
					list.add(fee);
					map.put(fee.getCategory(), list);
				}
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}

		return new FeeList(amount + "", map);
	}

	public int getNextFeeId() {
		Cursor cursor = database.query(TABLE_NAME_FEE, new String[] { "max(id)" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				return cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return 0;
	}

	public void insertCategory(String label) {
		String sql = "insert into " + TABLE_NAME_CATEGORY + "(id, label) values (?,?)";
		statement = database.compileStatement(sql);
		statement.bindLong(1, getNextCategoryId() + 1);
		statement.bindString(2, label);
		statement.executeInsert();
	}

	public void updateCategory(int id, String newLabel) {
		ContentValues args = new ContentValues();
		args.put("label", newLabel);
		database.update(TABLE_NAME_CATEGORY, args, "id=" + id, null);
	}

	public void deleteCategory(String label) {
		if (label.equals("Divers")) {
			return;
		}
		int id = getCategoryId(label);
		int defaultId = getCategoryId("Divers");
		ContentValues args = new ContentValues();
		args.put("idcategory", defaultId);
		database.update(TABLE_NAME_FEE, args, "idcategory=" + id, null);
		database.delete(TABLE_NAME_CATEGORY, "id=" + id, null);
	}

	public boolean existCategory(String label) {
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "id" }, "label='" + label + "'", null, null, null, null);
		if (cursor.moveToFirst()) {
			return true;
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return false;
	}

	public Category getCategory(int id) {
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "label" }, "id=" + id, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Category category = new Category();
				category.setId(id);
				category.setLabel(cursor.getString(0));
				return category;
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return null;
	}

	public List<Category> getCategories() {
		List<Category> list = new ArrayList<Category>();
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "id", "label" }, null, null, null, null, "label asc");
		if (cursor.moveToFirst()) {
			do {
				Category category = new Category();
				category.setId(cursor.getInt(0));
				category.setLabel(cursor.getString(1));
				list.add(category);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<String> getCategoriesLabel() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "label" }, null, null, null, null, "label asc");
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public int getCategoryId(String label) {
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "id" }, "label='" + label + "'", null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				return cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return 0;
	}

	public String getCategoryLabel(int id) {
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "label" }, "id=" + id, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				return cursor.getString(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return "";
	}

	public int getNextCategoryId() {
		Cursor cursor = database.query(TABLE_NAME_CATEGORY, new String[] { "max(id)" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				return cursor.getInt(0);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return 0;
	}

	public void insertLocation(String label) {
		if (existLocation(label)) {
			return;
		}
		String sql = "insert into " + TABLE_NAME_LOCATION + "(label) values (?)";
		statement = database.compileStatement(sql);
		statement.bindString(1, label);
		statement.executeInsert();
	}

	public boolean existLocation(String label) {
		return getLocations().contains(label);
	}

	public List<String> getLocations() {
		List<String> list = new ArrayList<String>();
		Cursor cursor = database.query(TABLE_NAME_LOCATION, new String[] { "label" }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	/******************** GRAPHIQUES ************************/

	private String getGroupOrderBy(int timeUnit) {
		switch (timeUnit) {
		case Calendar.DAY_OF_MONTH:
			return "year, month, day";
		case Calendar.MONTH:
			return "year, month";
		case Calendar.YEAR:
			return "year";
		default:
			return "";
		}
	}
	
	
	public Map<String, Double> graphPrcCat(String dateFrom){
		String req = "select c.label, sum(f.amount) from fee f, category c ";
		req+= "where f.dateinsr>=date('"+dateFrom+"') and f.idcategory=c.id ";
		req+= "group by c.label ";
		req+= "order by c.label asc";
		Cursor cursor = database.rawQuery(req, null);

		Map<String, Double> map = new HashMap<String, Double>();
		if (cursor.moveToFirst()) {
			do {
				map.put(cursor.getString(0),cursor.getDouble(1));
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return map;
	}
	
	public List<GraphItem> graphVarTot(String dateFrom, int timeUnit){ 
		String req = "select sum(amount), "+getGroupOrderBy(timeUnit)+" from fee ";
		req+= "where dateinsr>=date('"+dateFrom+"') ";
	//	req+= "order by "+getGroupOrderBy(timeUnit)+ " asc ";
		req+= "group by "+getGroupOrderBy(timeUnit);
		Cursor cursor = database.rawQuery(req, null);

		List<GraphItem> list = new ArrayList<GraphItem>();
		if (cursor.moveToFirst()) {
			do {
				GraphItem gi = new GraphItem();
				switch (timeUnit) {
				case Calendar.DAY_OF_MONTH:
					gi.setYear(cursor.getInt(1));
					gi.setMonth(cursor.getInt(2));
					gi.setDay(cursor.getInt(3));
					break;
				case Calendar.MONTH:
					gi.setYear(cursor.getInt(1));
					gi.setMonth(cursor.getInt(2));
					break;
				case Calendar.YEAR:
					gi.setYear(cursor.getInt(1));
					break;
				default:
					break;
				}
		
				gi.setValue(cursor.getDouble(0));
				list.add(gi);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return GraphItemFactory.fillList(list, dateFrom, timeUnit);
	 }
	  
	  public Map<String, List<GraphItem>> graphVarCat(String dateFrom, int timeUnit){ 
		String req = "select c.label, sum(f.amount), "+getGroupOrderBy(timeUnit)+" from fee f, category c ";
		req+= "where f.dateinsr>=date('"+dateFrom+"') and f.idcategory=c.id ";
		req+= "group by c.label, "+getGroupOrderBy(timeUnit)+" ";
		req+= "order by "+getGroupOrderBy(timeUnit)+ " asc";
		Cursor cursor = database.rawQuery(req, null);

		Map<String, List<GraphItem>> map = new HashMap<String, List<GraphItem>>();
		if (cursor.moveToFirst()) {
			do {
				GraphItem gi = new GraphItem();
				gi.setValue(cursor.getDouble(1));
				switch (timeUnit) {
				case Calendar.DAY_OF_MONTH:
					gi.setYear(cursor.getInt(2));
					gi.setMonth(cursor.getInt(3));
					gi.setDay(cursor.getInt(4));
					break;
				case Calendar.MONTH:
					gi.setYear(cursor.getInt(2));
					gi.setMonth(cursor.getInt(3));
					break;
				case Calendar.YEAR:
					gi.setYear(cursor.getInt(2));
					break;
				default:
					break;
				}
	
				if(!map.containsKey(cursor.getString(0))){
					List<GraphItem> list = new ArrayList<GraphItem>();
					list.add(gi);
					map.put(cursor.getString(0),list);
				}else{
					map.get(cursor.getDouble(0)).add(gi);
				}
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return map;
	 }
	 

	 private static class OpenHelper extends SQLiteOpenHelper {
		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE "
					+ TABLE_NAME_FEE
					+ "(id INTEGER PRIMARY KEY, label TEXT, amount FLOAT, idcategory INTEGER, location TEXT, dateinsr DATE, day INTEGER, month INTEGER, year INTEGER)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_CATEGORY + "(id INTEGER PRIMARY KEY, label TEXT)");
			db.execSQL("CREATE TABLE " + TABLE_NAME_LOCATION + "(label TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FEE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_LOCATION);
			onCreate(db);
		}
	}
}
