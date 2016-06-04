package beta.com.android17.onevault.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import beta.com.android17.onevault.Object.Account;
import beta.com.android17.onevault.Object.Transaction;
import beta.com.android17.onevault.Utility.RunTime;

/**
 * Created by radleyrosal on 10/22/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    //INITIALIZE DATABASE
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transactions";

    //TYPES;
    private final int ALL = -1;
    private final int INCOME = 0;
    private final int EXPENSE = 1;
    private final int RECURRING = 2;

    // column ids
    //for expense and income
    private final int INDEX_TRANSACTION_ID = 0;
    private final int INDEX_TRANSACTION_AMOUNT = 1;
    private final int INDEX_TRANSACTION_PAYMENT_METHOD = 2;
    private final int INDEX_TRANSACTION_DATE = 3;
    private final int INDEX_TRANSACTION_REF_CHECK = 4;
    private final int INDEX_TRANSACTION_DESCRIPTION = 5;
    private final int INDEX_TRANSACTION_TAX = 6;
    private final int INDEX_TRANSACTION_QUANTITY = 7;
    private final int INDEX_TRANSACTION_PAYER = 8;
    private final int INDEX_TRANSACTION_TAGS = 9;
    //private final int INDEX_TRANSACTION_UNIT = 10;
    private final int INDEX_TRANSACTION_CATEGORY = 10;
    private final int INDEX_TRANSACTION_STATUS = 11;
    private final int INDEX_TRANSACTION_FK_ACCOUNT_ID = 12;
    //for recurring
    private final int INDEX_RECURRING_ID = 0;
    private final int INDEX_RECURRING_DESCRIPTION = 1;
    private final int INDEX_RECURRING_AMOUNT = 2;
    private final int INDEX_RECURRING_TAX = 3;
    private final int INDEX_RECURRING_NO_PAYMENTS = 4;
    private final int INDEX_RECURRING_FREQUENCY = 5;
    private final int INDEX_RECURRING_FIRST_PAYMENT = 6;
    private final int INDEX_RECURRING_PAYER = 7;
    private final int INDEX_RECURRING_CATEGORY = 8;
    private final int INDEX_RECURRING_PAYMENT_METHOD = 9;
    private final int INDEX_RECURRING_STATUS = 10;
    private final int INDEX_RECURRING_IS_INCOME = 11;
    private final int INDEX_RECURRING_FK_ACCOUNT_ID = 12;

    //for account
    private final int INDEX_ACCOUNT_ID = 0;
    private final int INDEX_ACCOUNT_NAME = 1;
    private final int INDEX_ACCOUNT_DESCRIPTION = 2;
    private final int INDEX_ACCOUNT_CURRENCY = 3;
    private final int INDEX_ACCOUNT_INIT_BALANCE = 4;
    private final int INDEX_ACCOUNT_HAS_CARD = 5;
    private final int INDEX_ACCOUNT_LIMIT = 6;
    private final int INDEX_ACCOUNT_ISACTIVE = 7;


    //DECLARE INCOME TABLE
    private static final String TABLE_INCOME = "income";
    //attributes
    private static final String PKEY_ID_INCOME = "transac_id"; //INTEGER
    private static final String KEY_AMOUNT_INCOME = "amount"; // DOUBLE
    private static final String KEY_DATE_INCOME = "date"; // DATETIME
    private static final String KEY_REF_CHECK_INCOME = "ref_check"; // INTEGER
    private static final String KEY_DESCRIPTION_INCOME = "description"; // VARCHAR(200)
    private static final String KEY_TAX_INCOME = "tax"; //DOUBLE
    private static final String KEY_QUANTITY_INCOME = "quantity"; //INTEGER
    private static final String KEY_PAYER = "payer"; //VARCHAR(30)
    private static final String KEY_PAYMENT_METHOD_INCOME = "payment_method";
    private static final String KEY_TAGS_INCOME = "tags";
    private static final String FKEY_ID_INCOME = "account_id"; //INTEGER
    //private static final String KEY_UNIT_INCOME ="unit";
    private static final String KEY_CATEGORY_INCOME = "category";
    private static final String KEY_STATUS_INCOME = "status";

    //DECLARE EXPENSE TABLE
    private static final String TABLE_EXPENSE = "expense";
    //attributes
    private static final String PKEY_ID_EXP = "transac_id"; //INTEGER
    private static final String KEY_AMOUNT_EXP = "amount"; // DOUBLE
    private static final String KEY_PAYMENT_METHOD_EXP = "payment_method"; // DOUBLE
    private static final String KEY_DATE_EXP = "date"; //DATETIME
    private static final String KEY_REF_CHECK_EXP = "ref_check"; // INTEGER
    private static final String KEY_DESCRIPTION_EXP = "description"; //VARCHAR(300)
    private static final String KEY_TAX_EXP = "tax";  //DOUBLE
    private static final String KEY_QUANTITY_EXP = "quantity"; //INTEGER
    private static final String KEY_PAYEE = "payee"; //VARCHAR(30)
    private static final String KEY_TAGS_EXP= "tags"; //VARCHAR(30)
    private static final String FKEY_ID_EXP = "account_id"; //INTEGER
    //private static final String KEY_UNIT_EXP ="unit";
    private static final String KEY_CATEGORY_EXP = "category";
    private static final String KEY_STATUS_EXP = "status";



    //DECLARE RECURRENCES
    private static final String TABLE_RECURRENCE = "recurrence";
    //attributes
    private static final String PKEY_ID_RECURRENCE = "recurrence_id";
    private static final String KEY_DESCRIPTION_RECURRENCE = "description";
    private static final String KEY_AMOUNT_RECURRENCE = "amount";
    private static final String KEY_TAX_REC = "tax"; //DOUBLE
    private static final String KEY_NO_PAYMENTS = "no_of_payment"; //INTEGER
    private static final String KEY_FREQUENCY = "frequency"; //STRING
    private static final String KEY_FIRST_PAYMENT = "first_payment"; // DATETIME
    private static final String KEY_PAYER_REC = "payer";  //STRING
    private static final String KEY_CATEGORY_RECURRENCE = "category";
    private static final String KEY_PAYMENTMETHOD_RECURRENCE = "payment_method";
    private static final String KEY_STATUS_RECURRENCE = "status";
    private static final String KEY_IS_INCOME_RECURRENCE = "is_income"; //BOOLEAN
    private static final String FKEY_ID_RECURRING = "account_id"; //INTEGER

    //DECLARE ACCOUNT
    private static final String TABLE_ACCOUNT = "account";
    //attributes
    private static final String KEY_ID_ACCOUNT = "account_id";//INT
    private static final String KEY_NAME = "name";//STRING
    private static final String KEY_DESCRIPTION_ACCOUNT = "description";//STRING
    private static final String KEY_CURRENCY = "currency";//STRING
    private static final String KEY_INIT_BALANCE = "init_balance";//DOUBLE
    private static final String KEY_HAS_CARD = "has_card";//BOOLEAN
    private static final String KEY_LIMIT = "card_limit";//DOUBLE
    private static final String KEY_ISACTIVE = "is_active";

    //DECLARE LOG
    private static final String TABLE_LOG = "log";
    //attributes
    private static final String KEY_ID_LOG = "log_id";
    private static final String FKEY_ACCOUNT_ID = "account_id";
    private static final String FKEY_TRANSAC_ID = "transac_id";



    // CREATE INCOME TABLE
    private static final String CREATE_INCOME_TABLE =
            "CREATE TABLE " + TABLE_INCOME + " (" +
                    PKEY_ID_INCOME + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AMOUNT_INCOME + " DOUBLE, " +
                    KEY_PAYMENT_METHOD_INCOME + " STRING, " + KEY_DATE_INCOME + " DATETIME, " + KEY_REF_CHECK_INCOME+ " INTEGER, " +
                    KEY_DESCRIPTION_INCOME + " VARCHAR(300), " + KEY_TAX_INCOME + " DOUBLE, " + KEY_QUANTITY_INCOME + " INTEGER, " + KEY_PAYER + " VARCHAR(30), " + KEY_TAGS_INCOME + " VARCHAR(30), "+
                    KEY_CATEGORY_INCOME + " VARCHAR(30), " + KEY_STATUS_INCOME + " VARCHAR(30), " +
                    FKEY_ID_INCOME + " INTEGER, FOREIGN KEY ("+FKEY_ID_INCOME+") REFERENCES "+TABLE_ACCOUNT+"("+KEY_ID_ACCOUNT+") ON DELETE CASCADE ON UPDATE CASCADE)";

    // CREATE EXPENSE TABLE
    public static final String CREATE_EXPENSE_TABLE =
            "CREATE TABLE " + TABLE_EXPENSE + "(" +
            PKEY_ID_EXP + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_AMOUNT_EXP + " DOUBLE,"+
            KEY_PAYMENT_METHOD_EXP + " STRING, "+ KEY_DATE_EXP + " DATETIME, "+KEY_REF_CHECK_EXP+" INTEGER, "+
            KEY_DESCRIPTION_EXP+" VARCHAR(300), " +KEY_TAX_EXP+" DOUBLE, "+KEY_QUANTITY_EXP+" INTEGER, "+KEY_PAYEE+" VARCHAR(30), "+KEY_TAGS_EXP+" VARCHAR(30), "+
            KEY_CATEGORY_EXP + " VARCHAR(30), " + KEY_STATUS_EXP + " VARCHAR(30), " +
            FKEY_ID_EXP + " INTEGER, FOREIGN KEY ("+FKEY_ID_EXP+") REFERENCES "+TABLE_ACCOUNT+"("+KEY_ID_ACCOUNT+") ON DELETE CASCADE ON UPDATE CASCADE)";

    // CREATE RECURRENCE TABLE
    private static  final String CREATE_RECURRENCE_TABLE =
            "CREATE TABLE " + TABLE_RECURRENCE + "(" + PKEY_ID_RECURRENCE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_DESCRIPTION_RECURRENCE + " STRING," + KEY_AMOUNT_RECURRENCE + " DOUBLE," +
                    KEY_TAX_REC + " DOUBLE, " + KEY_NO_PAYMENTS + " INTEGER, " + KEY_FREQUENCY + " STRING, " +
                    KEY_FIRST_PAYMENT + " DATETIME, " + KEY_PAYER_REC + " STRING," +
                    KEY_CATEGORY_RECURRENCE + " STRING," + KEY_PAYMENTMETHOD_RECURRENCE + " STRING," +
                    KEY_STATUS_RECURRENCE + " STRING," + KEY_IS_INCOME_RECURRENCE + " BOOLEAN, " +
                    FKEY_ID_RECURRING + " INTEGER, FOREIGN KEY("+ FKEY_ID_RECURRING+") REFERENCES "+ TABLE_ACCOUNT +"("+KEY_ID_ACCOUNT+") ON DELETE CASCADE ON UPDATE CASCADE)";

    // CREATE CUSTOM TABLE INCOME

    // CREATE ACCOUNT TABLE
    public static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " +  TABLE_ACCOUNT +
            "(" + KEY_ID_ACCOUNT + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_NAME + " VARCHAR(15), " + KEY_DESCRIPTION_ACCOUNT + " VARCHAR(30), " + KEY_CURRENCY + " VARCHAR(5), "+
            KEY_INIT_BALANCE + " DOUBLE, " + KEY_HAS_CARD + " BOOLEAN, " + KEY_LIMIT + " DOUBLE, " +
            KEY_ISACTIVE + " BOOLEAN " + ")";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("SQL [CREATE TABLES]: ", CREATE_ACCOUNT_TABLE);
        Log.i("SQL [CREATE TABLES]: ", CREATE_INCOME_TABLE);
        Log.i("SQL [CREATE TABLES]: ", CREATE_EXPENSE_TABLE);
        Log.i("SQL [CREATE TABLES]: ", CREATE_RECURRENCE_TABLE);


        db.execSQL(CREATE_ACCOUNT_TABLE);
        db.execSQL(CREATE_EXPENSE_TABLE);
        db.execSQL(CREATE_INCOME_TABLE);
        db.execSQL(CREATE_RECURRENCE_TABLE);

        createDefaultAccount(db);



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECURRENCE);
        onCreate(db);
        db.close();
    }


    /*
        INCOME QUERIES HERE
        CRUD Operations
        If there is a RetrieveAll method,  return an ArrayList
        E suwat ang parameters (input) then unsa iya e return
     */

    /**
     * @param transaction Income transation to be added to the database
     * adds income transaction
     */
    public void addIncome(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");


        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);


        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT_INCOME, transaction.getKEY_AMOUNT());
        values.put(KEY_PAYMENT_METHOD_INCOME, transaction.getKEY_PAYMETHOD());
        values.put(KEY_DATE_INCOME, date);
        values.put(KEY_REF_CHECK_INCOME, transaction.getKEY_REF_CHECK());
        values.put(KEY_DESCRIPTION_INCOME, transaction.getKEY_DESCRIPTION());
        values.put(KEY_TAX_INCOME, transaction.getKEY_TAX());
        values.put(KEY_QUANTITY_INCOME, transaction.getKEY_QUANTITY());
        values.put(KEY_PAYER, transaction.getKEY_PAYER()); // check
        values.put(KEY_TAGS_INCOME, transaction.getKEY_TAGS());
        //values.put(KEY_UNIT_INCOME, transaction.getKEY_UNIT());
        values.put(KEY_CATEGORY_INCOME, transaction.getKEY_CATEGORY());
        values.put(KEY_STATUS_INCOME, transaction.getKEY_STATUS());
        values.put(FKEY_ID_INCOME, transaction.getFKEY_ID());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.insert(TABLE_INCOME, null, values);
        db.close();
    }

    /**
     * @param KEY_ID ID of the Income Transaction to be retrieved
     * @return Transaction = Income Transaction being retrieved
     */
    public Transaction getIncomeTransaction(int KEY_ID){
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME + " WHERE " + PKEY_ID_INCOME +
                " LIKE ?", new String[]{""+KEY_ID});
        Log.w("WhereClause", "SELECT * FROM " + TABLE_INCOME + " WHERE " + PKEY_ID_INCOME +
                "= ?" + KEY_ID);
        Log.w("Cursor count", "" + cursor.getCount());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        Transaction transaction = new Transaction();


        if(cursor!=null && cursor.moveToFirst()){
            transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_TRANSACTION_AMOUNT));
            transaction.setKEY_TAX(cursor.getDouble(INDEX_TRANSACTION_TAX));
            transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_TRANSACTION_DESCRIPTION));
            transaction.setKEY_PAYER(cursor.getString(INDEX_TRANSACTION_PAYER));
            transaction.setKEY_REF_CHECK(cursor.getInt(INDEX_TRANSACTION_REF_CHECK));

            String date = cursor.getString(INDEX_TRANSACTION_DATE);
            java.util.Date javaUtilDate = new java.util.Date();
            java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            try {
                javaUtilDate = sdf1.parse(date);
                javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            transaction.setKEY_DATE(javaSqlDate);

            transaction.setKEY_QUANTITY(cursor.getInt(INDEX_TRANSACTION_QUANTITY));
            transaction.setKEY_ID(cursor.getInt(INDEX_TRANSACTION_ID));
            transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_TRANSACTION_PAYMENT_METHOD));
            transaction.setKEY_TAGS(cursor.getString(INDEX_TRANSACTION_TAGS));
           // transaction.setKEY_UNIT(cursor.getString(INDEX_TRANSACTION_UNIT));
            transaction.setKEY_CATEGORY(cursor.getString(INDEX_TRANSACTION_CATEGORY));
            transaction.setKEY_STATUS(cursor.getString(INDEX_TRANSACTION_STATUS));
            transaction.setFKEY_ID(cursor.getInt(INDEX_TRANSACTION_FK_ACCOUNT_ID));
            cursor.close();
            Log.w("GETINCOMETRANSACTION", "Inside IF statement");
        }
        else{
            Log.w("GETINCOMETRANSACTION" , "Inside ELSE statement");
        }

        return transaction;
    }

    /**
     * get all income transaction
     * @return ArrayList<Transaction> = List of All income Transactions
     */
    public ArrayList<Transaction> getAllIncomeTransactions(){
        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_INCOME+ " WHERE "+FKEY_ID_INCOME+" = "+ RunTime.ACTIVE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);

        Log.i("cursor size", "" + cursor.getColumnCount());
        if(cursor.moveToFirst()){
            do{
                Transaction transaction = new Transaction();
                transaction.setKEY_ID(cursor.getInt(INDEX_TRANSACTION_ID));
                transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_TRANSACTION_AMOUNT));
                transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_TRANSACTION_PAYMENT_METHOD));

                String date = cursor.getString(INDEX_TRANSACTION_DATE);
                java.util.Date javaUtilDate = new java.util.Date();
                java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
                try {
                    javaUtilDate = sdf1.parse(date);
                    javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setKEY_DATE(javaSqlDate);

                transaction.setKEY_REF_CHECK(cursor.getInt(INDEX_TRANSACTION_REF_CHECK));
                transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_TRANSACTION_DESCRIPTION));
                transaction.setKEY_TAX(cursor.getDouble(INDEX_TRANSACTION_TAX));
                transaction.setKEY_QUANTITY(cursor.getInt(INDEX_TRANSACTION_QUANTITY));
                transaction.setKEY_PAYER(cursor.getString(INDEX_TRANSACTION_PAYER));
                transaction.setKEY_TAGS(cursor.getString(INDEX_TRANSACTION_TAGS));
                //transaction.setKEY_UNIT(cursor.getString(INDEX_TRANSACTION_UNIT));
                transaction.setKEY_CATEGORY(cursor.getString(INDEX_TRANSACTION_CATEGORY));
                transaction.setKEY_STATUS(cursor.getString(INDEX_TRANSACTION_STATUS));
                transaction.setFKEY_ID(cursor.getInt(INDEX_TRANSACTION_FK_ACCOUNT_ID));
                transactionList.add(transaction);
            }while(cursor.moveToNext());
        }

        return transactionList;

    }

    /**
     * updates a specific income transaction
     * @param transaction Updated info of the income transaction
     * @param KEY_ID ID of the income Transaction to be Updated
     */
    public void updateIncomeTransaction(Transaction transaction, int KEY_ID){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");



        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);

        values.put(KEY_AMOUNT_INCOME, transaction.getKEY_AMOUNT());
        values.put(KEY_DATE_INCOME, date);
        values.put(KEY_REF_CHECK_INCOME, transaction.getKEY_REF_CHECK());
        values.put(KEY_DESCRIPTION_INCOME, transaction.getKEY_DESCRIPTION());
        values.put(KEY_TAX_INCOME, transaction.getKEY_TAX());
        values.put(KEY_QUANTITY_INCOME, transaction.getKEY_QUANTITY());
        values.put(KEY_PAYER, transaction.getKEY_PAYER());
        values.put(KEY_TAGS_INCOME, transaction.getKEY_TAGS());
        values.put(KEY_PAYMENT_METHOD_INCOME, transaction.getKEY_PAYMETHOD());
        //values.put(KEY_UNIT_INCOME, transaction.getKEY_UNIT());
        values.put(KEY_CATEGORY_INCOME, transaction.getKEY_CATEGORY());
        values.put(KEY_STATUS_INCOME, transaction.getKEY_STATUS());
        values.put(FKEY_ID_INCOME, transaction.getFKEY_ID());
        String whereClause = PKEY_ID_INCOME + " LIKE ? ";
        String[] whereArgs = {""+KEY_ID};
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.update(TABLE_INCOME, values, whereClause, whereArgs);
;
    }

    /**
     * deletes a specific income transaction
     * @param KEY_ID income ID to be deleted
     */
    public void deleteIncomeTransaction(int KEY_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.delete(TABLE_INCOME, PKEY_ID_INCOME + " = ?", new String[]{"" + KEY_ID});
//        db.delete(TABLE_CUSTOM_INCOME, FKEY_ID_CUSTOM_INCOME+" = ?", new String[]{""+KEY_ID});

    }

    /**
     * Adds expense transaction
     * @param transaction ID of the Expense Transaction tobe added to the database
     */
    public void addExpense(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");


        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);


        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT_EXP, transaction.getKEY_AMOUNT());
        values.put(KEY_PAYMENT_METHOD_EXP, transaction.getKEY_PAYMETHOD());
        values.put(KEY_DATE_EXP, date);
        values.put(KEY_REF_CHECK_EXP, transaction.getKEY_REF_CHECK());
        values.put(KEY_DESCRIPTION_EXP, transaction.getKEY_DESCRIPTION());
        values.put(KEY_TAX_EXP, transaction.getKEY_TAX());
        values.put(KEY_QUANTITY_EXP, transaction.getKEY_QUANTITY());
        values.put(KEY_PAYEE, transaction.getKEY_PAYER()); // check
        values.put(KEY_TAGS_EXP, transaction.getKEY_TAGS());
        //values.put(KEY_UNIT_INCOME, transaction.getKEY_UNIT());
        values.put(KEY_CATEGORY_EXP, transaction.getKEY_CATEGORY());
        values.put(KEY_STATUS_EXP, transaction.getKEY_STATUS());
        values.put(FKEY_ID_EXP, transaction.getFKEY_ID());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.insert(TABLE_EXPENSE, null, values);
    }

    /**
     * gets a specific expense transaction
     * @param KEY_ID ID of the Expense Transaction to be retrieved
     * @return Transaction = Expense Transaction being retrieved
     */
    public Transaction getExpenseTransaction(int KEY_ID){
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_EXPENSE + " WHERE " + PKEY_ID_EXP +
                " LIKE ?", new String[]{""+KEY_ID});
        Log.w("WhereClause", "SELECT * FROM " + TABLE_EXPENSE + " WHERE " + PKEY_ID_EXP +
                "= ?" + KEY_ID);
        Log.w("Cursor count", "" + cursor.getCount());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        Transaction transaction = new Transaction();


        if(cursor!=null && cursor.moveToFirst()){
            transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_TRANSACTION_AMOUNT));
            transaction.setKEY_TAX(cursor.getDouble(INDEX_TRANSACTION_TAX));
            transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_TRANSACTION_DESCRIPTION));
            transaction.setKEY_PAYER(cursor.getString(INDEX_TRANSACTION_PAYER));
            transaction.setKEY_REF_CHECK(cursor.getInt(INDEX_TRANSACTION_REF_CHECK));

            String date = cursor.getString(INDEX_TRANSACTION_DATE);
            java.util.Date javaUtilDate = new java.util.Date();
            java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            try {
                javaUtilDate = sdf1.parse(date);
                javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            transaction.setKEY_DATE(javaSqlDate);

            transaction.setKEY_QUANTITY(cursor.getInt(INDEX_TRANSACTION_QUANTITY));
            transaction.setKEY_ID(cursor.getInt(INDEX_TRANSACTION_ID));
            transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_TRANSACTION_PAYMENT_METHOD));
            transaction.setKEY_TAGS(cursor.getString(INDEX_TRANSACTION_TAGS));
            // transaction.setKEY_UNIT(cursor.getString(INDEX_TRANSACTION_UNIT));
            transaction.setKEY_CATEGORY(cursor.getString(INDEX_TRANSACTION_CATEGORY));
            transaction.setKEY_STATUS(cursor.getString(INDEX_TRANSACTION_STATUS));
            transaction.setFKEY_ID(cursor.getInt(INDEX_TRANSACTION_FK_ACCOUNT_ID));
            cursor.close();
            Log.w("GETINCOMETRANSACTION", "Inside IF statement");
        }
        else{
            Log.w("GETINCOMETRANSACTION" , "Inside ELSE statement");
        }

        return transaction;
    }

    /**
     * get all expense transaction
     * @return ArrayList<Transaction> = List of All Expense Transactions
     */
    public ArrayList<Transaction> getAllExpenseTransactions(){
        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_EXPENSE+ " WHERE "+FKEY_ID_EXP+" = "+RunTime.ACTIVE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);

        Log.i("cursor size", "" + cursor.getColumnCount());
        if(cursor.moveToFirst()){
            do{
                Transaction transaction = new Transaction();
                transaction.setKEY_ID(cursor.getInt(INDEX_TRANSACTION_ID));
                transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_TRANSACTION_AMOUNT));
                transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_TRANSACTION_PAYMENT_METHOD));

                String date = cursor.getString(INDEX_TRANSACTION_DATE);
                java.util.Date javaUtilDate = new java.util.Date();
                java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
                try {
                    javaUtilDate = sdf1.parse(date);
                    javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setKEY_DATE(javaSqlDate);

                transaction.setKEY_REF_CHECK(cursor.getInt(INDEX_TRANSACTION_REF_CHECK));
                transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_TRANSACTION_DESCRIPTION));
                transaction.setKEY_TAX(cursor.getDouble(INDEX_TRANSACTION_TAX));
                transaction.setKEY_QUANTITY(cursor.getInt(INDEX_TRANSACTION_QUANTITY));
                transaction.setKEY_PAYER(cursor.getString(INDEX_TRANSACTION_PAYER));
                transaction.setKEY_TAGS(cursor.getString(INDEX_TRANSACTION_TAGS));
                //transaction.setKEY_UNIT(cursor.getString(INDEX_TRANSACTION_UNIT));
                transaction.setKEY_CATEGORY(cursor.getString(INDEX_TRANSACTION_CATEGORY));
                transaction.setKEY_STATUS(cursor.getString(INDEX_TRANSACTION_STATUS));
                transaction.setFKEY_ID(cursor.getInt(INDEX_TRANSACTION_FK_ACCOUNT_ID));
                transactionList.add(transaction);
            }while(cursor.moveToNext());
        }

        return transactionList;


    }


    /**
     * updates a specific expense transaction
     * @param transaction Updated info of the expense transaction
     * @param KEY_ID ID of the Expense Transaction to be Updated
     */
    public void updateExpenseTransaction(Transaction transaction, int KEY_ID){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");



        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);

        values.put(KEY_AMOUNT_EXP, transaction.getKEY_AMOUNT());
        values.put(KEY_DATE_EXP, date);
        values.put(KEY_REF_CHECK_EXP, transaction.getKEY_REF_CHECK());
        values.put(KEY_DESCRIPTION_EXP, transaction.getKEY_DESCRIPTION());
        values.put(KEY_TAX_EXP, transaction.getKEY_TAX());
        values.put(KEY_QUANTITY_EXP, transaction.getKEY_QUANTITY());
        values.put(KEY_PAYEE, transaction.getKEY_PAYER());
        values.put(KEY_TAGS_EXP, transaction.getKEY_TAGS());
        values.put(KEY_PAYMENT_METHOD_EXP, transaction.getKEY_PAYMETHOD());
        //values.put(KEY_UNIT_INCOME, transaction.getKEY_UNIT());
        values.put(KEY_CATEGORY_EXP, transaction.getKEY_CATEGORY());
        values.put(KEY_STATUS_EXP, transaction.getKEY_STATUS());
        values.put(FKEY_ID_EXP, transaction.getFKEY_ID());
        String whereClause = PKEY_ID_EXP + " LIKE ? ";
        String[] whereArgs = {""+KEY_ID};
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.update(TABLE_EXPENSE, values, whereClause, whereArgs);
    }

    /**
     * deletes a specific expense transaction
     * @param KEY_ID Expense ID to be deleted
     */
    public void deleteExpenseTransaction(int KEY_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.delete(TABLE_EXPENSE, PKEY_ID_EXP + " = ?", new String[]{"" + KEY_ID});

    }


    /*
       RECURRENCES QUERIES HERE
       CRUD Operations
       If there is a RetrieveAll method,  return an ArrayList
       E suwat ang parameters (input) then unsa iya e return
    */
    public void addRecurrence(Transaction transaction){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");


        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);
//nopayments
        //is_income
        ContentValues values = new ContentValues();
        values.put(KEY_DESCRIPTION_RECURRENCE, transaction.getKEY_DESCRIPTION());
        values.put(KEY_AMOUNT_RECURRENCE, transaction.getKEY_AMOUNT());
        values.put(KEY_TAX_REC, transaction.getKEY_TAX());
        values.put(KEY_NO_PAYMENTS,transaction.getKEY_QUANTITY());
        values.put(KEY_FREQUENCY, transaction.getKEY_FREQUENCY());
        values.put(KEY_FIRST_PAYMENT, date);
        values.put(KEY_PAYER_REC, transaction.getKEY_PAYER()); // check
        values.put(KEY_CATEGORY_RECURRENCE, transaction.getKEY_CATEGORY());//done
        values.put(KEY_STATUS_RECURRENCE, transaction.getKEY_STATUS());//error
        values.put(KEY_PAYMENTMETHOD_RECURRENCE, transaction.getKEY_PAYMETHOD());
        values.put(KEY_IS_INCOME_RECURRENCE, transaction.getKEY_ISINCOME());
        values.put(FKEY_ID_EXP, transaction.getFKEY_ID());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.insert(TABLE_RECURRENCE, null, values);
    }

    // retrieve all expense transactions
    public ArrayList<Transaction> getAllRecurrenceTransactions(){
        ArrayList<Transaction> transactionList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_RECURRENCE+ " WHERE "+FKEY_ID_RECURRING+" = "+RunTime.ACTIVE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        if(cursor.moveToFirst()){
            do{
                Transaction transaction = new Transaction();
                transaction.setKEY_ID(cursor.getInt(INDEX_RECURRING_ID));
                transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_RECURRING_AMOUNT));
                transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_RECURRING_PAYMENT_METHOD));
                String date = cursor.getString(INDEX_RECURRING_FIRST_PAYMENT);
                java.util.Date javaUtilDate = new java.util.Date();
                java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
                try {
                    javaUtilDate = sdf1.parse(date);
                    javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                transaction.setKEY_DATE(javaSqlDate);
                transaction.setKEY_QUANTITY(cursor.getInt(INDEX_RECURRING_NO_PAYMENTS));
                transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_RECURRING_DESCRIPTION));
                transaction.setKEY_TAX(cursor.getDouble(INDEX_RECURRING_TAX));
                transaction.setKEY_FREQUENCY(cursor.getString(INDEX_RECURRING_FREQUENCY));
                transaction.setKEY_PAYER(cursor.getString(INDEX_RECURRING_PAYER));
                transaction.setKEY_ISINCOME(cursor.getInt(INDEX_RECURRING_IS_INCOME));
                transaction.setKEY_CATEGORY(cursor.getString(INDEX_RECURRING_CATEGORY));
                transaction.setKEY_STATUS(cursor.getString(INDEX_RECURRING_STATUS));
                transaction.setFKEY_ID(cursor.getInt(INDEX_RECURRING_FK_ACCOUNT_ID));
                transactionList.add(transaction);
            }while(cursor.moveToNext());
        }

        return transactionList;

    }

    public Transaction getRecurringTransaction(String args){
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RECURRENCE + " WHERE " + PKEY_ID_RECURRENCE +
                " LIKE ?", new String[]{args});
        Log.w("WhereClause","SELECT * FROM " + TABLE_RECURRENCE + " WHERE " + PKEY_ID_RECURRENCE +
                "= ?" + args);
        Log.w("Cursor count", "" + cursor.getCount());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        Transaction transaction = new Transaction();


        if(cursor!=null && cursor.moveToFirst()){
            transaction.setKEY_ID(cursor.getInt(INDEX_RECURRING_ID));
            transaction.setKEY_AMOUNT(cursor.getDouble(INDEX_RECURRING_AMOUNT));
            transaction.setKEY_PAYMETHOD(cursor.getString(INDEX_RECURRING_PAYMENT_METHOD));
            String date = cursor.getString(INDEX_RECURRING_FIRST_PAYMENT);
            java.util.Date javaUtilDate = new java.util.Date();
            java.sql.Date javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");
            try {
                javaUtilDate = sdf1.parse(date);
                javaSqlDate = new java.sql.Date(javaUtilDate.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            transaction.setKEY_DATE(javaSqlDate);
            transaction.setKEY_QUANTITY(cursor.getInt(INDEX_RECURRING_NO_PAYMENTS));
            transaction.setKEY_DESCRIPTION(cursor.getString(INDEX_RECURRING_DESCRIPTION));
            transaction.setKEY_TAX(cursor.getDouble(INDEX_RECURRING_TAX));
            transaction.setKEY_FREQUENCY(cursor.getString(INDEX_RECURRING_FREQUENCY));
            transaction.setKEY_PAYER(cursor.getString(INDEX_RECURRING_PAYER));
            transaction.setKEY_ISINCOME(cursor.getInt(INDEX_RECURRING_IS_INCOME));
            transaction.setKEY_CATEGORY(cursor.getString(INDEX_RECURRING_CATEGORY));
            transaction.setKEY_STATUS(cursor.getString(INDEX_RECURRING_STATUS));
            transaction.setFKEY_ID(cursor.getInt(INDEX_RECURRING_FK_ACCOUNT_ID));
            cursor.close();
            Log.w("GETEXPENSETRANSACTION", "Inside IF statement");
        }
        else{
            Log.w("GETEXPENSETRANSACTION" , "Inside ELSE statement");
        }

        return transaction;
    }

    //update a specific expense transaction
    public void updateRecurringTransaction(Transaction transaction, int KEY_ID){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat sdf1 = new SimpleDateFormat("EE dd-MM-yyyy");



        String date = sdf1.format(transaction.getKEY_DATE());
        Log.i("Date to DB", date);

        values.put(KEY_AMOUNT_RECURRENCE, transaction.getKEY_AMOUNT());
        values.put(KEY_PAYMENTMETHOD_RECURRENCE, transaction.getKEY_PAYMETHOD());
        values.put(KEY_FIRST_PAYMENT, date);
        values.put(KEY_NO_PAYMENTS,transaction.getKEY_QUANTITY());
        values.put(KEY_DESCRIPTION_RECURRENCE, transaction.getKEY_DESCRIPTION());
        values.put(KEY_TAX_REC, transaction.getKEY_TAX());
        values.put(KEY_FREQUENCY, transaction.getKEY_FREQUENCY());
        values.put(KEY_PAYER_REC, transaction.getKEY_PAYER()); // check
        values.put(KEY_CATEGORY_RECURRENCE, transaction.getKEY_CATEGORY());//error
        values.put(KEY_STATUS_RECURRENCE, transaction.getKEY_STATUS());//error
        values.put(KEY_IS_INCOME_RECURRENCE, transaction.getKEY_ISINCOME());
        values.put(FKEY_ID_EXP, transaction.getFKEY_ID());
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.update(TABLE_RECURRENCE, values,null,null);

    }

    //delete a specific expense transaction
    public void deleteRecurringTransaction(int KEY_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.delete(TABLE_RECURRENCE, PKEY_ID_RECURRENCE + " = ?", new String[]{"" + KEY_ID});
    }





    /**
     * adds account to db
     * @param account Account to be added to the database
     */
    public void addAccount(Account account){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, account.getKEY_NAME());
        values.put(KEY_DESCRIPTION_ACCOUNT, account.getKEY_DESCRIPTION());
        values.put(KEY_CURRENCY, account.getKEY_CURRENCY());
        values.put(KEY_INIT_BALANCE, account.getKEY_INIT_BALANCE());
        values.put(KEY_HAS_CARD, account.isKEY_HAS_CARD());
        values.put(KEY_LIMIT, account.getKEY_LIMIT());
        values.put(KEY_ISACTIVE, account.IS_ACTIVE());
        this.deactivateAccount(RunTime.ACTIVE_ACCOUNT);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.insert(TABLE_ACCOUNT, null, values);

    }

    /**
     * gets all account from db
     * @return ArrayList<Account> = List of All Accounts in the Database
     */
    public ArrayList<Account> getAllAccount(){
        ArrayList<Account> accountList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_ACCOUNT;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        if(cursor.moveToFirst()){
            do{
                Account account = new Account();
                account.setKEY_ID(cursor.getInt(INDEX_ACCOUNT_ID));
                account.setKEY_NAME(cursor.getString(INDEX_ACCOUNT_NAME));
                account.setKEY_DESCRIPTION(cursor.getString(INDEX_ACCOUNT_DESCRIPTION));
                account.setKEY_CURRENCY(cursor.getString(INDEX_ACCOUNT_CURRENCY));
                account.setKEY_INIT_BALANCE(cursor.getDouble(INDEX_ACCOUNT_INIT_BALANCE));
                account.setKEY_HAS_CARD(cursor.getInt(INDEX_ACCOUNT_HAS_CARD) != 0);
                account.setKEY_LIMIT(cursor.getDouble(INDEX_ACCOUNT_LIMIT));
                account.setIS_ACTIVE(cursor.getInt(INDEX_ACCOUNT_ISACTIVE) != 0);
                account.setLIST_EXPENSE_TRANSACTIONS(getTransaction(account.getKEY_ID(), 0));
                account.setLIST_INCOME_TRANSACTIONS(getTransaction(account.getKEY_ID(), -1));
                accountList.add(account);
            }while(cursor.moveToNext());
        }

        return accountList;

    }

    /**
     * get a specific account from db
     * @param KEY_ID Account ID to be retrieved
     * @return Account = The Account being retrieved
     */
    public Account getAccount(int KEY_ID){
        SQLiteDatabase db  = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + KEY_ID_ACCOUNT +
                " LIKE ?", new String[]{""+KEY_ID});
        Log.w("WhereClause", "SELECT * FROM " + TABLE_ACCOUNT + " WHERE " + KEY_ID_ACCOUNT +
                "= ?" + KEY_ID);
        Log.w("Cursor count", "" + cursor.getCount());
        Account account = new Account();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);

        if(cursor!=null && cursor.moveToFirst()){
            account.setKEY_ID(cursor.getInt(INDEX_ACCOUNT_ID));
            account.setKEY_NAME(cursor.getString(INDEX_ACCOUNT_NAME));
            account.setKEY_DESCRIPTION(cursor.getString(INDEX_ACCOUNT_DESCRIPTION));
            account.setKEY_CURRENCY(cursor.getString(INDEX_ACCOUNT_CURRENCY));
            account.setKEY_INIT_BALANCE(cursor.getDouble(INDEX_ACCOUNT_INIT_BALANCE));
            account.setKEY_HAS_CARD(cursor.getInt(INDEX_ACCOUNT_HAS_CARD) != 0);
            account.setKEY_LIMIT(cursor.getDouble(INDEX_ACCOUNT_LIMIT));
            account.setIS_ACTIVE(cursor.getInt(INDEX_ACCOUNT_ISACTIVE) != 0);
            account.setLIST_EXPENSE_TRANSACTIONS(getTransaction(account.getKEY_ID(), 0));
            account.setLIST_INCOME_TRANSACTIONS(getTransaction(account.getKEY_ID(), -1));
            cursor.close();
            Log.w("GETACCOUNT", "Inside IF statement");
        }
        else{
            Log.w("GETACCOUNT" , "Inside ELSE statement");
        }

        return account;
    }

    /**
     * updates a specific account
     * @param account Updated info of the account
     * @param KEY_ID Account ID to be Updated
     */
    public void updateAccount(Account account, int KEY_ID){
        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        values.put(KEY_NAME, account.getKEY_NAME());
        values.put(KEY_DESCRIPTION_ACCOUNT, account.getKEY_DESCRIPTION());
        values.put(KEY_CURRENCY, account.getKEY_CURRENCY());
        values.put(KEY_INIT_BALANCE, account.getKEY_INIT_BALANCE());
        values.put(KEY_HAS_CARD, account.isKEY_HAS_CARD());
        values.put(KEY_LIMIT, account.getKEY_LIMIT());
        values.put(KEY_ISACTIVE, account.IS_ACTIVE());
        String whereClause = KEY_ID_ACCOUNT + " LIKE ? ";
        String[] whereArgs = {""+KEY_ID};
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        db.update(TABLE_ACCOUNT, values, whereClause, whereArgs);

    }

    /**
     * deletes a specific account from db
     * @param KEY_ID Account ID to be deleted
     */
    public void deleteAccount(int KEY_ID){
        SQLiteDatabase db = this.getWritableDatabase();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        String whereClause = KEY_ID_ACCOUNT + " LIKE ? ";
        String[] whereArgs = {""+KEY_ID};
        db.delete(TABLE_ACCOUNT, whereClause, whereArgs);

    }

    /**
     * Gets the Total Number of Account
     * @return Total Number of Account
     */
    public int getAccountCount(){
        String query = "SELECT * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        cursor.close();
        return cursor.getCount();
    }


     /*
        Additional Functions
     */


    /**
     * creates a defualt account when the user first run the program
     * @param db Database
     */
    private void createDefaultAccount( SQLiteDatabase db){
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);
        Account DEFAULT = new Account();
        DEFAULT.setKEY_NAME("Personal Account");
        DEFAULT.setKEY_DESCRIPTION("This is the default account");
        DEFAULT.setKEY_CURRENCY("PHP");
        DEFAULT.setKEY_INIT_BALANCE(0.00);
        DEFAULT.setKEY_HAS_CARD(false);
        DEFAULT.setKEY_LIMIT(0.00);
        DEFAULT.setIS_ACTIVE(true);
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, DEFAULT.getKEY_NAME());
        values.put(KEY_DESCRIPTION_ACCOUNT, DEFAULT.getKEY_DESCRIPTION());
        values.put(KEY_CURRENCY, DEFAULT.getKEY_CURRENCY());
        values.put(KEY_INIT_BALANCE, DEFAULT.getKEY_INIT_BALANCE());
        values.put(KEY_HAS_CARD, DEFAULT.isKEY_HAS_CARD());
        values.put(KEY_LIMIT, DEFAULT.getKEY_LIMIT());
        values.put(KEY_ISACTIVE, DEFAULT.IS_ACTIVE());
        db.insert(TABLE_ACCOUNT, null, values);

    }

    /**
     * gets the active account from db
     * @return Account = the Account_ID of the active account
     */
    public Account getActive_Account(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ACCOUNT + " WHERE "
                + KEY_ISACTIVE + " = ?", new String[]{"1"});

        Log.i("Cursor Size", "Row: " + cursor.getCount() + " Col: " + cursor.getColumnCount() +
                " Index: " + cursor.getColumnIndex(KEY_ID_ACCOUNT));
//      Log.w("Cursor count", "" + cursor.getCount());
        Account account = new Account();
        String pragma = "PRAGMA foreign_keys = ON";
        db.execSQL(pragma);

        if(cursor!=null && cursor.moveToFirst()){
            account.setKEY_ID(cursor.getInt(INDEX_ACCOUNT_ID));
            account.setKEY_NAME(cursor.getString(INDEX_ACCOUNT_NAME));
            account.setKEY_DESCRIPTION(cursor.getString(INDEX_ACCOUNT_DESCRIPTION));
            account.setKEY_CURRENCY(cursor.getString(INDEX_ACCOUNT_CURRENCY));
            account.setKEY_INIT_BALANCE(cursor.getDouble(INDEX_ACCOUNT_INIT_BALANCE));
            account.setKEY_HAS_CARD(cursor.getInt(INDEX_ACCOUNT_HAS_CARD) != 0);
            account.setKEY_LIMIT(cursor.getDouble(INDEX_ACCOUNT_LIMIT));
            account.setIS_ACTIVE(cursor.getInt(INDEX_ACCOUNT_ISACTIVE) != 0);
            account.setLIST_EXPENSE_TRANSACTIONS(getTransaction(account.getKEY_ID(), 0));
            account.setLIST_INCOME_TRANSACTIONS(getTransaction(account.getKEY_ID(), -1));
            cursor.close();
            Log.w("GETACCOUNT", "Inside IF statement");
        }
        else{
            Log.w("GETACCOUNT" , "Inside ELSE statement");
        }

        return account;
    }

    /**
     * gets a specific transaction type from the database
     * @param KEY_ID  Account ID
     * @param type  -1 = ALL; 0 = INCOME; 1 = EXPENSE
     * @return ArrayList<Transaction> = List of transactions being retrieved
     */
    public  ArrayList<Transaction> getTransaction(int KEY_ID, int type){
        ArrayList<Transaction> listOfDesiredTransactions = new ArrayList<>();
        ArrayList<Transaction> listOfAllTransaction = new ArrayList<>();

        if(type == INCOME){
            listOfAllTransaction = this.getAllIncomeTransactions();
        }
        else if(type == EXPENSE){
            listOfAllTransaction = this.getAllExpenseTransactions();

        }
        else if(type == ALL){
            listOfAllTransaction = this.getAllIncomeTransactions();
            listOfAllTransaction.addAll(this.getAllIncomeTransactions());

        }
        for(Transaction e:listOfAllTransaction){
            if(e.getFKEY_ID() == KEY_ID){
                listOfDesiredTransactions.add(e);
            }
        }
        return listOfDesiredTransactions;
    }

    /**
     * gets the total number of transaction based from the type given
     * @param KEY_ID Account ID
     * @param type -1 = ALL; 0 = INCOME; 1 = EXPENSE
     * @return int = transaction count
     */
    public int getTransactionCount(int KEY_ID, int type){
        String query = "";
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        if(type == INCOME){
            query = "SELECT * FROM " + TABLE_INCOME+" WHERE "+PKEY_ID_INCOME+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            cursor.close();
            count =  cursor.getCount();
        }
        else if(type == EXPENSE){
            query = "SELECT * FROM " + TABLE_EXPENSE+" WHERE "+PKEY_ID_EXP+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            cursor.close();
            count =  cursor.getCount();
        }
        else if(type == RECURRING){
            query = "SELECT * FROM " + TABLE_RECURRENCE+" WHERE "+PKEY_ID_RECURRENCE+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            cursor.close();
            count =  cursor.getCount();
        }
        else if(type == ALL){
            query = "SELECT * FROM " + TABLE_INCOME+" WHERE "+PKEY_ID_INCOME+ " = "+KEY_ID;
            Cursor cursorI = db.rawQuery(query, null);
            String pragmaI = "PRAGMA foreign_keys = ON";
            db.execSQL(pragmaI);
            cursorI.close();
            count =  cursorI.getCount();
            query = "SELECT * FROM " + TABLE_EXPENSE+" WHERE "+PKEY_ID_EXP+ " = "+KEY_ID;
            Cursor cursorE = db.rawQuery(query, null);
            String pragmaE = "PRAGMA foreign_keys = ON";
            db.execSQL(pragmaE);
            cursorE.close();
            count =  count + cursorE.getCount();
            query = "SELECT * FROM " + TABLE_RECURRENCE+" WHERE "+PKEY_ID_RECURRENCE+ " = "+KEY_ID;
            Cursor cursorR = db.rawQuery(query, null);
            String pragmaR = "PRAGMA foreign_keys = ON";
            db.execSQL(pragmaR);
            cursorR.close();
            count =  count + cursorR.getCount();
        }

        return count;
    }

    /**
     * gets the total amount of transaction based from the type given
     * @param KEY_ID Account ID
     * @param type -1 = ALL; 0 = INCOME; 1 = EXPENSE
     * @return double = total amount of desired Transaction
     */
    public double getTotalAmount(int KEY_ID, int type){

        String query = "";
        double total = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        if(type == INCOME){
            query = "SELECT SUM"+KEY_AMOUNT_INCOME+ "FROM " + TABLE_INCOME+" WHERE "+PKEY_ID_INCOME+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            total = cursor.getDouble(0);
        }
        else if(type == EXPENSE){
            query = "SELECT SUM"+KEY_AMOUNT_EXP+ "FROM " + TABLE_EXPENSE+" WHERE "+PKEY_ID_EXP+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            total = cursor.getDouble(0);
        }
        else if(type == ALL){
            query = "SELECT SUM"+KEY_AMOUNT_EXP+ "FROM " + TABLE_EXPENSE+" WHERE "+PKEY_ID_INCOME+ " = "+KEY_ID;
            Cursor cursor = db.rawQuery(query, null);
            String pragma = "PRAGMA foreign_keys = ON";
            db.execSQL(pragma);
            total = cursor.getDouble(0);
            query = "SELECT SUM"+KEY_AMOUNT_INCOME+ "FROM " + TABLE_INCOME+" WHERE "+PKEY_ID_EXP+ " = "+KEY_ID;
            Cursor cursors = db.rawQuery(query, null);
            String pragmas = "PRAGMA foreign_keys = ON";
            db.execSQL(pragmas);
            total = total + cursors.getDouble(0);
        }
        return total;
    }

    /**
     * Deactivates current account upon addition of new account
     * @param KEY_ID_ISACTIVE = Account ID to be deactivated
     */
    public void deactivateAccount(int KEY_ID_ISACTIVE){
        Account accountToBeDeactivated = this.getAccount(KEY_ID_ISACTIVE);
        accountToBeDeactivated.setIS_ACTIVE(false);
        this.updateAccount(accountToBeDeactivated, KEY_ID_ISACTIVE);
    }

    /**
     * Switches active account upon selection
     * @param KEY_ID_CURRENTACTIVE = Account ID to be Deactivated
     * @param KEY_ID_TOBEACTIVATED = Account ID to be Activated
     */
    public void switchActiveAccount(int KEY_ID_CURRENTACTIVE,int KEY_ID_TOBEACTIVATED){
        this.deactivateAccount(KEY_ID_CURRENTACTIVE);
        Account accountToBeDeactivated = this.getAccount(KEY_ID_TOBEACTIVATED);
        accountToBeDeactivated.setIS_ACTIVE(true);
        this.updateAccount(accountToBeDeactivated, KEY_ID_TOBEACTIVATED);


    }

    /**
     * Upon deletion of active account this sets the first Account to be active
     * @param KEY_ID = Account ID to be deleted
     * @return 0 = Deletion not Allowed; 1 = Deletion Allowed
     */
    public int nextAccount(int KEY_ID){
        ArrayList<Account> listOfAllAccounts = this.getAllAccount();
        if(listOfAllAccounts.size()==1)
            return 0;
        if (listOfAllAccounts.get(0).getKEY_ID() == KEY_ID)
            this.switchActiveAccount(KEY_ID,listOfAllAccounts.get(1).getKEY_ID());
        else
            this.switchActiveAccount(KEY_ID,listOfAllAccounts.get(0).getKEY_ID());
        return 1;
    }

    /**
     * Transfers a Transaction from current Account to Specific Account
     * @param type 0 = INCOME; 1 = EXPENSE
     * @param transaction = transaction to be transferred
     * @param KEY_ID = Account ID to where it will be transferred
     * @return 0 if unsuccessful; 1 if successful
     */
    public int transferTransaction(int type, Transaction transaction, int KEY_ID){
        Transaction updatedTransaction = new Transaction();
        if(type == INCOME){
            updatedTransaction = this.getIncomeTransaction(transaction.getKEY_ID());
            updatedTransaction.setFKEY_ID(KEY_ID);
            this.updateIncomeTransaction(updatedTransaction,updatedTransaction.getKEY_ID());
            return 1;
        }
        else if(type == EXPENSE){
            updatedTransaction = this.getExpenseTransaction(transaction.getKEY_ID());
            updatedTransaction.setFKEY_ID(KEY_ID);
            this.updateExpenseTransaction(updatedTransaction, updatedTransaction.getKEY_ID());
            return 1;
        }
        else if(type == RECURRING){
            updatedTransaction = this.getRecurringTransaction(""+transaction.getKEY_ID());
            updatedTransaction.setFKEY_ID(KEY_ID);
            this.updateRecurringTransaction(updatedTransaction, updatedTransaction.getKEY_ID());
            return 1;
        }
        return 0;
    }

    /**
     * Transfers all Transaction from current Account to Specific Account
     * @param type 0 = INCOME; 1 = EXPENSE
     * @param KEY_ID = Account ID to where it will be transferred
     * @return 0 if unsuccessful; 1 if successful
     */
    public int transferAllTransaction(int type, int KEY_ID){
        ArrayList<Transaction> listOfTransactions = new ArrayList<>();
        if(type == INCOME){
            listOfTransactions = this.getAllIncomeTransactions();
            for(Transaction t: listOfTransactions){
                this.transferTransaction(type,t,KEY_ID);
            }
            return 1;
        }
        else if(type == EXPENSE){
            listOfTransactions = this.getAllExpenseTransactions();
            for(Transaction t: listOfTransactions){
                this.transferTransaction(type,t,KEY_ID);
            }
            return 1;
        }
        else if(type == RECURRING){
            listOfTransactions = this.getAllRecurrenceTransactions();
            for(Transaction t: listOfTransactions){
                this.transferTransaction(type,t,KEY_ID);
            }
            return 1;
        }
        return 0;
    }


}
