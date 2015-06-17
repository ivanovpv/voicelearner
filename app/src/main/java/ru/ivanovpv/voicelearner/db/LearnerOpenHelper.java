package ru.ivanovpv.voicelearner.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/**
 * Created by pivanov on 17.06.2015.
 */
public class LearnerOpenHelper extends SQLiteOpenHelper {
    private final static String TAG=LearnerOpenHelper.class.getName();
    Context context;
    SQLiteDatabase myDataBase;

    private static final String LESSONS_CREATE="create table if not exists\n"
            + Lesson.TABLE+"(\n"
            + Lesson._ID+" integer primary key autoincrement,\n"
            + Lesson.LESSON_NAME+" String not null,\n"
            + ");";

    private static final String EXAMS_CREATE="create table if not exists\n"
            + Exam.TABLE+"(\n"
            + Exam._ID+" integer primary key autoincrement,\n"
            + Exam.LESSON_ID+" integer not null,\n"
            + Exam.DATE+" long, \n"
            + Exam.PASSED+" boolean, \n"
            + Exam.POINTS+" integer \n"
            +");";

    private static final String TOPICS_CREATE="create table if not exists\n"
            + WordTopic.TABLE+"(\n"
            + WordTopic._ID+" integer primary key autoincrement,\n"
            + WordTopic.LESSON_ID+" integer not null,\n"
            + WordTopic.WORD_ID+" integer not null \n"
            +");";

    public LearnerOpenHelper(Context context, int version) {
        super(context, "learner.db", null, version);
        this.context=context;
        try {
            if (!checkDataBase()) {
                this.copyDataBaseFromAssets();
            }
            openDatabase();
        } catch (Exception ex) {
            Log.i(TAG, "Error copying from assets or opening database", ex);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.beginTransaction();
        try
        {
            database.execSQL(LESSONS_CREATE);
            database.execSQL(EXAMS_CREATE);
            database.execSQL(TOPICS_CREATE);
            database.setTransactionSuccessful();
        }
        catch(Exception e)
        {
            Log.d(TAG, "Can't create VoiceLearner database", e);
        }
        database.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // copy the database file from asset folder to the DDMS database folder
    private void copyDataBaseFromAssets() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = context.getAssets().open(getDatabaseName());

        // Path to the just created empty db
        File outFile = context.getDatabasePath(this.getDatabaseName());

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFile);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    // this method will check the existance of database
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        String myPath = context.getDatabasePath(this.getDatabaseName()).getPath();
        try {
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    public void openDatabase() throws SQLException {
        //Open the database
        String mypath = context.getDatabasePath(this.getDatabaseName()).getPath();
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
