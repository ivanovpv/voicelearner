package ru.ivanovpv.voicelearner;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Date;

import ru.ivanovpv.voicelearner.db.Exam;
import ru.ivanovpv.voicelearner.db.Lesson;

/**
 * Created by pivanov on 19.06.2015.
 */
public class ExamsCursorRowAdapter extends SimpleCursorAdapter {
    private LayoutInflater layoutInflater;
    private int layout;

    public ExamsCursorRowAdapter(Context context, int layout) {
        super(context, layout, null,
                new String[] { Exam.DATE, Exam.POINTS, Exam.PASSED},
                new int[] { R.id.examDate, R.id.examPoints, R.id.examPassed}, 0
        );
        this.layoutInflater = LayoutInflater.from(context);
        this.layout=layout;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = layoutInflater.inflate(layout, null);
        ViewHolder viewHolder=new ViewHolder();
        long millis=cursor.getLong(cursor.getColumnIndex(Exam.DATE));
        viewHolder.examDate=new Date(millis).toString();
        viewHolder.examPoints=cursor.getString(cursor.getColumnIndex(Exam.POINTS));
        String passed=cursor.getString(cursor.getColumnIndex(Exam.PASSED));
        viewHolder.examPassed=new Boolean(passed);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder )view.getTag();
        TextView tv=(TextView )view.findViewById(R.id.examDate);
        tv.setText(viewHolder.examDate);
        tv=(TextView )view.findViewById(R.id.examPoints);
        tv.setText(viewHolder.examPoints);
        CheckBox chk=(CheckBox )view.findViewById(R.id.examPassed);
        chk.setChecked(viewHolder.examPassed);
    }

    static class ViewHolder {
        public String examDate;
        public String examPoints;
        public boolean examPassed;
    }
}
