package fotl.vyaapar.acq;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public  static final String databasename="vyaapar.db";
    public DatabaseHelper(Context context) {
        super(context, databasename,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table IF NOT EXISTS Login(Username varchar(20), Password varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }
    public boolean InsertLogin(ContentValues c)
    {
        SQLiteDatabase myDB=this.getWritableDatabase();
            Log.d("created","table");
            long Result=myDB.insert("Login", null, c);
            Log.d("result","inserted data");
        if(Result==-1)
            return false;
        else
            return true;


    }
    public Cursor getLogin()
    {
        SQLiteDatabase myDB=this.getWritableDatabase();
            Cursor c = myDB.rawQuery("select * from Login", null);
                return c;
    }
}
