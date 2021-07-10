package com.example.fuckingretrofit.room;

import android.content.Context;
import androidx.room.Room;

/*
public class DatabaseService {
    private static final String ID = "id";
    private static final String EMAIL = "email";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String AVATAR = "avatar";
    private static DatabaseService dInstance;
    Context context;
    SQLiteDatabase db;
    Cursor query;

    private DatabaseService(Context context) {
        this.context = context;
    }

    public static DatabaseService getInstance(Context context) {
        if (dInstance == null) {
            dInstance = new DatabaseService(context);
        }
        return dInstance;
    }

    public void connect() {
        db = context.openOrCreateDatabase("app.db", MODE_PRIVATE, null);
        Log.e("LOGG", "бд создана");
    }

    public void close() {
        if (db != null) {
            db.close();
            Log.e("LOGG", "бд закрыта");
        }
    }

    public void create() {
        if (db != null) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS users (id INTEGER, email TEXT, firstname TEXT, lastname TEXT, avatar TEXT)");
                Log.e("LOGG", "таблица создана");
            } catch (SQLException e) {
            }
        }
    }

    public void insert(UserData user) {
        if (db != null) {
            try {
                ContentValues values = new ContentValues();
                values.put(ID, user.getId());
                values.put(EMAIL, user.getEmail());
                values.put(FIRSTNAME, user.getFirstName());
                values.put(LASTNAME, user.getLastName());
                values.put(AVATAR, user.getAvatar());
                db.insert("users", null, values);
                Log.e("LOGG", "данные вставлены");
            } catch (SQLException e) {
            }
        }
    }

    public List<UserData> select() {
        List<UserData> users = new ArrayList<>();
        if (db != null) {
            try {
                query = db.rawQuery("SELECT * FROM users;", null);
                while (query.moveToNext()) {
                    int id = query.getInt(0);
                    String email = query.getString(1);
                    String firstname = query.getString(2);
                    String lastname = query.getString(3);
                    String avatar = query.getString(4);
                    UserData user = new UserData(id, email, firstname, lastname, avatar);
                    users.add(user);
                    Log.e("LOGG", user.toString());
                }
                Log.e("LOGG", "данные выбраны");
                query.close();
            } catch (SQLException e) {
            }
        }
        return users;
    }
}
*/

/*
public class DatabaseService {
    private static DatabaseService dInstance;
    private Context context;
    private static AppDatabase db;

    private DatabaseService(Context context, AppDatabase db) {
        this.context = context;
        this.db = db;
    }

    public static DatabaseService getInstance(Context context) {
        if (dInstance == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "app.db")
                    .allowMainThreadQueries()   // remove then
                    .build();
            dInstance = new DatabaseService(context, db);

        }
        return dInstance;
    }

    public static AppDatabase getDb() {
        return db;
    }
}

//==========================
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract MyDataDao myDataDao();

    private static MyRoomDatabase INSTANCE;

    static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "data.sqlite3") // get db from assets
                            .openHelperFactory(new AssetSQLiteOpenHelperFactory())
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        synchronized (MyRoomDatabase.class) {
            INSTANCE = null;
        }
    }
}
*/