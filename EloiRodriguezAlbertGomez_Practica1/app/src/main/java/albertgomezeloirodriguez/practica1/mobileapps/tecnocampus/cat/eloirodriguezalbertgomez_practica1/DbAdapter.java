package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1;

/**
 * Created by albertgomez on 21/4/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Simple notes database access helper class. Defines the basic CRUD operations
 * for the notepad example, and gives the ability to list all notes as well as
 * retrieve or modify a specific note.
 */
public class DbAdapter {

    public static abstract class Todo implements BaseColumns {
        public static final String KEY_NOM = "nom";
        public static final String KEY_ROWID = "_id";
        public static final String KEY_SURNAME = "surname";
        public static final String KEY_DNI = "dni";
        public static final String KEY_TELF = "telf";
        public static final String KEY_CURS = "curs";
        public static final String KEY_GRAU = "grau";
        private static final String TABLE_NAME = "todo";

        private static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + "( "
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + KEY_NOM + " TEXT NOT NULL,"
                        + KEY_SURNAME + " TEXT NOT NULL, "
                        + KEY_DNI + " TEXT UNIQUE, "
                        + KEY_TELF + " TEXT NOT NULL, "
                        + KEY_GRAU + " TEXT NOT NULL, "
                        + KEY_CURS + " TEXT NOT NULL)";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        public static final String GET_COUNT = "SELECT count(*) FROM " + TABLE_NAME + ";";
    }

    private static DbAdapter instance = null;

    private static final String TAG = "NotesDbAdapter";
    private DatabaseHelper mDbHelper = null;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "dataStudent";
    private static final int DATABASE_VERSION = 4;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(Todo.CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL(Todo.DELETE_TABLE);
            onCreate(db);
        }
    }

    //implements the abstract factory pattern
    public static DbAdapter getInstance(Context ctx) {
        if (instance == null) {
            instance = new DbAdapter(ctx);
        }
        return instance;
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     *
     * @param ctx the Context within which to work
     */
    private DbAdapter(Context ctx) {
        this.mCtx = ctx;
        mDbHelper = null;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     *
     * @return this (self reference, allowing this to be chained in an
     * initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public DbAdapter open() throws SQLException {
        if (mDbHelper == null) {
            mDbHelper = new DatabaseHelper(mCtx);
            mDb = mDbHelper.getWritableDatabase();
        }
        return this;
    }

    public void close() {

        mDbHelper.close();
        mDbHelper = null;
    }

    public void upgrade() {
        mDbHelper.onUpgrade(mDb, 1, 2);
    }

    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * <p>
     * title the title of the note
     *
     * @return rowId or -1 if failed
     */

    public long createStudent(String nom, String surname,
                              String dni, String telf, String grau, String curs) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(Todo.KEY_NOM, nom);
        initialValues.put(Todo.KEY_SURNAME, surname);
        initialValues.put(Todo.KEY_DNI, dni);
        initialValues.put(Todo.KEY_TELF, telf);
        initialValues.put(Todo.KEY_GRAU, grau);
        initialValues.put(Todo.KEY_CURS, curs);


        return mDb.insertOrThrow(Todo.TABLE_NAME, null, initialValues);
    }

    /**
     * Delete the note with the given rowId
     * <p>
     * rowId id of note to delete
     *
     * @return true if deleted, false otherwise
     */
    public boolean deleteTodo(String dni) {

        return mDb.delete(Todo.TABLE_NAME, Todo.KEY_DNI + "=" + dni, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     *
     * @return Cursor over all notes
     */
    public Cursor fetchAllTodos() {

        return mDb.query(Todo.TABLE_NAME, new String[]{
                Todo.KEY_ROWID, Todo.KEY_NOM, Todo.KEY_SURNAME,
                Todo.KEY_TELF, Todo.KEY_DNI, Todo.KEY_GRAU, Todo.KEY_CURS}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     *
     * @param dni id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchTodo(String dni) throws SQLException {

        Cursor mCursor = mDb.query(true, Todo.TABLE_NAME, new String[]{Todo.KEY_ROWID, Todo.KEY_NOM,
                        Todo.KEY_SURNAME, Todo.KEY_TELF, Todo.KEY_DNI, Todo.KEY_GRAU, Todo.KEY_CURS},
                Todo.KEY_DNI + "=" + dni, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public int updateStudent(String nom, String surname, String dni, String telf, String grau, String curs) {

        ContentValues values = new ContentValues();

        values.put(Todo.KEY_NOM, nom);
        values.put(Todo.KEY_SURNAME, surname);
        values.put(Todo.KEY_TELF, telf);
        values.put(Todo.KEY_GRAU, grau);
        values.put(Todo.KEY_CURS, curs);

        return mDb.update(Todo.TABLE_NAME, values, Todo.KEY_DNI+"=?", new String[] {dni});

    }

    public boolean isEmpty() {

        Cursor cursor = mDb.rawQuery(Todo.GET_COUNT, null);
        cursor.moveToFirst();

        int i = cursor.getInt(0);
        Log.w("hola", "cursor num raws: " + i);
        return i <= 0;
    }
}