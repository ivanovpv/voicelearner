package ru.ivanovpv.voicelearner;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.ivanovpv.voicelearner.db.LearnerOpenHelper;

/**
 * Created by pivanov on 17.06.2015.
 */
public class Me extends Application {
    private static Me me;
    LearnerOpenHelper databaseHelper;

    public Me() {
        me = this;
    }

    public void onCreate() {
        super.onCreate();
    }

    public static Me getApplication() {
        return me;
    }

    public LearnerOpenHelper getDatabaseHelper(Context context) {
        if(databaseHelper==null)
            databaseHelper=new LearnerOpenHelper(context, 1);
        return databaseHelper;
    }
}
