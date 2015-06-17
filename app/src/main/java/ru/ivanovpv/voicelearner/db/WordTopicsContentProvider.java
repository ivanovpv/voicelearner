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
public class WordTopicsContentProvider extends ContentProvider {
    private static final String TAG=LessonsContentProvider.class.getName();
    private final static int GET_TOPICS_LIST = 0;
    private final static int GET_ONE_TOPIC = 1;
    private static final String TOPICS_PATH = "topics";
    private static final String AUTHORITY = "ru.ivanovpv.db.TopicsContentProvider";
    public static final Uri TOPICS_URI = Uri.parse("content://" + AUTHORITY + "/" + TOPICS_PATH);
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase database;

    static {
        sUriMatcher.addURI(AUTHORITY, TOPICS_PATH, GET_TOPICS_LIST);
        sUriMatcher.addURI(AUTHORITY, TOPICS_PATH + "/#", GET_ONE_TOPIC);
    }

    public WordTopicsContentProvider() {
        super();
        database= Me.getApplication().getDatabaseHelper().getWritableDatabase();
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return database.delete(WordTopic.TABLE, selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id=database.insert(Lesson.TABLE, null, values);
        if(id==-1L)
            return null;
        return TOPICS_URI.buildUpon().appendPath(new Long(id).toString()).build();
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
        switch (sUriMatcher.match(uri)) {
            case GET_TOPICS_LIST:
                cursor = database.query(WordTopic.TABLE, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case GET_ONE_TOPIC:
                String lessonId=uri.getLastPathSegment();
                selection=Lesson._ID+"=?";
                selectionArgs=new String[]{lessonId};
                cursor = database.query(WordTopic.TABLE, projection, selection, selectionArgs, null, null, null);
                break;
        }
        if(cursor!=null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(WordTopic.TABLE, values, selection, selectionArgs);
    }
}

