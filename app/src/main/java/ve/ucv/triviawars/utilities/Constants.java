package ve.ucv.triviawars.utilities;

public class Constants {
    public static final String DATABASE_NAME = "triviawars.db";
    public static final String TRIVIA_DATA_FILENAME = "trivia.json";
    public static final String QUESTION_DATA_FILENAME = "questions.json";
    public static final String USER_DATA_FILENAME = "users.json";
    public static final String TRIVIA_USER_DATA_FILENAME = "trivia-users.json";

    public static final int DEFAULT_GRID_COLUMNS = 2;

    public static final int OPTIONS_LIST_SIZE = 4;
    public static final long ANSWER_TIME_MILLISEC = 10000;
    public static final long SECOND = 1000;
    public static final int DEFAULT_QUESTIONS_LIST_SIZE = 10;

    public static final int ANSWER_TIME_SEC = (int)(ANSWER_TIME_MILLISEC /SECOND);

}