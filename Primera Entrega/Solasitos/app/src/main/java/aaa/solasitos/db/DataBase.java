package aaa.solasitos.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String consCreate = "CREATE TABLE Usuarios (" +
                "'Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "'Username' VARCHAR(255)," +
                "'Password' VARCHAR(255)," +
                "'Nombre' VARCHAR(255)," +
                "'Apellido' VARCHAR(255))";
        db.execSQL(consCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String consUpgrade = "DROP TABLE IF EXISTS Usuarios";
        db.execSQL(consUpgrade);
        onCreate(db);
    }

    public boolean existsUser(String pUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consExists = "SELECT * FROM Usuarios WHERE Username = ?";
        Cursor cursor = db.rawQuery(consExists, new String[]{pUsername});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }

    public boolean existsUserPassword(String pUsername, String pPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        String consExists = "SELECT * FROM Usuarios WHERE Username = ? AND Password = ?";
        Cursor cursor = db.rawQuery(consExists, new String[]{pUsername,pPassword});
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }

    public void createUser(String pUsername, String pPassword, String pNombre, String pApellido) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues params = new ContentValues();
        params.put("Username",pUsername);
        params.put("Password",pPassword);
        params.put("Nombre",pNombre);
        params.put("Apellido",pApellido);
        db.insert("Usuarios",null,params);
        db.close();
    }
}

