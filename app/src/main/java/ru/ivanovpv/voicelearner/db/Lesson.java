package ru.ivanovpv.voicelearner.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pivanov on 17.06.2015.
 */
public class Lesson {
    public static final String TABLE="lessons";
    public static final String _ID="_id";
    public static final String LESSON_NAME="lesson_name";
    public static final String MAX_POINTS="max_points";
    String id;
    String name;
    Integer maxPoints;;
    ArrayList<Topic> topics;
}
