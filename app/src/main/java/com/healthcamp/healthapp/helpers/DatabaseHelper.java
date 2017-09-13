package com.healthcamp.healthapp.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.healthcamp.healthapp.models.Carts.CartModel;
import com.healthcamp.healthapp.models.Carts.WishListModel;

import java.io.File;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String FOLDER_NAME = "HealthApp";
    public static final String DATABASE_NAME = "HealthCamp.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase sqLiteDatabase;
    /**
     * Cart table variables
     */
    public static final String CART_TABLE_NAME = "Cart";
    public static final String CART_COLUMN_ID = "Id";
    public static final String CART_COLUMN_NAME = "Name";
    public static final String CART_COLUMN_DESCRPTION = "Description";
    public static final String CART_COLUMN_TAXRATE = "TaxRate";
    public static final String CART_COLUMN_PRODUCTTYPE = "ProductType";
    public static final String CART_COLUMN_PRICE = "Price";
    public static final String CART_COLUMN_COMPAREPRICE = "ComparePrice";
    public static final String CART_COLUMN_IMAGE = "ImageUrl";
    public static final String CART_COLUMN_DATECREATED = "DateCreated";
    public static final String CART_COLUMN_ITEMCOUNT = "ItemCount";

    /**
     * WishList table variables
     */
    public static final String WISHLIST_TABLE_NAME = "WishLists";
    public static final String WISHLIST_COLUMN_ID = "Id";
    public static final String WISHLIST_COLUMN_NAME = "Name";
    public static final String WISHLIST_COLUMN_DESCRPTION = "Description";
    public static final String WISHLIST_COLUMN_PRICE = "Price";
    public static final String WISHLIST_COLUMN_DATECREATED = "DateCreated";
    public static final String WISHLIST_COLUMN_IMAGE = "ImageUrl";


    public DatabaseHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory() + File.separator + FOLDER_NAME + File.separator + DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("AA", "faf");
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS Cart(Id INTEGER PRIMARY KEY, Name TEXT, Description TEXT, TaxRate TEXT, ProductType TEXT, Price TEXT, ComparePrice TEXT, ImageUrl TEXT, DateCreated TEXT, ItemCount TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS WishList(Id INTEGER PRIMARY KEY, Name TEXT, Description TEXT, Price TEXT, DateCreated TEXT, ImageUrl TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteDatabase = db;
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS Cart");
        db.execSQL("DROP TABLE IF EXISTS WishList");
        onCreate(db);
    }

    public ArrayList<CartModel> getAllCartItems() {
        ArrayList<CartModel> cartList = new ArrayList<CartModel>();
        String selectQuery = "SELECT  * FROM " + CART_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String taxRate = cursor.getString(3);
                String productType = cursor.getString(4);
                String price = cursor.getString(5);
                String comparePrice = cursor.getString(6);
                String imageUrl = cursor.getString(7);
                String dateCreated = cursor.getString(8);
                String itemCount = cursor.getString(9);
                CartModel cartModel = new CartModel(id, name, description, taxRate, productType, price, comparePrice, imageUrl, dateCreated, itemCount);

                cartList.add(cartModel);
            } while (cursor.moveToNext());
        }
        return cartList;
    }

    public ArrayList<WishListModel> getAllWishListItems() {
        ArrayList<WishListModel> wishList = new ArrayList<WishListModel>();
        String selectQuery = "SELECT  * FROM " + WISHLIST_TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String dateCreated = cursor.getString(3);
                String price = cursor.getString(4);
                String imageUrl = cursor.getString(5);
                WishListModel wishListModel = new WishListModel(id, name, description, dateCreated, price, imageUrl);

                wishList.add(wishListModel);
            } while (cursor.moveToNext());
        }
        return wishList;
    }


    public boolean insertCart(CartModel cartModel) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put(CART_COLUMN_ID, cartModel.getId());
            contentValues.put(CART_COLUMN_NAME, cartModel.getName());
            contentValues.put(CART_COLUMN_DESCRPTION, cartModel.getDescription());
            contentValues.put(CART_COLUMN_TAXRATE, cartModel.getTaxRate());
            contentValues.put(CART_COLUMN_PRODUCTTYPE, cartModel.getProductType());
            contentValues.put(CART_COLUMN_PRICE, cartModel.getPrice());
            contentValues.put(CART_COLUMN_COMPAREPRICE, cartModel.getComparePrice());
            contentValues.put(CART_COLUMN_IMAGE, cartModel.getImageUrl());
            contentValues.put(CART_COLUMN_DATECREATED, cartModel.getDateCreated());
            //contentValues.put(CART_COLUMN_ITEMCOUNT, cartModel.getItemCount());

            if (checkRecordInDb(CART_TABLE_NAME, CART_COLUMN_ID, cartModel.getId())) {
                int itemCount = getItemCount(CART_TABLE_NAME, CART_COLUMN_ITEMCOUNT, CART_COLUMN_ID, cartModel.getId()) + 1;
                contentValues.put(CART_COLUMN_ITEMCOUNT, String.valueOf(itemCount));
                long ret = db.update(CART_TABLE_NAME, contentValues, CART_COLUMN_ID + " = ?",
                        new String[]{cartModel.getId()});
            } else {
                contentValues.put(CART_COLUMN_ITEMCOUNT, cartModel.getItemCount());
                long ret = db.insertOrThrow(CART_TABLE_NAME, null, contentValues);
            }

        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }
        // db.insert(CART_TABLE_NAME, null, contentValues);
        return true;
    }

    public int updateCart(CartModel cartModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CART_COLUMN_ID, cartModel.getId());
        contentValues.put(CART_COLUMN_NAME, cartModel.getName());
        contentValues.put(CART_COLUMN_DESCRPTION, cartModel.getDescription());
        contentValues.put(CART_COLUMN_PRICE, cartModel.getPrice());
        contentValues.put(CART_COLUMN_DATECREATED, cartModel.getDateCreated());
        contentValues.put(CART_COLUMN_IMAGE, cartModel.getImageUrl());
        db.execSQL("");
        return db.update(CART_TABLE_NAME, contentValues,
                CART_COLUMN_ID + " = ?",
                new String[]{String.valueOf(cartModel.getId())});

    }

    public boolean insertWishList(WishListModel wishListModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(WISHLIST_COLUMN_ID, wishListModel.getId());
        contentValues.put(WISHLIST_COLUMN_NAME, wishListModel.getName());
        contentValues.put(WISHLIST_COLUMN_DESCRPTION, wishListModel.getDescription());
        contentValues.put(WISHLIST_COLUMN_PRICE, wishListModel.getPrice());
        contentValues.put(WISHLIST_COLUMN_DATECREATED, wishListModel.getDateCreated());
        contentValues.put(WISHLIST_COLUMN_IMAGE, wishListModel.getImageUrl());
        db.insert(WISHLIST_TABLE_NAME, null, contentValues);


        return true;
    }

    public int updateWishList(WishListModel wishListModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WISHLIST_COLUMN_ID, wishListModel.getId());
        contentValues.put(WISHLIST_COLUMN_NAME, wishListModel.getName());
        contentValues.put(WISHLIST_COLUMN_DESCRPTION, wishListModel.getDescription());
        contentValues.put(WISHLIST_COLUMN_PRICE, wishListModel.getPrice());
        contentValues.put(WISHLIST_COLUMN_DATECREATED, wishListModel.getDateCreated());
        contentValues.put(WISHLIST_COLUMN_IMAGE, wishListModel.getImageUrl());

        return db.update(WISHLIST_TABLE_NAME, contentValues,
                WISHLIST_COLUMN_ID + " = ?",
                new String[]{String.valueOf(wishListModel.getId())});

    }

    private boolean checkRecordInDb(String tableName, String colName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor checkDbCursor = null;
        try {
            String rawQuery = "select count(" + colName + ") from " + tableName + " where " + colName + " = ?";
            checkDbCursor = db.rawQuery(rawQuery, new String[]{value});
            if (checkDbCursor != null) {
                checkDbCursor.moveToFirst();
                int count = checkDbCursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (checkDbCursor != null) {
                checkDbCursor.close();
            }
        }
        return false;
    }

    private int getItemCount(String tableName, String selectColumnName,  String conditionalColumName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor checkDbCursor = null;
        try {
            String rawQuery = "select " + selectColumnName + " from " + tableName + " where " + conditionalColumName + " = '" + value + "'";
            checkDbCursor = db.rawQuery(rawQuery, null);
            if (checkDbCursor != null) {
                checkDbCursor.moveToFirst();
                String strCount = checkDbCursor.getString(checkDbCursor.getColumnIndex(selectColumnName));
                int count = Integer.parseInt(strCount);
                return count;
            }
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (checkDbCursor != null) {
                checkDbCursor.close();
            }
        }
        return -1;
    }
    /*public boolean insertContact(ArrayList<ContactDetails> arrayOfUsers) {
        SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		for (int i = 0; i < arrayOfUsers.size(); i++) {
			contentValues.put("name", arrayOfUsers.get(i).name);
			contentValues.put("phone", arrayOfUsers.get(i).phone);
			contentValues.put("address", arrayOfUsers.get(i).address);
			contentValues.put("email", arrayOfUsers.get(i).email);
			contentValues.put("photo", arrayOfUsers.get(i).image);
			db.insert("contacts", null, contentValues);

		}
		return true;
	}

	public ArrayList<ContactDetails> getAllContacts() {
		ArrayList<ContactDetails> contactList = new ArrayList<ContactDetails>();
		String selectQuery = "SELECT  * FROM " + CONTACTS_TABLE_NAME;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				int id = cursor.getInt(0);
				String name = cursor.getString(1);
				String phone = cursor.getString(2);
				String address = cursor.getString(3);
				String email = cursor.getString(4);
				String photo = cursor.getString(5);
				ContactDetails contact = new ContactDetails(id, name, photo, phone,
						address, email);

				contactList.add(contact);
			} while (cursor.moveToNext());
		}

		// return contact list
		return contactList;
	}

	public boolean insertnewContact(ContactDetails contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", contact.name);
		contentValues.put("phone", contact.phone);
		contentValues.put("address", contact.address);
		contentValues.put("email", contact.email);
		contentValues.put("photo", contact.image);
		db.insert("contacts", null, contentValues);
		return true;
	}

	public int updateContact(ContactDetails contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("name", contact.name);
		contentValues.put("phone", contact.phone);
		contentValues.put("address", contact.address);
		contentValues.put("email", contact.email);
		contentValues.put("photo", contact.image);

		return db.update(CONTACTS_TABLE_NAME, contentValues,
				KEY_ID + " = ?",
				new String[] { String.valueOf(contact.id) });

	}
	
	 public void deleteContact(ContactDetails contact) {  
	        SQLiteDatabase db = this.getWritableDatabase();  
	        db.delete(CONTACTS_TABLE_NAME, KEY_ID + " = ?",  
	                new String[] { String.valueOf(contact.id) });  
	        db.close();  
	 }*/
}
