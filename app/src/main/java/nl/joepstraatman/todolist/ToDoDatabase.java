package nl.joepstraatman.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Gebruiker on 20-11-2017.
 */

public class ToDoDatabase extends SQLiteOpenHelper {
    private static ToDoDatabase instance;
    private ToDoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table todos (_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, completed BOOLEAN DEFAULT 0);");
        db.execSQL("INSERT INTO todos (title) Values ('shopping')");
        db.execSQL("INSERT INTO todos (title) Values ('cleaning')");
        db.execSQL("INSERT INTO todos (title,completed) Values ('washing', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "todos");
        onCreate(db);
    }

    public static ToDoDatabase getInstance(Context context){
        if(instance == null) {
            // call the private constr  uctor
            instance = new ToDoDatabase(context, "todos", null, 1);
        }
        return instance;
    }
    public Cursor selectAll(){
        return getWritableDatabase().rawQuery("SELECT * FROM todos", null);
    }
    public void insert(String title, int completed){
        SQLiteDatabase db = getWritableDatabase();//("INSERT INTO todos (title,completed) Values ('"+title+"',"+completed, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("completed",completed);
        db.insert("todos", null, contentValues);
        db.close();
    }
    public void update(long id){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor cursorc = db.rawQuery("SELECT * FROM todos WHERE _id = " + id, null);
        if (cursorc != null)
            cursorc.moveToFirst();
        int complIndex = cursorc.getColumnIndex("completed");
        if (cursorc.getInt(complIndex) == 0){
            contentValues.put("completed", 1);
        }else{
            contentValues.put("completed", 0);
        }
        db.update("todos",contentValues, "_id =" + id, null);
    }
    public void delete(long id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("todos","_id =" + id,null );
    }
}
