package ru.ivanovpv.voicelearner.db;

import java.util.Date;

/**
 * Created by pivanov on 17.06.2015.
 */
public class Exam {
    public static final String TABLE="exams";
    public static final String _ID="_id";
    public static final String LESSON_ID="lesson_id";
    public static final String DATE="date_millis";
    public static final String PASSED="passed";
    public static final String POINTS="points";
    String id;
    Lesson lesson;
    Date date;
    Boolean passed;
    Integer point;
}
