package ru.ivanovpv.voicelearner.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import ru.ivanovpv.voicelearner.Me;

/**
 * Created by pivanov on 17.06.2015.
 */
public class ExamsContentProvider extends ContentProvider {
    private static final String TAG=ExamsContentProvider.class.getName();
    private final static int GET_EXAMS_LIST = 0;
    private final static int GET_ONE_EXAM = 1;
    private static final String EXAMS_PATH = "exams";
    private static final String AUTHORITY = "ru.ivanovpv.db.ExamsContentProvider";
    public static final Uri EXAMS_URI = Uri.parse("content://" + AUTHORITY + "/" + EXAMS_PATH);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase database;

    static {
        sUriMatcher.addURI(AUTHORITY, EXAMS_PATH, GET_EXAMS_LIST);
        sUriMatcher.addURI(AUTHORITY, EXAMS_PATH + "/#", GET_ONE_EXAM);
    }

    public ExamsContentProvider() {
        super();
    }

    private void init() {
        if(database==null)
            database= Me.getApplication().getDatabaseHelper(this.getContext()).getWritableDatabase();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        init();
        return database.delete(Exam.TABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        init();
        long id=database.insert(Exam.TABLE, null, values);
        if(id==-1L)
            return null;
        return EXAMS_URI.buildUpon().appendPath(new Long(id).toString()).build();
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "Creating messages content provider");
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor=null;
        init();
        switch (sUriMatcher.match(uri)) {
            case GET_EXAMS_LIST:
                cursor = database.query(Exam.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GET_ONE_EXAM:
                String examId=uri.getLastPathSegment();
                selection=Exam._ID+"=?";
                selectionArgs=new String[]{examId};
                cursor = database.query(Exam.TABLE, projection, selection, selectionArgs, null, null, null);
                break;
        }
        if(cursor!=null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        init();
        return database.update(Exam.TABLE, values, selection, selectionArgs);
    }
}
