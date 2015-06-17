package ru.ivanovpv.voicelearner.db;

import java.util.ArrayList;

/**
 * Created by pivanov on 17.06.2015.
 */
public class WordTopic extends Topic {
    public static final String TABLE="wordtopics";
    public static final String _ID="_id";
    public static final String LESSON_ID="topic_id";
    public static final String WORD_ID="word_id";
    public ArrayList<Word> words;
}
