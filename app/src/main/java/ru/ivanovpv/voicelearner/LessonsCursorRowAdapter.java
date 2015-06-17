package ru.ivanovpv.voicelearner;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ivanovpv.voicelearner.db.Lesson;

/**
 * Created by pivanov on 17.06.2015.
 */
public class LessonsCursorRowAdapter extends SimpleCursorAdapter {
    private LayoutInflater layoutInflater;
    private int layout;

    public LessonsCursorRowAdapter(Context context, int layout) {
        super(context, layout, null,
                new String[] { Lesson.LESSON_NAME, Lesson.MAX_POINTS},
                new int[] { R.id.lessonName, R.id.maxPoints}, 0
                );
        this.layoutInflater = LayoutInflater.from(context);
        this.layout=layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(layout, null);
        ViewHolder viewHolder=new ViewHolder();
        viewHolder.lessonName=cursor.getColumnName(cursor.getColumnIndex(Lesson.LESSON_NAME));
        viewHolder.maxPoints=cursor.getColumnName(cursor.getColumnIndex(Lesson.MAX_POINTS));
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder )view.getTag();
        TextView tv=(TextView )view.findViewById(R.id.lessonName);
        tv.setText(viewHolder.lessonName);
        tv=(TextView )view.findViewById(R.id.maxPoints);
        tv.setText(viewHolder.maxPoints);
    }

    static class ViewHolder {
        public String lessonName;
        public String maxPoints;
    }
}
